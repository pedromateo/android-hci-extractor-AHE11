package org.mmi.android.instrumentation.filters.interaction;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.mmi.android.instrumentation.filters.BaseFilter;
import org.mmi.android.instrumentation.InstrumentationContext;
import org.mmi.android.instrumentation.InstrumentationException;
import org.mmi.android.instrumentation.OptionsMenuObserver;
import org.mmi.android.instrumentation.optionsmenu.listeners.OnOptionsMenuPreparedListener;
import org.mmi.android.instrumentation.sentinel.SentinelView;
import org.mmi.android.instrumentation.sentinel.listeners.OnAttachedToWindowListener;
import org.mmi.android.instrumentation.sentinel.listeners.OnWindowFocusChangedListener;
import org.mmi.android.instrumentation.sentinel.listeners.OnWindowVisibilityChangedListener;
import org.mmi.android.instrumentation.utils.StringUtils;
import org.mmi.android.instrumentation.viewproxies.listeners.ListenerExtractor;
import org.mmi.android.instrumentation.MmiFacade;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnAttachStateChangeListener;
import android.view.View.OnCreateContextMenuListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnLayoutChangeListener;
import android.view.View.OnSystemUiVisibilityChangeListener;


public class ViewChangesFilter
extends BaseFilter
implements OnFocusChangeListener,
OnWindowVisibilityChangedListener,
OnWindowFocusChangedListener,
OnAttachedToWindowListener,
OnShowListener, 					// since API 5
OnCreateContextMenuListener, 		// since API 5
OnLayoutChangeListener, 			// since API 11
OnSystemUiVisibilityChangeListener,	// since API 11
OnAttachStateChangeListener,	    // sinceAPI 12
TabListener,
OnOptionsMenuPreparedListener
{

	private static final String TAG = ViewChangesFilter.class.getName();

	/// Proxies for intercepted listeners
	private Map<Tab, TabListener> tabListeners = new HashMap<Tab, TabListener>();
	private Map<View, OnCreateContextMenuListener> onCreateContextMenuListener = new HashMap<View, View.OnCreateContextMenuListener>();
	private Map<Dialog, OnShowListener> onShowListeners = new HashMap<Dialog, DialogInterface.OnShowListener>(); 

	// Collects all views from onPreparedMenu event , in order to
	// track if the event happens the first time for a menu (which 
	// is related to a particular view)
	private Set<View> knownViewsForOptionsMenus = new HashSet<View>();


	private ViewProcessor viewProcessor;

	/**
	 * Constructor
	 */
	public ViewChangesFilter(InstrumentationContext ic, MmiFacade f){
		super(ic, f);

		viewProcessor = new ViewProcessor(_mmifacade);
	}

	///
	/// InteractionListener interface implementation
	///

	@Override
	public void onInteractionInitialize() {
		// do previous configuration here
	}

	@Override
	public void onInteractionStart() {} // nothing



	@Override
	public void onInteractionEnd() {} // nothing

	///
	/// BaseFilter abstract methods implementation
	///

	public void install(View v) {
		
		///
		/// for now, this filter uses the "onLayoutChange" method to detect
		/// when a view changes. A change means also to inflate in the main screen.
		/// SentinelView is not used in this version of the filter
		///
		
		try {
			if (v != null)	{

				///
				/// only for SentinelView (disabled in AHE16)
				//if (v instanceof SentinelView) {
				//	Log.d(TAG,"SentinelView :: Installing listeners");
				//	((SentinelView)v).setOnWindowVisibilityChangedListener(this);
				//	// ( (SentinelView) v ).setOnWindowFocusChangedListener(this);
				//	((SentinelView)v).setOnAttachedToWindowListener(this);
				//}

				///
				/// for all views

				// listener called each time a view is attached to a window
				v.addOnLayoutChangeListener(this);
				//v.addOnAttachStateChangeListener(this);
				//v.setOnFocusChangeListener(this);

				
				OnCreateContextMenuListener contextMenuListener = 
						ListenerExtractor.getOnCreateContextMenuListener(v);
				if ( contextMenuListener != null ) {
					onCreateContextMenuListener.put(v, contextMenuListener);
					v.setOnCreateContextMenuListener(this);
				}

				// since API 11 or higher
				v.setOnSystemUiVisibilityChangeListener(this);
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

	@Override
	public void install(ActionBar actionBar) {
		int tabCount = actionBar.getTabCount();

		for (int tabIndex = 0; tabIndex < tabCount; tabIndex++ ) {
			Tab tab = actionBar.getTabAt(tabIndex);
			TabListener listener = ListenerExtractor.getTabListener(tab);

			if( tabListeners.get(tab) == null ) {
				tabListeners.put(tab, listener);
			}

			tab.setTabListener(this);
		}
	}

	@Override
	public void install(MenuItem item, View parentView) {
		// TODO Auto-generated method stub
	}

	@Override
	public void install(AlertDialog dialog) {
		Log.d(TAG, "Install on AlertDialog");
		OnShowListener onShowListener = ListenerExtractor.getOnShowListener(dialog);
		if ( onShowListener != null ) {
			onShowListeners.put(dialog, onShowListener);
		}
		//dialog.getOwnerActivity();
		dialog.setOnShowListener(this);
	}

	@Override
	public void install(OptionsMenuObserver observer) {
		Log.d(TAG, "Install on observer");
		observer.addListener(this);
	}

	///
	/// filtering methods
	///

	@Override
	public void onWindowVisibilityChange(SentinelView sv, int visibility) {
		Log.d(TAG,"@@@@@@@@@@@@@@@@@@@@@@@@@ onWindowVisibilityChange " + visibility);
		//if (visibility == View.VISIBLE) {
		//	viewProcessor.processFromTopView(sv);
		//}
		// done in onViewAttachedToWindow method
	}

	@Override
	public void onWindowFocusChange(SentinelView sv, boolean hasWindowFocus) {
		Log.d(TAG,"@@@@@@@@@@@@@@@@@@@@@@@@@ onWindowFocusChange " + hasWindowFocus);
		//if (hasWindowFocus) {
		//	viewProcessor.processFromTopView(sv);
		//}
		// done in onViewAttachedToWindow method
	}

	@Override
	public void onAttachedToWindow(SentinelView sv, boolean attached) {
		Log.d(TAG,"@@@@@@@@@@@@@@@@@@@@@@@@@ onAttachedToWindow " + attached);
		//if (attached) {
		//	viewProcessor.processFromTopView(sv);
		//}
		// done in onViewAttachedToWindow method
	}

	@Override
	public void onViewAttachedToWindow(View v) {
		Log.d(TAG, "@@@@@@@ onViewAttachedToWindow " + v.toString());
		//viewProcessor.analyzeViewContent(v);
		//  done in onLayoutChange method
	}

	@Override
	public void onViewDetachedFromWindow(View v) {
		Log.e(TAG, "@@@@@@@ onViewDetachedToWindow " + v.toString());
	}

	@Override
	public void onLayoutChange(View v, int arg1, int arg2, int arg3,
			int arg4, int arg5, int arg6, int arg7, int arg8) {
		Log.d(TAG, "@@@@@@@ onLayoutChange: " + v.toString());

		boolean change = !(arg5 == 0 && arg6 == 0 &&
				arg7 == 0 && arg8 == 0);
		boolean is_visible = v.getVisibility() == View.VISIBLE;
		boolean is_already_attached = v.getKeyDispatcherState() != null;//v.getDisplay() != null



		if (is_visible && change && is_already_attached) 
			Log.d(TAG,"onLayoutChange" + " : " + v.toString() + " : " 
					+ arg1 + "."
					+ arg2 + "."
					+ arg3 + "."
					+ arg4 + "."
					+ arg5 + "."
					+ arg6 + "."
					+ arg7 + "."
					+ arg8
					);


		if (change && is_visible && is_already_attached){
			viewProcessor.analyzeViewContent(v);
		}
	}

	///
	/// other filtering non-used methods
	///

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		Log.d(TAG, "event: onTabSelected");
		// First we will log end of a user turn and start of a system turn
		// Secondly we have to pass through the event to the origin listener

		Log.d(TAG, "Another tab in the action bar was selected.");

		// pass through the event to the origin listener
		TabListener originListener = tabListeners.get(tab);
		originListener.onTabSelected(tab, ft);
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		Log.d(TAG, "event: onTabUnselected");
		// just pass through the event to the origin listener
		TabListener originListener = tabListeners.get(tab);
		originListener.onTabUnselected(tab, ft);
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		Log.d(TAG, "event: onTabReselected");
		// just pass through the event to the origin listener

		TabListener originListener = tabListeners.get(tab);
		originListener.onTabReselected(tab, ft);
	}

	@Override
	public void onSystemUiVisibilityChange(int arg0) {
		Log.d(TAG,"@@@@@@@@@@@@@@@@@@@@@@@@@onSystemUiVisibilityChange");
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View originView, ContextMenuInfo menuInfo) {
		Log.d(TAG,"onCreateContextMenu");
		// the user did a long click in order to show the context menu
		// By some reason, opening the context menu, avoids that the
		// onItemLongClickEvent (for list elements) is released (or received).
		// Thus, we have to log the click implicitly here.
		_mmifacade.click(0);

		// forward event to origin listener, this is necessary in order to fill
		// the context menu with its items
		onCreateContextMenuListener.get(originView).onCreateContextMenu(menu, originView, menuInfo);

		_processMenu(menu, originView);
	}

	@Override
	public void onMenuPrepared(Menu menu, View originView) {
		Log.d(TAG, "event: onMenuPrepared");

		// onMenuPrepared is also created when initializing the GUI. Thus, we
		// do not nothing the first time, the event happens. 
		if ( ! knownViewsForOptionsMenus.contains(originView) ) {
			// the event occurs the first time for the view
			knownViewsForOptionsMenus.add(originView);
		} else {

			// the user clicked, in order to show the menu
			_mmifacade.click(0);

			// log new elements
			_processMenu(menu, originView);
		}
	}

	private void _processMenu(Menu menu, View originView) {
		// install filter on menu items
		for( int itemIndex = 0; itemIndex < menu.size(); itemIndex++ ) {
			MenuItem item = menu.getItem(itemIndex);
			try {
				// FIXME adding every new menu item to the filters 
				// could lead to a memory hole...
				InstrumentationContext.get().registerMenuItem(item, originView);
			} catch (InstrumentationException e) {
				Log.e(TAG, "Could not register menu item: " + e);
			}
		}

		// now we can count the times
		for( int itemIndex = 0; itemIndex < menu.size(); itemIndex++ ) {
			MenuItem item = menu.getItem(itemIndex);
			int nelems = 0;
			//one element for the widget
			nelems++;
			//one element for each word in the text
			if ( item.getTitle() != null ) {
				String text = item.getTitle().toString();
				nelems += StringUtils.countWords(text);
			}

			_mmifacade.newGuiElements(0, nelems);
		}
	}

	@Override
	public void onFocusChange(View arg0, boolean arg1) {
		Log.d(TAG,"onFocusChange" + " : " + arg0.toString());
	}

	@Override
	public void onShow(DialogInterface dialogInterface) {
		Log.d(TAG, "@@@@@@@@@@@@@@@@@@@@@@@@@onShow");
		// Currently it should be an AlertDialog
		if( dialogInterface instanceof AlertDialog) {
			AlertDialog dialog = (AlertDialog) dialogInterface;

			// inform origin listener (if exists)
			OnShowListener originListener = onShowListeners.get(dialog);
			if ( originListener != null ) {
				originListener.onShow(dialog);
			}
		}

		// do something?
	}


}
