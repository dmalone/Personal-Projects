/*
 * Copyright (C) 2007 The Android Open Source Project
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

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES20;
import android.opengl.Matrix;

/**
 * A vertex shaded cube.
 */
class Cube
{
	private FloatBuffer mVertices;


	/**
	 * Shader-id
	 */
	private int mSimpleColorProgram;

	/**
	 * Shader-id of the Model-View-Projection-Matrix
	 */
	private int muMVPMatrixHandle;

	/**
	 * Shader-id of the vertex-position-handle.
	 */
	private int maPositionHandle;

	/**
	 * Shader-id of the 
	 */
	private int maColorHandle;

	/**
	 * Model-View-Projection-Matrix
	 */
	private float[] mMVPMatrix = new float[16];

	/** 
	 * Model-Matrix. Also used for the pose
	 */
	private float[] mMMatrix = new float[16];

	/**
	 * Camera-Matrix. Only set at start.
	 */
	public  float[] mVMatrix;
	
	/**
	 * Projection-Matrix. Need to be updated only when the surface changes. 
	 */
	public  float[] mProjMatrix;

	/**
	 * If this object is visible or not. If it's not visible drawing will be skipped. 
	 */
	private boolean mIsVisible;

	/**
	 * Number of float values stored for each vertex. 
	 */
	private static final int VALUES_PER_VERTEX = 6;
	


	private static final int VERTICES_DATA_STRIDE_BYTES = VALUES_PER_VERTEX * GLES20Utils.FLOAT_SIZE_BYTES;


	public Cube()
	{
		/**
		 * Scale factor of the cube. 
		 */
		final float scale = 20f;

		/**
		 * Array of vertices of the cube, centered at the origin and
		 * color values.
		 */
		float[] vertices = {
				// front
				-0.5f, 	-0.5f, 	0.5f,  		0,1,0 ,
				 0.5f, 	-0.5f, 	0.5f, 		1,1,0 ,
				-0.5f,   0.5f, 	0.5f, 		0,0,1 ,
				 0,5f,   0.5f, 	0.5f, 		1,0,0 ,

				// back
				-0.5f,  -0.5f, -0.5f, 		1,1,0 ,
				-0.5f,   0.5f, -0.5f, 		1,1,1 ,
				 0.5f,  -0.5f, -0.5f, 		1,0,0 ,
				 0.5f,   0.5f, -0.5f, 		1,0,1 ,

				// left
				-0.5f,  -0.5f,  0.5f, 		0,1,1 ,
				-0.5f,   0.5f,  0.5f, 	    1,1,1 ,
				-0.5f,  -0.5f, -0.5f, 		0,1,0 ,
				-0.5f,   0.5f, -0.5f, 		1,1,0 ,

				// right
				 0.5f,  -0.5f, -0.5f, 	    1,1,1 ,
				 0.5f,   0.5f, -0.5f, 		0,1,1 ,
				 0.5f,  -0.5f,  0.5f, 		1,0,1 ,
				 0.5f,   0.5f,  0.5f, 		0,0,1 ,

				//top
				-0.5f,   0.5f,  0.5f, 		0,1,1 ,
				 0.5f,   0.5f,  0.5f, 		0,1,0 ,
				-0.5f,   0.5f, -0.5f, 		0,0,1 ,
				 0.5f,   0.5f, -0.5f, 		1,0,0 ,

				// bottom
				-0.5f,  -0.5f,  0.5f, 		0,0,0 ,
				-0.5f,  -0.5f, -0.5f, 		1,0,0 ,
				 0.5f,  -0.5f,  0.5f, 		0,0,1 ,
				 0.5f,  -0.5f, -0.5f,		1,0,1 
		};

		

		// Buffers to be passed to gl*Pointer() functions
		// must be direct, i.e., they must be placed on the
		// native heap where the garbage collector cannot
		// move them.
		//
		// Buffers with multi-byte datatypes (e.g., short, int, float)
		// must have their byte order set to native order
		for ( int i = 0 ; i < vertices.length; i+= VALUES_PER_VERTEX )
		{
			vertices[i]  *= scale;
			vertices[i+1]*=scale;
			vertices[i+2]*=scale;
		}
		mVertices = ByteBuffer.allocateDirect(vertices.length
				* GLES20Utils.FLOAT_SIZE_BYTES).order(ByteOrder.nativeOrder()).asFloatBuffer();
		mVertices.put(vertices).position(0);

		mSimpleColorProgram = GLES20Utils.createProgram(mVertexShader, mFragmentShaderSimpleColor );
		if ( mSimpleColorProgram == 0 )
		{
			throw new RuntimeException("Could not compile the program");
		}
		muMVPMatrixHandle = GLES20.glGetUniformLocation(mSimpleColorProgram, "uVMVPMatrix");
		if ( muMVPMatrixHandle == -1 )
		{
			throw new RuntimeException("Could not get the attrib uMVPMatrix");
		}
		maPositionHandle  = GLES20.glGetAttribLocation(mSimpleColorProgram, "aVertexPosition");
		if (maPositionHandle == -1) {
			throw new RuntimeException("Could not get attrib location for aVertexPosition");
		}
		maColorHandle = GLES20.glGetAttribLocation(mSimpleColorProgram, "fVertexColor");
		if (maColorHandle == -1) {
			throw new RuntimeException("Could not get attrib location for fVertexColor");
		}
		
		Matrix.setIdentityM(mMMatrix, 0);


		Matrix.translateM(mMMatrix, 0, 0, 0, 600);
	
		Matrix.rotateM(mMMatrix, 0, 0, 0, 1, 0);
	}




