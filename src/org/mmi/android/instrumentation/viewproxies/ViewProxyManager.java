package org.mmi.android.instrumentation.viewproxies;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import android.view.View;

/**
 * This class is used to manage a set of viewProxies.
 * 
 * @author mateo-navarro.pedro
 *
 */
public class ViewProxyManager {

	/**
	 * All proxies which are added to this manager.
	 */
	private HashMap< Class<? extends Object>, BaseViewProxy> mProxies;

	/**
	 * Constructor.
	 */
	public ViewProxyManager(){
		mProxies = new HashMap< Class<? extends Object>, BaseViewProxy>();
	}

	/**
	 * Adds a proxy
	 * @param p the proxy
	 * @throws ProxyConflictException
	 */
	public void addProxy(BaseViewProxy p) throws ProxyConflictException{
		LinkedList< Class<? extends Object> > adapted = p.getAdaptedWidgets();

		for (Class<? extends Object> c : adapted) {
			// check if class to be adpated is already adapted by another proxy.
			if ( mProxies.get(c) != null ) {
				throw new ProxyConflictException("One class can't be adapted by more then on proxy. Multiple adpated class: " + c.getSimpleName());
			}

			mProxies.put(c, p);
		}
	}

	/**
	 * Return a proxy depending on the view to adapt
	 * @param v the view object
	 * @return a proxy
	 */
	public BaseViewProxy getProxy(View v){
		BaseViewProxy bvp = null;
		//first try the class
		bvp = mProxies.get(v.getClass());
		if (bvp != null)
			return bvp;

		// if not, try from subclass
		Iterator it = mProxies.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry)it.next();
			Class<?> classtype = (Class)pair.getKey();
			//if (v instanceof classtype)
			if (classtype.isAssignableFrom(v.getClass())){
				return (BaseViewProxy)pair.getValue();
			}
		}

		// other case, return null
		return null;
	}

	/**
	 * Exception used to describe a proxy conflict, i.e. a
	 * class is adapted by more than one proxy.
	 * 
	 * @author mateo-navarro.pedro
	 *
	 */
	public class ProxyConflictException extends Exception{
		public ProxyConflictException(){}
		public ProxyConflictException(String message) {
			super(message);
		}
	}
}
