/**
 * ARViewActivitySimple.java
 * Example SDK Internal
 *
 * Created by Arsalan Malik on 08.03.2011
 * Copyright 2011 metaio GmbH. All rights reserved.
 *
 */

package com.metaio.MobileSDKExample.simple;

import java.util.Random;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.metaio.MobileSDKExample.R;
import com.metaio.tools.io.AssetsManager;
import com.metaio.unifeye.UnifeyeDebug;
import com.metaio.unifeye.ndk.IUnifeyeMobileCallback;
import com.metaio.unifeye.ndk.IUnifeyeMobileGeometry;
import com.metaio.unifeye.ndk.Vector3d;


/**
 * EXAMPLE 2
 * 
 * This is a simple AR activity which shows 2D planar marker tracking with
 * animations. The target pattern and the model is MetaioMan.
 * 
 *  Please visit the following link for a detailed explanation. 
 * {@link http://docs.metaio.com/bin/view/Main/AdvancedContentExample} 
 *
 * @author arsalan.malik, tim.oppermann
 * 
 */
public class AdvancedContentActivity extends ARViewActivity {
	
	public int var = 0;

	/**
	 * Callback handler for onAnimationFinished events from the MobileSDK.
	 */
	private MobileSDKCallbackHandler mMobileSDKCallbackHandler;

	/**
	 * Tracking data to use here. 
	 */
	private final String mTrackingDataFileName = "TrackingData_MarkerlessFast.xml";

	/**
	 * Truck model
	 */
	private IUnifeyeMobileGeometry mTruck;

	/**
	 * A plane on which a movie shall be shown at. 
	 */
	private IUnifeyeMobileGeometry mMoviePlane;
	
	/**
	 * Plane to display a play-button. The user can start the movie by 
	 * clicking on this plane.
	 */
	private IUnifeyeMobileGeometry mMoviePlayButtonPlane;

	/**
	 * References the MetaioMan geometry
	 */
	public IUnifeyeMobileGeometry mMetaioMan;

	/**
	 * References the currently visible geometry.
	 * Can be equal to mTruck, mMoviePlane or mMetaioMan.  
	 */
	private IUnifeyeMobileGeometry mSelectedGeometry;

