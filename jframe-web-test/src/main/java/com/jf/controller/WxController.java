package com.jf.controller;

import com.jf.entity.ResMsg;
import com.jf.http.HttpUtil;
import com.jf.json.JSONUtils;
import com.jf.string.StringUtil;
import com.jf.system.third.wechat.SignUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * Description: 微信API测试
 * User: xujunfei
 * Date: 2018-01-16
 * Time: 10:34
 */
@Controller
public class WxController extends BaseController {


    @RequestMapping("/wx")
    @ResponseBody
    public String wx(String signature, String timestamp, String nonce, String openid, HttpServletRequest request) {
        Map<String, String[]> parameters = request.getParameterMap();
        Set<String> keys = parameters.keySet();
        String _para = "";
        for (String key : keys) {
            String[] params = parameters.get(key);
            System.out.println(key + "=" + params[0]);
        }
        if (SignUtil.checkSignature(signature, timestamp, nonce)) {
            String str = "<xml> <ToUserName>" + openid + "</ToUserName> " +
                    "<FromUserName>iframeworld</FromUserName> " +
                    "<CreateTime>" + (System.currentTimeMillis() / 1000) + "</CreateTime> " +
                    "<MsgType>text</MsgType> " +
                    "<Content>Hello</Content> </xml>";
            return str;
        }
        return "";
    }

    @RequestMapping("/createMenu")
    @ResponseBody
    public ResMsg createMenu() throws Exception {
        String token = getAccessToken();
        if (StringUtil.isBlank(token)) {
            return new ResMsg(-1, "获取Token失败");
        }
        String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + token;
        String menu = "{\n" +
                "     \"button\":[\n" +
                "     {    \n" +
                "          \"type\":\"click\",\n" +
                "          \"name\":\"今日歌曲\",\n" +
                "          \"key\":\"V1001_TODAY_MUSIC\"\n" +
                "      },\n" +
                "      {\n" +
                "           \"name\":\"菜单\",\n" +
                "           \"sub_button\":[\n" +
                "           {    \n" +
                "               \"type\":\"view\",\n" +
                "               \"name\":\"搜索\",\n" +
                "               \"url\":\"http://www.soso.com/\"\n" +
                "            },\n" +
                "            {\n" +
                "                 \"type\":\"miniprogram\",\n" +
                "                 \"name\":\"wxa\",\n" +
                "                 \"url\":\"http://mp.weixin.qq.com\",\n" +
                "                 \"appid\":\"wxfe9a8aecbd3506b5\",\n" +
                "                 \"pagepath\":\"pages/lunar/index\"\n" +
                "             },\n" +
                "            {\n" +
                "               \"type\":\"click\",\n" +
                "               \"name\":\"赞一下我们\",\n" +
                "               \"key\":\"V1001_GOOD\"\n" +
                "            }]\n" +
                "       }]\n" +
                " }";
        try {
            String result = HttpUtil.post(url, menu);
            Map<String, String> map = JSONUtils.toHashMap(result);
            System.out.println(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResMsg(0, SUCCESS);
    }


    private String getAccessToken() throws Exception {
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wxfe9a8aecbd3506b5&secret=512dce03bb3305162632e1bdc9fc0e8f";
        String result = HttpUtil.get(url);
        Map<String, String> map = JSONUtils.toHashMap(result);
        return map.get("access_token");
    }
}
