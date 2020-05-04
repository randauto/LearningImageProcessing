package practice.com.learningimageprocessing.editor.common.utils;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Matrix;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.OnScanCompletedListener;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore.Audio;
import android.provider.MediaStore.Images.Media;
import android.provider.MediaStore.Images.Thumbnails;
import android.provider.MediaStore.Video;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import p045io.fabric.sdk.android.services.settings.SettingsJsonConstants;

public class StorageUtils {
    /* access modifiers changed from: private */
    public static final String LOG_TAG = "com.photo.effect.editor.common.utils.StorageUtils";

    static class C07041 implements OnScanCompletedListener {
        C07041() {
        }

        public void onScanCompleted(String str, Uri uri) {
            String a = StorageUtils.LOG_TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("Finished scanning ");
            sb.append(str);
            sb.append(". New row: ");
            sb.append(uri);
            Log.d(a, sb.toString());
        }
    }

    private StorageUtils() {
    }

    public static String copyAssetFileToInternalStorage(Context context, String str, String str2, String str3) {
        try {
            return copyFileFromAssetFolder(context, str, context.getDir(str2, 0), str3);
        } catch (Exception unused) {
            return null;
        }
    }

    public static String copyAssetFolderToInternalStorage(Context context, String str, String str2, String str3) {
        try {
            String[] list = context.getAssets().list(str.substring(0, str.lastIndexOf(File.separator)));
            File dir = context.getDir(str2, 0);
            for (String concat : list) {
                copyFileFromAssetFolder(context, str.concat(concat), dir, str3);
            }
            Log.i(LOG_TAG, String.format("Copy asset folder %s to %s success.", new Object[]{str, dir.getPath()}));
            return dir.getPath();
        } catch (Exception unused) {
            return null;
        }
    }

