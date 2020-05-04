package practice.com.learningimageprocessing.editor.videomaker.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import practice.com.learningimageprocessing.editor.common.constants.DevConstants;
import practice.com.learningimageprocessing.editor.videomaker.enums.AssetTypeEnum;

public class Effect {
    public static final List<Effect> LIST = new ArrayList();
    private String displayName;
    private int fps;
    private int frameNum;
    private String name;

    static {
        LIST.add(new Effect("love_heart2", "Love Heart2", 144, 20));
        LIST.add(new Effect("heart1", "Heart", 450, 20));
        LIST.add(new Effect("hearts", "Heart1", 144, 20));
        LIST.add(new Effect("leaves", "Leaves", 301, 25));
        LIST.add(new Effect("love_heart", "Love Heart", 144, 20));
        LIST.add(new Effect("love_heart1", "Love Heart1", 144, 20));
        LIST.add(new Effect("red-heart", "Red Heart", 133, 20));
        LIST.add(new Effect("rose-leaves", "Rose Leaves", 276, 20));
        LIST.add(new Effect("red-love-heart", "Red Love Heart", 286, 20));
        LIST.add(new Effect("snow", "Snow", 47, 15));
        LIST.add(new Effect("star", "Fire", 48, 15));
        LIST.add(new Effect("bubble", "Bubble", 24, 15));
        LIST.add(new Effect("confetty", "Confetty", 144, 15));
        LIST.add(new Effect("fire", "Fire", 10, 20));
        LIST.add(new Effect("fireworks", "Fireworks", 24, 15));
    }

    private Effect(String str, String str2, int i, int i2) {
        this.name = str;
        this.displayName = str2;
        this.frameNum = i;
        this.fps = i2;
    }

    private String getFramePath(String str, int i) {
        StringBuilder sb = new StringBuilder();
        sb.append(this.name);
        sb.append("/");
        String sb2 = sb.toString();
        String format = String.format("%s%s%s", new Object[]{this.name, String.format(DevConstants.FRAME_SEQUENTIAL_POSTFIX,
                new Object[]{Integer.valueOf(i)}), DevConstants.PNG_EXTENSION});
        StringBuilder sb3 = new StringBuilder();
        sb3.append(str);
        sb3.append(File.separator);
        return String.format("%s%s%s", new Object[]{sb3.toString(), sb2, format});
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public String getFolderPath(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(this.name);
        sb.append(File.separator);
        String sb2 = sb.toString();
        StringBuilder sb3 = new StringBuilder();
        sb3.append(str);
        sb3.append(File.separator);
        return String.format("%s%s", new Object[]{sb3.toString(), sb2});
    }

    public int getFps() {
        return this.fps;
    }

    public String getFrameNamePattern() {
        return String.format("%s%s%s", new Object[]{this.name, DevConstants.FRAME_SEQUENTIAL_POSTFIX, DevConstants.PNG_EXTENSION});
    }

    public int getFrameNum() {
        return this.frameNum;
    }

    public List<String> getFramePaths(String str) {
        ArrayList arrayList = new ArrayList(this.frameNum);
        for (int i = 0; i < this.frameNum; i++) {
            arrayList.add(getFramePath(str, i));
        }
        return arrayList;
    }

    public String getIconPath() {
        String folderPath = AssetTypeEnum.EFFECT_ICON.getFolderPath();
        StringBuilder sb = new StringBuilder();
        sb.append(this.name);
        sb.append(DevConstants.PNG_EXTENSION);
        return String.format("%s%s", new Object[]{folderPath, sb.toString()});
    }

    public String getName() {
        return this.name;
    }

    public String getZipPath() {
        return String.format("%s%s%s", new Object[]{AssetTypeEnum.EFFECT.getFolderPath(), this.name, DevConstants.ZIP_EXTENSION});
    }
}
