package practice.com.learningimageprocessing.editor.common.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import com.photo.effect.editor.common.constants.DevConstants;
import com.photo.effect.editor.common.enums.FlipDirection;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class PhotoUtils {
    private static final String LOG_TAG = "PhotoUtils";

    private PhotoUtils() {
    }

    public static Bitmap borderImage(Bitmap bitmap, int i) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.RGB_565);
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawColor(-1);
        float f = (float) i;
        canvas.drawBitmap(scaleImageByRatio(bitmap, ((float) (bitmap.getWidth() - (i * 2))) / ((float) bitmap.getWidth())), f, f, null);
        return createBitmap;
    }

    private static int exifToDegrees(int i) {
        if (i == 6) {
            return 90;
        }
        if (i == 3) {
            return 180;
        }
        return i == 8 ? 270 : 0;
    }

    public static Bitmap flip(Bitmap bitmap, FlipDirection flipDirection) {
        Matrix matrix = new Matrix();
        if (flipDirection == FlipDirection.VERTICAL) {
            matrix.preScale(1.0f, -1.0f);
        } else if (flipDirection != FlipDirection.HORIZONTAL) {
            return bitmap;
        } else {
            matrix.preScale(-1.0f, 1.0f);
        }
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public static Bitmap generateBitmap(Context context, View view, int i) {
        ImageView imageView = (ImageView) view.findViewById(i);
        Bitmap createBitmap = Bitmap.createBitmap(imageView.getWidth(), imageView.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Matrix matrix = new Matrix();
        matrix.setTranslate((float) (-((view.getWidth() - imageView.getWidth()) / 2)), (float) (-((view.getHeight() - imageView.getHeight()) / 2)));
        canvas.setMatrix(matrix);
        view.draw(canvas);
        return createBitmap;
    }

    public static int getImagePreviewSize(Context context) {
        int screenDensity = ScreenUtils.getScreenDensity(context);
        if (screenDensity == 640) {
            return 320;
        }
        if (screenDensity == 480) {
            return DevConstants.FRAME_PREVIEW_SIZE_XXHDPI;
        }
        int i = 120;
        if (screenDensity == 320) {
            i = DevConstants.FRAME_PREVIEW_SIZE_XHDPI;
        } else if (screenDensity != 240 && screenDensity == 160) {
            i = 80;
        }
        return i;
    }

    public static Bitmap loadImage(Context context, Uri uri) {
        InputStream openInputStream = context.getContentResolver().openInputStream(uri);
        if (openInputStream != null) {
            Bitmap rotateImageToOriginal = rotateImageToOriginal(context, BitmapFactory.decodeStream(openInputStream), uri);
            Log.i(LOG_TAG, String.format("Image loaded with size (width x height): %s x %s", new Object[]{Integer.valueOf(rotateImageToOriginal.getWidth()), Integer.valueOf(rotateImageToOriginal.getHeight())}));
            return rotateImageToOriginal;
        }
        Log.e(LOG_TAG, "Exception: File not found!");
        throw new FileNotFoundException();
    }

    public static Bitmap loadImage(Context context, Uri uri, int i) {
        double d;
        Bitmap bitmap;
        try {
            InputStream openInputStream = context.getContentResolver().openInputStream(uri);
            Options options = new Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(openInputStream, null, options);
            openInputStream.close();
            int i2 = 1;
            while (true) {
                double d2 = (double) (options.outWidth * options.outHeight);
                double pow = 1.0d / Math.pow((double) i2, 2.0d);
                Double.isNaN(d2);
                double d3 = d2 * pow;
                d = (double) i;
                if (d3 <= d) {
                    break;
                }
                i2++;
            }
            String str = LOG_TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("scale = ");
            sb.append(i2);
            sb.append(", orig-width: ");
            sb.append(options.outWidth);
            sb.append(", orig-height: ");
            sb.append(options.outHeight);
            Log.d(str, sb.toString());
            InputStream openInputStream2 = context.getContentResolver().openInputStream(uri);
            if (i2 > 1) {
                int i3 = i2 - 1;
                Options options2 = new Options();
                options2.inSampleSize = i3;
                Bitmap decodeStream = BitmapFactory.decodeStream(openInputStream2, null, options2);
                int height = decodeStream.getHeight();
                int width = decodeStream.getWidth();
                String str2 = LOG_TAG;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("1st scale operation dimenions - width: ");
                sb2.append(width);
                sb2.append(", height: ");
                sb2.append(height);
                Log.d(str2, sb2.toString());
                double d4 = (double) width;
                double d5 = (double) height;
                Double.isNaN(d4);
                Double.isNaN(d5);
                double d6 = d4 / d5;
                Double.isNaN(d);
                double sqrt = Math.sqrt(d / d6);
                Double.isNaN(d5);
                double d7 = sqrt / d5;
                Double.isNaN(d4);
                bitmap = scaleBitmap(decodeStream, (int) (d7 * d4), (int) sqrt);
                decodeStream.recycle();
            } else {
                bitmap = BitmapFactory.decodeStream(openInputStream2);
            }
            openInputStream2.close();
            Bitmap rotateImageToOriginal = rotateImageToOriginal(context, bitmap, uri);
            String str3 = LOG_TAG;
            StringBuilder sb3 = new StringBuilder();
            sb3.append("bitmap size - width: ");
            sb3.append(rotateImageToOriginal.getWidth());
            sb3.append(", height: ");
            sb3.append(rotateImageToOriginal.getHeight());
            Log.d(str3, sb3.toString());
            return rotateImageToOriginal;
        } catch (IOException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            return null;
        }
    }

    private static Bitmap rotateImageToOriginal(Context context, Bitmap bitmap, Uri uri) {
        int attributeInt = new ExifInterface(StorageUtils.getPath(context, uri)).getAttributeInt(android.support.media.ExifInterface.TAG_ORIENTATION, 1);
        int exifToDegrees = exifToDegrees(attributeInt);
        Matrix matrix = new Matrix();
        if (((float) attributeInt) != 0.0f) {
            Log.i(LOG_TAG, String.format("Loaded image is rotated %s. Rotate the image to original position.", new Object[]{Integer.valueOf(exifToDegrees)}));
            matrix.preRotate((float) exifToDegrees);
        }
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public static Bitmap scaleBitmap(Bitmap bitmap, int i, int i2) {
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Config.ARGB_8888);
        float width = ((float) i) / ((float) bitmap.getWidth());
        float height = ((float) i2) / ((float) bitmap.getHeight());
        Matrix matrix = new Matrix();
        matrix.setScale(width, height, 0.0f, 0.0f);
        Canvas canvas = new Canvas(createBitmap);
        canvas.setMatrix(matrix);
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, new Paint(2));
        return createBitmap;
    }

    public static Bitmap scaleImage(Bitmap bitmap, int i) {
        Log.i(LOG_TAG, String.format("Scale image with maxium size: %s", new Object[]{Integer.valueOf(i)}));
        float f = (float) i;
        return scaleImageByRatio(bitmap, Math.min(f / ((float) bitmap.getWidth()), f / ((float) bitmap.getHeight())));
    }

    public static Bitmap scaleImageByRatio(Bitmap bitmap, float f) {
        return scaleBitmap(bitmap, Math.round(((float) bitmap.getWidth()) * f), Math.round(((float) bitmap.getHeight()) * f));
    }

    public static Bitmap scaleImageMaxHeight(Bitmap bitmap, int i) {
        Log.i(LOG_TAG, String.format("Scale image with maxium height: %s", new Object[]{Integer.valueOf(i)}));
        return scaleImageByRatio(bitmap, ((float) i) / ((float) bitmap.getHeight()));
    }

    public static Bitmap scaleImageMaxWidth(Bitmap bitmap, int i) {
        Log.i(LOG_TAG, String.format("Scale image with maxium width: %s", new Object[]{Integer.valueOf(i)}));
        return scaleImageByRatio(bitmap, ((float) i) / ((float) bitmap.getWidth()));
    }
}
