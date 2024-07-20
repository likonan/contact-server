package com.swpu.userserver.user.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.swpu.userserver.common.result.Result;
import com.swpu.userserver.user.dto.LoginUser;
import com.swpu.userserver.user.dto.QueryPageUser;
import com.swpu.userserver.user.entity.Permission;
import com.swpu.userserver.user.entity.User;
import com.swpu.userserver.user.service.UserService;
import com.swpu.userserver.user.vo.UserPermTree;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author sdx2009
 * @since 2024-07-15
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/login")
    public Result<?> login(@RequestBody LoginUser loginUser) {
        log.info("Login user: {}", loginUser);
        User user = userService.login(loginUser);
        if (ObjectUtils.isEmpty(user)) {
            return new Result<>().error("用户名或密码错误");
        }else
            return new Result<>().success("登陆成功").put(user.getId());
    }
    @GetMapping("/perm")
    public Result<?> getPermissions(Integer userId){
        log.info("Get permission for user: {}", userId);
        List<Permission> permissions = userService.getPermissions(userId);
        if(ObjectUtils.isEmpty(permissions)){
            return new Result<>().error("该用户无任何权限");
        }else
            return new Result<>().success("查询成功").put(permissions);
    }
    @GetMapping("/menu")
    public Result<?> getMenuByUserId(Integer userId){
        log.info("Get menu for user: {}", userId);
        Map<String,Object> map = new HashMap<>();
        map.put("userId",userId);
        List<UserPermTree> list = userService.getUserPermTreeByUserId(map);

        return new Result<>().success().put(list);
    }
    @GetMapping("/info")
    public Result<?> getUserInfo(Integer userId){
        log.info("Get user info for user: {}", userId);
        User user = userService.getById(userId);
        JSONObject obj = new JSONObject();
        if (ObjectUtils.isEmpty(user)){
            return new Result<>().error();
        }else {
            obj.put("username",user.getUsername());
            // data中的数据格式：data{"username":"admin"}
            return new Result<>().success().put(obj);
        }
    }
    @GetMapping("/page")
    public Result<?> page(QueryPageUser queryPageUser){
        log.info("分页查询对象：{}",queryPageUser);
        Page<User> page = userService.getPageUser(queryPageUser);
        JSONObject obj = new JSONObject();
        obj.put("total",page.getTotal());
        obj.put("rows",page.getRecords());
        return new Result<>().success().put(obj);
    }

}
