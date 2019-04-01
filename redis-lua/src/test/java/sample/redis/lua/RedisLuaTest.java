package sample.redis.lua;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;

@RunWith(value = SpringRunner.class)
@SpringBootTest
public class RedisLuaTest {

    private static final Logger logger = LoggerFactory.getLogger(RedisLuaTest.class);
    private static final Charset CHARSET = Charset.forName("UTF-8");

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private String projectPath;
    private String relativePath;

    @Before
    public void before() {
        String path = RedisLuaTest.class.getClassLoader().getResource(".").getPath();
        projectPath = path.substring(0, path.indexOf("/target"));
        relativePath = "/src/test/java/" + RedisLuaTest.class.getPackage().getName().replaceAll("[.]", "/");
    }

    @Test
    @Ignore
    public void test() {
        redisTemplate.opsForValue().set("name", "jack");
        String value = redisTemplate.opsForValue().get("name");
        logger.info("value is {}", value);
    }

    @Test
    @Ignore
    public void testRedisLuaUseJsonDataAndFunction() throws IOException {

        // set decrement data before test
        redisTemplate.delete(Lists.newArrayList("key1", "key2", "key3", "key4"));
        redisTemplate.opsForValue().set("key1", "10");
        redisTemplate.opsForValue().set("key4", "10");

        File file = new File(new File(projectPath, relativePath), "redis-lua-json-function.lua");
        List<String> lines = FileUtils.readLines(file, CHARSET).stream() //
                .filter(line -> !line.trim().startsWith("--")) //
                .map(line -> {
                    int index = line.indexOf(" --");
                    if (index == -1) {
                        return line;
                    }
                    return line.substring(0, index);
                })//
                .collect(Collectors.toList());
        final String lua = StringUtils.join(lines, "\n");

        List<Data> dataList = Lists.newArrayList();
        dataList.add(Data.decrement("key1", 6L));
        dataList.add(Data.increment("key2", 3L));
        dataList.add(Data.increment("key3", 16L));
        dataList.add(Data.decrement("key4", 8L));

        List<String> arguments = Lists.newArrayList();
        // lock key
        arguments.add("_lock_key");
        // data
        arguments.add(JSON.toJSONString(dataList));

        // data key number operator
        arguments.add("key");
        arguments.add("number");
        arguments.add("operator");

        // operator increment and decrement
        arguments.add("increment");
        arguments.add("decrement");

        // lock value and expire
        arguments.add(UUID.randomUUID().toString().replaceAll("[-]", ""));
        arguments.add("100");

        byte[][] keysAndArgs = arguments.stream().map(String::getBytes).collect(Collectors.toList())
                .toArray(new byte[][] {});

        byte[] result = redisTemplate.execute((RedisConnection conn) -> {
            return conn.eval(lua.getBytes(CHARSET), ReturnType.VALUE, 1, keysAndArgs);
        });
        logger.info("{}", new String(result));
    }

    @Test
    @Ignore
    public void testRedisLuaUseJsonDataAndFunctionWithDefaultLuaValue() throws IOException {

        // set decrement data before test
        redisTemplate.delete(Lists.newArrayList("key1", "key2", "key3", "key4"));
        redisTemplate.opsForValue().set("key1", "10");
        redisTemplate.opsForValue().set("key4", "10");

        File file = new File(new File(projectPath, relativePath), "redis-lua-json-function.lua");
        List<String> lines = FileUtils.readLines(file, CHARSET).stream() //
                .filter(line -> !line.trim().startsWith("--")) //
                .map(line -> {
                    int index = line.indexOf(" --");
                    if (index == -1) {
                        return line;
                    }
                    return line.substring(0, index);
                })//
                .collect(Collectors.toList());
        final String lua = StringUtils.join(lines, "\n");

        List<Data> dataList = Lists.newArrayList();
        dataList.add(Data.decrement("key1", 6L));
        dataList.add(Data.increment("key2", 3L));
        dataList.add(Data.increment("key3", 16L));
        dataList.add(Data.decrement("key4", 8L));

        List<String> arguments = Lists.newArrayList();
        // lock key
        arguments.add("_lock_key");
        // data
        arguments.add(JSON.toJSONString(dataList));

        byte[][] keysAndArgs = arguments.stream().map(String::getBytes).collect(Collectors.toList())
                .toArray(new byte[][] {});

        byte[] result = redisTemplate.execute((RedisConnection conn) -> {
            return conn.eval(lua.getBytes(CHARSET), ReturnType.VALUE, 1, keysAndArgs);
        });
        logger.info("{}", new String(result));
    }

    public static class Data {

        private static final String OPERATOR_INCREMENT = "increment";
        private static final String OPERATOR_DECREMENT = "decrement";

        /** 键 */
        private String key;
        /** 操作数量 */
        private Long number;
        /** 操作 */
        private String operator;

        public static Data increment(String key, Long number) {
            return new Data(key, number, OPERATOR_INCREMENT);
        }

        public static Data decrement(String key, Long number) {
            return new Data(key, number, OPERATOR_DECREMENT);
        }

        private Data(String key, Long number, String operator) {
            this.key = key;
            this.number = number;
            this.operator = operator;
        }

        public String getKey() {
            return key;
        }

        public Long getNumber() {
            return number;
        }

        public String getOperator() {
            return operator;
        }

        @Override
        public String toString() {
            return "Data [key=" + key + ", number=" + number + ", operator=" + operator + "]";
        }

    } // end class Data

}
