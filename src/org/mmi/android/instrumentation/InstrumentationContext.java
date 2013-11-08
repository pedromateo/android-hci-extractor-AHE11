package org.mmi.android.instrumentation;

import java.lang.reflect.Field;

import org.mmi.android.instrumentation.config.InstrumentationConfig;
import org.mmi.android.instrumentation.filters.interaction.AppTimingFilter;
import org.mmi.android.instrumentation.filters.context.ContextPhysicalFilter;
import org.mmi.android.instrumentation.filters.context.ContextUserFilter;
import org.mmi.android.instrumentation.filters.context.ContextSocialFilter;
import org.mmi.android.instrumentation.filters.context.ContextLocationTimeFilter;
import org.mmi.android.instrumentation.filters.context.ContextDeviceFilter;
import org.mmi.android.instrumentation.filters.context.ContextCommunicationFilter;
import org.mmi.android.instrumentation.filters.interaction.KeyInputFilter;
import org.mmi.android.instrumentation.filters.interaction.MotionInputFilter;
import org.mmi.android.instrumentation.filters.interaction.NavigationInputFilter;
import org.mmi.android.instrumentation.filters.interaction.NewChildFilter;
import org.mmi.android.instrumentation.filters.interaction.SpeechInputFilter;
import org.mmi.android.instrumentation.filters.interaction.ViewChangesFilter;
import org.mmi.android.instrumentation.filters.BaseFilter;
import org.mmi.android.instrumentation.filters.CompositeFilter;
import org.mmi.android.instrumentation.filters.ContextBaseFilter;
import org.mmi.android.instrumentation.sentinel.SentinelView;
import org.mmi.android.instrumentation.utils.UiTraverser;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

/**
 * Global context for the instrumentation process.
 * 
 * @author mateo-navarro.pedro
 *
 */
public class InstrumentationContext {

	private static final String TAG = "InstrumentationContext";

	/// ///////////////////////////////////////////////////////////////////
	///
	/// singleton functionality
	///

	/// Singleton instance
	private static InstrumentationContext _sInstance = null;
	/// Singleton configuration
	private static MmiFacade _sCurrentMmiFacade = null;
	private static InstrumentationConfig _sInstrumentationConfig = null;
	private static ContextDescription _sInitialContextDescription = null;
	private static boolean _initialized = false;

	// FIXME holds and informs Listeners of the options menu
	private OptionsMenuObserver _mOptionsMenuObserver;


	/**
	 * Singleton initialization method
	 * @return true if well initialized
	 */
	public static boolean initializeInstrumentation(MmiFacade f, InstrumentationConfig ic) {

		//test log outputs
		Log.v(TAG, "log VERBOSE is enabled");
		Log.d(TAG, "log DEBUG is enabled");
		Log.i(TAG, "log INFO is enabled");
		Log.w(TAG, "log WARNING is enabled");
		Log.e(TAG, "log ERROR is enabled");

		if (f != null && ic != null){
			_sCurrentMmiFacade = f;
			_sInstrumentationConfig = ic;
			_sInstance = null;
			_initialized = true;
			get();
		}
		else{
			Log.e(TAG,"ERROR: Instrumentation context not initialized yet. Null parameters.");
		}
		return _initialized;
	}

	/**
	 * Singleton initialization method
	 * @return true if well initialized
	 */
	public static boolean initializeInstrumentation(MmiFacade f, InstrumentationConfig ic, ContextDescription cd) {
		if (cd != null){
			_sInitialContextDescription = cd;
		}
		else{
			Log.w(TAG,"WARNING: ContextDescription == null.");
		}
		return initializeInstrumentation(f,ic);
	}

	/**
	 * Singleton get method
	 * @return The global singleton instance
	 */
	public static InstrumentationContext get() {
		if (_initialized && _sInstance == null){
			_sInstance = new InstrumentationContext(_sCurrentMmiFacade,
					_sInstrumentationConfig, _sInitialContextDescription);
		}
		else if (!_initialized){
			Log.e(TAG,"ERROR: Instrumentation context not initialized yet.");
			return null;
		}
		return _sInstance;
	}

	/**
	 * Bug overcoming during testing process:
	 * library is not removed when application is restarted.
	 * This method creates a new context.
	 */
	public static void reset() {
		if (_initialized){
			_sInstance = new InstrumentationContext(_sCurrentMmiFacade,_sInstrumentationConfig,_sInitialContextDescription);
			Log.d(TAG,"Instrumentation context reset.");
		}
		else{
			Log.e(TAG,"ERROR: Instrumentation context not initialized yet.");
		}
	}
	
	public static void stop(){
		
	}



	/// ///////////////////////////////////////////////////////////////////
	///
	/// class
	///

	///
	/// public variables

	//android application context
	public ActivityManager activityManager;
	public Context appContext;
	public Activity mainActivity;

