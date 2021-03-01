package springbook.learningtest.pointcut;

import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import static org.junit.jupiter.api.Assertions.*;

public class PointcutTest {
    @Test
    public void methodSignaturePointcut() throws SecurityException, NoSuchMethodException {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(public int springbook.learningtest.pointcut.Target.minus(int, int) throws java.lang.RuntimeException)");

        // Target.minus()
        assertTrue(pointcut.getClassFilter().matches(Target.class));
        assertTrue(pointcut.getMethodMatcher().matches(Target.class.getMethod("minus", int.class, int.class), null));

        // Target.plus()
        assertTrue(pointcut.getClassFilter().matches(Target.class));
        assertFalse(pointcut.getMethodMatcher().matches(Target.class.getMethod("plus", int.class, int.class), null));

        // Bean.method()
        // TODO: (AspectJ 1.6.6) 클래스를 필터링 하는 것도 getMethodMatcher에서 진행하는 듯. ClassFilter는 항상 true를 반환하는 거 같다.
        // TODO: (AspectJ 1.9.6) 이제 클래스를 필터링할 때 false를 반환한다? 버그가 수정되었나?
        // TODO: (AspectJ 1.6.7) 버그가 수정되었는데, 해당 버그의 내용을 잘 못 찾겠다. 어쨌든 False가 나오는 게 맞음.
        assertFalse(pointcut.getClassFilter().matches(Bean.class));
        assertFalse(pointcut.getMethodMatcher().matches(Bean.class.getMethod("method"), null));
    }

    @Test
    public void pointcut() throws Exception {
        targetClassPointcutMatches("execution(* *(..))", true, true, true, true, true, true);
        targetClassPointcutMatches("execution(* hello(..))", true, true, false, false, false, false);
        targetClassPointcutMatches("execution(* hello())", true, false, false, false, false, false);
        targetClassPointcutMatches("execution(* hello(String))", false, true, false, false, false, false);
        targetClassPointcutMatches("execution(* meth*(..))", false, false, false, false, true, true);
        targetClassPointcutMatches("execution(* *(int, int))", false, false, true, true, false, false);
        targetClassPointcutMatches("execution(* *())", true, false, false, false, true, true);
        targetClassPointcutMatches("execution(* springbook.learningtest.pointcut.Target.*(..))", true, true, true, true, true, false);
        targetClassPointcutMatches("execution(* springbook.learningtest.pointcut.*.*(..))", true, true, true, true, true, true);
        targetClassPointcutMatches("execution(* springbook.learningtest.pointcut..*.*(..))", true, true, true, true, true, true);
        targetClassPointcutMatches("execution(* springbook..*.*(..))", true, true, true, true, true, true);
        targetClassPointcutMatches("execution(* com..*.*(..))", false, false, false, false, false, false);
        targetClassPointcutMatches("execution(* *..Target.*(..))", true, true, true, true, true, false);
        targetClassPointcutMatches("execution(* *..Tar*.*(..))", true, true, true, true, true, false);
        targetClassPointcutMatches("execution(* *..*get.*(..))", true, true, true, true, true, false);
        targetClassPointcutMatches("execution(* *..B*.*(..))", false, false, false, false, false, true);
        targetClassPointcutMatches("execution(* *..TargetInterface.*(..))", true, true, true, true, false, false);
        targetClassPointcutMatches("execution(* *(..) throws Runtime*)", false, false, false, true, false, true);
        targetClassPointcutMatches("execution(int *(..))", false, false, true, true, false, false);
        targetClassPointcutMatches("execution(void *(..))", true, true, false, false, true, true);
    }

    public void targetClassPointcutMatches(String expression, boolean... expected) throws Exception {
        pointcutMatches(expression, expected[0], Target.class, "hello");
        pointcutMatches(expression, expected[1], Target.class, "hello", String.class);
        pointcutMatches(expression, expected[2], Target.class, "plus", int.class, int.class);
        pointcutMatches(expression, expected[3], Target.class, "minus", int.class, int.class);
        pointcutMatches(expression, expected[4], Target.class, "method");
        pointcutMatches(expression, expected[5], Bean.class, "method");
    }

    public void pointcutMatches(String expression, Boolean expected, Class<?> aClass, String methodName, Class<?>... args) throws Exception {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(expression);

        assertEquals(pointcut.getClassFilter().matches(aClass)
                && pointcut.getMethodMatcher().matches(aClass.getMethod(methodName, args), null), expected);
    }
}
