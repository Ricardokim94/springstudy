package com.kcm.common;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class EncodingFilter implements Filter {
       
	private String encoding = null;

	@Override 
	public void init(FilterConfig fConfig) throws ServletException {
		this.encoding = fConfig.getInitParameter("encoding"); //매개변수를 넣어 문자열로 넘겨주면 된는데 web.xml에서 설정해놓은 init-param을 말하는거임
		System.out.println(fConfig.getFilterName() + "필터가 시작되었습니다.");
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		if(encoding != null) {
			request.setCharacterEncoding(encoding);
			response.setCharacterEncoding(encoding);
		}
		chain.doFilter(request, response);
	
	}

	public void destroy() {

	}
	
}
