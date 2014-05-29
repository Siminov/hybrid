package siminov.hybrid.adapter.handlers;

import siminov.hybrid.adapter.IAdapter;

public class NotificationHandler implements IAdapter {

	private siminov.connect.notification.NotificationManager notificationManager = siminov.connect.notification.NotificationManager.getInstance();
	
	public void doRegistration() {
		notificationManager.doRegistration();
	}
	
	public void doUnregistration() {
		notificationManager.doUnregistration();
	}
}
