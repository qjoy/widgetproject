package com.alexq.baseutils;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;


public class Utils {
    public static int DpToPx(Application context, float x) {
        int result = 0;
        final float scale = context.getResources().getDisplayMetrics().density;
        result = (int) (x * scale + 0.5f);
        return result;
    }

    public static int PxToDp(Application context, int x) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (x / scale + 0.5f);
    }

    /**
     * 获取状态栏高度
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Application context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        }
        return 0;
    }

	private static String sVersionName = "";
    public static String getVersionName(Application context) {
	    if (TextUtils.isEmpty(sVersionName)) {
		    String result = "";
		    PackageInfo packInfo = null;
		    PackageManager pkgManager = context.getPackageManager();
		    try {
			    packInfo = pkgManager.getPackageInfo(context.getPackageName(), 0);
			    result += "version::" + packInfo.versionName;
//			result += ", versionCode: " + packInfo.versionCode;
//			if(android.os.Build.VERSION.SDK_INT >= 9) {
//				result += ", firstInstallTime: " + packInfo.firstInstallTime;
//				result += ", lastUpdateTime: " + packInfo.lastUpdateTime;
//			}
		    } catch (NameNotFoundException e) {
			    e.printStackTrace();
		    }
		    sVersionName = result;
		    return result;
	    }
	    else
		    return sVersionName;
    }

	private static String sEasyVersionName = "";
    public static String getEasyVersionName(Application context) {
	    if (TextUtils.isEmpty(sEasyVersionName)) {
		    String result = "";
		    PackageInfo packInfo = null;
		    PackageManager pkgManager = context.getPackageManager();
		    try {
			    packInfo = pkgManager.getPackageInfo(context.getPackageName(), 0);
			    result += packInfo.versionName;
//			result += ", versionCode: " + packInfo.versionCode;
//			if(android.os.Build.VERSION.SDK_INT >= 9) {
//				result += ", firstInstallTime: " + packInfo.firstInstallTime;
//				result += ", lastUpdateTime: " + packInfo.lastUpdateTime;
//			}
		    } catch (NameNotFoundException e) {
			    e.printStackTrace();
		    }
		    sEasyVersionName = result;
		    return result;
	    }
	    else
		    return sEasyVersionName;
    }

	private static String sSimpleVersionName = "";
    public static String getSimpleVersionName(Application context) {
	    if (TextUtils.isEmpty(sSimpleVersionName)) {
		    String result = "";
		    PackageInfo packInfo = null;
		    PackageManager pkgManager = context.getPackageManager();
		    try {
			    packInfo = pkgManager.getPackageInfo(context.getPackageName(), 0);
			    result = packInfo.versionName;
		    } catch (NameNotFoundException e) {
			    e.printStackTrace();
		    }
		    sSimpleVersionName = result;
		    return result;
	    }
	    else
		    return sSimpleVersionName;
    }

	private static String sVertionCode = "";
    public static String getVersionCode(Application context) {
	    if (TextUtils.isEmpty(sVertionCode)) {
		    String result = "";
		    PackageInfo packInfo = null;
		    PackageManager pkgManager = context.getPackageManager();
		    try {
			    packInfo = pkgManager.getPackageInfo(context.getPackageName(), 0);
			    result = String.valueOf(packInfo.versionCode);
		    } catch (NameNotFoundException e) {
			    e.printStackTrace();
		    }
		    sVertionCode = result;
		    return result;
	    }
	    else
		    return sVertionCode;

    }



    public static String getIMEI(Application context) {
        String Imei = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
        return Imei;
    }

    public static Bitmap convertDrawable2Bitmap(Drawable drawable) {
        BitmapDrawable bd = (BitmapDrawable) drawable;
        return bd.getBitmap();
    }

    public static Drawable convertBitmap2Drawable(Application context, Bitmap bitmap) {
        Resources res = context.getResources();
        BitmapDrawable bd = new BitmapDrawable(res, bitmap);
        return bd;
    }

    public static Drawable getImageDrawable(Application context, int resId, TileMode mode) {
        BitmapDrawable drawble = null;
        drawble = new BitmapDrawable(context.getResources(), getImageBitmap(context, resId));
        if (mode != null) drawble.setTileModeX(mode);
        return drawble;
    }

    public static Bitmap getImageBitmap(Application context, int resId) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
        opts.inPurgeable = true;
        opts.inInputShareable = true;
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(), resId, opts);
        opts.inSampleSize = 1;
        opts.inDensity = (int) (opts.inDensity / context.getResources().getDisplayMetrics().density);
        opts.inJustDecodeBounds = false;
        Bitmap result = BitmapFactory.decodeResource(context.getResources(), resId, opts);
        return result;
    }

    public static byte[] convertBitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    public static final byte[] convertObject2Bytes(Object obj) {
        byte[] bytes = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            bytes = bos.toByteArray();
            oos.close();
            bos.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return bytes;
    }

    public static final Object convertBytes2Object(byte[] bytes) {
        Object obj = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bis);
            obj = ois.readObject();
            ois.close();
            bis.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return obj;
    }

    public static String convertMapToStr(Map<String, String> args) {
        if (args == null) {
            return null;
        }
        JSONObject tmp = null;
        tmp = new JSONObject(args);
        return tmp.toString();
    }

    public static Map<String, String> convertStrToMap(String args) {
        if (args == null) {
            return null;
        }
        Map<String, String> result = new HashMap<String, String>();
        JSONObject tmp = null;
        try {
            tmp = new JSONObject(args);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Iterator<?> iterator = tmp.keys();
        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            String value = tmp.optString(key);
            result.put(key, value);
        }
        return result;
    }

    @SuppressLint("NewApi")
    public static int getRealWidth(Application context, Display mDisplay) {
        int w = 0;
        w = context.getResources().getDisplayMetrics().widthPixels;
        if (android.os.Build.VERSION.SDK_INT >= 14 && android.os.Build.VERSION.SDK_INT < 17) {
            try {
                w = (Integer) Display.class.getMethod("getRawWidth").invoke(mDisplay);
            } catch (Exception ignored) {

            }
        } else if (android.os.Build.VERSION.SDK_INT >= 17) {
            try {
                DisplayMetrics realMetrics = new DisplayMetrics();
                mDisplay.getRealMetrics(realMetrics);
                w = realMetrics.widthPixels;
            } catch (Exception ignored) {

            }
        } else {
            try {
                w = (Integer) Display.class.getMethod("getRealWidth").invoke(mDisplay);
            } catch (Exception ignored) {

            }
        }
        return w;
    }

    @SuppressLint("NewApi")
    public static int getRealHeight(Application context, Display mDisplay) {
        int h = 0;
        h = context.getResources().getDisplayMetrics().heightPixels;
        if (android.os.Build.VERSION.SDK_INT >= 14 && android.os.Build.VERSION.SDK_INT < 17) {
            try {
                h = (Integer) Display.class.getMethod("getRawHeight").invoke(mDisplay);
            } catch (Exception ignored) {

            }
        } else if (android.os.Build.VERSION.SDK_INT >= 17) {
            try {
                DisplayMetrics realMetrics = new DisplayMetrics();
                mDisplay.getRealMetrics(realMetrics);
                h = realMetrics.heightPixels;
            } catch (Exception ignored) {

            }
        } else {
            try {
                h = (Integer) Display.class.getMethod("getRealHeight").invoke(mDisplay);
            } catch (Exception ignored) {

            }
        }
        return h;
    }

    public static boolean isAppOnForeground(Application context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> tasksInfo = manager.getRunningTasks(1);
        if (tasksInfo.size() > 0) {
            // 应用程序位于堆栈的顶层
            String src = context.getPackageName();
            String des = tasksInfo.get(0).topActivity.getPackageName();
            if (src.equalsIgnoreCase(des)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNull(String key) {
        if (key == null) {
            return true;
        }
        if (key.equals("") || key.equals("null")) {
            return true;
        }
        return false;
    }

    public static Intent createExplicitFromImplicitIntent(Application context, Intent implicitIntent) {
        // Retrieve all services that can match the given intent
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> resolveInfo = null;
        try {
            resolveInfo = pm.queryIntentServices(implicitIntent, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Make sure only one match was found
        //fix bug:java.lang.IndexOutOfBoundsException  Invalid index 0, size is 0
        if (resolveInfo == null || resolveInfo.isEmpty()) {
            return null;
        }

        // Get component info and create ComponentName
        ResolveInfo serviceInfo = resolveInfo.get(0);
        String packageName = serviceInfo.serviceInfo.packageName;
        String className = serviceInfo.serviceInfo.name;
        ComponentName component = new ComponentName(packageName, className);

        // Create a new intent. Use the old one for extras and such reuse
        Intent explicitIntent = new Intent(implicitIntent);

        // Set the component to be explicit
        explicitIntent.setComponent(component);

        return explicitIntent;
    }

    public static <T> List<T> convertMap2List(Map<String, T> map) {
        List<T> list = new ArrayList<T>();
        Set keySet = map.keySet();
        for (Iterator<String> it = keySet.iterator(); it.hasNext(); ) {
            String key = it.next();
            list.add(map.get(key));
        }
        return list;
    }

    public static String sort(SortedMap<String, String> arg) {
        List<String> values = new ArrayList<String>();
        for (Map.Entry<String, String> tmp : arg.entrySet()) {
            values.add(tmp.getValue());
        }
        Collections.sort(values);
        StringBuilder builder = new StringBuilder();
        for (String tmp : values) {
            builder.append(tmp);

        }
        return builder.toString();
    }

    private static TypedValue mTypedValue = new TypedValue();

    /**
     * 根据id不同，如果id对应的是dp的，那函数返回的就是dp值，如果id对应的是sp的，那函数返回的就是sp值
     *
     * @param context
     * @param resId
     * @return
     */
    public static int getXMLDef(Application context, int resId) {
        synchronized (mTypedValue) {
            TypedValue typedValue = mTypedValue;
            context.getResources().getValue(resId, typedValue, true);
            return (int) TypedValue.complexToFloat(typedValue.data);
        }
    }

    public static int getScreenWidth(Application context) {
        DisplayMetrics metric = new DisplayMetrics();
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;
        return width;
    }

    public static int getScreenHeight(Application context) {
        DisplayMetrics metric = new DisplayMetrics();
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metric);
        int height = metric.heightPixels;
        return height;
    }

    /**
     * 获取栈顶activty的名称
     *
     * @param context
     * @return
     */
    public static String getTopActivityName(Application context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(1);

        if (runningTaskInfos != null) {
            String totalActName = (runningTaskInfos.get(0).topActivity).getClassName();
            String[] array = totalActName.split("[.]");
            int size = array.length;
            if (size >= 1) {
                return array[size - 1];
            } else {
                return totalActName;
            }
        } else
            return null;
    }

    /**
     * 文字的高，不包括上下留白
     */
    public static float getFontHeightOnlyText(float fontSize) {
        Paint paint = new Paint();
        paint.setTextSize(fontSize);
        Paint.FontMetrics fm = paint.getFontMetrics();
        return fm.descent - fm.ascent;
    }

    public static Bitmap rotateBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    /**
     * 文字的高，包括上下留白
     */
    public static float getFontHeight(float fontSize) {
        Paint paint = new Paint();
        paint.setTextSize(fontSize);
        Paint.FontMetrics fm = paint.getFontMetrics();
        return fm.bottom - fm.top;
    }

    public static Bitmap scaleBitmap(Bitmap source, int width, int height) {
        Matrix matrix = new Matrix();
        int sourceWidth = source.getWidth();
        int sourceHeight = source.getHeight();

        float xScale = (float) width / sourceWidth;
        float yScale = (float) height / sourceHeight;

        matrix.postScale(xScale, yScale);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    public static Bitmap scaleBitmap(Bitmap source, float scale) {
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    /**
     * 判断GPS是否开启，GPS或者AGPS开启一个就认为是开启的
     *
     * @param context
     * @return true 表示开启
     */
    public static final boolean GpsIsOPen(final Application context) {
        LocationManager locationManager
                = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps || network) {
            return true;
        }
        return false;
    }

    public static double getScreenInches(Application context) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        double x = Math.pow(dm.widthPixels / dm.xdpi, 2);
        double y = Math.pow(dm.heightPixels / dm.ydpi, 2);
        double screenInches = Math.sqrt(x + y); //屏幕尺寸（英寸）
        return screenInches;
    }



}
