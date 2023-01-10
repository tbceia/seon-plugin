package com.igh.cordova.plugin;
// The native APIs
import android.content.Context;
// Cordova-required packages
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
//Seon classes
import io.seon.androidsdk.service.Seon;
import io.seon.androidsdk.service.SeonBuilder;

public class SeonPlugin extends CordovaPlugin {

    private CallbackContext callbackContext;

    @Override
    public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) {
		
		this.callbackContext = callbackContext;

		if (!action.equals("getDeviceFingerprint")) {
			callbackContext.error("\"" + action + "\" is not a recognized action.");
			return false;
		}
			
		Context context = this.cordova.getActivity().getApplicationContext();

		final String sessionId;
		try {
			JSONObject options = args.getJSONObject(0);
			sessionId = options.getString("sessionId");
		} catch (JSONException e) {
			callbackContext.error("Error encountered: " + e.getMessage());
			return false;
		}

		if(sessionId.length() == 0 || sessionId == null){
			callbackContext.error("Error encountered: Session Id is mandatory");
			return false;
		}

		cordova.getThreadPool().execute(new Runnable() {
			public void run() {
				try {
					// Build with parameters
					Seon seonFingerprint = new SeonBuilder().withContext(context).withSessionId(sessionId).build();

					// Enable logging
					seonFingerprint.setLoggingEnabled(true);

					seonFingerprint.getFingerprintBase64(fingerprint->{
						//set seonFingerprint as the value for the session property of the fraud API request.
						fingerprintCallback(fingerprint);
					});
				} catch (Exception e) {
					e.printStackTrace();
				}

				//PluginResult pluginResult = new PluginResult(PluginResult.Status.OK, "init");
				//pluginResult.setKeepCallback(true);
				//callbackContext.sendPluginResult(pluginResult);
				//callbackContext.success(); // Thread-safe.
			}
		});

        return true;
    }

    private void fingerprintCallback(String base64String) {
        // Send a positive result to the callbackContext
        PluginResult pluginResult = new PluginResult(PluginResult.Status.OK, base64String);
        callbackContext.sendPluginResult(pluginResult);
    }
}
