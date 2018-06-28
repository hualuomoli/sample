package sample.springboot.mail.send.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class SendService {

  @Value(value = "${spring.mail.username}")
  private String mailFrom;

  @Autowired
  private JavaMailSender mailSender;

  public void sendContent(String subject, String content, String... toMails) {
    SimpleMailMessage mailMessage = new SimpleMailMessage();
    mailMessage.setTo(toMails);
    mailMessage.setFrom(mailFrom);
    mailMessage.setSubject(subject);
    mailMessage.setText(content);

    mailSender.send(mailMessage);
  }

  public void sendHtml(String subject, String html, String... toMails) throws MessagingException {
    MimeMessage mimeMessage = mailSender.createMimeMessage();

    MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
    mimeMessageHelper.setTo(toMails);
    mimeMessageHelper.setFrom(mailFrom);
    mimeMessageHelper.setSubject(subject);
    mimeMessageHelper.setText(html, true);

    mailSender.send(mimeMessage);

  }

  public void sendContentHasImages(String subject, String content, List<File> images, String... toMails)
      throws MessagingException, IOException {
    this.sendHasImages(subject, content, images, false, toMails);
  }

  public void sendHtmlHasImages(String subject, String html, List<File> images, String... toMails)
      throws MessagingException, IOException {
    this.sendHasImages(subject, html, images, true, toMails);
  }

  private void sendHasImages(String subject, String text, List<File> images, boolean isHtml, String... toMails)
      throws MessagingException, IOException {
    MimeMessage mimeMessage = mailSender.createMimeMessage();

    MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
    mimeMessageHelper.setTo(toMails);
    mimeMessageHelper.setFrom(mailFrom);
    mimeMessageHelper.setSubject(subject);
    mimeMessageHelper.setText(text, isHtml);

    // add image
    for (int i = 0; i < images.size(); i++) {
      File image = images.get(i);
      mimeMessageHelper.addInline("imageId" + (i + 1), image);
    }

    mailSender.send(mimeMessage);
  }

  public void sendContentHasAttachment(String subject, String content, List<File> attachments, String... toMails)
      throws MessagingException {
    this.sendHasAttachment(subject, content, attachments, false, toMails);
  }

  public void sendHtmlHasAttachment(String subject, String html, List<File> attachments, String... toMails)
      throws MessagingException {
    this.sendHasAttachment(subject, html, attachments, true, toMails);
  }

  private void sendHasAttachment(String subject, String text, List<File> attachments, boolean isHtml, String... toMails)
      throws MessagingException {
    MimeMessage mimeMessage = mailSender.createMimeMessage();

    MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
    mimeMessageHelper.setTo(toMails);
    mimeMessageHelper.setFrom(mailFrom);
    mimeMessageHelper.setSubject(subject);
    mimeMessageHelper.setText(text, isHtml);

    // add attachment
    for (int i = 0; i < attachments.size(); i++) {
      File attachment = attachments.get(i);
      FileSystemResource img = new FileSystemResource(attachment);
      mimeMessageHelper.addAttachment(attachment.getName(), img);
    }

    mailSender.send(mimeMessage);
  }

}
