package com.swpu.userserver.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
@Slf4j
@RestController
@RequestMapping("/redis")
public class RedisController {
    //    redis客户端提供的默认处理String类型的工具，只能处理String
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private RedisTemplate<Object,Object> redisTemplate;
    @GetMapping("/str")
    public Map<Object, Object> stringOpts() {
        //set方法两个参数永久保存，
        stringRedisTemplate.opsForValue().set("name", "张三");
        //带时间单位的保存方法，数据在redis中保存指定时间后自动清除
        stringRedisTemplate.opsForValue().set("age", "18",2, TimeUnit.MINUTES);
        String name = stringRedisTemplate.opsForValue().get("name");
        log.info("name:{}",name);
        //redis删除数据

        Boolean b = stringRedisTemplate.delete("name");
        log.info("删除结果:{}",b);

      //判断key是否存在
        Boolean b1 = stringRedisTemplate.hasKey("age");
        log.info("判断结果:{}",b1);

        redisTemplate.opsForValue().set("age1","18");
        Object age=redisTemplate.opsForValue().get("age1");
        log.info("age1:{}",age);
        return new HashMap<>();

    }

    @GetMapping("/list")
    public Map<String, Object> list() {
        //模拟栈操作入栈
        redisTemplate.delete("name");
        redisTemplate.opsForList().leftPush("name", "张三");
        redisTemplate.opsForList().leftPush("name", "李四");
        redisTemplate.opsForList().leftPush("name", "王五");
        redisTemplate.opsForList().set("name", 0, "李四");
        //出栈
//        String name = redisTemplate.opsForList().leftPop("name").toString();
        //模拟队列
        Object name1 = redisTemplate.opsForList().rightPop("name");
        log.info("name1:{}",name1);
        //练习rightPush 和rightpop
        redisTemplate.delete("name1");
        redisTemplate.opsForList().rightPush("name1", "张三");
        redisTemplate.opsForList().rightPush("name1", "李四");
        redisTemplate.opsForList().rightPush("name1", "王五");
        redisTemplate.opsForList().set("name1", 0, "李四");
        Object name2 = redisTemplate.opsForList().rightPop("name1");
        log.info("name2:{}",name2);
        //；练习Map set，get，delete，set
        return new HashMap<>();
    }
    @GetMapping("/map")
    public Map<String, Object> map() {
        //；练习Map put，get，delete，
        redisTemplate.opsForHash().put("information","name","张三");
        redisTemplate.opsForHash().put("information","age","19");
        redisTemplate.opsForHash().put("information","sex","男");
        redisTemplate.opsForHash().put("information","address","北京");
        redisTemplate.opsForHash().delete("information","name");
        Object age = redisTemplate.opsForHash().get("information", "age");
        log.info("age:{}",age);

        return new HashMap<>();

    }

}