	private final String mFragmentShaderSimpleColor =
		"precision mediump float;\n" +
		"varying vec3 vfColor;\n" +
		"" +
		"void main() {\n" +
		"  gl_FragColor = vec4(vfColor.xyz, 1.0);\n" +
		"}\n";

	private final String mVertexShader =
		"uniform mat4 uVMVPMatrix;\n" +
		"attribute vec4 aVertexPosition;\n" +
		"attribute vec3 fVertexColor;\n" +
		"varying vec3 vfColor;\n" +
		"void main() {\n" +
		"  gl_Position = uVMVPMatrix * aVertexPosition;\n" +
		"  vfColor = fVertexColor;" +
		"}\n";



	/**
	 * Draws the object for the current frame.
	 */
	public  void onDrawFrame( ) {
		// don't draw if not visible
		if ( !mIsVisible  ) 
		{
			return;
		}
		GLES20.glUseProgram(mSimpleColorProgram);

		/*
		 * Setup the view-matrices.  
		 */
		Matrix.multiplyMM(mMVPMatrix, 0, mVMatrix, 0, mMMatrix, 0);
		Matrix.multiplyMM(mMVPMatrix, 0, mProjMatrix, 0, mMVPMatrix, 0);
		GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, mMVPMatrix, 0);
		/*
		 * Write the vertex data
		 */
		mVertices.position(0);
		GLES20.glVertexAttribPointer(maPositionHandle, 3, GLES20.GL_FLOAT, false, VERTICES_DATA_STRIDE_BYTES, mVertices);

		/*
		 * Write the color data.
		 */
		mVertices.position(3);
		GLES20.glVertexAttribPointer(maColorHandle, 3, GLES20.GL_FLOAT, false, VERTICES_DATA_STRIDE_BYTES, mVertices);

		GLES20.glEnableVertexAttribArray(maPositionHandle);     

		/*
		 * For each side of the cube draw a triangle strip.
		 */
		for ( int i = 0; i < 6; i++ )
		{
			GLES20.glDrawArrays(GL10.GL_TRIANGLE_STRIP, i*4, 4);
		}
	}

	public void setVisibile( boolean isVisibile)
	{
		mIsVisible = isVisibile;
	}

	public void setMMatrix(float[] modelMatrix) {
		
		mMMatrix = modelMatrix;
	}



}
