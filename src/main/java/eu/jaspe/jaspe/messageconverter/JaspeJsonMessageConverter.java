package eu.jaspe.jaspe.messageconverter;

import eu.jaspe.jaspe.view.JaspeJsonView;

/**
 * <p>
 * <p>
 * <p>
 * <p>
 * @author cjrequena
 *
 */
public class JaspeJsonMessageConverter extends JaspeAbstractMessageConverter<JaspeJsonView> {

  public JaspeJsonMessageConverter() {
    super(new JaspeJsonView.Builder().build());
  }

  public JaspeJsonMessageConverter(JaspeJsonView view) {
    super(view);
  }

}
