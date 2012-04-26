/**
 * ARViewActivitySimple.java
 * Example SDK Internal
 *
 * Created by Arsalan Malik on 08.03.2011
 * Copyright 2011 metaio GmbH. All rights reserved.
 *
 */

package com.metaio.MobileSDKExample.simple;

import android.content.Intent;
import android.util.Log;

import com.metaio.unifeye.UnifeyeDebug;
import com.metaio.unifeye.ndk.IUnifeyeMobileGeometry;
import com.metaio.unifeye.ndk.Vector3d;
import com.metaio.unifeye.ndk.Vector4d;
import java.lang.Math;
/**
 * EXAMPLE 1
 * 
 * This is a simple AR activity which shows 2D planar marker tracking with
 * animations. The target pattern and the model is MetaioMan.
 * 
 * Please visit the following link for a detailed explanation. 
 * {@link http://docs.metaio.com/bin/view/Main/HelloAugmentedWorldExample}
 * 
 * @author arsalan.malik, tim.oppermann
 * 
 */

public class HelloAugmentedWorldActivity extends ARViewActivity  {
	
	/**
	 * The geometry to be displayed
	 */
	private IUnifeyeMobileGeometry mGeometry;
	private IUnifeyeMobileGeometry mGeometry2;
	private IUnifeyeMobileGeometry mGeometry3;
	private IUnifeyeMobileGeometry mGeometry4;
	private IUnifeyeMobileGeometry mButton;
	/**
	 * Tracking file you like to use. The file must be within the assets folder
	 * of this project. The reference image and explanation can be found at
	 * http://docs.metaio.com/bin/view/Main/UnifeyeMobileTrackingConfiguration
	 */
	private final String mTrackingDataFileName = "TrackingData_MarkerlessFast.xml";

	/**
	 * This tracking configuration used Markerless 3D tracking. You can use
	 * the meatioman_target to veriy the tracking
	 */
	private final String mTrackingDataML3D= "TrackingData_ML3D.xml";

	/**
	 * This tracking configuration used ID Markers for tracking
	 */
	private final String mTrackingDataMarker = "TrackingData_Marker.xml";

	
	/**
	 * Gets called by the super-class after the GLSurface has been created. 
	 * It runs on the OpenGL-thread.
	 */
	@Override
	protected void loadUnifeyeContents() {
		try {
			//Intent myIntent = new Intent(HelloAugmentedWorldActivity.this,Receiver.class);

			// Load Tracking data
			loadTrackingData( mTrackingDataFileName ); 

			// Load all geometry
			mGeometry = loadGeometry("darthvader/darthvader.md2");
			mGeometry.setVisible(true);
			mGeometry.setMoveRotation(new Vector4d(1f,0f,0f,1.57f));
			mGeometry.setMoveRotation(new Vector4d(0f,0f,1f,1.57f),true);
			mGeometry.setMoveTranslation(new Vector3d(0,100,0));
			mGeometry.startAnimation("Stand", true);
			
			mGeometry2 = loadGeometry("darthvader/weapon.md2");
			mGeometry2.setVisible(true);
			mGeometry2.setMoveRotation(new Vector4d(1f,0f,0f,1.57f));
			mGeometry2.setMoveRotation(new Vector4d(0f,0f,1f,1.57f),true);
			mGeometry2.setMoveTranslation(new Vector3d(0,100,0));
			mGeometry2.startAnimation("Stand", true);

			
			mGeometry3 = loadGeometry("yoshi/yoshi.md2");
			mGeometry3.setVisible(true);
			mGeometry3.setMoveRotation(new Vector4d(1f,0f,0f,1.57f));
			mGeometry3.setMoveRotation(new Vector4d(0f,0f,1f,-1.57f),true);
			mGeometry3.setMoveTranslation(new Vector3d(0,-100,0));
			mGeometry3.startAnimation("stand", true);
			//mGeometry2 = loadGeometry("metaioman.md2");
			//mGeometry3 = loadGeometry("metaioman.md2");

			//mButton = loadGeometry("plane.obj");
			
			// Do something with it, like scaling
//			mGeometry.setMoveScale( new Vector3d(.5f,.5f,.5f) );
//			mGeometry.setMoveRotation(new Vector3d(200, 0, 0));
//			
//			mGeometry.setMoveTranslation(new Vector3d(0,20,0));
//			mGeometry.setMoveTranslation(new Vector3d(0,20,0));
//			mGeometry.setMoveTranslation(new Vector3d(0,20,0));
//			mGeometry.setMoveTranslation(new Vector3d(0,20,0));
//			mGeometry.setMoveTranslation(new Vector3d(0,20,0));
			
			//mGeometry.
			
			
			//mGeometry2.setMoveScale( new Vector3d(1,1,1) );
			//mGeometry2.setMoveRotation(new Vector3d(200,0,0));
			//mButton.setMoveScale(new Vector3d(50,50,50));
			
			//mGeometry3.setMoveTranslation(new Vector3d(100,0,0));
			//mGeometry3.setMoveScale( new Vector3d(.5f,.5f,.5f) );
			//mGeometry3.setMoveRotation(new Vector3d(200, 0, 0));
		} catch (Exception e) {
			UnifeyeDebug.printStackTrace(Log.ERROR, e);
		}
	}
}
