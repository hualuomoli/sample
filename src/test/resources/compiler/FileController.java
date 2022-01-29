package sample.compiler.file;

import sample.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileController {

    @Autowired
    private UserService userService;

    public String show(String str) {
        return userService.nickname(str);
    }

}