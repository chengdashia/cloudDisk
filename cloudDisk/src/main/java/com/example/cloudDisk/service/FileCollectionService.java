package com.example.cloudDisk.service;

import com.example.cloudDisk.common.result.R;
import com.example.cloudDisk.pojo.FileCollection;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 成大事
 * @since 2022-07-01 11:54:53
 */
@Transactional
public interface FileCollectionService extends IService<FileCollection> {

    /**
     * 文件收藏
     * @param fileId     文件id
     * @return           R
     */
    R<Object> fileCollection(String fileId);

    /**
     * 获取我收藏的文件
     * @return          R
     */
    R<Object> getMyCollectionFile();

    /**
     * 删除我的文件收藏
     * @param fileId     文件id
     * @return           R
     */
    R<Object> delMyFileCollection(String fileId);
}
