package sample.redis.lua;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;
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
    public void testLua() throws IOException {
        File file = new File(new File(projectPath, relativePath), "stock.lua");
        final String lua = FileUtils.readFileToString(file, CHARSET);
        logger.info("lua is \n{}", lua);

        String product = "product";
        Long success = redisTemplate.execute((RedisConnection conn) -> {
            return conn.eval(lua.getBytes(CHARSET), ReturnType.INTEGER, 2, product.getBytes(CHARSET));
        });
        logger.info("decr stock {}", success);
    }

}
