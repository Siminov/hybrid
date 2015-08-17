You can start your service by creating its instance, and calling invoke() API. You can also pass additional resources to your service by using below Resource APIs:

- **Android:addResource(key_of_your_resource, your_resource) | iOS:addResource:key_of_your_resource resource:your_resource) | Windows:addResource(key_of_your_resource, your_resource) | JavaScript:addResource(key_of_your_resource, your_resource)**: 
Using this API you can add additional resources to your service.

- **Android:containResource(key_of_your_resource) | iOS:containResource:key_of_your_resource | Windows:containResource(key_of_your_resource) | JavaScript:containResource(key_of_your_resource))**: Using this API you can check whether resource exists with service or not.

#### Android Sample: Adding Resource

```java

    LiquorBrand liquorBrand = new LiquorBrand();
    liquorBrand.addResource(key_of_your_resource, your_resource);
    liquorBrand.invoke();

```

#### iOS Sample: Adding Resource

```objective-c

    LiquorBrand *liquorBrand = [[LiquorBrand alloc] init];
    [liquorBrand addResource:key_of_your_resource resource:your_resource];
    [liquorBrand invoke];

```

#### Windows Sample: Adding Resource

```c#

    LiquorBrand liquorBrand = new LiquorBrand();
    liquorBrand.AddResource(key_of_your_resource, your_resource);
    liquorBrand.Invoke();

```

#### JavaScript Sample: Adding Resource

```javascript

    var liquorBrand = new LiquorBrand();
    liquorBrand.addResource(key_of_your_resource, your_resource);
    liquorBrand.invoke();

```