package practice.com.learningimageprocessing.videoeffect.lovevideo.heartvideo.loveheart.Ads;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.support.p003v7.app.AppCompatDelegate;
import com.facebook.ads.AdSettings;
import com.facebook.ads.InterstitialAd;
import com.google.android.gms.ads.AdRequest.Builder;
import com.onesignal.OSNotification;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;
import com.onesignal.OneSignal.OSInFocusDisplayOption;
import com.photo.effect.editor.videomaker.application.AssetManager;

public class AppController extends MultiDexApplication {
    public static String folderName = "";
    private static AppController mInstance;

    private class NotificationOpenedHandler implements com.onesignal.OneSignal.NotificationOpenedHandler {
        private NotificationOpenedHandler() {
        }

        public void notificationOpened(OSNotificationOpenResult oSNotificationOpenResult) {
            OneSignal.clearOneSignalNotifications();
        }
    }

    private class NotificationReceivedHandler implements com.onesignal.OneSignal.NotificationReceivedHandler {
        private NotificationReceivedHandler() {
        }

        public void notificationReceived(OSNotification oSNotification) {
        }
    }

    public static synchronized AppController getInstance() {
        AppController appController;
        synchronized (AppController.class) {
            appController = mInstance;
        }
        return appController;
    }

    public static void loadAM() {
        AdsConfig.mAdmobInterstitialAd.loadAd(new Builder().addTestDevice("1111").build());
    }

    public static void loadFB() {
        AdsConfig.mFBInterstitialAd.loadAd();
    }

    /* access modifiers changed from: protected */
    public void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }

    public void onCreate() {
        super.onCreate();
        mInstance = this;
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        AdsConfig.mFBInterstitialAd = new InterstitialAd(this, "1111");
        AdSettings.addTestDevice("1294e760-3d10-4d3b-b951-561677c47411");
        AdsConfig.mAdmobInterstitialAd = new com.google.android.gms.ads.InterstitialAd(this);
        AdsConfig.mAdmobInterstitialAd.setAdUnitId("ca-app-pub-4087317046605472/4975124770");
        OneSignal.startInit(this).setNotificationReceivedHandler(new NotificationReceivedHandler()).setNotificationOpenedHandler(new NotificationOpenedHandler()).inFocusDisplaying(OSInFocusDisplayOption.Notification).init();
        AssetManager.getInstance().initialize(this);
        OneSignal.startInit(this).init();
    }
}
