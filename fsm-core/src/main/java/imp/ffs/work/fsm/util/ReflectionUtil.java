package imp.ffs.work.fsm.util;

/**
 * @author peiheng.zph created on 18/5/10 下午8:17
 * @version 1.0
 */
public class ReflectionUtil {

  private static final CustomSecurityManager securityManager = new CustomSecurityManager();

  public static <T> Class<T> getCallerClass() {
    return getCallerClass(4);
  }

  @SuppressWarnings("unchecked")
  public static <T> Class<T> getCallerClass(int stackDepth) {
    return securityManager.getCallerClass(stackDepth);
  }

  static class CustomSecurityManager extends SecurityManager {

    Class getCallerClass(int stackDepth) {
      return getClassContext()[stackDepth];
    }
  }

}
