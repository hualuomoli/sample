package sample.flink.cep.entity;

/** 数据状态 */
public enum RecordStatus {

    /** 成功 */
    SUCCESS,
    /** 错误 */
    ERROR__,
    /** 失败 */
    FAIL___;

    public static RecordStatus of(int index) {
        assert index > 0;
        RecordStatus[] values = RecordStatus.values();
        return values[index % values.length];
    }

}
