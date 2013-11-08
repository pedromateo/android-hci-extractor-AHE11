package org.mmi.android.instrumentation;


import java.util.Calendar;
import java.util.Date;

import org.mmi.android.instrumentation.ContextDescription.Communication.WirelessAccessType;
import org.mmi.android.instrumentation.ContextDescription.Device.ScreenOrientation;
import org.mmi.android.instrumentation.ContextDescription.Device.ScreenResolution;
import org.mmi.android.instrumentation.ContextDescription.Device.ScreenSize;
import org.mmi.android.instrumentation.ContextDescription.SocialContext.SocialArena;
import org.mmi.android.instrumentation.ContextDescription.SocialContext.SocialCompany;
import org.mmi.android.instrumentation.ContextDescription.User.EducationLevel;
import org.mmi.android.instrumentation.ContextDescription.User.Gender;
import org.mmi.android.instrumentation.ContextDescription.User.PreviousExperience;
import org.mmi.android.instrumentation.filters.*;
import org.mmi.android.instrumentation.ContextDescription;

import android.location.Location;

/**
 * Facade base interface to connect the Android Intrumentation Framework
 * with other projects.
 * 
 * @author mateo-navarro.pedro
 *
 */
public interface MmiFacade {

	///
	/// ////////////////////////////////////////////
	///
	/// application timing methods
	///
	/// ////////////////////////////////////////////
	///

	///
	/// dialogue
	///

	/**
	 * Method called to notify interaction start.
	 * @param ms milliseconds (set 0 to use factory timestamps)
	 */
	public abstract void interactionStarts(long ms);

	/**
	 * Method called to notify interaction end.
	 * @param ms milliseconds (set 0 to use factory timestamps)
	 */
	public abstract void interactionEnds(long ms);

	///
	/// system turn
	///

	/**
	 * Method called to notify system turn start.
	 * @param ms milliseconds (set 0 to use factory timestamps)
	 */
	public abstract void systemTurnStarts(long ms);

	/**
	 * Method called to notify system feedback start.
	 * @param ms milliseconds (set 0 to use factory timestamps)
	 */
	public abstract void systemFeedbackStarts(long ms);

	/**
	 * Method called to notify system action start.
	 * @param ms milliseconds (set 0 to use factory timestamps)
	 */
	public abstract void systemActionStarts(long ms);

	/**
	 * Method called to notify system action end.
	 * @param ms milliseconds (set 0 to use factory timestamps)
	 */
	public abstract void systemActionEnds(long ms);

	/**
	 * Method called to notify system turn end.
	 * @param ms milliseconds (set 0 to use factory timestamps)
	 */
	public abstract void systemTurnEnds(long ms);

	///
	/// user turn
	///

	/**
	 * Method called to notify user turn start.
	 * @param ms milliseconds (set 0 to use factory timestamps)
	 */
	public abstract void userTurnStarts(long ms);

	/**
	 * Method called to notify user feedback start.
	 * @param ms milliseconds (set 0 to use factory timestamps)
	 */
	public abstract void userFeedbackStarts(long ms);

	/**
	 * Method called to notify user action start.
	 * @param ms milliseconds (set 0 to use factory timestamps)
	 */
	public abstract void userActionStarts(long ms);

	/**
	 * Method called to notify user action end.
	 * @param ms milliseconds (set 0 to use factory timestamps)
	 */
	public abstract void userActionEnds(long ms);

	/**
	 * Method called to notify user turn end.
	 * @param ms milliseconds (set 0 to use factory timestamps)
	 */
	public abstract void userTurnEnds(long ms);


	///
	/// ////////////////////////////////////////////
	///
	/// gui input methods
	///
	/// ////////////////////////////////////////////
	///

	/**
	 * This method should be called on touch event
	 * @param ms timestamp
	 */
	public abstract void touch(long ms);

	/**
	 * This method should be called on click event
	 * @param ms timestamp
	 */
	public abstract void click(long ms);

	/**
	 * This method should be called on scroll event
	 * @param ms timestamp
	 */
	public abstract void scroll(long ms);

	/**
	 * This method should be called on key-text (e.g. the "a"
	 * character) event
	 * @param ms timestamp
	 */
	public abstract void keytext(long ms);

	/**
	 * This method should be called on key-command (e.g. the
	 * "return" key) event
	 * @param ms timestamp
	 */
	public abstract void keycommand(long ms);

	/**
	 * This method should be called on key-explore (e.g. down
	 * arrow) event
	 * @param ms timestamp
	 */
	public abstract void keyexplore(long ms);

	///
	/// ////////////////////////////////////////////
	///
	/// speech input methods
	///
	/// ////////////////////////////////////////////
	///


	/// words

	/**
	 * Method called when user speech input. This method is used
	 * for words.
	 * @param ms timestamp
	 * @param n number of elements
	 */
	public abstract void overallWords(long ms, int n);