	//meta-filters
	public NewChildFilter newChildFilter;
	//filters
	public CompositeFilter allFilters;
	public AppTimingFilter appTimingFilter;
	public ContextPhysicalFilter contextPhysicalFilter;
	public ContextUserFilter contextUserFilter;
	public ContextSocialFilter contextSocialFilter;
	public ContextLocationTimeFilter contextLocationTimeFilter;
	public ContextDeviceFilter contextDeviceFilter;
	public ContextCommunicationFilter contextCommunicationFilter;
	public NavigationInputFilter navigationInputFilter;
	public MotionInputFilter motionInputFilter;
	public KeyInputFilter keyInputFilter;
	public ViewChangesFilter viewChangesFilter;
	public SpeechInputFilter speechInputFilter;

	/**
	 * Protected constructor
	 * @param facade
	 * @param config
	 * @param context
	 */
	protected InstrumentationContext(MmiFacade facade, InstrumentationConfig config, ContextDescription context) {

		// configure instrumentation
		_configureInstrumentation(config);

		// configure initial context (if exists)
		if (context != null)
			_configureInitialContext(context);

		//initialize application context
		activityManager = null;
		appContext = null;
		mainActivity = null;

		/// FIXME
		_mOptionsMenuObserver = new OptionsMenuObserver();
		registerOptionsMenuObserver(_mOptionsMenuObserver);
		///

		Log.i(TAG,"Instrumentation context ready.");
	}

	private void _configureInstrumentation(InstrumentationConfig config){

		//create a composite filter
		allFilters = new CompositeFilter(this,_sCurrentMmiFacade);

		// for each filter:
		//  - create filter
		//  - add to the composite filter
		//  - 

		/// interaction filters

		if (config.appTiming){
			appTimingFilter = new AppTimingFilter(this,_sCurrentMmiFacade);
			allFilters.addSubFilter(appTimingFilter);
			Log.i(TAG,"Enabling appTimingFilter.");
		}

		if (config.keyInput){
			keyInputFilter = new KeyInputFilter(this,_sCurrentMmiFacade);
			allFilters.addSubFilter(keyInputFilter);
			Log.i(TAG,"Enabling keyInputFilter.");
		}

		if (config.navigationInput){
			navigationInputFilter = new NavigationInputFilter(this,_sCurrentMmiFacade);
			allFilters.addSubFilter(navigationInputFilter);
			Log.i(TAG,"Enabling navigationInputFilter.");
		}

		if (config.motionInput){
			motionInputFilter = new MotionInputFilter(this,_sCurrentMmiFacade);
			allFilters.addSubFilter(motionInputFilter);
			Log.i(TAG,"Enabling motionInputFilter.");
		}

		if (config.speechInput){
			speechInputFilter = new SpeechInputFilter(this,_sCurrentMmiFacade);
			allFilters.addSubFilter(speechInputFilter);
			Log.i(TAG,"Enabling speechInputFilter.");
		}

		if (config.viewChanges){
			viewChangesFilter = new ViewChangesFilter(this,_sCurrentMmiFacade);
			allFilters.addSubFilter(viewChangesFilter);
			Log.i(TAG,"Enabling viewChangesFilter.");
		}

		/// context filters
		

		if (config.physicalEnvironment){
			contextPhysicalFilter = 
					new ContextPhysicalFilter(this,_sCurrentMmiFacade,config);
			allFilters.addSubFilter(contextPhysicalFilter);
			Log.i(TAG,"Enabling contextPhysicalFilter.");
		}

		if (config.user){
			contextUserFilter = 
					new ContextUserFilter(this,_sCurrentMmiFacade,config);
			allFilters.addSubFilter(contextUserFilter);
			Log.i(TAG,"Enabling contextUserFilter.");
		}

		if (config.socialContext){
			contextSocialFilter = 
					new ContextSocialFilter(this,_sCurrentMmiFacade,config);
			allFilters.addSubFilter(contextSocialFilter);
			Log.i(TAG,"Enabling contextSocialFilter.");
		}

		if (config.locationTime){
			contextLocationTimeFilter = 
					new ContextLocationTimeFilter(this,_sCurrentMmiFacade,config);
			allFilters.addSubFilter(contextLocationTimeFilter);
			Log.i(TAG,"Enabling contextLocationTimeFilter.");
		}

		if (config.device){
			contextDeviceFilter = 
					new ContextDeviceFilter(this,_sCurrentMmiFacade,config);
			allFilters.addSubFilter(contextDeviceFilter);
			Log.i(TAG,"Enabling contextDeviceFilter.");
		}
		
		if (config.communication){
			contextCommunicationFilter = 
					new ContextCommunicationFilter(this,_sCurrentMmiFacade,config);
			allFilters.addSubFilter(contextCommunicationFilter);
			Log.i(TAG,"Enabling contextCommunicationFilter.");
		}


		/// create child filter
		
		newChildFilter = new NewChildFilter(this,_sCurrentMmiFacade,allFilters);
		//also add this filter to the composite one
		allFilters.addSubFilter(newChildFilter);
		Log.i(TAG,"Enabling newChildFilter.");
	}

