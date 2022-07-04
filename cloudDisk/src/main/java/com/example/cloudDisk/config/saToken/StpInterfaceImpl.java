package com.example.cloudDisk.config.saToken;////package com.example.springboot.config.saToken;
//
//import cn.dev33.satoken.stp.StpInterface;
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.nongXingGang.pojo.User;
//import com.nongXingGang.service.UserService;
//import com.nongXingGang.utils.redis.RedisUtil;
//import com.nongXingGang.utils.safe.JWTUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
///**
// * 自定义权限验证接口扩展
// * @author 成大事
// * @since 2022/3/31 22:23
// */
//@Component    // 保证此类被SpringBoot扫描，完成Sa-Token的自定义权限验证扩展
//public class StpInterfaceImpl implements StpInterface {
//
//    private final UserService userService;
//    private final RedisUtil redisUtil;
//
//    @Autowired
//    public StpInterfaceImpl(UserService userService,RedisUtil redisUtil){
//        this.userService = userService;
//        this.redisUtil = redisUtil;
//    }
//
//    /**
//     * 返回一个账号所拥有的权限码集合
//     */
//    @Override
//    public List<String> getPermissionList(Object token, String loginType) {
//        return null;
//    }
//
//
//    /**
//     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
//     */
//    @Override
//    public List<String> getRoleList(Object token, String loginType) {
//        String uId = JWTUtil.getOpenid((String) token);
//        System.out.println(uId);
//        try{
//            String uIdRoles = uId + ":roles";
//            if(redisUtil.hasKey(uIdRoles)){
//                List<Object> objects = redisUtil.lGet(uIdRoles, 0, -1);
//                if(objects != null){
//                    return objects.stream().map(Object::toString).collect(Collectors.toList());
//                }
//            }else {
//                User user = userService.getOne(new QueryWrapper<User>()
//                        .select("u_status")
//                        .eq("u_id", uId));
//                if(user != null){
//                    List<String> list = new ArrayList<>();
//                    Integer uStatus = user.getUserStatus();
//                    while (uStatus >= -1){
//                        list.add(uStatus.toString());
//                        uStatus--;
//                    }
//                    System.out.println(list);
//                    redisUtil.lSet(uIdRoles,list);
//                    return list;
//                }
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return null;
//    }
//}
