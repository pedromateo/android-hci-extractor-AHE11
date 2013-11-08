package org.mmi.android.instrumentation.viewproxies;

import java.util.LinkedList;

import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Dialog;
import android.content.DialogInterface.OnShowListener;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.view.View.OnKeyListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

/**
 * Base for view-proxy classes.
 * These classes are used to easily get some common
 * information from different objects with a common
 * super-class.
 * 
 * @author mateo-navarro.pedro
 *
 */
public abstract class BaseViewProxy {
	
	/**
	 * List of widgets supported by a concrete implementation of {@link BaseViewProxy}. 
	 */
	protected LinkedList< Class<? extends Object> > adaptedWidgets = 
			new LinkedList< Class<? extends Object> >();

	/**
	 * Constructor.
	 */
	public BaseViewProxy(){
	}

	///
	/// abstract methods
	///

	/**
	 * Returns the number of elements included in this view.
	 * @param v the view
	 * @return elements
	 */
	public abstract int getElements(View v);

	/**
	 * Returns true if the view represents a question.
	 * @param v the view
	 * @return is question
	 */
	public abstract boolean isQuestion(View v);

	/**
	 * Returns the number of concepts included in this view.
	 * @param v the view
	 * @return elements
	 */
	public abstract int getConcepts(View v);

	/**
	 * Returns the number of noise elements included in this view.
	 * @param v the view
	 * @return elements
	 */
	public abstract int getNoise(View v);

	/**
	 * Returns true if the view represents system feedback.
	 * @param v the view
	 * @return is feedback
	 */
	public abstract boolean isFeedback(View v);

	/**
	 * List of classes adapted by this proxy.
	 */
	public LinkedList< Class<? extends Object> > getAdaptedWidgets(){
		return adaptedWidgets;
	}
}
