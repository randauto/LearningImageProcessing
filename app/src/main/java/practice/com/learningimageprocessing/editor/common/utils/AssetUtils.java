package practice.com.learningimageprocessing.editor.common.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.util.Log;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public final class AssetUtils {
    public static final String AES = "AES";
    private static final String LOG_TAG = "com.photo.effect.editor.common.utils.AssetUtils";

    private AssetUtils() {
    }

    private static int calculateInSampleSize(Options options, int i, int i2) {
        int i3 = options.outHeight;
        int i4 = options.outWidth;
        int i5 = 1;
        if (i3 > i2 || i4 > i) {
            int i6 = i3 / 2;
            int i7 = i4 / 2;
            while (i6 / i5 > i2 && i7 / i5 > i) {
                i5 *= 2;
            }
        }
        return i5;
    }

    private static void dirChecker(String str, String str2) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(str2);
        File file = new File(sb.toString());
        if (!file.isDirectory() && !file.mkdirs()) {
            String str3 = LOG_TAG;
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Failed to create folder ");
            sb2.append(file.getName());
            Log.w(str3, sb2.toString());
        }
    }

    public static Bitmap getBitmapFromAsset(Context context, String str, Options options, String str2) {
        if (str2 == null) {
            return getBitmapFromRawAsset(context, str, options);
        }
        byte[] byteArrayFromAsset = getByteArrayFromAsset(context, str);
        return BitmapFactory.decodeByteArray(byteArrayFromAsset, 0, byteArrayFromAsset.length, options);
    }

    public static Bitmap getBitmapFromInternalStorage(String str, Options options, String str2) {
        if (str2 == null) {
            return BitmapFactory.decodeStream(new FileInputStream(new File(str)));
        }
        byte[] byteArrayFromInternalStorage = getByteArrayFromInternalStorage(str);
        try {
            return BitmapFactory.decodeByteArray(byteArrayFromInternalStorage, 0, byteArrayFromInternalStorage.length, options);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Bitmap getBitmapFromRawAsset(Context context, String str, Options options) {
        try {
            return BitmapFactory.decodeStream(context.getAssets().open(str), null, options);
        } catch (IOException unused) {
            return null;
        }
    }

    private static byte[] getByteArrayFromAsset(Context context, String str) {
        try {
            InputStream open = context.getAssets().open(str);
            byte[] bArr = new byte[open.available()];
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            while (true) {
                int read = open.read(bArr);
                if (read == -1) {
                    return byteArrayOutputStream.toByteArray();
                }
                byteArrayOutputStream.write(bArr, 0, read);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static byte[] getByteArrayFromInternalStorage(String str) {
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(str));
            byte[] bArr = new byte[fileInputStream.available()];
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            while (true) {
                int read = fileInputStream.read(bArr);
                if (read == -1) {
                    return byteArrayOutputStream.toByteArray();
                }
                byteArrayOutputStream.write(bArr, 0, read);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] inputStreamToByteArray(InputStream inputStream) {
        try {
            byte[] bArr = new byte[inputStream.available()];
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            while (true) {
                int read = inputStream.read(bArr);
                if (read == -1) {
                    return byteArrayOutputStream.toByteArray();
                }
                byteArrayOutputStream.write(bArr, 0, read);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void unzip(InputStream inputStream, String str) {
        dirChecker(str, "");
        byte[] bArr = new byte[1024];
        try {
            ZipInputStream zipInputStream = new ZipInputStream(inputStream);
            while (true) {
                ZipEntry nextEntry = zipInputStream.getNextEntry();
                if (nextEntry != null) {
                    String str2 = LOG_TAG;
                    StringBuilder sb = new StringBuilder();
                    sb.append("Unzipping ");
                    sb.append(nextEntry.getName());
                    sb.append(" to ");
                    sb.append(str);
                    Log.v(str2, sb.toString());
                    if (nextEntry.isDirectory()) {
                        dirChecker(str, nextEntry.getName());
                    } else {
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(str);
                        sb2.append(nextEntry.getName());
                        if (!new File(sb2.toString()).exists()) {
                            StringBuilder sb3 = new StringBuilder();
                            sb3.append(str);
                            sb3.append(nextEntry.getName());
                            FileOutputStream fileOutputStream = new FileOutputStream(sb3.toString());
                            while (true) {
                                int read = zipInputStream.read(bArr);
                                if (read == -1) {
                                    break;
                                }
                                fileOutputStream.write(bArr, 0, read);
                            }
                            zipInputStream.closeEntry();
                            fileOutputStream.close();
                        }
                    }
                } else {
                    zipInputStream.close();
                    return;
                }
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "unzip", e);
        }
    }

    public static void unzipFromAssets(Context context, String str, String str2) {
        if (str2.length() == 0) {
            str2 = context.getFilesDir().getAbsolutePath();
        }
        try {
            unzip(context.getAssets().open(str), str2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
