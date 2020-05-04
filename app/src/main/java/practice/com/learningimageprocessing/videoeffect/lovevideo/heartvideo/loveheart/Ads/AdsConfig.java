package practice.com.learningimageprocessing.videoeffect.lovevideo.heartvideo.loveheart.Ads;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.facebook.ads.AdChoicesView;
import com.facebook.ads.AdError;
import com.facebook.ads.AdIconView;
import com.facebook.ads.C0482Ad;
import com.facebook.ads.InterstitialAdListener;
import com.facebook.ads.MediaView;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdListener;
import com.facebook.ads.NativeBannerAd;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import java.util.ArrayList;
import java.util.List;
import love.heart.gif.autoanimation.videomaker.R;

@SuppressLint({"StaticFieldLeak"})
public class AdsConfig extends ConstantKey {
    private static AdsConfig adsConfig = null;
    public static int amqctst = 0;
    public static int fullqctAdCount = 1;
    public static String imagePath = null;
    public static boolean isUpdateAvailable = false;
    public static boolean isfirst = true;
    public static boolean isfirsttime = true;
    public static InterstitialAd mAdmobInterstitialAd;
    public static com.facebook.ads.InterstitialAd mFBInterstitialAd;
    public static RewardedVideoAd mRewardedVideoAd;
    public static int qctRandomCount;
    public static int qctRandomCount1;
    public static float versioncode;

