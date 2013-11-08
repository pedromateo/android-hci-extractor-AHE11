package org.mmi.android.instrumentation.utils;

import java.util.Stack;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AdapterView;

/**
 * Iterator that allows you to iterate over the elements
 * compounding an Android GUI.
 * 
 * @author mateo-navarro.pedro
 *
 */
public class UiTraverser implements java.util.Iterator<View> {
	
	private static final String TAG = UiTraverser.class.getName();
	private Stack<View> nodes;
	
	private final static boolean PRINT_VIEW_TREE = false;

	public UiTraverser(View v)	{
		Log.d(TAG, "Construct new instance of UiTraverser");
		
		if ( PRINT_VIEW_TREE ) {
			printViewStructure(v, 0);
		}
		
		
		nodes = new Stack<View>();
		if (v != null) {
			Log.d(TAG, "Push view on stack. Type of view is: " + v.getClass().getName());
			nodes.push(v);
		}
	}

	public boolean hasNext() {
		return !nodes.empty();
	}


	public View next()
	{
		View v = null;

		//if remaining nodes
		if (hasNext())
		{
			//return the last element
			v = nodes.pop();
			// Log.d(TAG, "View was poped from stack. Type of view is: " + v.getClass().getName());
			
			//if it is a container, add its children to the stack
			if (v instanceof ViewGroup ||	v instanceof AdapterView) {
				ViewGroup vg = (ViewGroup)v;
				for (int i = 0; i < vg.getChildCount(); i++) {
					
					View child = vg.getChildAt(i);
					
					if(child == null) {
						Log.i(TAG, "Child view is null. It won't be push on the stack.");
					} else {
						// Log.d(TAG, "Push child view on stack. Type of child view is: " + child.getClass().getName());
						nodes.push(vg.getChildAt(i));
					}
				}				
			}
		}

		//return result
		return v;
	}

	public void remove() 
	{
		//not supported
	}
	
	/**
	 * Internal method to get the top parent of a View
	 * @param v The reference View
	 * @return The top parent
	 */
	public static View getTopParent(View v)
	{
		View result = v;
		ViewParent vp = v.getParent();
		while (vp != null)
		{
			//Log.d(TAG,"Parent :: " + vp.toString());
			if (vp instanceof View)
			{
				result = (View)vp;
				Log.d(TAG,"  :: is View");
			}
			vp = vp.getParent();
		}

		return result;
	}
	
	private static void printViewStructure(View v, int layer) {
				
		StringBuilder sb = new StringBuilder();
		
		// indent n blanks per layer 
		int n = 2;
		for (int j = 0; j <= n * layer; j++ ) {
			sb.append(" ");
		}
		
		Log.d("UiTraverser", sb.toString() + v.getClass().getSimpleName());
		
		if ( v instanceof ViewGroup ) {
			ViewGroup vg = (ViewGroup) v;
			
			for ( int i = 0; i < vg.getChildCount(); i++ ) {
				View child = vg.getChildAt(i);
				
				if (child != null) {
					printViewStructure(child, layer + 1);
				}
			}
		}
	}
}
