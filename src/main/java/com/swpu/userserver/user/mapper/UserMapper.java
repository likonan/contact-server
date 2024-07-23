package com.swpu.userserver.user.mapper;

import com.swpu.userserver.user.entity.Permission;
import com.swpu.userserver.user.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.swpu.userserver.user.vo.UserPermTree;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>

 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    List<Permission> getPermissions(Integer userId);

    List<UserPermTree> selectUserPermTreeByUserId(Map<String, Object> param);

    List<String> selectPermListByUserId(Map<String, Object> param);
}
