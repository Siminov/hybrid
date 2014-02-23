

var ServiceHandler = (function() {

	var serviceHandler;
	
	return {
	
		getInstance : function() {
			if(serviceHandler == null) {
				serviceHandler = new ServiceHandler();
				
				serviceHandler.constructor = null;
			}
			
			return serviceHandler;
		}
	
	}
	

	function ServiceHandler() {
		
		
		this.handle = function(iService) {
			
	        var adapter = new Adapter();
	        adapter.setAdapterName(Constants.SIMINOV_SERVICE_ADAPTER);
	        adapter.setHandlerName(Constants.SIMINOV_SERVICE_ADAPTER_INVOKE_HANDLER);
		
			var hybridSiminovDatas = new HybridSiminovDatas();
				
			var service = new HybridSiminovDatas.HybridSiminovData();
			service.setDataType(Constants.SIMINOV_SERVICE_ADAPTER_INVOKE_HANDLER_SERVICE_PARAMETER);
			service.setDataValue(iService.getService());
			
			var api = new HybridSiminovDatas.HybridSiminovData();
			api.setDataType(Constants.SIMINOV_SERVICE_ADAPTER_INVOKE_HANDLER_API_PARAMETER);
			api.setDataValue(iService.getApi());
			
			hybridSiminovDatas.addHybridSiminovData(service);
			hybridSiminovDatas.addHybridSiminovData(api);
	
			
			var data = SIJsonHelper.toJson(hybridSiminovDatas);
			
			adapter.addParameter(data);
	
			adapter.invoke();
		}
	}
		
}) ();
