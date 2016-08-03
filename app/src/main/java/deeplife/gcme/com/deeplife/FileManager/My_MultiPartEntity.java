package deeplife.gcme.com.deeplife.FileManager;

/**
 * Created by bengeos on 8/2/16.
 */

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import deeplife.gcme.com.deeplife.DeepLife;


public class My_MultiPartEntity extends MultipartEntity{
    private final ProgressListener listener;

    public My_MultiPartEntity(final ProgressListener listener) {
        super();
        this.listener = listener;
    }

    public My_MultiPartEntity(final HttpMultipartMode mode,
                              final ProgressListener listener) {
        super(mode);
        this.listener = listener;
    }

    public My_MultiPartEntity(HttpMultipartMode mode, final String boundary,
                              final Charset charset, final ProgressListener listener) {
        super(mode, boundary, charset);
        this.listener = listener;
    }
    @Override
    public void writeTo(OutputStream outstream) throws IOException {
        // TODO Auto-generated method stub
        super.writeTo(new CountingOutputStream(outstream, this.listener));
    }

    public static interface ProgressListener {
        void transferred(long num);
    }

    public static class CountingOutputStream extends FilterOutputStream {

        private final ProgressListener listener;
        private long transferred;

        public CountingOutputStream(final OutputStream out,
                                    final ProgressListener listener) {
            super(out);
            this.listener = listener;
            this.transferred = 0;
        }

        public void write(byte[] b, int off, int len) throws IOException {
            out.write(b, off, len);
            this.transferred += len;
            this.listener.transferred(this.transferred);
        }

        public void write(int b) throws IOException {
            out.write(b);
            this.transferred++;
            this.listener.transferred(this.transferred);
        }
    }




}