	/**
	 * Method called when user speech input. This method is used
	 * for words.
	 * @param ms timestamp
	 * @param n number of elements
	 */
	public abstract void substitutedWords(long ms, int n);

	/**
	 * Method called when user speech input. This method is used
	 * for words.
	 * @param ms timestamp
	 * @param n number of elements
	 */
	public abstract void deletedWords(long ms, int n);

	/**
	 * Method called when user speech input. This method is used
	 * for words.
	 * @param ms timestamp
	 * @param n number of elements
	 */
	public abstract void insertedWords(long ms, int n);

	/// sentences

	/**
	 * Method called when user speech input. This method is used
	 * for sentences.
	 * @param ms timestamp
	 * @param n number of elements
	 */
	public abstract void overallSentences(long ms, int n);

	/**
	 * Method called when user speech input. This method is used
	 * for sentences.
	 * @param ms timestamp
	 * @param n number of elements
	 */
	public abstract void substitutedSentences(long ms, int n);

	/**
	 * Method called when user speech input. This method is used
	 * for sentences.
	 * @param ms timestamp
	 * @param n number of elements
	 */
	public abstract void deletedSentences(long ms, int n);

	/**
	 * Method called when user speech input. This method is used
	 * for sentences.
	 * @param ms timestamp
	 * @param n number of elements
	 */
	public abstract void insertedSentences(long ms, int n);

	/// concepts

	/**
	 * Method called when user speech input. This method is used
	 * for concepts.
	 * @param ms timestamp
	 * @param n number of elements
	 */
	public abstract void overallConcepts(long ms, int n);

	/**
	 * Method called when user speech input. This method is used
	 * for concepts.
	 * @param ms timestamp
	 * @param n number of elements
	 */
	public abstract void substitutedConcepts(long ms, int n);

	/**
	 * Method called when user speech input. This method is used
	 * for concepts.
	 * @param ms timestamp
	 * @param n number of elements
	 */
	public abstract void deletedConcepts(long ms, int n);

	/**
	 * Method called when user speech input. This method is used
	 * for concepts.
	 * @param ms timestamp
	 * @param n number of elements
	 */
	public abstract void insertedConcepts(long ms, int n);

	///
	/// parsing results
	///

	/**
	 * Method called when user speech input to indicate a
	 * parsing result.
	 * @param ms timestamp
	 */
	public abstract void correctlyParsedUtterance(long ms);

	/**
	 * Method called when user speech input to indicate a
	 * parsing result.
	 * @param ms timestamp
	 */
	public abstract void partiallyParsedUtterance(long ms);

	/**
	 * Method called when user speech input to indicate a
	 * parsing result.
	 * @param ms timestamp
	 */
	public abstract void incorrectlyParsedUtterance(long ms);

	///
	/// ////////////////////////////////////////////
	///
	/// motion input methods
	///
	/// ////////////////////////////////////////////
	///

	/**
	 * Method called when user do a motion-tilt.
	 * @param ms timestamp
	 */
	public abstract void tilt(long ms);
	
	/**
	 * Method called when user do a motion-twist.
	 * @param ms timestamp
	 */
	public abstract void twist(long ms);

	/**
	 * Method called when user do a motion-shake.
	 * @param ms timestamp
	 */
	public abstract void shake(long ms);

	
	///
	/// ////////////////////////////////////////////
	///
	/// gui output
	///
	/// ////////////////////////////////////////////
	///

	/**
	 * Method called when system GUI output.
	 * @param ms timestamp
	 * @param n elements
	 */
	public abstract void newGuiElements(long ms, int n);

	/**
	 * Method called when system GUI output.
	 * @param ms timestamp
	 * @param n elements
	 */
	public abstract void newGuiFeedback(long ms, int n);

	/**
	 * Method called when system GUI output.
	 * @param ms timestamp
	 * @param n elements
	 */
	public abstract void newGuiNoise(long ms, int n);

	/**
	 * Method called when system GUI output.
	 * @param ms timestamp
	 * @param n elements
	 */
	public abstract void newGuiQuestions(long ms, int n);

	/**
	 * Method called when system GUI output.
	 * @param ms timestamp
	 * @param n elements
	 */
	public abstract void newGuiConcepts(long ms, int n);



	///
	/// ////////////////////////////////////////////
	///
	/// speech output
	///
	/// ////////////////////////////////////////////
	///

	/**
	 * Method called when system speech output.
	 * @param ms timestamp
	 * @param n elements
	 */
	public abstract void newSpeechElements(long ms, int n);

	/**
	 * Method called when system speech output.
	 * @param ms timestamp
	 * @param n elements
	 */
	public abstract void newSpeechFeedback(long ms, int n);

	/**
	 * Method called when system speech output.
	 * @param ms timestamp
	 * @param n elements
	 */
	public abstract void newSpeechNoise(long ms, int n);

	/**
	 * Method called when system speech output.
	 * @param ms timestamp
	 * @param n elements
	 */
	public abstract void newSpeechQuestions(long ms, int n);

