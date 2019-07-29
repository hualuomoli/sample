package sample.flink.model.util;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.common.collect.Lists;

import sample.flink.model.login.LoginRecord;
import sample.flink.model.login.LoginStatus;

public class LoginUtils {

    private static final long BASE_SECONDS = 1546272000; // 2019-01-01 00:00:00
    private static final long EXCHANGE = 1000;
    private static final AtomicInteger ATOMIC = new AtomicInteger();

    /**
     * 创建一组数据
     * @param size 数据大小
     * @param users 用户
     * @param maxIncrementOffsetSeconds 最大递增偏移秒
     * @return 一组登陆记录
     */
    public static List<LoginRecord> create(int size, List<String> users, int maxIncrementOffsetSeconds) {
        List<LoginRecord> records = Lists.newArrayList();
        Random random = new Random();

        List<LoginStatus> statusList = Lists.newArrayList(LoginStatus.values());
        int offsetSeconds = 0;
        for (int i = 0; i < size; i++) {
            String username = users.get(random.nextInt(users.size()));
            LoginStatus loginStatus = statusList.get(random.nextInt(statusList.size()));
            offsetSeconds = RandomUtils.nextInt(1, maxIncrementOffsetSeconds) + offsetSeconds;
            records.add(create(username, loginStatus, offsetSeconds));
        }
        return records;
    }

    /**
     * 生成一条登陆记录
     * @param username 登陆用户
     * @param loginStatus 登陆状态
     * @param offsetSeconds 登陆偏移秒数
     * @return 登陆记录
     */
    public static LoginRecord create(String username, LoginStatus loginStatus, int offsetSeconds) {
        LoginRecord record = new LoginRecord();
        record.setId(ATOMIC.incrementAndGet());
        record.setUsername(username);
        record.setLoginStatus(loginStatus);
        record.setLoginTime((BASE_SECONDS + offsetSeconds) * EXCHANGE);
        return record;
    }

}
