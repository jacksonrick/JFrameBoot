package com.jf.srvs;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2019-05-07
 * Time: 10:54
 */
public interface ServiceRegistry {

    void register(String serviceName, String serviceAddress);

    ArrayList<String> getValue(String path);

}
