package com.alexq.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alexq.R;
import com.alexq.shadowloadingviewdemo.ShadowMovingLoadingDemoActivity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity{


	@Bind(R.id.wp_recyclerview)
	protected RecyclerView m_recyclerView;

	private MainRecyclerViewAdaper m_mainRecyclerViewAdaper;
	private LinearLayoutManager m_LayoutManager;


	private ArrayList<MainRecyclerViewAdaper.DataInfo> m_activityNames = new ArrayList();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ButterKnife.bind(this);

		initRecyclerView();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		ButterKnife.unbind(this);
	}

	private void initRecyclerView(){

		m_LayoutManager = new LinearLayoutManager(this);

		m_activityNames.add(new MainRecyclerViewAdaper.DataInfo(
				ShadowMovingLoadingDemoActivity.class.getName(),
				"ShadowMovingLoading\n(阴影进度条)",
				"http://7xox5k.com1.z0.glb.clouddn.com/shadowmovingloadingview.gif")
		);

		m_mainRecyclerViewAdaper = new MainRecyclerViewAdaper(m_activityNames);

		m_recyclerView.setHasFixedSize(true);
		m_recyclerView.setLayoutManager(m_LayoutManager);
		m_recyclerView.setAdapter(m_mainRecyclerViewAdaper);
	}
}
