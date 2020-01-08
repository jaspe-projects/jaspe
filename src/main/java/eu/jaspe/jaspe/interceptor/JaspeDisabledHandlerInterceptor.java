package eu.jaspe.jaspe.interceptor;

import eu.jaspe.jaspe.annotation.DisableJaspe;
import eu.jaspe.jaspe.util.RequestUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * <p>
 * <p>
 * <p>
 * @author cjrequena
 *
 */
@Component
public class JaspeDisabledHandlerInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
    if(handler instanceof HandlerMethod) {
      HandlerMethod method = (HandlerMethod) handler;
      if (method.getMethodAnnotation(DisableJaspe.class) != null) {
        RequestUtil.disableJaspe(request);
      }
    }
    return true;
  }

  @Override
  public void postHandle(
    HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
  }

  @Override public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

  }
}

