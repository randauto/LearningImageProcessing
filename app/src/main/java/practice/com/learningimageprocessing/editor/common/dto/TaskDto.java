package practice.com.learningimageprocessing.editor.common.dto;

public class TaskDto {
    private Throwable error;

    public TaskDto() {
    }

    public TaskDto(Throwable th) {
        this.error = th;
    }

    public Throwable getError() {
        return this.error;
    }

    public boolean hasError() {
        return this.error != null;
    }

    public void setError(Throwable th) {
        this.error = th;
    }
}
