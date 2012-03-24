/*
 * Copyright (C) 2011 metaio GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.metaio.MobileSDKExample.opengl20;

import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import android.app.Activity;
import android.util.Log;
import android.widget.FrameLayout;

import com.metaio.MobileSDKExample.Configuration;
import com.metaio.tools.io.AssetsManager;
import com.metaio.unifeye.UnifeyeDebug;
import com.metaio.unifeye.UnifeyeSensorsManager;
import com.metaio.unifeye.ndk.AS_UnifeyeSDKMobile;
import com.metaio.unifeye.ndk.ECOLOR_FORMAT;
import com.metaio.unifeye.ndk.IUnifeyeMobileAndroid;
import com.metaio.unifeye.ndk.IUnifeyeMobileCallback;
import com.metaio.unifeye.ndk.ImageStruct;
import com.metaio.unifeye.ndk.Pose;
import com.metaio.unifeye.ndk.PoseVector;
import com.metaio.unifeye.ndk.Vector2di;

/***
 * This class provides factory methods for convenience, so that the
 * initialization of the system don't have to be reprogrammed for different
 * views again.
 * 
 * Furthermore this class is not restricted to be used by a View only, so that
 * you can control the AR-functionality of your application independent from the
 * view cycle of the system.
 * 
 * Note that this class does not depend on a specific Application class, so that
 * you can use it with any specialized class.
 * 
 * @author tim.oppermann
 * 
 */
public class ARController implements Closeable {

	private IUnifeyeMobileAndroid mMobileSDK;
	
	private MobileSDKCallback mMobileSDKCallback;

	private UnifeyeSensorsManager mSensorsManager;

	private Activity mOwner;

	private ICallback mTrackingCallback;

	private Vector2di mCameraResolution;

	/**
	 * To receive callbacks from the accelerometer, the compass or GPS you can define
	 * a class that implements UnifeyeSensorsManager.Callback and pass it to this member;
	 */
	private UnifeyeSensorsManager.Callback mSensorsCallback;

	/**
	 * Used to trigger events when the tracking gets lost or starts. 
	 * It maps the COS-ID to its visibility. 
	 */
	private HashMap<Integer, Boolean> mIsCosTracking = new HashMap<Integer, Boolean>();


	private boolean mIsCameraActivated;

	/**
	 * Creates the Controller without using the MobileSDK renderer
	 * 
	 * @param applicationContext
	 *            Your application. You can get it with getApplication() from
	 *            inside an Activity.
	 */
	public ARController(Activity owner) {
		mOwner = owner;

	}

	/**
	 * Links the renderer with the MobileSDK
	 * 
	 * @param renderer
	 */
	public void setRenderer(ARController.ICallback renderer) {

		mTrackingCallback = renderer;
	}



	/**
	 * Create and configure an MobileSDK instance.
	 * 
	 * Call this method onStart() of your Activity.
	 * 
	 */
	public void initializeMobileSDK() throws RuntimeException {
		if (mMobileSDK == null) {

			// Make sure to provide a valid application signature
			mMobileSDK = AS_UnifeyeSDKMobile.CreateUnifeyeMobileAndroid( mOwner, Configuration.signature);
			mMobileSDK.registerSensorsComponent(mSensorsManager);
			mMobileSDKCallback = new MobileSDKCallback();
			mMobileSDK.registerCallback(mMobileSDKCallback);
			if (mMobileSDK == null) {
				throw new RuntimeException(
						"Failed to create the MobileSDK! "
						+ "Please check your license key in the Configuration class.");
			}
			activateCamera();
		} else {
			throw new RuntimeException(
			"There is already an instance of the MobileSDK running.");
		}
	}
 
	/**
	 * Activates the camera. Needs to be called to start the tracking.  
	 */
	private  void activateCamera() {
	
		// create a empty layout, required for the camera on some devices
		mOwner.setContentView( new FrameLayout( mOwner ) ); 
	
		/*
		 * The resolution is a more a proposal than a forced value.
		 * We can't assume that the actual resolution will be the same
		 * as the desired. 
		 */
		mCameraResolution = mMobileSDK.startCamera(
				Configuration.Camera.deviceId, // id of device	
				Configuration.Camera.resolutionX, // width in pixels of
				Configuration.Camera.resolutionY); // height in pixels of

		mIsCameraActivated = true;
		

	}

