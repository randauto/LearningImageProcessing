package practice.com.learningimageprocessing.editor.common.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import practice.com.learningimageprocessing.editor.common.constants.FFMPEGConstants;
import practice.com.learningimageprocessing.editor.common.dto.FFMPEGCommand;
import practice.com.learningimageprocessing.editor.common.dto.FFMPEGCommand.FilterGraph;
import practice.com.learningimageprocessing.editor.common.enums.VideoTransitionEnum;

public class FFMPEGCommandUtils {
    public static final String BLEND_OVERLAY_EXP = "'B*(if(gte(T,%s),1,T/%s))+A*(1-(if(gte(T,%s),1,T/%s)))'";
    public static final String BLEND_UNCOVER_LEFT_EXP = "'if(gte(N*15*SW+X,W),B,A)'";

    public static String buildConvertVideoToGifCommand(String str, String str2) {
        FFMPEGCommand fFMPEGCommand = new FFMPEGCommand();
        fFMPEGCommand.input(str).pixelFormat(FFMPEGConstants.PIXEL_FORMAT_RGB24).loop(false).overwriteExistingOutput().output(str2);
        return fFMPEGCommand.getCommand();
    }

    public static String buildImageSequenceOverlayCommand(String str, String str2, String str3, int i, String str4) {
        FFMPEGCommand fFMPEGCommand = new FFMPEGCommand();
        FFMPEGCommand inputFrameRate = fFMPEGCommand.loop(true).input(str).inputFrameRate(i);
        StringBuilder sb = new StringBuilder();
        sb.append(str2);
        sb.append(File.separator);
        sb.append(str3);
        inputFrameRate.input(sb.toString()).filterComplex(new FilterGraph().overlayShortest()).overwriteExistingOutput().output(str4);
        return fFMPEGCommand.getCommand();
    }

    public static String buildSimpleSlideShowCommand(List<String> list, List<Float> list2, String str) {
        FFMPEGCommand fFMPEGCommand = new FFMPEGCommand();
        ArrayList arrayList = new ArrayList(list.size());
        for (int i = 0; i < list.size(); i++) {
            fFMPEGCommand.loop(true).time(((Float) list2.get(i)).floatValue()).input((String) list.get(i));
            StringBuilder sb = new StringBuilder();
            sb.append(i);
            sb.append(":v");
            arrayList.add(sb.toString());
        }
        FilterGraph filterGraph = new FilterGraph();
        filterGraph.concat((String[]) arrayList.toArray(new String[arrayList.size()]));
        fFMPEGCommand.filterComplex(filterGraph);
        fFMPEGCommand.output(str);
        return fFMPEGCommand.getCommand();
    }

