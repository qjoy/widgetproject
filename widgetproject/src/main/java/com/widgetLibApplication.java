package alexq;

import android.app.Application;

/**
 * @author AleXQ
 * @Date 16/8/1
 * @Description: ${TODO}(用一句话描述该文件做什么)
 */

public class widgetLibApplication extends Application {

	private static widgetLibApplication mLibInstance = null;
	private static Application mAppInstance = null;
	public static Application getInstance() {
		return mAppInstance;
	}

	public widgetLibApplication(Application instance) {
		mAppInstance = instance;
	}
}
