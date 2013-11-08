package android.hci.extractor;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONObject;
import org.mmi.android.genericquest.QuestManager;
import org.mmi.android.instrumentation.ContextDescription;
import org.mmi.android.instrumentation.InstrumentationContext;
import org.mmi.android.instrumentation.config.InstrumentationConfig;
import org.mmi.android.instrumentation.utils.ConnectionUtils;
import org.mmi.android.instrumentation.utils.DeviceUtils;
import org.mmi.android.instrumentation.utils.LocationUtils;
import org.mmi.android.instrumentation.utils.NetworkUtils;

import android.graphics.Point;
import android.hci.extractor.MyFacade;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hci.extractor.test.R;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

public class testActivity extends Activity {

	private static final String TAG = "testActivity";
	
	private final boolean _do_quest = false;
	private final boolean _simulate_quest = false;
	
	private boolean _quest_done = false;
	ArrayList<String> _quest_results;
	private boolean _instr_initialized = false;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
 
		//set elements to the spinner
		Spinner spinner = (Spinner) findViewById(R.id.spinner1);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.test_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);

		//set generic listener
		findViewById(R.id.bExit).setOnClickListener(gocl);
		findViewById(R.id.button1).setOnClickListener(gocl);
		findViewById(R.id.checkBox1).setOnClickListener(gocl);
		findViewById(R.id.checkBox2).setOnClickListener(gocl);
		findViewById(R.id.editText1).setOnClickListener(gocl);
		findViewById(R.id.label).setOnClickListener(gocl);
		findViewById(R.id.radioButton1).setOnClickListener(gocl);
		findViewById(R.id.seekBar1).setOnClickListener(gocl);
		findViewById(R.id.bActivity).setOnClickListener(gocl);
		spinner.setOnItemSelectedListener(goisl);

		if (_do_quest){

			/// ///////////////////////////////////////////////////////////////
			/// questionnaire - launch
			/// ///////////////////////////////////////////////////////////////
			///

			/// launch questionnaire

			Intent intent = new Intent(this,QuestManager.class);

			Bundle data = new Bundle();
			data.putString(QuestManager.Q_FILE,"quest_config.json");
			intent.putExtras(data);

			startActivityForResult(intent,QuestManager.Q_REQUEST_CODE);

			/// 
			/// ///////////////////////////////////////////////////////////////
		}
	}


	@Override
	protected void onResume() {
		super.onResume();

		if ((_do_quest && _quest_done) || !_do_quest){

			/// ///////////////////////////////////////////////////////////////
			/// instrumentation
			/// ///////////////////////////////////////////////////////////////
			///
			try {

				///// network
				//Log.e(TAG,"Network available?  " + NetworkUtils.hasNetworkConnectivity(this));
                //Log.e(TAG,"Network type: " + NetworkUtils.getNetworkAccessType(this));
				//Log.e(TAG,"Network access point: " + NetworkUtils.getAccessPointName(this));
				//Log.e(TAG,"Network signal power: " + NetworkUtils.getNetworkSignalStrength(this));
				///// device
				//Log.e(TAG,"Screen resolution: " + DeviceUtils.getScreenResolution_px(this).toString());
				//Log.e(TAG,"Screen size inches: " + DeviceUtils.getScreenSize_inch(this));
				//Log.e(TAG,"Screen orientation landscape? " + DeviceUtils.isScreenOrientationLandscape(this));
				//Log.e(TAG,"Screen brightness %: " + DeviceUtils.getScreenBrightnessLevel(this));
				//Log.e(TAG,"Volume level %: " + DeviceUtils.getVolumeLevel(this));
				//Log.e(TAG,"Memory usage %: " + DeviceUtils.getMemoryUsage(this));
				//Log.e(TAG,"CPU usage %: " + DeviceUtils.getCpuUsage(this));

				// check internet connection
				// if (!ConnectionUtils.hasConnection(this)){
				// 	Log.w(TAG, "WARNING: No internet connection.");
				// }

				/// do instrumentation only once
				if (!_instr_initialized){

					///
					/// do instrumentation
					///

					// 1. create the facade that will be called when events occur

					MyFacade f = new MyFacade();

					// 2. create a configuration object from config file

					String configPath = "instr_config.json";
					InputStream configFile = null;
					try{
						configFile = getAssets().open(configPath); 
					}
					catch (Exception e) {
						e.printStackTrace();
						finish();
					}

					InstrumentationConfig config = 
							new InstrumentationConfig(configFile);

					// 3. (optional) create an initial context with values obtained
					//    from a previous questionnaire

					ContextDescription context = null;
					if (!_do_quest && _simulate_quest){
						context = new ContextDescription();
						utils.fillDummyValuesOnContextDescription(context);
					}
					
					if (_quest_results != null && _quest_results.size() > 0){
						context = new ContextDescription();
						for (int i = 0; i < _quest_results.size(); i++){
							if (!context.parseContextJSONValue(_quest_results.get(i),
									QuestManager.Q_VALUE, QuestManager.Q_RESULT))
							Log.e(TAG,"ERROR: while parsing string: " + _quest_results.get(i));
						}
					}

					// 4. initialize instrumentation process

					if (!InstrumentationContext.initializeInstrumentation(f,config,context)){
						Log.e(TAG, "ERROR when initializing InstrumentationContext");
						finish();
					}

					// 5. register this activity as the main activity
					InstrumentationContext.get().registerMainActivity(this);

					// 6. start interaction tracking
					InstrumentationContext.get().startInteraction(); 

					// do instrumentation only once
					_instr_initialized = true;
				}

				//} catch (InstrumentationException e) {
			} catch (Exception e) {
				e.printStackTrace();
			}
			/// 
			/// ///////////////////////////////////////////////////////////////
		}
	}


	@Override
	protected void onDestroy() {
		super.onDestroy();

		/// ///////////////////////////////////////////////////////////////
		/// instrumentation
		/// ///////////////////////////////////////////////////////////////
		///
		try {
			// 1. end interaction tracking
			InstrumentationContext.get().endInteraction(); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		/// 
		/// ///////////////////////////////////////////////////////////////
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (_do_quest){

			/// ///////////////////////////////////////////////////////////////
			/// questionnaire - results
			/// ///////////////////////////////////////////////////////////////
			///

			/// get results from questionnaire

			if (requestCode == QuestManager.Q_REQUEST_CODE) {
				// Make sure the request was successful
				if (resultCode == RESULT_OK) {

					_quest_results = new ArrayList<String>();

					// get the number of questions
					int nquest = data.getIntExtra(QuestManager.Q_NUMBER_QUESTIONS,0);
					Log.e(TAG,"QUESTIONNAIRE RESULT. nquestions = " + nquest);

					// get the results
					for (int i = 0; i < nquest; i++){
						_quest_results.add(data.getStringExtra(Integer.toString(i)));
						Log.e(TAG,"  - " + _quest_results.get(i));
					}

					_quest_done = true;
				}
			}

			/// 
			/// ///////////////////////////////////////////////////////////////
		}
	}



	/// ///////////////////////////////////////////////////////////////////
	/// ///////////////////////////////////////////////////////////////////
	/// ///////////////////////////////////////////////////////////////////
	/// ///////////////////////////////////////////////////////////////////

	protected void launchSecondaryActivity(){
		Intent intent = new Intent(this,TestActivity2.class);
		startActivity(intent);
	}

	///
	///
	///
	OnClickListener gocl = new OnClickListener() {
		public void onClick(View view) {
			TextView label = (TextView) findViewById(R.id.label);
			if (label != null)
				label.setText(view.toString());

			// if bActivity we create secondary activity
			if (view.getId() == R.id.bActivity){
				launchSecondaryActivity();
			}
			// if bExit we finish
			else if (view.getId() == R.id.bExit){
				finish();
			}
		}
	};

	///
	///
	///
	OnItemSelectedListener goisl = new OnItemSelectedListener() {
		@Override
		public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
			TextView label = (TextView) findViewById(R.id.label);
			if (label != null)
				label.setText(selectedItemView.toString());
		}

		@Override
		public void onNothingSelected(AdapterView<?> parentView) {
			TextView label = (TextView) findViewById(R.id.label);
			if (label != null)
				label.setText(parentView.toString());
		}
	};

	///
	///
	///
	OnItemClickListener gicl = new OnItemClickListener() {
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			TextView label = (TextView) findViewById(R.id.label);
			if (label != null)
				label.setText(arg1.toString());
		}
	};
}