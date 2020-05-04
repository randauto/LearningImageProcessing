package practice.com.learningimageprocessing.videoeffect.lovevideo.heartvideo.loveheart;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.p000v4.content.ContextCompat;
import android.support.p003v7.app.AlertDialog.Builder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.ContentViewEvent;
import com.facebook.ads.AdError;
import com.facebook.ads.C0482Ad;
import com.facebook.ads.NativeAdListener;
import com.google.android.gms.ads.AdListener;
import com.kobakei.ratethisapp.RateThisApp;
import com.kobakei.ratethisapp.RateThisApp.Config;
import com.photo.effect.editor.common.constants.DevConstants;
import com.photo.effect.editor.common.constants.ScreenConstants;
import com.photo.effect.editor.common.dto.TaskDto;
import com.photo.effect.editor.common.utils.PhotoUtils;
import com.photo.effect.editor.common.utils.PreferencesUtils;
import com.photo.effect.editor.common.utils.StorageUtils;
import com.photo.effect.editor.videomaker.activities.SelectEffectActivity;
import com.photo.effect.editor.videomaker.activities.SelectPhotoActivity;
import com.photo.effect.editor.videomaker.application.AssetManager;
import com.squareup.picasso.Picasso;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCrop.Options;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import love.heart.gif.autoanimation.videomaker.R;
import p045io.fabric.sdk.android.Fabric;
import p048pl.aprilapps.easyphotopicker.DefaultCallback;
import p048pl.aprilapps.easyphotopicker.EasyImage;
import p048pl.aprilapps.easyphotopicker.EasyImage.ImageSource;
import videoeffect.lovevideo.heartvideo.loveheart.Ads.AdsConfig;

public class MainActivity extends BaseActivity {
    public static String CROPPED_IMAGE_NAME = "CropImage.jpg";

    /* renamed from: a */
    ImageView f5883a;

    /* renamed from: b */
    ExpandableHeightGridView f5884b;

    /* renamed from: c */
    ScrollView f5885c;

    /* renamed from: d */
    ArrayList<String> f5886d = new ArrayList<>();

    /* renamed from: e */
    ArrayList<String> f5887e = new ArrayList<>();

    /* renamed from: f */
    ArrayList<String> f5888f = new ArrayList<>();

    /* renamed from: g */
    protected ProgressDialog f5889g;
    private RelativeLayout mNativeView;
    /* access modifiers changed from: private */
    public String photoPath;

    class C02631 implements OnClickListener {
        C02631() {
        }

        public void onClick(View view) {
            try {
                if (ContextCompat.checkSelfPermission(MainActivity.this, "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
                    MainActivity.this.mo18750a("android.permission.WRITE_EXTERNAL_STORAGE", MainActivity.this.getString(R.string.permission_write_storage_rationale), 102);
                } else {
                    EasyImage.openGallery((Activity) MainActivity.this, 0);
                }
            } catch (Exception e) {
                Log.i("Photos to Collage", e.getMessage());
            }
        }
    }

    class C04494 extends DefaultCallback {
        C04494() {
        }

        public void onCanceled(ImageSource imageSource, int i) {
            if (imageSource == ImageSource.CAMERA) {
                File lastlyTakenButCanceledPhoto = EasyImage.lastlyTakenButCanceledPhoto(MainActivity.this);
                if (lastlyTakenButCanceledPhoto != null) {
                    lastlyTakenButCanceledPhoto.delete();
                }
            }
        }

        public void onImagePickerError(Exception exc, ImageSource imageSource, int i) {
            exc.printStackTrace();
        }

        public void onImagesPicked(List<File> list, ImageSource imageSource, int i) {
            MainActivity.this.onPhotosReturned(list);
        }
    }

    class LaterButton implements DialogInterface.OnClickListener {
        LaterButton() {
        }

        public void onClick(DialogInterface dialogInterface, int i) {
        }
    }

    private class MyCustomAdapter extends BaseAdapter {
        private LayoutInflater mInflater;

        public MyCustomAdapter() {
            this.mInflater = (LayoutInflater) MainActivity.this.getSystemService("layout_inflater");
        }

        public int getCount() {
            return MainActivity.this.f5886d.size();
        }

        public String getItem(int i) {
            return (String) MainActivity.this.f5886d.get(i);
        }

        public long getItemId(int i) {
            return (long) i;
        }

