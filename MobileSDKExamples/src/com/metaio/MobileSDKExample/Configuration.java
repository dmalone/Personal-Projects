package com.metaio.MobileSDKExample;

/**
 * Static constant values go here. 
 * 
 * @author tim.oppermann
 *
 */
public abstract class Configuration 
{
	public static final String signature = "Ig1hNdxRO52rNP9Qn1NPz13pGkIoXjHxgclAV26cEFI=";

	public abstract class Camera
	{
		public static final long resolutionX = 480;  	
		public static final long resolutionY = 320;
		/*
		 * 0: normal camera
		 * 1: front facing camera
		 */
		public static final int deviceId = 0;
	}

}
