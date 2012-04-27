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
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.metaio.MobileSDKExample.R;
import com.metaio.unifeye.UnifeyeDebug;
import com.metaio.unifeye.ndk.IUnifeyeMobileCallback;
import com.metaio.unifeye.ndk.IUnifeyeMobileGeometry;
import com.metaio.unifeye.ndk.Vector3d;
import com.metaio.unifeye.ndk.Vector4d;

import java.awt.SplashScreen;
import java.lang.Math;

/**
 * EXAMPLE 1
 * 
 * This is a simple AR activity which shows 2D planar marker tracking with
 * animations. The target pattern and the model is MetaioMan.
 * 
 * Please visit the following link for a detailed explanation. {@link http
 * ://docs.metaio.com/bin/view/Main/HelloAugmentedWorldExample}
 * 
 * @author arsalan.malik, tim.oppermann
 * 
 */

public class CharacterSelectionActivity extends ARViewActivity {
	private MobileSDKCallbackHandler mMobileSDKCallbackHandler;

	@Override
	protected int getGUILayout() {
		// TODO Auto-generated method stub
		return R.layout.battlemenu2;
	}

	/**
	 * The geometry to be displayed
	 */
	private IUnifeyeMobileGeometry mGeometry;
	private IUnifeyeMobileGeometry mGeometry2;
	private IUnifeyeMobileGeometry mGeometry3;
	private IUnifeyeMobileGeometry mGeometry4;
	private IUnifeyeMobileGeometry mSelectedGeometry;
	private IUnifeyeMobileGeometry mButton;
	/**
	 * Tracking file you like to use. The file must be within the assets folder
	 * of this project. The reference image and explanation can be found at
	 * http://docs.metaio.com/bin/view/Main/UnifeyeMobileTrackingConfiguration
	 */
	private final String mTrackingDataFileName = "TrackingData_MarkerlessFast.xml";

	/**
	 * This tracking configuration used Markerless 3D tracking. You can use the
	 * meatioman_target to veriy the tracking
	 */
	private final String mTrackingDataML3D = "TrackingData_ML3D.xml";

	/**
	 * This tracking configuration used ID Markers for tracking
	 */
	private final String mTrackingDataMarker = "TrackingData_Marker.xml";

	private ImageView icon;
	private ProgressBar bar;
	private TextView win;
	private LinearLayout introLayout;

	protected void showGeometryPlayer(IUnifeyeMobileGeometry geometry) {
		geometry.setMoveRotation(new Vector4d(1f, 0f, 0f, 1.57f));
		geometry.setMoveRotation(new Vector4d(0f, 0f, 1f, 1.57f), true);
		geometry.setMoveTranslation(new Vector3d(0, 100, 0));
		if (geometry.equals(mGeometry)) {
			geometry.startAnimation("Stand", true);
		} else if (geometry.equals(mGeometry3)) {
			geometry.startAnimation("stand", true);
		}
	}
	
