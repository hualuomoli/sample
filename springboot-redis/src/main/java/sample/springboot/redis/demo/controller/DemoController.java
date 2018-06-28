package sample.springboot.redis.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;

import sample.springboot.redis.demo.entity.Data;
import sample.springboot.redis.demo.service.DemoService;
import sample.springboot.redis.util.RedisUtils;

@RequestMapping(path = "/demo")
@RestController
public class DemoController {

	private static final Logger logger = LoggerFactory.getLogger(DemoController.class);

	private static final String CACHE_KEY = "datas";

	@Autowired
	private DemoService demoService;

	@RequestMapping(path = "/loadDatas")
	public List<Data> loadDatas() {
		List<Data> datas = null;
		// load from cache
		String cacheDatas = RedisUtils.get(CACHE_KEY);
		if (cacheDatas == null) {
			logger.info("load data from service.");
			// load from service
			datas = demoService.loadDatas();
			RedisUtils.set(CACHE_KEY, JSON.toJSONString(datas));
		} else {
			logger.debug("load data from cache.");
			datas = JSON.parseArray(cacheDatas, Data.class);
		}

		return datas;
	}

	@RequestMapping(path = "clean")
	public void clean() {
		RedisUtils.clean(CACHE_KEY);
	}

}
