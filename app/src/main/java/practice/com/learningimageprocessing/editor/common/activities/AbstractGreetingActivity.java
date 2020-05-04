package practice.com.learningimageprocessing.editor.common.activities;

import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.p000v4.app.ActivityCompat;
import android.support.p000v4.content.ContextCompat;
import com.photo.effect.editor.common.utils.PreferencesUtils;
import java.util.HashMap;
import love.heart.gif.autoanimation.videomaker.R;

public abstract class AbstractGreetingActivity extends BaseActivity {

    class C03351 implements OnClickListener {
        C03351() {
        }

        public void onClick(DialogInterface dialogInterface, int i) {
            ActivityCompat.requestPermissions(AbstractGreetingActivity.this, new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, 110);
        }
    }

    class LaterButton implements OnClickListener {
        LaterButton() {
        }

        public void onClick(DialogInterface dialogInterface, int i) {
        }
    }

    class RateNow implements OnClickListener {
        RateNow() {
        }

        public void onClick(DialogInterface dialogInterface, int i) {
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setData(Uri.parse(AbstractGreetingActivity.this.getString(R.string.app_link_on_google_play, new Object[]{AbstractGreetingActivity.this.getPackageName()})));
            AbstractGreetingActivity.this.startActivity(intent);
            PreferencesUtils.writeBooleanValue(AbstractGreetingActivity.this.getApplicationContext(), PreferencesUtils.SHARED_PREFS_RATING_SUCCESS, true);
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public abstract void mo16432a();

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public boolean mo16433a(String str) {
        if (ContextCompat.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGE") == 0) {
            return true;
        }
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.READ_EXTERNAL_STORAGE")) {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, 110);
            return false;
        }
        mo16452a(str, (OnClickListener) new C03351());
        return false;
    }

    public abstract int getAppIcon();

    public abstract String getAppName();

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        int i2 = 0;
        switch (i) {
            case 110:
                HashMap hashMap = new HashMap();
                hashMap.put("android.permission.READ_EXTERNAL_STORAGE", Integer.valueOf(0));
                while (i2 < strArr.length) {
                    hashMap.put(strArr[i2], Integer.valueOf(iArr[i2]));
                    i2++;
                }
                if (((Integer) hashMap.get("android.permission.READ_EXTERNAL_STORAGE")).intValue() == 0) {
                    mo16432a();
                    return;
                }
                break;
            case 111:
                HashMap hashMap2 = new HashMap();
                hashMap2.put("android.permission.WRITE_EXTERNAL_STORAGE", Integer.valueOf(0));
                while (i2 < strArr.length) {
                    hashMap2.put(strArr[i2], Integer.valueOf(iArr[i2]));
                    i2++;
                }
                if (((Integer) hashMap2.get("android.permission.WRITE_EXTERNAL_STORAGE")).intValue() == 0) {
                    mo16432a();
                    return;
                }
                break;
            default:
                super.onRequestPermissionsResult(i, strArr, iArr);
                return;
        }
        mo16451a(getString(R.string.msg_permission_denied), 1);
    }

    public void showRatingReminder(int i) {
        if (i % 3 == 0 && !PreferencesUtils.getBooleanValue(getApplicationContext(), PreferencesUtils.SHARED_PREFS_RATING_SUCCESS, false)) {
            Builder builder = new Builder(this);
            String appName = getAppName();
            builder.setTitle(getString(R.string.rate_app_main_title));
            builder.setIcon(getAppIcon());
            builder.setMessage(getString(R.string.rate_app_sub_title, new Object[]{appName})).setPositiveButton(getString(R.string.rate_app_positive_answer), new RateNow()).setNegativeButton(getString(R.string.rate_app_negative_answer), new LaterButton()).setCancelable(false);
            builder.create().show();
        }
    }
}
