Web provides few event notifiers which gets triggered based on particular action. Application have to provide implementation for these event notifiers and register them with Siminov.

> **Note**
>
> - **Event Handler:** It can be define in both Native and Web.
>
> - **Native Event Handler:** If you want to handle Event in Native then define Event Handler using Native language. Specify full model class path and name in ApplicationDescriptor.si.xml.
>
> - **Web Event Handler:** If you want to handle Event in Web then define Event Handler using JavaScript. Specify only JavaScript Function name in ApplicationDescriptor.si.xml.
>
> - **Both (Native/Web) Event Handler:** If you want to handle Event in both Native and Web then define Event Handler in both Native and JavaScript. Specify full model class path and name in ApplicationDescriptor.si.xml, no need to define for JavaScript because Siminov will automatically assume same name for it.

***
**ISiminov**: It provide API's related to life cycle of Siminov Framework


**Android API: Event Handler**
```java

    public interface ISiminovEvents {

        public void onFirstTimeSiminovInitialized();

        public void onSiminovInitialized();

        public void onSiminovStopped();
    }

```


**iOS API: Event Handler**

```objective-c

    @protocol SICISiminovEvents <NSObject>

    - (void)onFirstTimeSiminovInitialized;

    - (void)onSiminovInitialized;

    - (void)onSiminovStopped;

    @end

```


**Windows Sample: Event Handler**

```c#

    public interface ISiminovEvents 
    {

        void OnFirstTimeSiminovInitialized();

        void OnSiminovInitialized();

        void OnSiminovStopped();
    }

```


**JavaScript API: Event Handler**

```javascript

    function ISiminovEvents {

        this.onFirstTimeSiminovInitialized = function() {};

        this.onSiminovInitialized = function() {};

        this.onSiminovStopped = function() {};
    }

```


###### 1. On First Time Siminov Initialized - [Android:onFirstTimSiminovInitialized | iOS:onFirstTimSiminovInitialized | Windows:OnFirstTimSiminovInitialized | JavaScript:onFirstTimSiminovInitialized]

It is triggered when core is initialized for first time. In this you can perform tasks which are related to initialization of things only first time of application starts.

- Example: Preparing initial data for application, which is required by application in its life
time, Since it is to be done only once, therefore we will use this API:

#### Android Sample: First Time Siminov Initialized 

```java

    public void onFirstTimeSiminovInitialized() {
        new DatabaseUtils().prepareData();
    }

```

#### iOS Sample: First Time Siminov Initialized

```objective-c

    - (void)onFirstTimeSiminovInitialized {
        [DatabaseUtils prepareData];
    }

```

#### Windows Sample: First Time Siminov Initialized

```c#

    public void OnFirstTimeSiminovInitialized() {
        new DatabaseUtils().PrepareData();
    }

```


#### JavaScript Sample: First Time Siminov Initialized 

```java

    function onFirstTimeSiminovInitialized() {
        new DatabaseUtils().prepareData();
    }

```

> **Note**
>
> - This API will be triggered only once when Core is initialized first time.


###### 2. On Siminov Initialized - [Android:onSiminovInitialized | iOS:onSiminovInitialized | Windows:OnSiminovInitialized | JavaScript:onSiminovInitialized]
It is triggered whenever Core is initialized.

> **Note**
>
> - This doesn't gets triggered when Core is first time initialized, instead of this API will be triggered.


###### 3. On Siminov Stopped - [Android:onSiminovStopped | iOS:onSiminovStopped | Windows:OnSiminovStopped | JavaScript:onSiminovStopped]
It is triggered when Core is shutdown.

## Android Sample: ISiminov Event Handler

***

![Android Sample: ISiminov Event Handler] (https://raw.github.com/Siminov/android-web/docs/github/v1.0/isiminov_core_application_sample_example.png "Android Sample: ISiminov Event Handler")

***

## iOS Sample: SICISiminov Event Handler

***

![iOS Sample: SICISiminov Event Handler] (https://raw.github.com/Siminov/ios-web/docs/github/v1.0/isiminov_core_application_sample_example.png "iOS Sample: SICISiminov Event Handler")

***


## Windows Sample: ISiminov Event Handler

***

![Windows Sample: ISiminov Event Handler] (https://raw.github.com/Siminov/windows-web/docs/github/v1.0/isiminov_core_application_sample_example.png "Windows Sample: ISiminov Event Handler")

***