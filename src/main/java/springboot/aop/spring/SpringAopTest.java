package springboot.aop.spring;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component  //加入IOC容器
@Aspect  // 指定当前类为切面类
public class SpringAopTest {

    // 指定切入点表达式： 拦截哪些方法； 即为哪些类生成代理对象
	//解释@Pointcut("execution(* cn.itcast.e_aop_anno.*.*(..))")
	//@Pointcut("execution(*    切入点表达式固定写法, cn.itcast.e_aop_anno表示包.类名(可以用*表示包下所有的类).方法名(可以用*表示类下所有的方法)(..)表示参数可以用..
    @Pointcut("execution(* cn.itcast.e_aop_anno.*.*(..))")
    public void pointCut_(){
    }
    
	//@Before("execution(* cn.itcast.e_aop_anno.*.*(..))")每个方法需要写相同的引用，所以将相同的部分抽取到一个空的方法中pointCut_(),
    // 前置通知 : 在执行目标方法之前执行
    @Before("pointCut_()")
    public void begin(){
        System.out.println("开始事务/异常");
    }
    
    // 后置/最终通知：在执行目标方法之后执行  【无论是否出现异常最终都会执行】
    @After("pointCut_()")
    public void after(){
        System.out.println("提交事务/关闭");
    }
    
    // 返回后通知： 在调用目标方法结束后执行 【出现异常不执行】
    @AfterReturning("pointCut_()")
    public void afterReturning() {
        System.out.println("afterReturning()");
    }
    
    // 异常通知： 当目标方法执行异常时候执行此关注点代码
    @AfterThrowing("pointCut_()")
    public void afterThrowing(){
        System.out.println("afterThrowing()");
    }
    
    // 环绕通知：环绕目标方式执行
    @Around("pointCut_()")
    public void around(ProceedingJoinPoint pjp) throws Throwable{
        System.out.println("环绕前....");
        pjp.proceed();  // 执行目标方法
        System.out.println("环绕后....");
    }
    
}