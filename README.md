# VIVO-PROXY
VIVO-PROXY is a RESTful api and SDK client that allows data to be ingested into VIVO from applications using JSON data models

Work in progress


## Installation



To run the server, please execute the following:

Run a VIVO instance at `http://localhost:8080/vivo`

need Java JDK-11

```
cd bundles/ca.uqam.tool.vivoproxy
mvn clean package jetty:run
```

You can then view the swagger listing here:

```
http://localhost:9090/vivo-proxy.yaml
```
and swagger console here:

```
http://localhost:9090/
```

## Other instructions and videos
VIVO Conference 2021: VIVO-PROXY POC: at [https://doi.org/10.13140/RG.2.2.24103.06569 ](https://doi.org/10.13140/RG.2.2.24103.06569)




