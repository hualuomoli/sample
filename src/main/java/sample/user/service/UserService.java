package sample.user.service;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    public String nickname(String username) {
        return username + ":" + "测试";
    }

}
