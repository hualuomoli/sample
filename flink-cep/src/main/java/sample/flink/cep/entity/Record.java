package sample.flink.cep.entity;

public class Record {

    private static final long BASE = 1564118580; // 模拟基线时间

    /** ID */
    private Integer id;
    /** 用户名 */
    private String username;
    /** 状态,用于模拟过滤 */
    private RecordStatus status;
    /** 数据的真实时间 */
    private long emit;
    /** 数据的真实时间排异基线的秒数(测试时使用真实时间太长) */
    private int offset;

    public Record() {
    }

    public Record(Integer id, String username, RecordStatus status, int offset) {
        this.id = id;
        this.username = username;
        this.status = status;
        this.offset = offset;
        this.emit = (BASE + offset) * 1000;
    }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public RecordStatus getStatus() {
        return status;
    }

    public long getEmit() {
        return emit;
    }

    public int getOffset() {
        return offset;
    }

    @Override
    public String toString() {
        return "Record{" + "id=" + id + ", username='" + username + '\'' + ", status=" + status + ", offset=" + offset
                + '}';
    }

}
