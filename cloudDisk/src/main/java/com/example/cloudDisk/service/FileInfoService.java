package com.example.cloudDisk.service;

import com.example.cloudDisk.common.result.R;
import com.example.cloudDisk.pojo.FileInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 成大事
 * @since 2022-07-01 11:54:54
 */
@Transactional
public interface FileInfoService extends IService<FileInfo> {

    /**
     * 根据文件id获取文件信息
     * @param fileId  文件id
     * @return  R
     */
    R<Object> getFileInfoById(String fileId);


    /**
     * 根据页数获取文件信息列表（一页20个）
     * @param page  页码
     * @return  R
     */
    R<Object> getFileInfoListByPage(int page);

    /**
     * 获取点击量最高的前十个文件信息
     * @return  R
     */
    R<Object> getFileTopTen();



    /**
     * 随机获取十条文件信息
     * @return  R
     */
    R<Object> getFileRandomTen();

    /**
     * 上传文件
     * @param folderId  父文件夹id
     * @param file      文件
     * @param labelList 标签列表
     * @param fileName  文件名称
     * @param remarks   文件备注
     * @return R
     */
    R<Object> uploadFile(String folderId, MultipartFile file, List<String> labelList, String fileName, String remarks);

    /**
     * 逻辑 删除文件
     *
     * @param fileId 文件id
     * @return R
     */
    R<Object> delFile(String fileId);


    /**
     * 更改文件状态
     * @param fileId  文件id
     * @param fileStatus  文件id
     * @return          R
     */
    R<Object> updateFileStatus(String fileId, int fileStatus);


    /**
     * 根据文件夹id查询文件
     * @param folderId  文件夹id
     * @return          R
     */
    R<Object> getFileInfoByFolderId(String folderId);


    /**
     * 获取自己分享的文件
     * @return  R
     */
    R<Object> getFileInfoByShare();


    /**
     * 下载文件
     * @param fileId 文件id
     * @return  R
     */
    R<Object> downloadFile(String fileId);

    /**
     * 更改文件名称
     * @param fileId 文件id
     * @param fileName 文件名称
     * @return  R
     */
    R<Object> updateFileName(String fileId, String fileName);

    /**
     * 更改文件备注
     * @param fileId 文件id
     * @param fileRemark 文件备注
     * @return  R
     */
    R<Object> updateFileRemark(String fileId, String fileRemark);


}
