package sample.netty.chat;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import sample.netty.chat.check.SampleChatCheckBootstrap;
import sample.netty.chat.sticky.SampleChatStickyBootstrap;

@SpringBootApplication
public class SampleChatApplication {

    public static void main(String[] args) {
        SpringApplication.run(SampleChatApplication.class, args);
        startNetty();
    }

    private static void startNetty() {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        // sticky
        executorService.submit(new Runnable() {

            @Override
            public void run() {
                SampleChatStickyBootstrap.start(9001);
            }
        });

        // check
        executorService.submit(new Runnable() {

            @Override
            public void run() {
                SampleChatCheckBootstrap.start(9002);
            }
        });

        // end
    }
}
