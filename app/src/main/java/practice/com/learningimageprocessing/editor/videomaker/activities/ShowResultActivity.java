package practice.com.learningimageprocessing.editor.videomaker.activities;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import practice.com.learningimageprocessing.R;
import practice.com.learningimageprocessing.editor.common.activities.AbstractEditVideoActivity;
import practice.com.learningimageprocessing.editor.common.activities.AbstractEditVideoActivity.IFFmpegExecuteListener;
import practice.com.learningimageprocessing.editor.common.constants.DevConstants;
import practice.com.learningimageprocessing.editor.common.utils.FFMPEGCommandUtils;
import practice.com.learningimageprocessing.editor.common.utils.StorageUtils;
import practice.com.learningimageprocessing.editor.videomaker.application.AssetManager;
import practice.com.learningimageprocessing.editor.videomaker.utils.CustomProgressDialog;

public class ShowResultActivity extends AbstractEditVideoActivity implements IFFmpegExecuteListener {
    private static final String CURRENT_VIDEO_SEC = "currentVideoSec";
    private static final String LOG_TAG = "ShowResultActivity";
    private int currentVideoSec;
    private boolean exportGifSuccess;
    private RelativeLayout mNativeView;
    private String outputGifPath;
    /* access modifiers changed from: private */
    public String outputVideoPath;
    private OnClickListener positiveButtonListener = new C07346();
    private CustomProgressDialog progressDialog;
    private TextView tvBack;
    /* access modifiers changed from: private */
    public VideoView videoView;

    class C07281 implements View.OnClickListener {
        C07281() {
        }

        public void onClick(View view) {
            ShowResultActivity.this.onBackPressed();
        }
    }

    class C07302 implements OnPreparedListener {

        class C07291 implements OnSeekCompleteListener {
            C07291() {
            }

            public void onSeekComplete(MediaPlayer mediaPlayer) {
                ShowResultActivity.this.videoView.start();
            }
        }

        C07302() {
        }

        public void onPrepared(MediaPlayer mediaPlayer) {
            mediaPlayer.setLooping(true);
            mediaPlayer.setOnSeekCompleteListener(new C07291());
        }
    }

    class C07346 implements OnClickListener {
        C07346() {
        }

        public void onClick(DialogInterface dialogInterface, int i) {
            StorageUtils.deleteFile(ShowResultActivity.this.outputVideoPath);
            StorageUtils.refreshStorage(ShowResultActivity.this, ShowResultActivity.this.outputVideoPath);
            ShowResultActivity.this.outputVideoPath = null;
            ShowResultActivity.this.videoView.stopPlayback();
            ShowResultActivity.this.videoView.setVisibility(4);
        }
    }

    class GifGenerator implements View.OnClickListener {
        GifGenerator() {
        }

        public void onClick(View view) {
            ShowResultActivity.this.exportGif();
        }
    }

    class ShareVideo implements View.OnClickListener {
        ShareVideo() {
        }

        public void onClick(View view) {
            ShowResultActivity.this.shareVideo(null);
        }
    }

    /* access modifiers changed from: private */
    public void exportGif() {
        if (this.outputVideoPath != null) {
            this.outputGifPath = generateOutputFilePath();
            mo16424b(FFMPEGCommandUtils.buildConvertVideoToGifCommand(this.outputVideoPath, this.outputGifPath));
            return;
        }
        mo16453c(getString(R.string.message_no_video_to_export_gif));
    }

