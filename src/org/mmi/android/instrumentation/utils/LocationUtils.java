package org.mmi.android.instrumentation.utils;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;

public class LocationUtils {

	private static final String TAG = "LocationUtils";

	/// static variables

	private static boolean _initialized = false;
	private static LocationManager _locationManager;
	private static Location _location;
	//private static LocationProvider _locationProvider;
	//private static String _locationProviderName = "";
	private static Geocoder _geocoder;
	
	///
	/// init/finish methods
	///

	public static void initializeLocationUtils(Activity main_activity){

		// initialize variables
		_locationManager = 	(LocationManager) main_activity.getSystemService(Context.LOCATION_SERVICE);
		_location = null;
		_geocoder = new Geocoder(main_activity, Locale.getDefault());
		_initialized = true;
	}

	public static void finalizeLocationUtils(){
		_locationManager = null;
		_location = null;
		_geocoder = null;
		_initialized = false;
	}
	
	///
	/// accessor methods
	///
	
	public static LocationManager getLocationManager(){
		if (!_initialized){
			Log.e(TAG, "ERROR: LocationUtils must be initialized first.");
			return null;
		}
		return _locationManager;
	}

	///
	/// checking methods
	///
	
	public static boolean isGPSProviderEnabled(){
		if (!_initialized){
			Log.e(TAG, "ERROR: LocationUtils must be initialized first.");
			return false;
		}
		return _locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
	}

	public static boolean isNetworkProviderEnabled(){
		if (!_initialized){
			Log.e(TAG, "ERROR: LocationUtils must be initialized first.");
			return false;
		}
		return _locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
	}


	///
	/// location methods
	///
	
	public static String getBestLocationProvider(int criteria){ 
		if (!_initialized){
			Log.e(TAG, "ERROR: LocationUtils must be initialized first.");
			return "";
		}

		Criteria locationCriteria = new Criteria();
		locationCriteria.setAccuracy(criteria);
		return _locationManager.getBestProvider(locationCriteria, true);
	}


	public static Location getCurrentLocation(){
		if (!_initialized){
			Log.e(TAG, "ERROR: LocationUtils must be initialized first.");
			return null;
		}

		Location result;

		///
		/// option 1: try to get current location


		///
		/// option 2: try with last known location
		if ((result = _locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)) == null){
			if ((result = _locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)) == null){
				if ((result = _locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER)) == null){
					Log.w(TAG,"(getCurrentLocation) No last known location available.");
				}
				else return result;
			}
			else return result;
		}
		else return result;

		///
		/// default option: use default location
		if (result == null){
			result = new Location(LocationManager.PASSIVE_PROVIDER);
			//the lab: 38.020357,-1.170863
			result.setLatitude(38.020357);
			result.setLongitude(-1.170863);
			Log.w(TAG,"(getCurrentLocation) Location set to default.");
		}

		return result;
	}

	/**
	 * Returns current zipcode if geocoder different than null
	 * @return zipcode if it exists or empty string if problem found
	 */
	public static String getCurrentZipCode(Location location){
		if (!_initialized){
			Log.e(TAG, "ERROR: LocationUtils must be initialized first.");
			return "";
		}
		
		if (location == null){
			Log.e(TAG, "ERROR: A valid location is needed to get current zipcode.");
			return "";
		}

		Log.d(TAG, "getting zipcode...");
		List<Address> addresses = null;
		try {
			addresses = _geocoder.getFromLocation(location.getLatitude(), 
					location.getLongitude(), 5);
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		for (int i = 0; i < addresses.size(); i++) {
			Log.d(TAG, "trying address");
			if (addresses.get(i).getPostalCode() != null){
				return addresses.get(i).getPostalCode();
			}
		}

		return "";
	}

}
