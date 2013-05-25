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



Object.defineProperty(
    Object.prototype, "properties", {
        value: function() {
            var result = [];
            for (var property in this) {
                if (this.hasOwnProperty(property))
                    result.push(property);
            }

            return result;
        }
    }
);


Object.defineProperty(
    Object.prototype, "containProperties", {
        value: function(property) {
            var properties = this.properties();

            for(var i = 0;i < properties.length;i++) {
                if(properties[i] === property) {
                    return true;
                }
            }

            return false;
        }
    }
);


Object.defineProperty(
    Object.prototype, "getterProperties", {
        value: function() {
            var properties = this.properties();
            var getterProperties = [];

            for(var i = 0;i < properties.length;i++) {
                if(properties[i].indexOf("get") == 0 || properties[i].indexOf("is") == 0) {
                    getterProperties.push(properties[i]);
                }
            }

            return getterProperties;
        }
    }
);


Object.defineProperty(
    Object.prototype, "setterProperties", {
        value: function() {
            var properties = this.properties();
            var setterProperties = [];

            for(var i = 0;i < properties.length;i++) {
                if(properties[i].indexOf("set") === 0) {
                    setterProperties.push(properties[i]);
                }
            }

            return setterProperties;
        }
    }
);


Object.defineProperty(
    Object.prototype, "getObjectName", {
        value: function() {
            var funcNameRegex = /function (.{1,})\(/;
            var results = (funcNameRegex).exec((this).constructor.toString());

            return (results && results.length > 1) ? results[1] : "";
        }
    }
);



function FunctionUtils() {

}



FunctionUtils.extend = function (parent, child) {
    child.prototype = new parent();
    child.prototype.constructor = child;
}


FunctionUtils.createFunctionInstance = function(functionName) {
    var obj = FunctionUtils.createFunctionInstanceDescend(window, functionName);
    return new obj();
}

FunctionUtils.createFunctionInstanceDescend = function(obj, path) {
    var parts = path.split('.');

    for(var i = 0; i < parts.length; i++) {
        obj = obj[parts[i]];
    }

    return obj;
}

FunctionUtils.invokeAndInflate = function(object, functionName, parameterValues) {
	object[functionName].apply(object, Array.prototype.slice.call(arguments, 2));
}

FunctionUtils.invokeAndFetch = function(object, functionName) {
    return object[functionName] ();
}