    private static String copyFileFromAssetFolder(Context context, String str, File file, String str2) {
        File file2;
        try {
            InputStream open = context.getAssets().open(str);
            byte[] inputStreamToByteArray = AssetUtils.inputStreamToByteArray(open);
            file2 = new File(file, str.substring(str.lastIndexOf(File.separator) + 1));
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file2);
                fileOutputStream.write(inputStreamToByteArray);
                open.close();
                fileOutputStream.flush();
                fileOutputStream.close();
                Log.i(LOG_TAG, String.format("Copied %s (from asset folder) to %s.", new Object[]{str, file2.getPath()}));
                return file2.getPath();
            } catch (Exception e) {
                e = e;
                Log.e(LOG_TAG, String.format("ERROR while copying file %s (from asset folder) to %s.", new Object[]{str, file2.getPath()}), e);
                throw e;
            }
        } catch (Exception e2) {
            e = e2;
            file2 = null;
            Log.e(LOG_TAG, String.format("ERROR while copying file %s (from asset folder) to %s.", new Object[]{str, file2.getPath()}), e);
            throw e;
        }
    }

    private static String copyFileInInternalStorage(Context context, String str, File file, String str2) {
        File file2;
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(str));
            byte[] inputStreamToByteArray = AssetUtils.inputStreamToByteArray(fileInputStream);
            file2 = new File(file, str.substring(str.lastIndexOf(File.separator) + 1));
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file2);
                fileOutputStream.write(inputStreamToByteArray);
                fileInputStream.close();
                fileOutputStream.flush();
                fileOutputStream.close();
                Log.i(LOG_TAG, String.format("Copied %s to %s.", new Object[]{str, file2.getPath()}));
                return file2.getPath();
            } catch (Exception e) {
                e = e;
                Log.e(LOG_TAG, String.format("ERROR while copying file %s to %s.", new Object[]{str, file2.getPath()}), e);
                throw e;
            }
        } catch (Exception e2) {
            e = e2;
            file2 = null;
            Log.e(LOG_TAG, String.format("ERROR while copying file %s to %s.", new Object[]{str, file2.getPath()}), e);
            throw e;
        }
    }

    public static String copyFolderInInternalStorage(Context context, File file, File file2, String str) {
        try {
            for (File path : file.listFiles()) {
                copyFileInInternalStorage(context, path.getPath(), file2, str);
            }
            Log.i(LOG_TAG, String.format("Copy asset folder %s to %s success.", new Object[]{file.getPath(), file2.getPath()}));
            return file2.getPath();
        } catch (Exception unused) {
            return null;
        }
    }

    public static void deleteFile(String str) {
        String str2 = LOG_TAG;
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Delete %s ", new Object[]{str}));
        sb.append(new File(str).delete() ? "success." : "FAILED!");
        Log.i(str2, sb.toString());
    }

    public static boolean deleteFileOrDirectory(File file) {
        if (file.isDirectory()) {
            for (File deleteFileOrDirectory : file.listFiles()) {
                deleteFileOrDirectory(deleteFileOrDirectory);
            }
        }
        boolean delete = file.delete();
        String str = LOG_TAG;
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Delete %s ", new Object[]{file.getPath()}));
        sb.append(delete ? "success." : "FAILED!");
        Log.i(str, sb.toString());
        return delete;
    }

    /* JADX WARNING: Removed duplicated region for block: B:25:0x0041  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static String getDataColumn(Context r7, Uri r8, String r9, String[] r10) {
        /*
            r0 = 0
            android.content.ContentResolver r1 = r7.getContentResolver()     // Catch:{ Throwable -> 0x003e }
            r7 = 1
            java.lang.String[] r3 = new java.lang.String[r7]     // Catch:{ Throwable -> 0x003e }
            r7 = 0
            java.lang.String r2 = "_data"
            r3[r7] = r2     // Catch:{ Throwable -> 0x003e }
            r6 = 0
            r2 = r8
            r4 = r9
            r5 = r10
            android.database.Cursor r7 = r1.query(r2, r3, r4, r5, r6)     // Catch:{ Throwable -> 0x003e }
            if (r7 == 0) goto L_0x0032
            boolean r8 = r7.moveToFirst()     // Catch:{ Throwable -> 0x0030 }
            if (r8 != 0) goto L_0x001e
            goto L_0x0032
        L_0x001e:
            java.lang.String r8 = "_data"
            int r8 = r7.getColumnIndexOrThrow(r8)     // Catch:{ Throwable -> 0x0030 }
            java.lang.String r8 = r7.getString(r8)     // Catch:{ Throwable -> 0x0030 }
            if (r7 == 0) goto L_0x003d
            r7.close()     // Catch:{ Throwable -> 0x002e }
            goto L_0x003d
        L_0x002e:
            r0 = r8
            goto L_0x003f
        L_0x0030:
            goto L_0x003f
        L_0x0032:
            if (r7 == 0) goto L_0x0037
            r7.close()     // Catch:{ Throwable -> 0x0030 }
        L_0x0037:
            if (r7 == 0) goto L_0x003c
            r7.close()     // Catch:{ Throwable -> 0x0030 }
        L_0x003c:
            r8 = r0
        L_0x003d:
            return r8
        L_0x003e:
            r7 = r0
        L_0x003f:
            if (r7 == 0) goto L_0x0044
            r7.close()
        L_0x0044:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.photo.effect.editor.common.utils.StorageUtils.getDataColumn(android.content.Context, android.net.Uri, java.lang.String, java.lang.String[]):java.lang.String");
    }

    @SuppressLint({"NewApi"})
    public static String getPath(Context context, Uri uri) {
        Uri uri2 = null;
        if ((VERSION.SDK_INT >= 19) && DocumentsContract.isDocumentUri(context, uri)) {
            String[] strArr = new String[0];
            if (isExternalStorageDocument(uri)) {
                String[] split = DocumentsContract.getDocumentId(uri).split(":");
                if (!"primary".equalsIgnoreCase(split[0])) {
                    return null;
                }
                StringBuilder sb = new StringBuilder();
                sb.append(Environment.getExternalStorageDirectory());
                sb.append("/");
                sb.append(split[1]);
                return sb.toString();
            } else if (isDownloadsDocument(uri)) {
                return getDataColumn(context, ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(DocumentsContract.getDocumentId(uri)).longValue()), null, null);
            } else {
                if (!isMediaDocument(uri)) {
                    return null;
                }
                String str = DocumentsContract.getDocumentId(uri).split(":")[0];
                if ("image".equals(str)) {
                    uri2 = Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(str)) {
                    uri2 = Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(str)) {
                    uri2 = Audio.Media.EXTERNAL_CONTENT_URI;
                }
                return getDataColumn(context, uri2, "_id=?", new String[]{strArr[1]});
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        } else {
            if ("file".equalsIgnoreCase(uri.getScheme())) {
                return uri.getPath();
            }
            return null;
        }
    }

    /* JADX WARNING: type inference failed for: r12v5, types: [android.net.Uri, java.lang.String, java.lang.String[], android.graphics.BitmapFactory$Options] */
    /* JADX WARNING: type inference failed for: r13v4, types: [android.net.Uri] */
    /* JADX WARNING: type inference failed for: r13v5 */
    /* JADX WARNING: type inference failed for: r13v7, types: [android.net.Uri] */
    /* JADX WARNING: type inference failed for: r13v9 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r12v5, types: [android.net.Uri, java.lang.String, java.lang.String[], android.graphics.BitmapFactory$Options]
      assigns: [?[int, float, boolean, short, byte, char, OBJECT, ARRAY]]
      uses: [java.lang.String, java.lang.String[], ?[OBJECT, ARRAY], android.net.Uri, android.graphics.BitmapFactory$Options]
      mth insns count: 66
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:99)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:92)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x00ae  */
    /* JADX WARNING: Unknown variable types count: 3 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final String insertImage(ContentResolver r10, Bitmap r11, String r12, String r13) {
        /*
            android.content.ContentValues r0 = new android.content.ContentValues
            r0.<init>()
            java.lang.String r1 = "title"
            r0.put(r1, r12)
            java.lang.String r1 = "_display_name"
            r0.put(r1, r12)
            java.lang.String r12 = "description"
            r0.put(r12, r13)
            java.lang.String r12 = "mime_type"
            java.lang.String r13 = "image/jpeg"
            r0.put(r12, r13)
            java.lang.String r12 = "date_added"
            long r1 = java.lang.System.currentTimeMillis()
            java.lang.Long r13 = java.lang.Long.valueOf(r1)
            r0.put(r12, r13)
            java.lang.String r12 = "datetaken"
            long r1 = java.lang.System.currentTimeMillis()
            java.lang.Long r13 = java.lang.Long.valueOf(r1)
            r0.put(r12, r13)
            r12 = 0
            android.net.Uri r13 = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI     // Catch:{ Exception -> 0x00a3 }
            android.net.Uri r13 = r10.insert(r13, r0)     // Catch:{ Exception -> 0x00a3 }
            if (r11 == 0) goto L_0x007e
            java.io.OutputStream r0 = r10.openOutputStream(r13)     // Catch:{ Exception -> 0x007c }
            android.graphics.Bitmap$CompressFormat r1 = android.graphics.Bitmap.CompressFormat.JPEG     // Catch:{ Exception -> 0x007c }
            r2 = 100
            r11.compress(r1, r2, r0)     // Catch:{ Exception -> 0x007c }
            r0.close()     // Catch:{ Exception -> 0x007c }
            long r5 = android.content.ContentUris.parseId(r13)     // Catch:{ Exception -> 0x007c }
            r11 = 1
            android.graphics.Bitmap r4 = android.provider.MediaStore.Images.Thumbnails.getThumbnail(r10, r5, r11, r12)     // Catch:{ Exception -> 0x007c }
            r7 = 1112014848(0x42480000, float:50.0)
            r8 = 1112014848(0x42480000, float:50.0)
            r9 = 3
            r3 = r10
            storeThumbnail(r3, r4, r5, r7, r8, r9)     // Catch:{ Exception -> 0x007c }
            if (r13 == 0) goto L_0x0061
            return r12
        L_0x0061:
            java.lang.String r11 = r13.toString()     // Catch:{ Exception -> 0x007c }
            java.lang.String r0 = LOG_TAG     // Catch:{ Exception -> 0x007c }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x007c }
            r1.<init>()     // Catch:{ Exception -> 0x007c }
            java.lang.String r2 = "Image has been inserted to "
            r1.append(r2)     // Catch:{ Exception -> 0x007c }
            r1.append(r11)     // Catch:{ Exception -> 0x007c }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x007c }
            android.util.Log.i(r0, r1)     // Catch:{ Exception -> 0x007c }
            return r11
        L_0x007c:
            r11 = move-exception
            goto L_0x00a5
        L_0x007e:
            java.lang.String r11 = LOG_TAG     // Catch:{ Exception -> 0x007c }
            java.lang.String r0 = "Failed to create thumbnail, removing original"
            android.util.Log.e(r11, r0)     // Catch:{ Exception -> 0x007c }
            r10.delete(r13, r12, r12)     // Catch:{ Exception -> 0x007c }
            java.lang.String r11 = r12.toString()     // Catch:{ Exception -> 0x00a3 }
            java.lang.String r13 = LOG_TAG     // Catch:{ Exception -> 0x00a3 }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00a3 }
            r0.<init>()     // Catch:{ Exception -> 0x00a3 }
            java.lang.String r1 = "Image has been inserted to "
            r0.append(r1)     // Catch:{ Exception -> 0x00a3 }
            r0.append(r11)     // Catch:{ Exception -> 0x00a3 }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x00a3 }
            android.util.Log.i(r13, r0)     // Catch:{ Exception -> 0x00a3 }
            return r11
        L_0x00a3:
            r11 = move-exception
            r13 = r12
        L_0x00a5:
            java.lang.String r0 = LOG_TAG
            java.lang.String r1 = "Failed to insert image"
            android.util.Log.e(r0, r1, r11)
            if (r13 == 0) goto L_0x00b1
            r10.delete(r13, r12, r12)
        L_0x00b1:
            return r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.photo.effect.editor.common.utils.StorageUtils.insertImage(android.content.ContentResolver, android.graphics.Bitmap, java.lang.String, java.lang.String):java.lang.String");
    }

    private static void insertImageToMediaStore(Context context, Bitmap bitmap, String str) {
        try {
            if (insertImage(context.getContentResolver(), bitmap, str, str) != null) {
                Log.i(LOG_TAG, "Insert image to MediaStore success.");
                return;
            }
            throw new Exception("Insert image to MediaStore return empty URL.");
        } catch (Exception e) {
            Log.e(LOG_TAG, "Insert image to MediaStore error!", e);
            throw e;
        }
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static void refreshStorage(Context context, String... strArr) {
        Intent intent;
        try {
            if (VERSION.SDK_INT >= 19) {
                intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
                intent.setData(Uri.fromFile(new File(strArr[0])));
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("file://");
                sb.append(Environment.getExternalStorageDirectory());
                intent = new Intent("android.intent.action.MEDIA_MOUNTED", Uri.parse(sb.toString()));
            }
            context.sendBroadcast(intent);
        } catch (Exception unused) {
            if (VERSION.SDK_INT >= 19) {
                MediaScannerConnection.scanFile(context, strArr, null, new C07041());
                return;
            }
            try {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("file://");
                sb2.append(Environment.getExternalStorageDirectory());
                context.sendBroadcast(new Intent("android.intent.action.MEDIA_MOUNTED", Uri.parse(sb2.toString())));
            } catch (Exception unused2) {
            }
        }
    }

    /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x0017 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void saveImage(Context r2, Bitmap r3, File r4, String r5) {
        /*
            java.lang.String r0 = LOG_TAG
            java.lang.String r1 = "Start to save image to external storage..."
            android.util.Log.i(r0, r1)
            java.lang.String r0 = LOG_TAG     // Catch:{ Exception -> 0x0017, Throwable -> 0x0012 }
            java.lang.String r1 = "Start to insert image to MediaStore..."
            android.util.Log.i(r0, r1)     // Catch:{ Exception -> 0x0017, Throwable -> 0x0012 }
            insertImageToMediaStore(r2, r3, r5)     // Catch:{ Exception -> 0x0017, Throwable -> 0x0012 }
            goto L_0x0026
        L_0x0012:
            r2 = move-exception
            r2.printStackTrace()
            goto L_0x0026
        L_0x0017:
            java.lang.String r0 = LOG_TAG     // Catch:{ Exception -> 0x002e }
            java.lang.String r1 = "Insert image to MediaStore FAILED! Now try to write image to external storage..."
            android.util.Log.i(r0, r1)     // Catch:{ Exception -> 0x002e }
            writeImageToExternalStorage(r2, r3, r4, r5)     // Catch:{ Throwable -> 0x0022 }
            goto L_0x0026
        L_0x0022:
            r2 = move-exception
            r2.printStackTrace()     // Catch:{ Exception -> 0x002e }
        L_0x0026:
            java.lang.String r2 = LOG_TAG
            java.lang.String r3 = "Save image success."
            android.util.Log.i(r2, r3)
            return
        L_0x002e:
            r2 = move-exception
            java.lang.String r3 = LOG_TAG
            java.lang.String r4 = "Write image to external memory FAILED!"
            android.util.Log.i(r3, r4)
            java.lang.String r3 = LOG_TAG
            java.lang.String r4 = "Save image FAILED!"
            android.util.Log.i(r3, r4)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.photo.effect.editor.common.utils.StorageUtils.saveImage(android.content.Context, android.graphics.Bitmap, java.io.File, java.lang.String):void");
    }

    private static final Bitmap storeThumbnail(ContentResolver contentResolver, Bitmap bitmap, long j, float f, float f2, int i) {
        Matrix matrix = new Matrix();
        matrix.setScale(f / ((float) bitmap.getWidth()), f2 / ((float) bitmap.getHeight()));
        Bitmap createBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        ContentValues contentValues = new ContentValues(4);
        contentValues.put("kind", Integer.valueOf(i));
        contentValues.put("image_id", Integer.valueOf((int) j));
        contentValues.put(SettingsJsonConstants.ICON_HEIGHT_KEY, Integer.valueOf(createBitmap.getHeight()));
        contentValues.put(SettingsJsonConstants.ICON_WIDTH_KEY, Integer.valueOf(createBitmap.getWidth()));
        try {
            OutputStream openOutputStream = contentResolver.openOutputStream(contentResolver.insert(Thumbnails.EXTERNAL_CONTENT_URI, contentValues));
            createBitmap.compress(CompressFormat.JPEG, 100, openOutputStream);
            openOutputStream.close();
            return createBitmap;
        } catch (FileNotFoundException | IOException unused) {
            return null;
        }
    }

    /* JADX WARNING: Missing exception handler attribute for start block: B:22:0x00be */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static String writeImageToExternalStorage(Context r3, Bitmap r4, File r5, String r6) {
        /*
            java.lang.String r0 = LOG_TAG
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "File directory to save: "
            r1.append(r2)
            java.lang.String r2 = r5.getPath()
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            android.util.Log.i(r0, r1)
            boolean r0 = r5.exists()
            if (r0 != 0) goto L_0x004e
            java.lang.String r0 = LOG_TAG
            java.lang.String r1 = "Directory not found yet. Start to create directory..."
            android.util.Log.e(r0, r1)
            boolean r0 = r5.mkdirs()
            if (r0 == 0) goto L_0x002e
            goto L_0x004e
        L_0x002e:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "Cannot create directory: "
            r3.append(r4)
            java.lang.String r4 = r5.getPath()
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            java.lang.String r4 = LOG_TAG
            android.util.Log.e(r4, r3)
            java.lang.Exception r4 = new java.lang.Exception
            r4.<init>(r3)
            throw r4
        L_0x004e:
            java.io.File r0 = new java.io.File
            r0.<init>(r5, r6)
            boolean r5 = r0.exists()
            if (r5 == 0) goto L_0x0070
            java.lang.String r5 = LOG_TAG
            java.lang.String r1 = "Detect file exists with the same name. File will be delete now to write new one..."
            android.util.Log.i(r5, r1)
            java.lang.String r5 = LOG_TAG
            boolean r1 = r0.delete()
            if (r1 == 0) goto L_0x006b
            java.lang.String r1 = "Delete existing file success."
            goto L_0x006d
        L_0x006b:
            java.lang.String r1 = "Cannot delete existing file! Now try to overwrite existing file..."
        L_0x006d:
            android.util.Log.i(r5, r1)
        L_0x0070:
            java.io.FileOutputStream r5 = new java.io.FileOutputStream     // Catch:{ IOException -> 0x00e0, Throwable -> 0x00d7 }
            r5.<init>(r0)     // Catch:{ IOException -> 0x00e0, Throwable -> 0x00d7 }
            android.graphics.Bitmap$CompressFormat r1 = android.graphics.Bitmap.CompressFormat.JPEG     // Catch:{ IOException -> 0x00ce, Exception -> 0x00c5, Throwable -> 0x00be }
            r2 = 100
            boolean r4 = r4.compress(r1, r2, r5)     // Catch:{ IOException -> 0x00ce, Exception -> 0x00c5, Throwable -> 0x00be }
            if (r4 == 0) goto L_0x00b6
            r5.flush()     // Catch:{ IOException -> 0x00ce, Exception -> 0x00c5, Throwable -> 0x00be }
            r5.close()     // Catch:{ IOException -> 0x00ce, Exception -> 0x00c5, Throwable -> 0x00be }
            java.lang.String r4 = LOG_TAG     // Catch:{ IOException -> 0x00ce, Exception -> 0x00c5, Throwable -> 0x00be }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x00ce, Exception -> 0x00c5, Throwable -> 0x00be }
            r1.<init>()     // Catch:{ IOException -> 0x00ce, Exception -> 0x00c5, Throwable -> 0x00be }
            java.lang.String r2 = "Write image to "
            r1.append(r2)     // Catch:{ IOException -> 0x00ce, Exception -> 0x00c5, Throwable -> 0x00be }
            java.lang.String r2 = r0.getAbsolutePath()     // Catch:{ IOException -> 0x00ce, Exception -> 0x00c5, Throwable -> 0x00be }
            r1.append(r2)     // Catch:{ IOException -> 0x00ce, Exception -> 0x00c5, Throwable -> 0x00be }
            java.lang.String r2 = " success."
            r1.append(r2)     // Catch:{ IOException -> 0x00ce, Exception -> 0x00c5, Throwable -> 0x00be }
            java.lang.String r1 = r1.toString()     // Catch:{ IOException -> 0x00ce, Exception -> 0x00c5, Throwable -> 0x00be }
            android.util.Log.i(r4, r1)     // Catch:{ IOException -> 0x00ce, Exception -> 0x00c5, Throwable -> 0x00be }
            r4 = 1
            java.lang.String[] r4 = new java.lang.String[r4]     // Catch:{ IOException -> 0x00ce, Exception -> 0x00c5, Throwable -> 0x00be }
            r1 = 0
            java.lang.String r2 = r0.getPath()     // Catch:{ IOException -> 0x00ce, Exception -> 0x00c5, Throwable -> 0x00be }
            r4[r1] = r2     // Catch:{ IOException -> 0x00ce, Exception -> 0x00c5, Throwable -> 0x00be }
            refreshStorage(r3, r4)     // Catch:{ IOException -> 0x00ce, Exception -> 0x00c5, Throwable -> 0x00be }
            java.lang.String r3 = r0.getPath()     // Catch:{ IOException -> 0x00ce, Exception -> 0x00c5, Throwable -> 0x00be }
            return r3
        L_0x00b6:
            java.lang.Exception r3 = new java.lang.Exception     // Catch:{ IOException -> 0x00ce, Exception -> 0x00c5, Throwable -> 0x00be }
            java.lang.String r4 = "Compress image FAILED!"
            r3.<init>(r4)     // Catch:{ IOException -> 0x00ce, Exception -> 0x00c5, Throwable -> 0x00be }
            throw r3     // Catch:{ IOException -> 0x00ce, Exception -> 0x00c5, Throwable -> 0x00be }
        L_0x00be:
            r5.flush()     // Catch:{ IOException -> 0x00e0, Throwable -> 0x00d7 }
            r5.close()     // Catch:{ IOException -> 0x00e0, Throwable -> 0x00d7 }
            goto L_0x00df
        L_0x00c5:
            r3 = move-exception
            java.lang.String r4 = LOG_TAG     // Catch:{ IOException -> 0x00e0, Throwable -> 0x00d7 }
            java.lang.String r5 = "Save file error!"
            android.util.Log.e(r4, r5, r3)     // Catch:{ IOException -> 0x00e0, Throwable -> 0x00d7 }
            throw r3     // Catch:{ IOException -> 0x00e0, Throwable -> 0x00d7 }
        L_0x00ce:
            r3 = move-exception
            java.lang.String r4 = LOG_TAG     // Catch:{ IOException -> 0x00e0, Throwable -> 0x00d7 }
            java.lang.String r5 = "Error while close output stream!"
            android.util.Log.e(r4, r5, r3)     // Catch:{ IOException -> 0x00e0, Throwable -> 0x00d7 }
            throw r3     // Catch:{ IOException -> 0x00e0, Throwable -> 0x00d7 }
        L_0x00d7:
            r3 = move-exception
            java.lang.String r4 = LOG_TAG
            java.lang.String r5 = "Save file error!"
            android.util.Log.e(r4, r5, r3)
        L_0x00df:
            return r6
        L_0x00e0:
            r3 = move-exception
            java.lang.String r4 = LOG_TAG
            java.lang.String r5 = "Error while close output stream!"
            android.util.Log.e(r4, r5, r3)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.photo.effect.editor.common.utils.StorageUtils.writeImageToExternalStorage(android.content.Context, android.graphics.Bitmap, java.io.File, java.lang.String):java.lang.String");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0050, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0051, code lost:
        android.util.Log.e(LOG_TAG, "Error while saving image to internal storage!", r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0058, code lost:
        throw r3;
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:18:0x0043 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:6:0x002b */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0050 A[ExcHandler: Exception (r3v4 'e' java.lang.Exception A[CUSTOM_DECLARE]), Splitter:B:1:0x0017] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static String writeImageToInternalStorage(Context r3, Bitmap r4, String r5, String r6) {
        /*
            java.lang.String r0 = LOG_TAG
            java.lang.String r1 = "Start to save image to internal storage (mode private)..."
            android.util.Log.i(r0, r1)
            java.io.File r0 = new java.io.File
            android.content.ContextWrapper r1 = new android.content.ContextWrapper
            r1.<init>(r3)
            r3 = 0
            java.io.File r3 = r1.getDir(r5, r3)
            r0.<init>(r3, r6)
            r3 = 0
            java.io.FileOutputStream r6 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x0050, Throwable -> 0x0043 }
            r6.<init>(r0)     // Catch:{ Exception -> 0x0050, Throwable -> 0x0043 }
            android.graphics.Bitmap$CompressFormat r1 = android.graphics.Bitmap.CompressFormat.PNG     // Catch:{ IOException -> 0x003a, Exception -> 0x002f, Throwable -> 0x002b }
            r2 = 100
            r4.compress(r1, r2, r6)     // Catch:{ IOException -> 0x003a, Exception -> 0x002f, Throwable -> 0x002b }
            r6.close()     // Catch:{ IOException -> 0x003a, Exception -> 0x002f, Throwable -> 0x002b }
            java.lang.String r4 = r0.getPath()     // Catch:{ IOException -> 0x003a, Exception -> 0x002f, Throwable -> 0x002b }
            return r4
        L_0x002b:
            r6.close()     // Catch:{ Exception -> 0x0050, Throwable -> 0x0043 }
            goto L_0x0046
        L_0x002f:
            r3 = move-exception
            java.lang.String r4 = LOG_TAG     // Catch:{ Exception -> 0x0050, Throwable -> 0x0038 }
            java.lang.String r0 = "Error while saving image to internal storage!"
            android.util.Log.e(r4, r0, r3)     // Catch:{ Exception -> 0x0050, Throwable -> 0x0038 }
            throw r3     // Catch:{ Exception -> 0x0050, Throwable -> 0x0038 }
        L_0x0038:
            r3 = r6
            goto L_0x0043
        L_0x003a:
            r4 = move-exception
            java.lang.String r6 = LOG_TAG     // Catch:{ Exception -> 0x0050, Throwable -> 0x0043 }
            java.lang.String r0 = "Error while closing file output stream!"
            android.util.Log.e(r6, r0, r4)     // Catch:{ Exception -> 0x0050, Throwable -> 0x0043 }
            throw r4     // Catch:{ Exception -> 0x0050, Throwable -> 0x0043 }
        L_0x0043:
            r3.close()     // Catch:{ IOException -> 0x0047 }
        L_0x0046:
            return r5
        L_0x0047:
            r3 = move-exception
            java.lang.String r4 = LOG_TAG
            java.lang.String r5 = "Error while closing file output stream!"
            android.util.Log.e(r4, r5, r3)
            throw r3
        L_0x0050:
            r3 = move-exception
            java.lang.String r4 = LOG_TAG
            java.lang.String r5 = "Error while saving image to internal storage!"
            android.util.Log.e(r4, r5, r3)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.photo.effect.editor.common.utils.StorageUtils.writeImageToInternalStorage(android.content.Context, android.graphics.Bitmap, java.lang.String, java.lang.String):java.lang.String");
    }
}
