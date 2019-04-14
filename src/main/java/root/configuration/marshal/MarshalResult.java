package root.configuration.marshal;

import java.io.File;

public class MarshalResult {
    private final String message;
    private final File file;

    public MarshalResult(String message) {
        this.message = message;
        this.file = null;
    }

    public MarshalResult(String message, File file) {
        this.message = message;
        this.file = file;
    }

    public String getMessage() {
        return message;
    }

    public File getFile() {
        return file;
    }

    @Override
    public String toString() {
        return "MarshalResult{" +
                "message='" + message + '\'' +
                ", file=" + file +
                '}';
    }
}
