package android.hci.extractor;

import org.json.JSONException;
import org.json.JSONObject;
import org.mmi.android.genericquest.QuestManager;
import org.mmi.android.instrumentation.ContextDescription;
import org.mmi.android.instrumentation.ContextDescription.LocationTime.MobilityLevel;
import org.mmi.android.instrumentation.ContextDescription.SocialContext.SocialArena;
import org.mmi.android.instrumentation.ContextDescription.SocialContext.SocialCompany;
import org.mmi.android.instrumentation.ContextDescription.User.EducationLevel;
import org.mmi.android.instrumentation.ContextDescription.User.PreviousExperience;
import org.mmi.android.instrumentation.ContextDescription.PhysicalEnvironment.Weather;
import org.mmi.android.instrumentation.ContextDescription.User.Gender;
import org.mmi.android.instrumentation.config.JSONConfiguration;

import android.util.Log;

public class utils {

	private static final String TAG = "android.hci.extractor.utils";

	public static void fillDummyValuesOnContextDescription(ContextDescription context){
		context.parseContextJSONValue("{\"q_value\" : \"age\", \"q_result\" : \"16\"}", QuestManager.Q_VALUE, QuestManager.Q_RESULT);
		context.parseContextJSONValue("{\"q_value\" : \"gender\", \"q_result\" : \"male\"}", QuestManager.Q_VALUE, QuestManager.Q_RESULT);
		context.parseContextJSONValue("{\"q_value\" : \"deviceType\", \"q_result\" : \"tablet\"}", QuestManager.Q_VALUE, QuestManager.Q_RESULT);
		context.parseContextJSONValue("{\"q_value\" : \"weather\", \"q_result\" : \"sunny\"}", QuestManager.Q_VALUE, QuestManager.Q_RESULT);
		context.parseContextJSONValue("{\"q_value\" : \"socialCompany\", \"q_result\" : \"with a person\"}", QuestManager.Q_VALUE, QuestManager.Q_RESULT);
		context.parseContextJSONValue("{\"q_value\" : \"socialArena\", \"q_result\" : \"work\"}", QuestManager.Q_VALUE, QuestManager.Q_RESULT);
		context.parseContextJSONValue("{\"q_value\" : \"location\", \"q_result\" : \"office/school\"}", QuestManager.Q_VALUE, QuestManager.Q_RESULT);
		context.parseContextJSONValue("{\"q_value\" : \"mobilityLevel\", \"q_result\" : \"sitting\"}", QuestManager.Q_VALUE, QuestManager.Q_RESULT);
	}


}
