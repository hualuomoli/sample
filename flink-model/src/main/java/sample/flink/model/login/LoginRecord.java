package sample.flink.model.login;

import java.io.Serializable;

/**
 * 登陆记录
 */
public class LoginRecord implements Serializable {

    private static final long serialVersionUID = -6552036060361017890L;

    /** ID */
    private Integer id;
    /** 用户名 */
    private String username;
    /** 登陆状态 */
    private LoginStatus loginStatus;
    /** 登陆时间 */
    private Long loginTime;

    public LoginRecord() {
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

    public LoginStatus getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(LoginStatus loginStatus) {
        this.loginStatus = loginStatus;
    }

    public Long getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Long loginTime) {
        this.loginTime = loginTime;
    }

    @Override
    public String toString() {
        return "LoginRecord [id=" + id + ", username=" + username + ", loginStatus=" + loginStatus + ", loginTime="
                + loginTime + "]";
    }

}
