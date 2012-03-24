/*
 * Copyright (C) 2009 The Android Open Source Project
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

import javax.microedition.khronos.opengles.GL10;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.FrameLayout;

import com.metaio.MobileSDKExample.MobileSDKExampleApplication;
import com.metaio.unifeye.UnifeyeDebug;
import com.metaio.unifeye.ndk.Vector2di;

/**
 * EXAMPLE 4
 * 
 * 
 */
public class GLES20Activity extends Activity {


	/**
	 * Wake lock to avoid screen time outs.
	 * 
	 * The application must request WAKE_LOCK permission.
	 */
	private PowerManager.WakeLock mWakeLock;

	/**
	 * Used for rendering of the scene.
	 */
	private GLSurfaceView mGLSurfaceView;

	/**
	 * Current application.
	 */
	private MobileSDKExampleApplication mApplication;

	/**
	 * The renderer
	 */
	private GLES20CameraImageRenderer mRenderer;

	/**
	 * The ARController controls the MobileSDK and sends events to the renderer.
	 */
	private ARController mARController;

	/**
	 * The tracking data to be used here. 
	 */
	private final String trackingDataFileInAssets = "TrackingData_MarkerlessFast.xml";

	/**
	 * Creates a new instance of the MobileSDK.
	 */
	private void initializeMobileSDK() {
		/*
		 * Create a new ARController with the application context and without
		 * using the MobileSDK renderer.
		 */
		mARController = new ARController(this);
		/* 
		 * Create an instance of the MobileSDK The required signature is stored
		 * in the Configuration class. If you change the package name the
		 * signatures as to be changed as well.
		 */
		mARController.initializeMobileSDK();
		/*
		 * Load the tracking data
		 */
		mARController.setTrackingData(trackingDataFileInAssets);
	}

	/**
	 * Called by the system when the Activity gets created. The layout is not
	 * available here yet.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mApplication = (MobileSDKExampleApplication) getApplication();

	}


	/**
	 * Try to create an instance of a OpenGL ES 2.0 renderer. Falls back to a ES
	 * 1.x renderer, if necessary.
	 */
	private void initOpenGLRenderer() {
		/*
		 * Get a GL-context from the system.
		 */
		mGLSurfaceView = new GLSurfaceView(this);
		if (detectOpenGLES20()) {
			/*
			 * Tell the surface view we want to create an OpenGL ES
			 * 2.0-compatible context, and set an OpenGL ES 2.0-compatible
			 * renderer.
			 */
			mGLSurfaceView.setEGLContextClientVersion(2);
			Vector2di cameraResolution = mARController.getCameraResolution();
			mRenderer = new GLES20CameraImageRenderer(this, cameraResolution.getX(), cameraResolution.getY());
		} else {
			throw new RuntimeException("OpenGL ES 2.0 must be supported!");
		}
		/*
		 * Link the different elements of this Activity
		 */
		mRenderer.setARController(mARController);
		mARController.setRenderer(mRenderer.getARControllerCallback());
		mGLSurfaceView.setRenderer(mRenderer);
		/*
		 * Attach the view to the content view, so that it is visible for the
		 * user.
		 */
		DisplayMetrics displayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		addContentView(mGLSurfaceView, new FrameLayout.LayoutParams(
				displayMetrics.widthPixels, displayMetrics.heightPixels));
		mGLSurfaceView.setZOrderMediaOverlay(true);
	}

	/**
	 * Checks whether OpenGL ES 2.0 is available on this device. 
	 * 
	 * @return true, if and only if OpenGL ES2.0 is available on this device.
	 */
	private boolean detectOpenGLES20() {
		ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		ConfigurationInfo info = am.getDeviceConfigurationInfo();
		return (info.reqGlEsVersion >= 0x20000);
	}

	/**
	 * Called by the system when the activity starts, hand as been created
	 * already.
	 */
	@Override
	protected void onStart() {

		super.onStart();
		try {
			initializeMobileSDK();
			initOpenGLRenderer();
		} catch (Exception e) {
			UnifeyeDebug.log(Log.ERROR, "Starting of Unifeye Mobile Failed! Please check the logcat output.");
			UnifeyeDebug.printStackTrace(Log.ERROR, e);
			
		}
	}

	/**
	 * Called by the system when the view should resume.
	 */
	@Override
	protected void onResume() {
		// Ideally a game should implement onResume() and onPause()
		// to take appropriate action when the activity looses focus
		super.onResume();
		mGLSurfaceView.onResume();

		if (mWakeLock != null)
			mWakeLock.acquire();

		mARController.onResume();
	}

	/**
	 * Called by the system when the view should pause.
	 */
	@Override
	protected void onPause() {
		// Ideally a game should implement onResume() and onPause()
		// to take appropriate action when the activity looses focus
		super.onPause();
		mGLSurfaceView.onPause();
		UnifeyeDebug.log("ARViewActivity.onPause()");

		if (mWakeLock != null)
			mWakeLock.release();

		mARController.onPause();

	}


	/**
	 * Called by the renderer before rendering the image. You don't need to do
	 * anything here, but if you wish to do something in the GL-context it's
	 * possible here.
	 * 
	 * @param glUnused OpenGL 1.0 context. Not needed here. 
	 */
	public void onDrawFrame(GL10 glUnused) {

	}




}
