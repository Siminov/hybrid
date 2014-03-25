package siminov.hybrid.adapter.constants;

public interface HybridServiceHandler {

	public String TRIGGERED_EVENT = "TRIGGERED_EVENT";

	public String ISERVICE_API_HANDLER = "ISERVICE_API_HANDLER";
	
	public String ISERVICE_ON_SERVICE_START = "onServiceStart";

	public String ISERVICE_ON_SERVICE_QUEUE = "onServiceQueue";

	public String ISERVICE_ON_SERVICE_PAUSE = "onServicePause";
	
	public String ISERVICE_ON_SERVICE_RESUME = "onServiceResume";
	
	public String ISERVICE_ON_SERVICE_FINISH = "onServiceFinish";
	
	public String ISERVICE_ON_SERVICE_API_INVOKE = "onServiceApiInvoke";
	
	public String ISERVICE_ON_SERVICE_API_FINISH = "onServiceApiFinish";
	
	public String ISERVICE_ON_SERVICE_TERMINATE = "onServiceTerminate";
}
