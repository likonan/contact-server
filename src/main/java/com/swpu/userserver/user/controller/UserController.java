package com.swpu.userserver.user.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.swpu.userserver.common.result.Result;
import com.swpu.userserver.config.JwtComponent;
import com.swpu.userserver.user.dto.UserDTO;
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
import org.springframework.web.multipart.MultipartFile;

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
    @Autowired
    private JwtComponent jwtComponent;
    @PostMapping("/login")
    public Result<?> login(@RequestBody LoginUser loginUser) {
        log.info("Login user: {}", loginUser);
        if(ObjectUtils.isEmpty(loginUser.getVcode())){
            return new Result<>().error("验证码为空");
        }else if(!userService.getUserVcoe(loginUser.getVcode())){
            return new Result<>().error("验证码错误");
        }
        User user = userService.login(loginUser);
        if (ObjectUtils.isEmpty(user)) {
            return new Result<>().error("用户名或密码错误");
        }else{
            //如果登录成功，生成jwt令牌
            String token = jwtComponent.sign(loginUser.getUsername(), user.getPassword());
            //返回结果的封装
            JSONObject obj = new JSONObject();
            obj.put("token", token);
            obj.put("userId", user.getId());

            return new Result<>().success("登陆成功").put(obj);
        }
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
    @PostMapping("/img")
    public Result<?> saveUserImg(@RequestParam("file") MultipartFile multipartFile){
        log.info("multipartFile:{}",multipartFile);
        String imgUrl = userService.saveUserImg(multipartFile);
        return new Result<>().success().put(imgUrl);
    }
    @PostMapping("/add")
    public Result<?> addUser(@RequestBody UserDTO userDTO){
        log.info("user:{}", userDTO);
        boolean b = userService.addUser(userDTO);
        return b?new Result<>().success("添加成功"):new Result<>().error("添加失败");
    }
    @PutMapping("/update")
    public Result<?> updateUser(@RequestBody UserDTO userDTO){
        boolean b = userService.updateUser(userDTO);
        return b?new Result<>().success("修改成功"):new Result<>().error("修改失败");
    }
    @DeleteMapping("/delete")
    public Result<?> deleteUser(Integer id){
        log.info("删除用户id:{}",id);
        boolean b = userService.removeById(id);
        return b?new Result<>().success("删除成功"):new Result<>().error("删除失败");
    }
    @GetMapping("/vcode")
    public Result<?> getVcode(){
        String vcode = userService.getVcode();
        return new Result<>().success().put(vcode);
    }
    @GetMapping("/permList")
    public Result<?> getPermListByUserId(Integer userId){
        log.info("用户userId:{}",userId);
        Map<String,Object> map = new HashMap<>();
        map.put("userId",userId);
        List<String> list = userService.getPermListByUserId(map);
        return new Result<>().success().put(list);
    }
}
