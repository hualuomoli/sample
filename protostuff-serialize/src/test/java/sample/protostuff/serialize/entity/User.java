package sample.protostuff.serialize.entity;

/**
 * 用户
 */
public class User {

    /** 用户名 */
    private String username;
    /** 昵称 */
    private String nickname;
    /** 用户类型 */
    private UserType userType;
    /** 居住地 */
    private Address address;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "User [username=" + username + ", nickname=" + nickname + ", userType=" + userType + ", address="
                + address + "]";
    }

}
