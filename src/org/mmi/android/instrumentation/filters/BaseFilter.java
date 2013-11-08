package org.mmi.android.instrumentation.filters;

import org.mmi.android.instrumentation.InstrumentationContext;
import org.mmi.android.instrumentation.InteractionListener;
import org.mmi.android.instrumentation.MmiFacade;
import org.mmi.android.instrumentation.OptionsMenuObserver;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

/**
 * The basis for instrumentation filters.
 * 
 * @author mateo-navarro.pedro
 *
 */
public abstract class BaseFilter 
implements InteractionListener {

	private static final String TAG = "BaseFilter";

	/**
	 * Constructor
	 * @param f facade to interact with the Multimodal Model Framework
	 */
	public BaseFilter(InstrumentationContext ic, MmiFacade f){
		//initialize variables
		_icontext = ic;
		_mmifacade = f;
	}

	/**
	 * variables
	 */
	protected InstrumentationContext _icontext;
	protected MmiFacade _mmifacade;


	/**
	 * filter installing
	 */
	public abstract void install(View v);

	/**
	 * filter installing
	 */
	@TargetApi(11)
	public abstract void install(ActionBar actionBar);

	/**
	 * filter installing
	 * @param parentView TODO
	 */
	public abstract void install(MenuItem item, View parentView);

	/**
	 * filter installing
	 * @param parentView TODO
	 */
	public abstract void install(AlertDialog dialog);

	/**
	 * filter installing
	 * @param parentView TODO
	 */
	public abstract void install(OptionsMenuObserver observer);

}