	/**
	 * Flag, thats prevents that a animation is started if
	 * one is playing already. Does not apply for the idle-animation.
	 */
	private boolean mIsAnimationRunning;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mMobileSDKCallbackHandler = new MobileSDKCallbackHandler();
		
	}

	public final static float PI_2 = (float) (Math.PI / 2.0);

	protected void showGeometry(IUnifeyeMobileGeometry geometry) {
		if (geometry != null && geometry != mSelectedGeometry) {
			if ( mSelectedGeometry == mMoviePlayButtonPlane)
			{
				mMoviePlane.pauseMovieTexture();
				mMoviePlane.setVisible(false);
			}
			mSelectedGeometry.setVisible(false);
			geometry.setVisible(true);
			mSelectedGeometry = geometry;
			
		}
	}

	/**
	 * Define that the mainscreen layout should be used. 
	 * 
	 */
	@Override
	protected int getGUILayout() {
		return R.layout.mainscreen;
	}

	@Override
	protected IUnifeyeMobileCallback getMobileSDKCallbackHandler() {
		return mMobileSDKCallbackHandler;
	}

	/**
	 * 
	 * We prefer to load models on start of the application, so that 
	 * there are no loading lags during the AR-experience.
	 * 
	 * - This method gets called after the renderer has been started.
	 * - It runs on the OpenGL-thread.
	 */
	@Override
	protected void loadUnifeyeContents() {
		try {
			/*
			 *  Load the tracking data
			 */
			boolean success = loadTrackingData(mTrackingDataFileName);
			if ( !success )
			{
				UnifeyeDebug.log("Loading of the tracking data failed.");
			}
			/*
			 *  Load all the geometries
			 */
			mTruck = loadGeometry("truck/truck.obj");
			mTruck.setMoveRotation(new Vector3d( (float) Math.PI / 2.0f, 0 , 0 ));
			mTruck.setMoveRotation(new Vector3d( 0, 0 , (float) Math.PI  ), true);

			mTruck.setVisible(false);
			
			/*
			 * Get the path to of the env-map folder 
			 */
			String path = AssetsManager.getAssetPath("truck/env_map");
			if ( path == null )
			{
				UnifeyeDebug.log("Path of environmet map is invalid!");
			}
			success = mMobileSDK.loadEnvironmentMap(path);
			if  ( !success )
			{
				UnifeyeDebug.log("Loading of the enviroment map failed.");
			}
			
			/*
			 * For the movie texture we use a plane as play button 
			 * and one plane for the movie itself. 
			 */
			mMoviePlayButtonPlane = loadGeometry("plane.obj");
			mMoviePlayButtonPlane.setVisible(false);			
			mMoviePlane = loadGeometry("movieplanes/3to2.md2");
			mMoviePlane.setVisible(false);
			
			final float scale = 100;

			Vector3d movieScale = new Vector3d(scale,scale,scale);
			mMoviePlane.setMoveScale(movieScale);
			mMoviePlane.setMoveRotation(new Vector3d(PI_2, PI_2, PI_2 ));
			mMoviePlane.setMoveTranslation(new Vector3d(0,  -55,0));
		
			mMoviePlayButtonPlane.setMoveScale(movieScale);
			mMoviePlayButtonPlane.setMoveRotation(new Vector3d(0,0, PI_2));


			/*
			 * Movie textures may be set explicit.
			 */
			mMoviePlane.setMovieTexture(AssetsManager.getAssetPath("demo_movie.3g2"), false, true);
			mMoviePlane.playMovieTexture();
			/*
			 * Load the MetaioMan
			 */
			mMetaioMan = loadGeometry("metaioman.md2");
			
			mMetaioMan.startAnimation("idle", false);

			mSelectedGeometry = mMetaioMan;
		} catch (Exception e) {
			UnifeyeDebug.printStackTrace(Log.ERROR, e);
		}
	}

	/**
	 * Called by the MobileSDK on user input on the main-thread.
	 * 
	 * @param geometry The geometry that has been touched. 
	 */
	@Override
	protected void onGeometryTouched(IUnifeyeMobileGeometry geometry) {
		/**
		 * Make sure to use 'equals' here, because the reference pointer to the geometry coming
		 * from the MobileSDK will be different than your local reference
		 */
		if ( !mIsAnimationRunning && geometry.equals(mMetaioMan)) {
			UnifeyeDebug.log("UnifeyeCallbackHandler.onGeometryTouched: " + geometry);
			//mMetaioMan.setMoveTranslation(new Vector3d(0,-100,0));
			
//			Serialized s1 = new Serialized(mMetaioMan);
			//IUnifeyeMobileGeometry blah = mMetaioMan;
			//MyParcelable p = new MyParcelable(blah);
			//Bundle bundle = new Bundle();
			//bundle.putSerializable("lastState", s1)
			
			Intent myIntent = new Intent(AdvancedContentActivity.this,ARService.class);
			//bundle.putParcelable("geoMan", p);
			//if(bundle.containsKey("geoMan")){
			//myIntent.putExtras(bundle);}
			
			mMetaioMan.setMoveTranslation(new Vector3d(var, 0,0));
			var+=10;
			
			//Toast.makeText(this, "TROLOLOL", Toast.LENGTH_LONG).show();

			//startService(myIntent);
			//Log.d("damn","fuck");
			//Toast.makeText(this, "TROLOLOLOLOLOLOLOLOL", Toast.LENGTH_LONG).show();

			//Random random = new Random();
			//int k = 5;
			//for(int i=0; i<20; i++){
			//	try { Thread.sleep(100); } catch(InterruptedException e) { e.printStackTrace(); }
//				mMetaioMan.setMoveTranslation(new Vector3d(0,0,-k));
				
				//k+=5;
			//}

			if ( Math.random()< .5 )
			{
				mMetaioMan.startAnimation("close_down", false);
			}
			else
			{
				mMetaioMan.startAnimation("shock_down", false);
			}
			//mIsAnimationRunning = true;
		}
		/*else if ( geometry.equals(mMoviePlayButtonPlane))
		{
			mMoviePlane.playMovieTexture();
			mMoviePlane.setVisible(true);
			mMoviePlayButtonPlane.setVisible(false);
		}*/
	}

	
	
	/**
	 * Handle events from the MobileSDK by implementing the abstract class IUnifeyeMobileCallback
	 * @author tim.oppermann
	 *
	 */
	private final class MobileSDKCallbackHandler extends IUnifeyeMobileCallback {

		/**
		 * Called by the MobileSDK when an animation has finished.
		 * 
		 * Note: Runs on the OpenGL-Thread
 		 * 
		 * @param geometry Reference to the geometry that was animated
		 * @param animationName Name of the animation that has been played.
		 */
		public void onAnimationEnd(IUnifeyeMobileGeometry geometry,
				String animationName) {
			UnifeyeDebug.log("MobileSDKCallbackHandler.onAnimationEnd: "
					+ animationName);

			/*
			 * Decide which animation should be played next. 
			 * If no special conditions apply, it will be the 
			 * 'idle' animation.
			 */
			String nextAnimationName = "idle";
			if ( animationName == "close_down")
			{
				nextAnimationName = "close_up";
			} else if ( animationName == "close_up")
			{
				nextAnimationName = "close_idle";
			} else if ( animationName == "shock_down")
			{
				nextAnimationName = "shock_up";
			} else if ( animationName == "shock_up" )
			{
				nextAnimationName = "shock_idle";
			} else
			{
				mIsAnimationRunning = false;
			}
			/*
			 * Proceed with the next animation.
			 */
			geometry.startAnimation(nextAnimationName, false);
			
		}

		/**
		 * Called by the MobileSDK when an movie has finished.
		 * 
		 * Note: Runs on the OpenGL-Thread
 		 * 
		 * @param geometry Reference to the geometry that was been used
		 * @param movieName Name of the movie that has been played.
		 */
		public void onMovieEnd(IUnifeyeMobileGeometry geometry, String movieName) {
			UnifeyeDebug.log("MobileSDKCallbackHandler.onMovieEnd: " + movieName);

			// hide the movie plane
			mMoviePlane.pauseMovieTexture();
			mMoviePlane.setVisible(false);

			// show the play button
			mMoviePlayButtonPlane.setVisible(true);
			mSelectedGeometry = mMoviePlayButtonPlane;
		
		}
		
	}

	
	/**
	 * Called from the layout /res/layout/mainscreen.xml when
	 * the respective button is clicked.
	 * @param eventSource not needed here
	 */
	public void onMetaioManBttnClicked(final View eventSource) {
		showGeometry(mMetaioMan);
		if ( mMetaioMan != null )
		{
			mMetaioMan.startAnimation("idle", false);
		}
		if ( mMoviePlane != null )
		{
			mMoviePlane.stopMovieTexture();
		}
	}

	/**
	 * Called from the layout /res/layout/mainscreen.xml when
	 * the respective button is clicked.
	 * @param eventSource not needed here
	 */
	public void onTruckBttnClicked(final View eventSource) {
		showGeometry(mTruck);
		if ( mMoviePlane != null )
		{
			mMoviePlane.stopMovieTexture();
		}

	}

	/**
	 * Called from the layout /res/layout/mainscreen.xml when
	 * the respective button is clicked.
	 * @param eventSource not needed here
	 */
	public void onMoviePlaneBttnClicked(final View eventSource) {
		showGeometry(mMoviePlayButtonPlane);

	}
}
