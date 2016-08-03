package com.alexq.shadowloadingviewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import com.alexq.uiwidget.ShadowMovingLoadingView;

import alexq.com.shadowloadingviewdemo.R;


public class ShadowMovingLoadingDemoActivity extends AppCompatActivity implements View.OnClickListener{

	private ShadowMovingLoadingView m_shadowMovingLoadingView;
	private RelativeLayout m_relativeLayout;

	private boolean m_bBackgroundFirst = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		m_shadowMovingLoadingView = (ShadowMovingLoadingView) findViewById(R.id.progressBarVideoLoading);

		m_relativeLayout = (RelativeLayout) findViewById(R.id.layout);


		findViewById(R.id.changebackground).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.changebackground){
			if (m_bBackgroundFirst){
				m_relativeLayout.setBackgroundResource(R.mipmap.background);
				m_shadowMovingLoadingView.setIsLight(false);
			}
			else{
				m_relativeLayout.setBackgroundResource(R.mipmap.background1);
				m_shadowMovingLoadingView.setIsLight(true);
			}
			m_bBackgroundFirst = !m_bBackgroundFirst;
		}
	}
}
