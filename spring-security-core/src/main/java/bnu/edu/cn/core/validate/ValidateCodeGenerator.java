/**
 * 
 */
package bnu.edu.cn.core.validate;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author 11388
 *
 */
public interface ValidateCodeGenerator {

	/**
	 * 随机生成验证码
	 * @param request
	 * @return
	 */
	ImageCode createImageCode(ServletWebRequest request);
}
