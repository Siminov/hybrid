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



function IGroupConcat(select) {

    return {

        interfaceName : "IGroupConcat",

        delimiter : select.delimiter,

        where : select.where,

        whereClause : select.whereClause,

        and : select.and,

        or : select.or,

        groupBy : select.groupBy,

        having : select.having,

        havingClause : select.havingClause,

        column : select.column,

        execute : select.execute

    }

}
