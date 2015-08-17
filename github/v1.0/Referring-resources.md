When you are creating Service Descriptor, there will be scenarios where you want to refer resources at a run time. By below ways you can refer resources from descriptor at run time.

- **Resource {@resource KEY_OF_RESOURCE}**: 
Passing resource as a parameter to request itself.

```xml

             <!-- Android Sample: Passing resource as a parameter -->
    <request>

        <property name="name">DELETE-LIQUOR</property>
        <property name="type">DELETE</property>
        <property name="api">delete-liquor</property>
        <property name="handler">siminov.connect.sample.services.DeleteLiquor</property>
        <property name="mode">ASYNC</property>

        <header-parameters>
                <header-parameter name="Accept">application/xml</header-parameter>
                <header-parameter name="Content-Type">application/xml</header-parameter>
                <header-parameter name="name">{@resource LIQUOR_NAME}</header-parameter>
        </header-parameters>

    </request>

    Liquor liquor = new Liquor();
    liquor.addResource('LIQUOR_NAME', name_of_liquor);
```


```xml
     
                 <!-- iOS Sample: Passing resource as a parameter -->
    <request>

        <property name="name">DELETE-LIQUOR</property>
        <property name="type">DELETE</property>
        <property name="api">delete-liquor</property>
        <property name="handler">DeleteLiquor</property>
        <property name="mode">ASYNC</property>

        <header-parameters>
            <header-parameter name="Accept">application/xml</header-parameter>
            <header-parameter name="Content-Type">application/xml</header-parameter>
            <header-parameter name="name">{@resource LIQUOR_NAME}</header-parameter>
        </header-parameters>

    </request>

    Liquor *liquor = [[Liquor alloc] init];
    [liquor addResource:@"LIQUOR_NAME" value:name_of_liquor];
```


```xml
     
                 <!-- JavaScript Sample: Passing resource as a parameter -->
    <request>

        <property name="name">DELETE-LIQUOR</property>
        <property name="type">DELETE</property>
        <property name="api">delete-liquor</property>
        <property name="handler">DeleteLiquor</property>
        <property name="mode">ASYNC</property>

        <header-parameters>
            <header-parameter name="Accept">application/xml</header-parameter>
            <header-parameter name="Content-Type">application/xml</header-parameter>
            <header-parameter name="name">{@resource LIQUOR_NAME}</header-parameter>
        </header-parameters>

    </request>

    var liquor = new Liquor();
    liquor.addResource('LIQUOR_NAME', name_of_liquor);
```

- **Reference {@refer REFERENCE_RESOURCE_FULL_PATH-PARAMETER_TO_REFERENCE_RESOURCE}**: 
Call refered resource location to get value for property. 

```xml
 
               <!-- Android Sample: Referring resource location -->
    <request>

        <property name="name">DELETE-LIQUOR</property>
        <property name="type">DELETE</property>
        <property name="api">delete-liquor</property>
        <property name="handler">siminov.connect.sample.services.DeleteLiquor</property>
        <property name="mode">ASYNC</property>

        <header-parameters>
            <header-parameter name="Accept">application/xml</header-parameter>
            <header-parameter name="Content-Type">application/xml</header-parameter>
            <header-parameter name="name">{@refer siminov.connect.sample.Refer.getLiquorName}</header-parameter>
        </header-parameters>

    </request>

```


```xml
 
               <!-- iOS Sample: Referring resource location -->
    <request>

        <property name="name">DELETE-LIQUOR</property>
        <property name="type">DELETE</property>
        <property name="api">delete-liquor</property>
        <property name="handler">DeleteLiquor</property>
        <property name="mode">ASYNC</property>

        <header-parameters>
            <header-parameter name="Accept">application/xml</header-parameter>
            <header-parameter name="Content-Type">application/xml</header-parameter>
            <header-parameter name="name">{@refer Refer.getLiquorName}</header-parameter>
        </header-parameters>

    </request>

```


```xml
 
               <!-- JavaScript Sample: Referring resource location -->
    <request>

        <property name="name">DELETE-LIQUOR</property>
        <property name="type">DELETE</property>
        <property name="api">delete-liquor</property>
        <property name="handler">DeleteLiquor</property>
        <property name="mode">ASYNC</property>

        <header-parameters>
            <header-parameter name="Accept">application/xml</header-parameter>
            <header-parameter name="Content-Type">application/xml</header-parameter>
            <header-parameter name="name">{@refer Refer.getLiquorName}</header-parameter>
        </header-parameters>

    </request>

```


- **Self Reference Resource {@self NAME_OF_PROPERTY_CONTAINED_WITHIN}**: 
Get self reference resource from service descriptor itself.

```xml

           <!-- Android Sample: Self referring resource -->
    <request>

        <property name="name">DELETE-LIQUOR</property>
        <property name="type">DELETE</property>
        <property name="api">delete-liquor</property>
        <property name="handler">siminov.connect.sample.services.DeleteLiquor</property>
        <property name="mode">ASYNC</property>

        <header-parameters>
            <header-parameter name="Accept">application/xml</header-parameter>
            <header-parameter name="Content-Type">application/xml</header-parameter>
            <header-parameter name="name">{@self name}</header-parameter>
        </header-parameters>

    </request>

```


```xml

           <!-- iOS Sample: Self referring resource -->
    <request>

        <property name="name">DELETE-LIQUOR</property>
        <property name="type">DELETE</property>
        <property name="api">delete-liquor</property>
        <property name="handler">DeleteLiquor</property>
        <property name="mode">ASYNC</property>

        <header-parameters>
            <header-parameter name="Accept">application/xml</header-parameter>
            <header-parameter name="Content-Type">application/xml</header-parameter>
            <header-parameter name="name">{@self name}</header-parameter>
        </header-parameters>

    </request>

```



```xml

           <!-- JavaScript Sample: Self referring resource -->
    <request>

        <property name="name">DELETE-LIQUOR</property>
        <property name="type">DELETE</property>
        <property name="api">delete-liquor</property>
        <property name="handler">DeleteLiquor</property>
        <property name="mode">ASYNC</property>

        <header-parameters>
            <header-parameter name="Accept">application/xml</header-parameter>
            <header-parameter name="Content-Type">application/xml</header-parameter>
            <header-parameter name="name">{@self name}</header-parameter>
        </header-parameters>

    </request>

```
