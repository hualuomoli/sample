package sample.springboot.doc.user.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(outputDir = "target/snippets")
public class UserDocumentation {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void testLogin() throws Exception {
    mockMvc.perform(get("/user/login")//
        .param("username", "admin")//
        .param("password", "admin123")) //
        .andDo(document("user/login"));
  }

  @Test
  public void testFindUser() throws Exception {
    mockMvc.perform(get("/user/findUser")//
        .param("username", "admin")) //
        .andDo(document("user/findUser"));
  }

}
