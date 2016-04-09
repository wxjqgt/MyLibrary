package com.gt.mylibrary.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.LruCache;

import java.io.File;

/**
 * 定义一个内存管理的工具类，该工具类主要针对图片的处理
 * @author bin.xie
 * @date 2016/3/29
 */
@SuppressLint("NewApi")
public class LruCacheUtils {
	
	private LruCache<String, Bitmap> mMemoryCache = null;
	
	private static LruCacheUtils pThis = null;
	
	/**
	 * 获取实例
	 * @return
	 */
	public static LruCacheUtils getInstance() {
		if (null == pThis) {
			pThis = new LruCacheUtils();
		}
		return pThis;
	}
	
	/**
	 * 删除实例
	 */
	public static void removeInstance() {
		if (null != pThis) {
			pThis.clearCache();
			pThis = null;
		}
	}

	private LruCacheUtils() {
		int MAXMEMONRY = (int) (Runtime.getRuntime() .maxMemory() / 1024);

		mMemoryCache = new LruCache<String, Bitmap>(MAXMEMONRY / 8) {// 手机剩余内存的1/8

			@Override
			protected void entryRemoved(boolean evicted, String key,
					Bitmap oldValue, Bitmap newValue) {
				super.entryRemoved(evicted, key, oldValue, newValue);
			}

			@Override
			protected int sizeOf(String key, Bitmap bitmap) {
				return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
			}
		};
	}

	/**
	 * 清除缓存
	 */
	public void clearCache() {
        if (mMemoryCache != null) {
            if (mMemoryCache.size() > 0) {
                mMemoryCache.evictAll();
//                Logcat.d("mMemoryCache.size()" + mMemoryCache.size());
            }
            mMemoryCache = null;
        }
    }

    /**
     * 获取内存图片，如果图片不存在，自动从路径获取
     * @param path
     * @return
     */
    public synchronized Bitmap getBitmapFromMemCache(String path) {
    	if (TextUtils.isEmpty(path)) {
//    		Logcat.w("path is null!");
    		return null;
    	}
        Bitmap bm = mMemoryCache.get(path);
        if (null != bm) {
            return bm;
        } else {
        	// 没有图片，直接从指定路径加载
        	if ((new File(path)).exists()) {
        		// 文件存在，从文件中加载图片
        		bm = BitmapFactory.decodeFile(path);
        		addBitmapToMemoryCache(path, bm);
        		return bm;
        	} else {
//        		Logcat.w(path + " is not exists!");
        		return null;
        	}
        }
    }
    
    /**
     * 资源id
     * @param resId
     * @return
     */
    public synchronized Bitmap getBitmapFromMemCache(int resId, Context context) {
    	if (0 == resId || null == context) {
//    		Logcat.w("resId error:" + resId + " or context is null!");
    		return null;
    	}
    	Bitmap bm = mMemoryCache.get("" + resId);
    	if (null != bm) {
    		return bm;
    	} else {
    		try {
    			bm = BitmapFactory.decodeResource(context.getResources(), resId);
        		addBitmapToMemoryCache("" + resId, bm);
			} catch (Exception e) {
//				Logcat.w("resId error:" + resId);
			}
    		return bm;
    	}
    }

    /**
     * 移除缓存，如果之前是通过res id获取的，key就是id，如果是通过路径获取的，key就是路径
     * @param key
     */
    public synchronized void removeImageCache(String key) {
        if (key != null) {
            if (mMemoryCache != null) {
                Bitmap bm = mMemoryCache.remove(key);
                if (bm != null)
                    bm.recycle();
            }
        }
    }
    
	/**
	 * 添加一个图片到内存
	 * @param key 键值
	 * @param bitmap 图片
	 */
    private void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (mMemoryCache.get(key) == null) {
            if (key != null && bitmap != null)
                mMemoryCache.put(key, bitmap);
        }
    }
}
