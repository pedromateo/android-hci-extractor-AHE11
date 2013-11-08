package org.mmi.android.instrumentation.filters.context;

import org.mmi.android.instrumentation.ContextDescription;
import org.mmi.android.instrumentation.ContextDescription.Device.ScreenOrientation;
import org.mmi.android.instrumentation.ContextDescription.Device.ScreenResolution;
import org.mmi.android.instrumentation.ContextDescription.Device.ScreenSize;
import org.mmi.android.instrumentation.InstrumentationContext;
import org.mmi.android.instrumentation.MmiFacade;
import org.mmi.android.instrumentation.config.InstrumentationConfig;
import org.mmi.android.instrumentation.filters.ContextBaseFilter;
import org.mmi.android.instrumentation.utils.DeviceUtils;

import android.graphics.Point;
import android.util.Log;


public class ContextDeviceFilter extends ContextBaseFilter {

	private static final String TAG = "ContextDeviceFilter";

	protected ContextDescription.Device _deviceContext;

	public ContextDeviceFilter(InstrumentationContext ic, MmiFacade f,
			InstrumentationConfig cfc) {
		super(ic, f, cfc);

		_deviceContext = new ContextDescription.Device();
	}

	@Override
	public void updateValues_coarse() {
		_updateOrientation();
		_updateBrightness();
		_updateVolume();
	}

	@Override
	public void updateValues_fine() {
	}

	@Override
	public void updateValues_finest() {
		Log.i(TAG, "(ContextDeviceFilter::updateValues_finest)");

		_updateMemoryUsage();
		_updateCpuUsage();

		// send data through facade
		// data have to be updated only in one method (using the finest)
		_mmifacade.deviceContextChange(0,
				_deviceContext.deviceType, 
				_deviceContext.screenSize, _deviceContext.screenResolution, 
				_deviceContext.screenOrientation, _deviceContext.screenBrightnessLevel, 
				_deviceContext.volumeLevel, _deviceContext.memoryLoad, _deviceContext.cpuLoad);
	}

	@Override
	public void updateValues_once() {
		Log.i(TAG, "(ContextDeviceFilter::updateValues_once)");

		_updateSizeResolution();
		_updateOrientation();
		_updateBrightness();
		_updateVolume();
		_updateMemoryUsage();
		_updateCpuUsage();

		// send data through facade
		_mmifacade.deviceContextChange(0,
				_deviceContext.deviceType, 
				_deviceContext.screenSize, _deviceContext.screenResolution, 
				_deviceContext.screenOrientation, _deviceContext.screenBrightnessLevel, 
				_deviceContext.volumeLevel, _deviceContext.memoryLoad, _deviceContext.cpuLoad);
	}

	@Override
	public void pushContextValues(ContextDescription context) {
		_deviceContext.deviceType = context.device.deviceType; 
		_deviceContext.screenSize = context.device.screenSize;
		_deviceContext.screenResolution = context.device.screenResolution;
		_deviceContext.screenOrientation = context.device.screenOrientation; 
		_deviceContext.screenBrightnessLevel = context.device.screenBrightnessLevel;
		_deviceContext.volumeLevel = context.device.volumeLevel;
		_deviceContext.memoryLoad = context.device.memoryLoad;
		_deviceContext.cpuLoad = context.device.cpuLoad;
	}

	@Override
	public void onInteractionInitialize() {
	}

	///
	/// internal update methods
	///

	final double SMALL_SSIZE = 4;
	final double LARGE_SSIZE = 10;
	final Point SMALL_SRESOLUTION = new Point(480,800);
	final Point LARGE_SRESOLUTION = new Point(1280,800); 

	private void _updateSizeResolution(){
		// screen size
		double size = DeviceUtils.getScreenSize_inch(_icontext.mainActivity);
		if (size <= SMALL_SSIZE)
			_deviceContext.screenSize = ScreenSize.SCREEN_SIZE_SMALL;
		else if (size >= LARGE_SSIZE)
			_deviceContext.screenSize = ScreenSize.SCREEN_SIZE_LARGE;
		else
			_deviceContext.screenSize = ScreenSize.SCREEN_SIZE_NORMAL;

		// screen resolution
		Point reso = DeviceUtils.getScreenResolution_px(_icontext.mainActivity);
		if (_comparePoints(reso, SMALL_SRESOLUTION) <= 0)
			_deviceContext.screenResolution = ScreenResolution.SCREEN_RESOLUTION_SMALL;
		else if (_comparePoints(reso, LARGE_SRESOLUTION) >= 0)
			_deviceContext.screenResolution = ScreenResolution.SCREEN_RESOLUTION_LARGE;
		else 
			_deviceContext.screenResolution = ScreenResolution.SCREEN_RESOLUTION_NORMAL;
	}

	private void _updateOrientation(){
		if (DeviceUtils.isScreenOrientationLandscape(_icontext.mainActivity))
			_deviceContext.screenOrientation = ScreenOrientation.ORIENTATION_LANDSCAPE;
		else 
			_deviceContext.screenOrientation = ScreenOrientation.ORIENTATION_PORTRAIT;
	}

	private void _updateBrightness(){
		_deviceContext.screenBrightnessLevel = 
				DeviceUtils.getScreenBrightnessLevel(_icontext.mainActivity);
	}

	private void _updateVolume(){
		_deviceContext.volumeLevel = 
				DeviceUtils.getVolumeLevel(_icontext.mainActivity);
	}

	private void _updateMemoryUsage(){
		_deviceContext.memoryLoad = 
				DeviceUtils.getMemoryUsage(_icontext.mainActivity);
	}

	private void _updateCpuUsage(){
		_deviceContext.cpuLoad = 
				DeviceUtils.getCpuUsage(_icontext.mainActivity);	
	}

	///
	/// supporting methods
	///

	private int _comparePoints(Point a, Point b){
		int ra = a.x * a.y;
		int rb = b.x * b.y;

		if (ra == rb)
			return 0;
		else if (ra < rb)
			return -1;
		else
			return 1;
	}
}
