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

public class ContextSocialFilter extends ContextBaseFilter {

	private static final String TAG = "ContextSocialFilter";

	public ContextSocialFilter(InstrumentationContext ic, MmiFacade f,
			InstrumentationConfig cfc) {
		super(ic, f, cfc);

		_socialContext = new ContextDescription.SocialContext();
	}

	///
	/// variables
	protected ContextDescription.SocialContext _socialContext;

	///
	/// implemented methods

	@Override
	public void pushContextValues(ContextDescription context) {
		_socialContext.socialCompany = context.socialContext.socialCompany;
		_socialContext.socialArena = context.socialContext.socialArena;
	}

	@Override
	public void updateValues_once() {
		Log.i(TAG, "(ContextSocialFilter::updateValues_once)");
		// send data through facade
		// data have to be updated only in one method (using the finest)
		_mmifacade.socialContextChange(0,
				_socialContext.socialCompany, 
				_socialContext.socialArena);
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

