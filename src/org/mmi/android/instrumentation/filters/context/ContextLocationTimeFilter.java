package org.mmi.android.instrumentation.filters.context;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.mmi.android.instrumentation.ContextDescription;
import org.mmi.android.instrumentation.InstrumentationContext;
import org.mmi.android.instrumentation.InstrumentationException;
import org.mmi.android.instrumentation.MmiFacade;
import org.mmi.android.instrumentation.OptionsMenuObserver;
import org.mmi.android.instrumentation.config.InstrumentationConfig;
import org.mmi.android.instrumentation.filters.ContextBaseFilter;
import org.mmi.android.instrumentation.utils.LocationUtils;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.GeolocationPermissions;


/**
 * This filter extracts physical context information like:
 *  - temperature
 *  - weather
 *  - noise
 *  - light
 * 
 * @author mateo-navarro.pedro
 *
 */
public class ContextLocationTimeFilter extends ContextBaseFilter {

	private static final String TAG = "ContextLocationTimeFilter";

	protected ContextDescription.LocationTime _locationTimeContext;
	private String _zipcode = "";
	LocationListener _locationListener;

	/**
	 * Constructor
	 * @param f facade to interact with the Multimodal Model Framework
	 */
	public ContextLocationTimeFilter(InstrumentationContext ic, MmiFacade f, InstrumentationConfig cfc){
		super(ic,f,cfc);

		_locationTimeContext = new ContextDescription.LocationTime();
	}

	///
	/// initialization and update methods
	///

	protected void _initialize() 
			throws InstrumentationException{

		boolean error = false;
		String error_msg = "";

		///
		/// init variables


		// we will be able to initialize location data if the main activity
		// is already registered

		if (_icontext.mainActivity == null){
			error = true;
			error_msg = "Main activity is null.";
		}
		else{

			///
			/// check location providers

			LocationUtils.initializeLocationUtils(_icontext.mainActivity);

			if (!LocationUtils.isGPSProviderEnabled()) 
				Log.e(TAG, "ERROR: GPS provider disabled.");
			if (!LocationUtils.isNetworkProviderEnabled()) 
				Log.e(TAG, "ERROR: Network provider disabled.");


			// if providers enabled, enable listener for location changes
			if (LocationUtils.isGPSProviderEnabled() || 
					LocationUtils.isNetworkProviderEnabled()){

				String providerName = LocationUtils.getBestLocationProvider(Criteria.ACCURACY_COARSE);
				if (providerName.equals("")){
					error = true;
					error_msg = "No Location provider available.";
				}
				else{

					// create a location listener
					_locationListener = new LocationListener() {

						public void onLocationChanged(Location location) {
							// update location

							_locationTimeContext.geoLocation = location;

							// update zipcode
							_zipcode = LocationUtils.getCurrentZipCode(_locationTimeContext.geoLocation);

							// Called when a new location is found by the network location provider.
							Log.e(TAG, "(LocationListener) onLocationChanged");
							Log.e(TAG, "(LocationListener) Location is: :" + location.toString());
						}

						public void onStatusChanged(String provider, int status, Bundle extras) {
							Log.e(TAG, "(LocationListener) onStatusChanged");
						}

						public void onProviderEnabled(String provider) {
							Log.e(TAG, "(LocationListener) onProviderEnabled");
						}

						public void onProviderDisabled(String provider) {
							Log.e(TAG, "(LocationListener) onProviderDisabled");
						}
					};

					final long minTime = 5 * 60 * 1000; // ms
					final float minDistance = 20; // meters
					LocationUtils.getLocationManager().requestLocationUpdates(providerName, 
							minTime, minDistance, _locationListener);
				}
			}
			else{
				error = true;
				error_msg = "Neither GPS nor Network provider available";

				// set default values for geoLocation updates
				_locationTimeContext.geoLocation = new Location("");
			}
		}


		/// error case -> disable thread
		if (error){
			throw new InstrumentationException("(ContextLocationTimeFilter::_initialize) " + error_msg);
		}
	}

	///
	/// ContextBaseFilter interface implementation
	///

	/// update coarse
	@Override
	public void updateValues_coarse() {
		//_updateLocation();

		// send data through facade
		// data have to be updated only in one method (using the finest)
		_mmifacade.locationContextChange(0,
				_locationTimeContext.userLocation, 
				_locationTimeContext.geoLocation, 
				_locationTimeContext.mobilityLevel);
	}

	/// update fine
	@Override
	public void updateValues_fine() {
	}

	/// update finest
	@Override
	public void updateValues_finest() {
		Log.i(TAG, "(ContextLocationTimeFilter::updateValues_finest)");
		_updateTime();

		// send data through facade
		// data have to be updated only in one method (using the finest)
		_mmifacade.timeContextChange(0,
				_locationTimeContext.currentTime);
	}

	/// update once
	@Override
	public void updateValues_once() {
		// send data collected from initial questionnaire
		_mmifacade.locationContextChange(0,
				_locationTimeContext.userLocation, 
				_locationTimeContext.geoLocation, 
				_locationTimeContext.mobilityLevel);
	}


	@Override
	public void pushContextValues(ContextDescription context) {
		_locationTimeContext.userLocation = context.locationTime.userLocation;
		_locationTimeContext.geoLocation = context.locationTime.geoLocation;
		_locationTimeContext.mobilityLevel = context.locationTime.mobilityLevel;
		_locationTimeContext.currentTime = context.locationTime.currentTime;
	}

	///
	/// internal update methods
	///

	private void _updateTime(){
		// update current time
		_locationTimeContext.currentTime = Calendar.getInstance();
	}

	private void _updateLocation(){

		// Location is updated using listeners. 
		// Listeners where added at initialization		
	}



	///
	/// InteractionListener interface implementation
	///

	@Override
	public void onInteractionInitialize() {
		// do previous configuration here
		try {
			_initialize();
		} catch (InstrumentationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e(TAG, "ERROR: LocationManager cannot be initialized. Location will not be updated.");
		}
	}

	@Override
	public void onInteractionEnd(){
		super.onInteractionEnd();
		// remove location listener
		if (_locationListener != null)
			LocationUtils.getLocationManager().removeUpdates(_locationListener);
		// stop LocationUtils
		LocationUtils.finalizeLocationUtils();
	}
}
