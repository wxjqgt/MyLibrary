package com.gt.mylibrary.customs_views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import com.gt.mylibrary.R;

public class Radarview extends View {
	private Bitmap car;
	
	private class BitmapList {
		public Bitmap[] bitmaps;
	}
	private BitmapList[] mPreBitmaps = new BitmapList[4]; // 左
	private BitmapList[] mLastBitmaps = new BitmapList[4]; // 右
	
	private int x = 274, y = 224;// 坐标
	private byte[] mPre = new byte[]{0x00, 0x00, 0x00, 0x00};
	private byte[] mLast = new byte[]{0x00, 0x00, 0x00, 0x00}; // 前4个和后4个
	
	public Radarview(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

	}

	public Radarview(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public Radarview(Context context) {
		super(context);
	}

	/**
	 * 前置雷达
	 * @param one 最左边
	 * @param two 左边第二个
	 * @param three 右边第二个
	 * @param four 最右边
	 */
	public void setPreParam(byte one, byte two, byte three, byte four) {
		mPre[0] = one;
		mPre[1] = two;
		mPre[2] = three;
		mPre[3] = four;
		invalidate();
	}

	/**
	 * 后置雷达
	 * @param one
	 * @param two
	 * @param three
	 * @param four
	 */
	public void setLastParam(byte one, byte two, byte three, byte four) {
		mLast[0] = one;
		mLast[1] = two;
		mLast[2] = three;
		mLast[3] = four;
		invalidate();
	}

	private void init() {
		// 背景图初始化
		car = BitmapFactory.decodeResource(getResources(), R.mipmap.car);
		//左边初始化
		
		for (int i = 0; i < mPreBitmaps.length; i++) {
			mPreBitmaps[i] = new BitmapList();
			mPreBitmaps[i].bitmaps = new Bitmap[4];
		}
		mPreBitmaps[0].bitmaps[0] = BitmapFactory
				.decodeResource(getResources(), R.mipmap.zuo1_1);
		mPreBitmaps[0].bitmaps[1] = BitmapFactory
				.decodeResource(getResources(), R.mipmap.zuo1_2);
		mPreBitmaps[0].bitmaps[2] = BitmapFactory
				.decodeResource(getResources(), R.mipmap.zuo1_3);
		mPreBitmaps[0].bitmaps[3] = BitmapFactory
				.decodeResource(getResources(), R.mipmap.zuo1_4);
		
		mPreBitmaps[1].bitmaps[0] = BitmapFactory
				.decodeResource(getResources(), R.mipmap.zuo2_1);
		mPreBitmaps[1].bitmaps[1] = BitmapFactory
				.decodeResource(getResources(), R.mipmap.zuo2_2);
		mPreBitmaps[1].bitmaps[2] = BitmapFactory
				.decodeResource(getResources(), R.mipmap.zuo2_3);
		mPreBitmaps[1].bitmaps[3] = BitmapFactory
				.decodeResource(getResources(), R.mipmap.zuo2_4);
		
		mPreBitmaps[2].bitmaps[0] = BitmapFactory
				.decodeResource(getResources(), R.mipmap.zuo3_1);
		mPreBitmaps[2].bitmaps[1] = BitmapFactory
				.decodeResource(getResources(), R.mipmap.zuo3_2);
		mPreBitmaps[2].bitmaps[2] = BitmapFactory
				.decodeResource(getResources(), R.mipmap.zuo3_3);
		mPreBitmaps[2].bitmaps[3] = BitmapFactory
				.decodeResource(getResources(), R.mipmap.zuo3_4);
		
		mPreBitmaps[3].bitmaps[0] = BitmapFactory
				.decodeResource(getResources(), R.mipmap.zuo4_1);
		mPreBitmaps[3].bitmaps[1] = BitmapFactory
				.decodeResource(getResources(), R.mipmap.zuo4_2);
		mPreBitmaps[3].bitmaps[2] = BitmapFactory
				.decodeResource(getResources(), R.mipmap.zuo4_3);
		mPreBitmaps[3].bitmaps[3] = BitmapFactory
				.decodeResource(getResources(), R.mipmap.zuo4_4);
		//右边初始化
	
		for (int i = 0; i < mLastBitmaps.length; i++) {
			mLastBitmaps[i] = new BitmapList();
			mLastBitmaps[i].bitmaps = new Bitmap[4];
		}
		mLastBitmaps[0].bitmaps[0] = BitmapFactory
				.decodeResource(getResources(), R.mipmap.you1_1);
		mLastBitmaps[0].bitmaps[1] = BitmapFactory
				.decodeResource(getResources(), R.mipmap.you1_2);
		mLastBitmaps[0].bitmaps[2] = BitmapFactory
				.decodeResource(getResources(), R.mipmap.you1_3);
		mLastBitmaps[0].bitmaps[3] = BitmapFactory
				.decodeResource(getResources(), R.mipmap.you1_4);
		
		mLastBitmaps[1].bitmaps[0] = BitmapFactory
				.decodeResource(getResources(), R.mipmap.you2_1);
		mLastBitmaps[1].bitmaps[1] = BitmapFactory
				.decodeResource(getResources(), R.mipmap.you2_2);
		mLastBitmaps[1].bitmaps[2] = BitmapFactory
				.decodeResource(getResources(), R.mipmap.you2_3);
		mLastBitmaps[1].bitmaps[3] = BitmapFactory
				.decodeResource(getResources(), R.mipmap.you2_4);
		
		mLastBitmaps[2].bitmaps[0] = BitmapFactory
				.decodeResource(getResources(), R.mipmap.you3_1);
		mLastBitmaps[2].bitmaps[1] = BitmapFactory
				.decodeResource(getResources(), R.mipmap.you3_2);
		mLastBitmaps[2].bitmaps[2] = BitmapFactory
				.decodeResource(getResources(), R.mipmap.you3_3);
		mLastBitmaps[2].bitmaps[3] = BitmapFactory
				.decodeResource(getResources(), R.mipmap.you3_4);
		
		mLastBitmaps[3].bitmaps[0] = BitmapFactory
				.decodeResource(getResources(), R.mipmap.you4_1);
		mLastBitmaps[3].bitmaps[1] = BitmapFactory
				.decodeResource(getResources(), R.mipmap.you4_2);
		mLastBitmaps[3].bitmaps[2] = BitmapFactory
				.decodeResource(getResources(), R.mipmap.you4_3);
		mLastBitmaps[3].bitmaps[3] = BitmapFactory
				.decodeResource(getResources(), R.mipmap.you4_4);
	}

	@Override
	protected void onDraw(Canvas canvas) {
	
		super.onDraw(canvas);
		canvas.drawBitmap(car, 250, 200, null);
		// 画左边- 前面
		for (int i = 0; i < mPre.length; i++) {
			int data = mPre[i];
			int index = mPre.length - i - 1;
			
			if (data > 0x00 && data <= 0x08) {
				// 显示一格
				canvas.drawBitmap(mPreBitmaps[index].bitmaps[0], x, y, null);
			} else if (data > 0x08 && data <= 0x0f) {
				// 两格
				canvas.drawBitmap(mPreBitmaps[index].bitmaps[0], x, y, null);
				canvas.drawBitmap(mPreBitmaps[index].bitmaps[1], x, y, null);
			} else if (data > 0x0f && data <= 0x18) {
				// 三格
				canvas.drawBitmap(mPreBitmaps[index].bitmaps[0], x, y, null);
				canvas.drawBitmap(mPreBitmaps[index].bitmaps[1], x, y, null);
				canvas.drawBitmap(mPreBitmaps[index].bitmaps[2], x, y, null);
			} else if (data > 0x18 && data <= 0x1f) {
				// 四格
				canvas.drawBitmap(mPreBitmaps[index].bitmaps[0], x, y, null);
				canvas.drawBitmap(mPreBitmaps[index].bitmaps[1], x, y, null);
				canvas.drawBitmap(mPreBitmaps[index].bitmaps[2], x, y, null);
				canvas.drawBitmap(mPreBitmaps[index].bitmaps[3], x, y, null);
			} else {
				// 不显示
			}
		}	
		// 画右边- 后面
		for (int i = 0; i < mLast.length; i++) {
			int data = mLast[i];
			int index = mLast.length - i - 1;

			if (data > 0x00 && data <= 0x08) {
				// 显示一格
				canvas.drawBitmap(mLastBitmaps[index].bitmaps[0],
						car.getWidth() + 500 - mLastBitmaps[index].bitmaps[0].getWidth() - x,
						y, null);
			} else if (data > 0x08 && data <= 0x0f) {
				// 两格
				canvas.drawBitmap(mLastBitmaps[index].bitmaps[0],
						car.getWidth() + 500- mLastBitmaps[index].bitmaps[0].getWidth() - x,
						y, null);
				canvas.drawBitmap(
						mLastBitmaps[index].bitmaps[1],
						car.getWidth() + 500- mLastBitmaps[index].bitmaps[1].getWidth() - x,
						y, null);
			} else if (data > 0x0f && data <= 0x18) {
				// 三格
				canvas.drawBitmap(
						mLastBitmaps[index].bitmaps[0],
						car.getWidth() + 500 - mLastBitmaps[index].bitmaps[0].getWidth() - x,
						y, null);
				canvas.drawBitmap(
						mLastBitmaps[index].bitmaps[1],
						car.getWidth() + 500- mLastBitmaps[index].bitmaps[1].getWidth() - x,
						y, null);
				canvas.drawBitmap(
						mLastBitmaps[index].bitmaps[2],
						car.getWidth() + 500- mLastBitmaps[index].bitmaps[2].getWidth() - x,
						y, null);
			} else if (data > 0x18 && data <= 0x1f) {
				// 四格
				canvas.drawBitmap(
						mLastBitmaps[index].bitmaps[0],
						car.getWidth() + 500- mLastBitmaps[index].bitmaps[0].getWidth() - x,
						y, null);
				canvas.drawBitmap(
						mLastBitmaps[index].bitmaps[1],
						car.getWidth() + 500- mLastBitmaps[index].bitmaps[1].getWidth() - x,
						y, null);
				canvas.drawBitmap(
						mLastBitmaps[index].bitmaps[2],
						car.getWidth() + 500- mLastBitmaps[index].bitmaps[2].getWidth() - x,
						y, null);
				canvas.drawBitmap(
						mLastBitmaps[index].bitmaps[3],
						car.getWidth() + 500- mLastBitmaps[index].bitmaps[3].getWidth() - x,
						y, null);
			} else {
				// 不显示
			}
		}

	

//		// 画右边
//		// 第一圈
//		canvas.drawBitmap(you1_1, car.getWidth() + 500 - you1_1.getWidth() - x,
//				y, null);
//		canvas.drawBitmap(you2_1, car.getWidth() + 500 - you2_1.getWidth() - x,
//				y, null);
//		canvas.drawBitmap(you3_1, car.getWidth() + 500 - you3_1.getWidth() - x,
//				y, null);
//		canvas.drawBitmap(you4_1, car.getWidth() + 500 - you4_1.getWidth() - x,
//				y, null);
//		// 第二圈
//		canvas.drawBitmap(you1_2, car.getWidth() + 500 - you1_2.getWidth() - x,
//				y, null);
//		canvas.drawBitmap(you2_2, car.getWidth() + 500 - you2_2.getWidth() - x,
//				y, null);
//		canvas.drawBitmap(you3_2, car.getWidth() + 500 - you3_2.getWidth() - x,
//				y, null);
//		canvas.drawBitmap(you4_2, car.getWidth() + 500 - you4_2.getWidth() - x,
//				y, null);
//		// 第三圈
//		canvas.drawBitmap(you1_3, car.getWidth() + 500 - you1_3.getWidth() - x,
//				y, null);
//		canvas.drawBitmap(you2_3, car.getWidth() + 500 - you2_3.getWidth() - x,
//				y, null);
//		canvas.drawBitmap(you3_3, car.getWidth() + 500 - you3_3.getWidth() - x,
//				y, null);
//		canvas.drawBitmap(you4_3, car.getWidth() + 500 - you4_3.getWidth() - x,
//				y, null);
//		// 第四圈
//		canvas.drawBitmap(you1_4, car.getWidth() + 500 - you1_4.getWidth() - x,
//				y, null);
//		canvas.drawBitmap(you2_4, car.getWidth() + 500 - you2_4.getWidth() - x,
//				y, null);
//		canvas.drawBitmap(you3_4, car.getWidth() + 500 - you3_4.getWidth() - x,
//				y, null);
//		canvas.drawBitmap(you4_4, car.getWidth() + 500 - you4_4.getWidth() - x,
//				y, null);
	}
}
