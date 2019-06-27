package com.jf.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jf.entity.ResMsg;
import com.jf.database.enums.ResCode;
import com.jf.obj.XmlUtil;
import com.jf.string.StringUtil;
import com.jf.system.conf.RestClient;
import com.jf.system.utils.Constant;
import com.jf.system.utils.CookieUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description: 微信公众号测试
 * User: xujunfei
 * Date: 2018-09-18
 * Time: 11:45
 */
@Controller
public class WxController {

    @Resource
    private RestClient restClient;

    @RequestMapping("/index")
    public String index(String a, HttpServletRequest request) {
        String openid = CookieUtils.getCookie(request, "openid");
        if (StringUtil.isBlank(openid) || StringUtil.isNotBlank(a)) {
            return "redirect:https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + Constant.APPID +
                    "&redirect_uri=http%3a%2f%2fwx.809573150.cn%2foauth&response_type=value&scope=snsapi_userinfo&state=1#wechat_redirect";
        }
        return "wx/index";
    }

    @RequestMapping("/oauth")
    public String oauth(String code, String state, HttpServletResponse response) throws IOException {
        String tokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + Constant.APPID + "&SECRET=" + Constant.SECRET +
                "&value=" + code + "&grant_type=authorization_code";

        ResMsg res = restClient.request(tokenUrl, HttpMethod.GET);
        if (ResCode.HTTP_OK.code().equals(res.getCode())) {
            ObjectMapper mapper = new ObjectMapper();
            Map map = mapper.readValue(String.valueOf(res.getData()), Map.class);
            System.out.println(map);

            String access_token = String.valueOf(map.get("access_token"));
            String openid = String.valueOf(map.get("openid"));
            if (!"null".equals(access_token) && !"null".equals(openid)) {
                CookieUtils.setCookie(response, "access_token", access_token);
                CookieUtils.setCookie(response, "openid", openid);
            }
        } else {
            return "error/500";
        }
        return "redirect:user";
    }

    @RequestMapping("/user")
    public String user() {
        return "wx/user";
    }

    @RequestMapping("/userinfo")
    @ResponseBody
    public ResMsg userinfo(HttpServletRequest request) throws IOException {
        String access_token = CookieUtils.getCookie(request, "access_token");
        String openid = CookieUtils.getCookie(request, "openid");
        String openidUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=" + access_token + "&openid=" + openid;
        ResMsg res = restClient.request(openidUrl, HttpMethod.GET);
        if (ResCode.HTTP_OK.code().equals(res.getCode())) {
            ObjectMapper mapper = new ObjectMapper();
            Map map = mapper.readValue(String.valueOf(res.getData()), Map.class);
            System.out.println(map);
            return new ResMsg(ResCode.SUCCESS.code(), ResCode.SUCCESS.msg(), map);
        } else {
            return res;
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public String doGet(String timestamp, String nonce, String echostr, String signature) {
        return echostr;
    }

    @RequestMapping(value = "/", produces = {"application/xml;charset=UTF-8"}, method = RequestMethod.POST)
    @ResponseBody
    public String doPost(HttpServletRequest request) throws Exception {
        String wxMsgXml = IOUtils.toString(request.getInputStream());
        System.out.println(wxMsgXml);
        Msgs msg = XmlUtil.deSerializeFromXml(wxMsgXml, "xml", Msgs.class);
        System.out.println(msg);

        Msgs msgs = new Msgs();
        msgs.setToUserName(msg.getFromUserName());
        msgs.setFromUserName(msg.getToUserName());
        msgs.setCreateTime(new Date().getTime() + "");
        msgs.setMsgType("text");
        msgs.setContent("http://wx.809573150.cn/index");
        return XmlUtil.serializeToXml(msgs, "xml");
    }

    @XmlRootElement(name = "xml")
    static class Msgs {
        private String ToUserName;
        private String FromUserName;
        private String CreateTime;
        private String Content;
        private String MsgType;
        private String MsgId;

        public String getToUserName() {
            return ToUserName;
        }

        public void setToUserName(String toUserName) {
            ToUserName = toUserName;
        }

        public String getFromUserName() {
            return FromUserName;
        }

        public void setFromUserName(String fromUserName) {
            FromUserName = fromUserName;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String createTime) {
            CreateTime = createTime;
        }

        public String getContent() {
            return Content;
        }

        public void setContent(String content) {
            Content = content;
        }

        public String getMsgType() {
            return MsgType;
        }

        public void setMsgType(String msgType) {
            MsgType = msgType;
        }

        public String getMsgId() {
            return MsgId;
        }

        public void setMsgId(String msgId) {
            MsgId = msgId;
        }

        @Override
        public String toString() {
            return "Msgs{" +
                    "ToUserName='" + ToUserName + '\'' +
                    ", FromUserName='" + FromUserName + '\'' +
                    ", CreateTime='" + CreateTime + '\'' +
                    ", Content='" + Content + '\'' +
                    ", MsgType='" + MsgType + '\'' +
                    ", MsgId='" + MsgId + '\'' +
                    '}';
        }
    }

}
