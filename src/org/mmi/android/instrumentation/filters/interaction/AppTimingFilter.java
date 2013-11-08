package org.mmi.android.instrumentation.filters.interaction;

import org.mmi.android.instrumentation.InstrumentationContext;
import org.mmi.android.instrumentation.OptionsMenuObserver;
import org.mmi.android.instrumentation.MmiFacade;
import org.mmi.android.instrumentation.filters.BaseFilter;


import android.app.ActionBar;
import android.app.AlertDialog;
import android.view.MenuItem;
import android.view.View;

/**
 * This filters is not used for filtering.
 * It only uses the onInteractionStart and -End methods
 * to call the appStart and *End methods.
 * In the future maybe some Android events could be used
 * to indicate when te application starts and ends.
 * 
 * @author mateo-navarro.pedro
 *
 */
public class AppTimingFilter extends BaseFilter {
	
	private static final String TAG = "AppTimingFilter";

	/**
	 * Constructor
	 * @param f the model facade
	 */
	public AppTimingFilter(InstrumentationContext ic, MmiFacade f){
		super(ic, f);
	}
	
	///
	/// InteractionListener interface implementation
	///
	
	@Override
	public void onInteractionInitialize() {
		// do previous configuration here
	}
	
	@Override
	public void onInteractionStart() {
		///
		/// the first thing we have to do is to notify that
		/// the application has started
		_mmifacade.interactionStarts(0);
	}

	@Override
	public void onInteractionEnd() {
		///
		/// at the end of the interaction we notify that
		/// the application is finished
		_mmifacade.interactionEnds(0);
	}
	
	///
	/// BaseFilter abstract methods implementation
	///
	

	@Override
	public void install(View v) {
		//this filter is not installed on views
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
