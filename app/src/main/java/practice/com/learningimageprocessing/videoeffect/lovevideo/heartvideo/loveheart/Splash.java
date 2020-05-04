package practice.com.learningimageprocessing.videoeffect.lovevideo.heartvideo.loveheart;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.p003v7.app.AppCompatActivity;
import com.crashlytics.android.Crashlytics;
import com.google.android.gms.ads.AdListener;
import love.heart.gif.autoanimation.videomaker.R;
import p045io.fabric.sdk.android.Fabric;
import videoeffect.lovevideo.heartvideo.loveheart.Ads.AdsConfig;
import videoeffect.lovevideo.heartvideo.loveheart.Ads.AppController;

public class Splash extends AppCompatActivity {
    public void GoNext() {
        if (AdsConfig.isConnected()) {
            AdsConfig.setAdmobInterstitial(new AdListener() {
                public void onAdClosed() {
                    Splash.this.startActivity(new Intent(Splash.this, MainActivity.class));
                    Splash.this.finish();
                }

                public void onAdFailedToLoad(int i) {
                    Splash.this.startActivity(new Intent(Splash.this, MainActivity.class));
                    Splash.this.finish();
                }
            });
            return;
        }
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    /* access modifiers changed from: protected */
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_splash);
        Fabric.with(this, new Crashlytics());
        AppController.loadAM();
        AppController.loadFB();
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Splash.this.GoNext();
            }
        }, 4000);
    }
}
