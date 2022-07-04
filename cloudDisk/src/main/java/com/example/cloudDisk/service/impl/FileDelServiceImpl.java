package com.example.cloudDisk.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.cloudDisk.common.result.R;
import com.example.cloudDisk.mapper.FileInfoMapper;
import com.example.cloudDisk.mapper.FolderFileInfoMapper;
import com.example.cloudDisk.pojo.FileDel;
import com.example.cloudDisk.mapper.FileDelMapper;
import com.example.cloudDisk.pojo.FileInfo;
import com.example.cloudDisk.pojo.FolderFileInfo;
import com.example.cloudDisk.service.FileDelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.cloudDisk.utils.hdfs.HdfsUtil;
import com.example.cloudDisk.utils.time.DateCalculateUtil;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
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
@Service
public class FileDelServiceImpl extends ServiceImpl<FileDelMapper, FileDel> implements FileDelService {

    @Resource
    private FileDelMapper fileDelMapper;

    @Resource
    private FileInfoMapper fileInfoMapper;

    @Resource
    private FolderFileInfoMapper folderFileInfoMapper;

    /**
     * 获取回收站  自己删除的文件信息
     * @return  R
     */
    @Override
    public R<Object> getRecycleMyFileList() {
        String uId = (String) StpUtil.getLoginId();
        try {
            List<Map<String, Object>> maps = fileDelMapper.selectJoinMaps(new MPJLambdaWrapper<>()
                    .select(FileDel::getFileId, FileDel::getFileDelTime)
                    .select(FileInfo::getFileName, FileInfo::getFileType)
                    .leftJoin(FileInfo.class, FileInfo::getFileId, FileDel::getFileId)
                    .eq(FileDel::getUserId, uId));
            if (maps != null) {
                for (Map<String, Object> m : maps) {
                    Date fileDelTime = (Date) m.get("fileDelTime");
                    String expirationTime = DateCalculateUtil.remainingExpirationTime(fileDelTime);
                    m.put("expirationTime", expirationTime);
                }
                return R.ok(maps);
            }else {
                return R.error();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return R.error();
        }

    }

    /**
     * 彻底删除回收站  自己删除的文件信息
     * @param fileId  文件id
     * @return  R
     */
    @Override
    public R<Object> delRecycleMyFile(String fileId) {
        FileInfo fileInfo = fileInfoMapper.getFilePathByFileId(fileId);
        if (fileInfo != null) {
            String filePath = fileInfo.getFilePath();
            int delete = fileInfoMapper.delFileInfoByFileId(fileId);
            if (delete == 1) {
                int delete1 = folderFileInfoMapper.delete(new QueryWrapper<FolderFileInfo>().eq("folder_file_id", fileId));
                if (delete1 == 1) {
                    HdfsUtil.deleteFileOrFolder(filePath);
                    return R.ok();
                }
                return R.error();
            }

        }
        return R.error();
    }

    /**
     * 从回收站恢复 自己删除的文件信息
     * @param fileId  文件id
     * @return  R
     */
    @Override
    public R<Object> recoveryMyFile(String fileId) {
        String uId = (String) StpUtil.getLoginId();
        int update = fileInfoMapper.recoveryFile(uId, fileId);
        if (update == 1){
            int delete = fileDelMapper.delete(new QueryWrapper<FileDel>()
                    .eq("file_id", fileId)
                    .eq("user_id", uId));
            if (delete == 1){
                return R.ok();
            }
        }
        return R.error();
    }
}
