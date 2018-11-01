package sample.springboot.kafka.entity;

import java.util.Date;

/**
 * 自定义消息
 */
public class Message {

  /** 消息ID */
  private String id;
  /** 消息内容 */
  private String content;
  /** 发送时间 */
  private Date sendTime;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Date getSendTime() {
    return sendTime;
  }

  public void setSendTime(Date sendTime) {
    this.sendTime = sendTime;
  }

  @Override
  public String toString() {
    return "Message [id=" + id + ", content=" + content + ", sendTime=" + sendTime + "]";
  }

}
