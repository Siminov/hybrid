/** 
 * [SIMINOV FRAMEWORK]
 * Copyright [2013] [Siminov Software Solution LLP|support@siminov.com]
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



/**
	It contain all Events triggered by Siminov Framework.

	@module Events	
*/

/**
	Any event triggered by Siminov is first handled by this function later it will deliver to appropriate Event APIs. 
	
	@module Events
	@class EventHandler
	@constructor	
*/
function EventHandler() {


	/**
		Handle event triggered by Siminov.
		
		@method triggerEvent
	*/
    this.triggerEvent = function(data) {

        var hybridSiminovDatas = SIJsonHelper.toSI(data);
        var datas = hybridSiminovDatas.getHybridSiminovDatas();

        var functionName;
        var apiName;

        var events = new Array();
        var parameters = new Array();

        if(datas != undefined && datas != null && datas.length > 0) {

            for(var i = 0;i < datas.length;i++) {
                var data = datas[i];

                var dataType = data.getDataType();
                if(dataType === Constants.EVENT_HANDLER_TRIGGERED_EVENT) {
                    apiName = data.getDataValue();
                } else if(dataType === Constants.EVENT_HANDLER_EVENTS) {

                    var values = data.getValues();
                    if(values != undefined &&  values != null && values.length > 0) {
                        for(var j = 0;j < values.length;j++) {
                            events.push(values[j].getValue());
                        }
                    }

                } else if(dataType === Constants.EVENT_HANDLER_EVENT_PARAMETERS) {
					
					var parameterDatas = data.getDatas();
					if(parameterDatas != undefined && parameterDatas != null && parameterDatas.length > 0) {
						
						for(var j = 0;j < parameterDatas.length;j++) {

							var parameter = SIDatasHelper.toModel(parameterDatas[j]);
							parameters.push(parameter);
						}					
					}
                }
            }
        }


        if(events != undefined && events != null && events.length > 0) {

            for(var i = 0;i < events.length;i++) {
                var obj = FunctionUtils.createFunctionInstance(events[i]);

                var containEvent = obj.containProperties(apiName);
                if(containEvent) {
                    functionName = obj.getObjectName();
                    break;
                }
            }
        }

        var eventHandler = FunctionUtils.createFunctionInstance(functionName);
        if(apiName === Constants.EVENT_HANDLER_ISIMINOV_EVENT_FIRST_TIME_SIMINOV_INITIALIZED) {

            FunctionUtils.invokeAndInflate(eventHandler, apiName);
        } else if(apiName === Constants.EVENT_HANDLER_ISIMINOV_EVENT_SIMINOV_INITIALIZED) {

            FunctionUtils.invokeAndInflate(eventHandler, apiName);
        } else if(apiName === Constants.EVENT_HANDLER_ISIMINOV_EVENT_SIMINOV_STOPPED) {

            setTimeout(FunctionUtils.invokeAndInflate(eventHandler, apiName), 0);
        } else if(apiName === Constants.EVENT_HANDLER_IDATABASE_EVENT_DATABASE_CREATED) {
            
            var databaseDescriptor = parameters[0];
            FunctionUtils.invokeAndInflate(eventHandler, apiName, databaseDescriptor);
            
        } else if(apiName === Constants.EVENT_HANDLER_IDATABASE_EVENT_DATABASE_DROPPED) {

			var databaseDescriptor = parameters[0];
			FunctionUtils.invokeAndInflate(eventHandler, apiName, databaseDescriptor);

        } else if(apiName === Constants.EVENT_HANDLER_IDATABASE_EVENT_TABLE_CREATED) {

			var databaseDescriptor = parameters[0];
			var databaseMappingDescriptor = parameters[1];
		
			FunctionUtils.invokeAndInflate(eventHandler, apiName, databaseDescriptor, databaseMappingDescriptor);

        } else if(apiName === Constants.EVENT_HANDLER_IDATABASE_EVENT_TABLE_DROPPED) {

			var databaseDescriptor = parameters[0];
			var databaseMappingDescriptor = parameters[1];
		
			FunctionUtils.invokeAndInflate(eventHandler, apiName, databaseDescriptor, databaseMappingDescriptor);

        } else if(apiName === Constants.EVENT_HANDLER_IDATABASE_EVENT_INDEX_CREATED) {

			var databaseDescriptor = parameters[0];
			var databaseMappingDescriptor = parameters[1];
			var index = parameters[2];
			
			FunctionUtils.invokeAndInflate(eventHandler, apiName, databaseDescriptor, databaseMappingDescriptor, index);

        } else if(apiName === Constants.EVENT_HANDLER_IDATABASE_EVENT_INDEX_DROPPED) {

			var databaseDescriptor = parameters[0];
			var databaseMappingDescriptor = parameters[1];
			var index = parameters[2];
		
			FunctionUtils.invokeAndInflate(eventHandler, apiName, databaseDescriptor, databaseMappingDescriptor, index);

        }

    }

}
