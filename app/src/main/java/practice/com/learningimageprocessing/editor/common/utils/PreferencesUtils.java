package practice.com.learningimageprocessing.editor.common.utils;

import android.content.Context;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.preference.PreferenceManager;

public class PreferencesUtils {
    public static final String SHARED_PREFS_FIRST_TIME_APP_OPEN = "app.first.time.open";
    public static final String SHARED_PREFS_NUM_TIMES_APP_OPEN = "app.open.num.times";
    public static final String SHARED_PREFS_RATING_SUCCESS = "app.rating.success";

    public static boolean getBooleanValue(Context context, String str, boolean z) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(str, z);
    }

    public static int getIntValue(Context context, String str) {
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(str, 0);
    }

    public static String getStringValue(Context context, String str) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(str, null);
    }

    public static void registerSharedPreferenceChangeListener(OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {
        PreferenceManager.getDefaultSharedPreferences((Context) onSharedPreferenceChangeListener).registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
    }

    public static void unregisterSharedPreferenceChangeListener(OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {
        PreferenceManager.getDefaultSharedPreferences((Context) onSharedPreferenceChangeListener).unregisterOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
    }

    public static void writeBooleanValue(Context context, String str, boolean z) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean(str, z).apply();
    }

    public static int writeIntValue(Context context, String str, int i) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putInt(str, i).apply();
        return i;
    }

    public static void writeStringValue(Context context, String str, String str2) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(str, str2).apply();
    }
}
