package sample.compiler.file;

import sample.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContentService {

    @Autowired
    private UserService userService;

    public String content(String str) {
        return "conent " + str;
    }

}