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

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.DisplayMetrics;
import android.util.Log;

import com.metaio.MobileSDKExample.opengl20.ARController.ICallback;
import com.metaio.unifeye.UnifeyeDebug;

/**
 * Renders the camera image on a plane. 
 * 
 * @author tim.oppermann
 *
 */
class GLES20CameraImageRenderer implements GLSurfaceView.Renderer, Closeable {

	/**
	 * Buffer used to store the vertices.
	 */
	private FloatBuffer mTriangleVertices;

	/**
	 * Vertex shader for the camera plane
	 */
	private final String mVertexShader = "uniform mat4 uMVPMatrix;\n"
			+ "attribute vec4 aPosition;\n" + "attribute vec2 aTextureCoord;\n"
			+ "varying vec2 vTextureCoord;\n" + "void main() {\n"
			+ "  gl_Position = uMVPMatrix * aPosition;\n"
			+ "  vTextureCoord = aTextureCoord;\n" + "}\n";

	/**
	 * Fragment shader for the camera plane.
	 */
	private final String mFragmentShader = "precision mediump float;\n"
			+ "varying vec2 vTextureCoord;\n" + "uniform sampler2D sTexture;\n"
			+ "void main() {\n"
			+ "  gl_FragColor = texture2D(sTexture, vTextureCoord);\n" + "}\n";

	/**
	 * Model-View-Projection-Matrix for OpenGL
	 */
	private float[] mMVPMatrix = new float[16];

	/**
	 * Model-Matrix
	 */
	private float[] mMMatrix = new float[16];
	
	/**
	 * View-Matrix. Also known as Camera-Matrix
	 */
	private float[] mVMatrix = new float[16];

	/**
	 * GL id of the shader program
	 */
	private int mProgram;
	
	/**
	 * GL id of the texture
	 */
	private int mTextureID;
	
	/**
	 * Shader-Handle for the Model-View-Projection-Matrix
	 */
	private int muMVPMatrixHandle;
	
	/**
	 * Shader-Handle for the vertex position attribute. 
	 */
	private int maPositionHandle;
	
	/**
	 * Shader handler for the camera texture. 
	 */
	private int maTextureHandle;

	/**
	 * Current activity context
	 */
	private GLES20Activity mContext;

	/**
	 * Size of the camera image.
	 */
	private final int mCameraImageWidth;
	
	/**
	 * Size of the camera image.
	 */
	private final int mCameraImageHeight;
	
	/**
	 * Projection matrix used to render the camera-plane.
	 */
	private float[] mProjMatrixOrtho;
	
	/**
	 * Attached geometry. In this example the cube will get the pose. 
	 */
	private Cube mCube;

	/**
	 * The ARController wraps around the MobileSDK so that you can
	 * access its functionality easily. 
	 */
	public ARController mARController;

	private int mWidth;

	private int mHeight;

	/**
	 * Create a new renderer. 
	 * @param context The activity this render should render to.
	 */
	public GLES20CameraImageRenderer(GLES20Activity context, int cameraWidth, int cameraHeight) {
		mContext = context;
		mCameraImageWidth = cameraWidth;
		mCameraImageHeight = cameraHeight;
	}

	/**
	 * Called by the renderer on each render frame.
	 * 
	 * Calls onDrawFrame in the Activity, which calls onDrawFrame on the
	 * AR-Controller.
	 * 
	 * @param glUnused The OpenGL 1.0 rendering context, which is not used.
	 */
	public void onDrawFrame(GL10 glUnused) {
		// Ignore the passed-in GL10 interface, and use the GLES20
		// class's static methods instead.
		mContext.onDrawFrame(glUnused);
		mARController.onDrawFrame();
		// Clear the screen and the depth-buffer
		GLES20.glClearColor(0.0f, 0.0f, 1.0f, 1.0f);
		GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
		GLES20.glUseProgram(mProgram);
		GLES20Utils.checkGlError("glUseProgram");
		// Disable depth-test while drawing the camera-plane
		GLES20.glDisable(GLES20.GL_DEPTH_TEST);
		// Activate and bind the camera texture.
		GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureID);

		mTriangleVertices.position(TRIANGLE_VERTICES_DATA_POS_OFFSET);
		GLES20.glVertexAttribPointer(maPositionHandle, 3, GLES20.GL_FLOAT,
				false, TRIANGLE_VERTICES_DATA_STRIDE_BYTES, mTriangleVertices);

		GLES20Utils.checkGlError("glVertexAttribPointer maPosition");

