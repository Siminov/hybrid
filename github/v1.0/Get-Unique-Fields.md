## Get Unique Fields

#### Android API: Get Unique Fields

```java

    public final Iterator<String> getUniqueFields() throws DatabaseException;

```

- _Android Sample: Get Unique Fields_

```java

    Iterator<String> uniqueFields = null;
    try {
        uniqueFields = new Liquor().getUniqueFields();
    } catch(DatabaseException de) {
		//Log it.
    }

```

#### iOS API: Get Unique Fields

```objective-c

    - (NSArray *)getUniqueFields;

```

- _iOS Sample: Get Unique Fields_

```objective-c

    NSArray *uniqueFields = nil;
    @try {
        uniqueFields = [[[Liquor alloc] init] getUniqueFields];
    } @catch(SICDatabaseException *databaseException) {
		//Log it.
    }

```

#### Windows API: Get Unique Fields

```c#

    public IEnumerator<String> GetUniqueFields();

```

- _Windows Sample: Get Column Names_

```c#

    IEnumerator<String> uniqueFields = null;
    try 
    {
        uniqueFields = new Liquor().GetUniqueFields();
    } 
    catch(DatabaseException de) 
    {
		//Log it.
    }

```

#### JavaScript API: Get Unique Fields

```javascript

    this.getUniqueFields = function();

```

- _JavaScript Sample: Get Column Names_

```javascript

    var uniqueFields = null;
    try {
        uniqueFields = new Liquor().getUniqueFields();
    } catch(DatabaseException de) {
		//Log it.
    }

```