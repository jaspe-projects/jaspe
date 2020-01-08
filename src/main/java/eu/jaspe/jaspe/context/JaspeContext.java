package eu.jaspe.jaspe.context;

import eu.jaspe.jaspe.util.RequestUtil;
import lombok.Data;
import org.springframework.http.HttpStatus;

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
@Data
public class JaspeContext {
  private final HttpServletRequest request;
  private final HttpServletResponse response;
  private final HttpStatus httpStatus;
  private final boolean jaspeDisabled;

  /**
   * @param request
   * @param response
   * @param httpStatus
   */
  public JaspeContext(
    final HttpServletRequest request,
    final HttpServletResponse response,
    HttpStatus httpStatus) {
    this.request = request;
    this.response = response;
    this.httpStatus = httpStatus;
    this.jaspeDisabled = RequestUtil.isJaspeDisabled(request);
  }

}
