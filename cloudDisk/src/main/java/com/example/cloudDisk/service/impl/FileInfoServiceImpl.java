package com.example.cloudDisk.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.cloudDisk.common.result.R;
import com.example.cloudDisk.common.result.ResultCode;
import com.example.cloudDisk.mapper.*;
import com.example.cloudDisk.pojo.*;
import com.example.cloudDisk.service.FileInfoService;
import com.example.cloudDisk.utils.Constants.Constant;
import com.example.cloudDisk.utils.Constants.StatusType;
import com.example.cloudDisk.utils.file.FileTypeUtil;
import com.example.cloudDisk.utils.file.FileUtil;
import com.example.cloudDisk.utils.hdfs.HdfsUtil;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 成大事
 * @since 2022-07-01 11:54:54
 */
@Slf4j
@Service
public class FileInfoServiceImpl extends ServiceImpl<FileInfoMapper, FileInfo> implements FileInfoService {

    @Resource
    private FileLabelMapper fileLabelMapper;

    @Resource
    private FileDelMapper fileDelMapper;

    @Resource
    private FolderInfoMapper folderInfoMapper;

    @Resource
    private FolderFileInfoMapper folderFileInfoMapper;

    @Resource
    private HdfsUtil hdfsUtil;

    /**
     * 根据文件id获取文件信息
     * @param fileId  文件id
     * @return  R
     */
    @Override
    public R<Object> getFileInfoById(String fileId) {
        Map<String, Object> map = new HashMap<>(3);
        try {
            List<Map<String, Object>> fileInfoMaps = this.baseMapper.selectJoinMaps(new MPJLambdaWrapper<>()
                    .select(FileInfo.class, i -> !"file_info_id".equals(i.getColumn())
                            && !"file_folder_id".equals(i.getColumn())
                            && !"file_status".equals(i.getColumn())
                            && !"file_del".equals(i.getColumn()))
                    .select(UserInfo::getUserName, UserInfo::getUserAvatar)
                    .leftJoin(UserInfo.class, UserInfo::getUserId, FileInfo::getFileUploadId)
                    .eq(FileInfo::getFileId, fileId));

            List<Map<String, Object>> fileLabelMaps = fileLabelMapper.selectJoinMaps(new MPJLambdaWrapper<>()
                    .select(LabelInfo::getLabelName)
                    .leftJoin(LabelInfo.class, LabelInfo::getInterestLabelId, FileLabel::getFileLabelId)
                    .eq(FileLabel::getFileId, fileId));
            int i = this.baseMapper.increaseClickNumByFileId(fileId);
            if (i == 1) {
                map.put("data", fileInfoMaps);
                map.put("label", fileLabelMaps);
                map.put("downLoad",Constant.getDownLoadPath(fileInfoMaps.get(0).get("filePath").toString()));
                return R.ok(map);
            }
            return R.error();
        } catch (Exception e) {
            e.printStackTrace();
            return R.error();
        }
    }


    /**
     * 根据页数获取文件信息列表（一页20个）
     * @param page  页码
     * @return  R
     */
    @Override
    public R<Object> getFileInfoListByPage(int page) {

        IPage<Map<String, Object>> mapPage;
        try {
            mapPage = this.baseMapper.selectJoinMapsPage(new Page<>(page, 20),new MPJLambdaWrapper<>()
                            .select(FileInfo.class, i -> !"file_del".equals(i.getColumn())
                                    && !"file_path".equals(i.getColumn())
                                    && !"file_folder_id".equals(i.getColumn())
                                    && !"file_info_id".equals(i.getColumn()))
                            .select(FileInfo::getFileId)
                            .select(UserInfo::getUserName)
                            .leftJoin(UserInfo.class, UserInfo::getUserId, FileInfo::getFileUploadId)
                            .orderByAsc(FileInfo::getFileUploadTime)
                            .eq(FileInfo::getFileStatus, Constant.PUBLIC_TYPE));
            if (mapPage != null) {
                return R.ok(mapPage);
            }
            return R.error();
        } catch (Exception e) {
            e.printStackTrace();
            return R.error();
        }
    }

    /**
     * 获取点击量最高的前十个文件信息
     * @return  R
     */
    @Override
    public R<Object> getFileTopTen() {
        IPage<Map<String, Object>> mapPage;
        try {
            mapPage = this.baseMapper.selectJoinMapsPage(new Page<>(1, 10,false),
                    new MPJLambdaWrapper<>()
                            .select(FileInfo.class, i -> !"file_del".equals(i.getColumn())
                                    && !"file_path".equals(i.getColumn())
                                    && !"file_folder_id".equals(i.getColumn())
                                    && !"file_info_id".equals(i.getColumn())
                            )
                            .select(FileInfo::getFileId)
                            .select(UserInfo::getUserName)
                            .leftJoin(UserInfo.class, UserInfo::getUserId, FileInfo::getFileUploadId)
                            .orderByAsc(FileInfo::getFileClickNums)
                            .eq(FileInfo::getFileStatus, Constant.PUBLIC_TYPE));
            if (mapPage != null) {
              return R.ok(mapPage);
            }
            return R.error();
        } catch (Exception e) {
            e.printStackTrace();
            return R.error();
        }
    }


