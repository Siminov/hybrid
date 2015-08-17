It exposes APIs to Get and Set connection exception information.

#### Android Sample: Connection Exception

```java

    public class ConnectionException extends Exception {

        public String getClassName() { }
        public void setClassName(String className) { }

        public String getMethodName() { }
        public void setMethodName(String methodName) { }

        public String getMessage() { }
        public void setMessage(String message) { }

    }

```

#### iOS Sample: Connection Exception 


```java

    @interface SIKConnectionException : SICSiminovException

    - (id)initWithClassName:(NSString * const)classname methodName:(NSString * const)methodname message:(NSString * const)exceptionmessage;

    @end

```

#### Windows Sample: Connection Exception

```c#

    public class ConnectionException : Exception {

        public String GetClassName() { }
        public void SetClassName(String className) { }

        public String GetMethodName() { }
        public void SetMethodName(String methodName) { }

        public String GetMessage() { }
        public void SetMessage(String message) { }

    }

```

#### JavaScript Sample: Connection Exception

```javascript

    function ConnectionException() {

        this.getClassName = function() { }
        this.setClassName = function(className) { }

        this.getMethodName = function() { }
        this.setMethodName = function(methodName) { }

        this.getMessage = function() { }
        this.setMessage = function(message) { }

    }

```