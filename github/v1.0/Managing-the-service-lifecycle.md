Services are a fundamental building block of Connect Framework and they can exist in a number of different states. The service lifecycle begins with instantiation and ends with destruction, and includes many states in between. When a service changes state, the appropriate lifecycle event method is called, notifing the service of the impending state change and allowing it to execute code in order to adapt to that change. 

Within the lifecycle callback methods, you can declare how your service behaves when their is any event on the service. 

###### Overview
Services are an unusual programming concept specific to Siminov Connect. The service lifecycle is implemented as a collection of methods the application calls throughout the lifecycle of an service. These methods allow developers to implement the functionality that is necessary to satisfy the state and resource management requirements of their applications.

It is extremely important for the application developer to analyse the requirements of each service to determine which methods exposed by the service lifecycle need to be implemented. Failure to do this can result in application instability, inconsistent data.

###### Lifecycle
The Service lifecycle comprises a collection of methods exposed within the Service class that provide the developer with a resource management framework. This framework allows developers to meet the unique state management requirements of each service within an application and properly handle resource management.

**Service States**: Service states can be broken into 4 main group as follows:

- **Active or Running**: Services are considered action or running if they are executing or processing.
- **Paused**: When the device goes to sleep, or there is no network communication, the service is considered paused. Paused services are still alive, that is, they maintain all state and member information, and remain attached to the application.
- **Stopped**: Service are considered stopped when they finish there process or are terminated.
- **Resume**: It is possible for a service that is anywhere from paused to stopped in the lifecycle to be removed from memory by application.

![Service Lifecycle] (https://raw.githubusercontent.com/Siminov/android-connect/doc-resources/github-wiki-resources/service_lifecycle.png)


**Lifecycle Methods**: As a developer, you can handle state changes by overriding these methods within a service. It's important to note, however, that all lifecycle methods may be called on the UI thread/Non UI thread and may block the OS from performing the next piece of UI work, such as hiding the current screen, displaying a new screen, etc. As such, code in these methods should be as brief as possible to make an application feel well performing. Any long-running tasks should be executed in ASYNC Service mode.


- **On Start - [Android:onStart | iOS:onStart | Windows:OnStart | JavaScript:onStart]**: 

This is the first method to be called when a service is created.

OnStart is always overridden to perform any startup initialization that may be required by a Service such as:

1. Initializing variables
2. Binding static data to service
3. Binding related screen to service

Once OnStart has finished, Connect will call OnServiceQueue if Service is in ASYNC mode else OnServiceRequestInvoke.

- **On Queue - [Android:onQueue | iOS:onQueue | Windows:OnQueue | JavaScript:onQueue]**: This method is called when the service is put in the queue for the execution

- **On Queue - [Android:onPause | iOS:onPause | Windows:OnPause | JavaScript:onPause]**: This method is called when there is no network. Services should override this method if they need to:

1. Commit unsaved changes to persistent data
2. Destroy or clean up other objects consuming resources
3. Display any relevant alerts or dialogs


- **On Resume - [Android:onResume | iOS:onResume | Windows:OnResume | JavaScript:onResume]**: The Connect calls this method when the Service is ready to start executing. Services should override this method to perform tasks such as:

1. Display any relevant alerts or dialogs
2. Wire up external event handlers
3. Listening for GPS updates


- **On Finish - [Android:onFinish | iOS:onFinish | Windows:OnFinish | JavaScript:onFinish]**: This is the final method that is called on a Service instance before it's destroyed and completely removed from memory. There will be no lifecycle methods called after the Service has been destroyed.

- **On Request Invoke - [Android:onRequestInvoke(ConnectionRequest) | iOS:onRequestInvoke:ConnectionRequest | Windows:onRequestInvoke(ConnectionRequest) | JavaScript:onRequestInvoke(ConnectionRequest)]**: This method is called before Service calls Web Service API.


- **On Request Finish - [Android:onRequestFinish(ConnectionResponse) | iOS:onRequestFinish:ConnectionResponse | Windows:OnRequestFinish(ConnectionResponse) | JavaScript:onRequestFinish(ConnectionResponse)]**: This method is called after Web Service API is executed.


- **On Terminate - [Android:onTerminate(SiminovException) | iOS:onTerminate:SiminovException | Windows:OnTerminate(SiminovException) | JavaScript:OnTerminate(SiminovException)]**: This method is called when there is any exception while executing the service. Once this is called the service will be terminated and release from the memory.