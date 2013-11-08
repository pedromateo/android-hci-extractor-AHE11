package org.mmi.android.instrumentation.sentinel;

import org.mmi.android.instrumentation.sentinel.listeners.OnAttachedToWindowListener;
import org.mmi.android.instrumentation.sentinel.listeners.OnWindowFocusChangedListener;
import org.mmi.android.instrumentation.sentinel.listeners.OnWindowVisibilityChangedListener;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Special view object which is not shown on the screen
 * but that can report valuable information during the
 * instrumentation process.
 * 
 * @author mateo-navarro.pedro
 *
 */
public class SentinelView extends View {

	private static final String TAG = "SentinelView";

	/**
	 * Window visibility change listener.
	 */
	private OnWindowVisibilityChangedListener onWindowVisibilityChangedListener_;

	/**
	 * Window focus change listener.
	 */
	private OnWindowFocusChangedListener onWindowFocusChangedListener_;

	/**
	 * Window attached change listener.
	 */
	private OnAttachedToWindowListener onAttachedToWindowListener_;

	///
	/// constructors
	///

	/**
	 * Constructor.
	 * @param context instrumentation context reference
	 */
	public SentinelView(Context context) {
		super(context);
		Log.i(TAG,"SentinelView created."); 
	}

	/**
	 * 
	 * @param context instrumentation context reference
	 * @param attrs attribute set
	 */
	public SentinelView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * @param context instrumentation context reference
	 * @param attrs attribute set
	 * @param defStyle definition style
	 */
	public SentinelView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	///
	/// window visibility changes listener
	///

	/**
	 *  Window visibility change notification method.
	 */
	@Override
	protected void onWindowVisibilityChanged (int visibility){
		//Log.d(TAG,"onWindowVisibilityChanged");
		if (onWindowFocusChangedListener_ != null){
			onWindowVisibilityChangedListener_.onWindowVisibilityChange(this, visibility);
		}
	}


	/**
	 * Sets the current window visibility change listener
	 * @param l
	 */
	public void setOnWindowVisibilityChangedListener(OnWindowVisibilityChangedListener l) {
		onWindowVisibilityChangedListener_ = l;
	}

	///
	/// extended methods
	///

	/**
	 * Handles a onFinishInflate event.
	 */
	@Override
	protected void onFinishInflate (){
		Log.d(TAG,"onFinishInflate");
	}

	/**
	 * Handles a onLayout event.
	 */
	@Override
	protected void onLayout (boolean changed, int left, int top, int right, int bottom){
		Log.d(TAG,"onLayout");
	}

	/**
	 * Handles a onSizeChanged event.
	 */
	@Override
	protected void onSizeChanged (int w, int h, int oldw, int oldh){
		Log.d(TAG,"onSizeChanged");
	}

	/**
	 * Handles a onDraw event.
	 */
	@Override
	protected void onDraw (Canvas canvas){
		Log.d(TAG,"onDraw");
	}

	/**
	 * Handles a onFocusChanged event.
	 */
	@Override
	protected void onFocusChanged (boolean gainFocus, int direction, Rect previouslyFocusedRect){
		Log.d(TAG,"onFocusChanged");
	}

	/**
	 * Handles a onWindowFocusChanged event.
	 */
	@Override
	public void onWindowFocusChanged (boolean hasWindowFocus){
		//Log.d(TAG,"onWindowFocusChanged");
		if (onWindowFocusChangedListener_ != null){
			onWindowFocusChangedListener_.onWindowFocusChange(this, hasWindowFocus);
		}
	}

	/**
	 * Sets the current window focus change listener
	 * @param l
	 */
	public void setOnWindowFocusChangedListener(OnWindowFocusChangedListener l) {
		onWindowFocusChangedListener_ = l;
	}

	/**
	 * Handles a onAttachedToWindow event.
	 */
	@Override
	protected void onAttachedToWindow (){
		//Log.d(TAG,"onAttachedToWindow");
		if (onAttachedToWindowListener_ != null){
			onAttachedToWindowListener_.onAttachedToWindow(this, true);
		}
	}
	
	/**
	 * Sets the current window attached change listener
	 * @param l
	 */
	public void setOnAttachedToWindowListener(OnAttachedToWindowListener l) {
		onAttachedToWindowListener_ = l;
	}

	/**
	 * Handles a onDetachedFromWindow event.
	 */
	@Override
	protected void onDetachedFromWindow (){
		//Log.d(TAG,"onDetachedFromWindow");
		if (onAttachedToWindowListener_ != null){
			onAttachedToWindowListener_.onAttachedToWindow(this, false);
		}
	}

	/**
	 * Handles a onDisplayHint event.
	 */
	@Override
	protected void onDisplayHint (int hint){
		Log.d(TAG,"onDisplayHint");
	}
}
