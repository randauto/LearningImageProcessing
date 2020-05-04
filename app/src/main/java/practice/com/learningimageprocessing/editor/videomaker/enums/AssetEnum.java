package practice.com.learningimageprocessing.editor.videomaker.enums;

import java.util.ArrayList;
import java.util.List;

public enum AssetEnum {
    NONE;
    
    private static final AssetEnum[] VALUES = null;
    public AssetTypeEnum assetType;
    public String name;

    static {
        VALUES = new AssetEnum[]{NONE};
    }

    private AssetEnum(String str, AssetTypeEnum assetTypeEnum) {
        this.name = str;
        this.assetType = assetTypeEnum;
    }

    public static List<AssetEnum> getItems(AssetTypeEnum assetTypeEnum) {
        AssetEnum[] values;
        ArrayList arrayList = new ArrayList();
        for (AssetEnum assetEnum : values()) {
            if (assetEnum.assetType == assetTypeEnum) {
                arrayList.add(assetEnum);
            }
        }
        return arrayList;
    }

    public String getPath() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.assetType.getFolderPath());
        sb.append(this.name);
        return sb.toString();
    }
}
