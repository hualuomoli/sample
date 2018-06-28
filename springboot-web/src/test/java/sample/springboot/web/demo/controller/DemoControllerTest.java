package sample.springboot.web.demo.controller;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DemoControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testSayGet() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/demo/say")//
		    .param("world", "mock mvc"))//
		    .andDo((result) -> {
			    String data = result.getResponse().getContentAsString();
			    Assert.assertEquals("[testSayGet] 验证失败", "I can say mock mvc", data);
		    }) //
		    .andExpect(MockMvcResultMatchers.status().isOk())//
		;
	}

	@Test
	public void testSayPost() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/demo/say")//
		    .param("world", "mock mvc"))//
		    .andDo((result) -> {
			    String data = result.getResponse().getContentAsString();
			    Assert.assertEquals("[testSayPost] 验证失败", "I can say mock mvc", data);
		    }) //
		    .andExpect(MockMvcResultMatchers.status().isOk())//
		;
	}

	@Test
	public void testSayPostPayload() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/demo/say")//
		    .contentType(MediaType.APPLICATION_JSON)// 指定协议
		    .param("world", "mock mvc"))//
		    .andDo((result) -> {
			    String data = result.getResponse().getContentAsString();
			    Assert.assertEquals("[testSayPostPayload] 验证失败", "I can say mock mvc", data);
		    }) //
		    .andExpect(MockMvcResultMatchers.status().isOk())//
		;
	}

	@Test
	public void testSayPostForm() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/demo/say")//
		    .contentType(MediaType.APPLICATION_FORM_URLENCODED)// 指定协议
		    .param("world", "mock mvc"))//
		    .andDo((result) -> {
			    String data = result.getResponse().getContentAsString();
			    Assert.assertEquals("[testSayPostForm] 验证失败", "I can say mock mvc", data);
		    }) //
		    .andExpect(MockMvcResultMatchers.status().isOk())//
		;
	}

	@Test
	public void testDealNoParam() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/demo/deal"))//
		    .andExpect(MockMvcResultMatchers.status().isOk())//
		;
	}

	@Test
	public void testDealHasParam() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/demo/deal")//
		    .param("content", "content"))//
		    .andExpect(MockMvcResultMatchers.status().isOk())//
		;
	}

}
