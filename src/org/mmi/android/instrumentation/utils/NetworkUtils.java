package org.mmi.android.instrumentation.utils;

import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.util.Log;

public class NetworkUtils {


	private static final String TAG = "NetworkUtils";

	/// 
	/// static variables
	private static ConnectivityManager _connectivityMngr;

	/**
	 * Checks if the device has Internet connection.
	 * 
	 * @return <code>true</code> if the phone is connected to the Internet.
	 */
	public static boolean hasNetworkConnectivity(Context context) {
		if (_connectivityMngr == null) 
			_connectivityMngr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo wifiNetwork = _connectivityMngr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (wifiNetwork != null && wifiNetwork.isConnected()) {
			Log.d(TAG, "(hasNetworkConnectivity) Wifi network available.");
			return true;
		}

		NetworkInfo mobileNetwork = _connectivityMngr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if (mobileNetwork != null && mobileNetwork.isConnected()) {
			Log.d(TAG, "(hasNetworkConnectivity) Mobile network available.");
			return true;
		}

		NetworkInfo bluetoothNetwork = _connectivityMngr.getNetworkInfo(ConnectivityManager.TYPE_BLUETOOTH);
		if (bluetoothNetwork != null && bluetoothNetwork.isConnected()) {
			Log.d(TAG, "(hasNetworkConnectivity) Bluetooth network available.");
			return true;
		}

		NetworkInfo activeNetwork = _connectivityMngr.getActiveNetworkInfo();
		if (activeNetwork != null && activeNetwork.isConnected()) {
			Log.d(TAG, "(hasNetworkConnectivity) Network available: " + activeNetwork.getTypeName());
			return true;
		}

		return false;
	}


	public static int getNetworkAccessType(Context context) {
		if (_connectivityMngr == null) 
			_connectivityMngr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo activeNetwork = _connectivityMngr.getActiveNetworkInfo();
		return activeNetwork.getType();
	}


	public static String getAccessPointName(Context context) {
		// only if access type is WiFi
		if (getNetworkAccessType(context) == ConnectivityManager.TYPE_WIFI){
			WifiManager mWiFiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
			return mWiFiManager.getConnectionInfo().getSSID();
		}
		return "";
	}


	public static int getNetworkSignalStrength(Context context) {

		// only if access type is WiFi, MOBILE or Bluetooth
		int ctype = getNetworkAccessType(context);
		//
		//
		if (ctype == ConnectivityManager.TYPE_WIFI){
			WifiManager wmngr = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

			if (!wmngr.isWifiEnabled())
				return 0;

			// we calculate signal from (100% -> RSSI = 0) to 
			// (0% -> RSSI = -100)
			int rssi = wmngr.getConnectionInfo().getRssi();
			return _normalizeRssi(rssi);
		}
		//
		//
		else if (ctype == ConnectivityManager.TYPE_MOBILE){
			//TelephonyManager telManager  = ( TelephonyManager )context.getSystemService(Context.TELEPHONY_SERVICE);
			// TODO
			
			
			TelephonyManager telephonyManager = 
					(TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			
			PhoneStateListener signalListener = new PhoneStateListener() {
		        public void onSignalStrengthChanged(int asu) {
		            //asu is the signal strength
		        	Log.e(TAG,"@@@@@@@@@@@@@ asu = " + asu);
		        }
		    };

		    telephonyManager.listen(signalListener, PhoneStateListener.LISTEN_SIGNAL_STRENGTH);
			
			
			return 1;
		}
		//
		//
		else if (ctype == ConnectivityManager.TYPE_BLUETOOTH){
			//TODO
			return 1;
		}
		//
		//
		else
			return 0;
	}
	
	private static int _normalizeRssi(int rssi){
		// Anything worse than or equal to this will show 0 bars
		final int MIN_RSSI = -100;
		// Anything better than or equal to this will show the max bars.
		final int MAX_RSSI = -55;
		
		int range = MAX_RSSI - MIN_RSSI;
		return 100 - ((MAX_RSSI - rssi) * 100 / range);
	}


	public static int getReceivedDataThroughput_KB() {
		// TODO
		return 0;
	}


	public static int getSentDataThroughput_KB() {
		// TODO
		return 0;
	}


	public static int getRoundTripTime_ms() {
		// TODO
		return 0;
	}



	public static int getServerResponseTime_ms() {
		// TODO
		return 0;
	}

}
