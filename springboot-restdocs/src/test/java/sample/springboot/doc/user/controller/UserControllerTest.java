package sample.springboot.doc.user.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testLogin() throws Exception {
        mockMvc.perform(get("/user/login")//
                .param("username", "admin")//
                .param("password", "admin123")) //
                .andDo(print()) //
                .andExpect(status().isOk()) //
                .andExpect(content().string("true"));
    }

    @Test
    public void testFindUser() throws Exception {
        mockMvc.perform(get("/user/findUser")//
                .param("username", "admin")) //
                .andDo(print()) //
                .andExpect(status().isOk());
    }

}
