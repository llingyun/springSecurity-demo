package bnu.edu.cn.aspect;

import java.util.Date;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TimeAscept {

	@Around("execution(* bnu.edu.cn.controller.HomeController.*(..))")
	public Object handleControllerMethod(ProceedingJoinPoint pjp) throws Throwable {
		System.out.println("aspect");
		Object[] args = pjp.getArgs();
		for (int i = 0; i < args.length; i++) {
			System.out.println("arg is " + args[i]);
		}
		long startTime = new Date().getTime();
		Object object = pjp.proceed();
		System.out.println("aspect 耗时：" + (new Date().getTime() - startTime));
		return object;
	}
}
