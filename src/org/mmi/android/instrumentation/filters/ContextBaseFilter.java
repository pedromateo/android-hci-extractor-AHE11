package org.mmi.android.instrumentation.filters;


import org.mmi.android.instrumentation.ContextDescription;
import org.mmi.android.instrumentation.InstrumentationContext;
import org.mmi.android.instrumentation.MmiFacade;
import org.mmi.android.instrumentation.OptionsMenuObserver;
import org.mmi.android.instrumentation.config.InstrumentationConfig;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.view.MenuItem;
import android.view.View;

public abstract class ContextBaseFilter 
extends BaseFilter {

	private static final String TAG = "ContextBaseFilter";

	// variables
	private InstrumentationConfig _config;

	/**
	 * Constructor
	 * @param ic context for the instrumentation process
	 * @param f facade to interact with the Multimodal Model Framework
	 */
	public ContextBaseFilter(InstrumentationContext ic, MmiFacade f, InstrumentationConfig cfc){
		super (ic,f);
		_config = cfc;
		_config.addContextUpdateSuscriber(this);
	}

	/**
	 * Updates context values and sends information to the facade.
	 * This method is called each "coarse" loop
	 */
	public abstract void updateValues_coarse();

	/**
	 * Updates context values and sends information to the facade.
	 * This method is called each "fine" loop
	 */
	public abstract void updateValues_fine();

	/**
	 * Updates context values and sends information to the facade.
	 * This method is called each "finest" loop
	 */
	public abstract void updateValues_finest();

	/**
	 * Updates context values and sends information to the facade.
	 * This method is called only once on interaction start
	 */
	public abstract void updateValues_once();
	
	/**
	 * Set context values from description
	 * @param context the values
	 */
	public abstract void pushContextValues(ContextDescription context);
	

	///
	/// interaction listener interface implementation
	///

	/**
	 * Notifies when interaction is ready to start
	 */
	public abstract void onInteractionInitialize();

	/**
	 * Notifies when interaction starts
	 */
	public void onInteractionStart(){
		// start update threads
		_config.start();
		// call update once method
		updateValues_once();
	}

	/**
	 * Notifies when interaction ends
	 */
	public void onInteractionEnd(){
		// stop update threads
		_config.stop();
	}
	
	///
	/// installation methods
	///
	/// by default, context filters are not installed
	///
	
	@Override
	public void install(View v) {
		// TODO Auto-generated method stub

	}

	@Override
	public void install(ActionBar actionBar) {
		// TODO Auto-generated method stub

	}

	@Override
	public void install(MenuItem item, View parentView) {
		// TODO Auto-generated method stub

	}

	@Override
	public void install(AlertDialog dialog) {
		// TODO Auto-generated method stub

	}

	@Override
	public void install(OptionsMenuObserver observer) {
		// TODO Auto-generated method stub

	}

	
}
