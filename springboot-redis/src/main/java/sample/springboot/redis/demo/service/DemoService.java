package sample.springboot.redis.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import sample.springboot.redis.demo.entity.Data;

@Service
public class DemoService {

	public List<Data> loadDatas() {
		List<Data> datas = new ArrayList<Data>();
		for (int i = 1; i <= 10; i++) {
			Data data = new Data();
			data.setId(i);
			data.setCode("0" + i);
			data.setName("name with index " + i);
			datas.add(data);
		}
		return datas;
	}

}
