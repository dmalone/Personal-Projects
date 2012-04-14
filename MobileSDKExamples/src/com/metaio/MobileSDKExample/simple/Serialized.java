package com.metaio.MobileSDKExample.simple;

import java.io.Serializable;

import com.metaio.unifeye.ndk.IUnifeyeMobileGeometry;

public class Serialized implements Serializable{
	
	public static final long serialVersionUID = 1;
	
	private IUnifeyeMobileGeometry dude;
	
	public Serialized(IUnifeyeMobileGeometry geo){
		dude = geo;
	}
	
	public IUnifeyeMobileGeometry getDude(){
		return dude;
	}

}
