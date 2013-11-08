package org.mmi.android.instrumentation;

import java.util.Calendar;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;
import org.mmi.android.instrumentation.ContextDescription.Device.DeviceType;
import org.mmi.android.instrumentation.ContextDescription.LocationTime.MobilityLevel;
import org.mmi.android.instrumentation.ContextDescription.LocationTime.UserLocation;
import org.mmi.android.instrumentation.ContextDescription.PhysicalEnvironment.Weather;
import org.mmi.android.instrumentation.ContextDescription.SocialContext.SocialArena;
import org.mmi.android.instrumentation.ContextDescription.SocialContext.SocialCompany;
import org.mmi.android.instrumentation.ContextDescription.User.EducationLevel;
import org.mmi.android.instrumentation.ContextDescription.User.Gender;
import org.mmi.android.instrumentation.ContextDescription.User.PreviousExperience;
import org.mmi.android.instrumentation.config.JSONConfiguration;


import android.location.Location;
import android.util.Log;

public class ContextDescription {

	private static final String TAG = "QuestManager";

	///
	/// variables and constructor
	///

	public PhysicalEnvironment physicalEnvironment;
	public User user;
	public SocialContext socialContext;
	public LocationTime locationTime;
	public Device device;
	public Communication communication;


	public ContextDescription(){
		physicalEnvironment = new PhysicalEnvironment();
		user = new User();
		socialContext = new SocialContext();
		locationTime = new LocationTime();
		device = new Device();
		communication = new Communication();
	}

	///
	/// inner classes
	///

	///
	///
	public static class PhysicalEnvironment{

		/// enum values
		public enum Weather {
			WEATHER_NONE,
			WEATHER_SUNNY, 
			WEATHER_CLOUDY, 
			WEATHER_WINDY, 
			WEATHER_RAINY, 
			WEATHER_SNOWY
		}

		/// variables
		public int temperature = 0; 
		public Weather weather = Weather.WEATHER_NONE;
		public int noise = 0;
		public int light = 0;
	}

	///
	///
	public static class User{

		/// enum values
		public enum Gender {
			GENDER_NONE,
			GENDER_MALE,
			GENDER_FEMALE, 
			GENDER_OTHER
		}

		public enum EducationLevel {
			EDUCATION_NONE,
			EDUCATION_HIGHSCHOOL,
			EDUCATION_PROFESSIONAL,
			EDUCATION_COLLEGE,
			EDUCATION_NOT_APPLICABLE
		}

		public enum PreviousExperience {
			PREEXPERIENCE_NONE,
			PREEXPERIENCE_LOW,
			PREEXPERIENCE_MEDIUM,
			PREEXPERIENCE_HIGH,
			PREEXPERIENCE_EXPERT
		}

		/// variables
		public int age = 0; 
		public Gender gender = Gender.GENDER_NONE;
		public EducationLevel educationLevel = EducationLevel.EDUCATION_NONE;
		public PreviousExperience previousExperience = PreviousExperience.PREEXPERIENCE_NONE;
	}

	///
	///
	public static class SocialContext{

		/// enum values
		public enum SocialCompany {
			COMPANY_NONE,
			COMPANY_ALONE,
			COMPANY_WITH_A_PERSON,
			COMPANY_WITH_A_GROUP
		}

		public enum SocialArena {
			ARENA_NONE,
			ARENA_DOMESTIC,
			ARENA_WORK,
			ARENA_EDUCATIONAL,
			ARENA_LEISURE
		}


		/// variables
		public SocialCompany socialCompany = SocialCompany.COMPANY_NONE;
		public SocialArena socialArena = SocialArena.ARENA_NONE;
	}

	///
	///
	public static class LocationTime{

		/// enum values
		public enum UserLocation {
			USER_LOCATION_NONE,
			USER_LOCATION_HOME,
			USER_LOCATION_OFFICE_SCHOOL,
			USER_LOCATION_STREET,
			USER_LOCATION_OTHER_INDOOR,
			USER_LOCATION_OTHER_OUTDOOR
		}

		public enum MobilityLevel {
			MOBILITY_NONE,
			MOBILITY_SITTING,
			MOBILITY_STANDING,
			MOBILITY_WALKING,
			MOBILITY_SPORTING,
			MOBILITY_DRIVING,
			MOBILITY_OTHER
		}