    public static String buildSlideShowCommand(List<String> list, List<Float> list2, List<VideoTransitionEnum> list3, List<Float> list4, String str) {
        FilterGraph blend = new FilterGraph();
        StringBuilder sb = new StringBuilder();
        List<Float> list5 = list2;
        List<VideoTransitionEnum> list6 = list3;
        List<Float> list7 = list4;
        FFMPEGCommand fFMPEGCommand = new FFMPEGCommand();
        ArrayList arrayList = new ArrayList(list.size());
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (int i = 0; i < list.size(); i++) {
            fFMPEGCommand.loop(true).time(((Float) list5.get(i)).floatValue()).input((String) list.get(i));
            StringBuilder sb2 = new StringBuilder();
            sb2.append(i);
            sb2.append(":v");
            arrayList.add(sb2.toString());
        }
        FilterGraph filterGraph = new FilterGraph();
        for (int i2 = 0; i2 < list3.size(); i2++) {
            VideoTransitionEnum videoTransitionEnum = (VideoTransitionEnum) list6.get(i2);
            int i3 = i2 - 1;
            if (i2 == 0) {
                String str2 = "s0";
                linkedHashSet.add(str2);
                filterGraph.trim((String) arrayList.get(0), 0.0f, ((Float) list5.get(0)).floatValue()).setpts(FFMPEGConstants.TIMESTAMP_START);
                if (videoTransitionEnum == VideoTransitionEnum.FADE_IN) {
                    filterGraph.fade(FFMPEGConstants.FADE_TYPE_IN, 0.25f, ((Float) list7.get(i2)).floatValue());
                }
                filterGraph.asStream(str2);
            } else {
                if (!(videoTransitionEnum == VideoTransitionEnum.NONE || i2 == 0 || i2 == list3.size() - 1)) {
                    if (videoTransitionEnum == VideoTransitionEnum.CROSS_FADE) {
                        blend = filterGraph.blend((String) arrayList.get(i3), (String) arrayList.get(i2), getBlendOverlayExp(((Float) list7.get(i2)).floatValue()));
                        sb = new StringBuilder();
                    } else {
                        if (videoTransitionEnum != VideoTransitionEnum.UNCOVER_DOWN) {
                            if (videoTransitionEnum == VideoTransitionEnum.UNCOVER_LEFT) {
                                blend = filterGraph.blend((String) arrayList.get(i3), (String) arrayList.get(i2), getBlendUncoverLeftExp());
                                sb = new StringBuilder();
                            } else {
                                VideoTransitionEnum videoTransitionEnum2 = VideoTransitionEnum.UNCOVER_UP_LEFT;
                            }
                        }
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append("trans");
                        sb3.append(i2);
                        String sb4 = sb3.toString();
                        linkedHashSet.add(sb4);
                        StringBuilder sb5 = new StringBuilder();
                        sb5.append("transTemp");
                        sb5.append(i2);
                        filterGraph.trim(sb5.toString(), 0.0f, ((Float) list7.get(i2)).floatValue() + 1.0f).setpts(FFMPEGConstants.TIMESTAMP_START).asStream(sb4);
                    }
                    sb.append("transTemp");
                    sb.append(i2);
                    blend.asStream(sb.toString());
                    StringBuilder sb32 = new StringBuilder();
                    sb32.append("trans");
                    sb32.append(i2);
                    String sb42 = sb32.toString();
                    linkedHashSet.add(sb42);
                    StringBuilder sb52 = new StringBuilder();
                    sb52.append("transTemp");
                    sb52.append(i2);
                    filterGraph.trim(sb52.toString(), 0.0f, ((Float) list7.get(i2)).floatValue() + 1.0f).setpts(FFMPEGConstants.TIMESTAMP_START).asStream(sb42);
                }
                if (i2 < list3.size() - 1) {
                    StringBuilder sb6 = new StringBuilder();
                    sb6.append("s");
                    sb6.append(i2);
                    linkedHashSet.add(sb6.toString());
                    filterGraph.trim((String) arrayList.get(i2), 0.0f, ((Float) list5.get(i2)).floatValue()).setpts(FFMPEGConstants.TIMESTAMP_START);
                    if (i2 == list3.size() - 2 && list6.get(list3.size() - 1) == VideoTransitionEnum.FADE_OUT) {
                        filterGraph.fade(FFMPEGConstants.FADE_TYPE_OUT, (((Float) list5.get(i2)).floatValue() - ((Float) list7.get(list3.size() - 1)).floatValue()) - 0.25f, ((Float) list7.get(list3.size() - 1)).floatValue());
                    }
                    StringBuilder sb7 = new StringBuilder();
                    sb7.append("s");
                    sb7.append(i2);
                    filterGraph.asStream(sb7.toString());
                }
            }
        }
        filterGraph.concat((String[]) linkedHashSet.toArray(new String[linkedHashSet.size()])).asStream(FFMPEGConstants.FADE_TYPE_OUT);
        fFMPEGCommand.filterComplex(filterGraph);
        fFMPEGCommand.map(FFMPEGConstants.FADE_TYPE_OUT);
        fFMPEGCommand.pixelFormat(FFMPEGConstants.PIXEL_FORMAT_YUV420P);
        fFMPEGCommand.output(str);
        return fFMPEGCommand.getCommand();
    }

    public static String buildVideoColorKeyCommand(String str, String str2, String str3, String str4) {
        FFMPEGCommand fFMPEGCommand = new FFMPEGCommand();
        FilterGraph filterGraph = new FilterGraph();
        ArrayList arrayList = new ArrayList(2);
        arrayList.add("0:v");
        arrayList.add("1:v");
        fFMPEGCommand.input(str).input(str2).filterComplex(filterGraph.colorKey((String) arrayList.get(1), str3).asStream("ckout").overlay((String) arrayList.get(0), "ckout").asStream(FFMPEGConstants.FADE_TYPE_OUT)).map(FFMPEGConstants.FADE_TYPE_OUT).overwriteExistingOutput().output(str4);
        return fFMPEGCommand.getCommand();
    }

    public static String getBlendOverlayExp(float f) {
        return String.format(BLEND_OVERLAY_EXP, new Object[]{Float.valueOf(f), Float.valueOf(f), Float.valueOf(f), Float.valueOf(f)});
    }

    public static String getBlendUncoverLeftExp() {
        return String.format(BLEND_UNCOVER_LEFT_EXP, new Object[0]);
    }
}
