package com.example.cloudDisk.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.cloudDisk.common.result.R;
import com.example.cloudDisk.mapper.FileHistoryMapper;
import com.example.cloudDisk.pojo.FileHistory;
import com.example.cloudDisk.pojo.FileInfo;
import com.example.cloudDisk.service.FileHistoryService;
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
 * @since 2022-07-01 11:54:54
 */
@Service
public class FileHistoryServiceImpl extends ServiceImpl<FileHistoryMapper, FileHistory> implements FileHistoryService {

    @Resource
    private FileHistoryMapper fileHistoryMapper;

    /**
     * 添加浏览记录 不使用redis
     * @param fileId   文件id
     * @return           R
     */
    @Override
    public R<Object> addMyFileHistory(String fileId) {
        String uId = (String) StpUtil.getLoginId();
        FileHistory fileHistory = new FileHistory();
        fileHistory.setHistoryId(IdUtil.fastUUID());
        fileHistory.setFileId(fileId);
        fileHistory.setUserId(uId);
        int insert = fileHistoryMapper.insert(fileHistory);
        if(insert > 0){
            return R.ok();
        }
        return R.error();
    }

    /**
     * 获取自己浏览的文件信息
     * @return  R
     */
    @Override
    public R<Object> getMyFileHistory() {
        String uId = (String) StpUtil.getLoginId();
        try {
            List<Map<String, Object>> maps = fileHistoryMapper.selectJoinMaps(new MPJLambdaWrapper<>()
                    .select(FileHistory::getHistoryId, FileHistory::getViewTime)
                    .select(FileInfo::getFileName, FileInfo::getFileType, FileInfo::getFileOthers)
                    .leftJoin(FileInfo.class, FileInfo::getFileId, FileHistory::getFileId)
                    .eq(FileHistory::getUserId, uId));
            if(maps != null){
                return R.ok(maps);

            }
            return R.error();
        } catch (Exception e) {
            e.printStackTrace();
            return R.error();
        }
    }

    /**
     * 删除自己浏览的文件信息
     * @return  R
     * @param historyId  文件id
     */
    @Override
    public R<Object> delMyFileHistory(String historyId) {
        String uId = (String) StpUtil.getLoginId();
        try {
            int remove = fileHistoryMapper.delete(new QueryWrapper<FileHistory>()
                    .eq("history_id", historyId)
                    .eq("user_id",uId));
            if (remove > 0){
                return  R.ok();
            }
            return R.error();
        } catch (Exception e) {
            e.printStackTrace();
            return R.error();
        }
    }
}
