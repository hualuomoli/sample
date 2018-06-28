package sample.zuul.provider3.info.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sample.zuul.provider3.info.service.InfoService;

@RequestMapping(path = "/info")
@RestController
public class InfoController {

	@Autowired
	private InfoService infoService;

	@RequestMapping(path = "")
	public String msg() {
		return infoService.msg();
	}

}
