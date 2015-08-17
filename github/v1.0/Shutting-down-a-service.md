You can shut down a service by calling its terminate method.

> **Note**
>
> In most cases, you should not explicitly finish a service using these methods. As discussed in the following section about the service lifecycle, the framework manages the life of a service for you, so you do not need to terminate your own service. Calling these methods could adversely affect the expected user experience and should only be used when you absolutely do not want the user to return to this instance of the service.
