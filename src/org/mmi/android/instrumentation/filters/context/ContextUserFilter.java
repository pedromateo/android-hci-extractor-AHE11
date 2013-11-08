package org.mmi.android.instrumentation.filters.context;

import org.mmi.android.instrumentation.ContextDescription;
import org.mmi.android.instrumentation.InstrumentationContext;
import org.mmi.android.instrumentation.MmiFacade;
import org.mmi.android.instrumentation.OptionsMenuObserver;
import org.mmi.android.instrumentation.config.InstrumentationConfig;
import org.mmi.android.instrumentation.filters.ContextBaseFilter;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

public class ContextUserFilter extends ContextBaseFilter {

	private static final String TAG = "ContextUserFilter";

	public ContextUserFilter(InstrumentationContext ic, MmiFacade f,
			InstrumentationConfig cfc) {
		super(ic, f, cfc);

		_userContext = new ContextDescription.User();
	}

	///
	/// variables
	protected ContextDescription.User _userContext;

	///
	/// implemented methods

	@Override
	public void pushContextValues(ContextDescription context) {
		_userContext.age = context.user.age;
		_userContext.gender = context.user.gender;
		_userContext.educationLevel = context.user.educationLevel;
		_userContext.previousExperience = context.user.previousExperience;
	}

	@Override
	public void updateValues_once() {
		Log.i(TAG, "(ContextUserFilter::updateValues_once)");
		// send data through facade.
		// data have to be updated only in one method (using the finest)
		_mmifacade.userContextChange(0,
				_userContext.age, 
				_userContext.gender, 
				_userContext.educationLevel, 
				_userContext.previousExperience);
	}

	///
	/// unmodified methods

	@Override
	public void updateValues_coarse() {
		// do nothing
	}

	@Override
	public void updateValues_fine() {
		// do nothing
	}

	@Override
	public void updateValues_finest() {
		// do nothing
	}

	@Override
	public void onInteractionInitialize() {
		// do nothing
	}
}
