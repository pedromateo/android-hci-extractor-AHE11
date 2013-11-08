package org.mmi.android.instrumentation.filters.interaction;

import org.mmi.android.instrumentation.filters.BaseFilter;
import org.mmi.android.instrumentation.InstrumentationContext;
import org.mmi.android.instrumentation.OptionsMenuObserver;
import org.mmi.android.instrumentation.filters.CompositeFilter;
import org.mmi.android.instrumentation.objectproxies.ToastProxy;
import org.mmi.android.instrumentation.MmiFacade;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.OnHierarchyChangeListener;
import android.widget.Toast;


/**
 * Instrumentation filter for new GUI elements that appear
 * momentarily on the screen or that are shown in the same
 * activity due to an action of the user (e.g. a toast message).
 * 
 * @author mateo-navarro.pedro
 *
 */
public class NewChildFilter extends BaseFilter
implements OnHierarchyChangeListener{

	private static final String TAG = NewChildFilter.class.getName();

	/**
	 * Constructor
	 * @param f model facade
	 * @param cf a filter to be installed in the new children if necessary
	 */
	public NewChildFilter(InstrumentationContext ic, MmiFacade f, CompositeFilter cf) {
		super(ic,f);
		mCompositeFilter = cf;
	}

	private CompositeFilter mCompositeFilter;

	///
	/// InteractionListener interface implementation
	///

	@Override
	public void onInteractionInitialize() {
		// do previous configuration here
	}
	
	@Override
	public void onInteractionStart() {
		// TODO Auto-generated method stub
	}

	@Override
	public void onInteractionEnd() {
		// TODO Auto-generated method stub

	}

	///
	/// BaseFilter abstract methods implementation
	///

	@Override
	public void install(View v) {
		try{
			if (v != null && v instanceof ViewGroup)
			{
				Log.d(TAG,"View :: Installing listeners");
				//((ViewGroup)v).setOnHierarchyChangeListener(this);
				//((ViewGroup)v).setAlwaysDrawnWithCacheEnabled(false);
			}
			else{
				Log.w(TAG,"View is NULL");
			}
		}
		catch (Exception e)
		{
			Log.e(TAG, "Error while installing listeners", e);
		}
	}

	///
	/// child creation control methods
	///
	
	/**
	 * Handles a new child view added to a view on the screen.
	 */
	@Override
	public void onChildViewAdded(View parent, View child) {
		Log.d(TAG,"onChildViewAdded");
		// install all filters in new children
		//Log.d(TAG,"Installing filters in new children");
		//TODO cfilter_.installOnView(child);
	}

	/**
	 * Handles a child view removed to a view on the screen.
	 */
	@Override
	public void onChildViewRemoved(View parent, View child) {
		//do nothing
		Log.d(TAG,"onChildViewRemoved");
	}
	
	///
	/// toast support
	///
	
	/**
	 * This method simulates toast detection. So far, real toast
	 * detection is not supported by this framework.
	 * FIXME TODO implement this in the right way
	 * @param toast the toast object
	 */
	public void onToastShown(Toast t) {
		Log.d(TAG,"onToastShown");
		
		ToastProxy tproxy = new ToastProxy();
		
		///
		//send information to the framework
		int aux;
		//set elements or feedback elements
		aux = tproxy.getElements(t);
		_mmifacade.newGuiFeedback(0,aux);
			
		//set concepts
		aux = tproxy.getConcepts(t);
		if (aux > 0) _mmifacade.newGuiConcepts(0,aux);
		//set noise
		aux = tproxy.getNoise(t);
		if (aux > 0) _mmifacade.newGuiNoise(0,aux);
		//set question
		if (tproxy.isQuestion(t))
			_mmifacade.newGuiQuestions(0,1);
		
		Log.d(TAG,"%%%% (elements) " + tproxy.getElements(t));
		Log.d(TAG,"%%%% (concepts) " + tproxy.getConcepts(t));
		Log.d(TAG,"%%%% (noise) " + tproxy.getNoise(t));
		Log.d(TAG,"%%%% (is question) " + tproxy.isQuestion(t));
		Log.d(TAG,"%%%% (is feedback) " + tproxy.isFeedback(t));
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
