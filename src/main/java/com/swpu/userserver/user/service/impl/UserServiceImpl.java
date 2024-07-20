package com.swpu.userserver.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.swpu.userserver.user.dto.LoginUser;
import com.swpu.userserver.user.dto.QueryPageUser;
import com.swpu.userserver.user.entity.Permission;
import com.swpu.userserver.user.entity.User;
import com.swpu.userserver.user.mapper.UserMapper;
import com.swpu.userserver.user.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swpu.userserver.user.vo.UserPermTree;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * <p>
 *  服务实现类
 * </p>

 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    private UserMapper userMapper;
    @Override
    public User login(LoginUser loginUser) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        queryWrapper.eq("username", loginUser.getUsername()).eq("password", loginUser.getPassword());
        /*queryWrapper.and(new Consumer<QueryWrapper<User>>() {
            @Override
            public void accept(QueryWrapper<User> userQueryWrapper) {

            }
        });*/
        //lambda表达式：左边为方法参数名称，右边为方法的方法体
        queryWrapper.and(qw->{
           qw.eq("username", loginUser.getUsername()).or().eq("password", loginUser.getPassword());
        });
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public List<Permission> getPermissions(Integer userId) {
        return userMapper.getPermissions(userId);
    }

    @Override
    public List<UserPermTree> getUserPermTreeByUserId(Map<String, Object> param) {
        return userMapper.selectUserPermTreeByUserId(param);
    }

    @Override
    public Page<User> getPageUser(QueryPageUser queryPageUser) {
        //初始化page条件
        Page<User> page = new Page<>(queryPageUser.getPageNumber(),queryPageUser.getPageSize());
        //初始化条件
        QueryWrapper<User> w = new QueryWrapper<>();
        if(!ObjectUtils.isEmpty(queryPageUser.getUsername())){
            w.like("username", queryPageUser.getUsername());
        }

        //根据page对象进行分页查询
        return this.page(page,w);
    }
}
