package siminov.hybrid.adapter.constants;

public interface HybridEventHandler {

	public String TRIGGERED_EVENT = "TriggeredEvent";
	
	public String ISIMINOV_EVENT_FIRST_TIME_SIMINOV_INITIALIZED = "firstTimeSiminovInitialized";
	public String ISIMINOV_EVENT_SIMINOV_INITIALIZED = "siminovInitialized";
	public String ISIMINOV_EVENT_SIMINOV_STOPPED = "siminovStopped";
	
	
	public String IDATABASE_EVENT_DATABASE_CREATED = "databaseCreated";
	public String IDATABASE_EVENT_DATABASE_DROPPED = "databaseDropped";
	public String IDATABASE_EVENT_TABLE_CREATED = "tableCreated";
	public String IDATABASE_EVENT_TABLE_DROPPED = "tableDropped";
	public String IDATABASE_EVENT_INDEX_CREATED = "indexCreated";
	public String IDATABASE_EVENT_INDEX_DROPPED = "indexDropped";
	
	
	public String EVENTS = "Events";
	
	public String EVENT_PARAMETERS = "EventParameters";
	
}
