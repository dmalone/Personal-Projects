package com.metaio.MobileSDKExample.opengl20;

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


import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.Log;

import com.metaio.unifeye.UnifeyeDebug;

/**
 * Class to support common OpenGL operations. 
 * 
 * @author tim.oppermann
 *
 */
public abstract class GLES20Utils {
	private static int loadShader(int shaderType, String source) {
		int shader = GLES20.glCreateShader(shaderType);
		if (shader != 0) {
			GLES20.glShaderSource(shader, source);
			GLES20.glCompileShader(shader);
			int[] compiled = new int[1];
			GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compiled, 0);
			if (compiled[0] == 0) {
				UnifeyeDebug.log(Log.ERROR, "Could not compile shader " + shaderType + ":");
				UnifeyeDebug.log(Log.ERROR, GLES20.glGetShaderInfoLog(shader));
				GLES20.glDeleteShader(shader);
				shader = 0;
			}
		}
		return shader;
	}
	
	public static int createProgram(String vertexSource, String fragmentSource) {
		int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexSource);
		if (vertexShader == 0) {
			return 0;
		}

		int pixelShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentSource);
		if (pixelShader == 0) {
			return 0;
		}

		int program = GLES20.glCreateProgram();
		if (program != 0) {
			GLES20.glAttachShader(program, vertexShader);
			checkGlError("glAttachShader");
			GLES20.glAttachShader(program, pixelShader);
			checkGlError("glAttachShader");
			GLES20.glLinkProgram(program);
			int[] linkStatus = new int[1];
			GLES20.glGetProgramiv(program, GLES20.GL_LINK_STATUS, linkStatus, 0);
			if (linkStatus[0] != GLES20.GL_TRUE) {
				UnifeyeDebug.log(Log.ERROR, "Could not link program: ");
				UnifeyeDebug.log(Log.ERROR, GLES20.glGetProgramInfoLog(program));
				GLES20.glDeleteProgram(program);
				program = 0;
			}
		}
		return program;
	}

	public static void checkGlError(String op) {
		int error;
		while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
			UnifeyeDebug.log(Log.ERROR, op + ": glError " + error);
			throw new RuntimeException(op + ": glError " + error);
		}
	}
	
	/**
	 * Creates an orthogonal projection matrix for OpenGL. See the manual of
	 * OpenGL for further details.
	 * 
	 * @see http://www.opengl.org/sdk/docs/man/xhtml/glOrtho.xml
	 */
	public static float[] createCameraPlaneProjectionMatrix() {
		float[] result = new float[16];
		float left = 1;
		float right = -1;
		float top = 1;
		float bottom = -1;
		float near = -1;
		float far = 1;
		float tx = -(right + left) / (right - left);
		float ty = -(top + bottom) / (top - bottom);
		float tz = -(far + near) / (far - near);

		Matrix.setIdentityM(result, 0);
		result[0] = 2 / (right - left);
		result[5] = 2 / (top - bottom);
		result[10] = -2 / (far - near);
		result[3] = tx;
		result[7] = ty;
		result[11] = tz;
		return result;
	}
	
	/**
	 * Bytes used for one float value.
	 */
	public static final int FLOAT_SIZE_BYTES = 4;
}
