# VIVO-PROXY
VIVO-PROXY is a RESTful api and SDK client that allows data to be ingested into VIVO from applications using JSON data models

Work in progress


## Installation

### Prerequisites

- VIVO 1.12.0 Instance installed and running
- Java JDK-11
- Maven 3.6.3

### To run VIVO-PROXY, 
please execute the following:

#### Run a VIVO instance at 
`http://localhost:8080/vivo`

#### Configure VIVO-LOGIN for VIVO-PROXY by performing the following commands

```
cd bundles/ca.uqam.tool.vivoproxy
cp src-overlay/main/java/ca/uqam/tool/util/credential/YOUR_LOGIN.java src-overlay/main/java/ca/uqam/tool/util/credential/LOGIN.java
vi src-overlay/main/java/ca/uqam/tool/util/credential/LOGIN.java`

Replace     
    USER_NAME = "YOUR-VIVO_LOGIN";
    PASSWD = "YOUR-VIVO_LOGIN-PASSWD"; 
    by your VIVO_root credential (see :
            rootUser.emailAddress = 
            rootUser.passwordChangeRequired = false
            rootUser.password = 
      
    properties in the $VIVO_HOME/config/runtimes.properties

```

####  compile and run VIVO-PROXY

```
mvn clean package jetty:run
```

##### You can then view the swagger listing here:

`http://localhost:9090/vivo-proxy.yaml`

##### and swagger console here:

`http://localhost:9090/`

## Other instructions and videos
### PowerPoint Presentation
VIVO Conference 2021: VIVO-PROXY POC: at [https://doi.org/10.13140/RG.2.2.24103.06569 ](https://doi.org/10.13140/RG.2.2.24103.06569)
### List of presentation videos
[VIVO-PROXY Scenario of 3 actions](https://youtu.be/alOBBHnIx14)
[Swagger Model Driven Development Demonstration ](https://youtu.be/jyz0WQuj9UU)





