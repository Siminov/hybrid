Service is a client-side communication component that process and handles any web service request. It performs long running operations in the background. A Service is a group of Requests which deals on one particular web service.

An application usually consists of multiple services that are loosely bound to each other. Typically, a service in an application is specified to perform a given task for a specific web service. Each service can start another service in order to perform different action. 

There are several callback methods that an service might receive, due to a change in its state-whether the framework is creating it, stopping it, resuming it, or destroying it-and each callback provides you the opportunity to perform specific work that's appropriate to that state change. For instance, when stopped, your service should release any large objects, such as network or database connections. 

When the service resumes, you can re-acquire the necessary resources and resume services that were interrupted. These state transitions are all part of the service lifecycle.

Lets discuss the basics of how to build and use a service, including a complete discussion of how the service lifecycle works, so you can properly manage the transition between various service states.

## Creating a Service
To create a service, you must create a subclass of Service (or an existing subclass of it). In your subclass, you need to implement callback methods that the framework calls when the service transitions between various states of its lifecycle, such as when the service is being started, stopped, resumed, or destroyed. 

The two most important callback methods are:

###### 1. On Start - [Android:onStart | iOS:onStart | Windows:onStart | JavaScript:onStart]: 
This is the first method to be called when a service is created. 

- Initializing variables
- Binding static data to service
- Binding related screen to service

Once OnStart has finished, Connect will call OnServiceQueue if Service is in ASYNC mode else OnServiceRequestInvoke.

###### 2. On Request Finish - [Android:onRequestFinish(ConnectionResponse) | iOS:onRequestFinish:ConnectionResponse | Windows:OnRequestFinish(ConnectionResponse) | JavaScript:onRequestFinish(connectionResponse)]: 

This method is called after Web Service API is executed.

There are several other lifecycle callback methods that you should use in order to handle service transition change. All of the lifecycle callback methods are discussed later, in the section about Managing the Service Lifecycle.