	/**
	 * Method called when system speech output.
	 * @param ms timestamp
	 * @param n elements
	 */
	public abstract void newSpeechConcepts(long ms, int n);

	public enum PromptType{
		PROMPT_CLOSED,
		PROMPT_HALFOPEN,
		PROMPT_NOQUESTION,
		PROMPT_OPEN
	}

	/**
	 * Method called to denote the prompt type
	 * @param ms
	 * @param type
	 */
	public abstract void newPromptType(long ms, PromptType type);

	///
	/// ////////////////////////////////////////////
	///
	/// metacomm
	///
	/// ////////////////////////////////////////////
	///

	///
	/// general
	///

	/**
	 * Method called when metacommunication data is
	 * collected. This one is called when a help turn.
	 * @param ms timestamp
	 */
	public abstract void isHelpTurn(long ms);

	/**
	 * Method called when metacommunication data is
	 * collected. This one is called when a correction turn.
	 * @param ms timestamp
	 */
	public abstract void isCorrectionTurn(long ms);

	///
	/// system
	///

	/**
	 * Method called when system metacommunication data is
	 * collected. This one is called when an ASR rejection.
	 * @param ms timestamp
	 */
	public abstract void isASRRejection(long ms);

	/**
	 * Method called when system metacommunication data is
	 * collected. This one is called when a DIV rejection.
	 * @param ms timestamp
	 */
	public abstract void isDIVRejection(long ms);

	/**
	 * Method called when system metacommunication data is
	 * collected. This one is called when a GR rejection.
	 * @param ms timestamp
	 */
	public abstract void isGRRejection(long ms);

	///
	/// user
	///

	/**
	 * Method called when user metacommunication data is
	 * collected. This one is called when a timeout.
	 * @param ms timestamp
	 */
	public abstract void isTimeout(long ms);

	/**
	 * Method called when user metacommunication data is
	 * collected. This one is called when a cancel intent.
	 * @param ms timestamp
	 */
	public abstract void isCancel(long ms);

	/**
	 * Method called when user metacommunication data is
	 * collected. This one is called when a restart intent.
	 * @param ms timestamp
	 */
	public abstract void isRestart(long ms);

	/**
	 * Method called when user metacommunication data is
	 * collected. This one is called when a bargein.
	 * @param ms timestamp
	 * @param success result of bargein
	 */
	public abstract void isBargein(long ms, boolean success);

	///
	/// ////////////////////////////////////////////
	///
	/// context update methods
	///
	/// ////////////////////////////////////////////
	///


	/**
	 * This method should be called on physical context change
	 * @param temperature
	 * @param weather
	 * @param noise
	 * @param light
	 */
	public abstract void physicalContextChange(long ms, 
			int temperature, 
			ContextDescription.PhysicalEnvironment.Weather weather, 
			int noise, 
			int light);

	/**
	 * This method should be called on user context change
	 * @param age
	 * @param gender
	 * @param educationLevel
	 * @param previousExperience
	 */
	public abstract void userContextChange(long ms, 
			int age, 
			Gender gender,
			EducationLevel educationLevel,
			PreviousExperience previousExperience);

	/**
	 * This method should be called on social context change
	 * @param company
	 * @param arena
	 */
	public abstract void socialContextChange(long ms, 
			SocialCompany company,
			SocialArena arena);


	/**
	 * This method should be called on time context change
	 * @param currentTime
	 */
	public abstract void timeContextChange(long ms, 
			Calendar currentTime);
	
	/**
	 * This method should be called on location context change
	 * @param location
	 * @param geoLocation
	 * @param mobilityLevel
	 */
	public abstract void locationContextChange(long ms, 
			ContextDescription.LocationTime.UserLocation location,
			Location geoLocation,
			ContextDescription.LocationTime.MobilityLevel mobilityLevel);

	/**
	 * This method should be called on device context change
	 * @param deviceType
	 * @param screenSize
	 * @param screenResolution
	 * @param screenOrientation
	 * @param screenBrightnessLevel
	 * @param volumeLevel
	 * @param memoryLoad
	 * @param cpuLoad
	 */
	public abstract void deviceContextChange(long ms, 
			ContextDescription.Device.DeviceType deviceType,
			ContextDescription.Device.ScreenSize screenSize,
			ContextDescription.Device.ScreenResolution screenResolution,
			ContextDescription.Device.ScreenOrientation screenOrientation,
			int screenBrightnessLevel,
			int volumeLevel,
			int memoryLoad,
			int cpuLoad);

	/**
	 * This method should be called on communication context change
	 * @param wirelessAccessType
	 * @param accessPointName
	 * @param signalStrength
	 * @param receivedDataThroughput
	 * @param sentDataThroughput
	 * @param rtt
	 * @param srt
	 */
	public abstract void communicationContextChange(long ms, 
			ContextDescription.Communication.WirelessAccessType wirelessAccessType,
			String accessPointName,
			int signalStrength,
			int receivedDataThroughput,
			int sentDataThroughput,
			int rtt,
			int srt	);
}