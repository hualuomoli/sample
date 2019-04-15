package sample.kafka.logback.converter;

import java.net.InetAddress;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

/**
 * 获取主机IP地址
 */
public class IpLogConverter extends ClassicConverter {

    private static final String ip;

    static {
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String convert(ILoggingEvent event) {
        return ip;
    }

}
