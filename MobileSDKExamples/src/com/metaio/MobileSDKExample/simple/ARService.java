package com.metaio.MobileSDKExample.simple;

import java.io.Serializable;

import com.metaio.unifeye.ndk.IUnifeyeMobileGeometry;
import com.metaio.unifeye.ndk.Vector3d;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;
import com.metaio.MobileSDKExample.simple.*;;

public class ARService extends Service{
	private IUnifeyeMobileGeometry mMan;
	private IUnifeyeMobileGeometry mMetaioMan;

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "Binding", Toast.LENGTH_LONG).show();

		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		Toast.makeText(this, "Service Created", Toast.LENGTH_LONG).show();

		super.onCreate();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		//Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();

		super.onStart(intent, startId);
		//Serializable bundle  = intent.getSerializableExtra("lastState");
		//mMetaioMan = ((Bundle) bundle).getSerializable("lastState");
		
		//Bundle b = intent.getExtras();
		//MyParcelable p = b.getParcelable("geoMan");
		
		//mMetaioMan.setMoveTranslation(new Vector3d(100,0,0));
		
		Toast.makeText(this, "TROLOLOLOL", Toast.LENGTH_LONG).show();
		
		//mMetaioMan.setMoveTranslation(new Vector3d(100,0,0));
		//		int k = 5;
//		
//		for(int i=0; i<20; i++){
//			try { Thread.sleep(100); } catch(InterruptedException e) { e.printStackTrace(); }
//			mMetaioMan.setMoveTranslation(new Vector3d(0,k,0));
//			
//			k+=5;
//		}

	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	

}
