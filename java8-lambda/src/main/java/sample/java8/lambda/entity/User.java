package sample.java8.lambda.entity;

public class User {

  /** 用户名 */
  private String username;
  /** 昵称 */
  private String nickname;
  /** 年龄 */
  private int age;

  public User() {
    super();
  }

  public User(String username, String nickname, int age) {
    this.username = username;
    this.nickname = nickname;
    this.age = age;
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

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  @Override
  public String toString() {
    return "User [username=" + username + ", nickname=" + nickname + ", age=" + age + "]";
  }

}