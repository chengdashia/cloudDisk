package com.example.cloudDisk.mapper;

import com.example.cloudDisk.pojo.FileInfo;
import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 成大事
 * @since 2022-07-01 11:54:54
 */
@Mapper
public interface FileInfoMapper extends MPJBaseMapper<FileInfo> {

    @Select("select file_path from file_info where file_id = #{fileId} and file_del = 0")
    FileInfo getFilePathByFileId(String fileId);


    @Delete("delete from file_info where file_id = #{fileId}")
    int delFileInfoByFileId(String fileId);

    @Update("UPDATE file_info set file_click_nums = file_click_nums + 1 where file_id = #{fileId}")
    int increaseClickNumByFileId(String fileId);


    @Update("UPDATE file_info set file_download_nums = file_download_nums + 1 where file_id = #{fileId}")
    int increaseDownLoadNumByFileId(String fileId);

    @Update("UPDATE file_info set file_del = 1 where file_id = #{fileId} and file_upload_id = #{id}")
    int recoveryFile(@RequestParam("file_upload_id") String id, @RequestParam("fileId") String fileId);

}