	public UnifeyeSensorsManager createUnifeyeSensorsManager() {
		if (mSensorsManager == null) {
			mSensorsManager = new UnifeyeSensorsManager(mOwner);
		}
		return mSensorsManager;
	}

	
	/**
	 * Called by the renderer on each frame. - Reads the sensors of the device.
	 * - Performs the tracking on the current camera image. - Pushes the new
	 * camera image to the renderer as a texture.
	 */
	public void onDrawFrame() {
		if ( !mIsCameraActivated) 
		{
			UnifeyeDebug.log(Log.VERBOSE, "Waiting for the camera to be started...");
			return;
		}
		try {

			// Track the camera image
			mMobileSDK.requestCameraImage();
			mMobileSDK.render();
		
			for (int cosId = 1; cosId <= mMobileSDK
			.getNumberOfDefinedCoordinateSystems(); cosId++) {

				float[] trackingPose = new float[16];
				mMobileSDK.getTrackingValues(cosId, trackingPose);
				final Pose trackingValues = mMobileSDK.getTrackingValues(cosId);
				if (trackingValues.getQuality() > 0) {
					if (mIsCosTracking.get(cosId) != true) {
						mIsCosTracking.put(cosId, true);
						mTrackingCallback.onPatternDetected(cosId);
					}
					mTrackingCallback.onTrackingSuccessful(cosId, trackingPose);
				} else if (mIsCosTracking.get(cosId) != false) {
					
					mIsCosTracking.put(cosId, false);
					mTrackingCallback.onTrackingLost(cosId);
				}
				/*
				 * If you like to use LLA-markers or bar-codes with this example you 
				 * might want to use the following out commented lines of code and
				 * extend the ICallback interface by the respective methods.
				 */
				/*
				final String additionalValues = trackingValues.getAdditionalValues();
				if ( additionalValues != null && additionalValues.length() > 0 )
				{
					mTrackingCallback.onAdditionalValues(cosId, additionalValues);
				}
				final LLACoordinate llaCoordinates = trackingValues.getLlaCoordinate();
				if ( llaCoordinates != null )
				{
					mTrackingCallback.onLLACoordinate(cosId, llaCoordinates);
				}
				 */
			}

		} catch (Exception e) {
			UnifeyeDebug.printStackTrace(Log.ERROR, e);
		}

	}

	/**
	 * Tries to load the specified tracking data.
	 * 
	 * @param trackingDataFileInAssets
	 *            Name of the tracking data file as it appears in the assets
	 *            folder.
	 * @return true on success; false if: the MobileSDK has not been started,
	 *         the path is invalid or the file itself is invalid.
	 */
	public boolean setTrackingData(final String trackingDataFileInAssets) {

		if (mMobileSDK == null) {
			throw new RuntimeException(
					"There needs to be an instance of the MobileSDK to load tracking data."
					+ "Please use initializeMobileSDK to create one. If that method fails, your "
					+ "Licencse key in the Configuration is probably invalid.");

		}
		try {

			final String trackingDataFile = AssetsManager
			.getAssetPath(trackingDataFileInAssets);
			if (trackingDataFile == null) {
				throw new FileNotFoundException(trackingDataFile
						+ " not found!");
			}
			boolean success = mMobileSDK.setTrackingData(trackingDataFile);

			if (!success) {
				UnifeyeDebug.log(Log.ERROR, "Failed to load tracking data from "
						+ trackingDataFile + " (referenced as : "
						+ trackingDataFileInAssets + " in the assets folder)");
				return false;
			}
			for (int cosId = 1; cosId <= mMobileSDK
			.getNumberOfDefinedCoordinateSystems(); cosId++) {
				mIsCosTracking.put(cosId, false);
			}
			UnifeyeDebug.log("Tracking Data Loaded!");
		} catch (Exception e) {

			UnifeyeDebug.printStackTrace(Log.ERROR, e);
			return false;
		}

		return true;
	}

	/**
	 * Register an object to be called on change of sensor data. This includes
	 * GPS, Accelerometer and Compass.
	 * 
	 * @param callbackTarget
	 *            the object to be notified.
	 * @return success
	 */
	public boolean registerSensorsCallback(
			UnifeyeSensorsManager.Callback callbackTarget) {
		if (mSensorsManager != null) {

			mSensorsManager.registerCallback(callbackTarget);
			return true;
		}
		return false;
	}

