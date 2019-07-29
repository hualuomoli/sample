package sample.flink.model.trade;

/**
 * 交易状态
 */
public enum TradeStatus {

    /** 已创建 */
    CREATED,
    /** 已付款 */
    PAID__,
    /** 交易成功 */
    SUCCESS,
    /** 交易取消 */
    CANCEL_,
    /** 交易失败 */
    FAIL___;

}
