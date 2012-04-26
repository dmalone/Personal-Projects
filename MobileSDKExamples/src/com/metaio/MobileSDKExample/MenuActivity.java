/**
 *  SplashScreenActivity.java
 *  Example SDK Internal for Android
 *
 *  Created by Arsalan Malik on 09.06.2010
 *  Copyright 2011 metaio GmbH. All rights reserved.
 */
package com.metaio.MobileSDKExample;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.metaio.MobileSDKExample.opengl20.GLES20Activity;
import com.metaio.MobileSDKExample.simple.AdvancedContentActivity;
import com.metaio.MobileSDKExample.simple.GPSLocationBasedActivity;
import com.metaio.MobileSDKExample.simple.HelloAugmentedWorldActivity;
import com.metaio.unifeye.ndk.IUnifeyeMobileAndroid;
/**
 *	This is the first activity that is executed. It is responsible
 *	for initializing the application, splash screen and passing control
 *	to the an AR activity
 * 
 * @see MobileSDKExampleApplication
 * 
 * @author arsalan.malik
 *
 */
public class MenuActivity extends Activity
{ 

	static 
	{     
		IUnifeyeMobileAndroid.loadNativeLibs();
	} 


	
	private LinearLayout mMenuContainer;
	private LayoutParams mMenuItemLayoutParamters;

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.splash);


	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

		if ( mMenuContainer == null )
		{
			mMenuContainer = (LinearLayout) findViewById(R.id.menuContainer);
			mMenuItemLayoutParamters = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
			
			createMenuItem( "Example 1:\r\nHello Augmented World", HelloAugmentedWorldActivity.class);
			createMenuItem( "Example 2:\r\nAdvanced Content", AdvancedContentActivity.class);
//			createMenuItem( "Example 3:\r\nGPS Location Based", GPSLocationBasedActivity.class );
//			createMenuItem( "Example 4:\r\nCustom Renderer", GLES20Activity.class );
			findViewById(R.id.textStatus).setVisibility(View.INVISIBLE);
		}
	}
	
	@Override
	protected void onStop() {
		
		super.onStop();
		mMenuContainer.removeAllViews();
		mMenuContainer = null;
	}
	
	private void createMenuItem(final String label, final Class<?> targetActivity )
	{
		Button button = new Button(this);
		button.setText(label);
		final Intent intent = new Intent(this, targetActivity);

		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(intent);
			}
		});
		mMenuContainer.addView(button, mMenuItemLayoutParamters);
	}
}
