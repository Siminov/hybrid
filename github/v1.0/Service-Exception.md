It exposes APIs to Get and Set service exception information

#### Android API: Service Exception

```java

    public class ServiceException extends SiminovException {

        public String getClassName() { }
        public void setClassName(String className) { }

        public String getMethodName() { }
        public void setMethodName(String methodName) { }

        public String getMessage() { }
        public void setMessage(String message) { }

    }

```

#### iOS API: Service Exception

```objective-c

    @interface SIKServiceException : SICSiminovException

    - (id)initWithClassName:(NSString * const)classname methodName:(NSString * const)methodname message:(NSString * const)exceptionmessage;

    @end

```

#### Windows API: Service Exception 

```c#

    public class ServiceException : SiminovException {

        public String GetClassName() { }
        public void SetClassName(String className) { }

        public String GetMethodName() { }
        public void SetMethodName(String methodName) { }

        public String GetMessage() { }
        public void SetMessage(String message) { }

    }

```

#### JavaScript API: Service Exception 

```javascript

    function ServiceException() {

        this.getClassName = function() { }
        this.setClassName = function(className) { }

        this.getMethodName = function() { }
        this.setMethodName = function(methodName) { }

        this.getMessage = function() { }
        this.setMessage = function(message) { }

    }

```