package com.alexq.main;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.alexq.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;


/**
 * @author AleXQ
 * @Date 16/8/4
 * @Description: $主页面Recycler适配器
 */

public class MainRecyclerViewAdaper extends RecyclerView.Adapter<MainRecyclerViewAdaper.ViewHolder> {

	static public class DataInfo{
		public String activityName;
		public String infoString;
		public String coverUrl;

		public DataInfo(String activityName, String infoString, String coverUrl) {
			this.activityName = activityName;
			this.infoString = infoString;
			this.coverUrl = coverUrl;
		}
	}

	public ArrayList<DataInfo> datas = null;

	public MainRecyclerViewAdaper(ArrayList<DataInfo> datas) {
		this.datas = datas;
	}

	//创建新View，被LayoutManager所调用
	@Override
	public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
		View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_main_recyclerviewitem, viewGroup, false);
		ViewHolder vh = new ViewHolder(view);
		return vh;
	}

	//将数据与界面进行绑定的操作
	@Override
	public void onBindViewHolder(ViewHolder viewHolder, int position) {
		viewHolder.mBtnView.setText(datas.get(position).infoString);
		viewHolder.activityClassName = datas.get(position).activityName;
		viewHolder.showGif(datas.get(position).coverUrl);
	}

	//获取数据的数量
	@Override
	public int getItemCount() {
		return datas.size();
	}

	//自定义的ViewHolder，持有每个Item的的所有界面元素
	public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
		public Button mBtnView;
		private SimpleDraweeView mDraweeView;
		public String activityClassName = "";

		public ViewHolder(View view) {
			super(view);
			mBtnView = (Button) view.findViewById(R.id.wp_recyclerviewitem_btn);
			mBtnView.setOnClickListener(this);

			mDraweeView = (SimpleDraweeView) view.findViewById(R.id.my_image_view);
		}

		public void showGif(String url){
			Uri uri = Uri.parse(url);
			DraweeController controller = Fresco.newDraweeControllerBuilder()
					.setUri(uri)
					.setAutoPlayAnimations(true)
					.build();
			mDraweeView.setController(controller);
		}

		@Override
		public void onClick(View v) {
			try {
				Class activityClass = Class.forName(activityClassName);

				Intent intent = new Intent();
				intent.setClass(v.getContext(), activityClass);
				v.getContext().startActivity(intent);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
