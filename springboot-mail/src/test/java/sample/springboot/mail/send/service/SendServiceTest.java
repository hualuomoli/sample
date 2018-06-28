package sample.springboot.mail.send.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StreamUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SendServiceTest {

  @Value(value = "${sendToMails}")
  private String sendToMails;

  @Autowired
  private SendService sendService;

  private String[] toMails;
  private String html;
  private List<File> images;
  private List<File> attachments;

  @Before
  public void before() throws IOException {
    String path = SendServiceTest.class.getClassLoader().getResource(".").getPath();
    String projectPath = path.substring(0, path.indexOf("/target"));

    toMails = sendToMails.split("[,]");

    InputStream is = SendService.class.getResourceAsStream("/index.html");
    html = StreamUtils.copyToString(is, Charset.forName("UTF-8"));

    images = new ArrayList<File>();
    images.add(new File(projectPath, "src/test/resources/image/美女.jpg"));
    images.add(new File(projectPath, "src/test/resources/image/游戏壁纸.jpg"));

    attachments = new ArrayList<File>();
    attachments.add(new File(projectPath, "src/test/resources/attachment/在线文档预览用户手册.docx"));
  }

  @Test
  public void testSendContent() {
    sendService.sendContent("发送单独的内容", "这是一段邮件内容", toMails);
  }

  @Test
  public void testSendHtml() throws MessagingException {
    sendService.sendHtml("发送单独的网页", html, toMails);
  }

  @Test
  public void testSendContentHasImages() throws MessagingException, IOException {
    sendService.sendContentHasImages("带图片的内容", "内容包含图片", images, toMails);
  }

  @Test
  public void testSendHtmlHasImages() throws MessagingException, IOException {
    sendService.sendHtmlHasImages("带图片的网页", html, images, toMails);
  }

  @Test
  public void testSendContentHasAttachment() throws MessagingException {
    sendService.sendContentHasAttachment("带文档的内容", "内容包含附件", attachments, toMails);
  }

  @Test
  public void testSendHtmlHasAttachment() throws MessagingException {
    sendService.sendHtmlHasAttachment("带文档的网页", html, attachments, toMails);
  }

}
