Siminov Framework provides few event notifiers which gets triggered based on particular action. Application have to provide implementation for these event notifiers and register them with Siminov.

> **Note**
> - **Event Handler:** It can be define in both Native and Web.
>
> - **Native Event Handler:** If you want to handle Event in Native then define Event Handler using Native language. Specify full model class path and name in ApplicationDescriptor.si.xml.
>
> - **Web Event Handler:** If you want to handle Event in Web then define Event Handler using JavaScript. Specify only JavaScript Function name in ApplicationDescriptor.si.xml.
>
> - **Both (Native/Web) Event Handler:** If you want to handle Event in both Native and Web then define Event Handler in both Native and JavaScript. Specify full model class path and name in ApplicationDescriptor.si.xml, no need to define for JavaScript because Siminov will automatically assume same name for it.

***
**INotification** It contain events associated with notification.

**Android API: Notification Event**

```java

    public interface INotificationEvents {

        public void onRegistration(IRegistration registration);

        public void onUnregistration(IRegistration registration);

        public void onNotification(IMessage message);
	
        public void onError(NotificationException notificationException);
    }

```

**iOS API: Notification Event**

```objective-c

    @interface SIKINotificationEvents

    - (void)onRegistration:(id<SIKIRegistration>)registration;

    - (void)onUnregistration:(id<SIKIRegistration>)registration;

    - (void)onNotification:(id<SIKIMessage>)message;
	
    - (void)onError:(SIKNotificationException *)notificationException;

    @end

```

**Windows API: Sync Event**

```c#

    public interface ISyncEvents {

        void OnStart(ISyncRequest syncRequest);

        void OnQueue(ISyncRequest syncRequest);

        void OnFinish(ISyncRequest syncRequest);

        void OnTerminate(ISyncRequest syncRequest);
    }
```



**JavaScript API: Notification Event**

```java

    function INotificationEvents() {

        this.onRegistration = function(registration) {};

        this.onUnregistration = function(registration) {};

        this.onNotification = function(message) {};
	
        this.onError = function(notificationException) {};
    }

```


###### On Registration - [Android:onRegistration | iOS:onRegistration | Windows:OnRegistration | JavaScript:onRegistration]:
This is the first method to be called when application is successfully registered with push notification platform service.

###### On Unregistration - [Android:onUnregistration | iOS:onUnregistration | Windows:OnUnregistration | JavaScript:onUnregistration]:
This method is called when application get unregistered on the push notification platform.

###### On Notification - [Android:onNotification | iOS:onNotification | Windows:OnNotification | JavaScript:onNotification]:
This method is called when application gets any message/notification from server.

###### On Error - [Android:onError | iOS:onError | Windows:onError | JavaScript:onError]:
This method is called if there is any error in process of registration/notification.
