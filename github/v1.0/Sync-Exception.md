It exposes APIs to Get and Set sync exception information.

#### Android Sample: Sync Exception

```java

    public class SyncException extends Exception {

        public String getClassName() { }
        public void setClassName(String className) { }

        public String getMethodName() { }
        public void setMethodName(String methodName) { }

        public String getMessage() { }
        public void setMessage(String message) { }

    }

```

#### iOS Sample: Sync Exception

```objective-c

    @interface SIKSyncException : SICSiminovException

    - (id)initWithClassName:(NSString * const)classname methodName:(NSString * const)methodname message:(NSString * const)exceptionmessage;

    @end

```

#### Windows Sample: Sync Exception

```c#

    public class SyncException : System.Exception {

        public String GetClassName() { }
        public void SetClassName(String className) { }

        public String GetMethodName() { }
        public void SetMethodName(String methodName) { }

        public String GetMessage() { }
        public void SetMessage(String message) { }

    }

```

#### JavaScript Sample: Sync Exception

```javascript

    function SyncException() {

        this.getClassName = function() { }
        this.setClassName = function(className) { }

        this.getMethodName = function() { }
        this.setMethodName = function(methodName) { }

        this.getMessage = function() { }
        this.setMessage = function(message) { }

    }

```