	protected void showGeometryEnemy(IUnifeyeMobileGeometry geometry) {
		geometry.setMoveRotation(new Vector4d(1f, 0f, 0f, 1.57f));
		geometry.setMoveRotation(new Vector4d(0f, 0f, 1f, -1.57f), true);
		geometry.setMoveTranslation(new Vector3d(0, -100, 0));
		if (geometry.equals(mGeometry)) {
			geometry.startAnimation("Stand", true);
		} else if (geometry.equals(mGeometry3)) {
			geometry.startAnimation("stand", true);
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mMobileSDKCallbackHandler = new MobileSDKCallbackHandler();
		

	}

	@Override
	protected IUnifeyeMobileCallback getMobileSDKCallbackHandler() {
		return mMobileSDKCallbackHandler;
	}

	/**
	 * Gets called by the super-class after the GLSurface has been created. It
	 * runs on the OpenGL-thread.
	 */
	@Override
	protected void loadUnifeyeContents() {
		try {

			// Intent myIntent = new
			// Intent(HelloAugmentedWorldActivity.this,Receiver.class);
			icon = (ImageView) findViewById(R.id.imageView2);
			bar = (ProgressBar) findViewById(R.id.progressBar1);
			// Load Tracking data
			loadTrackingData(mTrackingDataFileName);
			// win.setVisibility(0);
			// Load all geometry
			mGeometry = loadGeometry("darthvader/darthvader.md2");
			mGeometry.setVisible(true);
			mGeometry.setMoveRotation(new Vector4d(1f, 0f, 0f, 1.57f));
			mGeometry.setMoveRotation(new Vector4d(0f, 0f, 1f, 3.14f), true);
			mGeometry.setMoveTranslation(new Vector3d(0, 20, 0));
			mGeometry.startAnimation("Stand", true);

			mGeometry2 = loadGeometry("darthvader/weapon.md2");
			mGeometry2.setVisible(false);
			// mGeometry2.setMoveRotation(new Vector4d(1f, 0f, 0f, 1.57f));
			// mGeometry2.setMoveRotation(new Vector4d(0f, 0f, 1f, 1.57f),
			// true);
			// mGeometry2.setMoveTranslation(new Vector3d(0, 100, 0));
			// mGeometry2.startAnimation("Stand", true);

			mGeometry3 = loadGeometry("yoshi/yoshi.md2");
			mGeometry3.setVisible(true);
			mGeometry3.setMoveRotation(new Vector4d(1f, 0f, 0f, 1.57f));
			mGeometry3.setMoveRotation(new Vector4d(0f, 0f, 1f, 3.14f), true);
			mGeometry3.setMoveTranslation(new Vector3d(0, -20, 0));
			mGeometry3.startAnimation("stand", true);

			// Do something with it, like scaling
			// mGeometry.setMoveScale( new Vector3d(.5f,.5f,.5f) );
			// mGeometry.setMoveRotation(new Vector3d(200, 0, 0));

		} catch (Exception e) {
			UnifeyeDebug.printStackTrace(Log.ERROR, e);
		}
	}

	@Override
	protected void onGeometryTouched(IUnifeyeMobileGeometry geometry) {
		// TODO Auto-generated method stub
		if (geometry.equals(mGeometry)) {
			mSelectedGeometry = mGeometry;
			icon.setImageResource(R.drawable.darthicon);
			showGeometryPlayer(mGeometry);
			showGeometryEnemy(mGeometry3);
			

			
		}
		if(geometry.equals(mGeometry3)) {
			mSelectedGeometry = mGeometry3;
			icon.setImageResource(R.drawable.yoshiicon);
			showGeometryPlayer(mGeometry3);
			showGeometryEnemy(mGeometry);
		}
	}

	public void onAttackClicked(final View eventSource) {
		// showGeometry(mMetaioMan);
		if (mGeometry != null) {
			mGeometry.startAnimation("Attack", false);
			mGeometry2.startAnimation("Attack", false);
			// icon.setImageResource(R.drawable.darthicon);
		}
		if (bar.getProgress() <= 0) {

		} else {
			bar.setProgress(bar.getProgress() - 20);
		}
	}

	public void onDefendClicked(final View eventSource) {
		// showGeometry(mMetaioMan);
		if (mGeometry != null) {
			mGeometry.startAnimation("CrStnd", true);
			mGeometry2.startAnimation("CrStnd", true);
			// icon.setImageResource(R.drawable.darthicon);
		}

	}

	public void onSpecialClicked(final View eventSource) {
		// showGeometry(mMetaioMan);
		if (mGeometry != null) {
			mGeometry.startAnimation("Taunt", false);
			mGeometry2.startAnimation("Taunt", false);
			// icon.setImageResource(R.drawable.darthicon);
		}

	}

	private final class MobileSDKCallbackHandler extends IUnifeyeMobileCallback {

		/**
		 * Called by the MobileSDK when an animation has finished.
		 * 
		 * Note: Runs on the OpenGL-Thread
		 * 
		 * @param geometry
		 *            Reference to the geometry that was animated
		 * @param animationName
		 *            Name of the animation that has been played.
		 */
		public void onAnimationEnd(IUnifeyeMobileGeometry geometry,
				String animationName) {
			UnifeyeDebug.log("MobileSDKCallbackHandler.onAnimationEnd: "
					+ animationName);

			/*
			 * Decide which animation should be played next. If no special
			 * conditions apply, it will be the 'idle' animation.
			 */

			geometry.startAnimation("Stand", true);

		}
	}
}
