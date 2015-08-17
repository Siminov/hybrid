* Download Core from [Build](http://siminov.github.io/android-web/build.html)
* Download Connect from [Build](http://siminov.github.io/android-web/builds.html)
* Download Web from [Build](http://siminov.github.io/android-web/builds.html)
* Extract Web build.


**Android Sample:** Adding Web build file into app

***

![Extract Siminov Build] (https://raw.github.com/Siminov/android-web/docs/github/v1.0/siminov_web_build_extract.png)

***

***

![After Extracting Siminov Web Build] (https://raw.github.com/Siminov/android-web/docs/github/v1.0/siminov_web_build_extracted.png)

***

* Add both **Native** and **Web** jar's into your application libs folder.

***

![After Extracting Siminov Web Build] (https://raw.github.com/Siminov/android-web/docs/github/v1.0/siminov_web_sample_application_add_siminov_jars.png)

***

* Add Siminov Web (Siminov.js) to your assets folder.

***

![Siminov Web Sample Application] (https://raw.github.com/Siminov/android-hybrid/docs/github/v1.0/siminov_web_sample_application_add_siminov_js_to_application_assets.png)

***

* Include Siminov Web (Siminov.js) in your view.

***

![Siminov Web Sample Application] (https://raw.github.com/Siminov/android-web/docs/github/v1.0/siminov_web_sample_application_include_siminov_js_to_application_view.png)

***

* Invoke Siminov Initialization on device ready event.

```javascript
<script>
       document.addEventListener("deviceready", Siminov.initialize, false);
</script>
````

* Add Cordova Jar into your application libs folder.

***

![Siminov Web Sample Application] (https://raw.github.com/Siminov/android-web/docs/github/v1.0/siminov_web_sample_application_add_cordova_jar.png)

***

* Include Coredova JS file Into Your Project

***

![Web Sample Application] (https://raw.github.com/Siminov/android-web/docs/github/v1.0/siminov_web_sample_application_include_cordova_into_project.png)

***




**iOS Sample:** Adding Web build file into app

***

![Extract Siminov Build] (https://raw.github.com/Siminov/ios-web/docs/github/v1.0/siminov_web_build_extract.png)

***

***

![After Extracting Siminov Web Build] (https://raw.github.com/Siminov/ios-web/docs/github/v1.0/siminov_web_build_extracted.png)

***

* Add both **Native** and **Web** jar's into your application libs folder.

***

![After Extracting Siminov Web Build] (https://raw.github.com/Siminov/ios-web/docs/github/v1.0/siminov_web_sample_application_add_siminov_jars.png)

***

* Add Siminov Web (Siminov.js) to your assets folder.

***

![Siminov Web Sample Application] (https://raw.github.com/Siminov/ios-web/docs/github/v1.0/siminov_web_sample_application_add_siminov_js_to_application_assets.png)

***

* Include Siminov Web (Siminov.js) in your view.

***

![Siminov Web Sample Application] (https://raw.github.com/Siminov/ios-web/docs/github/v1.0/siminov_web_sample_application_include_siminov_js_to_application_view.png)

***

* Invoke Siminov Initialization on device ready event.

```javascript
<script>
       document.addEventListener("deviceready", Siminov.initialize, false);
</script>
````

* Add Cordova Jar into your application libs folder.

***

![Siminov Web Sample Application] (https://raw.github.com/Siminov/ios-web/docs/github/v1.0/siminov_web_sample_application_add_cordova_jar.png)

***

* Include Coredova JS file Into Your Project

***

![Web Sample Application] (https://raw.github.com/Siminov/ios-web/docs/github/v1.0/siminov_web_sample_application_include_cordova_into_project.png)

***


