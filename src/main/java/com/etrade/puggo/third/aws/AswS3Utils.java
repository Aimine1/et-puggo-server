package com.etrade.puggo.third.aws;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.DeleteObjectsResult;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.etrade.puggo.common.exception.AwsException;
import com.etrade.puggo.common.exception.CommonError;
import com.etrade.puggo.utils.JsonUtils;
import java.io.File;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

/**
 * @author niuzhenyu
 * @description : Asw S3对象存储中间件
 * @date 2023/6/5 16:41
 **/
@Slf4j
@Service
@RefreshScope
public class AswS3Utils {

    @Value("${aws.S3.bucket-name:}")
    private String bucketName;

    @Value("${aws.S3.base-uri:}")
    private String baseUri;

    private static final Regions AP_SOUTHEAST_1 = Regions.AP_SOUTHEAST_1;

    /**
     * 上传对象到S3
     *
     * @param file 本地文件
     * @param key  对象key
     */
    public S3PutObjectResult upload(File file, String key) {
        log.info("[AswS3Utils] Uploading {} to S3 bucket {}...\n", key, bucketName);

        AmazonS3 s3 = getAmazonS3Client();
        try {
            PutObjectResult putObjectResult = s3.putObject(bucketName, key, file);
            log.info("[AswS3Utils] Upload Result: {}", JsonUtils.toJson(putObjectResult));
            return S3PutObjectResult.builder()
                .url(baseUri + key)
                .key(key)
                .versionId(putObjectResult.getVersionId())
                .build();
        } catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.
            log.error("[AswS3Utils] Upload Error", e);
            throw new AwsException(CommonError.S3_UPLOAD_ERROR);
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            log.error("[AswS3Utils] Upload Error", e);
            throw new AwsException(CommonError.S3_UPLOAD_ERROR);
        }
    }


    /**
     * 暂时删除对象
     *
     * @param objectKeys 对象key列表
     */
    public void remove(String... objectKeys) {
        log.info("[AswS3Utils] Remove {} from S3 bucket {}...\n", objectKeys, bucketName);

        AmazonS3 s3 = getAmazonS3Client();
        try {
            DeleteObjectsRequest dor = new DeleteObjectsRequest(bucketName).withKeys(objectKeys);
            DeleteObjectsResult deleteObjectsResult = s3.deleteObjects(dor);
            log.info("[AswS3Utils] Remove Result: {}", deleteObjectsResult);
        } catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.
            log.error("[AswS3Utils] Remove Error", e);
            throw new AwsException(CommonError.S3_REMOVE_ERROR);
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            log.error("[AswS3Utils] Remove Error", e);
            throw new AwsException(CommonError.S3_REMOVE_ERROR);
        }
    }


    /**
     * 对象列表
     */
    public void list() {
        try {
            AmazonS3 s3Client = getAmazonS3Client();

            // Get a list of objects in the bucket, two at a time, and
            // print the name and size of each object.
            ListObjectsRequest listRequest = new ListObjectsRequest().withBucketName(bucketName).withMaxKeys(2);
            ObjectListing objects = s3Client.listObjects(listRequest);
            while (true) {
                List<S3ObjectSummary> summaries = objects.getObjectSummaries();
                for (S3ObjectSummary summary : summaries) {
                    System.out.printf("Object \"%s\" retrieved with size %d\n", summary.getKey(), summary.getSize());
                }
                if (objects.isTruncated()) {
                    objects = s3Client.listNextBatchOfObjects(objects);
                } else {
                    break;
                }
            }
        } catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.
            log.error("[AswS3Utils] list Error", e);
            throw new AwsException(CommonError.S3_LIST_ERROR);
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            log.error("[AswS3Utils] list Error", e);
            throw new AwsException(CommonError.S3_LIST_ERROR);
        }
    }


    /**
     * 检查是否存在
     *
     * @param key 文件key
     * @return true or false
     */
    public boolean isExists(String key) {
        AmazonS3 s3 = getAmazonS3Client();
        try {
            return s3.doesObjectExist(bucketName, key);
        } catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.
            log.error("[AswS3Utils] Check If Exists Error", e);
            throw new AwsException(CommonError.S3_OPERATE_ERROR);
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            log.error("[AswS3Utils] Check If Exists Error", e);
            throw new AwsException(CommonError.S3_OPERATE_ERROR);
        }
    }

    private AmazonS3 getAmazonS3Client() {
        return AmazonS3ClientBuilder.standard()
            .withCredentials(new ProfileCredentialsProvider())
            .withRegion(AP_SOUTHEAST_1)
            .build();
    }

}

