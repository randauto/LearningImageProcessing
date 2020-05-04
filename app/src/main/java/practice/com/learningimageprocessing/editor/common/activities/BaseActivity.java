package practice.com.learningimageprocessing.editor.common.activities;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import love.heart.gif.autoanimation.videomaker.R;

public abstract class BaseActivity extends Activity {
    /* access modifiers changed from: protected */
    /* renamed from: a */
    public abstract void mo16450a(Bundle bundle);

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo16451a(String str, int i) {
        Toast.makeText(this, str, i).show();
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo16452a(String str, OnClickListener onClickListener) {
        new Builder(this).setMessage(str).setPositiveButton(getString(R.string.yes), onClickListener).setNegativeButton(getString(R.string.no), null).create().show();
    }

    /* access modifiers changed from: protected */
    /* renamed from: c */
    public void mo16453c(String str) {
        mo16451a(str, 0);
    }

    /* access modifiers changed from: protected */
    /* renamed from: d */
    public void mo16454d(String str) {
        startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)));
        StringBuilder sb = new StringBuilder();
        sb.append("Redirect to ");
        sb.append(str);
        Log.i("Activity", sb.toString());
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setFlags(1024, 1024);
        mo16450a(bundle);
    }
}
