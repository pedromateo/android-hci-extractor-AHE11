package org.mmi.android.instrumentation.filters.context;

import org.mmi.android.instrumentation.ContextDescription;
import org.mmi.android.instrumentation.InstrumentationContext;
import org.mmi.android.instrumentation.MmiFacade;
import org.mmi.android.instrumentation.OptionsMenuObserver;
import org.mmi.android.instrumentation.config.InstrumentationConfig;
import org.mmi.android.instrumentation.filters.ContextBaseFilter;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;


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
public class ContextPhysicalFilter extends ContextBaseFilter
implements SensorEventListener {

	private static final String TAG = "ContextPhysicalFilter";

	/**
	 * Constructor
	 * @param f facade to interact with the Multimodal Model Framework
	 */
	public ContextPhysicalFilter(InstrumentationContext ic, MmiFacade f, InstrumentationConfig cfc){
		super(ic,f,cfc);

		_physicalEnvironment = new ContextDescription.PhysicalEnvironment();
	}

	/// variables
	protected ContextDescription.PhysicalEnvironment _physicalEnvironment;

	///
	///
	/// sensors functionality
	///
	///
	///

	private SensorManager _sensorManager;
	private static boolean _sensorsInitialized = false;

	///
	/// proxy listener func

	private SensorEventListener _proxiedSensorEventListener;

	///
	/// variables

	///
	/// init and stop methods

	private void _startSensorListener(){
		if (!_sensorsInitialized) {
			_sensorManager = (SensorManager) 
					_icontext.getMainActivity().getBaseContext().getSystemService(
							Activity.SENSOR_SERVICE);
			_sensorManager.registerListener(this, 
					_sensorManager.getDefaultSensor(Sensor.TYPE_TEMPERATURE), 
					_sensorManager.SENSOR_DELAY_UI);
			_sensorManager.registerListener(this, 
					_sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT), 
					_sensorManager.SENSOR_DELAY_UI);

			// get proxy listener
			//_proxiedSensorEventListener = ListenerExtractor_16.getOnTouchListener(v);
			//if (_proxiedSensorEventListener != null) {
			//	onTouchListeners.put(v, onTouchListener);
			//}
			//v.setOnTouchListener(this);

			// init variables here

			_sensorsInitialized = true;
		}
	}

	private void _stopSensorListener(){
		if (_sensorsInitialized) {
			if (_sensorManager == null) 
				_sensorManager = (SensorManager) 
				_icontext.getMainActivity().getBaseContext().getSystemService(
						Activity.SENSOR_SERVICE);

			_sensorManager.unregisterListener(this,
					_sensorManager.getDefaultSensor(Sensor.TYPE_TEMPERATURE));
			_sensorManager.unregisterListener(this,
					_sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT));
			_sensorsInitialized = false;
		}
	}


	///
	/// sensors changes

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		///
		/// do the proxyed work
		///
		if (_proxiedSensorEventListener != null)
			_proxiedSensorEventListener.onAccuracyChanged(arg0, arg1);
	}


	@Override
	public void onSensorChanged(SensorEvent event) {
		///
		/// temperature
		///
		//if (event.sensor.getType()==Sensor.TYPE_AMBIENT_TEMPERATURE) //API 14 FIXME
		if (event.sensor.getType()==Sensor.TYPE_TEMPERATURE){

			// Sensor.TYPE_AMBIENT_TEMPERATURE:
			// values[0]: ambient (room) temperature in degree Celsius. 

			_physicalEnvironment.temperature = (int)event.values[0];
			Log.i(TAG,"@temperature value change: " + _physicalEnvironment.temperature);
		}
		///
		/// light
		///
		else if (event.sensor.getType()==Sensor.TYPE_LIGHT){

			// Sensor.TYPE_LIGHT:
			// values[0]: Ambient light level in SI lux units 
			
			_physicalEnvironment.light = (int)event.values[0];
			Log.i(TAG,"@light value change: " + _physicalEnvironment.light);
		}

		///
		/// do the proxyed work
		///
		if (_proxiedSensorEventListener != null)
			_proxiedSensorEventListener.onSensorChanged(event);

	}





	///
	///
	///
	///
	///
	///



	///
	/// ContextBaseFilter interface implementation
	///

	/// update coarse
	@Override
	public void updateValues_coarse() {

		Log.i(TAG, "(ContextPhysicalFilter::updateValues_coarse)");

		_updateTemperature();
		_updateWeather();
		_updateNoise();
		_updateLight();

		// send data to the facade
		// data have to be updated only in one method (using the finest)
		_mmifacade.physicalContextChange(0,
				_physicalEnvironment.temperature, 
				_physicalEnvironment.weather, 
				_physicalEnvironment.noise, 
				_physicalEnvironment.light);
	}

	/// update fine
	@Override
	public void updateValues_fine() {
		//Log.e(TAG, "(ContextPhysicalFilter::updateValues_fine)");
	}

	/// update finest
	@Override
	public void updateValues_finest() {
		//Log.e(TAG, "(ContextPhysicalFilter::updateValues_finest)");
	}

	/// update once
	@Override
	public void updateValues_once() {
	}

	@Override
	public void pushContextValues(ContextDescription context) {
		_physicalEnvironment.temperature = context.physicalEnvironment.temperature;
		_physicalEnvironment.weather = context.physicalEnvironment.weather;
		_physicalEnvironment.noise = context.physicalEnvironment.noise;
		_physicalEnvironment.light = context.physicalEnvironment.light;
	}

	///
	/// internal update methods
	///

	private void _updateTemperature(){
		// TODO
	}

	private void _updateWeather(){
		// TODO
	}

	private void _updateNoise(){
		// TODO
	}

	private void _updateLight(){
		// TODO
	}

	///
	/// InteractionListener interface implementation
	///

	@Override
	public void onInteractionInitialize() {
		// do previous configuration here
	}

	@Override
	public void onInteractionStart() {
		// start listener
		_startSensorListener();
	}

	@Override
	public void onInteractionEnd() {
		// stop listener
		_stopSensorListener();
	}
}
