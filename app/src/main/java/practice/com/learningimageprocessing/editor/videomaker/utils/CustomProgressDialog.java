package practice.com.learningimageprocessing.editor.videomaker.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.facebook.ads.AdError;
import com.facebook.ads.C0482Ad;
import com.facebook.ads.NativeAdListener;
import com.google.android.gms.ads.AdListener;
import love.heart.gif.autoanimation.videomaker.R;
import videoeffect.lovevideo.heartvideo.loveheart.Ads.AdsConfig;

public class CustomProgressDialog extends ProgressDialog {
    private static final String PROGRESS_PERCENTAGE_FORMAT = "%s%%";

    /* renamed from: a */
    Context f5152a;
    private AnimationDrawable animation;
    /* access modifiers changed from: private */
    public RelativeLayout mNativeView;
    private boolean showProgressPercentage;
    private TextView tvProgress;

    public CustomProgressDialog(Context context, boolean z) {
        super(context);
        this.showProgressPercentage = z;
        this.f5152a = context;
    }

    /* access modifiers changed from: private */
    public void showSequenceAd(boolean z, boolean z2) {
        try {
            GradientDrawable gradientDrawable = new GradientDrawable();
            gradientDrawable.setColor(-1);
            gradientDrawable.setStroke(2, -8875876);
            this.mNativeView.setBackground(gradientDrawable);
            if (z) {
                AdsConfig.setFbNative(this.f5152a, this.mNativeView, new NativeAdListener() {
                    public void onAdClicked(C0482Ad ad) {
                    }

                    public void onAdLoaded(C0482Ad ad) {
                    }

                    public void onError(C0482Ad ad, AdError adError) {
                        CustomProgressDialog.this.showSequenceAd(false, true);
                    }

                    public void onLoggingImpression(C0482Ad ad) {
                    }

                    public void onMediaDownloaded(C0482Ad ad) {
                    }
                });
            } else if (z2) {
                this.mNativeView.setBackground(null);
                this.mNativeView.addView(AdsConfig.setAdmobBanner_Medium_Rectangle(this.f5152a, new AdListener() {
                    public void onAdFailedToLoad(int i) {
                        CustomProgressDialog.this.mNativeView.setVisibility(8);
                    }
                }));
            }
        } catch (Exception unused) {
            this.mNativeView.setVisibility(8);
        }
    }

    public void dismiss() {
        try {
            this.animation.stop();
            super.dismiss();
        } catch (Exception unused) {
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.custom_progress_dialog);
        getWindow().setBackgroundDrawable(new ColorDrawable(0));
        ImageView imageView = (ImageView) findViewById(R.id.animation);
        imageView.setBackgroundResource(R.drawable.progress_dialog_anim);
        this.animation = (AnimationDrawable) imageView.getBackground();
        this.tvProgress = (TextView) findViewById(R.id.tvProgress);
        this.mNativeView = (RelativeLayout) findViewById(R.id.nativeView);
        if (AdsConfig.isConnected()) {
            showSequenceAd(true, false);
        }
        if (this.showProgressPercentage) {
            this.tvProgress.setText(String.format(PROGRESS_PERCENTAGE_FORMAT, new Object[]{Integer.valueOf(0)}));
            return;
        }
        this.tvProgress.setVisibility(8);
    }

    public void setProgress(int i) {
        super.setProgress(i);
        if (this.showProgressPercentage && this.tvProgress != null) {
            this.tvProgress.setText(String.format(PROGRESS_PERCENTAGE_FORMAT, new Object[]{Integer.valueOf(i)}));
        }
    }

    public void show() {
        super.show();
        this.animation.start();
    }
}
