In service descriptor you can define the mode of service execution. Service execute in two ways:


```xml

    <service-descriptor>
        <requests>
                <request>
                    <property name="mode">SYNC|ASYNC</property>
                </request>
        </requests>
    </service-descriptor>

```


- **Foreground Service**:
A foreground service is a service that's considered to be something the user is actively aware of and thus not a candidate for the system to kill when low on memory. A foreground service must provide a notification for the status bar, which is placed under the "Ongoing" heading, which means that the notification cannot be dismissed unless the service is either stopped or removed from the foreground.

**Example**:
For example, a music player that plays music from a service should be set to run in the foreground, because the user is explicitly aware of its operation. The notification in the status bar might indicate the current song and allow the user to launch an service to interact with the music player.

To request that your service run in the foreground, you have to configure your service descriptor mode property to SYNC, default this property value is SYNC only.

- **Background Service**: 
A background service is one which runs without user interaction, it provide a straightforward structure for running an operation on a single background thread. This allows to handle long-running operations without affecting your user interface's responsiveness. Also it is not affected by most user interface lifecycle events, so it continues to run in circumstances that would shut down an ASYNC Service.

To request that your service run in the background, you have to configure your service descriptor mode property to ASYNC.
