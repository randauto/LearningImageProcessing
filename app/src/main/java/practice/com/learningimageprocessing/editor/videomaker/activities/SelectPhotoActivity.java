package practice.com.learningimageprocessing.editor.videomaker.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;
import com.photo.effect.editor.common.constants.DevConstants;
import com.photo.effect.editor.common.dto.TaskDto;
import com.photo.effect.editor.common.utils.PhotoUtils;
import com.photo.effect.editor.common.utils.StorageUtils;
import com.photo.effect.editor.videomaker.application.AssetManager;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImage.ActivityResult;
import com.theartofdev.edmodo.cropper.CropImageView.CropShape;
import com.theartofdev.edmodo.cropper.CropImageView.Guidelines;
import java.io.File;
import love.heart.gif.autoanimation.videomaker.R;

public class SelectPhotoActivity extends Activity {
    public static final String PROCESSED_IMAGE_PATH_KEY = "processedImagePath";

    /* renamed from: a */
    protected ProgressDialog f5139a;
    /* access modifiers changed from: private */
    public String photoPath;

    @SuppressLint({"StaticFieldLeak"})
    class ProcessSelectedPhotoTask extends AsyncTask<Void, Integer, TaskDto> {
        ProcessSelectedPhotoTask() {
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public TaskDto doInBackground(Void... voidArr) {
            TaskDto taskDto = new TaskDto();
            try {
                SelectPhotoActivity.this.photoPath = StorageUtils.writeImageToInternalStorage(SelectPhotoActivity.this, PhotoUtils.scaleBitmap(AssetManager.getInstance().userSelectedBitmap, 480, 480), DevConstants.TEMPORARY_FOLDER_NAME, "tempPic.jpg");
            } catch (Throwable th) {
                th.printStackTrace();
            }
            try {
                publishProgress(new Integer[]{Integer.valueOf(100)});
            } catch (Exception e) {
                taskDto.setError(e);
                publishProgress(new Integer[]{Integer.valueOf(1)});
            }
            return taskDto;
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public void onPostExecute(TaskDto taskDto) {
            SelectPhotoActivity.this.f5139a.dismiss();
            if (taskDto.hasError() || SelectPhotoActivity.this.photoPath == null) {
                SelectPhotoActivity.this.showToast("ERROR: Cannot manipulate the selected photo!");
                return;
            }
            Intent intent = new Intent(SelectPhotoActivity.this, SelectEffectActivity.class);
            intent.putExtra(SelectPhotoActivity.PROCESSED_IMAGE_PATH_KEY, SelectPhotoActivity.this.photoPath);
            SelectPhotoActivity.this.startActivity(intent);
            SelectPhotoActivity.this.finish();
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public void onProgressUpdate(Integer... numArr) {
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            SelectPhotoActivity.this.f5139a.show();
            super.onPreExecute();
        }
    }

    private void SelectPhoto() {
        CropImage.activity(null).setFixAspectRatio(true).setCropShape(CropShape.RECTANGLE).setGuidelines(Guidelines.ON).start(this);
    }

    private Bitmap getBitmapFromPath(String str) {
        File file = new File(str);
        if (file.exists()) {
            return BitmapFactory.decodeFile(file.getAbsolutePath());
        }
        return null;
    }

    /* access modifiers changed from: private */
    public void showToast(String str) {
        Toast.makeText(this, str, 1).show();
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 203) {
            CropImage.getActivityResult(intent);
            ActivityResult activityResult = CropImage.getActivityResult(intent);
            if (i2 == -1) {
                Uri uri = activityResult.getUri();
                AssetManager.getInstance().userSelectedBitmap = getBitmapFromPath(uri.getPath());
                new ProcessSelectedPhotoTask().execute(new Void[0]);
                return;
            }
            if (i2 == 204) {
                StringBuilder sb = new StringBuilder();
                sb.append("Cropping failed: ");
                sb.append(activityResult.getError());
                Toast.makeText(this, sb.toString(), 1).show();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.f5139a = new ProgressDialog(this);
        this.f5139a.setTitle(getString(R.string.title_please_wait));
        this.f5139a.setProgressStyle(0);
        SelectPhoto();
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
    }
}
