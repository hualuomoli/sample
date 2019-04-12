# kafka

# 安装

+ 该例使用[单点](https://github.com/hualuomoli/config/tree/kafka/kafka/single)模式

# 注意

+ 需要调整`kafka`的配置文件`config/server.properties`，指定暴漏的`IP`地址，否则无法访问 `listeners=PLAINTEXT://your.host.name:9092`

# 参考文档
+ https://github.com/danielwegener/logback-kafka-appender