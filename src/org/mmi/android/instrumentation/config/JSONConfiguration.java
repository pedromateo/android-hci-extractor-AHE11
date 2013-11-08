package org.mmi.android.instrumentation.config;

import java.io.InputStream;

import org.json.JSONException;
import org.json.JSONObject;


import android.util.Log;

public class JSONConfiguration {

	private static final String TAG = "JSONConfiguration";

	public JSONObject _object;

	public JSONConfiguration(InputStream filestream){
		_object = _loadFile(filestream);
		if (_object == null)
			Log.e(TAG,"(JSONConfiguration) ERROR: Configuration JSON object is null. Filestream = " 
					+ filestream);
	}

	/**
	 * Loads a configuration file written in JSON
	 * @param filepath the file
	 * @return the object, or null if error
	 */
	protected JSONObject _loadFile(InputStream filestream){
		///
		/// open questionnaire file
		if (filestream == null){
			Log.e(TAG, "ERROR: File stream cannot be read, is null.");
			return null;
		}

		///
		/// open and parse the JSON file
		String jString = "";
		JSONObject jObject = null;

		try{
			// open and read file
			try {
				byte[] b = new byte[filestream.available()];
				filestream.read(b);
				jString = new String(b);
			}
			finally {
				filestream.close();
			}
			// create json object
			jObject = new JSONObject(jString); 

		} 
		catch (JSONException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		if (jObject == null){
			Log.e(TAG, "ERROR: No JSON object could be parsed.");
		}

		return jObject;
	}

	/**
	 * Creates a JSONObject from a JSON string
	 * @param s
	 * @return
	 */
	public static JSONObject parseJSONString(String s){
		try{
			// return json object
			return new JSONObject(s); 
		} 
		catch (JSONException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
