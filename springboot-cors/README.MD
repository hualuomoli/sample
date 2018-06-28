# 跨域

# 修改host
+ 修改`C:\Windows\System32\drivers\etc\hosts`文件，增加客户端、服务端域名映射
```
127.0.0.1     a.com
127.0.0.1     b.com
```

+ 刷新hosts
  - 打开cmd
  - 输入命令`ipconfig /flushdns`完成刷新

+ 测试是否配置成功
  - 打开cmd
  - 输入命令`ping a.com`查看是否访问127.0.0.1
  - 输入命令`ping b.com`查看是否访问127.0.0.1


# 配置nginx
  + 修改conf/nginx.conf
  在http模块下增加虚拟主机的配置
    ```
    # 包含所有vhost目录下以.conf结尾的文件
    include vhost/*.conf;
    ```

  + 在conf目录目录下创建vhost目录

  + 在conf/vhost目录下创建客户端a.com映射(文件名a.conf)
    ```
    server {
      listen  80;
      server_name a.com;

      location / {
        # 修改成你自己项目的目录
        alias  E:/project/github/hualuomoli/sample/java/spring-boot/springboot-cors/;
      }
    }
    ```

  + 配置完成后重新加载
    `nginx -s reload`

# 服务端直接支持
  + 创建过滤器，在doFilter方法增加支持跨域信息
    ```
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
      HttpServletRequest req = (HttpServletRequest) request;
      HttpServletResponse res = (HttpServletResponse) response;

      // 服务端增加跨域信息
      //带cookie的时候，origin必须是全匹配，不能使用*
      String origin = req.getHeader("origin");
      if (!org.springframework.util.StringUtils.isEmpty(origin)) {
        res.addHeader("Access-Control-Allow-Origin", origin);
      }

      // 支持的方法
      res.addHeader("Access-Control-Allow-Methods", "*");

      // 支持所有自定义头
      String headers = req.getHeader("Access-Control-Request-Headers");
      if (!org.springframework.util.StringUtils.isEmpty(headers)) {
        res.addHeader("Access-Control-Allow-Headers", headers);
      }
      
      // 缓存时间
      res.addHeader("Access-Control-Max-Age", "3600");

      // 允许使用cookie
      res.addHeader("Access-Control-Allow-Credentials", "true");

      // next
      chain.doFilter(request, response);
    }
    ```

  + [验证结果](http://a.com/html/demo-server.html)

# 修改服务端nginx
  + 在`nginx/conf/vhost`目录下创建`b.conf`文件，文件内容如下
    ```
    server {
      
      listen  80;
      server_name b.com;

      location / {

        proxy_pass  http://localhost:8080/;

        # 增加header
        add_header  Access-Control-Allow-Origin $http_origin;
        add_header  Access-Control-Allow-Methods *;
        add_header  Access-Control-Max-Age  3600;
        add_header  Access-Control-Allow-Credentials true;
        add_header  Access-Control-Allow-Headers $http_access_control_request_headers;

        # OPTIONS直接返回
        if ($http_method = OPTIONS) {
          return 200;
        }

      }

    }
    ```

  + 刷新nginx
    `nginx -s reload`

  + [验证结果](http://a.com/html/user-nginx-server.html)

# 客户端nginx配置
  + 修改`nginx/conf/vhost/a.com`增加服务器转发,修改后配置如下
    ```
    server {
      
      listen  80;
      server_name a.com;

      location / {
        # 修改成你自己项目的目录
        alias  E:/project/github/hualuomoli/sample/java/spring-boot/springboot-cors/;
      }

      # 配置客户端的相对URI转发到服务端
      location /server/ {
        proxy_pass  http://localhost:8080/;
      }

    }

    ```

  + 刷新nginx
    `nginx -s reload`

  + [验证结果](http://a.com/html/user-nginx-client.html)

