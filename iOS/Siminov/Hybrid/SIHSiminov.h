///
/// [SIMINOV FRAMEWORK - HYBRID]
/// Copyright [2014-2016] [Siminov Software Solution LLP|support@siminov.com]
///
/// Licensed under the Apache License, Version 2.0 (the "License");
/// you may not use this file except in compliance with the License.
/// You may obtain a copy of the License at
///
///     http://www.apache.org/licenses/LICENSE-2.0
///
/// Unless required by applicable law or agreed to in writing, software
/// distributed under the License is distributed on an "AS IS" BASIS,
/// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
/// See the License for the specific language governing permissions and
/// limitations under the License.
///


#import <Foundation/Foundation.h>

#import "SIKSiminov.h"
#import "SICIInitializer.h"


/**
 * Exposes methods to deal with SIMINOV HYBRID FRAMEWORK.
 *	<p>
 *		Such As
 *		<p>
 *			1. Initializer: Entry point to the SIMINOV HYBRID.
 *		</p>
 *
 *
 *	Note*: siminov.hybrid.Siminov extends siminov.core.Siminov.
 *
 *	</p>
 */
@interface SIHSiminov : SIKSiminov {
    
}


/**
 * It is used to check whether SIMINOV HYBRID FRAMEWORK is active or not.
 * <p>
 * SIMINOV become active only when its dependent and itself get initialized.
 *
 * @exception If SIMINOV is not active it will throw DeploymentException which is RuntimeException.
 *
 */
+ (void)isActive;

+ (bool)getActive;

+ (void)setActive:(BOOL)active;

+ (id<SICIInitializer>)initializer;


/**
 * It is the entry point to the SIMINOV HYBRID FRAMEWORK.
 * <p>
 * When application starts it should call this method to activate SIMINOV HYBRID FRAMEWORK, by providing ApplicationContext and WebView as the parameter.
 * </p>
 *
 * <p>
 * Siminov will read all descriptor defined by application, and do necessary processing.
 * </p>
 *
 * 	EXAMPLE
 * 	@code {
 *
 
 public class Siminov extends DroidGap {
	
 public void onCreate(Bundle savedInstanceState) {
 
 super.onCreate(savedInstanceState);
 super.init();
 super.appView.getSettings().setJavaScriptEnabled(true);
 
 initializeSiminov();
 
 super.loadUrl("file:///android_asset/www/home.html");
 
 }
 
 
 private void initializeSiminov() {
 siminov.hybrid.Siminov.initialize(getApplicationContext(), this.appView);
 }
 
 }
 
 * }
 *
 * @param context Application content.
 * @param webView WebView.
 * @exception If any exception occur while deploying application it will through DeploymentException, which is RuntimeException.
 */
+ (void)start;

/**
 * It is used to stop all service started by SIMINOV.
 * <p>
 * When application shutdown they should call this. It do following services:
 * <p>
 * 		<pre>
 * 			<ul>
 * 				<li> Close all database's opened by SIMINOV.
 * 				<li> Deallocate all resources held by SIMINOV.
 * 			</ul>
 *		</pre>
 *	</p>
 *
 * @throws SiminovException If any error occur while shutting down SIMINOV.
 */
+ (void)shutdown;


+ (void)processAdapterDescriptors;

+ (void)processHybridServices;

+ (void)siminovInitialized;

@end
