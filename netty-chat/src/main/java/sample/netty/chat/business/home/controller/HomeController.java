package sample.netty.chat.business.home.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping(value = "/home")
@Controller(value = "sample.netty.chat.business.home.controller.HomeController")
public class HomeController {

    @RequestMapping(value = "/index")
    @ResponseBody
    public String index() {
        return "home index page caller on " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

}
