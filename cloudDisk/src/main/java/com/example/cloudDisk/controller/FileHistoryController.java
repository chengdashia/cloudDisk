package com.example.cloudDisk.controller;


import com.example.cloudDisk.common.result.R;
import com.example.cloudDisk.service.FileHistoryService;
import com.example.cloudDisk.utils.redis.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 成大事
 * @since 2022-07-01 11:54:54
 */
@Api(tags = "浏览记录信息")
@RestController
@RequestMapping("/fileHistory")
@Validated
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FileHistoryController {

    private final FileHistoryService fileHistoryService;

    private final RedisUtil redisUtil;


    ///**
    // * 添加浏览记录
    // * @return  R
    // */
    //@ApiOperation("添加浏览记录")
    //@PostMapping("/addMyFileHistoryRedis")
    //public R addMyFileHistoryRedis(
    //        @ApiParam(value = "文件id",required = true) @NotNull @NotBlank(message = "文件Id 不能为空") @RequestParam("fileId") String fileId
    //){
    //
    //    String uuId = (String) StpUtil.getLoginId();
    //    String userHistoryList = Constant.getHistoryList(uuId);
    //    String userHistorySet = Constant.getHistorySet(uuId);
    //    if(redisUtil.hasKey(userHistoryList)){
    //        // 获取list中的数据的个数
    //        long listSize = redisUtil.lGetListSize(userHistorySet);
    //        //如果set已经没有这个。就插入。
    //        if(!redisUtil.sHasKey(userHistorySet,fileId)){
    //            //如果list里面的数量超过了100个。就删除一个。
    //            if(listSize >= 100){
    //                //list 中删除一个早的
    //                redisUtil.lRemove(userHistoryList,listSize - 2,listSize - 1);
    //                //list 中添加一个新的
    //                redisUtil.lPush(userHistoryList,fileId);
    //                //set中添加一个新的
    //                redisUtil.sSet(userHistorySet,fileId);
    //            }else {
    //                //list 中添加一个新的
    //                redisUtil.lPush(userHistoryList,fileId);
    //                //set中添加一个新的
    //                redisUtil.sSet(userHistorySet,fileId);
    //            }
    //        } else {
    //            //如果set已经有这个了。就不插入
    //            return R.ok();
    //        }
    //    }else {
    //        List<FileHistory> fileHistoryList = fileHistoryService.getBaseMapper().selectList(new QueryWrapper<FileHistory>()
    //                .select("file_id")
    //                .eq("user_id", uuId));
    //        List<String> collect = fileHistoryList.stream().map(FileHistory::getFileId).collect(Collectors.toList());
    //        for (String s : collect) {
    //            redisUtil.sSet(userHistorySet,s);
    //            redisUtil.lPush(userHistoryList,s);
    //        }
    //        // 获取list中的数据的个数
    //        long listSize = redisUtil.lGetListSize(userHistorySet);
    //        //如果set已经没有这个。就插入。
    //        if(!redisUtil.sHasKey(userHistorySet,fileId)){
    //            //如果list里面的数量超过了100个。就删除一个。
    //            if(listSize >= 100){
    //                //list 中删除一个早的
    //                redisUtil.lRemove(userHistoryList,listSize - 2,listSize - 1);
    //                //list 中添加一个新的
    //                redisUtil.lPush(userHistoryList,fileId);
    //                //set中添加一个新的
    //                redisUtil.sSet(userHistorySet,fileId);
    //            }else {
    //                //list 中添加一个新的
    //                redisUtil.lPush(userHistoryList,fileId);
    //                //set中添加一个新的
    //                redisUtil.sSet(userHistorySet,fileId);
    //            }
    //        } else {
    //            //如果set已经有这个了。就不插入
    //            return R.ok();
    //        }
    //    }
    //    return R.ok();
    //}


    /**
     * 添加浏览记录 不使用redis
     * @param fileId   文件id
     * @return           R
     */
    @ApiOperation("添加浏览记录 不使用redis")
    @PostMapping("/addMyFileHistory")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query",name="fileId",dataTypeClass = String.class,required=true,value="文件id")
    })
    public R<Object> addMyFileHistory(
            @NotNull @NotBlank(message = "文件Id 不能为空") @RequestParam("fileId") String fileId
    ){
        return fileHistoryService.addMyFileHistory(fileId);
    }

    /**
     * 获取自己浏览的文件信息
     * @return  R
     */
    @ApiOperation("获取自己浏览的文件信息")
    @PostMapping("/getMyFileHistory")
    public R<Object> getMyFileHistory(
    ){
        return fileHistoryService.getMyFileHistory();


    }



    /**
     * 删除自己浏览的文件信息
     * @return  R
     * @param historyId  文件id
     */
    @ApiOperation("删除自己浏览的文件信息")
    @PostMapping("/delMyFileHistory")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query",name="historyId",dataTypeClass = String.class,required=true,value="浏览记录的id")
    })
    public R<Object> delMyFileHistory(
            @RequestParam("historyId") @NotBlank(message = "文件的id") @NotNull String historyId
    ){

        return fileHistoryService.delMyFileHistory(historyId);
    }
}

