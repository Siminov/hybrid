package siminov.hybrid.sync;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import siminov.connect.model.SyncDescriptor;
import siminov.connect.resource.Resources;
import siminov.connect.sync.design.ISyncRequest;

public class SyncHandler {

	private Resources connectResources = Resources.getInstance();
	
	private SyncWorker syncWorker = SyncWorker.getInstance();
	private Map<ISyncRequest, Long> requestTimestamps = new HashMap<ISyncRequest, Long>();

	private static SyncHandler syncHandler = null;
	
	private SyncHandler() {
		
	}
	
	public static SyncHandler getInstance() {
		
		if(syncHandler == null) {
			syncHandler = new SyncHandler();
		}
		
		return syncHandler;
	}
	
	public void handle(ISyncRequest syncRequest) {

		SyncDescriptor syncDescriptor = connectResources.getSyncDescriptor(syncRequest.getName());
		
		Long requestTimestamp = requestTimestamps.get(syncRequest);
		if(requestTimestamp == null || requestTimestamp <= 0) {
			syncWorker.addRequest(syncRequest);
			requestTimestamps.put(syncRequest, Long.valueOf(Calendar.getInstance().get(Calendar.MILLISECOND)));

			return;
		}
		
		
		long syncInterval = syncDescriptor.getSyncInterval();
		long lastRefreshTimestamp = requestTimestamps.get(syncRequest.getName());
		long currentTimestamp = Long.valueOf(Calendar.getInstance().get(Calendar.MILLISECOND));
		
		long timeDifference = lastRefreshTimestamp + syncInterval;
		
		if(timeDifference < currentTimestamp) {
			syncWorker.addRequest(syncRequest);
			requestTimestamps.put(syncRequest, Long.valueOf(Calendar.getInstance().get(Calendar.MILLISECOND)));
		}
	}
}
