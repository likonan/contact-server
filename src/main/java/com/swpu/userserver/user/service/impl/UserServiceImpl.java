package com.swpu.userserver.user.service.impl;

import com.swpu.userserver.user.entity.User;
import com.swpu.userserver.user.mapper.UserMapper;
import com.swpu.userserver.user.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>

 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
