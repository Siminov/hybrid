Push notifications let your application notify a user of new messages or events even when the user is not actively using your application. On Android devices, when a device receives a push notification, your application's icon and a message appear in the status bar.When the user taps the notification, they are sent to your application.

Today push notification is provide by all the platforms but in different forms and implementation. Connect provides a unique and generic implementation on all platforms with same APIs bundle.
	
- Android: Google Cloud Messaging Services - GCM
- Apple: Apple Push Notification Services - APNS
- Windows: Windows Push Notification Services - WNS

## Handling Notification
Connect provides a Notification manager which manages all operations performed on push notification. If you want to handle any of the event, then you have to register INotificationEvent handler in your Application Descriptor.

```xml

    <siminov>
        <notification-descriptor>
            <property name="name_of_property">value_of_property</property>
        </notification-descriptor>
    </siminov>

```

It provide a property TAG, using this you can add your own property needed to handle push notification.

```xml

    <siminov>
        <notification-descriptor>
            <property name="sender_id">sender_id_of_the_app</property>
        </notification-descriptor>
    </siminov>

```
