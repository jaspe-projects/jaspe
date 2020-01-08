package eu.jaspe.jaspe.view;

/**
 * <p>
 * <p>
 * <p>
 * <p>
 * @author cjrequena
 *
 */
public interface JaspeViewBuilder<T extends JaspeView> {

    /**
     * @return fully configured Jaspe view
     */
    T build();
}
