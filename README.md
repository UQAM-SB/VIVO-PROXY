# VIVO-PROXY
VIVO-PROXY is a RESTful api and SDK client that allows data to be ingested into VIVO from applications using JSON data models

**Important note:** ***This work is in progress!***
### VIVO-Proxy methods for this release

You can see from the web [vivo-proxy.yaml](https://editor.swagger.io/?url=https://raw.githubusercontent.com/vivo-community/VIVO-PROXY/main/bundles/ca.uqam.tool.vivo-proxy/api/vivo-proxy.yaml)

## Installation

### Prerequisites

- VIVO 1.12.0 Instance installed and running
- Java JDK-11
- Maven 3.6.3

### To run VIVO-PROXY, 
please execute the following:

#### 1) Run a VIVO instance at 
`http://localhost:8080/vivo`

#### 2) Configure VIVO-LOGIN for VIVO-PROXY by performing the following commands

```
cd bundles/ca.uqam.tool.vivo-proxy
cp src-overlay/main/java/ca/uqam/tool/util/credential/YOUR_LOGIN.java src-overlay/main/java/ca/uqam/tool/util/credential/LOGIN.java
vi src-overlay/main/java/ca/uqam/tool/util/credential/LOGIN.java`

Replace     
    Class name YOUR_LOGIN by LOGIN
    USER_NAME = "YOUR_VIVO_LOGIN";
    PASSWD = "YOUR_VIVO_LOGIN_PASSWD"; 
    by your VIVO_root credential (see :
            rootUser.emailAddress = 
            rootUser.passwordChangeRequired = false
            rootUser.password = 
      
    properties in the $VIVO_HOME/config/runtimes.properties

```

####  3) compile and run VIVO-PROXY

```
mvn clean package jetty:run
```

##### You can then view the swagger listing here:

`http://localhost:9090/vivo-proxy.yaml` on localHost


##### and swagger console here:

`http://localhost:9090/`

## Other instructions and videos

### PowerPoint Presentation

- **VIVO Conference 2021**: VIVO-PROXY Proof Of Concept: at [https://doi.org/10.13140/RG.2.2.24103.06569 ](https://doi.org/10.13140/RG.2.2.24103.06569)

### List of presentation videos

- [VIVO-PROXY Scenario of 3 actions](https://youtu.be/alOBBHnIx14)
- [Swagger Model Driven Development Demonstration ](https://youtu.be/jyz0WQuj9UU)

## Try in out! : Use cases of VIVO-PROXY that you can try
You will find here a VIVO-PROXY usage scenario containing several use cases 
[Client use cases readme](https://github.com/vivo-community/VIVO-PROXY/tree/issues-3/bundles/ca.uqam.tool.vivo-proxy.client#readme)





