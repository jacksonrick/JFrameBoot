package com.jf.service.detail;

import com.jf.po.ICONSTANT;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2018-10-31
 * Time: 16:30
 */
public class OUserExtDetail extends WebAuthenticationDetails {

    private final String verify;
    private final String verifySession;

    public OUserExtDetail(HttpServletRequest request) {
        super(request);
        verify = request.getParameter(ICONSTANT.SE_VERIFY);
        verifySession = (String) request.getSession().getAttribute(ICONSTANT.SE_VERIFY);
    }

    public String getVerify() {
        return verify;
    }

    public String getVerifySession() {
        return verifySession;
    }

}
