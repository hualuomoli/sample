package sample.protostuff.serialize.entity;

/**
 * 居住地址
 */
public class Address {

    /** 省 */
    private String province;
    /** 市 */
    private String city;
    /** 镇 */
    private String county;
    /** 街道 */
    private String district;
    /** 详细信息 */
    private String detailInfo;

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDetailInfo() {
        return detailInfo;
    }

    public void setDetailInfo(String detailInfo) {
        this.detailInfo = detailInfo;
    }

    @Override
    public String toString() {
        return "Address [province=" + province + ", city=" + city + ", county=" + county + ", district=" + district
                + ", detailInfo=" + detailInfo + "]";
    }

}
