package practice.com.learningimageprocessing.editor.videomaker.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import practice.com.learningimageprocessing.R;
import practice.com.learningimageprocessing.editor.common.activities.AbstractEditVideoActivity;
import practice.com.learningimageprocessing.editor.common.constants.DevConstants;
import practice.com.learningimageprocessing.editor.common.utils.AssetUtils;
import practice.com.learningimageprocessing.editor.common.utils.FFMPEGCommandUtils;
import practice.com.learningimageprocessing.editor.common.utils.StorageUtils;
import practice.com.learningimageprocessing.editor.videomaker.application.AnimationsContainer;
import practice.com.learningimageprocessing.editor.videomaker.application.AssetManager;
import practice.com.learningimageprocessing.editor.videomaker.utils.CustomProgressDialog;
import practice.com.learningimageprocessing.editor.videomaker.utils.Effect;
import practice.com.learningimageprocessing.videoeffect.lovevideo.heartvideo.loveheart.Ads.AdsConfig;

public class SelectEffectActivity extends AbstractEditVideoActivity implements AbstractEditVideoActivity.IFFmpegExecuteListener {
    public static final String EFFECT_FOLDER_NAME = "effect";
    public static final String EFFECT_TEMP_FOLDER_NAME = "effectTemp";
    /* access modifiers changed from: private */
    public static final String LOG_TAG = "SelectEffectActivity";
    public static final String OUTPUT_FILE_PATH_KEY = "outputFilePath";
    public static final String SELECTED_INDEX = "selectedIndex";
    private RelativeLayout adView;
    /* access modifiers changed from: private */
    public boolean assetExistsChecked;
    private TextView btnSave;
    /* access modifiers changed from: private */
    public String effectInternalFolderPath;
    /* access modifiers changed from: private */
    public String effectTempInternalFolderPath;
    private boolean executeCommandSuccess;
    private ImageView ivFootage;
    private ImageView ivOverlay;
    /* access modifiers changed from: private */
    public String outputFilePath;
    /* access modifiers changed from: private */
    public AnimationsContainer.FramesSequenceAnimation playingAnimation;
    /* access modifiers changed from: private */
    public CustomProgressDialog progressDialog;
    /* access modifiers changed from: private */
    public int selectedIndex;
    private String selectedPhotoPath;
    private TextView tvBack;

    class BackPress implements OnClickListener {
        BackPress() {
        }

        public void onClick(View view) {
            SelectEffectActivity.this.onBackPressed();
        }
    }

