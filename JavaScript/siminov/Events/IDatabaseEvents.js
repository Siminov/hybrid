/**
 * [SIMINOV FRAMEWORK - HYBRID]
 * Copyright [2014-2016] [Siminov Software Solution LLP|support@siminov.com]
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

var win;
var dom;

try {

    if(!window) {
    	window = global || window;
    }

	win = window;
	dom = window['document'];
} catch(e) {
	win = Ti.App.Properties;
}



if(dom == undefined) {
    module.exports = IDatabaseEvents;
}


/**
    Exposes methods which deal with events associated with database operation's.
    It has methods such as (databaseCreated, databaseDroped, tableCreated, tableDroped, indexCreated).

    @module Database
    @class IDatabaseEvents
    @constructor
*/
function IDatabaseEvents(databaseEvents) {
    
    return {

        /**
            This event is fired when database gets created as per database descriptor.

            @method onDatabaseCreated
            @param databaseDescriptor {DatabaseDescriptor} Database Descriptor
        */
        onDatabaseCreated: databaseEvents.onDatabaseCreated,

        /**
            This event is fired when database is dropped.

            @method onDatabaseDropped
            @param databaseDescriptor {DatabaseDescriptor} Database Descriptor
        */
        onDatabaseDropped: databaseEvents.onDatabaseDropped,

        /**
            This event is fired when a table is created.

            @method onTableCreated
            @param databaseDescriptor {DatabaseDescriptor} Database Descriptor
            @param entityDescriptor {EntityDescriptor} Entity Descriptor
        */
        onTableCreated: databaseEvents.onTableCreated,

        /**
            This event is fired when a table is dropped.

            @method onTableDropped
            @param databaseDescriptor {DatabaseDescriptor} Database Descriptor
            @param entityDescriptor {EntityDescriptor} EntityDescriptor
        */
        onTableDropped: databaseEvents.onTableDropped,

        /**
            This event is fired when a index is created on table.

            @method onIndexCreated
            @param databaseDescriptor {DatabaseDescriptor} Database Descriptor
            @param entityDescriptor {EntityDescriptor} Entity Descriptor
            @param index {Index} Index
        */
        onIndexCreated: databaseEvents.onIndexCreated,
        
        /**
            This event is fired when a index is dropped.

            @method onIndexDropped
            @param databaseDescriptor {DatabaseDescriptor} Database Descriptor
            @param entityDescriptor {EntityDescriptor} Entity Descriptor
            @param index {Index} Index
        */
        onIndexDropped: databaseEvents.onIndexDropped
    }
}
