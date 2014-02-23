
function IService(service) {

    return {
		
		getRequestId: service.getRequestId,
		
		setRequestId: service.setRequestId,
		
		getService: service.getService,
		
		setService: service.setService,
		
		getApi: service.getApi,
		
		setApi: service.setApi,

		getServiceDescriptor: service.getServiceDescriptor,
		
		setServiceDescriptor: service.setServiceDesciptor,
		
		invoke: service.invoke
    }
}
