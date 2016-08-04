package com.alexq;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

import alexq.widgetLibApplication;

/**
 * @author AleXQ
 * @Date 16/8/1
 * @Description: ${TODO}(用一句话描述该文件做什么)
 */

public class MyApplication extends Application {

	private static MyApplication mAppInstance = null;

	private widgetLibApplication m_libApplication = null;
	public static MyApplication getInstance() {
		return mAppInstance;
	}

	public MyApplication() {

		mAppInstance = this;
		m_libApplication = new widgetLibApplication(this);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Fresco.initialize(this);
	}
}
