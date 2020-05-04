package practice.com.learningimageprocessing.videoeffect.lovevideo.heartvideo.loveheart;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.p000v4.app.ActivityCompat;
import android.support.p003v7.app.AlertDialog;
import android.support.p003v7.app.AlertDialog.Builder;
import android.support.p003v7.app.AppCompatActivity;
import love.heart.gif.autoanimation.videomaker.R;

public class BaseActivity extends AppCompatActivity {
    private AlertDialog mAlertDialog;

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo18750a(final String str, String str2, final int i) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, str)) {
            mo18751a(getString(R.string.permission_title_rationale), str2, new OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    ActivityCompat.requestPermissions(BaseActivity.this, new String[]{str}, i);
                }
            }, getString(R.string.label_ok), null, getString(R.string.label_cancel));
            return;
        }
        ActivityCompat.requestPermissions(this, new String[]{str}, i);
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo18751a(@Nullable String str, @Nullable String str2, @Nullable OnClickListener onClickListener, @NonNull String str3, @Nullable OnClickListener onClickListener2, @NonNull String str4) {
        Builder builder = new Builder(this);
        builder.setTitle((CharSequence) str);
        builder.setMessage((CharSequence) str2);
        builder.setPositiveButton((CharSequence) str3, onClickListener);
        builder.setNegativeButton((CharSequence) str4, onClickListener2);
        this.mAlertDialog = builder.show();
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        super.onStop();
        if (this.mAlertDialog != null && this.mAlertDialog.isShowing()) {
            this.mAlertDialog.dismiss();
        }
    }
}
