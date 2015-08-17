Siminov Framework provides few event notifiers which gets triggered based on particular action. Application have to provide implementation for these event notifiers and register them with Siminov.

> **Note**
>
> - **Event Handler:** It can be define in both Native and Web.
>
> - **Native Event Handler:** If you want to handle Event in Native then define Event Handler using Native language. Specify full model class path and name in ApplicationDescriptor.si.xml.
>
> - **Web Event Handler:** If you want to handle Event in Web then define Event Handler using JavaScript. Specify only JavaScript function name in ApplicationDescriptor.si.xml.
>
> - **Both (Native/Web) Event Handler:** If you want to handle Event in both Native and Web then define Event Handler in both Native and JavaScript. Specify full model class path and name in ApplicationDescriptor.si.xml, no need to define for JavaScript because Siminov will automatically assume same name for it.


**ISync** is a event handler which automatically gets triggered when action happen with sync. Application have to provide implementation for _ISync_ event notifier and register them with siminov.


**Java API: ISyncEvents**

```java

    public interface ISyncEvents {

        public void onStart(ISyncRequest syncRequest);

        public void onQueue(ISyncRequest syncRequest);

        public void onFinish(ISyncRequest syncRequest);

        public void onTerminate(ISyncRequest syncRequest);
    }

```

**iOS API: Sync Event**

```objective-c

    @implementation DatabaseEventHandler

        - (void)onStart:(SIKISyncRequest * const)syncRequest;

        - (void)onQueue:(SIKISyncRequest * const)syncRequest;

        - (void)onFinish:(SIKISyncRequest * const)syncRequest;

        - (void)onTerminate:(SIKISyncRequest * const)syncRequest;
    
    @end

```

**Windows API: ISyncEvents**

```java

    public interface ISyncEvents {

        public void OnStart(ISyncRequest syncRequest);

        public void OnQueue(ISyncRequest syncRequest);

        public void OnFinish(ISyncRequest syncRequest);

        public void OnTerminate(ISyncRequest syncRequest);
    }

```


**JavaScript API: ISyncEvents**

```java

    function ISyncEvents() {

        this.onStart = function(syncRequest) {};

        this.onQueue = function(syncRequest) {};

        this.onFinish = function(syncRequest) {};

        this.onTerminate = function(syncRequest) {};
    }

```



###### On Start - [Android:onStart | iOS:onStart | Windows:OnStart | JavaScript:onStart]:
 
This method is called then a Sync is started. In this you can initialize resources related to Sync. Once OnStart has finished, Connect will call OnQueue.

###### On Queue - [Android:onQueue | iOS:onQueue | Windows:OnQueue | JavaScript:onQueue]:
This method is called then the Sync request is added to the Queue.


###### On Finish - [Android:onFinish | iOS:onFinish | Windows:OnFinish | JavaScript:onFinish]:
This method is called then Sync request completes its all synchronization data with web service.


###### On Terminate - [Android:onTerminate | iOS:onTerminate | Windows:OnTerminate | JavaScript:onTerminate]:
This method is called if there is any error/exception while synchronizing data with web service.
