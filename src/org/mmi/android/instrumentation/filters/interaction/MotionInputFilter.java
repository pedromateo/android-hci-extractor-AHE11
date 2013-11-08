package org.mmi.android.instrumentation.filters.interaction;

import java.util.Map;

import org.mmi.android.instrumentation.InstrumentationContext;
import org.mmi.android.instrumentation.MmiFacade;
import org.mmi.android.instrumentation.OptionsMenuObserver;
import org.mmi.android.instrumentation.filters.BaseFilter;
import org.mmi.android.instrumentation.viewproxies.listeners.ListenerExtractor_16;

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
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;




/**
 * Instrumentation filter for user mouse and navigation input.
 * 
 * @author mateo-navarro.pedro
 *
 */
public class MotionInputFilter 
extends BaseFilter
implements SensorEventListener

{

	private static final String TAG = MotionInputFilter.class.getSimpleName();




	/**
	 * Constructor
	 */
	public MotionInputFilter(InstrumentationContext ic, MmiFacade f){
		super(ic, f);
	}


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

	private float x,y,z, lasty = 0; 
	private float _mAccel, _mAccelCurrent, _mAccelLast = 0;
	private final double TILT_FACTOR = 0.8;
	private final double SHAKE_FACTOR = 0.8;

	///
	/// init and stop methods

	private void _startSensorListener(){
		if (!_sensorsInitialized) {
			_sensorManager = (SensorManager) 
					_icontext.getMainActivity().getBaseContext().getSystemService(
							Activity.SENSOR_SERVICE);
			_sensorManager.registerListener(this, 
					_sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), 
					_sensorManager.SENSOR_DELAY_UI);

			// get proxy listener
			//_proxiedSensorEventListener = ListenerExtractor_16.getOnTouchListener(v);
			//if (_proxiedSensorEventListener != null) {
			//	onTouchListeners.put(v, onTouchListener);
			//}
			//v.setOnTouchListener(this);

			/// init variables
			_mAccel = 0.00f;
			_mAccelCurrent = SensorManager.GRAVITY_EARTH;
			_mAccelLast = SensorManager.GRAVITY_EARTH;


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
					_sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
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
		if (event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
			x=event.values[0];
			y=event.values[1];
			z=event.values[2];

			Log.e(TAG,"(x,y,z) (" + x + "," + y + "," + z + ")");

			///
			/// tilt

			if (x > lasty + TILT_FACTOR){
				// move right
				_mmifacade.tilt(0);
			}
			else if (x < lasty - TILT_FACTOR){
				// move left
				_mmifacade.tilt(0);
			}

			///
			/// shake

			_mAccelLast = _mAccelCurrent;
			_mAccelCurrent = (float) Math.sqrt((double) (x*x + y*y + z*z));
			float delta = _mAccelCurrent - _mAccelLast;
			_mAccel = _mAccel * 0.9f + delta; // perform low-cut filter
			if (_mAccel > SHAKE_FACTOR)
				_mmifacade.shake(0);

			///
			/// variables update
			lasty = x;

			/*
			// In this example, alpha is calculated as t / (t + dT),
			  // where t is the low-pass filter's time-constant and
			  // dT is the event delivery rate.

			  final float alpha = 0.8;

			  // Isolate the force of gravity with the low-pass filter.
			  gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
			  gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
			  gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];

			  // Remove the gravity contribution with the high-pass filter.
			  linear_acceleration[0] = event.values[0] - gravity[0];
			  linear_acceleration[1] = event.values[1] - gravity[1];
			  linear_acceleration[2] = event.values[2] - gravity[2];
			 */
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

	///
	/// BaseFilter abstract methods implementation
	///

	@Override
	public void install(View v) {
		// this filter is not installed in views
	}

	private void installOnNonAdapterView(View v) {
		//Log.d(TAG,"View :: Installing listeners");
	}

	private void installOnAdapterView(AdapterView<?> av) {
		//Log.d(TAG,"AdapterView :: Installing listeners");
	}

	@Override
	public void install(ActionBar actionBar) {
		// nothing
	}

	@Override
	public void install(MenuItem item, View parentView) {
		//Log.d(TAG, "Install onMenuItemClickListener");
	}

	@Override
	public void install(AlertDialog dialog) {
	}

	@Override
	public void install(OptionsMenuObserver observer) {
	}

}