		mTriangleVertices.position(TRIANGLE_VERTICES_DATA_UV_OFFSET);
		GLES20.glEnableVertexAttribArray(maPositionHandle);
		GLES20Utils.checkGlError("glEnableVertexAttribArray maPositionHandle");
		GLES20.glVertexAttribPointer(maTextureHandle, 2, GLES20.GL_FLOAT,
				false, TRIANGLE_VERTICES_DATA_STRIDE_BYTES, mTriangleVertices);
		GLES20Utils.checkGlError("glVertexAttribPointer maTextureHandle");
		GLES20.glEnableVertexAttribArray(maTextureHandle);
		GLES20Utils.checkGlError("glEnableVertexAttribArray maTextureHandle");
		
		Matrix.setIdentityM(mMMatrix, 0);
		Matrix.multiplyMM(mMVPMatrix, 0, mVMatrix, 0, mMMatrix, 0);
		Matrix.multiplyMM(mMVPMatrix, 0, mProjMatrixOrtho, 0, mMVPMatrix, 0);
		GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, mMVPMatrix, 0);
		// Draw the plane by pushing the vertex-data.
		GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0,
				Array.getLength(mCameraPlaneVerticesData) / 5);
		// The plane as been drawn. Enable the depth-test again.
		GLES20.glEnable(GLES20.GL_DEPTH_TEST);
		GLES20Utils.checkGlError("glDrawArrays");

		mCube.onDrawFrame();

	}

	public void onSurfaceChanged(GL10 glUnused, int width, int height) 
	{
		// Ignore the passed-in GL10 interface, and use the GLES20
		// class's static methods instead.
		GLES20.glViewport(0, 0, width, height);
		mWidth = width;
		mHeight = height;
		if (mARController != null) {
			mARController.onSurfaceChanged();
		}
	}

	/**
	 * Set up the surface for Open-GL rendering.
	 * 
	 * @param glUnused
	 *            The OpenGL1.0 context is not used here.
	 */
	public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {
		// Ignore the passed-in GL10 interface, and use the GLES20
		// class's static methods instead.
		DisplayMetrics displayMetrics = new DisplayMetrics();
		mContext.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		mWidth = displayMetrics.widthPixels;
		mHeight = displayMetrics.heightPixels;
		GLES20.glViewport(0, 0, mWidth, mHeight);
		mARController.onSurfaceCreated();
		mProgram = GLES20Utils.createProgram(mVertexShader, mFragmentShader);
		if (mProgram == 0) {
			return;
		}
		maPositionHandle = GLES20.glGetAttribLocation(mProgram, "aPosition");
		GLES20Utils.checkGlError("glGetAttribLocation aPosition");
		if (maPositionHandle == -1) {
			throw new RuntimeException(
					"Could not get attrib location for aPosition");
		}
		maTextureHandle = GLES20.glGetAttribLocation(mProgram, "aTextureCoord");
		GLES20Utils.checkGlError("glGetAttribLocation aTextureCoord");
		if (maTextureHandle == -1) {
			throw new RuntimeException(
					"Could not get attrib location for aTextureCoord");
		}

		muMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
		GLES20Utils.checkGlError("glGetUniformLocation uMVPMatrix");
		if (muMVPMatrixHandle == -1) {
			throw new RuntimeException(
					"Could not get attrib location for uMVPMatrix");
		}

		/*
		 * Create our texture. This has to be done each time the surface is
		 * created. First the texture is only a blank image.
		 */

		int[] textures = new int[1];
		GLES20.glGenTextures(1, textures, 0);

		mTextureID = textures[0];
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureID);

		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
				GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
				GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);

		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S,
				GLES20.GL_REPEAT);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T,
				GLES20.GL_REPEAT);
		

		int width = 32;
		int height = 32;
		while ( width < mCameraImageWidth ||  height < mCameraImageHeight )
		{
			width *= 2;
			height *= 2;
		}
		
		byte[] textureArray = new byte[width * height * 4];
		Array.setByte(textureArray, 0, (byte) 0);
		ByteBuffer buffer = ByteBuffer.wrap(textureArray);

		GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, // textureId
				0, // level of mipmapping.
				GLES20.GL_RGBA, // internal format
				width, // width of buffer in pixels
				height, // height of buffer in pixels
				0, // number of border in pixels
				GLES20.GL_RGBA, // format
				GLES20.GL_UNSIGNED_BYTE,// type
				buffer); // data
		GLES20Utils.checkGlError("init texture");
	
		float textureScaleX =  (float)mCameraImageWidth/ (float) width;
		float textureScaleY =	(float)mCameraImageHeight /(float)height;
		
		/*
		 * Make the texture-coordinates proper to the relation between the 
		 * texture size and the camera image size.
		 */
		for ( int i = 3; i < mCameraPlaneVerticesData.length; i+=5 )
		{
			// scale U if its not 0:
			if ( mCameraPlaneVerticesData[i] > 0 )
				mCameraPlaneVerticesData[i] =  textureScaleX;
			// scale V if its not 0:
			if ( mCameraPlaneVerticesData[i+1] > 0 )
				mCameraPlaneVerticesData[i+1]=  textureScaleY;
		}
		
		mTriangleVertices = ByteBuffer
				.allocateDirect(mCameraPlaneVerticesData.length * GLES20Utils.FLOAT_SIZE_BYTES)
				.order(ByteOrder.nativeOrder()).asFloatBuffer();
		mTriangleVertices.put(mCameraPlaneVerticesData).position(0);
		
		mCube = new Cube();
		/*
		 * Set the camera to the origin and let it look along the negative
		 * z-axis.
		 */
		Matrix.setIdentityM(mVMatrix, 0);
		Matrix.setLookAtM(mVMatrix, 0, //  viewMatrix, offset
				0, 0, 0, //  camera position
				0f, 0f, -1f, //  look-at-spot
				0f, 1.0f, 0.0f); // up-vector.

		GLES20.glEnable(GLES20.GL_DEPTH_TEST);

		mCube.mVMatrix = mVMatrix;

		mProjMatrixOrtho = GLES20Utils.createCameraPlaneProjectionMatrix();
	}


	private static final int TRIANGLE_VERTICES_DATA_STRIDE_BYTES = 5 * GLES20Utils.FLOAT_SIZE_BYTES;
	private static final int TRIANGLE_VERTICES_DATA_POS_OFFSET = 0;
	private static final int TRIANGLE_VERTICES_DATA_UV_OFFSET = 3;
	
	/**
	 * Vertex data for the plane including texture coordinates. 
	 */
	private float[] mCameraPlaneVerticesData = {
			// X, Y, Z, U, V
			-1, -1, 0, 1.0f, 0.0f,
			1, -1, 0, 0.0f, 0.0f,
			-1, 1, 0, 1.0f, 1.0f,
			1, 1, 0, 0.0f, 1.0f

	};


	/**
	 * Delegate that handles callbacks from the ARController. 
	 */
	private ARCallback mARControllerDelegate = new ARCallback();

	protected class ARCallback implements ARController.ICallback {
		/**
		 * Triggered if the pattern can't be detected anymore.
		 */
		@Override
		public void onTrackingLost(int cosId) {
			UnifeyeDebug.log(Log.INFO, "Lost tracking of COS " + cosId + "!");
			mCube.setVisibile(false);
		}

		/**
		 * Triggered if the pattern has been detected.
		 */
		@Override
		public void onPatternDetected(int cosId) {
			UnifeyeDebug.log(Log.INFO, "COS " + cosId + " detected!");
			mCube.setVisibile(true);
		}

		/**
		 * Sets the pose of the attached geometry. This method gets called by
		 * the ARController, when there is a valid tracking Pose.
		 * 
		 * If you insert own geometry, make sure that it gets this matrix at
		 * this point.
		 */
		@Override
		public void onTrackingSuccessful(int cosId, float[] matrix) {
			mCube.setMMatrix(matrix);
		}

		/**
		 * Updates the texture used for the camera-image Implements IRenderer.
		 * 
		 * @param bitmap
		 *            Array of bytes that contains the image.
		 * @param width
		 *            Width in pixels of the image. Must correspond to the
		 *            byte-data.
		 * @param height
		 *            Height in pixels of the image. Must correspond to the
		 *            byte-data.
		 */
		@Override
		public void setCameraTextureSource(final byte[] bitmap,
				final int width, final int height) {
			ByteBuffer buffer = ByteBuffer.wrap(bitmap);
			try {
				GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
				GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureID);
				GLES20.glTexSubImage2D(GLES20.GL_TEXTURE_2D, // textureId
						0, // level
						0, // offset x
						0, // offset y
						width, // width of buffer
						height, // height of buffer
						GLES20.GL_RGBA, // format
						GLES20.GL_UNSIGNED_BYTE, // type
						buffer); // data
				GLES20Utils.checkGlError("update texture");
			} catch (Exception e) {
				UnifeyeDebug.printStackTrace(Log.ERROR, e);
			}
		}

		/**
		 * Sets the projection matrix for the attached geometry.
		 */
		@Override
		public void setGeometryProjectionMatrix(float[] projectionMatrix) {
			mCube.mProjMatrix = projectionMatrix;
		}
	}

	@Override
	public void close() throws IOException {

		mARControllerDelegate = null;
	}

	public void setARController(ARController arController) {
		mARController = arController;

	}

	public ICallback getARControllerCallback() {

		return mARControllerDelegate;
	}

}