		/// variables
		public UserLocation userLocation = UserLocation.USER_LOCATION_NONE; 
		public Location geoLocation; 
		public MobilityLevel mobilityLevel = MobilityLevel.MOBILITY_NONE;
		public Calendar currentTime;
	}

	///
	///
	public static class Device{

		/// enum values
		public enum DeviceType {
			DEVICE_NONE,
			DEVICE_LAPTOP,
			DEVICE_TABLET,
			DEVICE_SMARTPHONE,
			DEVICE_MMPLAYER,
			DEVICE_OTHER
		}

		public enum ScreenSize {
			SCREEN_SIZE_NONE,
			SCREEN_SIZE_SMALL,
			SCREEN_SIZE_NORMAL,
			SCREEN_SIZE_LARGE
		}

		public enum ScreenResolution {
			SCREEN_RESOLUTION_NONE,
			SCREEN_RESOLUTION_SMALL,
			SCREEN_RESOLUTION_NORMAL,
			SCREEN_RESOLUTION_LARGE
		}

		public enum ScreenOrientation {
			ORIENTATION_NONE,
			ORIENTATION_LANDSCAPE,
			ORIENTATION_PORTRAIT
		}

		/// variables
		public DeviceType deviceType = DeviceType.DEVICE_NONE;
		public ScreenSize screenSize = ScreenSize.SCREEN_SIZE_NONE;
		public ScreenResolution screenResolution = ScreenResolution.SCREEN_RESOLUTION_NONE;
		public ScreenOrientation screenOrientation = ScreenOrientation.ORIENTATION_NONE;
		public int screenBrightnessLevel = 0;
		public int volumeLevel = 0;
		public int memoryLoad = 0;
		public int cpuLoad = 0;
	}



	///
	///
	public static class Communication{

		/// enum values
		public enum WirelessAccessType {
			WIRELESS_ACCESS_TYPE_NONE,
			WIRELESS_ACCESS_TYPE_MOBILE,
			WIRELESS_ACCESS_TYPE_WIFI,
			WIRELESS_ACCESS_TYPE_BLUETOOTH
		}

		/// variables
		public WirelessAccessType wirelessAccessType = WirelessAccessType.WIRELESS_ACCESS_TYPE_NONE;
		public String accessPointName = "";
		public int signalStrength = 0;
		public int receivedDataThroughput = 0;
		public int sentDataThroughput = 0;
		public int rtt = 0;
		public int srt = 0;
	}	

	///
	/// parsing method from JSON string
	///

