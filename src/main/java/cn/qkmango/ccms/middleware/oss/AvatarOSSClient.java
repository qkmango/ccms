package cn.qkmango.ccms.middleware.oss;

import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.PutObjectArgs;
import io.minio.errors.MinioException;
import io.minio.http.Method;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * 用户头像OSS
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-09-12 21:56
 */
public class AvatarOSSClient {
    private final String bucket;
    private final MinioClient client;
    private static final String PREFIX = "avatar/";
    private final Logger logger = Logger.getLogger(getClass());
    private final OSSProperties properties;

    public AvatarOSSClient(MinioClient client, String bucket, OSSProperties properties) {
        this.client = client;
        this.bucket = bucket;
        this.properties = properties;
    }

    /**
     * 上传文件
     */
    public String upload(MultipartFile file, String name) {
        // 2.通过Minio对象将图片上传到minio桶中
        // 使用putObject上传一个文件到存储桶中。
        String contentType = file.getContentType();
        PutObjectArgs objectArgs = null;
        try {
            objectArgs = PutObjectArgs.builder()
                    .object(PREFIX + name)
                    .bucket(bucket)
                    .contentType(contentType)
                    .stream(file.getInputStream(), file.getSize(), -1).build();
            ObjectWriteResponse response = client.putObject(objectArgs);
        } catch (MinioException | IOException | InvalidKeyException | NoSuchAlgorithmException e) {
            logger.error("头像文件上传到 OSS 失败");
            return null;
        }

        // 组装桶中文件的访问url
        return properties.getEndpoint() + "/" + OSSProperties.BUCKET_NAME + "/" + PREFIX + name;
    }

    /**
     * 获取文件URL
     */
    public String get(String name) {
        String url = null;
        try {
            url = client.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .bucket(bucket)
                            .object(this.absolutePath(name))
                            .method(Method.GET)
                            // .expiry()
                            .build()
            );
        } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException e) {
            logger.error("获取头像文件URL失败", e);
        }
        return url;
    }

    private String absolutePath(String name) {
        return properties.getEndpoint() + "/" + OSSProperties.BUCKET_NAME + "/" + PREFIX + name;
    }

}
