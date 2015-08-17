Every application when it starts they need to first initialize Core. This can be done by invoking initialize API of Android:siminov.connect.Siminov | iOS:SIKSiminov | Windows:Siminov.Connect.Siminov class by passing required parameters to the method.

## Android Initialization

There are two ways to initialize Connect on Android.

#### _**Initializing siminov by extending application class**_

```java

    public class ApplicationSiminov extends Application {

        public void onCreate() { 
            super.onCreate();

            initializeSiminov();
       }
	
       private void initializeSiminov() {
		
           IInitializer initializer = siminov.connect.Siminov.initialize();
           initializer.addParameter(this);

           initializer.start();
       }
    }

```

> **Note**
>
> - Android provides support in every application to create an application wide class. The base class for this is the _android.app.Application_ class.


#### _**Initializing siminov by extending activity class**_

```java

    public class HomeActivity extends Activity {
	
        public void onCreate(Bundle savedInstanceState) {
            initializeSiminov();
        }

        private void initializeSiminov() {
            IInitializer initializer = siminov.connect.Siminov.initialize();
            initializer.addParameter(getApplicationContext());

            initializer.start();
       }
    }

```

> **Note**
>
> - If you are initializing siminov from activity sub class then you should pass application context not the activity context. See above example.

#### Android: IInitializer Interface
_siminov.connect.Siminov.initialize()_ API returns IInitializer interface implemented class through which we can pass parameters needed by Siminov Framework to work functionally.


```java

    public interface IInitializer {

        public void addParameter(Object object);
	
        public void start();
	
    }

```


- _**Important points about Initializing Siminov**_

> **Note**
>
> - Application should call _siminov.connect.Siminov_ initialize only once in the life time of application.
>
> - Once siminov initialize it can not be re initialized.


## iOS Initialization

#### - _**Initializing Connect from AppDelegate**_

```objective-c

    id<SICIInitializer> initializer = [SIKSiminov initializer];
    [initializer initialize];

```

#### iOS:SIKIInitializer Interface
[SIKSiminov initializer] API returns SIKIInitializer interface through which we can pass parameters needed by Siminov Framework to work functionally.


```objective-c

    @protocol SIKIInitializer <NSObject>

    - (void)addParameter:(id)object;

    - (void)initialize;

    @end

```


> Note
>
> - Application should call _SIKSiminov_ initialize only once in the life time of application.
>
> - Once Core is initialized it can not be re-initialized.



## Windows Initialization

#### - _**Initializing Core by extending application class**_

```c#

    sealed partial class App : Application
    {
        /// <summary>
        /// Initializes the singleton application object.  This is the first line of authored code
        /// executed, and as such is the logical equivalent of main() or WinMain().
        /// </summary>
        public App()
        {
            this.InitializeComponent();
            this.Suspending += OnSuspending;

            new Siminov.Core.Sample.Siminov().InitializeSiminov();
        }
    }

```


#### Windows:IInitializer Interface
_Siminov.Connect.Siminov.Initialize()_ API returns IInitializer interface implemented class through which we can pass parameters needed by Siminov Framework to work functionally.


```c#

    public interface IInitializer 
    {

        void AddParameter(Object object);
	
        void Start();
	
    }

```



## Steps Performed While Initializing Siminov

#### 1. Database Creation

Siminov provides **Initialization Layer** which handles the creation of databases required by application. Siminov follows below steps to create databases required by application.

- Step 1: Then application invokes initialize method, it checks whether database exists or not.

- Step 2: If application database does not exists, siminov will create database required by the application.

- Step 3: If application database exists, then it will read all descriptors defined by application based on load initially property defined in ApplicationDescriptor.si.xml file.

#### 2. Initialize Resource Manager Layer

Any resource created by siminov framework is places in resource layer of siminov. You can use API's provided by Resources class to get required object.

**Android: Resource Manager**

```java

    public class ResourceManager {

        public ApplicationDescriptor getApplicationDescriptor();
	
        public void SetApplicationDescriptor(final ApplicationDescriptor applicationDescriptor);
	
        public ServiceDescriptor RequiredServiceDescriptorBasedOnPath(final String serviceDescriptorPath);
	
        public ServiceDescriptor RequiredServiceDescriptorBasedOnName(final String serviceDescriptorName);
	
        public Request requiredRequestBasedOnServiceDescriptorPath(final String serviceDescriptorPath, final String requestName); 
	
        public Request requireRequestBasedOnServiceDescriptorName(final String serviceDescriptorName, final String requestName);
	
        public INotificationEvents getNotificationEventHandler();
	
        public ISyncEvents getSyncEventHandler();
	
        public IEnumerator<SyncDescriptor> GetSyncDescriptors();
	
        public SyncDescriptor getSyncDescriptor(final String syncDescriptorName);
		
    }

```


**iOS: Resource Manager**

```objective-c

    @interface SIKResourceManager : NSObject

    + (SIKResourceManager *)getInstance;

    - (SIKApplicationDescriptor *)getApplicationDescriptor;

    - (void)setApplicationDescriptor:(SIKApplicationDescriptor *)appDescriptor;

    - (SIKServiceDescriptor *)requiredServiceDescriptorBasedOnPath:(NSString *)serviceDescriptorPath;

    - (SIKServiceDescriptor *)requiredServiceDescriptorBasedOnName:(NSString *)serviceDescriptorName;

    - (SIKRequest *)requiredRequestBasedOnServiceDescriptorPath:(NSString *)serviceDescriptorPath requestName:(NSString *)requestName;

    - (SIKRequest *)requireRequestBasedOnServiceDescriptorName:(NSString *)serviceDescriptorName requestName:(NSString *)requestName;

    - (id<SIKINotificationEvents>)getNotificationEventHandler;

    - (id<SIKISyncEvents>)getSyncEventHandler;

    - (NSEnumerator *)getSyncDescriptors;

    - (SIKSyncDescriptor *)getSyncDescriptor:(NSString *)syncDescriptorName;

    @end

```

**Windows: Resource Manager**

```c#

    public class ResourceManager {

        public ApplicationDescriptor GetApplicationDescriptor();
	
        public void SetApplicationDescriptor(ApplicationDescriptor applicationDescriptor);
	
        public ServiceDescriptor RequiredServiceDescriptorBasedOnPath(String serviceDescriptorPath);
	
        public ServiceDescriptor RequiredServiceDescriptorBasedOnName(String serviceDescriptorName);
	
        public Request RequiredRequestBasedOnServiceDescriptorPath(String serviceDescriptorPath, String requestName); 
	
        public Request RequireRequestBasedOnServiceDescriptorName(String serviceDescriptorName, String requestName);
	
        public INotificationEvents GetNotificationEventHandler();
	
        public ISyncEvents GetSyncEventHandler();
	
        public IEnumerator<SyncDescriptor> GetSyncDescriptors();
	
        public SyncDescriptor GetSyncDescriptor(String syncDescriptorName);
		
    }

```