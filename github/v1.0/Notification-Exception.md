It exposes APIs to Get and Set notification exception message information.


#### Android Sample: Notification Exception

```java

    public class NotificationException extends Exception {

        public String getClassName() { }
        public void setClassName(String className) { }

        public String getMethodName() { }
        public void setMethodName(String methodName) { }

        public String getMessage() { }
        public void setMessage(String message) { }

    }

```

#### iOS Sample: Notification Exception

```objective-c
 
    @interface SIKNotificationException : SICSiminovException

    - (id)initWithClassName:(NSString * const)classname methodName:(NSString * const)methodname message:(NSString * const)exceptionmessage;

    @end

```

#### Windows Sample: Notification Exception

```c#

    public class NotificationException : Exception {

        public String GetClassName() { }
        public void SetClassName(String className) { }

        public String GetMethodName() { }
        public void SetMethodName(String methodName) { }

        public String GetMessage() { }
        public void SetMessage(String message) { }

    }

```

#### JavaScript Sample: Notification Exception

```javascript

    function NotificationException() {

        this.GetClassName = function() { }
        this.SetClassName = function(className) { }

        this.GetMethodName = function() { }
        this.SetMethodName = function(methodName) { }

        this.GetMessage = function() { }
        this.SetMessage = function(message) { }

    }

```