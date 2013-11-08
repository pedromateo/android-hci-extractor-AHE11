package org.mmi.android.instrumentation.utils;

import java.io.IOException;
import java.io.RandomAccessFile;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.graphics.Point;
import android.media.AudioManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.WindowManager;


public class DeviceUtils {

	private static final String TAG = "DeviceUtils";

	/// 
	/// static variables
	private static WindowManager _windowManager;
	private static Display _display;
	private static AudioManager _audioManager;
	private static ActivityManager _activityManager;


	///
	/// static methods

	public static Point getScreenResolution_px(Context context) {
		if (_display == null){
			_windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
			_display = _windowManager.getDefaultDisplay();
		}
		Point size = new Point();
		_display.getSize(size);

		return size;    
	}

	public static double getScreenSize_inch(Context context) {
		if (_display == null){
			_windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
			_display = _windowManager.getDefaultDisplay();
		}

		Point size = new Point();
		DisplayMetrics dm = new DisplayMetrics();
		_display.getMetrics(dm);
		size.set( (int)Math.pow(dm.widthPixels/dm.xdpi,2),
				(int)Math.pow(dm.heightPixels/dm.ydpi,2));
		double screenInches = Math.sqrt(size.x + size.y);
		return screenInches;
		// FIXME check whether it works out of the simulator!
	}

	public static boolean isScreenOrientationLandscape(Context context){
		Point p = getScreenResolution_px(context);
		if (p.x > p.y) 
			return true;
		return false;

		// FIXME check whether it works out of the simulator!

		//if (_display == null){
		//	_windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		//	_display = _windowManager.getDefaultDisplay();
		//}
		//return _display.getRotation() == Surface.ROTATION_90 || 
		//		_display.getRotation() == Surface.ROTATION_270;
	}

	public static int getScreenBrightnessLevel(Context context){

		try {
			float curBrightnessValue = android.provider.Settings.System.getInt(
					context.getContentResolver(), android.provider.Settings.System.SCREEN_BRIGHTNESS);
			// value goes from 0 to 255; we'll return a percentage
			return (int)(curBrightnessValue*100/255);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}

	public static int getVolumeLevel(Context context){

		if (_audioManager == null)
			_audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);

		// STREAM_ALARM 	The audio stream for alarms
		// STREAM_DTMF 	The audio stream for DTMF Tones
		// STREAM_MUSIC 	The audio stream for music playback
		// STREAM_NOTIFICATION 	The audio stream for notifications
		// STREAM_RING 	The audio stream for the phone ring
		// STREAM_SYSTEM 	The audio stream for system sounds
		// STREAM_VOICE_CALL 	The audio stream for phone calls
		// USE_DEFAULT_STREAM_TYPE Suggests using the default stream type.

		//Log.e(TAG,Integer.toString(_audioManager.getStreamVolume(AudioManager.STREAM_ALARM)));
		//Log.e(TAG,Integer.toString(_audioManager.getStreamVolume(AudioManager.STREAM_DTMF)));
		//Log.e(TAG,Integer.toString(_audioManager.getStreamVolume(AudioManager.STREAM_MUSIC))); -> 15 max
		//Log.e(TAG,Integer.toString(_audioManager.getStreamVolume(AudioManager.STREAM_NOTIFICATION))); -> 7 max
		//Log.e(TAG,Integer.toString(_audioManager.getStreamVolume(AudioManager.STREAM_RING)));
		//Log.e(TAG,Integer.toString(_audioManager.getStreamVolume(AudioManager.STREAM_SYSTEM)));
		//Log.e(TAG,Integer.toString(_audioManager.getStreamVolume(AudioManager.STREAM_VOICE_CALL)));

		// FIXME we return music and media volume level (max value: 15)

		return (int)(_audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) * 100 / 15);
	}

	public static int getMemoryUsage(Context context){
		
		if (_activityManager == null)
			_activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		
		MemoryInfo mi = new MemoryInfo();
		_activityManager.getMemoryInfo(mi);
		long totalMB = mi.totalMem / 1048576L;
		long availMB = mi.availMem / 1048576L;
		long usedMB = totalMB - availMB;

		return (int)(usedMB * 100 / totalMB);
	}

	public static int getCpuUsage(Context context){

		try {
			RandomAccessFile reader = new RandomAccessFile("/proc/stat", "r");
			String load = reader.readLine();

			// cpu  127262 1391 77990 17087296 5445 0 1223 0 0 0

			String[] toks = load.split(" ");

			long idle1 = Long.parseLong(toks[5]);
			long cpu1 = Long.parseLong(toks[2]) + Long.parseLong(toks[3]) + Long.parseLong(toks[4])
					+ Long.parseLong(toks[6]) + Long.parseLong(toks[7]) + Long.parseLong(toks[8]);
			

			Log.d(TAG, "line1 -- " + load);
			Log.d(TAG, "idle1 -- " + idle1);
			Log.d(TAG, "cpu1 -- " + cpu1);

			try {
				Thread.sleep(360);
			} catch (Exception e) {}

			reader.seek(0);
			load = reader.readLine();
			reader.close();

			toks = load.split(" ");

			long idle2 = Long.parseLong(toks[5]);
			long cpu2 = Long.parseLong(toks[2]) + Long.parseLong(toks[3]) + Long.parseLong(toks[4])
					+ Long.parseLong(toks[6]) + Long.parseLong(toks[7]) + Long.parseLong(toks[8]);
			

			Log.d(TAG, "line2 -- " + load);
			Log.d(TAG, "idle2 -- " + idle2);
			Log.d(TAG, "cpu2 -- " + cpu2);

			Log.d(TAG, "usage -- " + Long.toString((cpu2 - cpu1) * 100 / ((cpu2 + idle2) - (cpu1 + idle1))));
			
			long usage = cpu2 - cpu1;
			long total = (cpu2 + idle2) - (cpu1 + idle1);

			return (int)(usage*100/total);

		} catch (IOException ex) {
			ex.printStackTrace();
			return 0;
		}

	}
}
