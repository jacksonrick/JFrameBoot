package org.springframework.security.web.authentication;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Description: 替换官方的AbstractAuthenticationTargetUrlRequestHandler
 * User: xujunfei
 * Date: 2020-06-09
 * Time: 17:35
 */
public abstract class AbstractAuthenticationTargetUrlRequestHandler {

    protected final Log logger = LogFactory.getLog(this.getClass());
    private String targetUrlParameter = "callback"; // 修改此项以支持前端可以重定向到页面
    private String defaultTargetUrl = "/";
    private boolean alwaysUseDefaultTargetUrl = false;
    private boolean useReferer = false;
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    protected AbstractAuthenticationTargetUrlRequestHandler() {
    }

    /**
     * Invokes the configured {@code RedirectStrategy} with the URL returned by the
     * {@code determineTargetUrl} method.
     * <p>
     * The redirect will not be performed if the response has already been committed.
     */
    protected void handle(HttpServletRequest request, HttpServletResponse response,
                          Authentication authentication) throws IOException {
        String targetUrl = determineTargetUrl(request, response, authentication);

        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect to "
                    + targetUrl);
            return;
        }

        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    /**
     * Builds the target URL according to the logic defined in the main class Javadoc
     *
     * @since 5.2
     */
    protected String determineTargetUrl(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication) {
        return determineTargetUrl(request, response);
    }

    /**
     * Builds the target URL according to the logic defined in the main class Javadoc.
     */
    protected String determineTargetUrl(HttpServletRequest request,
                                        HttpServletResponse response) {
        if (isAlwaysUseDefaultTargetUrl()) {
            return defaultTargetUrl;
        }

        // Check for the parameter and use that if available
        String targetUrl = null;

        if (targetUrlParameter != null) {
            targetUrl = request.getParameter(targetUrlParameter);

            if (StringUtils.hasText(targetUrl)) {
                logger.debug("Found targetUrlParameter in request: " + targetUrl);

                return targetUrl;
            }
        }

        if (useReferer && !StringUtils.hasLength(targetUrl)) {
            targetUrl = request.getHeader("Referer");
            logger.debug("Using Referer header: " + targetUrl);
        }

        if (!StringUtils.hasText(targetUrl)) {
            targetUrl = defaultTargetUrl;
            logger.debug("Using default Url: " + targetUrl);
        }

        return targetUrl;
    }

    /**
     * Supplies the default target Url that will be used if no saved request is found or
     * the {@code alwaysUseDefaultTargetUrl} property is set to true. If not set, defaults
     * to {@code /}.
     *
     * @return the defaultTargetUrl property
     */
    protected final String getDefaultTargetUrl() {
        return defaultTargetUrl;
    }

    /**
     * Supplies the default target Url that will be used if no saved request is found in
     * the session, or the {@code alwaysUseDefaultTargetUrl} property is set to true. If
     * not set, defaults to {@code /}. It will be treated as relative to the web-app's
     * context path, and should include the leading <code>/</code>. Alternatively,
     * inclusion of a scheme name (such as "http://" or "https://") as the prefix will
     * denote a fully-qualified URL and this is also supported.
     *
     * @param defaultTargetUrl
     */
    public void setDefaultTargetUrl(String defaultTargetUrl) {
        Assert.isTrue(UrlUtils.isValidRedirectUrl(defaultTargetUrl),
                "defaultTarget must start with '/' or with 'http(s)'");
        this.defaultTargetUrl = defaultTargetUrl;
    }

    /**
     * If <code>true</code>, will always redirect to the value of {@code defaultTargetUrl}
     * (defaults to <code>false</code>).
     */
    public void setAlwaysUseDefaultTargetUrl(boolean alwaysUseDefaultTargetUrl) {
        this.alwaysUseDefaultTargetUrl = alwaysUseDefaultTargetUrl;
    }

    protected boolean isAlwaysUseDefaultTargetUrl() {
        return alwaysUseDefaultTargetUrl;
    }

    /**
     * If this property is set, the current request will be checked for this a parameter
     * with this name and the value used as the target URL if present.
     *
     * @param targetUrlParameter the name of the parameter containing the encoded target
     * URL. Defaults to null.
     */
    public void setTargetUrlParameter(String targetUrlParameter) {
        if (targetUrlParameter != null) {
            Assert.hasText(targetUrlParameter, "targetUrlParameter cannot be empty");
        }
        this.targetUrlParameter = targetUrlParameter;
    }

    protected String getTargetUrlParameter() {
        return targetUrlParameter;
    }

    /**
     * Allows overriding of the behaviour when redirecting to a target URL.
     */
    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }

    protected RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }

    /**
     * If set to {@code true} the {@code Referer} header will be used (if available).
     * Defaults to {@code false}.
     */
    public void setUseReferer(boolean useReferer) {
        this.useReferer = useReferer;
    }

}