    @SuppressLint({"StaticFieldLeak"})
    public class CheckEffectAssetTask extends AsyncTask<Void, Integer, Void> {
        public CheckEffectAssetTask() {
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public Void doInBackground(Void... voidArr) {
            for (Effect effect : Effect.LIST) {
                File file = new File(effect.getFolderPath(SelectEffectActivity.this.effectInternalFolderPath));
                if (!file.exists()) {
                    String c = SelectEffectActivity.LOG_TAG;
                    StringBuilder sb = new StringBuilder();
                    sb.append(file);
                    sb.append(" doesn't exist, will create one by unzip from assets.");
                    Log.w(c, sb.toString());
                    AssetUtils.unzipFromAssets(SelectEffectActivity.this, effect.getZipPath(), effect.getFolderPath(SelectEffectActivity.this.effectInternalFolderPath));
                }
                publishProgress(new Integer[]{Integer.valueOf((int) ((((float) (Effect.LIST.indexOf(effect) + 1)) / ((float) Effect.LIST.size())) * 100.0f))});
            }
            return null;
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public void onPostExecute(Void voidR) {
            SelectEffectActivity.this.progressDialog.dismiss();
            SelectEffectActivity.this.progressDialog.setProgress(0);
            SelectEffectActivity.this.assetExistsChecked = true;
            SelectEffectActivity.this.manipulateEffect(SelectEffectActivity.this.selectedIndex);
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public void onProgressUpdate(Integer... numArr) {
            SelectEffectActivity.this.progressDialog.setProgress(numArr[0].intValue());
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            SelectEffectActivity.this.progressDialog.setProgress(0);
            SelectEffectActivity.this.progressDialog.show();
        }
    }

    @SuppressLint({"StaticFieldLeak"})
    public class CopyAndDecryptFramesTask extends AsyncTask<Void, Void, Void> {
        public CopyAndDecryptFramesTask() {
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public Void doInBackground(Void... voidArr) {
            StorageUtils.copyFolderInInternalStorage(SelectEffectActivity.this,
                    new File(SelectEffectActivity.this.getSelectedEffect().getFolderPath(SelectEffectActivity.this.effectInternalFolderPath)),
                    new File(SelectEffectActivity.this.effectTempInternalFolderPath),
                    AssetManager.getInstance().encryptedSeed);
            return null;
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public void onPostExecute(Void voidR) {
            SelectEffectActivity.this.executeCommand(buildCreateEffectCommand());
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            super.onPreExecute();
            SelectEffectActivity.this.playingAnimation.stop();
            SelectEffectActivity.this.progressDialog.setProgress(0);
            SelectEffectActivity.this.progressDialog.show();
            SelectEffectActivity.this.getWindow().addFlags(128);
        }
    }

    class SaveEffect implements OnClickListener {
        SaveEffect() {
        }

        public void onClick(View view) {
            new CopyAndDecryptFramesTask().execute(new Void[0]);
        }
    }

    class ShowResult implements Runnable {
        ShowResult() {
        }

        public void run() {
            Intent intent = new Intent(SelectEffectActivity.this, ShowResultActivity.class);
            intent.putExtra(SelectEffectActivity.OUTPUT_FILE_PATH_KEY, SelectEffectActivity.this.outputFilePath);
            SelectEffectActivity.this.startActivity(intent);
        }
    }

    /* access modifiers changed from: private */
    public String buildCreateEffectCommand() {
        this.outputFilePath = generateOutputFilePath();
        return FFMPEGCommandUtils.buildImageSequenceOverlayCommand(
                this.selectedPhotoPath,
                this.effectTempInternalFolderPath,
                getSelectedEffect().getFrameNamePattern(),
                getSelectedEffect().getFps(),
                this.outputFilePath);
    }

    private String generateOutputFilePath() {
        File externalStoragePublicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        StringBuilder sb = new StringBuilder();
        sb.append(DevConstants.OUTPUT_FILE_NAME_PREFIX);
        sb.append(new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()));
        sb.append(DevConstants.MP4_EXTENSION);
        return new File(externalStoragePublicDirectory, sb.toString()).getPath();
    }

    /* access modifiers changed from: private */
    public Effect getSelectedEffect() {
        return (Effect) Effect.LIST.get(this.selectedIndex);
    }

    private void initializePreviewEffectLayout() {
        Options options = new Options();
        options.inPreferredConfig = Config.ARGB_8888;
        Bitmap decodeFile = BitmapFactory.decodeFile(this.selectedPhotoPath, options);
        this.ivFootage = (ImageView) findViewById(R.id.ivFootage);
        this.ivFootage.setImageBitmap(decodeFile);
        this.ivOverlay = (ImageView) findViewById(R.id.ivOverlay);
    }

    private void initializeSelectionLayout() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.effectLayout);
        LayoutInflater layoutInflater = getLayoutInflater();
        ArrayList arrayList = new ArrayList(Effect.LIST.size());
        for (int i = 0; i < Effect.LIST.size(); i++) {
            arrayList.add(AssetManager.getInstance().loadBitmapFromAsset(((Effect) Effect.LIST.get(i)).getIconPath(), new Options()));
        }
        final ArrayList arrayList2 = new ArrayList(arrayList.size());
        for (final int i2 = 0; i2 < arrayList.size(); i2++) {
            View inflate = layoutInflater.inflate(R.layout.effect_item, linearLayout, false);
            final ImageView imageView = (ImageView) inflate.findViewById(R.id.effectItem);
            imageView.setImageBitmap((Bitmap) arrayList.get(i2));
            if (i2 == this.selectedIndex) {
                imageView.setSelected(true);
            }
            imageView.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    for (ImageView selected : arrayList2) {
                        selected.setSelected(false);
                    }
                    imageView.setSelected(true);
                    SelectEffectActivity.this.manipulateEffect(i2);
                }
            });
            arrayList2.add(imageView);
            linearLayout.addView(inflate.findViewById(R.id.frameItemLayout));
        }
        new CheckEffectAssetTask().execute(new Void[0]);
    }

    /* access modifiers changed from: private */
    public void loadBannerAd(boolean z, boolean z2) {

    }

    /* access modifiers changed from: private */
    public void manipulateEffect(int index) {
        if (this.assetExistsChecked) {
            if (this.playingAnimation != null) {
                this.playingAnimation.stop();
            }
            this.selectedIndex = index;
            Effect effect = (Effect) Effect.LIST.get(this.selectedIndex);
            this.playingAnimation = AnimationsContainer.getInstance().createFrameByFrameAnimation(this.ivOverlay, effect.getFramePaths(this.effectInternalFolderPath), effect.getFps());
            this.playingAnimation.start();
            this.effectTempInternalFolderPath = getDir(EFFECT_TEMP_FOLDER_NAME, 0).getPath();
            String str = LOG_TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("Folder to get effect image frames: ");
            sb.append(this.effectTempInternalFolderPath);
            Log.i(str, sb.toString());
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo16450a(Bundle bundle) {
        setContentView(R.layout.activity_select_effect);
        mo16488b();
        this.selectedPhotoPath = getIntent().getStringExtra(SelectPhotoActivity.PROCESSED_IMAGE_PATH_KEY);
        mo16422a();
        registerFFmpegExecuteListener(this);
        this.tvBack = (TextView) findViewById(R.id.tvBack);
        this.tvBack.setOnClickListener(new BackPress());
        this.tvBack.setTypeface(AssetManager.getInstance().fontIcon);
        this.btnSave = (TextView) findViewById(R.id.tvSave);
        this.btnSave.setOnClickListener(new SaveEffect());
        this.btnSave.setTypeface(AssetManager.getInstance().fontIcon);
        if (bundle != null) {
            this.selectedIndex = bundle.getInt(SELECTED_INDEX, 0);
        }
        this.effectInternalFolderPath = getDir(EFFECT_FOLDER_NAME, 0).getPath();
        initializePreviewEffectLayout();
        initializeSelectionLayout();
        this.adView = (RelativeLayout) findViewById(R.id.adView);
        if (AdsConfig.isConnected()) {
            loadBannerAd(true, false);
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: b */
    public void mo16488b() {
        this.progressDialog = new CustomProgressDialog(this, true);
        this.progressDialog.setCancelable(false);
        this.progressDialog.setCanceledOnTouchOutside(false);
        this.progressDialog.setMax(100);
        this.progressDialog.setProgress(0);
    }

    public void onFFmpegFailure() {
        this.executeCommandSuccess = false;
    }

    public void onFFmpegFinish() {
        String str;
        this.progressDialog.dismiss();
        getWindow().clearFlags(128);
        StorageUtils.deleteFileOrDirectory(new File(this.effectTempInternalFolderPath));
        if (this.executeCommandSuccess) {
            StorageUtils.refreshStorage(this, this.outputFilePath);
            mo16453c("Video saved into picture folder!");
            new ShowResult().run();
            str = "Video saved into picture folder!";
        } else {
            str = "Create video effect FAILED!";
        }
        mo16453c(str);
    }

    public void onFFmpegProgress(String str) {
        if (str.contains("frame= ")) {
            this.progressDialog.setProgress((int) ((((float) Integer.valueOf(str.substring(6, 11).trim()).intValue()) / ((float) getSelectedEffect().getFrameNum())) * 100.0f));
        }
    }

    public void onFFmpegStart() {
    }

    public void onFFmpegSuccess() {
        this.progressDialog.setProgress(100);
        this.executeCommandSuccess = true;
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        Log.i(LOG_TAG, "onPause()");
        if (this.effectTempInternalFolderPath != null) {
            StorageUtils.deleteFileOrDirectory(new File(this.effectTempInternalFolderPath));
        }
        super.onPause();
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        Log.i(LOG_TAG, "onResume()");
        manipulateEffect(this.selectedIndex);
    }

    /* access modifiers changed from: protected */
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt(SELECTED_INDEX, this.selectedIndex);
    }
}
