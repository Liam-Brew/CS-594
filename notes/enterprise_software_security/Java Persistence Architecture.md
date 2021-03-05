# Java Persistence Architecture

## Table of Contents

- [Java Persistence Architecture](#java-persistence-architecture)
  - [Table of Contents](#table-of-contents)
  - [Layered Architecture](#layered-architecture)
  - [Domain Layer](#domain-layer)
    - [Entities](#entities)
    - [Repositories](#repositories)
  - [Application Layer](#application-layer)
    - [Dependency Injection](#dependency-injection)
    - [Lifecycle Management](#lifecycle-management)
    - [Backing Bean/Controller](#backing-beancontroller)
    - [Data Collections](#data-collections)

## Layered Architecture

Enterprise software applications are basically client-server relationships. They are typically divided into three tiers:

1. client/presentation layer
2. application/server layer
3. resource manager/database

This follows the layered architecture methodology

![layered_design](/notes/assets/enterprise_software_security/layered_design.PNG)

Clean-slate enterprise applications typically follow the top-down design, while legacy systems use the bottom-up design

## Domain Layer

**Java Persistance Architecture**: API for persisting objects into a database

### Entities

In JPA, the ```@Entity``` annotation specifies that objects of this form are in-memory representatives of database records. They are intended to be loaded from a relational database, and if any changes are made these are written back to the database as well

In Java, a **bean** is a class that has properties that are defined by getter and setter methods. This allows entities to update their fields

```java
@Entity
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final int USER_NAME_LENGTH = 20;
    private static final int HASHED_PASSWORD_LENGTH = 1600;

    @Id
    @Column(length = USER_NAME_LENGTH, nullable = false)
    private String username;

    @Column(length = HASHED_PASSWORD_LENGTH, nullable = false)
    private String password;
    private String otpSecret;
    private String name;
}
```

### Repositories

The **repository** condenses database-specific details such as SQL code so that they are not proliferated throughout the entire codebase. This model is shown below, followed by sample code:

![repository_dao_pattern](/notes/assets/enterprise_software_security/repository_dao_pattern.PNG)

```java
public class UserDAO {
    private EntityManager em;
    
    public UserDAO(EntityManager em) {
        this.em = em;
    }

    @Override
    public User getUser(String username) throws UserExn {
        User u = em.find(User.class, username);
        if (u != null) {
            return u;
        }
        // ...
    }
}
```

## Application Layer

### Dependency Injection

**Dependency injection** injects the resources that a service needs to execute. This is based on annotations

Some examples are:

```java
// Injecting a repository object
@PersistenceContext(unitName = "PatientPU")
EntityManager em;

// Injecting a message queue
@Resource(name = "jms/Requests")
Queue requests;

// Injecting a service object
@Inject
IService server;

// Defining a service class
@RequestScoped
public class Service implements IService {
    // ...
}

// Injecting an instance based on metadata
@Inject IService service;
```

### Lifecycle Management

Managed beans have a lifecycle and are managed by the container

The ```@PostConstruct``` annotation indicates that the method is meant to be called after dependency injection

```java
@RequestScoped
public class Service implements IService {
    private UserFactory userFactory;
    private UserDAO userDAO;

    public Service() {
        userFactory = new UserFactory();
    }

    @PersistenceContext(unitName = "...")
    EntityManager em;

    @PostConstruct
    public void init() {
        user DAO = new UserDAO(em);
    }

    public void editUser(UserDto dto) {
        User user = userDAO.getUser(dto.getUsername());
        user.setName(dto.getName());
        user.setPassword(...);
        userDAO.sync();
    }
}
```

**Data transfer object (DTO)**: used to pass entity objects between the front-end and the business layer

There are several ways to manage service instancing:

- ```@RequestScoped```: every time a client wants to use this service, they get a new instance of the service (per call instance)
- ```@ApplicationScoped```: one instance of the service is shared by all clients (single instance)
- ```@SessionScoped```: one instance per session per client (per session)

### Backing Bean/Controller

The **controller** is where the state of the form before it is submitted back to the user

The state of the interface is based on the state of the backing bean

The ```@Named``` annotation identifies the name of the backing bean. This is optional; if no annotation is present the name is based on the name of the class

In addition to the previous methods of managing instancing, there exists two additional bean scopes:

- ```@ViewScoped```: for post-back forms. Useful for returning forms with feedback as it retains inputted content
- ```@FlowScoped```: for interactions involving several pages. Useful for several-paged forms wherein data is transferred from page to page. Under this, pages share state

```java
// Backing Bean/Controller
@Named("loginBacking")
@ViewScoped
public class LoginBean extends BaseBean {

    private String username;

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public String login() {
        securityContext.authenticate(...);
    }
}
```

### Data Collections

The below message service both displays database contents in the application layer (```getMessages()```) as well as adds message entities to the database (```addMessage()```)

```java
@RequestScoped
public class MessageService {
    public List<MessageDto> getMessages() {
        List<Message> messages = messageDAO.getMessages();
        List<MessageDto> dtos = new ArrayList<MessageDto>();
        
        for (Message user : messages) {
            dtos.add(messageDtoFactory.createMessageDto(user));
        }

        return dtos;
    }

    public long addMessage(MessageDto dto) {
        Message message = messageFactory.createMessage(dto.getSender(), dto.getText(), dto.getTimestamp());

        return messageDAO.addMessage(message);
    }
}
```