	@Override
	public void close() throws IOException {

		if (mMobileSDK != null) {
			mMobileSDK.delete();
			mMobileSDK = null;
		}

		if (mSensorsManager != null) {
			mSensorsManager.registerCallback(null);
			mSensorsManager = null;
		}

		mOwner = null;
		
		mSensorsCallback = null;
		mTrackingCallback = null;
		mIsCosTracking = null;
		mCameraResolution = null;
		System.runFinalization();
		System.gc();

	}

	public void onPause() {

		if (mSensorsManager != null) {
			mSensorsManager.registerCallback(null);
			mSensorsManager = null;
		}
	}

	public void onResume() {

		if (mSensorsManager == null) {
			mSensorsManager = new UnifeyeSensorsManager(mOwner);
		}

		// Open all sensors
		mSensorsManager.registerCallback(mSensorsCallback);
	}

	public void onStop() {

		if (mMobileSDK != null) {
			// Disable camera
			mMobileSDK.stopCamera();
		}

	}

	public void onSurfaceCreated()
	{
		// we are implementing our own renderer, so we do not need to
		// initilaize for OpenGL in the MobileSDK
		/*mMobileSDK.initializeRenderer(
				mCameraResolution.getX(),
				mCameraResolution.getY(),
				ERENDER_SYSTEM.ERENDER_SYSTEM_OPENGL_ES_2_0); */
	}

	public void onSurfaceChanged() {
		float[] projectionMatrix = new float[16];
		mMobileSDK.getProjectionMatrix(projectionMatrix, false);

		mTrackingCallback.setGeometryProjectionMatrix(projectionMatrix);
	}


	
	
	public class MobileSDKCallback extends IUnifeyeMobileCallback
	{
		/**
		 *  Update the rendered image.
		 */
		@Override
		public void onNewCameraFrame(ImageStruct cameraFrame) {
		
			ECOLOR_FORMAT format = cameraFrame.getColorFormat();
			if ( format == ECOLOR_FORMAT.ECF_A8R8G8B8 )
			{
				
				byte[] imageBuffer = cameraFrame.getBuffer();
				mTrackingCallback.setCameraTextureSource(imageBuffer,
						cameraFrame.getWidth(), cameraFrame.getHeight());
			}
		}
		
		/**
		 *  Will be called when new tracking information arrives
		 */
		@Override
		public void  onTrackingEvent(PoseVector poses){
			
		}
	}
	
	/**
	 * Events triggered my the ARController.
	 * 
	 * @author tim.oppermann
	 * 
	 */
	public interface ICallback {
		/**
		 * Triggered if a pattern was invisible and gets visible again.
		 * 
		 * @param cosId
		 *            The number of the coordinate system (starting from 1).
		 */
		public void onPatternDetected(final int cosId);


		/**
		 * Triggered if a pattern was visible and can't be found anymore.
		 * 
		 * @param cosId
		 *            The number of the coordinate system (starting from 1).
		 */
		public void onTrackingLost(final int cosId);

		/**
		 * Triggered if there is a valid pose for the target coordinate system.
		 * 
		 * @param cosId
		 *            The number of the coordinate system (starting from 1).
		 * @param matrix
		 *            The pose of the pattern.
		 */
		public void onTrackingSuccessful(final int cosId, final float[] matrix);

		/**
		 * Provides a new camera image.
		 * 
		 * @param bitmap
		 *            A bitmap-array containing the image sub-pixel-wise.
		 * @param width
		 *            Width in pixel of the image.
		 * @param height
		 *            Height in pixel of the image.
		 */
		public void setCameraTextureSource(final byte[] bitmap,
				final int width, final int height);

		/**
		 * Provides the projection matrix of the scene. It includes intrinsic
		 * camera parameters.
		 * 
		 * @param projectionMatrix
		 *            The projection matrix of the scene. Can be used with
		 *            OpenGL directly.
		 */
		public void setGeometryProjectionMatrix(final float[] projectionMatrix);

	}



	public Vector2di getCameraResolution() {

		return mCameraResolution;
	}

}
