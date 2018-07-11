package sample.oauth.wechat.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.io.IOUtils;

public class WechatUtils {

  /**
   * GET请求
   * @param urlString 请求URL地址
   * @return 相应结果
   * @throws IOException 调用异常
   */
  public static String get(String urlString) throws IOException {
    HttpURLConnection conn = null;
    InputStream is = null;

    try {
      // 获取URL地址
      URL url = new URL(urlString);
      conn = (HttpURLConnection) url.openConnection();
      conn.setRequestMethod("GET");

      is = conn.getInputStream();
      return IOUtils.toString(is, "UTF-8");
    } finally {
      if (is != null) {
        is.close();
      }
      if (conn != null) {
        conn.disconnect();
      }
    }
  }

}
