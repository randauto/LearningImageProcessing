package practice.com.learningimageprocessing.editor.videomaker.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import com.photo.effect.editor.common.constants.DevConstants;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class UtilsGallery {
    public static boolean checkCameraHardware(Context context) {
        return context.getPackageManager().hasSystemFeature("android.hardware.camera");
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x003f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static String getDataColumn(Context r7, Uri r8, String r9, String[] r10) {
        /*
            r0 = 0
            android.content.ContentResolver r1 = r7.getContentResolver()     // Catch:{ all -> 0x003b }
            r7 = 1
            java.lang.String[] r3 = new java.lang.String[r7]     // Catch:{ all -> 0x003b }
            r7 = 0
            java.lang.String r2 = "_data"
            r3[r7] = r2     // Catch:{ all -> 0x003b }
            r6 = 0
            r2 = r8
            r4 = r9
            r5 = r10
            android.database.Cursor r7 = r1.query(r2, r3, r4, r5, r6)     // Catch:{ all -> 0x003b }
            if (r7 == 0) goto L_0x0030
            boolean r8 = r7.moveToFirst()     // Catch:{ all -> 0x002e }
            if (r8 != 0) goto L_0x001e
            goto L_0x0030
        L_0x001e:
            java.lang.String r8 = "_data"
            int r8 = r7.getColumnIndexOrThrow(r8)     // Catch:{ all -> 0x002e }
            java.lang.String r8 = r7.getString(r8)     // Catch:{ all -> 0x002e }
            if (r7 == 0) goto L_0x002d
            r7.close()
        L_0x002d:
            return r8
        L_0x002e:
            r8 = move-exception
            goto L_0x003d
        L_0x0030:
            if (r7 == 0) goto L_0x0035
            r7.close()     // Catch:{ all -> 0x002e }
        L_0x0035:
            if (r7 == 0) goto L_0x003a
            r7.close()
        L_0x003a:
            return r0
        L_0x003b:
            r8 = move-exception
            r7 = r0
        L_0x003d:
            if (r7 == 0) goto L_0x0042
            r7.close()
        L_0x0042:
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.photo.effect.editor.videomaker.utils.UtilsGallery.getDataColumn(android.content.Context, android.net.Uri, java.lang.String, java.lang.String[]):java.lang.String");
    }

    public static File getOutputMediaFile(int i) {
        String str = "Camera";
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), str);
        if (file.exists() || file.mkdirs()) {
            String format = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
            if (i == 1) {
                StringBuilder sb = new StringBuilder();
                sb.append(file.getPath());
                sb.append(File.separator);
                sb.append("IMG_");
                sb.append(format);
                sb.append(DevConstants.JPG_EXTENSION);
                return new File(sb.toString());
            } else if (i != 2) {
                return null;
            } else {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(file.getPath());
                sb2.append(File.separator);
                sb2.append("VID_");
                sb2.append(format);
                sb2.append(DevConstants.MP4_EXTENSION);
                return new File(sb2.toString());
            }
        } else {
            StringBuilder sb3 = new StringBuilder();
            sb3.append("Oops! Failed create ");
            sb3.append(str);
            sb3.append(" directory");
            Log.d(str, sb3.toString());
            return null;
        }
    }

    public static Uri getOutputMediaFileUri(int i) {
        return Uri.fromFile(getOutputMediaFile(i));
    }

    public static String getPathFromURI(Uri uri, Context context) {
        Cursor query = context.getContentResolver().query(uri, null, null, null, null);
        if (query == null) {
            return uri.getPath();
        }
        query.moveToFirst();
        String string = query.getString(query.getColumnIndex("_data"));
        query.close();
        return string;
    }

    public static String getRealPathFromURI_API19(Context context, Uri uri) {
        boolean z = VERSION.SDK_INT >= 19;
        Uri uri2 = null;
        if (VERSION.SDK_INT >= 19) {
            if (z && DocumentsContract.isDocumentUri(context, uri)) {
                String[] strArr = new String[0];
                if (isExternalStorageDocument(uri)) {
                    String[] split = DocumentsContract.getDocumentId(uri).split(":");
                    if ("primary".equalsIgnoreCase(split[0])) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(Environment.getExternalStorageDirectory());
                        sb.append("/");
                        sb.append(split[1]);
                        return sb.toString();
                    }
                } else if (isMediaDocument(uri)) {
                    if ("image".equals(DocumentsContract.getDocumentId(uri).split(":")[0])) {
                        uri2 = Media.EXTERNAL_CONTENT_URI;
                    }
                    return getDataColumn(context, uri2, "_id=?", new String[]{strArr[1]});
                }
            } else if ("content".equalsIgnoreCase(uri.getScheme())) {
                return getDataColumn(context, uri, null, null);
            } else {
                if ("file".equalsIgnoreCase(uri.getScheme())) {
                    return uri.getPath();
                }
            }
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }
}
