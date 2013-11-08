package org.mmi.android.instrumentation.filters;

import java.util.LinkedList;

import org.mmi.android.instrumentation.InstrumentationContext;
import org.mmi.android.instrumentation.InstrumentationException;
import org.mmi.android.instrumentation.OptionsMenuObserver;
import org.mmi.android.instrumentation.MmiFacade;
import org.mmi.android.instrumentation.utils.UiTraverser;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

/**
 * This is a dummy filter used to create a set of subfilters.
 * It has no functionality.
 * 
 * @author mateo-navarro.pedro
 *
 */
public class CompositeFilter extends BaseFilter {

	private static final String TAG = "CompositeFilter";

	public CompositeFilter(InstrumentationContext ic,MmiFacade f) {
		super(ic,f);
		_subfilters = new LinkedList<BaseFilter>();
	}

	/**
	 * Set of sub-filters to implement a composite filter.
	 */
	protected LinkedList<BaseFilter> _subfilters;

	/**
	 * Adds a new filter to the sub-filters list.
	 * @param bf new filter to add
	 */
	public void addSubFilter(BaseFilter bf){
		_subfilters.addLast(bf);
	}

	/**
	 * Removes a filter to the sub-filters list (TODO)
	 * @param bf the filter to remove.
	 */
	public void removeSubFilter(BaseFilter bf){
		//TODO
	}

	/**
	 * Returns subfilters
	 * @return
	 */
	public LinkedList<BaseFilter> getSubFilters(){
		return _subfilters;
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
		// TODO Auto-generated method stub

	}

	@Override
	public void onInteractionEnd() {
		// TODO Auto-generated method stub

	}

	///
	/// BaseFilter abstract methods implementation
	///


	/**
	 * Installs the filter and subfilters in all widgets
	 * starting from given widget
	 * @param v
	 */
	@Override
	public void install(View v) {
		if (v != null){
			///install subfilters
			for(BaseFilter bf : _subfilters){
				bf.install(v);
			}
			//Log.e(TAG,"Installing on: " + v.getClass().getSimpleName());
		}
	}

	@Override
	public void install(ActionBar actionBar) {
		if (actionBar != null)
			for(BaseFilter bf : _subfilters){
				bf.install(actionBar);
			}
	}

	@Override
	public void install(MenuItem item, View parentView) {
		if (item != null && parentView != null)
			for(BaseFilter bf : _subfilters){
				bf.install(item, parentView);
			}
	}

	@Override
	public void install(AlertDialog dialog) {
		if (dialog != null)
			for(BaseFilter bf : _subfilters){
				bf.install(dialog);
			}
	}

	@Override
	public void install(OptionsMenuObserver observer) {
		if (observer != null)
			for(BaseFilter bf : _subfilters){
				bf.install(observer);
			}
	}
}
