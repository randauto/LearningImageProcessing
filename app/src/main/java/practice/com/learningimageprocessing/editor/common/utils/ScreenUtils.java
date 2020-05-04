package practice.com.learningimageprocessing.editor.common.utils;

import android.content.Context;
import com.photo.effect.editor.common.constants.ScreenConstants;

public class ScreenUtils {
    private ScreenUtils() {
    }

    public static float convertDpToPixel(float f, Context context) {
        return (((float) context.getResources().getDisplayMetrics().densityDpi) / 160.0f) * f;
    }

    public static float convertPixelsToDp(float f, Context context) {
        return f / (((float) context.getResources().getDisplayMetrics().densityDpi) / 160.0f);
    }

    public static int getScreenDensity(Context context) {
        return context.getResources().getDisplayMetrics().densityDpi;
    }

    public static int[] getVirtualSizeBaseOnDpi(int i, boolean z) {
        return (i == 120 || i == 160) ? z ? new int[]{ScreenConstants.SIZE_WIDTH_LARGE, ScreenConstants.SIZE_HEIGHT_LARGE} : new int[]{ScreenConstants.SIZE_WIDTH_NORMAL, 480} : i != 240 ? i != 320 ? (i == 480 || i == 640) ? z ? new int[]{ScreenConstants.SIZE_WIDTH_XLARGE, ScreenConstants.SIZE_HEIGHT_XLARGE} : new int[]{ScreenConstants.SIZE_WIDTH_XXLARGE, ScreenConstants.SIZE_HEIGHT_XXLARGE} : new int[]{ScreenConstants.SIZE_WIDTH_XLARGE, ScreenConstants.SIZE_HEIGHT_XLARGE} : new int[]{ScreenConstants.SIZE_WIDTH_XLARGE, ScreenConstants.SIZE_HEIGHT_XLARGE} : new int[]{ScreenConstants.SIZE_WIDTH_LARGE, ScreenConstants.SIZE_HEIGHT_LARGE};
    }
}
