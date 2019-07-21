package sample.flink.cep.entity;

// 空气质量记录
public class AirQualityRecoder {

    private Integer id; // ID
    private String city; // 城市
    private Integer quality; // 空气质量
    private Long emmit; // 时间戳

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getQuality() {
        return quality;
    }

    public void setQuality(Integer quality) {
        this.quality = quality;
    }

    public Long getEmmit() {
        return emmit;
    }

    public void setEmmit(Long emmit) {
        this.emmit = emmit;
    }

    @Override
    public String toString() {
        return "AirQualityRecoder [id=" + id + ", city=" + city + ", quality=" + quality + ", emmit=" + emmit + "]";
    }

}