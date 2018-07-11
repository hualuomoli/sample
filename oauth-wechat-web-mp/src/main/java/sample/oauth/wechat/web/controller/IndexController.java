package sample.oauth.wechat.web.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(path = "")
@RestController
public class IndexController {

  @RequestMapping(path = { "", "/", "/index", "/index.html", "/index.htm" })
  public void index(HttpServletRequest req, HttpServletResponse res) throws IOException {
    res.sendRedirect("/wechat/web/mp/index.html");
  }

}
