package sample.flink.model.trade;

import java.io.Serializable;

/**
 * 交易记录
 */
public class TradeRecord implements Serializable {

    private static final long serialVersionUID = 7110131710380925554L;

    /** ID */
    private Integer id;
    /** 用户名 */
    private String username;
    /** 交易编号 */
    private String tradeNo;
    /** 交易状态 */
    private TradeStatus tradeStatus;
    /** 交易金额 */
    private Integer tradeMoney;
    /** 交易时间 */
    private Long tradeTime;

    public TradeRecord() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public TradeStatus getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(TradeStatus tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    public Integer getTradeMoney() {
        return tradeMoney;
    }

    public void setTradeMoney(Integer tradeMoney) {
        this.tradeMoney = tradeMoney;
    }

    public Long getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(Long tradeTime) {
        this.tradeTime = tradeTime;
    }

    @Override
    public String toString() {
        return "TradeRecord [id=" + id + ", username=" + username + ", tradeNo=" + tradeNo + ", tradeStatus="
                + tradeStatus + ", tradeMoney=" + tradeMoney + ", tradeTime=" + tradeTime + "]";
    }

}
