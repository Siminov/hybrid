Every application when it starts they need to first initialize Siminov Core. They can do this by invoking initialize method of <i>siminov.orm.Siminov</i> class by passing <b>Application-Context</b> object as parameter to method.

> <b>Note</b>
> Use Siminov Core only if you are building Android Native based application.


There are two ways to initialize siminov.

## _**Initializing Siminov By Extending Application Class**_

```java
public class ApplicationSiminov extends Application {

	public void onCreate() { 
		super.onCreate();

		initializeSiminov();
	}
	
	private void initializeSiminov() {
                IInitializer initializer = siminov.core.Siminov.initialize();
                initializer.addParameter(this);

                initializer.start();
	}
}
```

> <b>Note</b>
> - Android provides support in every application to create an application wide class. The base class for this is the <i>android.app.Application<i/> class.


## _**Initializing Siminov By Extending Activity Class**_

```java
public class HomeActivity extends Activity {
	
	public void onCreate(Bundle savedInstanceState) {
		initializeSiminov();
	}

	private void initializeSiminov() {
                IInitializer initializer = siminov.core.Siminov.initialize();
                initializer.addParameter(getApplicationContext());

                initializer.start();
	}
}
```

> <b>Note</b>
> - If you are initializing Siminov from activity sub class then you should pass application context not the activity context. See above example.

- _**Important points about Initializing Siminov**_

> <b>Note</b>
> - Application should call <i>siminov.core.Siminov</i> initialize only once in the life time of application.
> - Once Siminov initialize it can not be re initialized.

## IInitializer Interface
siminov.core.Siminov.initialize() API returns IInitializer interface implemented class through which we can pass parameters needed by Siminov Framework to work functionally.


```java

public interface IInitializer {

	public void addParameter(Object object);
	
	public void start();
	
}

```


## Steps Performed While Initializing <b>Siminov Core Framework</b>
#### 1. Database Creation
Siminov provides <b>Initialization Layer</b> which handles the creation of databases required by application. Siminov Core follows below steps to create databases required by application.

- Step 1: Then application invokes initialize method, it checks whether database exists or not.
- Step 2: If application database does not exists, Siminov will create database required by the application.
- Step 3: If application database exists, then it will read all descriptors defined by application based on load initially property defined in ApplicationDescriptor.si.xml file.

#### 2. Initialize Resources Layer
Any resource created by Siminov framework is places in resource layer of Siminov. You can use API's provided by Resources class to get required object.

```java
public final class Resources {

	public static Resources getInstance();

	public Context getApplicationContext();

	public ApplicationDescriptor getApplicationDescriptor();

	public Iterator<String> getDatabaseDescriptorsPaths();

	public DatabaseDescriptor getDatabaseDescriptorBasedOnPath(final String databaseDescriptorPath);

	public DatabaseDescriptor getDatabaseDescriptorBasedOnName(final String databaseDescriptorName);

	public Iterator<DatabaseDescriptor> getDatabaseDescriptors();

	public DatabaseDescriptor getDatabaseDescriptorBasedOnClassName(final String className);

	public DatabaseDescriptor getDatabaseDescriptorBasedOnTableName(final String tableName);

	public DatabaseMapping getDatabaseMappingBasedOnClassName(final String className);

	public DatabaseMapping getDatabaseMappingBasedOnTableName(final String tableName);

	public Iterator<String> getLibraryPaths();
	
	public Iterator<String> getLibraryPathsBasedOnDatabaseDescriptorName(final String databaseDescriptorName);

	public Iterator<LibraryDescriptor> getLibraries();

	public Iterator<LibraryDescriptor> getLibrariesBasedOnDatabaseDescriptorName(final String databaseDescriptorName);
	
	public Iterator<DatabaseMapping> getLibraryDatabaseMappingsBasedOnLibraryDescriptorPath(final String libraryPath);

	public IDatabase getDatabaseBasedOnDatabaseDescriptorName(final String databaseName);

	public IDatabase getDatabaseBasedOnDatabaseMappingPojoClass(final Class<?> classObject);

        public IDatabase getDatabaseBasedOnDatabaseMappingClassName(final String databaseMappingClassName);

	public IDatabase getDatabaseBasedOnDatabaseMappingTableName(final String databaseMappingTableName);

	public Iterator<IDatabase> getDatabases();

	public ISiminovEvents getSiminovEventHandler();

	public IDatabaseEvents getDatabaseEventHandler();
}
```
