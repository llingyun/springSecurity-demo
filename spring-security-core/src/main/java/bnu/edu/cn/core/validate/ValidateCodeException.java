package bnu.edu.cn.core.validate;

import org.springframework.security.core.AuthenticationException;

/**
 * 自定义验证码异常
 * @author 11388
 *
 */
public class ValidateCodeException extends AuthenticationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ValidateCodeException(String mng) {
		super(mng);
	}

}
