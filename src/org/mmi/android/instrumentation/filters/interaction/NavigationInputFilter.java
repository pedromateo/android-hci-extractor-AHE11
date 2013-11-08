package org.mmi.android.instrumentation.filters.interaction;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.mmi.android.instrumentation.filters.BaseFilter;
import org.mmi.android.instrumentation.InstrumentationContext;
import org.mmi.android.instrumentation.OptionsMenuObserver;
import org.mmi.android.instrumentation.viewproxies.BaseViewProxy;
import org.mmi.android.instrumentation.viewproxies.listeners.ListenerExtractor;
import org.mmi.android.instrumentation.MmiFacade;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;

/**
 * Instrumentation filter for user mouse and navigation input.
 * 
 * @author mateo-navarro.pedro
 *
 */
public class NavigationInputFilter 
extends BaseFilter
implements OnClickListener,
OnLongClickListener,
OnTouchListener,
OnItemClickListener,
OnItemLongClickListener,
OnItemSelectedListener,
OnMenuItemClickListener
{

	private static final String TAG = NavigationInputFilter.class.getSimpleName();

	///
	/// proxied listeners
	///

	/**
	 * All the variables called "<listener_name>" are a set of
	 * event listeners subscribed to a concrete event. 
	 * As the filter is installed as a listener into the tracked
	 * view objects, the old existing listeners have to be also
	 * notified about the events. The old existing listeners are
	 * included in these sets. In that way, the filter acts as
	 * a proxy forwarding all the notifications to the corresponding
	 * listeners.
	 */
	private Map<View, OnClickListener> onClickListeners;
	private Map<View, OnLongClickListener> onLongClickListeners;
	private Map<View, OnTouchListener> onTouchListeners;
	private Map<View, OnItemClickListener> onItemClickListeners;
	private Map<View, OnItemLongClickListener> onItemLongClickListeners;
	private Map<View, OnItemSelectedListener> onItemSelectedListeners;
	private Map<MenuItem, OnMenuItemClickListener> onMenuItemClickListeners;

	/**
	 * Holds the reference to the parent view (e.g. a ListView) of the menu which
	 * holds a menu item.
	 */
	private Map<MenuItem, View> menuItemsParentViews;

	/**
	 * The map is used to determine if the OnItemSelectedEvent happens the first
	 * time or not. Please see
	 * {@link #onItemSelected(AdapterView, View, int, long)} about details.
	 */
	private Map<Spinner, Boolean> isFirstOnItemSelectedEvent;

	/**
	 * Contains all views on that this filter is already installed.
	 * If a view is found in this set, it has not to be installed again.  
	 */
	private Set<View> installedViews;


	/**
	 * Constructor
	 */
	public NavigationInputFilter(InstrumentationContext ic, MmiFacade f){
		super(ic, f);

		//
		onClickListeners			= new HashMap<View, OnClickListener>();
		onLongClickListeners		= new HashMap<View, OnLongClickListener>();
		onTouchListeners			= new HashMap<View, OnTouchListener>();
		onItemClickListeners		= new HashMap<View, OnItemClickListener>();
		onItemLongClickListeners	= new HashMap<View, OnItemLongClickListener>();
		onItemSelectedListeners		= new HashMap<View, AdapterView.OnItemSelectedListener>();
		onMenuItemClickListeners	= new HashMap<MenuItem, OnMenuItemClickListener>();

		menuItemsParentViews		= new HashMap<MenuItem, View>();

		isFirstOnItemSelectedEvent	= new HashMap<Spinner, Boolean>();
		installedViews 				= new HashSet<View>();
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

	@Override
	public void install(View v) {
		try {
			if (isAlreadyInstalled(v)){
				Log.d(TAG, "Filsters already installed in this view.");
			}
			else if (v != null){

				/// only in AdapterView
				if (v instanceof AdapterView){
					installOnAdapterView( (AdapterView<?>) v );
				}
				/// only in not AdapterView
				else {
					installOnNonAdapterView(v);
				}

				OnTouchListener onTouchListener = ListenerExtractor.getOnTouchListener(v);
				if (onTouchListener != null) {
					onTouchListeners.put(v, onTouchListener);
				}
				v.setOnTouchListener(this);

				// remember v as already been installed
				installedViews.add(v);

			} else {
				if( v == null ) {
					Log.w(TAG, "View is NULL");
				}
			}
		} catch (Exception e) {
			Log.e(TAG, "Error while installing listeners", e);
		}
	}

	private boolean isAlreadyInstalled(View v) {
		return installedViews.contains(v);
	}

	private void installOnNonAdapterView(View v) {
		Log.d(TAG,"View :: Installing listeners");

		/*	
	    /// those two events are not tracked; touch event is tracked instead
		
		OnClickListener onClickListener = ListenerExtractor.getOnClickListener(v);
		if ( onClickListener != null) {
			onClickListeners.put(v,	onClickListener);
		}
		v.setOnClickListener(this);

		OnLongClickListener onLongClickListener = ListenerExtractor.getOnLongClickListener(v);
		if (onLongClickListener != null) {
			onLongClickListeners.put(v,	onLongClickListener);
		}
		v.setOnLongClickListener(this);
		 */

		// Install onItemSelectedList
		if ( v instanceof AutoCompleteTextView ) {
			OnItemClickListener onItemSelectedListener = ListenerExtractor.getOnItemClickListener(v);
			if ( onItemSelectedListener != null ) {
				onItemClickListeners.put(v, onItemSelectedListener);
			}
			( (AutoCompleteTextView)v ).setOnItemClickListener(this);
		}
	}

	private void installOnAdapterView(AdapterView<?> av) {
		Log.d(TAG,"AdapterView :: Installing listeners");


		OnItemClickListener onItemClickListener = ListenerExtractor.getOnItemClickListenerAv(av);
		if (onItemClickListener != null) {
			onItemClickListeners.put(av, onItemClickListener);
			av.setOnItemClickListener(this);
		}

		OnItemLongClickListener onItemLongClickListener = ListenerExtractor.getOnItemLongClickListenerAv(av);
		if (onItemLongClickListener != null) {
			onItemLongClickListeners.put(av, onItemLongClickListener);
			av.setOnItemLongClickListener(this);
		}

		OnItemSelectedListener onItemSelectedListener = ListenerExtractor.getOnItemSelectedListenerAv(av);
		if ( onItemSelectedListener != null ) {
			onItemSelectedListeners.put(av, onItemSelectedListener);

			if ( av instanceof Spinner ) {
				isFirstOnItemSelectedEvent.put( (Spinner) av, Boolean.TRUE );
			}

			av.setOnItemSelectedListener(this);
		}

	}

	@Override
	@TargetApi(11)
	public void install(ActionBar actionBar) {
		// nothing
	}

	@Override
	public void install(MenuItem item, View parentView) {
		Log.d(TAG, "Install onMenuItemClickListener");

		OnMenuItemClickListener onMenuItemClickListener = ListenerExtractor.getOnMenuItemClickListener(item);

		if ( onMenuItemClickListener != null && this != onMenuItemClickListener && onMenuItemClickListeners.get(item) == null ) {
			onMenuItemClickListeners.put(item, onMenuItemClickListener);
		}
		menuItemsParentViews.put(item, parentView);
		item.setOnMenuItemClickListener(this);
	}

	/// //////////////////////////////////////////////////////////
	/// //////////////////////////////////////////////////////////

	/// 
	/// filtering methods
	///

	/**
	 * Handles a click event performed over a concrete view.
	 */
	@Override
	public void onClick (View v)
	{
		Log.d(TAG, "event :: " + "onClick");

		///
		/// do my work
		///

		// it is enough with TOUCH event
		// 
		_mmifacade.click(0);

		///
		/// do the proxyed work
		///

		OnClickListener l = onClickListeners.get(v);

		if (l != null) {

			// Do the proxy work only if the listener is not an PassThroughClickListener.
			// A PassThroughClickListener is an internal class of AutoCompleteTextView and
			// calling the onClick method leads to an infinite loop right here. 
			String listenerClassName = l.getClass().getName();
			if ( ! listenerClassName.endsWith("PassThroughClickListener")) {
				l.onClick(v);
			} else {
				Log.d(TAG,  "onClick was not called, since the listener is an instance of PassThroughClickListener");
			}
		} else {
			Log.d(TAG,"p_onClickListener_ :: no pre-listener ID:" + v.getId());
		}
	}

	/**
	 * Handles an itemClick event performed over a concrete view.
	 */
	@Override
	public void onItemClick (AdapterView<?> parent, View v, int position, long id)
	{
		Log.d(TAG, "event :: " + "onItemClick");	

		///
		/// do my work
		///

		_mmifacade.click(0);

		///
		/// do the proxyed work
		///

		OnItemClickListener l = onItemClickListeners.get(parent);
		if (l != null)
			l.onItemClick(parent, v, position, id);
		else
			Log.d(TAG,"p_onItemClickListener_ :: no pre-listener " + v.toString());
	}

	/**
	 * Handles a longItemClick event performed over a concrete view.
	 */
	@Override
	public boolean onItemLongClick (AdapterView<?> parent, View v, int position, long id)
	{
		Log.d(TAG, "event :: " + "onItemLongClick");	

		///
		/// do my work
		///

		_mmifacade.click(0);

		///
		/// do the proxyed work
		///
		boolean result;
		OnItemLongClickListener l = onItemLongClickListeners.get(parent);
		if (l != null)
			result = l.onItemLongClick(parent, v, position, id);
		else{
			Log.d(TAG,"p_onItemLongClickListener_ :: no pre-listener " + v.toString());
			result = false;
		}

		return result;
	}

	/**
	 * Handles a longClick event performed over a concrete view.
	 */
	@Override
	public boolean onLongClick (View v)
	{
		Log.d(TAG, "event :: " + "onLongClick");	

		///
		/// do my work
		///

		_mmifacade.click(0);

		///
		/// do the proxyed work
		///
		boolean result;
		OnLongClickListener l = onLongClickListeners.get(v);
		if (l != null)
			result = l.onLongClick(v);
		else{
			Log.d(TAG,"p_onLongClickListener_ :: no pre-listener " + v.toString());
			result = false;
		}

		return result;
	}



	/**
	 * 
	 */
	final private int SCROLL_MOVES_START = 7;
	private int lastTouch_ = -1;
	private int actionMoves_ = 0;

	/**
	 * Handles a touch event performed over a concrete view.
	 */
	@Override
	public boolean onTouch (View v, MotionEvent event)
	{
		Log.d(TAG, "event :: " + "onTouch" + event.getAction());	

		///
		/// do my work
		///

		//ACTION_DOWN 0 (0x00000000)
		//ACTION_MOVE 2 (0x00000002)
		//ACTION_UP 1 (0x00000001)
		//ACTION_SCROLL 8 (0x00000008)
		if (event.getAction() == MotionEvent.ACTION_DOWN){
			lastTouch_ = event.getAction();
			actionMoves_ = 0;
		}
		//
		//
		else if (event.getAction() == MotionEvent.ACTION_UP){
			//if last was ACTION_MOVE -> scroll
			//and actionMoves_ >= SCROLL_MOVES_START
			if (lastTouch_ == MotionEvent.ACTION_MOVE &&
					actionMoves_ > SCROLL_MOVES_START){
				_mmifacade.scroll(0);
			}
			//if last were only a few ACTION_MOVE
			//if last was ACTION_DOWN -> touch click
			else if (lastTouch_ == MotionEvent.ACTION_MOVE ||
					lastTouch_ == MotionEvent.ACTION_DOWN){
				_mmifacade.touch(0);
			}

			lastTouch_ = event.getAction();
			actionMoves_ = 0;
		}
		//
		//
		else if (event.getAction() == MotionEvent.ACTION_MOVE){
			lastTouch_ = event.getAction();
			actionMoves_++;
		}
		//
		//
		else{
			lastTouch_ = event.getAction();
			actionMoves_ = 0;
		} 

		///
		/// do the proxyed work
		///
		OnTouchListener l = onTouchListeners.get(v);
		if (l != null){

			//Log.e(TAG,"@@@@@@@@@ FORWARDING TOUCH TO " + v.toString());
			return l.onTouch(v,event);
		}
		else{
			Log.d(TAG,"p_onTouchListener_ :: no pre-listener ID:" + v.toString());
			return false;
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		if ( parent instanceof Spinner ) {
			Log.d(TAG,"event :: " + "onItemSelected (for Spinner)");
			onItemSelectedForSpinner(parent, view, position, id);
		} else {
			Log.d(TAG, "event :: " + "onItemSelected");
			processOnItemSelectedEvent(parent, view, position, id);
		}

	}

	private void onItemSelectedForSpinner(AdapterView<?> parent, View view, int position, long id) {
		if ( ! isFirstOnItemSelectedEvent.get( (Spinner) parent ) ) {
			processOnItemSelectedEvent(parent, view, position, id);
		} else {
			// set flag to false, since the first event call happens
			// The first call occurs during UI creation, and has not to be
			// counted as an user driven event.
			isFirstOnItemSelectedEvent.put( (Spinner) parent, Boolean.FALSE);
		}
	}

	private void processOnItemSelectedEvent(AdapterView<?> parent, View view,	int position, long id) {
		///
		/// do my work
		///

		_mmifacade.click(0);

		///
		/// do the proxyed work
		///

		OnItemSelectedListener l = onItemSelectedListeners.get(parent);
		if (l != null) {
			l.onItemSelected(parent, view, position, id);
		} else {
			Log.d(TAG,"p_onItemSelectedListener_ :: no pre-listener " + parent.toString());
		}
	}


	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		Log.d(TAG,"event :: " + "onNothingSelected");	
	}

	@Override
	public boolean onMenuItemClick(MenuItem item) {
		boolean result = false;
		/// do my work
		_mmifacade.click(0);

		OnMenuItemClickListener l = onMenuItemClickListeners.get(item);
		if ( l != null ) {
			result = l.onMenuItemClick(item);
		} else {
			Log.d(TAG, "onMenuItemClick: This filter holds no origin lister for the menu item.");
		}

		// get the parent view of menu item's menu (menu item -> menu -> view) and
		// and cause the next system turn
		View parentView = menuItemsParentViews.get(item);
		// FIXME nextStytemTurn(parentView);

		return result;
	}

	@Override
	public void install(AlertDialog dialog) {

		// We have to call the show() method on the dialog,
		// in order to initialize the buttons. Otherwise, the would be
		// null and it wont be possible to register as onClick listener on them.
		dialog.show();

		/// install to onClick events of the three dialog's buttons
		// First
		Button buttonPositive = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
		install(buttonPositive);
		// Second		
		Button buttonNegative = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
		install(buttonNegative);
		// Third
		Button buttonNeutral = dialog.getButton(DialogInterface.BUTTON_NEUTRAL);
		install(buttonNeutral);
	}

	@Override
	public void install(OptionsMenuObserver observer) {
		// TODO Auto-generated method stub

	}

}
