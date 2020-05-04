package practice.com.learningimageprocessing.editor.common.activities;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import com.photo.effect.editor.common.dto.TaskDto;
import love.heart.gif.autoanimation.videomaker.R;

public abstract class AbstractSelectStyleActivity extends BaseActivity {

    /* renamed from: a */
    protected boolean f5119a;

    /* renamed from: b */
    protected ProgressDialog f5120b;

    protected class AssetLoadingTask extends AsyncTask<Void, Integer, TaskDto> {

        /* renamed from: a */
        final /* synthetic */ AbstractSelectStyleActivity f5121a;

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public TaskDto doInBackground(Void... voidArr) {
            TaskDto taskDto = new TaskDto();
            try {
                this.f5121a.mo16441a();
                publishProgress(new Integer[]{Integer.valueOf(100)});
            } catch (Exception e) {
                taskDto.setError(e);
                Log.e("SelectStyleActivity", "Initialize asset error!", e);
                publishProgress(new Integer[]{Integer.valueOf(1)});
            }
            return taskDto;
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public void onPostExecute(TaskDto taskDto) {
            this.f5121a.f5120b.dismiss();
            if (taskDto.hasError()) {
                this.f5121a.mo16451a(this.f5121a.getString(R.string.msg_load_asset_error), 1);
            } else {
                this.f5121a.mo16442b();
            }
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            this.f5121a.f5120b.show();
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public abstract void mo16441a();

    /* access modifiers changed from: protected */
    /* renamed from: b */
    public abstract void mo16442b();

    public void onBackPressed() {
        this.f5119a = false;
        super.onBackPressed();
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.f5120b = new ProgressDialog(this);
        this.f5120b.setTitle(getString(R.string.title_please_wait));
        this.f5120b.setProgressStyle(0);
        this.f5119a = true;
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        this.f5119a = false;
    }
}