	private void _configureInitialContext(ContextDescription context){
		// push context values to all filters
		for (BaseFilter bf : allFilters.getSubFilters()){
			if (bf instanceof ContextBaseFilter){
				((ContextBaseFilter)bf).pushContextValues(context);
			}
		}
	}

	///
	/// public methods

	/**
	 * Method to notify "start" to interaction listeners
	 */
	public void startInteraction(){
		// step 1: initialize
		for (InteractionListener ic : allFilters.getSubFilters()){
			ic.onInteractionInitialize();
		}

		// step 2: start
		for (InteractionListener ic : allFilters.getSubFilters()){
			ic.onInteractionStart();
		}
	}

	/**
	 * Method to notify "end" to interaction listeners
	 */
	public void endInteraction(){
		for (InteractionListener ic : allFilters.getSubFilters()){
			ic.onInteractionEnd();
		}
	}

	/**
	 * Registers the main activity object into the instrumentation process
	 * using the default set of filters
	 * @param a the activity
	 * @throws InstrumentationException 
	 */
	public void registerMainActivity(Activity a) throws InstrumentationException{
		if (a == null)
			throw new InstrumentationException("In registerMainActivity :: Main activity is null");

		//set the main activity
		mainActivity = a;

		//set application context and activityManager
		if (activityManager == null)
			activityManager = (ActivityManager)a.getSystemService(Context.ACTIVITY_SERVICE);
		if (appContext == null)
			appContext = a.getApplicationContext();	

		//install filters on Views
		registerActivity(a,allFilters);
	}

	/**
	 * Registers a new activity object into the instrumentation process
	 * using the default set of filters
	 * @param a the activity
	 * @throws InstrumentationException 
	 */
	public void registerActivity(Activity a) throws InstrumentationException{
		if (a == null){
			throw new InstrumentationException("In registerActivity :: Activity is null");
		}

		//add activity to the list and
		//install default filter on Views
		registerActivity(a,allFilters);
	}

	/**
	 * Registers a new activity into the instrumentation process
	 * using a provided set of filters
	 * @param a the activity
	 * @param f the filter/s
	 * @throws InstrumentationException 
	 */
	public void registerActivity(Activity a, BaseFilter f){
		if (a == null){
			Log.w(TAG,"registerActivity :: Activity is null");
			return;
		}

		//add activity to the list
		//activities_.addLast(a);

		//add a sentinel view
		LayoutParams lp = new LayoutParams(0,0);
		a.addContentView(new SentinelView(a.getApplicationContext()),lp);

		//install filter on all views within the activity
		View topView = a.getWindow().getDecorView();
		if (topView == null){
			Log.e(TAG,"registerActivity :: Top view is null");
			return;
		}
		UiTraverser uit = new UiTraverser(topView);
		View aux = null;
		while (uit.hasNext())
		{
			aux = uit.next();
			if (aux != null){
				//Log.e(TAG,"@@@@@@@@@@@@@@ intalling on " + aux.toString());
				f.install(aux); 
			}
		}
	}

	/**
	 * Returns the main Activity af an Android application
	 * @return
	 */
	public Activity getMainActivity(){
		return mainActivity;
	}

	public void registerActionBar(ActionBar actionBar) throws InstrumentationException {
		if ( actionBar == null ) {
			throw new InstrumentationException("In registerActionBar :: action bar is null");
		}

		allFilters.install(actionBar);

	}

	public void registerMenuItem(MenuItem item, View parentView) throws InstrumentationException {
		if ( item == null ) {
			throw new InstrumentationException("registerMenuItem: item is null");
		}

		allFilters.install(item, parentView);

	}

	public void registerAlertDialog(AlertDialog dialog) throws InstrumentationException {
		if ( dialog == null ) {
			throw new InstrumentationException("registerAlertDialog: dialog is null");
		}

		// install the raw dialog (mainly the buttons) 
		allFilters.install(dialog);

		// Get the view of the AlertDialog and install filter on it.
		// The view contains all widgets that are not raw AlertDialog widgets (three buttons and a title - maybe more?)
		View internalView = getInternalView(dialog);
		allFilters.install(internalView);
	}


	private View getInternalView(AlertDialog dialog) {
		View internalView = null;

		try {
			// extract the ALertController from dialog
			Field fieldController = AlertDialog.class.getDeclaredField("mAlert");
			fieldController.setAccessible(true);
			Object controller = fieldController.get(dialog);

			// Class object of the AlertController
			String controllerClassName = controller.getClass().getName();
			Class<? extends Object> controllerClazz = Class.forName(controllerClassName);

			// Extract the view from the AlertContorller object
			Field fieldView = controllerClazz.getDeclaredField("mView");
			fieldView.setAccessible(true);
			internalView = (View) fieldView.get(controller);
		} catch (Exception e) {
			Log.i(TAG, "Colud not extract the internal view of AlertDialog. Error: " + e);
		}

		return internalView;
	}

	private void registerOptionsMenuObserver(OptionsMenuObserver observer) {
		allFilters.install(observer); 
	}

	// FIXME
	public OptionsMenuObserver getOptionsMenuObserver() {
		return _mOptionsMenuObserver;
	}
}
