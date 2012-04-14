package com.metaio.MobileSDKExample.simple;

import com.metaio.unifeye.ndk.IUnifeyeMobileGeometry;

import android.os.Parcel;
import android.os.Parcelable;

public class MyParcelable implements Parcelable{
	private IUnifeyeMobileGeometry mData;
	
	public MyParcelable(IUnifeyeMobileGeometry geo) {
		this.mData = geo;
	}
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

		public static final Parcelable.Creator<MyParcelable> CREATOR = new Parcelable.Creator<MyParcelable>() {
        public MyParcelable createFromParcel(IUnifeyeMobileGeometry in) {
            return new MyParcelable(in);
        }
        public MyParcelable[] newArray(int size) {
            return new MyParcelable[size];
        }
		@Override
		public MyParcelable createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return null;
		}
		
		
	};

		@Override
		public void writeToParcel(Parcel dest, int flags) {
			// TODO Auto-generated method stub
			
		}
		
		public IUnifeyeMobileGeometry get() {
	        return this.mData;
	    }
	
}