        public int getItemViewType(int i) {
            return i;
        }

        public View getView(final int i, View view, ViewGroup viewGroup) {
            View view2;
            ViewHolder viewHolder;
            new ViewHolder();
            if (view == null) {
                viewHolder = new ViewHolder();
                view2 = this.mInflater.inflate(R.layout.list_item_type, null);
                view2.setTag(viewHolder);
            } else {
                view2 = view;
                viewHolder = (ViewHolder) view.getTag();
            }
            viewHolder.textView = (TextView) view2.findViewById(R.id.mtext);
            viewHolder.imgView = (ImageView) view2.findViewById(R.id.img);
            view2.setTag(viewHolder);
            viewHolder.textView.setText((CharSequence) MainActivity.this.f5886d.get(i));
            Picasso.with(MainActivity.this).load((String) MainActivity.this.f5887e.get(i)).error((int) R.drawable.ic_launcher).placeholder((int) R.drawable.ic_launcher).into(viewHolder.imgView);
            view2.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    Answers.getInstance().logContentView((ContentViewEvent) new ContentViewEvent().putCustomAttribute("Click", (String) MainActivity.this.f5886d.get(i)));
                    MainActivity.goToMarket(MainActivity.this, (String) MainActivity.this.f5888f.get(i));
                }
            });
            return view2;
        }
    }

    @SuppressLint({"StaticFieldLeak"})
    class ProcessSelectedPhotoTask extends AsyncTask<Void, Integer, TaskDto> {
        ProcessSelectedPhotoTask() {
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public TaskDto doInBackground(Void... voidArr) {
            TaskDto taskDto = new TaskDto();
            try {
                MainActivity.this.photoPath = StorageUtils.writeImageToInternalStorage(MainActivity.this, PhotoUtils.scaleBitmap(AssetManager.getInstance().userSelectedBitmap, 480, 480), DevConstants.TEMPORARY_FOLDER_NAME, "tempPic.jpg");
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
            MainActivity.this.f5889g.dismiss();
            if (taskDto.hasError() || MainActivity.this.photoPath == null) {
                MainActivity.this.showToast("ERROR: Cannot manipulate the selected photo!");
                return;
            }
            Intent intent = new Intent(MainActivity.this, SelectEffectActivity.class);
            intent.putExtra(SelectPhotoActivity.PROCESSED_IMAGE_PATH_KEY, MainActivity.this.photoPath);
            MainActivity.this.startActivity(intent);
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public void onProgressUpdate(Integer... numArr) {
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            MainActivity.this.f5889g.show();
            super.onPreExecute();
        }
    }

    class RateNow implements DialogInterface.OnClickListener {
        RateNow() {
        }

        public void onClick(DialogInterface dialogInterface, int i) {
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setData(Uri.parse(MainActivity.this.getString(R.string.app_link_on_google_play, new Object[]{MainActivity.this.getPackageName()})));
            MainActivity.this.startActivity(intent);
            PreferencesUtils.writeBooleanValue(MainActivity.this.getApplicationContext(), PreferencesUtils.SHARED_PREFS_RATING_SUCCESS, true);
        }
    }

    public static class ViewHolder {
        public ImageView imgView;
        public TextView textView;
    }

    private UCrop advancedConfig(@NonNull UCrop uCrop) {
        Options options = new Options();
        options.setCompressionFormat(CompressFormat.JPEG);
        return uCrop.withOptions(options);
    }

    private UCrop basisConfig(@NonNull UCrop uCrop) {
        return uCrop.withAspectRatio(1.0f, 1.0f).withMaxResultSize(ScreenConstants.SIZE_WIDTH_XLARGE, ScreenConstants.SIZE_WIDTH_XLARGE);
    }

    private Bitmap getBitmapFromPath(String str) {
        File file = new File(str);
        if (file.exists()) {
            return BitmapFactory.decodeFile(file.getAbsolutePath());
        }
        return null;
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(7:2|3|4|5|8|9|10) */
    /* JADX WARNING: Code restructure failed: missing block: B:16:?, code lost:
        r3 = new java.lang.StringBuilder();
        r3.append("market://details?id=");
        r3.append(r6);
        r1 = new android.content.Intent("android.intent.action.VIEW", android.net.Uri.parse(r3.toString()));
        r1.setFlags(268435456);
        r5.startActivity(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0094, code lost:
        return;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:15:0x0072 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:8:0x002c */
    @SuppressLint({"WrongConstant"})
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void goToMarket(Activity r5, String r6) {
        /*
            r0 = 268435456(0x10000000, float:2.5243549E-29)
            if (r6 != 0) goto L_0x004f
            java.lang.String r1 = r5.getPackageName()     // Catch:{ Exception -> 0x002b }
            android.content.Intent r6 = new android.content.Intent     // Catch:{ Exception -> 0x002c }
            java.lang.String r2 = "android.intent.action.VIEW"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x002c }
            r3.<init>()     // Catch:{ Exception -> 0x002c }
            java.lang.String r4 = "http://play.google.com/store/apps/details?id="
            r3.append(r4)     // Catch:{ Exception -> 0x002c }
            r3.append(r1)     // Catch:{ Exception -> 0x002c }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x002c }
            android.net.Uri r3 = android.net.Uri.parse(r3)     // Catch:{ Exception -> 0x002c }
            r6.<init>(r2, r3)     // Catch:{ Exception -> 0x002c }
            r6.setFlags(r0)     // Catch:{ Exception -> 0x002c }
            r5.startActivity(r6)     // Catch:{ Exception -> 0x002c }
            goto L_0x0071
        L_0x002b:
            r1 = r6
        L_0x002c:
            android.content.Intent r6 = new android.content.Intent     // Catch:{ Exception -> 0x004e }
            java.lang.String r2 = "android.intent.action.VIEW"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x004e }
            r3.<init>()     // Catch:{ Exception -> 0x004e }
            java.lang.String r4 = "market://details?id="
            r3.append(r4)     // Catch:{ Exception -> 0x004e }
            r3.append(r1)     // Catch:{ Exception -> 0x004e }
            java.lang.String r1 = r3.toString()     // Catch:{ Exception -> 0x004e }
            android.net.Uri r1 = android.net.Uri.parse(r1)     // Catch:{ Exception -> 0x004e }
            r6.<init>(r2, r1)     // Catch:{ Exception -> 0x004e }
            r6.setFlags(r0)     // Catch:{ Exception -> 0x004e }
            r5.startActivity(r6)     // Catch:{ Exception -> 0x004e }
        L_0x004e:
            return
        L_0x004f:
            android.content.Intent r1 = new android.content.Intent     // Catch:{ Exception -> 0x0072 }
            java.lang.String r2 = "android.intent.action.VIEW"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0072 }
            r3.<init>()     // Catch:{ Exception -> 0x0072 }
            java.lang.String r4 = "http://play.google.com/store/apps/details?id="
            r3.append(r4)     // Catch:{ Exception -> 0x0072 }
            r3.append(r6)     // Catch:{ Exception -> 0x0072 }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x0072 }
            android.net.Uri r3 = android.net.Uri.parse(r3)     // Catch:{ Exception -> 0x0072 }
            r1.<init>(r2, r3)     // Catch:{ Exception -> 0x0072 }
            r1.setFlags(r0)     // Catch:{ Exception -> 0x0072 }
            r5.startActivity(r1)     // Catch:{ Exception -> 0x0072 }
        L_0x0071:
            return
        L_0x0072:
            android.content.Intent r1 = new android.content.Intent     // Catch:{ Exception -> 0x0094 }
            java.lang.String r2 = "android.intent.action.VIEW"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0094 }
            r3.<init>()     // Catch:{ Exception -> 0x0094 }
            java.lang.String r4 = "market://details?id="
            r3.append(r4)     // Catch:{ Exception -> 0x0094 }
            r3.append(r6)     // Catch:{ Exception -> 0x0094 }
            java.lang.String r6 = r3.toString()     // Catch:{ Exception -> 0x0094 }
            android.net.Uri r6 = android.net.Uri.parse(r6)     // Catch:{ Exception -> 0x0094 }
            r1.<init>(r2, r6)     // Catch:{ Exception -> 0x0094 }
            r1.setFlags(r0)     // Catch:{ Exception -> 0x0094 }
            r5.startActivity(r1)     // Catch:{ Exception -> 0x0094 }
        L_0x0094:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: videoeffect.lovevideo.heartvideo.loveheart.MainActivity.goToMarket(android.app.Activity, java.lang.String):void");
    }

    private void handleCropError(@NonNull Intent intent) {
        Log.i("Photos to Collage", UCrop.getError(intent).getMessage());
    }

    private void handleCropResult(@NonNull Intent intent) {
        Uri output = UCrop.getOutput(intent);
        if (output != null) {
            AssetManager.getInstance().userSelectedBitmap = getBitmapFromPath(output.getPath());
            new ProcessSelectedPhotoTask().execute(new Void[0]);
        }
    }

    /* access modifiers changed from: private */
    public void onPhotosReturned(List<File> list) {
        advancedConfig(basisConfig(UCrop.m5110of(Uri.fromFile((File) list.get(0)), Uri.fromFile(new File(getCacheDir(), CROPPED_IMAGE_NAME))))).start(this);
    }

    /* access modifiers changed from: private */
    public void showSequenceAd(boolean z, boolean z2) {
        try {
            GradientDrawable gradientDrawable = new GradientDrawable();
            gradientDrawable.setColor(-1);
            gradientDrawable.setStroke(2, -8875876);
            this.mNativeView.setBackground(gradientDrawable);
            if (z) {
                this.f5883a.setVisibility(8);
                AdsConfig.setFbNative(this, this.mNativeView, new NativeAdListener() {
                    public void onAdClicked(C0482Ad ad) {
                    }

                    public void onAdLoaded(C0482Ad ad) {
                    }

                    public void onError(C0482Ad ad, AdError adError) {
                        MainActivity.this.showSequenceAd(false, true);
                    }

                    public void onLoggingImpression(C0482Ad ad) {
                    }

                    public void onMediaDownloaded(C0482Ad ad) {
                    }
                });
            } else if (z2) {
                this.f5883a.setVisibility(8);
                this.mNativeView.setBackground(null);
                this.mNativeView.addView(AdsConfig.setAdmobBanner_Medium_Rectangle(this, new AdListener() {
                    public void onAdFailedToLoad(int i) {
                        MainActivity.this.f5883a.setVisibility(0);
                    }
                }));
            }
        } catch (Exception unused) {
            this.mNativeView.setVisibility(8);
        }
    }

    /* access modifiers changed from: private */
    public void showToast(String str) {
        Toast.makeText(this, str, 1).show();
    }

    public void addData() {
        this.f5886d.add("Nature Effect Video Maker");
        this.f5887e.add("https://lh3.googleusercontent.com/Nm0CsPj3JWBGdzMxZYm5Mi_y6Uq8sVCjEpMJMcNifml4WxMwgX0cLgbDsU9XfyMRkZ4=s180-rw");
        this.f5888f.add("natureeffect.videomaker.naturephotoeffects");
        this.f5886d.add("Heart Photo Effect Video Maker - video Animation");
        this.f5887e.add("https://lh3.googleusercontent.com/hxePe9_RJ3ikfpYpT_b0D7S0JUsGEoOAfdA0k4aYMv_ID2Adlu7i5MWyi-l5ZaqdbslJ=s180-rw");
        this.f5888f.add("hearteffect.lovevideo.heartvideo.videoanimation");
        this.f5886d.add("Love Heart Effect Video Maker - GIF, Animation");
        this.f5887e.add("https://lh3.googleusercontent.com/AJ7vMyQERHitjred16GppEYyiX9zGFZcPvstTjvLOJbrchomD3tc8OVP3zcufMZQF2E=s180-rw");
        this.f5888f.add("videoeffect.lovevideo.heartvideo.loveheart");
        this.f5886d.add("Birthday Photo Effect Video Maker");
        this.f5887e.add("https://lh3.googleusercontent.com/L6vI7Xk6blKOC62q8LRFhy6KCfA_RxXgbMmPIFx4b7IEPyQemca1mMRrJ23a3VZpQK8=s180-rw");
        this.f5888f.add("birthdayeffect.videoeffect.birthdayvideo");
        this.f5886d.add("Valentine Day Video Maker");
        this.f5887e.add("https://lh3.googleusercontent.com/9NfJqmJtGiHuoObibrsuF6esUFkxzDrmCzhvjr3skRyqnEexa1_U8RWHIV7u2jNnW1M=s180-rw");
        this.f5888f.add("valentineeffect.videoeffect.lovevideo.valentinevideomaker");
        this.f5886d.add("Anniversary Photo Effect Video Maker");
        this.f5887e.add("https://lh3.googleusercontent.com/wBMTis-hJuYXsZ-nu4i-bWyqO0mshcZMgyepZVm-hU5YjaD7C3jTKXvhYBH9q_Zxxtc=s180-rw");
        this.f5888f.add("aniversaryeffect.lovevideo.videoanimation.videoeffect");
        this.f5886d.add("All WAStickerPack - Sticker For WhatsApp");
        this.f5887e.add("https://lh3.googleusercontent.com/9QnGv8vfqgnB3F1-nZZdKdaCVGMsMlqM-0SS63OeXkxlSqgmTv8lNox7neXOq9_fxkA=s180-rw");
        this.f5888f.add("wastickerapps.store.stickerapp");
        this.f5886d.add("Happy New Year & Christmas-Love Effect Video Maker");
        this.f5887e.add("https://lh3.googleusercontent.com/mmG_vbas51wv5wpfyFXxpBWjRGvRACdjJu2EiNbRux1r-P8GULN1m4Vjj31AY6GRkEA=s180-rw");
        this.f5888f.add("newyear.year2019.happynewyear.newyearparty");
        this.f5886d.add("DSLR Camera - Shape Blur Camera & Auto Blur Camera");
        this.f5887e.add("https://lh3.googleusercontent.com/ySZDMnxnGA3NzUdCjMih7hFnXF5dSvFx3xlYjXxxiQ39H6SiFMsmej3-OgdSj-GXXtI=s180-rw");
        this.f5888f.add("com.lvim.blureditor");
        this.f5886d.add("Face Live Camera - Photo Effects, Filters");
        this.f5887e.add("https://lh3.googleusercontent.com/P3FMAPaudF6i3L2MnK84Z7JGTWPHFvzptB65TOeu8TlblhegAjKgmuZpRWlCpjxZzkg=s180-rw");
        this.f5888f.add("facecamera.livecamera.photoeffect.photofilter");
    }

    public int getAppIcon() {
        return R.drawable.ic_launcher;
    }

    public String getAppName() {
        return getString(R.string.app_name);
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 == -1) {
            if (i == 69) {
                handleCropResult(intent);
            } else {
                EasyImage.handleActivityResult(i, i2, intent, this, new C04494());
            }
        }
        if (i2 == 96) {
            handleCropError(intent);
        }
    }

    public void onBackPressed() {
        new Builder(this).setIcon(17301543).setTitle((CharSequence) "Closing Activity").setMessage((CharSequence) "Are you sure you want to close this activity?").setPositiveButton((CharSequence) "Yes", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                MainActivity.this.finish();
            }
        }).setNegativeButton((CharSequence) "No", (DialogInterface.OnClickListener) null).show();
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_main);
        showRatingReminder(AssetManager.getInstance().numTimesActivityOpened);
        this.f5889g = new ProgressDialog(this);
        this.f5889g.setTitle(getString(R.string.title_please_wait));
        this.f5889g.setProgressStyle(0);
        Fabric.with(this, new Crashlytics());
        Answers.getInstance().logContentView(new ContentViewEvent());
        this.f5884b = (ExpandableHeightGridView) findViewById(R.id.adGrid);
        this.f5885c = (ScrollView) findViewById(R.id.mainScroll);
        addData();
        this.f5884b.setExpanded(true);
        this.f5884b.setAdapter(new MyCustomAdapter());
        new Handler().postDelayed(new Runnable() {
            public void run() {
                MainActivity.this.f5885c.scrollTo(0, 0);
            }
        }, 500);
        RateThisApp.init(new Config(5, 2));
        RateThisApp.onStart(this);
        RateThisApp.showRateDialogIfNeeded(this);
        findViewById(R.id.ivgallery).setOnClickListener(new C02631());
        findViewById(R.id.ivrate).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MainActivity.goToMarket(MainActivity.this, MainActivity.this.getPackageName());
            }
        });
        findViewById(R.id.ivshare).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MainActivity.this.onShareApp(MainActivity.this);
            }
        });
        findViewById(R.id.ivmore).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MainActivity.this.openPlayStore(true, "Idroid App");
            }
        });
        this.f5883a = (ImageView) findViewById(R.id.iv_img);
        this.mNativeView = (RelativeLayout) findViewById(R.id.nativeView);
        if (AdsConfig.isConnected()) {
            showSequenceAd(true, false);
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        EasyImage.clearConfiguration(this);
        super.onDestroy();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:?, code lost:
        p048pl.aprilapps.easyphotopicker.EasyImage.openGallery((android.app.Activity) r1, 0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0015, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0016, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0017, code lost:
        r2.printStackTrace();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x001a, code lost:
        return;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x0012 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onRequestPermissionsResult(int r2, String[] r3, int[] r4) {
        /*
            r1 = this;
            r0 = 102(0x66, float:1.43E-43)
            if (r2 == r0) goto L_0x0008
            super.onRequestPermissionsResult(r2, r3, r4)
            return
        L_0x0008:
            r2 = 0
            r3 = r4[r2]     // Catch:{ Exception -> 0x0012 }
            if (r3 == 0) goto L_0x000e
            return
        L_0x000e:
            p048pl.aprilapps.easyphotopicker.EasyImage.openGallery(r1, r2)     // Catch:{ Exception -> 0x0012 }
            return
        L_0x0012:
            p048pl.aprilapps.easyphotopicker.EasyImage.openGallery(r1, r2)     // Catch:{ Exception -> 0x0016 }
            return
        L_0x0016:
            r2 = move-exception
            r2.printStackTrace()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: videoeffect.lovevideo.heartvideo.loveheart.MainActivity.onRequestPermissionsResult(int, java.lang.String[], int[]):void");
    }

    @SuppressLint({"WrongConstant"})
    public void onShareApp(Activity activity) {
        if (activity != null) {
            try {
                Intent intent = new Intent("android.intent.action.SEND");
                intent.setFlags(268435456);
                intent.setType("text/plain");
                StringBuilder sb = new StringBuilder();
                sb.append("Hi,Check out this awesome App ,available at Google Play store for FREE \nhttp://play.google.com/store/apps/details?id=");
                sb.append(activity.getPackageName());
                intent.putExtra("android.intent.extra.TEXT", sb.toString());
                activity.startActivityForResult(Intent.createChooser(intent, "Share via"), 200);
            } catch (Exception unused) {
            }
        }
    }

    public void openPlayStore(boolean z, String str) {
        Intent intent;
        Intent intent2;
        if (z) {
            StringBuilder sb = new StringBuilder();
            sb.append("market://search?q=pub:");
            sb.append(str);
            intent = new Intent("android.intent.action.VIEW", Uri.parse(sb.toString()));
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("market://details?id=");
            sb2.append(getPackageName());
            intent = new Intent("android.intent.action.VIEW", Uri.parse(sb2.toString()));
        }
        boolean z2 = false;
        Iterator it = getPackageManager().queryIntentActivities(intent, 0).iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            ResolveInfo resolveInfo = (ResolveInfo) it.next();
            if (resolveInfo.activityInfo.applicationInfo.packageName.equals("com.android.vending")) {
                ActivityInfo activityInfo = resolveInfo.activityInfo;
                ComponentName componentName = new ComponentName(activityInfo.applicationInfo.packageName, activityInfo.name);
                intent.addFlags(268435456);
                intent.addFlags(2097152);
                intent.addFlags(67108864);
                intent.setComponent(componentName);
                startActivity(intent);
                z2 = true;
                break;
            }
        }
        if (!z2) {
            if (z) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append("http://play.google.com/store/search?q=pub:");
                sb3.append(getPackageName());
                intent2 = new Intent("android.intent.action.VIEW", Uri.parse(sb3.toString()));
            } else {
                StringBuilder sb4 = new StringBuilder();
                sb4.append("https://play.google.com/store/apps/details?id=");
                sb4.append(getPackageName());
                intent2 = new Intent("android.intent.action.VIEW", Uri.parse(sb4.toString()));
            }
            startActivity(intent2);
        }
    }

    public void showRatingReminder(int i) {
        if (i % 3 == 0 && !PreferencesUtils.getBooleanValue(getApplicationContext(), PreferencesUtils.SHARED_PREFS_RATING_SUCCESS, false)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            String appName = getAppName();
            builder.setTitle(getString(R.string.rate_app_main_title));
            builder.setIcon(getAppIcon());
            builder.setMessage(getString(R.string.rate_app_sub_title, new Object[]{appName})).setPositiveButton(getString(R.string.rate_app_positive_answer), new RateNow()).setNegativeButton(getString(R.string.rate_app_negative_answer), new LaterButton()).setCancelable(false);
            builder.create().show();
        }
    }
}
