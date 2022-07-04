package com.example.cloudDisk.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.cloudDisk.common.result.R;
import com.example.cloudDisk.common.result.ResultCode;
import com.example.cloudDisk.pojo.FileCollection;
import com.example.cloudDisk.mapper.FileCollectionMapper;
import com.example.cloudDisk.pojo.FileInfo;
import com.example.cloudDisk.service.FileCollectionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 成大事
 * @since 2022-07-01 11:54:53
 */
@Service
public class FileCollectionServiceImpl extends ServiceImpl<FileCollectionMapper, FileCollection> implements FileCollectionService {

    @Resource
    private FileCollectionMapper fileCollectionMapper;


    /**
     * 文件收藏
     * @param fileId     文件id
     * @return           R
     */
    @Override
    public R<Object> fileCollection(String fileId) {
        String uId = (String) StpUtil.getLoginId();
        try {
            // 查询是否已经收藏
            FileCollection one = fileCollectionMapper.selectOne(new QueryWrapper<FileCollection>()
                    .eq("file_id", fileId)
                    .eq("user_id", uId));
            // 还没有收藏
            if (one == null) {
                one = new FileCollection();
                one.setCollectionId(IdUtil.simpleUUID());
                one.setFileId(fileId);
                one.setUserId(uId);
                // 收藏
                int save = fileCollectionMapper.insert(one);
                if (save == 1) {
                    // 收藏成功
                    return R.ok();
                }
                // 收藏失败
                return R.error();

            } else {
                // 已经收藏
                return R.error(ResultCode.COLLECTION_EXIST.getCode(), ResultCode.COLLECTION_EXIST.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return R.error();
        }
    }


    /**
     * 获取我收藏的文件
     * @return          R
     */
    @Override
    public R<Object> getMyCollectionFile() {
        String uId = (String) StpUtil.getLoginId();
        try {
            List<Map<String, Object>> maps = fileCollectionMapper.selectJoinMaps(new MPJLambdaWrapper<>()
                    .select(FileCollection::getFileId, FileCollection::getCollectionTime)
                    .select(FileInfo::getFileName, FileInfo::getFileType, FileInfo::getFileOthers)
                    .leftJoin(FileInfo.class, FileInfo::getFileId, FileCollection::getFileId)
                    .eq(FileCollection::getUserId, uId));

            return R.ok(maps);
        } catch (Exception e) {
            e.printStackTrace();
           return R.error();
        }
    }

    /**
     * 删除我的文件收藏
     * @param fileId     文件id
     * @return           R
     */
    @Override
    public R<Object> delMyFileCollection(String fileId) {
        String uId = (String) StpUtil.getLoginId();
        try {
            int remove = fileCollectionMapper.delete(new QueryWrapper<FileCollection>()
                    .eq("file_id", fileId)
                    .eq("user_id", uId));
            if (remove == 1) {
                return R.ok();
            }
            return R.error();
        } catch (Exception e) {
            e.printStackTrace();
            return R.error();
        }
    }
}
