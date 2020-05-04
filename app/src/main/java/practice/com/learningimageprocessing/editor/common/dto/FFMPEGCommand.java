package practice.com.learningimageprocessing.editor.common.dto;

import com.facebook.samples.ads.debugsettings.DebugSettings;
import com.photo.effect.editor.common.constants.FFMPEGConstants;
import java.util.logging.Logger;

public class FFMPEGCommand {
    private StringBuilder command = new StringBuilder();

    public static class FilterGraph {
        private StringBuilder expression = new StringBuilder();

        private void appendFilter(String str) {
            if (!this.expression.toString().isEmpty()) {
                this.expression.append(FFMPEGConstants.FILTER_SPLITTER);
            }
            this.expression.append(str);
        }

        private void appendFilterChain(String str) {
            if (!this.expression.toString().isEmpty()) {
                this.expression.append(FFMPEGConstants.FILTER_CHAIN_SPLITTER);
            }
            this.expression.append(str);
        }

        public FilterGraph asStream(String str) {
            this.expression.append(FFMPEGCommand.getStream(str));
            return this;
        }

        public FilterGraph blend(String str, String str2, String str3) {
            appendFilterChain(String.format("%s%sblend=all_expr=%s", new Object[]{FFMPEGCommand.getStream(str), FFMPEGCommand.getStream(str2), str3}));
            return this;
        }

        public FilterGraph colorKey(String str, String str2) {
            appendFilter(String.format("%scolorkey=0x%s:0.3:0.2", new Object[]{FFMPEGCommand.getStream(str), str2}));
            return this;
        }

        public FilterGraph concat(String... strArr) {
            String str = "";
            for (String str2 : strArr) {
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append(FFMPEGCommand.getStream(str2));
                str = sb.toString();
            }
            appendFilterChain(String.format("%sconcat=n=%s", new Object[]{str, Integer.valueOf(strArr.length)}));
            return this;
        }

        public FilterGraph fade(String str, float f, float f2) {
            appendFilter(String.format("fade=t=%s:st=%s:d=%s", new Object[]{str, Float.valueOf(f), Float.valueOf(f2)}));
            return this;
        }

        public String getExpression() {
            return this.expression.toString();
        }

        public FilterGraph overlay(String str, String str2) {
            appendFilterChain(String.format("%s%soverlay", new Object[]{FFMPEGCommand.getStream(str), FFMPEGCommand.getStream(str2)}));
            return this;
        }

        public FilterGraph overlayShortest() {
            appendFilterChain("overlay=shortest=1");
            return this;
        }

        public FilterGraph setpts(String str) {
            appendFilter(String.format("setpts=%s", new Object[]{str}));
            return this;
        }

        public FilterGraph trim(String str, float f, float f2) {
            appendFilterChain(String.format("%strim=start=%s:duration=%s", new Object[]{FFMPEGCommand.getStream(str), Float.valueOf(f), Float.valueOf(f2)}));
            return this;
        }
    }

    private void appendCommand(String str) {
        StringBuilder sb = this.command;
        sb.append(FFMPEGConstants.COMMAND_SPLITTER);
        sb.append(str);
    }

    /* access modifiers changed from: private */
    public static String getStream(String str) {
        return String.format("[%s]", new Object[]{str});
    }

    public FFMPEGCommand filterComplex(FilterGraph filterGraph) {
        appendCommand(String.format(FFMPEGConstants.FILTER_COMPLEX_COMMAND, new Object[]{filterGraph.getExpression()}));
        return this;
    }

    public String getCommand() {
        String trim = this.command.toString().trim();
        Logger.getLogger("FFMPEGCommand").info(trim);
        return trim;
    }

    public FFMPEGCommand input(String str) {
        appendCommand(String.format(FFMPEGConstants.INPUT_COMMAND, new Object[]{str}));
        return this;
    }

    public FFMPEGCommand inputFrameRate(int i) {
        appendCommand(String.format("-framerate %s", new Object[]{Integer.valueOf(i)}));
        return this;
    }

    public FFMPEGCommand loop(boolean z) {
        String str = "-loop %s";
        Object[] objArr = new Object[1];
        objArr[0] = z ? "1" : DebugSettings.OTHER_BID_DEFAULT;
        appendCommand(String.format(str, objArr));
        return this;
    }

    public FFMPEGCommand map(String str) {
        appendCommand(String.format("-map %s", new Object[]{getStream(str)}));
        return this;
    }

    public FFMPEGCommand output(String str) {
        appendCommand(String.format("%s", new Object[]{str}));
        return this;
    }

    public FFMPEGCommand outputFrameRate(int i) {
        appendCommand(String.format("-r %s", new Object[]{Integer.valueOf(i)}));
        return this;
    }

    public FFMPEGCommand overwriteExistingOutput() {
        appendCommand(FFMPEGConstants.OVERWRITE_EXISTING_OUTPUT);
        return this;
    }

    public FFMPEGCommand pixelFormat(String str) {
        appendCommand(String.format("-pix_fmt %s", new Object[]{str}));
        return this;
    }

    public FFMPEGCommand selectCodec(String str, String str2) {
        appendCommand(String.format("-c:%s %s", new Object[]{str, str2}));
        return this;
    }

    public FFMPEGCommand selectCodec(String str, String str2, String str3) {
        appendCommand(String.format("-c:%s:%s %s", new Object[]{str, str2, str3}));
        return this;
    }

    public FFMPEGCommand time(float f) {
        appendCommand(String.format("-t %s", new Object[]{Float.valueOf(f)}));
        return this;
    }
}
