package com.example.cloudDisk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.cloudDisk.common.result.R;
import com.example.cloudDisk.common.result.ResultCode;
import com.example.cloudDisk.mapper.FileInfoMapper;
import com.example.cloudDisk.mapper.FolderFileInfoMapper;
import com.example.cloudDisk.mapper.FolderInfoMapper;
import com.example.cloudDisk.pojo.FileInfo;
import com.example.cloudDisk.pojo.FolderFileInfo;
import com.example.cloudDisk.pojo.FolderInfo;
import com.example.cloudDisk.service.FolderFileInfoService;
import com.example.cloudDisk.utils.Constants.Constant;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 成大事
 * @since 2022-07-01 11:54:54
 */
@Service
public class FolderFileInfoServiceImpl extends ServiceImpl<FolderFileInfoMapper, FolderFileInfo> implements FolderFileInfoService {

    @Resource
    private FolderFileInfoMapper folderFileInfoMapper;

    @Resource
    private FileInfoMapper fileInfoMapper;

    @Resource
    private FolderInfoMapper folderInfoMapper;

    /**
     * 根据文件夹id查询文件夹下的文件
     * @param folderId 文件夹id
     * @return               R
     */
    @Override
    public R<Object> getFolderFileByFolderId(String folderId) {
        Map<String, Object> map = new HashMap<>(2);
        try {
            List<FolderFileInfo> folderFileInfoList = folderFileInfoMapper.selectList(new QueryWrapper<FolderFileInfo>().eq("folder_pd", folderId));
            if (folderFileInfoList != null) {
                Map<Integer, List<FolderFileInfo>> collect = folderFileInfoList.stream().collect(Collectors.groupingBy(FolderFileInfo::getFolderFileType));
                List<String> folderIdList ;
                List<String> fileIdList;
                List<Map<String, Object>> fileMaps = null;
                List<Map<String, Object>> folderMaps = null;
                for(Integer key : collect.keySet()){
                    if(key == Constant.FOLDER){
                        folderIdList = collect.get(key).stream().map(FolderFileInfo::getFolderFileId).collect(Collectors.toList());
                        folderMaps = folderInfoMapper.selectJoinMaps(new MPJLambdaWrapper<>()
                                .select(FolderInfo::getFolderId,FolderInfo::getFolderName,FolderInfo::getFolderCreateTime,FolderInfo::getFolderTips)
                                .in(FolderInfo::getFolderId, folderIdList));
                    }
                    if(key == Constant.FILE){
                        fileIdList = collect.get(key).stream().map(FolderFileInfo::getFolderFileId).collect(Collectors.toList());
                        fileMaps = fileInfoMapper.selectJoinMaps(new MPJLambdaWrapper<>()
                                .select(FileInfo::getFileId,FileInfo::getFileUploadTime,FileInfo::getFileOthers,FileInfo::getFileName)
                                .in(FileInfo::getFileId, fileIdList));
                    }
                }
                map.put("fileList", fileMaps);
                map.put("folderList", folderMaps);
                return R.ok(map);
            }else {
                return R.error(ResultCode.NOT_EXIST.getCode(), ResultCode.NOT_EXIST.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return R.error();
        }
    }
}
