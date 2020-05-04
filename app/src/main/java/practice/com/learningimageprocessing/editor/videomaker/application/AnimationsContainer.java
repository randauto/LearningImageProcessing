package practice.com.learningimageprocessing.editor.videomaker.application;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build.VERSION;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import java.lang.ref.SoftReference;
import java.util.List;

public class AnimationsContainer {
    /* access modifiers changed from: private */
    public static final String LOG_TAG = "com.photo.effect.editor.videomaker.application.AnimationsContainer";
    private static AnimationsContainer mInstance;

    public class FramesSequenceAnimation {
        /* access modifiers changed from: private */
        public Bitmap mBitmap = null;
        /* access modifiers changed from: private */
        public Options mBitmapOptions;
        /* access modifiers changed from: private */
        public int mDelayMillis;
        private List<String> mFrames;
        /* access modifiers changed from: private */
        public Handler mHandler = new Handler();
        private int mIndex;
        /* access modifiers changed from: private */
        public boolean mIsRunning;
        /* access modifiers changed from: private */
        public OnAnimationStoppedListener mOnAnimationStoppedListener;
        /* access modifiers changed from: private */
        public boolean mShouldRun;
        /* access modifiers changed from: private */
        public SoftReference<ImageView> mSoftReferenceImageView;

        class C07351 implements Runnable {
            C07351() {
            }

            public void run() {
                Bitmap bitmap;
                ImageView imageView = (ImageView) FramesSequenceAnimation.this.mSoftReferenceImageView.get();
                if (!FramesSequenceAnimation.this.mShouldRun || imageView == null) {
                    FramesSequenceAnimation.this.mIsRunning = false;
                    if (FramesSequenceAnimation.this.mOnAnimationStoppedListener != null) {
                        FramesSequenceAnimation.this.mOnAnimationStoppedListener.animationStopped();
                    }
                    return;
                }
                FramesSequenceAnimation.this.mIsRunning = true;
                FramesSequenceAnimation.this.mHandler.postDelayed(this, (long) FramesSequenceAnimation.this.mDelayMillis);
                if (imageView.isShown()) {
                    String f = FramesSequenceAnimation.this.getNextFramePath();
                    if (FramesSequenceAnimation.this.mBitmap != null) {
                        try {
                            bitmap = AssetManager.getInstance().loadBitmapFromInternalStorage(f, FramesSequenceAnimation.this.mBitmapOptions);
                        } catch (Exception e) {
                            Log.e(AnimationsContainer.LOG_TAG, "Load bitmap error!", e);
                            bitmap = null;
                        }
                        if (bitmap != null) {
                            imageView.setImageBitmap(bitmap);
                            return;
                        }
                        imageView.setImageBitmap(AssetManager.getInstance().loadBitmapFromInternalStorage(f, FramesSequenceAnimation.this.mBitmapOptions));
                        FramesSequenceAnimation.this.mBitmap.recycle();
                        FramesSequenceAnimation.this.mBitmap = null;
                        return;
                    }
                    imageView.setImageBitmap(AssetManager.getInstance().loadBitmapFromInternalStorage(f, FramesSequenceAnimation.this.mBitmapOptions));
                }
            }
        }

        public FramesSequenceAnimation(ImageView imageView, List<String> list, int i) {
            this.mFrames = list;
            this.mIndex = -1;
            this.mSoftReferenceImageView = new SoftReference<>(imageView);
            this.mShouldRun = false;
            this.mIsRunning = false;
            this.mDelayMillis = 1000 / i;
            Bitmap loadBitmapFromInternalStorage = AssetManager.getInstance().loadBitmapFromInternalStorage((String) list.get(0), this.mBitmapOptions);
            String a = AnimationsContainer.LOG_TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("frame0 is null: ");
            sb.append(loadBitmapFromInternalStorage == null ? "True" : "False");
            Log.w(a, sb.toString());
            if (loadBitmapFromInternalStorage != null) {
                imageView.setImageBitmap(loadBitmapFromInternalStorage);
                if (VERSION.SDK_INT >= 11) {
                    Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                    if (bitmap != null) {
                        this.mBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
                        this.mBitmapOptions = new Options();
                        this.mBitmapOptions.inBitmap = this.mBitmap;
                        this.mBitmapOptions.inMutable = true;
                        this.mBitmapOptions.inSampleSize = 1;
                    }
                }
            }
        }

        /* access modifiers changed from: private */
        public String getNextFramePath() {
            this.mIndex++;
            if (this.mIndex >= this.mFrames.size()) {
                this.mIndex = 0;
            }
            return (String) this.mFrames.get(this.mIndex);
        }

        public synchronized void start() {
            if (this.mBitmap != null) {
                this.mShouldRun = true;
                if (!this.mIsRunning) {
                    this.mHandler.post(new C07351());
                }
            }
        }

        public synchronized void stop() {
            this.mShouldRun = false;
        }
    }

    public interface OnAnimationStoppedListener {
        void animationStopped();
    }

    private AnimationsContainer() {
    }

    public static AnimationsContainer getInstance() {
        if (mInstance == null) {
            mInstance = new AnimationsContainer();
        }
        return mInstance;
    }

    public FramesSequenceAnimation createFrameByFrameAnimation(ImageView imageView, List<String> list, int i) {
        return new FramesSequenceAnimation(imageView, list, i);
    }
}
