package sample.oauth.wechat.web.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.google.common.collect.Maps;

import sample.oauth.wechat.util.WechatUtils;

/**
 * 微信公众号授权
 * <a href="https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140842">微信网页授权</a>
 */
@RequestMapping(path = "/wechat/web/mp")
@Controller
public class WechatWebController {

  private static final Logger logger = LoggerFactory.getLogger(WechatWebController.class);
  // 使用本地map存储
  private static final Map<String, UserInfo> userInfos = Maps.newHashMap();

  @Value(value = "${auth.wechat.web.mp.callback_url}")
  private String callbackUrl;
  @Value(value = "${auth.wechat.web.mp.index_url}")
  private String indexUrl;
  @Value(value = "${auth.wechat.web.mp.app_id}")
  private String appId;
  @Value(value = "${auth.wechat.web.mp.app_secret}")
  private String appSecret;

  @RequestMapping(path = "/index.html")
  public String index() {
    return "/wechat/web/mp/index";
  }

  // 生成授权URL
  @RequestMapping(path = "/auth")
  @ResponseBody
  public String auth(HttpServletRequest req, HttpServletResponse res) throws IOException {
    String state = req.getParameter("state");

    StringBuilder buffer = new StringBuilder();
    buffer.append("https://open.weixin.qq.com/connect/oauth2/authorize");
    buffer.append("?appid=").append(appId);
    buffer.append("&redirect_uri=").append(URLEncoder.encode(callbackUrl, "UTF-8"));
    buffer.append("&response_type=code");
    buffer.append("&scope=snsapi_userinfo");
    buffer.append("&state=").append(state);
    buffer.append("#wechat_redirect");

    return buffer.toString();
  }

  // 微信回调
  @RequestMapping(path = "/callback")
  public String callback(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
    logger.info("accept wechat auth callback.");

    String code = req.getParameter("code");
    String state = req.getParameter("state");
    logger.info("授权code={}, state={}", code, state);
    Validate.notBlank(code, "code is blank.");
    Validate.notBlank(state, "state is blank");

    try {
      // 1、获取accecc_token
      StringBuilder buffer = new StringBuilder();
      buffer.append("https://api.weixin.qq.com/sns/oauth2/access_token");
      buffer.append("?appid=").append(appId);
      buffer.append("&secret=").append(appSecret);
      buffer.append("&code=").append(code);
      buffer.append("&grant_type=authorization_code");

      String result = WechatUtils.get(buffer.toString());
      logger.info("获取access_token成功 result={}", result);
      JSONObject json = JSON.parseObject(result);
      String openid = json.getString("openid");
      String access_token = json.getString("access_token");

      // 2、获取用户信息
      buffer = new StringBuilder();
      buffer.append("https://api.weixin.qq.com/sns/userinfo");
      buffer.append("?access_token=").append(access_token);
      buffer.append("&openid=").append(openid);
      buffer.append("&lang=zh_CN");
      result = WechatUtils.get(buffer.toString());
      logger.info("获取用户信息成功 result={}", result);

      // 返回结果
      UserInfo userInfo = JSON.parseObject(result, UserInfo.class);
      userInfos.put(state, userInfo); // 存储授权使用的state与用户关系,用于查询
      req.setAttribute("userInfo", userInfo);
      return "/wechat/web/mp/result";
    } catch (IOException e) {
      logger.warn("调用微信接口失败 {}", e);
      throw new RuntimeException();
    }

    // end callback
  }

  // 查询登录状态
  @RequestMapping(path = "/query")
  @ResponseBody
  public Map<String, Object> query(@RequestParam(value = "state") String state) {
    Map<String, Object> result = Maps.newHashMap();
    UserInfo userInfo = userInfos.get(state);
    if (userInfo == null) {
      result.put("success", false);
    } else {
      result.put("success", true);
      result.put("userInfo", userInfo);
    }
    return result;
  }

  //微信显示主界面
  @RequestMapping(path = "/logged")
  public String logged(@RequestParam(value = "state") String state, HttpServletRequest req) throws IOException {
    UserInfo userInfo = userInfos.get(state);
    req.setAttribute("userInfo", userInfo);
    return "/wechat/web/mp/logged";
  }

  // 用户信息实体类
  public static class UserInfo {
    /** 用户openid */
    private String openid;
    /** 用户昵称 */
    private String nickname;
    /** 用户头像 */
    @JSONField(name = "headimgurl")
    private String avatar;

    public String getOpenid() {
      return openid;
    }

    public void setOpenid(String openid) {
      this.openid = openid;
    }

    public String getNickname() {
      return nickname;
    }

    public void setNickname(String nickname) {
      this.nickname = nickname;
    }

    public String getAvatar() {
      return avatar;
    }

    public void setAvatar(String avatar) {
      this.avatar = avatar;
    }

    @Override
    public String toString() {
      return "UserInfo [openid=" + openid + ", nickname=" + nickname + ", avatar=" + avatar + "]";
    }
  }

}