    /**
     * 随机获取十条文件信息
     * @return  R
     */
    @Override
    public R<Object> getFileRandomTen() {
        try {
            int count = Math.toIntExact(this.baseMapper.selectCount(new QueryWrapper<FileInfo>()
                    .select("1")
                    .eq("file_status",Constant.PUBLIC_TYPE)));
            int random = 0;
            int max = 10;
            if(count > max){
                random = RandomUtil.randomInt(0, count - 10) / 10;
                log.info("random{}",random );
            }

            Page<Map<String, Object>> mapPage = this.baseMapper.selectMapsPage(new Page<>(random, 10,false), new LambdaQueryWrapper<FileInfo>()
                            .select(FileInfo::getFileName,FileInfo::getFileId)
                            .eq(FileInfo::getFileType, Constant.PUBLIC_TYPE));
            if(mapPage != null){
                log.info("===================================================");
                return R.ok(mapPage);
            }
            return R.error(ResultCode.NOT_EXIST.getCode(), ResultCode.NOT_EXIST.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return R.error();
        }
    }

    /**
     * 上传文件
     * @param folderId  父文件夹id
     * @param file      文件
     * @param labelList 标签列表
     * @param fileName  文件名称
     * @param remarks   文件备注
     * @return R
     */
    @Override
    public R<Object> uploadFile(String folderId, MultipartFile file, List<String> labelList, String fileName, String remarks) {
        String uId = (String) StpUtil.getLoginId();
        //先保存到本地
        Map<String, Object> map = FileUtil.saveFile(file, uId);
        //保存成功！
        if (map.get("status").equals(StatusType.SUCCESS)) {
            FolderInfo folderInfo = folderInfoMapper.selectOne(new LambdaQueryWrapper<FolderInfo>()
                    .select(FolderInfo::getFolderUrl)
                    .eq(FolderInfo::getFolderId, folderId));
            //f父文件夹的路径
            String folderUrl = folderInfo.getFolderUrl();
            //上传到本地的文件
            File localFile = (File) map.get("filePath");

            //获取路径
            String filePath = localFile.getPath();
            //从本地上传到hdfs成功
            boolean upload = hdfsUtil.upload(filePath, folderUrl);
            if (upload) {
                //文件后缀
                String name = localFile.getName();
                String suffix = name.substring(name.lastIndexOf(".")+1);

                //拼接成hdfs 中的文件路径
                String hdfsFilePath = folderUrl + "/" +name;
                String newHdfsFilePath = folderUrl + "/" +fileName +"."+suffix;

                Integer type = FileTypeUtil.FILE_TYPE.get(suffix);

                //将名字改一下
                hdfsUtil.fileRename(hdfsFilePath,fileName);

                FileInfo fileInfo = new FileInfo();
                fileInfo.setFileInfoId(IdUtil.fastUUID());
                String fileId = IdUtil.simpleUUID();
                fileInfo.setFileId(fileId);
                fileInfo.setFilePath(newHdfsFilePath);
                fileInfo.setFileFolderId(folderId);
                fileInfo.setFileName(fileName);
                fileInfo.setFileType(type);
                fileInfo.setFileUploadId(uId);
                fileInfo.setFileOthers(remarks);
                int fileInsert = this.baseMapper.insert(fileInfo);
                //如果文件上传成功，插入文件表
                if (fileInsert == 1 ) {
                    FolderFileInfo folderFileInfo = new FolderFileInfo();
                    folderFileInfo.setFolderFileInfoId(IdUtil.fastUUID());
                    folderFileInfo.setFolderFileId(fileId);
                    folderFileInfo.setFolderFileType(Constant.FILE);
                    folderFileInfo.setFolderPd(folderId);
                    int folderFileInsert = folderFileInfoMapper.insert(folderFileInfo);
                    //如果文件夹文件表插入成功，插入folderFileInfo表
                    if (folderFileInsert == 1) {
                        //插入标签
                        if (labelList != null){
                            for (String s : labelList) {
                                FileLabel fileLabel = new FileLabel();
                                fileLabel.setFileLableId(IdUtil.fastUUID());
                                fileLabel.setFileLabelId(s);
                                fileLabel.setFileId(fileId);
                                fileLabelMapper.insert(fileLabel);
                            }
                        }
                        return R.ok();
                    }else {
                        return R.error();
                    }

                }
                else {
                    return R.error();
                }

            }
            else {
                return R.error();
            }
        }else {
            return R.error();
        }
    }


    /**
     * 逻辑 删除文件
     * @param fileId 文件id
     * @return R
     */
    @Override
    public R<Object> delFile(String fileId) {
        String uId = (String) StpUtil.getLoginId();
        int delete = this.baseMapper.delete(new LambdaUpdateWrapper<FileInfo>()
                .eq(FileInfo::getFileId, fileId));
        if (delete > 0) {
            FileDel fileDel = new FileDel();
            fileDel.setFileId(fileId);
            fileDel.setFileDelId(IdUtil.fastUUID());
            fileDel.setUserId(uId);
            int insert = fileDelMapper.insert(fileDel);
            if (insert > 0) {
                return R.ok();
            }
        }
        return R.error();
    }


    /**
     * 更改文件状态
     * @param fileId  文件id
     * @param fileStatus  文件id
     * @return          R
     */
    @Override
    public R<Object> updateFileStatus(String fileId, int fileStatus) {
        try {
            int update = this.baseMapper.update(null, new UpdateWrapper<FileInfo>()
                    .set("file_status", fileStatus)
                    .eq("file_id", fileId));
            if (update == 1){
                return R.ok();
            }
            return R.error();
        } catch (Exception e) {
            e.printStackTrace();
            return R.error();
        }
    }


    /**
     * 根据文件夹id查询文件
     * @param folderId  文件夹id
     * @return          R
     */
    @Override
    public R<Object> getFileInfoByFolderId(String folderId) {
        try {
            List<FileInfo> fileInfos = this.baseMapper.selectList(new QueryWrapper<FileInfo>()
                    .eq("file_folder_id", folderId));
            if (fileInfos != null){
                return R.ok(fileInfos);
            }
            return R.error();
        } catch (Exception e) {
            e.printStackTrace();
            return R.error();
        }
    }

    /**
     * 获取自己分享的文件
     * @return  R
     */
    @Override
    public R<Object> getFileInfoByShare() {
        String uId = (String) StpUtil.getLoginId();
        try {
            List<FileInfo> fileInfos = this.baseMapper.selectList(new QueryWrapper<FileInfo>()
                    .eq("file_upload_id", uId)
                    .eq("file_status", Constant.PUBLIC_TYPE));
            if(fileInfos != null){
                return R.ok(fileInfos);
            }
            return R.error(ResultCode.NOT_EXIST.getCode(), ResultCode.NOT_EXIST.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return R.error();
        }
    }

    /**
     * 下载文件
     * @param fileId 文件id
     * @return  R
     */
    @Override
    public R<Object> downloadFile(String fileId) {
        try {
            FileInfo fileInfo = this.baseMapper.selectOne(new QueryWrapper<FileInfo>().eq("file_id", fileId));
            if (fileInfo != null){
                fileInfo.setFileDownloadNums(fileInfo.getFileDownloadNums() + 1);
                this.baseMapper.updateById(fileInfo);
                return R.ok();
            }else {
                return R.error(ResultCode.NOT_EXIST.getCode(), ResultCode.NOT_EXIST.getMessage());
            }
        }catch (Exception e){
            e.printStackTrace();
            return R.error();
        }
    }

    /**
     * 更改文件名称
     * @param fileId 文件id
     * @param fileName 文件名称
     * @return  R
     */
    @Override
    public R<Object> updateFileName(String fileId, String fileName) {
        FileInfo fileInfo = this.baseMapper.selectOne(new QueryWrapper<FileInfo>().select("file_path").eq("file_id", fileId));
        if (fileInfo != null) {
            String filePath = fileInfo.getFilePath();
            String substring = filePath.substring(filePath.lastIndexOf("/")+1, filePath.lastIndexOf("."));
            String newFilePath = filePath.replace(substring,fileName);
            fileInfo.setFileId(fileId);
            fileInfo.setFileName(fileName);
            fileInfo.setFilePath(newFilePath);
            int i = this.baseMapper.updateById(fileInfo);
            if (i == 1) {
                hdfsUtil.fileRename(filePath,fileName);
                return R.ok();
            }
            return R.error();
        }
        return R.error(ResultCode.NOT_EXIST.getCode(), ResultCode.NOT_EXIST.getMessage());
    }

    /**
     * 更改文件备注
     * @param fileId 文件id
     * @param fileRemark 文件备注
     * @return  R
     */
    @Override
    public R<Object> updateFileRemark(String fileId, String fileRemark) {
        try {
            int update = this.baseMapper.update(null, new UpdateWrapper<FileInfo>()
                    .set("file_others", fileRemark)
                    .eq("file_id", fileId));
            if (update == 1){
                return R.ok();
            }
            return R.error();
        }catch (Exception e){
            e.printStackTrace();
            return R.error();
        }
    }


}
