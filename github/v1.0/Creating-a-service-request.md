Basically a service is a group of Requests, each request represent a HTTP method or a action to be performed on the identified resource.
 
**HTTP Method**: HTTP defines methods to indicate the desired action to be performed on the identified resource. Below are the methods defined in HTTP/1.1 specification:

- **GET**: It is used for retrieving data. 

```xml

    <service-descriptor>
        <requests>
            <request>
                <property name="type">GET</property>
            </request>
        </requests>
    </service-descriptor>

```

- **HEAD**: It is used to retrieve meta-information written in response headers, without having to transport the entire content.

```xml

    <service-descriptor>
        <requests>
            <request>
                <property name="type">HEAD</property>
            </request>
        </requests>
    </service-descriptor>

```

- **POST**: It is used to request the server accept the entity enclosed in the request as a new subordinate of the web resource identified by the URI.

```xml

    <service-descriptor>
        <requests>
            <request>
                <property name="type">POST</property>
            </request>
        </requests>
    </service-descriptor>

```

- **PUT**: It is used to request the enclosed entity be stored under the supplied URI.

```xml

    <service-descriptor>
        <requests>
            <request>
                <property name="type">PUT</property>
            </request>
        </requests>
    </service-descriptor>

```

- **DELETE**: Delete the specified resource.

```xml

    <service-descriptor>
        <requests>
            <request>
                <property name="type">DELETE</property>
            </request>
        </requests>
    </service-descriptor>

```

- **TRACE**: Echoes back the received request so that a client can see what (if any) changes or additions have been made by intermediate servers.

```xml
 
    <service-descriptor>
        <requests>
            <request>
                <property name="type">TRACE</property>
            </request>
        </requests>
    </service-descriptor>

```

- **OPTIONS**: Returns the HTTP methods that the server supports for the specified URL.

```xml

    <service-descriptor>
        <requests>
            <request>
                <property name="type">OPTIONS</property>
            </request>
        </requests>
    </service-descriptor>

```

- **CONNECT**: Converts the request connection to a transparent TCP/IP tunnel, usually to facilitate SSL-encrypted communication (HTTPS) through an unencrypted HTTP proxy.

```xml

    <service-descriptor>
        <requests>
            <request>
                <property name="type">CONNECT</property>
            </request>
        </requests>
    </service-descriptor>

```


- **Patch**: Is used to apply partial modifications to a resource.

```xml

    <service-descriptor>
        <requests>
            <request>
                <property name="type">PATCH</property>
            </request>
        </requests>
    </service-descriptor>

```
