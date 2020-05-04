package practice.com.learningimageprocessing.editor.videomaker.application;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory.Options;
import android.graphics.Typeface;
import android.util.Log;

import com.photo.effect.editor.videomaker.enums.AssetTypeEnum;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import love.heart.gif.autoanimation.videomaker.R;
import practice.com.learningimageprocessing.R;
import practice.com.learningimageprocessing.editor.common.application.AbstractAssetManager;
import practice.com.learningimageprocessing.editor.common.constants.DevConstants;
import practice.com.learningimageprocessing.editor.common.utils.AssetUtils;
import practice.com.learningimageprocessing.editor.videomaker.enums.AssetEnum;
import practice.com.learningimageprocessing.editor.videomaker.enums.AssetTypeEnum;

public class AssetManager extends AbstractAssetManager {
    private static final AssetManager INSTANCE = new AssetManager();
    private static final String LOG_TAG = AssetManager.class.getSimpleName();
    private Map<String, List<Bitmap>> assetMap;
    public String encryptedSeed;
    public Typeface fontIcon;
    public Typeface fontMain;
    public boolean isDebugMode;
    public Bitmap userSelectedBitmap;

    private AssetManager() {
    }

    public static AssetManager getInstance() {
        return INSTANCE;
    }

    public List<Bitmap> getLoadedAssets(String str) {
        return (List) this.assetMap.get(str);
    }

    public void initialize(Context context) {
        super.initialize(context);
        this.isDebugMode = Boolean.valueOf(context.getString(R.string.debug_mode)).booleanValue();
        this.encryptedSeed = String.valueOf(Boolean.valueOf(context.getString(R.string.asset_encrypted)));
        this.assetMap = new HashMap();
    }

    public void loadAssetsInFolder(String str, Options options) {
        if (!this.assetMap.containsKey(str)) {
            Log.i(LOG_TAG, String.format("Loading all bitmaps of asset in folder '%s'", new Object[]{str}));
            this.assetMap.put(str, loadBitmapsInFolder(str, options));
        }
    }

    public Bitmap loadBitmapFromAsset(AssetEnum assetEnum, Options options) {
        return loadBitmapFromAsset(assetEnum.getPath(), options);
    }

    public Bitmap loadBitmapFromAsset(String str, Options options) {
        try {
            Bitmap bitmapFromAsset = AssetUtils.getBitmapFromAsset(this.f5122a, str, options, this.encryptedSeed);
            Log.i(LOG_TAG, String.format("Bitmap %s has been loaded.", new Object[]{str}));
            return bitmapFromAsset;
        } catch (Exception e) {
            String str2 = LOG_TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("Load bitmap ERROR with file: ");
            sb.append(str);
            Log.e(str2, sb.toString(), e);
            Log.w(LOG_TAG, "loadBitmapFromAsset() will return null");
            return null;
        }
    }

    public Bitmap loadBitmapFromInternalStorage(String str, Options options) {
        try {
            Bitmap bitmapFromInternalStorage = AssetUtils.getBitmapFromInternalStorage(str, options, this.encryptedSeed);
            Log.i(LOG_TAG, String.format("Bitmap %s has been loaded.", new Object[]{str}));
            return bitmapFromInternalStorage;
        } catch (Exception e) {
            String str2 = LOG_TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("Load bitmap ERROR with file: ");
            sb.append(str);
            Log.e(str2, sb.toString(), e);
            Log.w(LOG_TAG, "loadBitmapFromAsset() will return null");
            return null;
        }
    }

    public List<Bitmap> loadBitmapsFromAsset(List<AssetEnum> list, Options options) {
        ArrayList arrayList = new ArrayList(list.size());
        for (AssetEnum loadBitmapFromAsset : list) {
            arrayList.add(loadBitmapFromAsset(loadBitmapFromAsset, options));
        }
        return arrayList;
    }

    public List<Bitmap> loadBitmapsInFolder(String str, Options options) {
        ArrayList arrayList = new ArrayList();
        try {
            for (String concat : this.f5122a.getAssets().list(str.substring(0, str.lastIndexOf("/")))) {
                String concat2 = str.concat(concat);
                String substring = concat2.substring(concat2.length() - 4);
                if (substring.equalsIgnoreCase(DevConstants.PNG_EXTENSION) || substring.equalsIgnoreCase(".bmp") || substring.equalsIgnoreCase(DevConstants.JPG_EXTENSION)) {
                    arrayList.add(loadBitmapFromAsset(concat2, options));
                }
            }
            return arrayList;
        } catch (IOException e) {
            Log.e(LOG_TAG, "Load file exception", e);
            throw e;
        }
    }

    public void unloadAssets(AssetTypeEnum assetTypeEnum) {
        if (this.assetMap.containsKey(assetTypeEnum)) {
            ((List) this.assetMap.get(assetTypeEnum)).clear();
            this.assetMap.remove(assetTypeEnum);
            Log.i(LOG_TAG, String.format("Assets of type %s has been unloaded and removed from asset map.", new Object[]{assetTypeEnum}));
        }
    }
}
