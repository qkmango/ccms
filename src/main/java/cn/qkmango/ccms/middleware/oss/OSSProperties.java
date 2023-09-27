package cn.qkmango.ccms.middleware.oss;

/**
 * OSS对象存储配置类
 * Minio 的对象存储
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-09-12 22:21
 */
public class OSSProperties {
    private String endpoint;
    private String accessKey;
    private String secretKey;
    private String bucket;

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }
}
