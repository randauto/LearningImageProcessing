package practice.com.learningimageprocessing.editor.common.application;

import android.content.Context;
import android.util.DisplayMetrics;

import practice.com.learningimageprocessing.R;
import practice.com.learningimageprocessing.editor.common.utils.PreferencesUtils;

public class AbstractAssetManager {

    /* renamed from: a */
    protected Context f5122a;
    public String appLinkOnStore;
    public boolean isFirstTimeAppOpen;
    public int numTimesActivityOpened;
    public String publisherLinkOnStore;
    public int screenHeight;
    public int screenWidth;

    private void initializeAppInfo(Context context) {
        this.publisherLinkOnStore = context.getString(R.string.publisher_on_google_play);
        this.isFirstTimeAppOpen = PreferencesUtils.getBooleanValue(context, PreferencesUtils.SHARED_PREFS_FIRST_TIME_APP_OPEN, true);
        PreferencesUtils.writeBooleanValue(context, PreferencesUtils.SHARED_PREFS_FIRST_TIME_APP_OPEN, false);
        this.numTimesActivityOpened = PreferencesUtils.getIntValue(context, PreferencesUtils.SHARED_PREFS_NUM_TIMES_APP_OPEN);
        this.numTimesActivityOpened++;
        PreferencesUtils.writeIntValue(context, PreferencesUtils.SHARED_PREFS_NUM_TIMES_APP_OPEN, this.numTimesActivityOpened);
    }

    public void initialize(Context context) {
        this.f5122a = context;
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        this.screenWidth = displayMetrics.widthPixels;
        this.screenHeight = displayMetrics.heightPixels;
        initializeAppInfo(context);
    }
}
