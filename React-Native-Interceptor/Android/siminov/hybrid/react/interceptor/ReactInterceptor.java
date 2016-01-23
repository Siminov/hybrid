package siminov.hybrid.interceptor;

import siminov.core.log.Log;
import siminov.hybrid.adapter.AdapterHandler;
import siminov.hybrid.adapter.IHandler;
import siminov.hybrid.resource.ResourceManager;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;


public class ReactInterceptor extends ReactContextBaseJavaModule implements IHandler {

    private static final String REACT_INTERCEPTOR = "ReactInterceptor";

    private static AdapterHandler adapterHandler = AdapterHandler.getInstance();
    private IHandler handler = adapterHandler.getHandler();

    private ResourceManager resourceManager = ResourceManager.getInstance();

    public ReactInterceptor(ReactApplicationContext reactContext) {
        super(reactContext);

        resourceManager.setInterceptor(this);
    }


    @Override
    public String getName() {
        return REACT_INTERCEPTOR;
    }

    public String handleHybridToNative(final String action) {
        return "";
    }

    @ReactMethod
    public String handleHybridToNative(final String action, final String data) {
        return "";
    }

    @Override
    public String handleHybridToNativeAsync(String requestId, String action, String data) {
        return null;
    }

    @ReactMethod
    public void handleHybridToNativeAsync(String protocol, String requestId, String action, String data) {

        if(data != null) {
            data = data = URLDecoder.decode(data);
        }

        handler.handleHybridToNativeAsync(requestId, action, data);
    }

    public void handleNativeToHybrid(final String action, final String...data) {

    }


    public void handleNativeToHybridAsync(String requestId, final String...data) {

    }

    public void handleNativeToHybrid(String functionName, String apiName, String action, String parameters) {

        WritableMap params = Arguments.createMap();
        params.putString("api", apiName);
        params.putString("action", action);
        params.putString("parameters", parameters);

        ReactApplicationContext reactContext = getReactApplicationContext();
        reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(functionName, params);
    }
}
