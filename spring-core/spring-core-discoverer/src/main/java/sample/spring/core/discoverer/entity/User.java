package sample.spring.core.discoverer.entity;

/**
 * 用户
 */
public class User {

  /** 用户名 */
  private String username;
  /** 昵称 */
  private String nickname;
  /** 年龄 */
  private Integer age;
  /** 性别 */
  private Gender gender;

  public User() {
  }

  public User(String username, String nickname, Integer age, Gender gender) {
    this.username = username;
    this.nickname = nickname;
    this.age = age;
    this.gender = gender;
  }

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

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public Gender getGender() {
    return gender;
  }

  public void setGender(Gender gender) {
    this.gender = gender;
  }

}
