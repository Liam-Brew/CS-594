# REST

## Table of Contents

- [REST](#rest)
  - [Table of Contents](#table-of-contents)
  - [Representational State Transfer (REST)](#representational-state-transfer-rest)
  - [HTTP](#http)
  - [JAX-RS API for RESTful Web Services](#jax-rs-api-for-restful-web-services)

## Representational State Transfer (REST)

REST is commonly defined as the software architecture for the web. It features the connection of different nodes to facilitate data transfer and storage

A **state machine** is a machine that is limited to a certain finite number of states. Based on the current state and a given input the machine performs state transitions and produces outputs. In REST, states should be kept on the client, as **stateless web servers are impossible on the Internet**

![rest](/notes/assets/web_services/rest.PNG)

Resources can be both data, such as a file, or services, such as a search action. REST uses web protocols to allow for clients to interact with resources hosted on a remote site. Due to abstraction, the modified resource itself is not sent back to the client, with a representation being used instead to serve as a unit of data exchange. The example below details a typical e-commerce interaction using REST:

![ecommerce](/notes/assets/web_services/ecommerce.PNG)

REST is contrasted to SOAP/WSDL in the following ways:

| SOAP/WSDL                    | REST                             |
| ---------------------------- | -------------------------------- |
| Service (operation) oriented | resource oriented                |
| one endpoint URL             | URL for each individual resource |
| application-defined verbs    | fixed set of HTTP verbs          |

REST verbs, used to define resource interactions, are:

- **retrieve**: ```HTTP GET```
- **create**:
  - ```HTTP PUT``` for new URI
  - ```HTTP POST``` for existing URI (server decides result URI)
- **modify**: ``HTTP PUT``, ```HTTP PATCH``` to existing URI
- **delete**: ```HTTP DELETE```
- **retrieve metadata only**: ```HTTP HEAD```
- **check which methods are supported**: ```HTTP OPTIONS```

## HTTP

REST primarily uses HTTP to manage requests to and from servers

HTTP request headers contain metadata regarding the request itself. Some examples of headers are:

- **accept**: used for content negotiation
  - Content-Type: response header
- **authorization**: app-defined auth info
  - WWW-Authenticate: response header with status code of 401 ("Unauthorized")
- **cookie (non-standard)**: associates client state with web service requests
  - Save-Cookie: to save cookie on client
- **last-modified**: time of last modification
  - If-Last-Modified: request header for caching
- **etag**: hash of metadata
  - If-None-Match: request header for caching
- **cache-control**: how long to cache
- **upgrade**: upgrade protocol, e.g. http to https
- **location**: URI for newly created resource, redirection, etc.

HTTP response codes reveal the status of a request. Some examples are:

- **1xx**: for negotiation with web server
- **2xx**: to signal success
- **3xx**: redirect clients
- **4xx**: client errors

## JAX-RS API for RESTful Web Services

In JAX-RS, RESTful web services are implemented as methods of objects

Some annotations used are:

- ```@Path``` for class: base context root
- ```@Path``` for methods: extensions for resources
- ```@Get```, ```@Post```,```@Put```, etc. to specify what HTTP operations methods correspond to
- ```@Produces```, ```@Consumes```: specifies type of data used
- ```@QueryParam```: param from query string

An example follows:

```java
@Path("/HelloService")
public class HelloResource {
    @GET
    @Produces("text/plain")
    public String sayHello(@QueryParam("name") String name) {
        return "Hello, " + name;
    }
}
```
