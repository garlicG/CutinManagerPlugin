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

package com.garlicg.sample.cutinappsupport.engine;

import android.content.Context;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;
import android.os.Handler;
import android.view.View;
import android.widget.FrameLayout;

import com.garlicg.cutin.appsupport.CutinEngine;
import com.garlicg.cutin.appsupport.CutinService;


/**
 * Refactor From API Demo
 *
 * Wrapper engine demonstrating the use of {@link GLSurfaceView} to
 * display translucent 3D graphics.
 */
public class GLSurfaceViewEngine extends CutinEngine {
	private GLSurfaceView mGLSurfaceView;

	public GLSurfaceViewEngine(CutinService cutinService) {
		super(cutinService);
	}

	@Override
	public View onCreateLayout(Context context) {
		FrameLayout frameLayout = new FrameLayout(context);
		mGLSurfaceView = new GLSurfaceView(context);
		// We want an 8888 pixel format because that's required for
		// a translucent window.
		// And we want a depth buffer.
		mGLSurfaceView.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
		// Tell the cube renderer that we want to render a translucent version
		// of the cube:
		mGLSurfaceView.setRenderer(new CubeRenderer(true));
		// Use a surface format with an Alpha channel:
		mGLSurfaceView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
		frameLayout.addView(mGLSurfaceView);
		return frameLayout;
	}

	@Override
	public void onStart() {

		mGLSurfaceView.onResume();

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				finishCutin();
			}
		}, 2000);
	}

	@Override
	public void onDestroy() {
		mGLSurfaceView.onPause();
	}
}


