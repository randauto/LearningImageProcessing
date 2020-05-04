package practice.com.learningimageprocessing.editor.videomaker.activities;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.photo.effect.editor.common.activities.AbstractGreetingActivity;
import com.photo.effect.editor.common.utils.FontUtils;
import com.photo.effect.editor.common.utils.PreferencesUtils;
import com.photo.effect.editor.videomaker.application.AssetManager;
import love.heart.gif.autoanimation.videomaker.R;

public class GreetingsActivity extends AbstractGreetingActivity {

    class C07235 implements OnClickListener {

        /* renamed from: a */
        final /* synthetic */ GreetingsActivity f5123a;

        public void onClick(DialogInterface dialogInterface, int i) {
            this.f5123a.mo16454d(AssetManager.getInstance().appLinkOnStore);
            PreferencesUtils.writeBooleanValue(this.f5123a.getApplicationContext(), PreferencesUtils.SHARED_PREFS_RATING_SUCCESS, true);
        }
    }

    class MoreApps implements View.OnClickListener {

        /* renamed from: a */
        final /* synthetic */ GreetingsActivity f5124a;

        public void onClick(View view) {
            this.f5124a.mo16454d(AssetManager.getInstance().publisherLinkOnStore);
        }
    }

    class RateMyApp implements View.OnClickListener {

        /* renamed from: a */
        final /* synthetic */ GreetingsActivity f5125a;

        public void onClick(View view) {
            this.f5125a.mo16454d(this.f5125a.getString(R.string.app_link_on_google_play, new Object[]{this.f5125a.getPackageName()}));
            PreferencesUtils.writeBooleanValue(this.f5125a.getApplicationContext(), PreferencesUtils.SHARED_PREFS_RATING_SUCCESS, true);
        }
    }

    class StartButton implements View.OnClickListener {
        StartButton() {
        }

        public void onClick(View view) {
            if (VERSION.SDK_INT < 23 || GreetingsActivity.this.mo16433a(GreetingsActivity.this.getString(R.string.request_read_storage_permission_rationale))) {
                GreetingsActivity.this.mo16432a();
            }
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo16432a() {
        startActivity(new Intent(this, SelectPhotoActivity.class));
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo16450a(Bundle bundle) {
        setContentView(R.layout.activity_greetings);
        FontUtils.setFontForViewGroup((ViewGroup) findViewById(R.id.activityGreetingsRootLayout), AssetManager.getInstance().fontMain);
        showRatingReminder(AssetManager.getInstance().numTimesActivityOpened);
        TextView textView = (TextView) findViewById(R.id.btnStart);
        textView.setOnClickListener(new StartButton());
        textView.setTypeface(AssetManager.getInstance().fontIcon);
    }

    public int getAppIcon() {
        return R.drawable.ic_launcher;
    }

    public String getAppName() {
        return getString(R.string.app_name);
    }

    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
