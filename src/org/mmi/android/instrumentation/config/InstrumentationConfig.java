package org.mmi.android.instrumentation.config;

import java.io.InputStream;
import java.util.LinkedList;

import org.json.JSONException;
import org.mmi.android.instrumentation.filters.ContextBaseFilter;

import android.util.Log;

public class InstrumentationConfig extends JSONConfiguration{

	private static final String TAG = "InstrumentationConfig";

	public InstrumentationConfig(InputStream filestream) {
		super(filestream);

		/// fill values from file
		_fillValues();

		// create update threads
		_threadCoarse = new UpdateThread(new CoarseUpdater(),updateTimeCoarse);
		_threadFine = new UpdateThread(new FineUpdater(),updateTimeFine);
		_threadFinest = new UpdateThread(new FinestUpdater(),updateTimeFinest);
		
		_filters = new LinkedList<ContextBaseFilter>(); 
	}

	///
	/// variables

	public boolean appTiming = false;
	public boolean navigationInput = false;
	public boolean motionInput = false;
	public boolean keyInput = false;
	public boolean viewChanges = false;
	public boolean speechInput = false;

	public enum ContextUpdate {
		UPDATE_COARSE,
		UPDATE_FINE,
		UPDATE_FINEST
	}

	public int updateTimeCoarse = 0;
	public int updateTimeFine = 0;
	public int updateTimeFinest = 0;

	public boolean physicalEnvironment = false;
	public boolean locationTime = false;
	public boolean user = false;
	public boolean socialContext = false;
	public boolean device = false;
	public boolean communication = false;


	///
	/// fill variables method

	private void _fillValues(){
		if (_object != null){

			appTiming = _getBooleanValue("appTiming");
			navigationInput = _getBooleanValue("navigationInput");
			motionInput = _getBooleanValue("motionInput");
			keyInput = _getBooleanValue("keyInput");
			viewChanges = _getBooleanValue("viewChanges");
			speechInput = _getBooleanValue("speechInput");

			///
			///

			updateTimeCoarse = _getIntValue("updateTimeCoarse");
			updateTimeFine = _getIntValue("updateTimeFine");
			updateTimeFinest =  _getIntValue("updateTimeFinest");

			///
			///

			physicalEnvironment = _getBooleanValue("physicalEnvironment");
			locationTime = _getBooleanValue("locationTime");
			user = _getBooleanValue("user");
			socialContext = _getBooleanValue("socialContext");
			device = _getBooleanValue("device");
			communication = _getBooleanValue("communication");
		}
	}

	//	private ContextUpdate _getUpdateValue(String value){
	//		String s = "";
	//		try {
	//			s = _object.getString(value);
	//			if (s.equals("finest")){
	//				return ContextUpdate.UPDATE_FINEST;
	//			}
	//			else if (s.equals("fine")){
	//				return ContextUpdate.UPDATE_FINE;
	//			}
	//			else {
	//				return ContextUpdate.UPDATE_COARSE;
	//			}
	//		} catch (JSONException e) {
	//			e.printStackTrace();
	//		}
	//
	//		return ContextUpdate.UPDATE_COARSE;
	//	}
	//
	//	private int _getUpdateTime(ContextUpdate cu){
	//
	//		if (cu == ContextUpdate.UPDATE_FINEST)
	//			return updateTimeFinest;
	//		else if (cu == ContextUpdate.UPDATE_FINE)
	//			return updateTimeFine;
	//		else 
	//			return updateTimeCoarse;
	//	}

	private boolean _getBooleanValue(String value){
		try {
			return _object.getBoolean(value);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return false;
	}

	private int _getIntValue(String value){
		try {
			return _object.getInt(value);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return 1000;
	}

	/// /////////////////////////////////////////////////////////
	///
	/// updatable filters functionality
	///

	protected LinkedList<ContextBaseFilter> _filters;

	public void addContextUpdateSuscriber(ContextBaseFilter cbf){
		_filters.add(cbf);
	}

	/// /////////////////////////////////////////////////////////
	///
	/// threads functionality
	///


	///
	/// internal class update thread

	public class UpdateThread extends Thread{


		private int _updateTime_ms;
		private boolean _exitThread;
		private Updater _updater;

		UpdateThread(Updater up, int time){
			_updater = up;
			_updateTime_ms = time;
			_exitThread = false;
		}

		public void run(){
			while(!_exitThread){
				try{
					sleep(_updateTime_ms);
					_updater.update();
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
		}

		public void stopThread(){
			_exitThread = true;
		}
	}

	///
	/// internal classes and interfaces for updaters

	private interface Updater{
		public void update();
	}

	private class CoarseUpdater implements Updater{
		public void update(){
			for (ContextBaseFilter cbf : _filters){
				cbf.updateValues_coarse();
			}
		}
	}

	private class FineUpdater implements Updater{
		public void update(){
			for (ContextBaseFilter cbf : _filters){
				cbf.updateValues_fine();
			}
		}
	}

	private class FinestUpdater implements Updater{
		public void update(){
			for (ContextBaseFilter cbf : _filters){
				cbf.updateValues_finest();
			}
		}
	}


	/// /////////////////////////////////////////////////////////
	///
	/// filters update configuration
	///

	protected UpdateThread _threadCoarse;
	protected UpdateThread _threadFine;
	protected UpdateThread _threadFinest;

	protected boolean _running = false;

	public void start(){
		if (!_running){
			_threadCoarse.start();
			_threadFine.start();
			_threadFinest.start();
			_running = true;
		}
	}

	public void stop(){
		if (_running){
			_threadCoarse.stopThread();
			_threadFine.stopThread();
			_threadFinest.stopThread();
			_running = false;
			Log.e(TAG,"############################ THREADS STOPPED");
		}
	}
}