    public static boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) AppController.getInstance().getApplicationContext().getSystemService("connectivity");
        if (connectivityManager == null) {
            return false;
        }
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isAvailable() && activeNetworkInfo.isConnected();
    }

    public static AdView setAdmobBanner(Context context, final AdListener adListener) {
        AdView adView = new AdView(context);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-4087317046605472/3853614795");
        adView.setAdListener(new AdListener() {
            public void onAdClicked() {
                adListener.onAdClicked();
            }

            public void onAdClosed() {
                adListener.onAdClosed();
            }

            public void onAdFailedToLoad(int i) {
                adListener.onAdFailedToLoad(i);
            }

            public void onAdImpression() {
                adListener.onAdImpression();
            }

            public void onAdLeftApplication() {
                adListener.onAdLeftApplication();
            }

            public void onAdLoaded() {
                adListener.onAdLoaded();
            }

            public void onAdOpened() {
                adListener.onAdOpened();
            }
        });
        adView.loadAd(new Builder().build());
        return adView;
    }

    public static AdView setAdmobBanner_Medium_Rectangle(Context context, final AdListener adListener) {
        AdView adView = new AdView(context);
        adView.setAdSize(AdSize.MEDIUM_RECTANGLE);
        adView.setAdUnitId("ca-app-pub-4087317046605472/3853614795");
        adView.setAdListener(new AdListener() {
            public void onAdClicked() {
                adListener.onAdClicked();
            }

            public void onAdClosed() {
                adListener.onAdClosed();
            }

            public void onAdFailedToLoad(int i) {
                adListener.onAdFailedToLoad(i);
            }

            public void onAdImpression() {
                adListener.onAdImpression();
            }

            public void onAdLeftApplication() {
                adListener.onAdLeftApplication();
            }

            public void onAdLoaded() {
                adListener.onAdLoaded();
            }

            public void onAdOpened() {
                adListener.onAdOpened();
            }
        });
        adView.loadAd(new Builder().build());
        return adView;
    }

    public static void setAdmobInterstitial(final AdListener adListener) {
        if (mAdmobInterstitialAd.isLoaded()) {
            mAdmobInterstitialAd.setAdListener(new AdListener() {
                public void onAdClosed() {
                    AdsConfig.mAdmobInterstitialAd.loadAd(new Builder().build());
                    adListener.onAdClosed();
                }

                public void onAdFailedToLoad(int i) {
                    AdsConfig.mAdmobInterstitialAd.loadAd(new Builder().build());
                    adListener.onAdFailedToLoad(i);
                }

                public void onAdLeftApplication() {
                    adListener.onAdLeftApplication();
                }

                public void onAdLoaded() {
                    adListener.onAdLoaded();
                }

                public void onAdOpened() {
                    adListener.onAdOpened();
                }
            });
            mAdmobInterstitialAd.show();
            return;
        }
        mAdmobInterstitialAd.loadAd(new Builder().build());
        adListener.onAdFailedToLoad(1);
    }

    public static void setFBInterstitial(final InterstitialAdListener interstitialAdListener) {
        mFBInterstitialAd.setAdListener(new InterstitialAdListener() {
            public void onAdClicked(C0482Ad ad) {
                interstitialAdListener.onAdClicked(ad);
            }

            public void onAdLoaded(C0482Ad ad) {
                interstitialAdListener.onAdLoaded(ad);
            }

            public void onError(C0482Ad ad, AdError adError) {
                interstitialAdListener.onError(ad, adError);
                AdsConfig.mFBInterstitialAd.loadAd();
            }

            public void onInterstitialDismissed(C0482Ad ad) {
                interstitialAdListener.onInterstitialDismissed(ad);
                AdsConfig.mFBInterstitialAd.loadAd();
            }

            public void onInterstitialDisplayed(C0482Ad ad) {
                interstitialAdListener.onInterstitialDisplayed(ad);
            }

            public void onLoggingImpression(C0482Ad ad) {
                interstitialAdListener.onLoggingImpression(ad);
            }
        });
        mFBInterstitialAd.show();
    }

    public static com.facebook.ads.AdView setFbBanner(Context context, final com.facebook.ads.AdListener adListener) {
        com.facebook.ads.AdView adView = new com.facebook.ads.AdView(context, "1111", com.facebook.ads.AdSize.BANNER_HEIGHT_50);
        adView.setAdListener(new com.facebook.ads.AdListener() {
            public void onAdClicked(C0482Ad ad) {
                adListener.onAdClicked(ad);
            }

            public void onAdLoaded(C0482Ad ad) {
                adListener.onAdLoaded(ad);
            }

            public void onError(C0482Ad ad, AdError adError) {
                adListener.onError(ad, adError);
            }

            public void onLoggingImpression(C0482Ad ad) {
                adListener.onLoggingImpression(ad);
            }
        });
        adView.loadAd();
        return adView;
    }

    public static void setFbNative(final Context context, final ViewGroup viewGroup, final NativeAdListener nativeAdListener) {
        final NativeAd nativeAd = new NativeAd(context, "199353067685362_199357457684923");
        nativeAd.setAdListener(new NativeAdListener() {
            public void onAdClicked(C0482Ad ad) {
            }

            public void onAdLoaded(C0482Ad ad) {
                try {
                    if (nativeAd != ad) {
                        nativeAdListener.onAdLoaded(ad);
                    }
                    nativeAd.unregisterView();
                    int i = 0;
                    View inflate = LayoutInflater.from(context).inflate(R.layout.native_ad_unit, viewGroup, false);
                    viewGroup.addView(inflate);
                    viewGroup.setVisibility(0);
                    ((LinearLayout) inflate.findViewById(R.id.ad_choices_container)).addView(new AdChoicesView(context, nativeAd, true), 0);
                    AdIconView adIconView = (AdIconView) inflate.findViewById(R.id.native_ad_icon);
                    TextView textView = (TextView) inflate.findViewById(R.id.native_ad_title);
                    TextView textView2 = (TextView) inflate.findViewById(R.id.native_ad_body);
                    MediaView mediaView = (MediaView) inflate.findViewById(R.id.native_ad_media);
                    TextView textView3 = (TextView) inflate.findViewById(R.id.native_ad_sponsored_label);
                    TextView textView4 = (TextView) inflate.findViewById(R.id.native_ad_social_context);
                    Button button = (Button) inflate.findViewById(R.id.native_ad_call_to_action);
                    textView4.setText(nativeAd.getAdSocialContext());
                    button.setText(nativeAd.getAdCallToAction());
                    if (!nativeAd.hasCallToAction()) {
                        i = 4;
                    }
                    button.setVisibility(i);
                    textView.setText(nativeAd.getAdvertiserName());
                    textView2.setText(nativeAd.getAdBodyText());
                    textView3.setText(nativeAd.getSponsoredTranslation());
                    ArrayList arrayList = new ArrayList();
                    arrayList.add(adIconView);
                    arrayList.add(mediaView);
                    arrayList.add(button);
                    nativeAd.registerViewForInteraction((View) viewGroup, mediaView, adIconView, (List<View>) arrayList);
                    nativeAd.destroy();
                    nativeAdListener.onAdLoaded(ad);
                } catch (Exception unused) {
                }
            }

            public void onError(C0482Ad ad, AdError adError) {
                try {
                    nativeAdListener.onError(ad, adError);
                } catch (Exception unused) {
                }
            }

            public void onLoggingImpression(C0482Ad ad) {
            }

            public void onMediaDownloaded(C0482Ad ad) {
            }
        });
        nativeAd.loadAd();
    }

    public static void setFbNativeBanner(final Context context, final ViewGroup viewGroup, final NativeAdListener nativeAdListener) {
        final NativeBannerAd nativeBannerAd = new NativeBannerAd(context, "199353067685362_199358321018170");
        nativeBannerAd.setAdListener(new NativeAdListener() {
            public void onAdClicked(C0482Ad ad) {
            }

            public void onAdLoaded(C0482Ad ad) {
                try {
                    if (nativeBannerAd != ad) {
                        nativeAdListener.onAdLoaded(ad);
                    }
                    nativeBannerAd.unregisterView();
                    int i = 0;
                    View inflate = LayoutInflater.from(context).inflate(R.layout.native_banner_ad_unit, viewGroup, false);
                    viewGroup.addView(inflate);
                    viewGroup.setVisibility(0);
                    ((RelativeLayout) inflate.findViewById(R.id.ad_choices_container)).addView(new AdChoicesView(context, nativeBannerAd, true), 0);
                    TextView textView = (TextView) inflate.findViewById(R.id.native_ad_title);
                    TextView textView2 = (TextView) inflate.findViewById(R.id.native_ad_social_context);
                    TextView textView3 = (TextView) inflate.findViewById(R.id.native_ad_sponsored_label);
                    AdIconView adIconView = (AdIconView) inflate.findViewById(R.id.native_icon_view);
                    Button button = (Button) inflate.findViewById(R.id.native_ad_call_to_action);
                    button.setText(nativeBannerAd.getAdCallToAction());
                    if (!nativeBannerAd.hasCallToAction()) {
                        i = 4;
                    }
                    button.setVisibility(i);
                    textView.setText(nativeBannerAd.getAdvertiserName());
                    textView2.setText(nativeBannerAd.getAdSocialContext());
                    textView3.setText(nativeBannerAd.getSponsoredTranslation());
                    ArrayList arrayList = new ArrayList();
                    arrayList.add(textView);
                    arrayList.add(button);
                    nativeBannerAd.registerViewForInteraction(inflate, adIconView, arrayList);
                    nativeBannerAd.destroy();
                    nativeAdListener.onAdLoaded(ad);
                } catch (Exception unused) {
                }
            }

            public void onError(C0482Ad ad, AdError adError) {
                try {
                    nativeAdListener.onError(ad, adError);
                } catch (Exception unused) {
                }
            }

            public void onLoggingImpression(C0482Ad ad) {
            }

            public void onMediaDownloaded(C0482Ad ad) {
            }
        });
        nativeBannerAd.loadAd();
    }

    public void setAdmobRewardedVideo(final RewardedVideoAdListener rewardedVideoAdListener) {
        if (mRewardedVideoAd.isLoaded()) {
            mRewardedVideoAd.show();
        } else {
            mRewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917", new Builder().build());
            rewardedVideoAdListener.onRewardedVideoAdFailedToLoad(1);
        }
        mRewardedVideoAd.setRewardedVideoAdListener(new RewardedVideoAdListener() {
            public void onRewarded(RewardItem rewardItem) {
                rewardedVideoAdListener.onRewarded(rewardItem);
            }

            public void onRewardedVideoAdClosed() {
                AdsConfig.mRewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917", new Builder().build());
                rewardedVideoAdListener.onRewardedVideoAdClosed();
            }

            public void onRewardedVideoAdFailedToLoad(int i) {
                rewardedVideoAdListener.onRewardedVideoAdFailedToLoad(i);
            }

            public void onRewardedVideoAdLeftApplication() {
                rewardedVideoAdListener.onRewardedVideoAdLeftApplication();
            }

            public void onRewardedVideoAdLoaded() {
                rewardedVideoAdListener.onRewardedVideoAdLoaded();
            }

            public void onRewardedVideoAdOpened() {
                rewardedVideoAdListener.onRewardedVideoAdOpened();
            }

            public void onRewardedVideoCompleted() {
                rewardedVideoAdListener.onRewardedVideoCompleted();
            }

            public void onRewardedVideoStarted() {
                rewardedVideoAdListener.onRewardedVideoStarted();
            }
        });
    }

    public com.facebook.ads.AdView setFbMediumRectBanner(Context context, final com.facebook.ads.AdListener adListener) {
        com.facebook.ads.AdView adView = new com.facebook.ads.AdView(context, "1111", com.facebook.ads.AdSize.RECTANGLE_HEIGHT_250);
        adView.setAdListener(new com.facebook.ads.AdListener() {
            public void onAdClicked(C0482Ad ad) {
                adListener.onAdClicked(ad);
            }

            public void onAdLoaded(C0482Ad ad) {
                adListener.onAdLoaded(ad);
            }

            public void onError(C0482Ad ad, AdError adError) {
                adListener.onError(ad, adError);
            }

            public void onLoggingImpression(C0482Ad ad) {
                adListener.onLoggingImpression(ad);
            }
        });
        adView.loadAd();
        return adView;
    }
}
