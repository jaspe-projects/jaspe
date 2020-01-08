package eu.jaspe.jaspe.messageconverter;

import eu.jaspe.jaspe.context.JaspeContext;
import eu.jaspe.jaspe.view.JaspeView;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 * <p>
 * <p>
 * <p>
 * @author cjrequena
 *
 */
public abstract class JaspeAbstractMessageConverter<T extends JaspeView> extends AbstractHttpMessageConverter<Object> {

  private final T view;

  public JaspeAbstractMessageConverter(final T view) {
    this.view = view;
  }

  @Override
  protected boolean supports(Class<?> aClass) {
    HttpServletRequest httpRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

    if (httpRequest.getParameter("fields") != null) {
      return Object.class.isAssignableFrom(aClass);
    }

    return false;
  }

  @Override
  public boolean canRead(Class<?> clazz, MediaType mediaType) {
    return super.canRead(clazz, mediaType);
  }

  @Override
  protected boolean canRead(MediaType mediaType) {
    return false;
  }

  @Override
  public boolean canWrite(Class<?> clazz, MediaType mediaType) {
    return super.canWrite(clazz, mediaType);
  }

  @Override
  protected boolean canWrite(MediaType mediaType) {
    return super.canWrite(mediaType);
  }

  @Override
  protected void addDefaultHeaders(HttpHeaders headers, Object obj, MediaType contentType) throws IOException {
    super.addDefaultHeaders(headers, obj, contentType);
  }

  @Override
  protected Object readInternal(Class<?> aClass, HttpInputMessage httpInputMessage) throws IOException, HttpMessageNotReadableException {
    return null;
  }

  @Override
  protected void writeInternal(Object obj, HttpOutputMessage httpOutputMessage) throws IOException, HttpMessageNotWritableException {
    try {
      HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
      HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
      HttpStatus httpStatus = HttpStatus.valueOf(response.getStatus());
      view.render(obj, new JaspeContext(request, response, httpStatus));
    } catch (Exception e) {
      e.printStackTrace();
    }

  }
}
