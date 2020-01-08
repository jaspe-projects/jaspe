package eu.jaspe.jaspe.util;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * <p>
 * <p>
 * <p>
 * @author cjrequena
 *
 */
public class RequestUtil {
    private static final String DISABLE_JASPE = "disable_jaspe";

    /**
     * Disables Jaspe query evaluation for the specified request.
     *
     * @param request the request for which to disable Jaspe query evaluation
     */
    public static void disableJaspe(final HttpServletRequest request) {
        request.setAttribute(DISABLE_JASPE, true);
    }

    /**
     * Returns a boolean indicating whether or not Jaspe query evaluation has been disabled for the
     * specified request.
     *
     * @param request
     * @return
     */
    public static boolean isJaspeDisabled(final HttpServletRequest request) {
        return (request.getAttribute(DISABLE_JASPE) != null) ? (Boolean) request.getAttribute(DISABLE_JASPE) : false;
    }

    /**
     * Checks if there is a parameter in the request query string with the supplied name.
     *
     * @param parameterName name to check for
     * @return <code>true</code> if the parameter exists in the query string; otherwise <code>false</code>
     */
    public static boolean containsParameter(final HttpServletRequest request, final String parameterName) {
        return request.getParameter(parameterName) != null;
    }
}
