package siminov.hybrid.events;

import siminov.connect.design.sync.ISyncRequest;
import siminov.connect.events.ISyncEvents;

public class SyncEventHandler implements ISyncEvents {

	public void onSyncStarted(ISyncRequest syncRequest) {
		System.out.print("");
	}

	public void onSyncQueued(ISyncRequest syncRequest) {
		System.out.print("");
	}

	public void onSyncRemoved(ISyncRequest syncRequest) {
		System.out.print("");
	}

	public void onSyncTerminated(ISyncRequest syncRequest) {
		System.out.print("");
	}
}
