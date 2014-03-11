package siminov.hybrid.service;

import siminov.connect.connection.ConnectionRequest;
import siminov.connect.connection.ConnectionResponse;
import siminov.connect.exception.ServiceException;
import siminov.connect.service.Service;

public class GenericService extends Service {

	public void onServiceStart() {
		System.out.print("");
	}

	public void onServiceQueue() {
		System.out.print("");
	}

	public void onServicePause() {
		System.out.print("");
	}

	public void onServiceResume() {
		System.out.print("");
	}

	public void onServiceFinish() {
		System.out.print("");
	}

	public void onServiceApiInvoke(ConnectionRequest connectionRequest) {
		System.out.print("");
	}

	public void onServiceApiFinish(ConnectionResponse connectionResponse) {
		System.out.print("");
	}

	public void onServiceTerminate(ServiceException serviceException) {
		System.out.print("");
	}
}
