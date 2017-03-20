package com.yutian.common.net.download;

/**
 * @author guangleilei
 * @version 1.0 2016-12-13
 */
public interface UploadProgressListener {


    /**
     * @param progress     已经下载或上传字节数
     * @param total        总字节数
     */
    void onProgress(long progress, long total);


}
