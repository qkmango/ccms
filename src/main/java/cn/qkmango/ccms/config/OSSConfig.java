package cn.qkmango.ccms.config;

import cn.qkmango.ccms.middleware.oss.AvatarOSSClient;
import cn.qkmango.ccms.middleware.oss.OSSProperties;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.errors.MinioException;
import org.apache.log4j.Logger;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * OSS 客户端
 * Minio 客户端
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-09-12 21:28
 */
@Configuration
public class OSSConfig {
    private final Logger logger = Logger.getLogger(getClass());

    @Bean("OSSProperties")
    @ConfigurationProperties(prefix = "oss")
    public OSSProperties ossProperties() {
        return new OSSProperties();
    }

    @Bean("minioClient")
    public MinioClient minioClient(OSSProperties properties) {
        MinioClient minioClient = null;
        String bucket = properties.getBucket();
        try {
            minioClient = MinioClient.builder()
                    .endpoint(properties.getEndpoint())
                    .credentials(properties.getAccessKey(), properties.getSecretKey())
                    .build();
            boolean exist = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
            if (exist) {
                logger.info("Bucket '" + bucket + "' already exists.");
            } else {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
            }
        } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException e) {
            logger.error("Minio 客户端创建失败", e);
        }
        return minioClient;
    }

    @Bean("AvatarOSSClient")
    public AvatarOSSClient avatarOSSClient(MinioClient minioClient, OSSProperties properties) {
        return new AvatarOSSClient(minioClient, properties);
    }


}
