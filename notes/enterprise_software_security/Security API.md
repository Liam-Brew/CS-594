# Security API

## Table of Contents

- [Security API](#security-api)
  - [Table of Contents](#table-of-contents)
  - [Java Standard Edition Security Mechanisms](#java-standard-edition-security-mechanisms)
  - [Jakarta Enterprise Edition Security Mechanisms](#jakarta-enterprise-edition-security-mechanisms)
  - [HTTP Authentication](#http-authentication)
    - [Basic HTTP Authentication](#basic-http-authentication)
    - [Form-based Authentication](#form-based-authentication)
    - [Custom Form-based Authentication](#custom-form-based-authentication)
    - [Web Authorization](#web-authorization)
    - [Identity Store](#identity-store)
    - [Security Context API](#security-context-api)
    - [API-Based Access Control](#api-based-access-control)

## Java Standard Edition Security Mechanisms

The standard edition of Java has the following security tools:

- Java Authentication and Authorization Service (JAAS)
  - PAM (pluggable login framework)
- Java Generic Security Services (Java GSS-API)
  - Kerberos
- Java Cryptography Extension (JCE)
- Java Secure Sockets Extension (JSSE)
- Simple Authentication and Security Layer (SASL)
  - use for LDAP-based authentication

## Jakarta Enterprise Edition Security Mechanisms

Security mechanisms for the following levels are as follow:

- application
  - authentication
  - role-based authorization
- transport
  - NONE, CONFIDENTIALITY, INTEGRITY
- messages
  - JSR-196 Java Authentication Service Provider Interface for Containers (JASPIC)

There are several ways of specifying security:

- annotations (most preferable)
  - ```@RolesAllowed("doctor")```
- deployment descriptors
  - web.xml
- programmmatic
  - ```if (context.isCallerInRole("doctor")){...}```

The core features of the Jakarta security API are:

- [HTTP authentication mechanism](#http-authentication)
- [identity store](#identity-store)
- [security context](#security-context)

The best way to do this is by adding a Maven dependency

## HTTP Authentication

The Security API supports the following methods of authentication using an annotation-based configuration:

### Basic HTTP Authentication

This consists of basic HTTP authentication, such as the sending of credentials

![basic_http](/notes/assets/enterprise_software_security/basic_http.PNG)

```java
// Basic HTTP authentication example configuration
@BasicAuthenticationMechanismDefinition(realmName = "userRealm")
@ApplicationScoped
public class AppConfig {}
```

The realm identifies which security group the code is in

### Form-based Authentication

This form of authentication sends credentials with a login request (only over HTTPS!), thereby creating an HTTP session on the server. This allows the session ID to be part of subsequent request contexts

![form_http](/notes/assets/enterprise_software_security/form_http.PNG)

```java
// Form HTTP authentication example configuration
@FormAuthenticationMechanismDefinition(
    loginToContinue = @LoginToContinue(
        loginPage = "/login.html",
        errorPage = "/login-error.html"
    )
)
@ApplicationScoped
public class AppConfig {}
```

This grants the option of customizing the login and error pages

### Custom Form-based Authentication

```java
// Custom form HTTP authentication example configuration
@CustomFormAuthenticationMechanismDefinition(
    loginToContinue = @LoginToContinue(
        loginPage = "/login.html",
    )
)
@ApplicationScoped
public class AppConfig {}
```

This allows backing authentication process:

```java
// Backing authentication example
@Named("LoginBacking")
@RequestScoped
public class LoginBackingBean {
    @Inject SecurityContext securityContext;

    @NotNull private String username;
    @NotNull private String password;

    public void login() {
        Credential credential = new UsernamePasswordCredential(username, new Password(password));

        AuthenticationStatus status = securityContext.authenticate(
            getHttpRequestFromFacesContext(),
            getHttpResponseFromFacesContext(),
            withParams().credential(credential));
        // ...
    }
}
```

Allows programmatic authentication of the user. Used for features such as multi-factor authentication. An example of a custom authentication mechanism is seen below:

```java
// Custom authentication mechanism example
@ApplicationScoped
public class CustomAuthentication implements HttpAuthenticationMechanism {
    public AuthenticationStatus validateRequest(
        HttpServletRequest request,
        HttpServletResponse response,
        HttPMessageContext httpMsgContext
    ) {
        String username = request.getParameter("username");
        String password = response.getParameter("password");

        UserDetail userDetail = // Obtain user information from a database
        if (userDetail != null) {
            return httpMsgContext.notifyContainerAboutLogin(
                new CustomPrincipal(userDetail),
                new HashSet<>(userDetail.getRoles())
            );
        }
        return httpMsgContext.responseUnauthorized();
    }
}
```

### Web Authorization

![web_authorization](/notes/assets/enterprise_software_security/web_authorization.PNG)

Role-based access control (RBAC) is used in enterprise environments to scale with the size of the entity:

![rbac](/notes/assets/enterprise_software_security/rbac.PNG)

An example of RBAC is the following:

```java
// RBAC example configuration
@Entity
public class User implements Serializable {
    @Id
    private String username;

    private String password;

    private List<Role> roles;
}

@Entity
public class Role implements Serializable {
    @Id
    private String rolename;

    private String description;
    
    private Collection<User> users;
}
```

The following annotations assist with authorization:

```java
// Authentication annotations

@WebServlet("/admin")   // Sets context root for permissions
@ServletSecurity(       // Specifies permissions to be enforced on @WebServlet 
    value = @HttpConstraint(rolesAllowed = {"admin"}),
    httpMethodConstraints = {
        @HttpMethodConstraint(
            value = "GET",
            rolesAllowed = {"admin"}),
        @HttpMethodConstraint(
            value = "POST",
            rolesAllowed = {"superadmin"})
        }
    ) 
public class AdminServlet extends HttpServlet {
    // ...
}
```

### Identity Store

This provides an API for:

- authentication: validate credentials
- authorization: retrieve group memberships
- used by HttpAuthenticationMechanism
  - IdentityStoreHandler interface

The identity store can be implemented via either a database or LDAP approach

In the database implementation, passwords are only stored and retrieved and must be stored as a hash. This can be accomplished using the ```PasswordHash``` interface

```java
// Database identity store

@DatabaseIdentityStoreDefinition(
    dataSourceLookup = "java:comp/env/jdbc/securityDS",
    callerQuery = "select password from users where username = ?",
    groupsQuery = "select GROUPNAME from groups where username = ?"
    priority=30,
    hashAlgorithm = PasswordHash.class,
    hashAlgorithmParameters = {
        "Pbkdf2PasswordHash.Iterations=3072",
        "Pbkdf2PasswordHash.Algorithm=PBKDF2WithHmacSHA512",
        "Pbkdf2PasswordHash.SaltSizeBytes=64"
    }
)
@ApplicationScoped
public class AppConfig {
}
```

Under the database approach password hashing must also be specified in the code itself:

```java
@RequestScoped
public class Service implements IService {
    @Inject 
    PasswordHash passwordHash;

    @PostConstruct
    private void init() {
        Map<String,String> hashParams= new HashMap<String,String>();
        hashParams.put("Pbkdf2PasswordHash.Iterations", "3072");
        hashParams.put("Pbkdf2PasswordHash.Algorithm", "PBKDF2WithHmacSHA512");
        hashParams.put("Pbkdf2PasswordHash.SaltSizeBytes", "64");
        passwordHash.initialize(hasParams);
    }

    public String addUser(...) {
        char[] password;
        // ...
        String hashedPassword = passwordHash.generate(password);
        // ...
    }
}
```

In the LDAP approach, passwords are hashed and verified automatically, allowing their storage and retrieval with no additional steps

```java
// LDAP identity store

@LdapIdentityStoreDefinition(
    url = "ldap://localhost:10389",
    callerBaseDn = "ou=caller,dc=stevens,dc=edu",
    groupSearchBase = "ou=group,dc=stevens,dc=edu",
    groupSearchFilter = "(&(member=%s)(objectClass=groupOfNames))"
)
@ApplicationScoped
public class AppConfig {

}
```

### Security Context API

The security context API is a programmatic alternative to declarative security

For example:

- get the principal for this caller in the container: ```Principal getCallerPrincipal();```
- programmatically check if principal is in role: ```boolean isCallerInRole(String role);```
- get all principals of a specific type: ```<T extends Principal> Set<t> getPrincipalsByType(Class<T> type);```

### API-Based Access Control

![defense_in_depth](/notes/assets/enterprise_software_security/defense_in_depth.PNG)

Declarative access checking:

```java
@DeclareRoles("admin", "moderator", "poster")
@Stateless // EJB
public class MessageService {
    @RolesAllowed("admin")
    public void editUser(...) {...}

    @RolesAllowed("poster")
    public long addMessage(...) {...}
}
```

Programmatic access checking:

```java
@Stateless
public class ChatService {
    @Inject
    SecurityContext context;

    public void addMessage(...) {
        if (context.isCallerInRole("poster")) {
            Principal prin = context.getCallerPrincipal();
            // ...
            prin.getName();
            // ...
        }
    }
}
```
