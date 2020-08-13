package xin.marcher.framework.util;

import org.springframework.util.StreamUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 流临时存储对象
 *
 * @author marcher
 */
public class StreamStoreWrapper {

    /**
     * body数据
     */
    private final byte[] body;

    public StreamStoreWrapper(InputStream inputStream) throws IOException {
        this.body = StreamUtils.copyToByteArray(inputStream);
    }

    public InputStream getInputStream() {
        return new ByteArrayInputStream(body);
    }
}
