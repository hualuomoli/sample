package sample.springboot.async.aspect;

import java.util.concurrent.atomic.AtomicInteger;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sample.springboot.async.log.service.LogService;

@Component
@Aspect
public class LoggerAspect {

  @Autowired
  private LogService logService;

  private static AtomicInteger count = new AtomicInteger();

  @Around("execution(* sample.springboot.async.user.service.UserService.loginAutoLog(String, String))")
  public Object log(ProceedingJoinPoint pjp) throws Throwable {
    int number = count.incrementAndGet();

    // 请求参数
    Object[] args = pjp.getArgs();
    // 1、打印请求参数
    logService.info("[number={}] parameter={}", number, args);

    // 2、执行业务逻辑
    Object result = null;
    try {
      result = pjp.proceed(args);
    } catch (Throwable t) {
      // 4、执行失败
      logService.error("[number={}] ", number, t);
      throw t;
    }

    // 3、打印执行结果
    logService.info("[number={}] result={}", number, result);

    return result;
  }

}
