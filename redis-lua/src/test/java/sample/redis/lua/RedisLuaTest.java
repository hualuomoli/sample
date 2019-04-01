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

    // redis-cli --eval test.lua _lock_test , 1 1000 id num ops incr decr '[{"id": "1234", "num": 20, "ops": "incr"}, {"id": "5678", "num": 10, "ops": "decr"}]'
    @Test
    public void testRedisLuaUseJsonDataAndFunction() throws IOException {
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
        dataList.add(new Data("name", 10L, "1"));
        dataList.add(new Data("age", 10L, "-1"));
        dataList.add(new Data("nickname", 10L, "-1"));
        dataList.add(new Data("salary", 10L, "1"));

        List<String> arguments = Lists.newArrayList();
        arguments.add("_lock_key");
        arguments.add(UUID.randomUUID().toString().replaceAll("[-]", ""));
        arguments.add("100");
        arguments.add("name");
        arguments.add("count");
        arguments.add("ops");
        arguments.add("1");
        arguments.add("-1");
        arguments.add(JSON.toJSONString(dataList));

        byte[][] keysAndArgs = arguments.stream().map(String::getBytes).collect(Collectors.toList())
                .toArray(new byte[][] {});

        byte[] result = redisTemplate.execute((RedisConnection conn) -> {
            return conn.eval(lua.getBytes(CHARSET), ReturnType.VALUE, 1, keysAndArgs);
        });
        logger.info("{}", new String(result));
    }

    public static class Data {
        private String name;
        private Long count;
        private String ops;

        public Data(String name, Long count, String ops) {
            this.name = name;
            this.count = count;
            this.ops = ops;
        }

        public String getName() {
            return name;
        }

        public Long getCount() {
            return count;
        }

        public String getOps() {
            return ops;
        }

        @Override
        public String toString() {
            return "Data [name=" + name + ", count=" + count + ", ops=" + ops + "]";
        }
    } // end class Data

}
