package sample.cdn.bce;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidubce.BceServiceException;
import com.baidubce.services.cdn.CdnClient;
import com.baidubce.services.cdn.model.CacheTTL;
import com.baidubce.services.cdn.model.CreateDomainRequest;
import com.baidubce.services.cdn.model.CreateDomainResponse;
import com.baidubce.services.cdn.model.Domain;
import com.baidubce.services.cdn.model.GetPrefetchStatusRequest;
import com.baidubce.services.cdn.model.GetPrefetchStatusResponse;
import com.baidubce.services.cdn.model.GetPurgeStatusRequest;
import com.baidubce.services.cdn.model.GetPurgeStatusResponse;
import com.baidubce.services.cdn.model.IpACL;
import com.baidubce.services.cdn.model.ListDomainsResponse;
import com.baidubce.services.cdn.model.OriginPeer;
import com.baidubce.services.cdn.model.PrefetchRequest;
import com.baidubce.services.cdn.model.PrefetchResponse;
import com.baidubce.services.cdn.model.PrefetchTask;
import com.baidubce.services.cdn.model.PurgeRequest;
import com.baidubce.services.cdn.model.PurgeResponse;
import com.baidubce.services.cdn.model.PurgeTask;
import com.baidubce.services.cdn.model.RefererACL;
import com.baidubce.services.cdn.model.SetDomainCacheTTLRequest;
import com.baidubce.services.cdn.model.SetDomainIpACLRequest;
import com.baidubce.services.cdn.model.SetDomainRefererACLRequest;

public class CdnUtilsTest {

  private static final Logger logger = LoggerFactory.getLogger(CdnUtilsTest.class);

  private static final String DOMAIN_NAME = "cdn.hualuomoli.cn";
  private static final String ORIGIN_DOMAIN_NAME = "cdnorigin.hualuomoli.cn";
  private static CdnClient client;

  @BeforeClass
  public static void beforeClass() {
    client = CdnUtils.getInstance();
  }

  @Test
  public void testGetInstance() {
    Assert.assertNotNull(client);
  }

  // 新建一个加速域名,如果已经创建抛出BceServiceException
  @Test
  public void testCreateDomain() {
    List<OriginPeer> origin = new ArrayList<OriginPeer>();
    origin.add(new OriginPeer().withPeer(ORIGIN_DOMAIN_NAME)); // 添加源站域名(只能有一个) 
    CreateDomainRequest request = new CreateDomainRequest()//
        .withDomain(DOMAIN_NAME)//
        .withOrigin(origin);
    try {
      CreateDomainResponse response = client.createDomain(request);
      logger.info("创建域名成功,CNAME={}", response.getCname()); // 创建成功，获取对应的CNAME
    } catch (BceServiceException e) {
      logger.error("创建域名失败: {}[{}]", e.getErrorMessage(), e.getErrorCode());
    }
  }

  // 列出所有的域名
  @Test
  public void testListDomains() {
    ListDomainsResponse response = client.listDomains();

    // 获取用户的加速域名列表
    List<Domain> domains = response.getDomains();
    // 遍历加速域名
    for (Domain domain : domains) {
      logger.debug("配置的域名={}", domain.getName());
    }
  }

  // 以删除一个域名,如果域名不存在抛出BceServiceException
  @Test
  public void testDeleteDomain() {
    try {
      client.deleteDomain(DOMAIN_NAME);
      logger.info("删除域名成功");
    } catch (BceServiceException e) {
      logger.error("删除域名失败: {}[{}]", e.getErrorMessage(), e.getErrorCode());
    }
  }

