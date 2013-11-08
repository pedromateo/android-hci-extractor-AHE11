package android.hci.extractor;

import java.io.InputStream;

import org.mmi.android.genericquest.QuestManager;
import org.mmi.android.instrumentation.ContextDescription;
import org.mmi.android.instrumentation.InstrumentationContext;
import org.mmi.android.instrumentation.config.InstrumentationConfig;
import org.mmi.android.instrumentation.utils.DeviceUtils;
import org.mmi.android.instrumentation.utils.LocationUtils;
import org.mmi.android.instrumentation.utils.NetworkUtils;

import android.hci.extractor.test.R;
import android.hci.extractor.test.R.layout;
import android.hci.extractor.test.R.menu;
import android.location.Location;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class TestActivity2 extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_activity2);

		//set generic listener
		findViewById(R.id.bExit2).setOnClickListener(gocl);
		findViewById(R.id.bButton1).setOnClickListener(gocl);
		findViewById(R.id.bButton2).setOnClickListener(gocl);

		/// ///////////////////////////////////////////////////////////////
		/// instrumentation
		/// ///////////////////////////////////////////////////////////////
		///
		try {
			
			// 1. register this activity into the instrumentation process
			InstrumentationContext.get().registerActivity(this);
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		/// 
		/// ///////////////////////////////////////////////////////////////
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_test_activity2, menu);
		return true;
	}

	///
	///
	///
	OnClickListener gocl = new OnClickListener() {
		public void onClick(View view) {
			TextView label = (TextView) findViewById(R.id.label2);
			if (label != null)
				label.setText(view.toString());

			// if bActivity we create secondary activity
			if (view.getId() == R.id.bExit2){
				finish();
			}
		}
	};

}
