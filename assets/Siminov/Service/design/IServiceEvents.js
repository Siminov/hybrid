

function IServiceEvents(serviceEvents) {

	return {
		
		onServiceStart: serviceEvents.onServiceStart,
		
		onServiceQueue: serviceEvents.onServiceQueue,
		
		onServicePause: serviceEvents.onServicePause,
		
		onServiceResume: serviceEvents.onServiceResume,
		
		onServiceFinish: serviceEvents.onServiceFinish,
		
		onServiceApiInvoke: serviceEvents.onServiceApiInvoke,
		
		onServiceApiFinish: serviceEvents.onServiceApiFinish,
		
		onServiceTerminate: serviceEvents.onServiceTerminate		
	}
}