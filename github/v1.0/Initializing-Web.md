Web application is a combination of Native and Web. Siminov Web Framework is build over Siminov Core, therefore it provides both Web ORM as well as Native ORM features.

> **Note**
>
> If you are using Siminov Web Framework then you don't have to explicitly initialize Siminov Core because Web will take care of that.


Initializing Siminov Core Framework is a two way of initialization through Native and Web.

- **Intialize Siminov Web From Native When Application Starts:** Initialize Siminov Web Framework by invoking initialize API of siminov.web.Siminov class and provide ApplicationContext and WebView as a parameter to API. Internally Siminov Web Framework will automatically initialize Siminov Native ORM.

**Android Sample: Initializing App**

```java

    public class Siminov extends DroidGap {

        public void onCreate(Bundle savedInstanceState) { 

            super.onCreate(savedInstanceState);
            super.init();
            super.appView.getSettings().setJavaScriptEnabled(true);

            initializeSiminov();

            super.loadUrl("file:///android_asset/www/home.html");
        }


        private void initializeSiminov() {

            IInitializer initializer = siminov.web.Siminov.initialize();
		
            initializer.addParameter(getApplicationContext());
            initializer.addParameter(super.appView);
            initializer.addParameter((Activity) this);
		
            initializer.start();
        }

        public void onDestroy() {
            super.onDestroy();
		
            System.exit(RESULT_OK);
        }
	
    }

```

- **Initialize Siminov Web From Web When Device Get Ready:** Intialize Siminov Web Framework by invoking initialize API of Siminov function, once Siminov gets initialized you will get a call back in firstTimeSiminovInitialized/siminovInitialized Event Handler.


```javascript

    //Register Siminov.initialize API For Device Ready Event. 
    document.addEventListener("deviceready", Siminov.initialize, false);

			
    //Application Defined Event Handler. Once Siminov gets initialize this Event Handler will be invoked.
    function SiminovEventHandler() {

        this.firstTimeSiminovInitialized = function() {
            //Siminov Initialized.
        }
			
        this.siminovInitialized = function() {
            //Siminov Initialized.
        }
					
    }

```


> **Note**
>
> - If you use Siminov Web Framework it is mandatory to register for Events.
>
> - If you use only Siminov Core it is not mandatory to register for Events.