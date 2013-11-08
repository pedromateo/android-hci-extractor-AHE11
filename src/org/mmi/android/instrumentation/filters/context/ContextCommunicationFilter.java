package org.mmi.android.instrumentation.filters.context;

import org.mmi.android.instrumentation.ContextDescription;
import org.mmi.android.instrumentation.filters.ContextBaseFilter;
import org.mmi.android.instrumentation.utils.NetworkUtils;
import org.mmi.android.instrumentation.ContextDescription.Communication.WirelessAccessType;
import org.mmi.android.instrumentation.InstrumentationContext;
import org.mmi.android.instrumentation.MmiFacade;
import org.mmi.android.instrumentation.config.InstrumentationConfig;

import android.net.ConnectivityManager;
import android.util.Log;

public class ContextCommunicationFilter extends ContextBaseFilter {

	private static final String TAG = "ContextCommunicationFilter";

	protected ContextDescription.Communication _commContext;

	public ContextCommunicationFilter(InstrumentationContext ic, MmiFacade f,
			InstrumentationConfig cfc) {
		super(ic, f, cfc);

		_commContext = new ContextDescription.Communication();
	}

	@Override
	public void updateValues_coarse() {
	}

	@Override
	public void updateValues_fine() {
		Log.i(TAG, "(ContextDeviceFilter::updateValues_fine)");

		_updateWirelessValues();
		_updateDataThroughput();
		_updateResponseTimes();

		// send data through facade
		// data have to be updated only in one method (using the finest)
		_mmifacade.communicationContextChange(0,
				_commContext.wirelessAccessType,
				_commContext.accessPointName,_commContext.signalStrength,
				_commContext.receivedDataThroughput,_commContext.sentDataThroughput,
				_commContext.rtt,_commContext.srt);
	}

	@Override
	public void updateValues_finest() {
	}

	@Override
	public void updateValues_once() {
	}

	@Override
	public void pushContextValues(ContextDescription context) {
		_commContext.wirelessAccessType = context.communication.wirelessAccessType;
		_commContext.accessPointName = context.communication.accessPointName;
		_commContext.signalStrength = context.communication.signalStrength;
		_commContext.receivedDataThroughput = context.communication.receivedDataThroughput;
		_commContext.sentDataThroughput = context.communication.sentDataThroughput;
		_commContext.rtt = context.communication.rtt;
		_commContext.srt = context.communication.srt;
	}

	@Override
	public void onInteractionInitialize() {
	}

	///
	/// internal update methods
	///

	private void _updateWirelessValues(){
		int ntype = NetworkUtils.getNetworkAccessType(_icontext.mainActivity);
		if (ntype == ConnectivityManager.TYPE_WIFI){
			_commContext.wirelessAccessType = WirelessAccessType.WIRELESS_ACCESS_TYPE_WIFI;
		}
		else if (ntype == ConnectivityManager.TYPE_MOBILE ||
				ntype == ConnectivityManager.TYPE_MOBILE_DUN ||
				ntype == ConnectivityManager.TYPE_MOBILE_HIPRI ||
				ntype == ConnectivityManager.TYPE_MOBILE_MMS ||
				ntype == ConnectivityManager.TYPE_MOBILE_SUPL){
			_commContext.wirelessAccessType = WirelessAccessType.WIRELESS_ACCESS_TYPE_MOBILE;
		}
		else if (ntype == ConnectivityManager.TYPE_BLUETOOTH){
			_commContext.wirelessAccessType = WirelessAccessType.WIRELESS_ACCESS_TYPE_BLUETOOTH;
		}
		else {
			_commContext.wirelessAccessType = WirelessAccessType.WIRELESS_ACCESS_TYPE_NONE;
		} 

		_commContext.accessPointName = NetworkUtils.getAccessPointName(_icontext.mainActivity);
		_commContext.signalStrength = NetworkUtils.getNetworkSignalStrength(_icontext.mainActivity);
	}

	private void _updateDataThroughput(){
		_commContext.receivedDataThroughput = 0;//TODO
		_commContext.sentDataThroughput = 0;//TODO
	}

	private void _updateResponseTimes(){
		_commContext.rtt = 0;//TODO
		_commContext.srt = 0;//TODO
	}

}
