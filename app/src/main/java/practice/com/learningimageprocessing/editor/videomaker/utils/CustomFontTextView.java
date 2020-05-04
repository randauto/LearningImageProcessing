package practice.com.learningimageprocessing.editor.videomaker.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class CustomFontTextView extends TextView {
    private Context context;
    private String fontName;

    public CustomFontTextView(Context context2, AttributeSet attributeSet) {
        super(context2, attributeSet);
        this.context = context2;
        for (int i = 0; i < attributeSet.getAttributeCount(); i++) {
            this.fontName = attributeSet.getAttributeValue("http://schemas.android.com/apk/res-auto", "fontName");
            init();
        }
    }

    private void init() {
        setTypeface(Typeface.createFromAsset(this.context.getAssets(), this.fontName));
    }

    public void setTypeface(Typeface typeface) {
        super.setTypeface(typeface);
    }
}
