package com.jf.system.handler.rest;

import com.jf.exception.RestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Description: Rest Error处理器
 * User: xujunfei
 * Date: 2018-07-05
 * Time: 17:06
 */
public class RestErrorHandler extends DefaultResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return super.hasError(response);
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        HttpStatus statusCode = HttpStatus.resolve(response.getRawStatusCode());
        if (statusCode == null) {
            throw new RestException(-1);
        } else {
            throw new RestException(statusCode.value());
        }
    }

}
