package com.zkboys.sdk.httpjson;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;

public class HttpProgressRequestBody extends RequestBody {
    private RequestBody requestBody;

    private ProgressListener listener;

    interface ProgressListener {
        /**
         * 请求进度回调
         *
         * @param len  已完成进度
         * @param size 总大小
         */
        void onProgress(long len, long size);
    }

    public HttpProgressRequestBody(RequestBody requestBody, ProgressListener listener) {
        this.requestBody = requestBody;
        this.listener = listener;
    }

    @Override
    public MediaType contentType() {
        return requestBody.contentType();
    }

    @Override
    public void writeTo(BufferedSink bufferedSink) throws IOException {
        if (listener != null) {
            // 计算总长度
            Buffer buffer = new Buffer();
            requestBody.writeTo(buffer);
            long size = buffer.size();
            if (size == -1) {
                return;
            }

            // 然后一次写2048大小的内容
            int blockSize = 2048;
            long writeSize = 0;
            while (writeSize + blockSize < size) {
                buffer.copyTo(bufferedSink.buffer(), writeSize, blockSize);
                writeSize += blockSize;
                listener.onProgress(writeSize, size);
            }
            buffer.copyTo(bufferedSink.buffer(), writeSize, size - writeSize);
            bufferedSink.flush();
            listener.onProgress(writeSize, size);
            buffer.clear();
        } else {
            requestBody.writeTo(bufferedSink);
        }
    }
}
