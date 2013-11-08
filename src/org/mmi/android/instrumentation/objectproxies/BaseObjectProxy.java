package org.mmi.android.instrumentation.objectproxies;

import java.util.LinkedList;

/**
 * Base for object-proxy classes.
 * These classes are used to easily get some common
 * information from different objects with a common
 * super-class.
 * 
 * @author mateo-navarro.pedro
 *
 */
public abstract class BaseObjectProxy {
	
	/**
	 * List of widgets supported by a concrete implementation of {@link BaseObjectProxy}. 
	 */
	protected LinkedList< Class<? extends Object> > adaptedClasses = new LinkedList< Class<? extends Object> >();

	/**
	 * Constructor.
	 */
	public BaseObjectProxy() {
	}
	
	///
	/// abstract methods
	///

	/**
	 * Returns the number of elements included in this object.
	 * @param o the object
	 * @return elements
	 */
	public abstract int getElements(Object o);
	
	/**
	 * Returns true if the object represents a question.
	 * @param o the object
	 * @return is question
	 */
	public abstract boolean isQuestion(Object o);

	/**
	 * Returns the number of concepts included in this object.
	 * @param o the object
	 * @return elements
	 */
	public abstract int getConcepts(Object o);

	/**
	 * Returns the number of noise elements included in this object.
	 * @param o the object
	 * @return elements
	 */
	public abstract int getNoise(Object o);

	/**
	 * Returns true if the object represents system feedback.
	 * @param o the object
	 * @return is feedback
	 */
	public abstract boolean isFeedback(Object o);


	public LinkedList< Class<? extends Object> > getAdaptedClasses() {
		return adaptedClasses;
	}

	///
	/// variables
	///

	protected Object object_;

}
