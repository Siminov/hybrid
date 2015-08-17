It allows app to automatically checks for updates in the background, using battery and your data plan. 

You can customise how often it does these checks by adjusting the Refresh Interval. If you don't framework to update regularly, you should set this value to zero to conserve both your battery and your data use.

> **Note**
>
> All sync request will execute in a different thread. 

## Creating Sync Request
To create a sync request, you must create an instance of Sync Request. Once you create an instance you have to provide it to Sync Handler.


**Android Sample: Sync Request**

```java

    ISyncRequest syncRequest = new SyncRequest();
    syncRequest.setName(name_of_your_sync_request);
    syncRequest.addResource(key_of_your_resource, resource);

    SyncHandler syncHandler = SyncHandler.getInstance();
    syncHandler.handle(syncRequest);

```

**iOS Sample: Sync Request**

```objective-c

    ISyncRequest syncRequest = [[SIKSyncRequest alloc] init];
    [syncRequest setName:name_of_your_sync_request];
    [syncRequest addResource:key_of_your_resource value:resource];

    SIKSyncHandler *syncHandler = [SIKSyncHandler getInstance];
    [syncHandler handle:syncRequest];

```

**Windows Sample: Sync Request**

```c#

    ISyncRequest syncRequest = new SyncRequest();
    syncRequest.SetName(name_of_your_sync_request);
    syncRequest.AddResource(key_of_your_resource, resource);

    SyncHandler syncHandler = SyncHandler.GetInstance();
    syncHandler.Handle(syncRequest);

```


**JavaScript Sample: Sync Request**

```javascript

    var syncRequest = new SyncRequest();
    syncRequest.setName(name_of_your_sync_request);
    syncRequest.addResource(key_of_your_resource, resource);

    var syncHandler = SyncHandler.GetInstance();
    syncHandler.handle(syncRequest);

```