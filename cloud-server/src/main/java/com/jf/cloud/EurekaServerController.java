package com.jf.cloud;

import com.jf.http.HttpUtil;
import com.jf.string.StringUtil;
import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.EurekaInstanceConfig;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.config.ConfigurationManager;
import com.netflix.discovery.shared.Application;
import com.netflix.eureka.EurekaServerContext;
import com.netflix.eureka.EurekaServerContextHolder;
import com.netflix.eureka.cluster.PeerEurekaNode;
import com.netflix.eureka.util.StatusInfo;
import com.netflix.eureka.util.StatusUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2018-02-01
 * Time: 15:15
 */
//@Controller
//@RequestMapping("/sba")
public class EurekaServerController {

    @Resource
    private ApplicationInfoManager manager;

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("logout");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            System.out.println("logout success");
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/sba/login.html?logout";
    }

    //@RequestMapping("/index")
    public String index(ModelMap map) {
        EurekaServerContext context = EurekaServerContextHolder.getInstance().getServerContext();
        EurekaInstanceConfig config = manager.getEurekaInstanceConfig();
        map.addAttribute("info", config);
        InstanceInfo instanceInfo = manager.getInfo();
        map.addAttribute("instanceInfo", instanceInfo);
        StatusInfo statusInfo = new StatusUtil(context).getStatusInfo();
        map.addAttribute("statusInfo", statusInfo);
        List<PeerEurekaNode> nodes = context.getPeerEurekaNodes().getPeerNodesView();
        map.addAttribute("nodes", nodes);

        map.addAttribute("currentTime", StatusInfo.getCurrentTimeAsString());
        map.addAttribute("upTime", StatusInfo.getUpTime());
        map.addAttribute("environment", ConfigurationManager.getDeploymentContext().getDeploymentEnvironment());
        map.addAttribute("datacenter", ConfigurationManager.getDeploymentContext().getDeploymentDatacenter());
        return "index";
    }

    @RequestMapping("/clients")
    public String clients(ModelMap map) {
        EurekaServerContext context = EurekaServerContextHolder.getInstance().getServerContext();
        List<Application> applications = context.getRegistry().getSortedApplications();
        map.addAttribute("applications", applications);
        return "clients";
    }

    @RequestMapping("/infos")
    public String infos(String instance, ModelMap map) {
        if (StringUtil.isBlank(instance)) {
            return "";
        }
        return "info";
    }

    @RequestMapping("/info")
    @ResponseBody
    public Object info(String monitor) {
        if (StringUtil.isBlank(monitor)) {
            return "";
        }
        String json = "";
        try {
            json = HttpUtil.getWithAuthorization(monitor, "spring:spring1234");
            return json;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
