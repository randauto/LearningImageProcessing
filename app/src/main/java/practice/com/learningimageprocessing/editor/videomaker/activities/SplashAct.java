package practice.com.learningimageprocessing.editor.videomaker.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.facebook.ads.AdError;
import love.heart.gif.autoanimation.videomaker.R;

public class SplashAct extends Activity {
    private int SPLASH_TIME_OUT = AdError.SERVER_ERROR_CODE;

    class C03371 implements Runnable {
        C03371() {
        }

        public void run() {
            SplashAct.this.startActivity(new Intent(SplashAct.this, GreetingsActivity.class));
            SplashAct.this.finish();
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.splash);
        new Handler().postDelayed(new C03371(), (long) this.SPLASH_TIME_OUT);
    }
}
