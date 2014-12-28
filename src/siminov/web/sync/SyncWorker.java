/** 
 * [SIMINOV FRAMEWORK]
 * Copyright [2015] [Siminov Software Solution LLP|support@siminov.com]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **/

package siminov.web.sync;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

import siminov.connect.Constants;
import siminov.connect.IRequest;
import siminov.connect.IWorker;
import siminov.connect.events.ISyncEvents;
import siminov.connect.model.ServiceDescriptor;
import siminov.connect.model.SyncDescriptor;
import siminov.connect.resource.ResourceManager;
import siminov.connect.service.design.IService;
import siminov.connect.sync.design.ISyncRequest;
import siminov.web.service.GenericService;

/**
 * It process sync request on the behalf of web
 */
public class SyncWorker implements IWorker {

	private static SyncWorker syncWorker = null;
	private SyncWorkerThread syncWorkerThread = null;
	
	private Collection<ISyncRequest> syncRequests = new ConcurrentLinkedQueue<ISyncRequest>();
	
	private ResourceManager resourceManager = ResourceManager.getInstance();
	
	/**
	 * SyncWorker Private Constructor
	 */
	private SyncWorker() {
		
	}

	/**
	 * It provides singleton instance of SyncWorker
	 * @return SyncWorker singleton instance
	 */
	public static SyncWorker getInstance() {
		
		if(syncWorker == null) {
			syncWorker = new SyncWorker();
		}
		
		return syncWorker;
	}
	
	
	public void startWorker() {
		
		if(syncWorkerThread == null) {
			syncWorkerThread = new SyncWorkerThread();
		}
		
		syncWorkerThread.start();
	}
	
	public void stopWorker() {
		
		if(syncWorkerThread == null) {
			return;
		}
		
		
		if(!syncWorkerThread.isAlive()) {
			syncWorkerThread.start();
		}
	}
	
	public boolean isWorkerRunning() {
		
		if(syncWorkerThread == null) {
			return false;
		}
		
		return syncWorkerThread.isAlive();
	}
	
	private class SyncWorkerThread extends Thread {
		
		public void run() {
			
			Iterator<ISyncRequest> requests = syncRequests.iterator();
			while(requests.hasNext()) {
				
				ISyncRequest syncRequest = requests.next();

				/*
				 * Fire Sync Started Event
				 */
				ISyncEvents syncEventHandler = resourceManager.getSyncEventHandler();
				if(syncEventHandler != null) {
					syncEventHandler.onStart(syncRequest);
				}
				
				
				/*
				 * Process Request
				 */
				SyncDescriptor refreshDescriptor = resourceManager.getSyncDescriptor(syncRequest.getName());
				
				Iterator<String> services = refreshDescriptor.getServiceDescriptorNames();
				while(services.hasNext()) {
					
					String service = services.next();
					
					String serviceName = service.substring(0, service.indexOf(Constants.SYNC_DESCRIPTOR_SERVICE_SEPARATOR));
					String requestName = service.substring(service.indexOf(Constants.SYNC_DESCRIPTOR_SERVICE_SEPARATOR) + 1, service.length());

					
					ServiceDescriptor serviceDescriptor = resourceManager.requiredServiceDescriptorBasedOnName(serviceName);
					
					IService genericService = new GenericService();
					genericService.setService(serviceName);
					genericService.setRequest(requestName);
					
					genericService.setServiceDescriptor(serviceDescriptor);

					Iterator<String> resources = syncRequest.getResources();
					while(resources.hasNext()) {
						String resourceName = resources.next();
						Object resourceValue = syncRequest.getResource(resourceName);
						
						genericService.addResource(resourceName, resourceValue);
					}
					
					
					genericService.invoke();
				}
				
				
				/*
				 * Fire Sync Started Event
				 */
				if(syncEventHandler != null) {
					syncEventHandler.onFinish(syncRequest);
				}
				
				
				syncRequests.remove(syncRequest);
			}
			
			
			syncWorkerThread = null;
		}
	}
	

	public void addRequest(final IRequest request) {
		
		ISyncRequest syncRequest = (ISyncRequest) request;
		if(containsRequest(syncRequest)) {
			return;
		}
		
		
		this.syncRequests.add(syncRequest);

		/*
		 * Fire Sync Queued Event
		 */
		ISyncEvents syncEventHandler = resourceManager.getSyncEventHandler();
		if(syncEventHandler != null) {
			syncEventHandler.onQueue(syncRequest);
		}
		
		
		if(!isWorkerRunning()) {
			startWorker();
		}
	}
	
	public boolean containsRequest(final IRequest refreshRequest) {
		return this.syncRequests.contains(refreshRequest);
	}
	
	public void removeRequest(final IRequest refreshRequest) {
		this.syncRequests.remove(refreshRequest);
	}
}