    private String generateOutputFilePath() {
        File externalStoragePublicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        StringBuilder sb = new StringBuilder();
        sb.append(DevConstants.OUTPUT_FILE_NAME_PREFIX);
        sb.append(new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()));
        sb.append(DevConstants.GIF_EXTENSION);
        return new File(externalStoragePublicDirectory, sb.toString()).getPath();
    }

    private void initializeShareButtons() {
        findViewById(R.id.btnShareOther).setOnClickListener(new ShareVideo());
        findViewById(R.id.tvCreateGif).setOnClickListener(new GifGenerator());
    }

    private void playVideo() {
        if (this.outputVideoPath != null) {
            this.videoView.setVisibility(View.VISIBLE);
            this.videoView.setVideoPath(this.outputVideoPath);
            if (this.currentVideoSec != 0) {
                this.videoView.seekTo(this.currentVideoSec);
                Log.i(LOG_TAG, String.format("Seek video to: %d", new Object[]{Integer.valueOf(this.currentVideoSec)}));
                return;
            }
            this.videoView.start();
        }
    }

    /* access modifiers changed from: private */
    public void shareVideo(String str) {
        if (this.outputVideoPath == null) {
            mo16453c(getString(R.string.message_no_video_to_share));
        } else if (mo16423a(str) || str == null) {
            startActivityForResult(Intent.createChooser(mo16421a(DevConstants.VIDEO_SHARE_TYPE, this.outputVideoPath, str), getString(R.string.title_share_to)), 102);
        } else {
            Log.w(LOG_TAG, "Application to share have not installed yet.");
            mo16454d(getString(R.string.app_link_on_google_play, new Object[]{str}));
        }
    }

    /* access modifiers changed from: private */
    public void showSequenceAd(boolean z, boolean z2) {
        try {
            GradientDrawable gradientDrawable = new GradientDrawable();
            gradientDrawable.setColor(-1);
            gradientDrawable.setStroke(2, -8875876);
            this.mNativeView.setBackground(gradientDrawable);
        } catch (Exception unused) {
            this.mNativeView.setVisibility(8);
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo16450a(Bundle bundle) {
        setContentView(R.layout.activity_show_result);
        mo16518b();
        mo16422a();
        registerFFmpegExecuteListener(this);
        if (bundle == null) {
            this.outputVideoPath = getIntent().getStringExtra(SelectEffectActivity.OUTPUT_FILE_PATH_KEY);
            this.currentVideoSec = 0;
        } else {
            this.outputVideoPath = bundle.getString(SelectEffectActivity.OUTPUT_FILE_PATH_KEY);
            this.currentVideoSec = bundle.getInt(CURRENT_VIDEO_SEC);
        }
        this.tvBack = (TextView) findViewById(R.id.tvBack);
        this.tvBack.setOnClickListener(new C07281());
        this.tvBack.setTypeface(AssetManager.getInstance().fontIcon);
        this.videoView = (VideoView) findViewById(R.id.videoView);
        this.videoView.setOnPreparedListener(new C07302());
        initializeShareButtons();
        this.mNativeView = (RelativeLayout) findViewById(R.id.nativeView);
    }

    /* access modifiers changed from: protected */
    /* renamed from: b */
    public void mo16518b() {
        this.progressDialog = new CustomProgressDialog(this, false);
        this.progressDialog.setCancelable(false);
        this.progressDialog.setCanceledOnTouchOutside(false);
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 102) {
            Log.i(LOG_TAG, "Show interstitial ad");
        }
    }

    public void onFFmpegFailure() {
        this.exportGifSuccess = false;
    }

    public void onFFmpegFinish() {
        String string;
        try {
            if (this.progressDialog != null) {
                this.progressDialog.dismiss();
            }
        } catch (Exception unused) {
        }
        playVideo();
        if (this.exportGifSuccess) {
            StorageUtils.refreshStorage(this, this.outputGifPath);
            string = getString(R.string.message_export_gif_success, new Object[]{Environment.DIRECTORY_PICTURES});
        } else {
            string = getString(R.string.message_export_gif_failed);
        }
        mo16453c(string);
    }

    public void onFFmpegProgress(String str) {
    }

    public void onFFmpegStart() {
        this.currentVideoSec = this.videoView.getCurrentPosition();
        this.videoView.stopPlayback();
        try {
            if (this.progressDialog != null) {
                this.progressDialog.show();
            }
        } catch (Exception unused) {
        }
    }

    public void onFFmpegSuccess() {
        this.exportGifSuccess = true;
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        this.currentVideoSec = this.videoView.getCurrentPosition();
        this.videoView.stopPlayback();
        super.onPause();
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        playVideo();
    }

    /* access modifiers changed from: protected */
    public void onSaveInstanceState(Bundle bundle) {
        bundle.putString(SelectEffectActivity.OUTPUT_FILE_PATH_KEY, this.outputVideoPath);
        bundle.putInt(CURRENT_VIDEO_SEC, this.currentVideoSec);
        super.onSaveInstanceState(bundle);
    }
}
