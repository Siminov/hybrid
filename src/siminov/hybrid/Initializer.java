package siminov.hybrid;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import siminov.orm.IInitializer;
import siminov.orm.resource.Resources;
import android.app.Activity;
import android.content.Context;
import android.webkit.WebView;

public class Initializer implements IInitializer {

	private Resources ormResources = Resources.getInstance();
	private siminov.hybrid.resource.Resources hybridResources = siminov.hybrid.resource.Resources.getInstance(); 
	
	private List<Object> parameters = new ArrayList<Object> ();
	
	public void addParameter(Object object) {
		parameters.add(object);
	}
	
	public void start() {
		
		Context context = null;
		WebView webView = null;
		Activity activity = null;
		
		
		Iterator<Object> iterator = parameters.iterator();
		while(iterator.hasNext()) {
			
			Object object = iterator.next();
			if(object instanceof Activity) {
				activity = (Activity) object;
			} else if(object instanceof Context) {
				context = (Context) object;
			} else if(object instanceof WebView) {
				webView = (WebView) object;
			} 
			
		}
		
		ormResources.setApplicationContext(context);

		hybridResources.setWebView(webView);
		hybridResources.setWebActivity(activity);
		
		Siminov.start();
		
	}

}
