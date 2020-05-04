package practice.com.learningimageprocessing.editor.videomaker.enums;

import java.io.File;

public enum AssetTypeEnum {
    EFFECT("effects"),
    EFFECT_ICON("effect_icons");
    
    private String folderName;

    private AssetTypeEnum(String str) {
        this.folderName = str;
    }

    public String getFolderPath() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.folderName);
        sb.append(File.separator);
        return sb.toString();
    }
}
