package practice.com.learningimageprocessing.editor.common.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.io.Closeable;
import java.io.IOException;

public class FontUtils {
    public static Typeface getTypefaceFromRaw(Context context, int i) {
        throw new UnsupportedOperationException("Method not decompiled: com.photo.effect.editor.common.utils.FontUtils.getTypefaceFromRaw(android.content.Context, int):android.graphics.Typeface");
    }

    public static Typeface loadFont(Context context, String str) {
        return Typeface.createFromAsset(context.getAssets(), str);
    }

    public static void setFontForView(View view, Typeface typeface) {
        if ((view instanceof TextView) || (view instanceof EditText) || (view instanceof Button)) {
            ((TextView) view).setTypeface(typeface);
        }
    }

    public static void setFontForViewGroup(ViewGroup viewGroup, Typeface typeface) {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = viewGroup.getChildAt(i);
            if ((childAt instanceof TextView) || (childAt instanceof EditText) || (childAt instanceof Button)) {
                ((TextView) childAt).setTypeface(typeface);
            } else if (childAt instanceof ViewGroup) {
                setFontForViewGroup((ViewGroup) childAt, typeface);
            }
        }
    }

    private static void tryClose(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
