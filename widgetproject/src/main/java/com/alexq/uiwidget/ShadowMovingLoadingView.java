package com.alexq.uiwidget;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Shader;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;

import com.alexq.baseutils.Utils;

import java.util.ArrayList;

import alexq.widgetLibApplication;

/**
 * @author AleXQ
 * @Date 16/6/20
 * @Description: 用于直播间内展示视频加载中的进度条
 */

public class ShadowMovingLoadingView extends View {

	private static int s_maxCount = 23;

	private int m_itemWidth = 0;
	private int m_itemOffset = 50;
	private int m_itemMoveOffset = 6;

	private int m_lefttopStart = 0;
	private int m_lefttopEnd = 0;
	private ArrayList<ShadowRect> m_postions = new ArrayList<ShadowRect>();

	private int m_viewHeight = 0;

	private Paint m_paint;

	private boolean m_bLight = true;

	private class ShadowRect{
		public Point leftTop;
		public Point rightTop;
		public Point leftBottom;
		public Point rightBottom;

		public ShadowRect(Point leftTop, Point rightTop, Point leftBottom, Point rightBottom) {
			this.leftTop = leftTop;
			this.rightTop = rightTop;
			this.leftBottom = leftBottom;
			this.rightBottom = rightBottom;
		}
	}

	public ShadowMovingLoadingView(Context context) {
		super(context);
		initView();
	}

	public ShadowMovingLoadingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	public ShadowMovingLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initView();
	}

	/**
	 * Set background is light or not(dark), shadowmovingloadingview
	 * will show the color of item with this judgement.
	 * @param blight :background is light
	 */
	public void setIsLight(boolean blight){
		m_bLight = blight;
		initPaint();
	}

	@Override
	protected void onDraw(Canvas canvas) {

		calcNewPosition();


		drawItemPostion(canvas);


		super.onDraw(canvas);

		postInvalidateDelayed(20);

	}

	private void initView(){

		Configuration mConfiguration = this.getResources().getConfiguration();
		int ori = mConfiguration.orientation ; //获取屏幕方向
		if(ori == mConfiguration.ORIENTATION_LANDSCAPE){
			s_maxCount = 43;
		}else if(ori == mConfiguration.ORIENTATION_PORTRAIT){
			s_maxCount = 25;
		}
		int screenwidth = Utils.getScreenWidth(widgetLibApplication.getInstance()) + 200;
		m_itemWidth = screenwidth / (s_maxCount*2);
		m_lefttopStart = 0;
		m_lefttopEnd = m_itemWidth +m_lefttopStart + m_itemWidth;
		m_itemMoveOffset = Utils.DpToPx(widgetLibApplication.getInstance(), 2);

		resetPostion();

		getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				initPaint();
				if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
					getViewTreeObserver().removeGlobalOnLayoutListener(this);
				} else {
					getViewTreeObserver().removeOnGlobalLayoutListener(this);
				}
				setVisibility(View.VISIBLE);
			}
		});
	}

	private void initPaint() {
		m_viewHeight = getHeight();
		if (m_paint == null)
			m_paint = new Paint();

		Shader shader = null;
		if (m_bLight) {
			shader = new LinearGradient(0, 0, 0, m_viewHeight,
					new int[]{Color.argb(50, 20, 20, 20), Color.argb(10, 120, 120, 120), Color.argb(1, 200, 200, 200)}
					, null, Shader.TileMode.REPEAT);
		}
		else{
			shader = new LinearGradient(0, 0, 0, m_viewHeight,
					new int[]{Color.argb(50, 200, 200, 200), Color.argb(10, 120, 120, 120), Color.argb(1, 20, 20, 20)}
					, null, Shader.TileMode.REPEAT);
		}
		m_paint.setShader(shader);
		m_paint.setAntiAlias(true);
	}

	private void calcNewPosition(){
		//是否完成一次整体绘制
		int preNewX = m_postions.get(0).leftTop.x + m_itemMoveOffset;
		if (preNewX >= m_lefttopEnd ){
			//完成了一次整体绘制，重置
			resetInitView();
		}
		else {
			//计算平移结果
			for (int i=0; i<s_maxCount;i++){
				ShadowRect rect = m_postions.get(i);
				rect.leftTop.x += m_itemMoveOffset;
				rect.rightTop.x += m_itemMoveOffset;
				rect.leftBottom.x += m_itemMoveOffset;
				rect.rightBottom.x += m_itemMoveOffset;
			}
		}
	}

	private void drawItemPostion(Canvas canvas){
		for (int i=0; i<m_postions.size(); i++) {
			float left = m_postions.get(i).leftTop.x;
			float top = m_postions.get(i).leftTop.y;
			float right = m_postions.get(i).rightTop.x;
			float bottom = m_postions.get(i).rightBottom.y;

			Matrix matrix = new Matrix();
			matrix.setValues(new float[]
					{
							1.0f, -0.4f, 0.5f,
							0f, 1.0f, 0f,
							0f, 0f, 1.0f
					});
			canvas.setMatrix(matrix);

			canvas.drawRect(left, top, right, bottom, m_paint);
		}
	}

	private void resetInitView(){
		m_postions.clear();
		resetPostion();
	}

	private void resetPostion(){
		//init Item postion
		for (int i=0; i<s_maxCount;i++){
			int leftTopX = m_lefttopStart + i*m_itemWidth*2;
			int leftTopY = 0;
			Point leftTop = new Point(leftTopX, leftTopY);

			int rightTopX = leftTop.x + m_itemWidth;
			int rightTopY = leftTop.y;
			Point rightTop = new Point(rightTopX, rightTopY);

			int leftBottomX = leftTop.x - m_itemOffset;
			int leftBottomY = m_viewHeight;
			Point leftBottom = new Point(leftBottomX, leftBottomY);

			int rightBottomX = leftBottom.x + m_itemWidth;
			int rightBottomY = leftBottom.y;
			Point rightBottom = new Point(rightBottomX, rightBottomY);

			m_postions.add(new ShadowRect(leftTop, rightTop, leftBottom, rightBottom));
		}
	}

	@Override
	public void setVisibility(int visibility) {
		super.setVisibility(visibility);
		if (visibility == VISIBLE)
			postInvalidate();
	}
}
