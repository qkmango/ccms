package cn.qkmango.ccms.middleware.oss;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.StatObjectArgs;
import io.minio.errors.MinioException;
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
    private static final String FOLDER = "avatar";
    private final Logger logger = Logger.getLogger(getClass());
    private final OSSProperties properties;
    private final String endpoint;
    // 获取对象时URL前缀 localhost:9000/ccms/avatar/
    private final String objectPreUrl;

    // 上传对象时路径的前缀 avatar/
    private final String uploadPrePath;

    public AvatarOSSClient(MinioClient client, OSSProperties properties) {
        this.client = client;
        this.properties = properties;
        this.endpoint = properties.getEndpoint();
        this.bucket = properties.getBucket();
        this.objectPreUrl = endpoint + "/" + bucket + "/" + FOLDER + "/";
        this.uploadPrePath = FOLDER + "/";
    }

    /**
     * 上传文件
     */
    public String upload(MultipartFile file, Integer account) {
        // 2.通过Minio对象将图片上传到minio桶中
        // 使用putObject上传一个文件到存储桶中。
        String contentType = file.getContentType();
        PutObjectArgs objectArgs = null;
        try {
            objectArgs = PutObjectArgs.builder()
                    .object(uploadPrePath + account + ".jpg")
                    .bucket(bucket)
                    .contentType(contentType)
                    .stream(file.getInputStream(), file.getSize(), -1).build();
            client.putObject(objectArgs);
        } catch (MinioException | IOException | InvalidKeyException | NoSuchAlgorithmException e) {
            logger.error("头像文件上传到 OSS 失败");
            return null;
        }

        // 组装桶中文件的访问url
        return this.get(account);
    }

    /**
     * 获取文件URL
     */
    public String get(Integer account) {
        // TODO 先判断文件是否存在，不存在返回null
        return "/oss/" + bucket + "/" + FOLDER + account + ".jpg";
    }

    public boolean exist(Integer account) {
        String name = "avatar/" + account + ".jpg";
        try {
            client.statObject(
                    StatObjectArgs.builder()
                            .bucket(bucket)
                            .object(name)
                            .build()
            );
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
