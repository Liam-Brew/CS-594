package edu.stevens.cs594.chat.webapp;

import static javax.faces.annotation.FacesConfig.Version.JSF_2_3;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.annotation.FacesConfig;
import javax.security.enterprise.authentication.mechanism.http.CustomFormAuthenticationMechanismDefinition;
import javax.security.enterprise.authentication.mechanism.http.LoginToContinue;
import javax.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;
import javax.security.enterprise.identitystore.PasswordHash;

@FacesConfig(version = JSF_2_3)

@CustomFormAuthenticationMechanismDefinition(
		loginToContinue = @LoginToContinue(
				loginPage = "/login.xhtml",
				errorPage = "/loginError.xhtml"))
@DatabaseIdentityStoreDefinition(
		dataSourceLookup = "jdbc/cs594",
		callerQuery = "select PASSWORD from USERS where USERNAME = ?",
		groupsQuery = "select ROLENAME from USERS_ROLES where USERNAME = ?",
		priority=30)		
public class AppConfig {

}
