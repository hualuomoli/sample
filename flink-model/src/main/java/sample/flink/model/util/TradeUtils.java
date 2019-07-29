package sample.flink.model.util;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.common.collect.Lists;

import sample.flink.model.trade.TradeRecord;
import sample.flink.model.trade.TradeStatus;

public class TradeUtils {

    private static final long BASE_SECONDS = 1546272000; // 2019-01-01 00:00:00
    private static final long EXCHANGE = 1000;
    private static final AtomicInteger ATOMIC = new AtomicInteger();

    /**
     * 创建一组数据
     * @param size 数据大小
     * @param users 用户
     * @param maxIncrementOffsetSeconds 最大递增偏移秒
     * @return 一组交易记录
     */
    public static List<TradeRecord> create(int size, List<String> users, int maxMoney, int maxIncrementOffsetSeconds) {
        List<TradeRecord> records = Lists.newArrayList();
        Random random = new Random();

        List<TradeStatus> statusList = Lists.newArrayList(TradeStatus.values());
        int offsetSeconds = 0;
        for (int i = 0; i < size; i++) {
            String username = users.get(random.nextInt(users.size()));
            TradeStatus tradeStatus = statusList.get(random.nextInt(statusList.size()));
            int tradeMoney = RandomUtils.nextInt(1, maxMoney);
            offsetSeconds = RandomUtils.nextInt(1, maxIncrementOffsetSeconds) + offsetSeconds;
            records.add(create(username, tradeStatus, tradeMoney, offsetSeconds));
        }
        return records;
    }

    /**
     * 创建交易记录
     * @param username 用户名
     * @param tradeStatus 交易状态
     * @param tradeMoney 交易金额
     * @param offsetSeconds 交易偏移秒数
     * @return 交易记录
     */
    public static TradeRecord create(String username, TradeStatus tradeStatus, int tradeMoney, int offsetSeconds) {
        TradeRecord record = new TradeRecord();
        record.setId(ATOMIC.incrementAndGet());
        record.setUsername(username);
        record.setTradeNo(String.valueOf(record.getId()));
        record.setTradeStatus(tradeStatus);
        record.setTradeMoney(tradeMoney);
        record.setTradeTime((BASE_SECONDS + offsetSeconds) * EXCHANGE);
        return record;
    }

}