	/**
	 * Adds a new value to the context description from a JSON string
	 * @param jsonString
	 * @param keyString
	 * @param valueString
	 * @return tru if result is OK; false otherwise
	 */
	public boolean parseContextJSONValue( 
			String jsonString, String keyString , String valueString ){

		// get the JSON object from the string
		JSONObject o = JSONConfiguration.parseJSONString(jsonString);
		if (o == null)
			Log.e(TAG,"(parseJSONValue) ERROR: result JSON object is null.");

		// get values from object

		String key = "";
		try {
			key = o.getString(keyString);
		} catch (JSONException e) {
			e.printStackTrace();
			return false;
		}
		String value = "";
		try {
			value = o.getString(valueString);
		} catch (JSONException e) {
			e.printStackTrace();
			return false;
		}

		if (key.equals("") || value.equals("")){
			Log.e(TAG,"(parseJSONValue) ERROR: key or value are empty. key = " + key + "; value = " + value);
			return false;
		}

		///
		/// set value

		try {

			/// /////////////////////////////////////////////////////////////////
			/// CONTEXT VALUES
			/// /////////////////////////////////////////////////////////////////

			//public static class PhysicalEnvironment
			//public int temperature = 0; 
			if (key.equals("temperature")){
				physicalEnvironment.temperature = 
						o.getInt(valueString);
			}
			//public Weather weather = Weather.WEATHER_NONE;
			else if (key.equals("weather")){
				if (value.equals("sunny"))
					physicalEnvironment.weather = Weather.WEATHER_SUNNY;
				else if (value.equals("cloudy"))
					physicalEnvironment.weather = Weather.WEATHER_CLOUDY;
				else if (value.equals("windy"))
					physicalEnvironment.weather = Weather.WEATHER_WINDY;
				else if (value.equals("rainy"))
					physicalEnvironment.weather = Weather.WEATHER_RAINY;
				else if (value.equals("snowy"))
					physicalEnvironment.weather = Weather.WEATHER_SNOWY;
			}
			//public int noise = 0;
			else if (key.equals("noise")){
				physicalEnvironment.noise = 
						o.getInt(valueString);
			}
			//public int light = 0;
			else if (key.equals("light")){
				physicalEnvironment.light = 
						o.getInt(valueString);
			}
			///
			///
			//public static class User{
			//public int age = 0; 
			else if (key.equals("age")){
				user.age = o.getInt(valueString);
			}
			//public Gender gender = Gender.GENDER_NONE;
			else if (key.equals("gender")){
				if (value.equals("male"))
					user.gender = Gender.GENDER_MALE;
				else if (value.equals("female"))
					user.gender = Gender.GENDER_FEMALE;
				else if (value.equals("other"))
					user.gender = Gender.GENDER_OTHER;
			}
			//public EducationLevel educationLevel = EducationLevel.EDUCATION_NONE;
			else if (key.equals("educationLevel")){
				//"high school, professional, college, not applicable"
				if (value.equals("high school"))
					user.educationLevel = EducationLevel.EDUCATION_HIGHSCHOOL;
				else if (value.equals("professional"))
					user.educationLevel = EducationLevel.EDUCATION_PROFESSIONAL;
				else if (value.equals("college"))
					user.educationLevel = EducationLevel.EDUCATION_COLLEGE;
				else if (value.equals("not applicable"))
					user.educationLevel = EducationLevel.EDUCATION_NOT_APPLICABLE;
			}
			//public PreviousExperience previousExperience = PreviousExperience.PREEXPERIENCE_NONE;
			else if (key.equals("previousExperience")){
				//"none, low, medium, high, expert"
				if (value.equals("none"))
					user.previousExperience = PreviousExperience.PREEXPERIENCE_NONE;
				else if (value.equals("low"))
					user.previousExperience = PreviousExperience.PREEXPERIENCE_LOW;
				else if (value.equals("medium"))
					user.previousExperience = PreviousExperience.PREEXPERIENCE_MEDIUM;
				else if (value.equals("high"))
					user.previousExperience = PreviousExperience.PREEXPERIENCE_HIGH;
				else if (value.equals("expert"))
					user.previousExperience = PreviousExperience.PREEXPERIENCE_EXPERT;
			}

			///
			///
			//public static class SocialContext{
			//public SocialCompany socialCompany = SocialCompany.COMPANY_NONE;
			else if (key.equals("socialCompany")){
				//"alone, with a person, with a group"
				if (value.equals("alone"))
					socialContext.socialCompany = SocialCompany.COMPANY_ALONE;
				else if (value.equals("with a person"))
					socialContext.socialCompany = SocialCompany.COMPANY_WITH_A_PERSON;
				else if (value.equals("with a group"))
					socialContext.socialCompany = SocialCompany.COMPANY_WITH_A_GROUP;
			}
			//public SocialArena socialArena = SocialArena.ARENA_NONE;
			else if (key.equals("socialArena")){
				//"domestic, work, educational, leisure"
				if (value.equals("domestic"))
					socialContext.socialArena = SocialArena.ARENA_DOMESTIC;
				else if (value.equals("work"))
					socialContext.socialArena = SocialArena.ARENA_WORK;
				else if (value.equals("educational"))
					socialContext.socialArena = SocialArena.ARENA_EDUCATIONAL;
				else if (value.equals("leisure"))
					socialContext.socialArena = SocialArena.ARENA_LEISURE;
			}

			///
			///
			//public static class LocationTime{
			//public UserLocation userLocation = UserLocation.USER_LOCATION_NONE; 
			else if (key.equals("location")){
				//"home, office/school, street, other indoor, other outdoor"
				if (value.equals("home"))
					locationTime.userLocation = UserLocation.USER_LOCATION_HOME;
				else if (value.equals("office/school"))
					locationTime.userLocation = UserLocation.USER_LOCATION_OFFICE_SCHOOL;
				else if (value.equals("street"))
					locationTime.userLocation = UserLocation.USER_LOCATION_STREET;
				else if (value.equals("other indoor"))
					locationTime.userLocation = UserLocation.USER_LOCATION_OTHER_INDOOR;
				else if (value.equals("other outdoor"))
					locationTime.userLocation = UserLocation.USER_LOCATION_OTHER_OUTDOOR;
				else
					locationTime.userLocation = UserLocation.USER_LOCATION_NONE;
			}
			//public Location geoLocation; 
			else if (key.equals("geoLocation")){
				//TODO
			}
			//public MobilityLevel mobilityLevel = MobilityLevel.MOBILITY_NONE;
			else if (key.equals("mobilityLevel")){
				//"sitting, standing, walking, sporting, driving, other"
				if (value.equals("sitting"))
					locationTime.mobilityLevel = MobilityLevel.MOBILITY_SITTING;
				else if (value.equals("standing"))
					locationTime.mobilityLevel = MobilityLevel.MOBILITY_STANDING;
				else if (value.equals("walking"))
					locationTime.mobilityLevel = MobilityLevel.MOBILITY_WALKING;
				else if (value.equals("sporting"))
					locationTime.mobilityLevel = MobilityLevel.MOBILITY_SPORTING;
				else if (value.equals("driving"))
					locationTime.mobilityLevel = MobilityLevel.MOBILITY_DRIVING;
				else if (value.equals("other"))
					locationTime.mobilityLevel = MobilityLevel.MOBILITY_OTHER;
			}
			//public Date currentTime;
			else if (key.equals("currentTime")){
				//TODO
			}

			///
			///
			//public static class Device{
			//public DeviceType deviceType = DeviceType.DEVICE_NONE;
			else if (key.equals("deviceType")){
				//"laptop, tablet, smartphone, multimedia player, other"
				if (value.equals("laptop"))
					device.deviceType = DeviceType.DEVICE_LAPTOP;
				else if (value.equals("tablet"))
					device.deviceType = DeviceType.DEVICE_TABLET;
				else if (value.equals("smartphone"))
					device.deviceType = DeviceType.DEVICE_SMARTPHONE;
				else if (value.equals("multimedia player"))
					device.deviceType = DeviceType.DEVICE_MMPLAYER;
				else if (value.equals("other"))
					device.deviceType = DeviceType.DEVICE_OTHER; 
			}
			/*		//public ScreenSize screenSize = ScreenSize.SCREEN_SIZE_NONE;
		else if (key.equals("screenSize"){
			//TODO
		}
		//public ScreenResolution screenResolution = ScreenResolution.SCREEN_RESOLUTION_NONE;
		else if (key.equals("screenResolution"){
			//TODO
		}
		//public ScreenOrientation screenOrientation = ScreenOrientation.ORIENTATION_NONE;
		else if (key.equals("screenOrientation"){
			//TODO
		}
		//public int screenBrightnessLevel = 0;
		else if (key.equals("screenBrightnessLevel"){
			//TODO
		}
		//public int volumeLevel = 0;
		else if (key.equals("volumeLevel"){
			//TODO
		}
		//public int memoryLoad = 0;
		else if (key.equals("memoryLoad"){
			//TODO
		}
		//public int cpuLoad = 0;
		else if (key.equals("cpuLoad"){
			//TODO
		}
			 */
			///
			///
			//public static class Communication{
			//public WirelessAccessType wirelessAccessType = WirelessAccessType.WIRELESS_ACCESS_TYPE_NONE;
			/*		else if (key.equals("wirelessAccessType"){
			//TODO
		}
		//public String accessPointName = "";
		else if (key.equals("accessPointName"){
			//TODO
		}
		//public int signalStrength = 0;
		else if (key.equals("signalStrength"){
			//TODO
		}
		//public int receivedDataThroughput = 0;
		else if (key.equals("receivedDataThroughput"){
			//TODO
		}
		//public int sentDataThroughput = 0;
		else if (key.equals("sentDataThroughput"){
			//TODO
		}
		//public int rtt = 0;
		else if (key.equals("rtt"){
			//TODO
		}
		//public int srt = 0;
		else if (key.equals("srt"){
			//TODO
		}
			 */

			return true;

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	///
	/// JSON configuration file strings
	///
	/*
	age
	//
    gender
    male
    female
    other
    //
    educationLevel
    high school
    professional
    college
    not applicable
    //
	previousExperience
	none
	low
	medium
	high
	expert
	//
	deviceType
	laptop
	tablet
	smartphone
	multimedia player
	other
	//
	temperature
	//
	weather
	sunny
	cloudy
	windy
	rainy
	snowy
	//
	noise
	//
	light
	//
	socialCompany
	alone
	with a person
	with a group
	//
	socialArena
	domestic
	work
	educational
	leisure
	//
	location
	home
	office/school
	street
	other indoor
	other outdoor
	//
	geoLocation
	//
	mobilityLevel
	sitting
	standing
	walking
	sporting
	driving
	other
	//
	currentTime
	 */

}
