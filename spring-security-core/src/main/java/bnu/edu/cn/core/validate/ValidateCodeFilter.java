package bnu.edu.cn.core.validate;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import bnu.edu.cn.authentication.MyAuthenticationFailureHandler;
import bnu.edu.cn.core.properties.SecurityProperties;

public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean{
	
	private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
	
	private MyAuthenticationFailureHandler authenticationFailureHandler;
	
	@Inject
	private SecurityProperties securityProperties;
	
	private Set<String> urls = new HashSet<>();
	
	private AntPathMatcher antPathMatcher = new AntPathMatcher();

	@Override
	public void afterPropertiesSet() throws ServletException {
		// TODO Auto-generated method stub
		super.afterPropertiesSet();
		String[] configUrls = StringUtils.splitByWholeSeparatorPreserveAllTokens(securityProperties.getCode().getImage().getUrl(), ",");
		if (configUrls == null)
			urls.add("/authentication/form");
		if (configUrls != null) {
			for (int i = 0; i < configUrls.length; i++) {
				urls.add(configUrls[i]);
				urls.add("/authentication/form");
			}
		}	
	}

	@Override
	protected void doFilterInternal (HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		boolean action = false;
		for (String url : urls) {
			if (antPathMatcher.match(url, request.getRequestURI()))
				action = true;
		}
		if (action) {
//			if (StringUtils.equals("/login", request.getRequestURI()) && StringUtils.equalsIgnoreCase(request.getMethod(), "post")) {
				try {
					validateCode(new ServletWebRequest(request));
				} catch (ValidateCodeException e) {
					authenticationFailureHandler.onAuthenticationFailure(request, response, e);
					return;
				}
//			}
		}
		filterChain.doFilter(request, response);
	}
	
	private void validateCode(ServletWebRequest request) throws ServletRequestBindingException {
		ImageCode codeInSession = (ImageCode) sessionStrategy.getAttribute(request, ValidateCodeController.SESSION_KEY);
		String codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), "imageCode");
		if (StringUtils.isBlank(codeInRequest)) {
			throw new ValidateCodeException("验证码不能为空！！");
		}
		
		if (codeInSession == null) {
			throw new ValidateCodeException("验证码不存在！！");
		}
		
		if (codeInSession.isExpried()) {
			throw new ValidateCodeException("验证码过期！！");
		}
		
		if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
			throw new ValidateCodeException("验证码错误！！");
		}
		sessionStrategy.removeAttribute(request, ValidateCodeController.SESSION_KEY);
	}

	public SessionStrategy getSessionStrategy() {
		return sessionStrategy;
	}

	public void setSessionStrategy(SessionStrategy sessionStrategy) {
		this.sessionStrategy = sessionStrategy;
	}

	public MyAuthenticationFailureHandler getAuthenticationFailureHandler() {
		return authenticationFailureHandler;
	}

	public void setAuthenticationFailureHandler(MyAuthenticationFailureHandler authenticationFailureHandler) {
		this.authenticationFailureHandler = authenticationFailureHandler;
	}

	public SecurityProperties getSecurityProperties() {
		return securityProperties;
	}

	public void setSecurityProperties(SecurityProperties securityProperties) {
		this.securityProperties = securityProperties;
	}
}
