package org.redis;

import org.junit.jupiter.api.Test;
import org.redis.config.RedisConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Author: helltractor
 * @Date: 2024/4/7 14:20
 * @Description: 测试类
 */

// @SpringBootTest
// @ContextConfiguration(classes = {RedisConfiguration.class})
public class SpringDataRedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 测试RedisTemplate
     */
    @Test
    public void testRedisTemplate() {
        System.out.println(redisTemplate);
    }

    /**
     * 测试string
     * set get setex setnx getset
     */
    @Test
    public void testString() {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set("name", "helltractor");

        String name = (String) valueOperations.get("name");
        System.out.println(name);

        valueOperations.set("code", "1234", 3, TimeUnit.MINUTES);
        valueOperations.setIfAbsent("lock", "1");
        valueOperations.setIfAbsent("lock", "2");
        valueOperations.setIfPresent("lock", "3");
    }

    /**
     * 测试hash
     * put get keys delete values
     */
    @Test
    public void testHash(){
        HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();

        hashOperations.put("user", "name", "helltractor");
        hashOperations.put("user", "age", "18");

        String name = (String) hashOperations.get("user", "name");
        System.out.println(name);
        String age = (String) hashOperations.get("user", "age");
        System.out.println(age);

        Set keys = hashOperations.keys("user");
        System.out.println(keys);

        List values = hashOperations.values("user");
        System.out.println(values);

        hashOperations.delete("user", "name");
    }

    /**
     * 测试list
     * leftpush leftpushall rightpush leftpop rightpop size
     */
    @Test
    public void testList(){
        ListOperations<String, Object> listOperations = redisTemplate.opsForList();

        listOperations.leftPush("list", "4");
        listOperations.leftPushAll("list", "1", "2", "3");
        listOperations.rightPush("list", "5");

        List list = listOperations.range("list", 0, -1);
        System.out.println(list);

        listOperations.leftPop("list");
        listOperations.rightPop("list");

        Long size = listOperations.size("list");
        System.out.println(size);
    }

    /**
     * 测试set
     * add members size intersect union difference remove
     */
    @Test
    public void testSet(){
        SetOperations<String, Object> setOperations = redisTemplate.opsForSet();

        setOperations.add("set", "1");
        setOperations.add("set1", "2");
        setOperations.add("set2", "3", "4");

        Set members = setOperations.members("set");
        System.out.println(members);

        Long size = setOperations.size("set");
        System.out.println(size);

        Set intersect = setOperations.intersect("set", "set1");
        System.out.println(intersect);

        Set union = setOperations.union("set", "set1");
        System.out.println(union);

        Set difference = setOperations.difference("set", "set1");
        System.out.println(difference);

        setOperations.remove("set", "1");
    }

    /**
     * 测试zset
     * add range rangebyscore incrementscore remove
     */
    @Test
    public void testZSet(){
        ZSetOperations<String, Object> zSetOperations = redisTemplate.opsForZSet();

        zSetOperations.add("zset", "1", 10);
        zSetOperations.add("zset", "2", 2);
        zSetOperations.add("zset", "3", 3);

        Set range = zSetOperations.range("zset", 0, -1);
        System.out.println(range);

        Set rangeByScore = zSetOperations.rangeByScore("zset", 1, 2);
        System.out.println(rangeByScore);

        zSetOperations.incrementScore("zset", "2", 20);

        zSetOperations.remove("zset", "1");
    }

    /**
     * 通用指令操作
     * keys exists type del
     */
    @Test
    public void testCommon(){
        Set keys = redisTemplate.keys("*");
        System.out.println(keys);

        Boolean exists = redisTemplate.hasKey("name");
        System.out.println(exists);

        for (Object key : keys) {
            DataType type = redisTemplate.type(key);
            System.out.println(type.name());
        }

        String type = redisTemplate.type("name").code();
        System.out.println(type);

        redisTemplate.delete("name");
    }
}
