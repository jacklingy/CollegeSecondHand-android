package cn.edu.ncu.collegesecondhand.entity;

import android.net.Uri;

import java.io.Serializable;

/**
 * Created by ren lingyun on 2020/4/24 16:56
 */
public class ReleaseImage  implements Serializable {
    private String localPath;
    private Uri uri;
    private String uuid;//
    private String netPath;
    private int imageCount;//

    public ReleaseImage() {
    }

    public ReleaseImage(String localPath, Uri uri, String uuid, String netPath, int imageCount) {
        this.localPath = localPath;
        this.uri = uri;
        this.uuid = uuid;
        this.netPath = netPath;
        this.imageCount = imageCount;
    }

    public ReleaseImage(String netPath) {
        this.netPath = netPath;
    }


    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getNetPath() {
        return netPath;
    }

    public void setNetPath(String netPath) {
        this.netPath = netPath;
    }

    public int getImageCount() {
        return imageCount;
    }

    public void setImageCount(int imageCount) {
        this.imageCount = imageCount;
    }
}
