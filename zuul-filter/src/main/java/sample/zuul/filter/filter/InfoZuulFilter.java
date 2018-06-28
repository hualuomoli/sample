package sample.zuul.filter.filter;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component
public class InfoZuulFilter extends ZuulFilter {

	private static final Logger logger = LoggerFactory.getLogger(InfoZuulFilter.class);

	@Override
	public boolean shouldFilter() {
		HttpServletRequest request = this.getRequest();
		String uri = request.getRequestURI();

		logger.info("request uri is {}", uri);

		return StringUtils.startsWith(uri, "/auth");
	}

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 0;
	}

	@Override
	public Object run() throws ZuulException {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();

		String token = request.getParameter("token");
		try {
			this.check(token, ctx);
		} catch (IOException e) {
			throw new ZuulException("token is blank.", 401, "未登录");
		}

		return null;
	}

	private void check(String token, RequestContext ctx) throws IOException {
		if (StringUtils.isNotBlank(token)) {
			return;
		}

		//		throw new ZuulException("token is blank.", 401, "未登录");

		ctx.setSendZuulResponse(false);
		ctx.setResponseStatusCode(401);
		ctx.getResponse().getWriter().write("token is empty");
	}

	private HttpServletRequest getRequest() {
		RequestContext ctx = RequestContext.getCurrentContext();
		return ctx.getRequest();
	}

}
