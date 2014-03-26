package siminov.hybrid.adapter.handlers;

public class NotificationHandler {

	private siminov.connect.notification.NotificationManager notificationManager = siminov.connect.notification.NotificationManager.getInstance();
	
	public void doRegistration() {
		notificationManager.doRegistration();
	}
	
	public void doUnregistration() {
		notificationManager.doUnregistration();
	}
}
