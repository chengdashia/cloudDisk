package com.example.cloudDisk.common.minio;

import com.example.cloudDisk.common.result.exception.BaseException;
import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import io.minio.messages.DeleteObject;
import io.minio.messages.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static com.example.cloudDisk.common.result.project.ConstantEnum.SEPARATOR;


/**
 * @author 成大事
 * @since 2022/7/9 22:02
 */
@Slf4j
@Component
public class MinioUtil {

    private static MinioClient minioClient;

    private  static String endpoint;
    private static String bucketName;
    private  static String accessKey;
    private  static String secretKey;
    private  static Integer imgSize;
    private  static Integer fileSize;


    public MinioUtil() {
    }

    public MinioUtil(String endpoint, String bucketName, String accessKey, String secretKey, Integer imgSize, Integer fileSize) {
        MinioUtil.endpoint = endpoint;
        MinioUtil.bucketName = bucketName;
        MinioUtil.accessKey = accessKey;
        MinioUtil.secretKey = secretKey;
        MinioUtil.imgSize = imgSize;
        MinioUtil.fileSize = fileSize;
        createMinioClient();
    }

    /**
     * 创建基于Java端的MinioClient
     */
    public void createMinioClient() {
        try {
            if (null == minioClient) {
                log.info("开始创建 MinioClient...");
                minioClient = MinioClient
                        .builder()
                        .endpoint(endpoint)
                        .credentials(accessKey, secretKey)
                        .build();
                createBucket(bucketName);
                log.info("创建完毕 MinioClient...");
            }
        } catch (Exception e) {
            log.error("MinIO服务器异常：{}", e);
        }
    }


    /**
     * 获取上传文件前缀路径
     * @return  "http://10.111.43.55:9000/file/"
     */
    public String getBasisUrl() {
        return endpoint + SEPARATOR.getMsg() + bucketName + SEPARATOR.getMsg();
    }

    /**
     * 获取用户的路径
     */
    public String getUserUploadUrl(String uId,String objectName) {
        return uId + SEPARATOR.getMsg() + objectName;
    }

    /**
     * 用户的注册的时候，给一个根目录
     * @param uId               用户id
     * @param bucketName        桶名
     * @return         boolean
     */
    public boolean createRootUrl(String uId,String bucketName){
        log.info("bucketName:  "+bucketName);
        String folder = uId + SEPARATOR.getMsg();
        try {
            createFolder(bucketName, folder);
            return true;
        } catch (Exception e) {
            throw new BaseException(e.getMessage());
        }
    }

    /******************************  Operate Bucket Start  ******************************/

