package eu.jaspe.jaspe.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.jaspe.jaspe.interceptor.JaspeDisabledHandlerInterceptor;
import eu.jaspe.jaspe.messageconverter.JaspeJsonMessageConverter;
import eu.jaspe.jaspe.view.JaspeJsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * <p>
 * <p>
 * <p>
 * @author cjrequena
 *
 */
@Configuration
@ComponentScan(basePackages = {"eu.jaspe.jaspe"})
public class JaspeWebConfiguration implements WebMvcConfigurer {



  @Autowired
  JaspeDisabledHandlerInterceptor jaspeDisabledHandlerInterceptor;

  @Autowired
  Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder;

  @Override
  public void configureContentNegotiation(ContentNegotiationConfigurer contentNegotiationConfigurer) {
    contentNegotiationConfigurer.mediaType("json", MediaType.APPLICATION_JSON);
  }

  @Override
  public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    final ObjectMapper objectMapper = this.jackson2ObjectMapperBuilder.build();
    JaspeJsonMessageConverter jaspeJsonMessageConverter = new JaspeJsonMessageConverter(new JaspeJsonView.Builder().withObjectMapper(objectMapper).build());
    jaspeJsonMessageConverter.setSupportedMediaTypes(getSupportedMediaTypes());
    converters.add(0,jaspeJsonMessageConverter);
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(jaspeDisabledHandlerInterceptor);
  }

  /**
   *
   * @return
   */
  private List<MediaType> getSupportedMediaTypes() {
    List<MediaType> list = new ArrayList<>();
    list.add(MediaType.ALL);
    return list;
  }
}
