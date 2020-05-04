package practice.com.learningimageprocessing.editor.common.activities;

import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.support.p000v4.content.FileProvider;
import android.util.Log;
import com.github.hiteshsondhi88.libffmpeg.ExecuteBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.FFmpeg;
import com.github.hiteshsondhi88.libffmpeg.LoadBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegCommandAlreadyRunningException;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegNotSupportedException;
import com.photo.effect.editor.common.constants.FFMPEGConstants;
import java.io.File;

public abstract class AbstractEditVideoActivity extends BaseActivity {

    /* renamed from: a */
    protected FFmpeg f5112a;

    /* renamed from: b */
    protected IFFmpegExecuteListener f5113b;

    class C11721 extends LoadBinaryResponseHandler {
        C11721() {
        }

        public void onFailure() {
            super.onFailure();
            Log.i("EditVideoActivity", "Load FFMPEG failed!");
            AbstractEditVideoActivity.this.mo16453c("Load FFMPEG failed!");
        }

        public void onFinish() {
            super.onFinish();
        }

        public void onStart() {
            super.onStart();
        }

        public void onSuccess() {
            super.onSuccess();
            Log.i("EditVideoActivity", "Load FFMPEG success.");
        }
    }

    class C11732 extends ExecuteBinaryResponseHandler {
        C11732() {
        }

        public void onFailure(String str) {
            Log.i("EditVideoActivity", "Execute FFMPEG failed!");
            Log.i("EditVideoActivity", str);
            if (AbstractEditVideoActivity.this.f5113b != null) {
                AbstractEditVideoActivity.this.f5113b.onFFmpegFailure();
            }
        }

        public void onFinish() {
            Log.i("EditVideoActivity", "Execute FFMPEG finished.");
            if (AbstractEditVideoActivity.this.f5113b != null) {
                AbstractEditVideoActivity.this.f5113b.onFFmpegFinish();
            }
        }

        public void onProgress(String str) {
            Log.i("EditVideoActivity", str);
            if (AbstractEditVideoActivity.this.f5113b != null) {
                AbstractEditVideoActivity.this.f5113b.onFFmpegProgress(str);
            }
        }

        public void onStart() {
            if (AbstractEditVideoActivity.this.f5113b != null) {
                AbstractEditVideoActivity.this.f5113b.onFFmpegStart();
            }
        }

        public void onSuccess(String str) {
            Log.i("EditVideoActivity", "Execute FFMPEG success.");
            Log.i("EditVideoActivity", str);
            if (AbstractEditVideoActivity.this.f5113b != null) {
                AbstractEditVideoActivity.this.f5113b.onFFmpegSuccess();
            }
        }
    }

    public interface IFFmpegExecuteListener {
        void onFFmpegFailure();

        void onFFmpegFinish();

        void onFFmpegProgress(String str);

        void onFFmpegStart();

        void onFFmpegSuccess();
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public Intent mo16421a(String str, String str2, String str3) {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType(str);
        intent.putExtra("android.intent.extra.STREAM", FileProvider.getUriForFile(this, "love.heart.gif.autoanimation.videomaker.provider", new File(str2)));
        if (str3 != null) {
            intent.setPackage(str3);
        }
        return intent;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo16422a() {
        this.f5112a = FFmpeg.getInstance(this);
        try {
            this.f5112a.loadBinary(new C11721());
        } catch (FFmpegNotSupportedException e) {
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public boolean mo16423a(String str) {
        try {
            getPackageManager().getPackageInfo(str, 1);
            return true;
        } catch (NameNotFoundException unused) {
            return false;
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: b */
    public void mo16424b(String str) {
        try {
            this.f5112a.execute(str.split(FFMPEGConstants.COMMAND_SPLITTER), new C11732());
        } catch (FFmpegCommandAlreadyRunningException e) {
            Log.e("EditVideoActivity", "Exception occur when execute FFMPEG!", e);
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    public void registerFFmpegExecuteListener(IFFmpegExecuteListener iFFmpegExecuteListener) {
        this.f5113b = iFFmpegExecuteListener;
    }
}