  // 设置缓存策略(刪除原有),如果域名不存在抛出BceServiceException
  @Test
  public void testSetDomainCacheTTL() {
    // 后缀:    suffix
    // 文件名:   exactPath
    // 目录:    path
    // 状态码:   code
    SetDomainCacheTTLRequest request = new SetDomainCacheTTLRequest()//
        .withDomain(DOMAIN_NAME)//
        .addCacheTTL(new CacheTTL().withType("suffix").withValue(".jpg").withTtl(60 * 60)) // .jpg后缀缓存一小时
        .addCacheTTL(new CacheTTL().withType("exactPath").withValue("/cdn/").withTtl(60 * 60 * 24)) // 以/cdn/为开头的文件缓存一天
        .addCacheTTL(new CacheTTL().withType("code").withValue("404").withTtl(60 * 60 * 24)) // 状态码为404缓存一天 (当前只支持404/502/503/504状态码缓存)
        .addCacheTTL(new CacheTTL().withType("path").withValue("/").withTtl(60 * 60 * 24 * 365)); // 根目录缓存一年
    try {
      client.setDomainCacheTTL(request);
      logger.info("配置域名缓存策略成功");
    } catch (BceServiceException e) {
      logger.error("配置域名缓存策略失败: {}[{}]", e.getErrorMessage(), e.getErrorCode());
    }
  }

  // 设置全url缓存
  @Test
  public void testSetDomainCacheFullUrl() {
    client.setDomainCacheFullUrl(DOMAIN_NAME, true);
  }

  // 设置访问Referer控制(黑名单域名)
  @Test
  public void testSetDomainRefererACLBlack() {
    RefererACL acl = new RefererACL() //
        .addBlackList("http://a.com") // 
        .addBlackList("http://b.com"); // 
    SetDomainRefererACLRequest request = new SetDomainRefererACLRequest()//
        .withDomain(DOMAIN_NAME)//
        .withRefererACL(acl);
    client.setDomainRefererACL(request);
  }

  // 设置访问Referer控制(白名单域名)
  @Test
  public void testSetDomainRefererACLWhite() {
    RefererACL acl = new RefererACL() //
        .addWhiteList("http://c.com") // 
        .addWhiteList("http://d.com"); // 
    SetDomainRefererACLRequest request = new SetDomainRefererACLRequest()//
        .withDomain(DOMAIN_NAME)//
        .withRefererACL(acl);
    client.setDomainRefererACL(request);
  }

  // 设置访问Ip控制(黑名单Ip)
  @Test
  public void testSetDomainIpACLBlack() {
    IpACL acl = new IpACL() // 
        .addBlackList("1.2.3.4")//
        .addBlackList("5.6.7.0/24");
    SetDomainIpACLRequest request = new SetDomainIpACLRequest().withDomain(DOMAIN_NAME).withIpACL(acl);
    client.setDomainIpACL(request);
  }

  //设置访问Ip控制(白名单Ip)
  @Test
  public void testSetDomainIpACLWhite() {
    IpACL acl = new IpACL() // 
        .addWhiteList("10.20.30.40")//
        .addWhiteList("50.60.70.0/24");
    SetDomainIpACLRequest request = new SetDomainIpACLRequest().withDomain(DOMAIN_NAME).withIpACL(acl);
    client.setDomainIpACL(request);
  }

  // 刷新缓存
  @Test
  public void testPurge() {
    String url = "http://" + DOMAIN_NAME + "/url";
    String directory = "http://" + DOMAIN_NAME + "/directory/";
    PurgeRequest request = new PurgeRequest()//
        .addTask(new PurgeTask().withUrl(url)) // url 单个URL地址
        .addTask(new PurgeTask().withDirectory(directory)); // directory 目录
    PurgeResponse purgeResponse = client.purge(request);
    // 根据任务id查询刷新结果
    GetPurgeStatusResponse purgeStatusResponse = client
        .getPurgeStatus(new GetPurgeStatusRequest().withId(purgeResponse.getId()));
    logger.info("purge success.{}", purgeStatusResponse);
  }

  // 预热缓存
  @Test
  public void testPrefetchUrl() {
    String url = "http://" + DOMAIN_NAME + "/url";

    PrefetchRequest request = new PrefetchRequest()//
        .addTask(new PrefetchTask().withUrl(url));
    PrefetchResponse prefetchResponse = client.prefetch(request);
    System.out.println(prefetchResponse);
    // 根据任务id查询预热结果
    GetPrefetchStatusResponse prefetchStatusResponse = client
        .getPrefetchStatus(new GetPrefetchStatusRequest().withId(prefetchResponse.getId()));
    logger.info("prefetch success.{}", prefetchStatusResponse);
  }

}