    /**
     * 启动SpringBoot容器的时候初始化Bucket
     * 如果没有Bucket则创建
     * @throws Exception  异常
     */
    private  void createBucket(String bucketName) throws Exception {
        if (!bucketExists(bucketName)) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }
    }

    /**
     *  判断Bucket是否存在，true：存在，false：不存在
     * @return  boolean
     * @throws Exception  异常
     */
    public  boolean bucketExists(String bucketName) throws Exception {
        return minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
    }


    /**
     * 获得Bucket的策略
     * @param bucketName 捅名
     * @return 桶的策略
     * @throws Exception   异常
     */
    public  String getBucketPolicy(String bucketName) throws Exception {
        return  minioClient
                .getBucketPolicy(
                        GetBucketPolicyArgs
                                .builder()
                                .bucket(bucketName)
                                .build());
    }


    /**
     * 获得所有Bucket列表
     * @return  所有的桶名
     * @throws Exception 异常
     */
    public  List<Bucket> getAllBuckets() throws Exception {
        return minioClient.listBuckets();
    }

    /**
     * 根据bucketName获取其相关信息
     * @param bucketName  桶名
     * @return 桶名其相关信息
     * @throws Exception 异常
     */
    public  Optional<Bucket> getBucket(String bucketName) throws Exception {
        return getAllBuckets().stream().filter(b -> b.name().equals(bucketName)).findFirst();
    }

    /**
     * 根据bucketName删除Bucket，true：删除成功； false：删除失败，文件或已不存在
     * @param bucketName  桶名
     * @throws Exception 异常
     */
    public  void removeBucket(String bucketName) throws Exception {
        minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
    }

    /******************************  Operate Bucket End  ******************************/


    /******************************  Operate Files Start  ******************************/

    /**
     * 判断文件是否存在
     * @param bucketName 存储桶
     * @param objectName 文件名
     * @return boolean
     */
    public  boolean isObjectExist(String bucketName, String objectName) {
        boolean exist = true;
        try {
            minioClient.statObject(StatObjectArgs.builder().bucket(bucketName).object(objectName).build());
        } catch (Exception e) {
            exist = false;
        }
        return exist;
    }

    /**
     * 判断文件夹是否存在
     * @param bucketName 存储桶
     * @param objectName 文件夹名称
     * @return
     */
    public  boolean isFolderExist(String bucketName, String objectName) {
        boolean exist = false;
        try {
            Iterable<Result<Item>> results = minioClient.listObjects(
                    ListObjectsArgs.builder().bucket(bucketName).prefix(objectName).recursive(false).build());
            for (Result<Item> result : results) {
                Item item = result.get();
                if (item.isDir() && objectName.equals(item.objectName())) {
                    exist = true;
                }
            }
        } catch (Exception e) {
            exist = false;
        }
        return exist;
    }

    /**
     * 根据文件前缀查询文件
     * @param bucketName 存储桶
     * @param prefix 前缀
     * @param recursive 是否使用递归查询
     * @return MinioItem 列表
     * @throws Exception 异常
     */
    public  List<Item> getAllObjectsByPrefix(String bucketName,
                                                   String prefix,
                                                   boolean recursive) throws Exception {
        List<Item> list = new ArrayList<>();
        Iterable<Result<Item>> objectsIterator = minioClient.listObjects(
                ListObjectsArgs.builder().bucket(bucketName).prefix(prefix).recursive(recursive).build());
        if (objectsIterator != null) {
            for (Result<Item> o : objectsIterator) {
                Item item = o.get();
                list.add(item);
            }
        }
        return list;
    }

    /**
     * 获取文件流
     * @param bucketName 存储桶
     * @param objectName 文件名
     * @return 二进制流
     */
    public  InputStream getObject(String bucketName, String objectName) throws Exception {
        return minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(objectName).build());
    }

    /**
     * 断点下载
     * @param bucketName 存储桶
     * @param objectName 文件名称
     * @param offset 起始字节的位置
     * @param length 要读取的长度
     * @return 二进制流
     */
    public InputStream getObject(String bucketName, String objectName, long offset, long length)throws Exception {
        return minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .offset(offset)
                        .length(length)
                        .build());
    }

    /**
     * 获取路径下文件列表
     * @param bucketName 存储桶
     * @param prefix 文件名称
     * @param recursive 是否递归查找，false：模拟文件夹结构查找
     * @return 二进制流
     */
    public  Iterable<Result<Item>> listObjects(String bucketName, String prefix,
                                                     boolean recursive) {
        return minioClient.listObjects(
                ListObjectsArgs.builder()
                        .bucket(bucketName)
                        .prefix(prefix)
                        .recursive(recursive)
                        .build());
    }

    /**
     * 使用MultipartFile进行文件上传
     * @param bucketName 存储桶
     * @param file 文件名
     * @throws Exception 异常
     */
    public  String
    uploadFile(String bucketName, MultipartFile file,String uId) throws Exception {

        String objectName = getUserUploadUrl(uId,file.getOriginalFilename());
        String contentType = file.getContentType();
        InputStream inputStream = file.getInputStream();
        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .contentType(contentType)
                        .stream(inputStream, inputStream.available(), -1)
                        .build());

        return getBasisUrl() + objectName;
    }

    /**
     * 上传本地文件
     * @param bucketName 存储桶
     * @param objectName 对象名称
     * @param fileName 本地文件路径
     */
    public  ObjectWriteResponse uploadFile(String bucketName, String objectName,
                                                 String fileName) throws Exception {
        return minioClient.uploadObject(
                UploadObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .filename(fileName)
                        .build());
    }

    /**
     * 通过流上传文件
     *
     * @param bucketName 存储桶
     * @param objectName 文件对象
     * @param inputStream 文件流
     */
    public  ObjectWriteResponse uploadFile(String bucketName, String objectName, InputStream inputStream) throws Exception {
        return minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .stream(inputStream, inputStream.available(), -1)
                        .build());
    }

    /**
     * 创建文件夹或目录
     * @param bucketName 存储桶
     * @param folderPath 目录路径
     */
    public  ObjectWriteResponse createFolder(String bucketName, String folderPath) throws Exception {
        if(!SEPARATOR.getMsg().equals(folderPath.substring(folderPath.length()-2))){
            folderPath += SEPARATOR.getMsg();
        }
        return minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object(folderPath)
                        .stream(new ByteArrayInputStream(new byte[]{}), 0, -1)
                        .build());
    }

    /**
     * 创建文件夹或目录
     * @param bucketName 存储桶
     * @param uId 用户的id
     */
    public ObjectWriteResponse createFolderByUserId(String uId,String bucketName) throws Exception {
        String folderPath = getFolderPath(uId);
        if(!isFolderExist(bucketName,folderPath)){
            return minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(folderPath)
                            .stream(new ByteArrayInputStream(new byte[]{}), 0, -1)
                            .build());
        }
        return null;
    }

    /**
     * 通过id 获取minio 的文件夹的路径
     * @param uId   用户的id
     * @return minio 的文件夹的路径
      */
    public String getFolderPath(String uId){
        return uId + SEPARATOR.getMsg();
    }

    /**
     * 获取文件信息, 如果抛出异常则说明文件不存在
     *
     * @param bucketName 存储桶
     * @param objectName 文件名称
     */
    public  String getFileStatusInfo(String bucketName, String objectName) throws Exception {
        return minioClient.statObject(
                StatObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .build()).toString();
    }

    /**
     * 拷贝文件
     *
     * @param bucketName 存储桶
     * @param objectName 文件名
     * @param srcBucketName 目标存储桶
     * @param srcObjectName 目标文件名
     */
    public  ObjectWriteResponse copyFile(String bucketName, String objectName,
                                               String srcBucketName, String srcObjectName) throws Exception {
        return minioClient.copyObject(
                CopyObjectArgs.builder()
                        .source(CopySource.builder().bucket(bucketName).object(objectName).build())
                        .bucket(srcBucketName)
                        .object(srcObjectName)
                        .build());
    }

    /**
     * 删除文件
     * @param bucketName 存储桶
     * @param objectName 文件名称
     */
    public  boolean removeFile(String bucketName, String objectName) {
        boolean isDelete = true;
        try{
            if(!isObjectExist(bucketName,objectName)){
                minioClient.removeObject(
                        RemoveObjectArgs.builder()
                                .bucket(bucketName)
                                .object(objectName)
                                .build());
            }
        }catch (Exception e){
            e.printStackTrace();
            isDelete = false;
        }
        return isDelete;

    }

    /**
     * 批量删除文件
     * @param bucketName 存储桶
     * @param keys 需要删除的文件列表
     */
    public  void removeFiles(String bucketName, List<String> keys) {
        List<DeleteObject> objects = new LinkedList<>();
        keys.forEach(s -> {
            objects.add(new DeleteObject(s));
            try {
                removeFile(bucketName, s);
            } catch (Exception e) {
                log.error("批量删除失败！error:{}",e);
            }
        });
    }

    /**
     * 获取文件外链
     * @param bucketName 存储桶
     * @param objectName 文件名
     * @param expires 过期时间 <=7 秒 （外链有效时间（单位：秒））
     * @return url
     * @throws Exception  异常
     */
    public  String getPresignedObjectUrl(String bucketName, String objectName, Integer expires) throws Exception {
        GetPresignedObjectUrlArgs args = GetPresignedObjectUrlArgs.builder().expiry(expires).bucket(bucketName).object(objectName).build();
        return minioClient.getPresignedObjectUrl(args);
    }

    /**
     * 获得文件外链
     * @param bucketName  存储桶
     * @param objectName  文件名称
     * @return url
     * @throws Exception  异常
     */
    public  String getPresignedObjectUrl(String bucketName, String objectName) throws Exception {
        GetPresignedObjectUrlArgs args = GetPresignedObjectUrlArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .method(Method.GET).build();
        return minioClient.getPresignedObjectUrl(args);
    }

    /**
     * 获得文件外链
     * @param uId   用户id
     * @param objectName   文件名称
     * @return url         文件的下载链接
     */
    public  String getFileUrl(String uId ,String objectName) {
        return getBasisUrl() + uId + SEPARATOR.getMsg() + objectName;
    }

    /**
     * 将URLDecoder编码转成UTF8
     * @param str url
     * @return   utf-8 的地址
     * @throws UnsupportedEncodingException  异常
     */
    public  String getUtf8ByUrlDecoder(String str) throws UnsupportedEncodingException {
        String url = str.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
        return URLDecoder.decode(url, "UTF-8");
    }

    /*****************************  Operate Files End  ******************************/

}
