package sample.cdn.bce;

import java.util.Properties;

import com.baidubce.BceClientConfiguration;
import com.baidubce.auth.DefaultBceCredentials;
import com.baidubce.services.cdn.CdnClient;

public class CdnUtils {

  private static final String END_POINT = "http://cdn.baidubce.com"; // CDN服务端接口地址

  private static final String accessKeyId;
  private static final String secretAccessKy;
  private static CdnClient client;

  static {

    Properties prop = new Properties();
    try {
      prop.load(CdnUtils.class.getClassLoader().getResourceAsStream("bce.properties"));
      prop.load(CdnUtils.class.getClassLoader().getResourceAsStream("local.properties"));
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(0);
    }

    accessKeyId = prop.getProperty("cdn.bce.key.access");
    secretAccessKy = prop.getProperty("cdn.bce.key.secret");
    init();
  }

  /**
   * 初始化
   */
  private static void init() {
    BceClientConfiguration config = new BceClientConfiguration()//
        .withCredentials(new DefaultBceCredentials(accessKeyId, secretAccessKy))//
        .withEndpoint(END_POINT);
    client = new CdnClient(config);
  }

  public static CdnClient getInstance() {
    return client;
  }

}
