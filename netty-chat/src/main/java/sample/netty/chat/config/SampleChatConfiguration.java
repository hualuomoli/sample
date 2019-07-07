package sample.netty.chat.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import sample.netty.chat.sticky.SampleChatStickyBootstrap;

@Configuration(value = "sample.netty.chat.config.SampleChatConfiguration")
public class SampleChatConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(SampleChatConfiguration.class);

    @Bean(initMethod = "init", destroyMethod = "destroy")
    public SampleChatStickyBootstrap sticky() {
        int port = 9001;
        SampleChatStickyBootstrap bootstrap = new SampleChatStickyBootstrap(port);
        logger.info("sticky mode started in {}", port);
        return bootstrap;

    }

}
