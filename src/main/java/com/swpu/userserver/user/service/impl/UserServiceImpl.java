package com.swpu.userserver.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.swpu.userserver.user.dto.UserDTO;
import com.swpu.userserver.user.dto.LoginUser;
import com.swpu.userserver.user.dto.QueryPageUser;
import com.swpu.userserver.user.entity.Permission;
import com.swpu.userserver.user.entity.User;
import com.swpu.userserver.user.mapper.UserMapper;
import com.swpu.userserver.user.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swpu.userserver.user.vo.UserPermTree;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  服务实现类
 * </p>

 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    private UserMapper userMapper;
    @Autowired
    private DefaultKaptcha defaultKaptcha;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
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

    /**
     * 1.将图片保存到本地目录下
     * 2.将图片目录映射为http的url路径
     * @param multipartFile
     * @return
     */
    @Override
    public String saveUserImg(MultipartFile multipartFile) {
        //将前端传递的图片保存在本地目录

        String descDir = "E:\\img";
        String fileName = multipartFile.getOriginalFilename();

        String filePath = descDir + File.separator + fileName;

        File descFile = new File(filePath);

        try {
            multipartFile.transferTo(descFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //拼接图片映射url路径
        return "http://localhost:8088/view/"+fileName;
    }

    @Override
    public boolean addUser(UserDTO userDTO) {
        User user = new User();
        //前提两个类中字段名一致，数量可以不一致
        BeanUtils.copyProperties(userDTO,user);
        return this.save(user);
    }

    @Override
    public boolean updateUser(UserDTO userDTO) {
        User user = new User();
        BeanUtils.copyProperties(userDTO,user);
        return this.updateById(user);
    }

    @Override
    public String getVcode() {
        //根据验证码文本生成图片
        String text = defaultKaptcha.createText();
        //验证码文本生成图片
        BufferedImage image = defaultKaptcha.createImage(text);
        //验证码文本存入redis
        stringRedisTemplate.opsForValue().set("vcode", text,15, TimeUnit.MINUTES);
        //验证码图片转换为Base64编码

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(image,"jpg",baos);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String code = "data:image/jpeg;base64,"+Base64Utils.encodeToString(baos.toByteArray());
        //将Base64编码返回
        return code;
    }

    @Override
    public boolean getUserVcoe(String vcode) {
        return Objects.equals(vcode, stringRedisTemplate.opsForValue().get("vcode"));
    }

    @Override
    public User getUserByUsername(String username) {
        return userMapper.selectOne(new QueryWrapper<User>().eq("username", username));
    }

    @Override
    public List<String> getPermListByUserId(Map<String, Object> param) {
        return userMapper.selectPermListByUserId(param);
    }
}
