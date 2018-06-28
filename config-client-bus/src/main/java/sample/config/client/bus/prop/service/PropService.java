package sample.config.client.bus.prop.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

@RefreshScope
@Service
public class PropService {

	// 从dev获取
	@Value(value = "${foo}")
	private String foo;

	// 从默认获取
	@Value(value = "${hostname}")
	private String hostname;

	public String call() {
		return "Config " + foo + " in " + hostname + " called.";
	}

}
