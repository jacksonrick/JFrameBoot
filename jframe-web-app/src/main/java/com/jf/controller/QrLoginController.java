package com.jf.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jf.entity.ResMsg;
import com.jf.entity.enums.ResCode;
import com.jf.file.Qrcode;
import com.jf.string.StringUtil;
import com.jf.system.conf.RestClient;
import com.jf.system.utils.Constant;
import com.jf.system.utils.CookieUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * Description: 微信扫码
 * User: xujunfei
 * Date: 2019-03-22
 * Time: 14:12
 */
@Controller
public class QrLoginController {

    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private RestClient restClient;

    // 适用于
    // 1.手机APP扫码登陆
    // 2.后台管理系统登陆

    /**
     * 页面
     *
     * @param response
     * @return
     */
    @GetMapping("/qr")
    public String qr(HttpServletResponse response) {
        String sessionid = StringUtil.getTokenId(); // 每刷新一次都会重新生成
        CookieUtils.setCookie(response, Constant.SESSIONID, sessionid);
        return "wx/qr";
    }

    /**
     * 二维码生成
     *
     * @param response
     * @param request
     */
    @GetMapping("/qrimg")
    public void qrimg(String type, HttpServletResponse response, HttpServletRequest request) throws Exception {
        if (StringUtil.isBlank(type)) {
            return;
        }
        String sessionid = CookieUtils.getCookie(request, Constant.SESSIONID); // 获取sessionid
        String qrstr = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + Constant.APPID +
                "&redirect_uri=http%3a%2f%2fwx.809573150.cn%2fqrback%3fsessionid%3d" + sessionid +
                "&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect";

        Map<String, String> map = new HashMap();
        // uid为用户id，confirm为确认状态
        map.put(Constant.STR_CONFIRM, Constant.Confirm.Scaning.value()); // 0-初始值 1-已扫码 2-确认 3-取消
        map.put(Constant.STR_TYPE, type); // value: login,bind,...
        if (Constant.Type.Login.value().equals(type)) {
            map.put(Constant.STR_UID, "0"); // 0-初始值
        } else if (Constant.Type.Bind.value().equals(type)) {
            map.put(Constant.STR_UID, "002"); // 获取到已登陆用户id
        } else {
            return;
        }
        redisTemplate.opsForHash().putAll(sessionid, map); // key为sessionid
        redisTemplate.expire(sessionid, 60, TimeUnit.SECONDS); // 有效期60秒

        Qrcode.write(qrstr, response);
    }

    /**
     * 扫码后跳转【微信页面】
     *
     * @param code
     * @param state
     * @param sessionid
     * @param map
     * @return
     * @throws IOException
     */
    @GetMapping("/qrback")
    public String qrback(String code, String state, String sessionid, ModelMap map) {
        if (StringUtil.isBlank(sessionid)) {
            map.addAttribute("msg", "sessionid error");
            return "wx/qr_confirm";
        }

        Map<String, String> keys = getKeys(sessionid);
        if (Constant.STR_EXPIRE.equals(keys.get(Constant.STR_STATE))) {
            map.addAttribute("msg", "二维码已失效");
            return "wx/qr_confirm";
        } else {
            String confirmed = keys.get(Constant.STR_CONFIRM);
            String type = keys.get(Constant.STR_TYPE);
            if (Constant.Confirm.Scaning.value().equals(confirmed)) {
                // ...
                redisTemplate.opsForHash().put(sessionid, Constant.STR_CONFIRM, Constant.Confirm.Scaned.value()); // 状态 - 已扫码

                map.addAttribute("type", Constant.Type.get(type));
            }
            map.addAttribute("code", code);
            map.addAttribute("sessionid", sessionid);
        }
        return "wx/qr_confirm";
    }

    /**
     * 扫码后跳转【用户操作】
     *
     * @param code
     * @param state
     * @param sessionid
     * @param confirm
     * @return
     */
    @GetMapping("/qrconfirm")
    public String qrconfirm(String code, String state, String sessionid, Boolean confirm, ModelMap model) throws IOException {
        if (StringUtil.isBlank(sessionid)) {
            model.addAttribute("msg", "sessionid error");
            return "wx/qr_confirm_result";
        }
        // 查询sessionid
        Map<String, String> keys = getKeys(sessionid);
        if (Constant.STR_EXPIRE.equals(keys.get(Constant.STR_STATE))) {
            model.addAttribute("msg", "二维码已失效");
            return "wx/qr_confirm_result";
        }
        if (!confirm) {
            redisTemplate.opsForHash().put(sessionid, Constant.STR_CONFIRM, Constant.Confirm.Cancel.value()); // 状态 - 已取消
            model.addAttribute("msg", "您已取消扫码确认");
            return "wx/qr_confirm_result";
        }

        // 以下两个是微信接口，获取openid和微信用户信息
        String tokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + Constant.APPID + "&SECRET=" + Constant.SECRET +
                "&code=" + code + "&grant_type=authorization_code";
        ResMsg res = restClient.request(tokenUrl, HttpMethod.GET);
        if (ResCode.HTTP_OK.code().equals(res.getCode())) {
            ObjectMapper mapper = new ObjectMapper();
            Map map = mapper.readValue(String.valueOf(res.getData()), Map.class);
            System.out.println(map);

            String access_token = String.valueOf(map.get("access_token"));
            String openid = String.valueOf(map.get("openid"));
            if (!"null".equals(access_token) && !"null".equals(openid)) {
                String openidUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=" + access_token + "&openid=" + openid;
                ResMsg res2 = restClient.request(openidUrl, HttpMethod.GET);
                if (ResCode.HTTP_OK.code().equals(res2.getCode())) {
                    ObjectMapper mapper2 = new ObjectMapper();
                    Map map2 = mapper2.readValue(String.valueOf(res2.getData()), Map.class);
                    System.out.println(map2);

                    String uid = keys.get(Constant.STR_UID);
                    String confirmed = keys.get(Constant.STR_CONFIRM);
                    String type = keys.get(Constant.STR_TYPE);
                    // 先判断是否已经被确认
                    // ...

                    // 确认状态
                    redisTemplate.opsForHash().put(sessionid, Constant.STR_CONFIRM, Constant.Confirm.Confirmed.value());
                    if (Constant.Type.Login.value().equals(type)) {
                        // 假设从数据库查询到openid对应到用户 openid => uid=001
                        // 查询到用户id
                        redisTemplate.opsForHash().put(sessionid, Constant.STR_UID, "001");
                        model.addAttribute("msg", map2.get("nickname") + "已扫码授权登陆");
                    } else if (Constant.Type.Bind.value().equals(type)) {
                        // 数据库操作，将uid和openid进行绑定
                        model.addAttribute("msg", map2.get("nickname") + "已扫码授权绑定" + "，用户ID：" + uid);
                    }
                }
            }
        }
        return "wx/qr_confirm_result";
    }

    /**
     * 检查扫码状态
     *
     * @param response
     * @param request
     * @return
     */
    @GetMapping("/checkqr")
    @ResponseBody
    public ResMsg checkqr(HttpServletResponse response, HttpServletRequest request) {
        String sessionid = CookieUtils.getCookie(request, Constant.SESSIONID); // 获取sessionid
        if (StringUtil.isNotBlank(sessionid)) {
            // 查询sessionid
            Map<String, String> keys = getKeys(sessionid);
            if (Constant.STR_EXPIRE.equals(keys.get(Constant.STR_STATE))) {
                redisTemplate.delete(sessionid);
                CookieUtils.delCookie(response, Constant.SESSIONID);
                return ResMsg.fail(5, "二维码已失效");
            } else {
                String uid = keys.get(Constant.STR_UID);
                String confirmed = keys.get(Constant.STR_CONFIRM);
                String type = keys.get(Constant.STR_TYPE);

                // 删除sessionid
                if (!Constant.Confirm.Scaning.value().equals(confirmed) && !Constant.Confirm.Scaned.value().equals(confirmed)) {
                    redisTemplate.delete(sessionid);
                    CookieUtils.delCookie(response, Constant.SESSIONID);
                }

                if (Constant.Confirm.Scaning.value().equals(confirmed)) {
                    return ResMsg.fail(1, "扫码中……");
                } else if (Constant.Confirm.Scaned.value().equals(confirmed)) {
                    return ResMsg.fail(2, "已扫码……");
                } else if (Constant.Confirm.Confirmed.value().equals(confirmed)) {
                    // 扫码&确认成功

                    if (Constant.Type.Login.value().equals(type)) {
                        // 重新生成新的后台系统token和UID进行绑定
                        String newToken = StringUtil.getTokenId();
                        redisTemplate.opsForValue().set(newToken, uid, Duration.ofSeconds(3600));
                        // 写入cookie【此cookie用于登陆状态】
                        CookieUtils.setCookie(response, Constant.TOKEN, newToken);
                        return ResMsg.successdata("/qresult?type=login&uid=" + uid);
                    } else if (Constant.Type.Bind.value().equals(type)) {
                        return ResMsg.successdata("/qresult?type=bind&uid=" + uid);
                    }
                } else if (Constant.Confirm.Cancel.value().equals(confirmed)) {
                    return ResMsg.fail(3, "用户取消操作");
                } else {
                    return ResMsg.fail(4, "确认状态错误");
                }
            }
        }
        return ResMsg.fail(5, "sessionid[cookie]失效");
    }

    /**
     * 扫码成功跳转
     *
     * @param type
     * @param uid
     * @param request
     * @param model
     * @return
     */
    @GetMapping("/qresult")
    public String qresult(String type, String uid, HttpServletRequest request, ModelMap model) {
        if (Constant.Type.Login.value().equals(type)) {
            String token = CookieUtils.getCookie(request, Constant.TOKEN);
            if (StringUtil.isBlank(token)) {
                model.addAttribute("msg", "登陆失败");
            } else {
                model.addAttribute("token", token);
                model.addAttribute("uid", uid);
                model.addAttribute("msg", "登陆成功");
            }
            return "wx/qr_result_login";
        } else if (Constant.Type.Bind.value().equals(type)) {
            model.addAttribute("uid", uid);
            model.addAttribute("msg", "绑定成功");
            return "wx/qr_result_bind";
        }
        return "";
    }


    private Map<String, String> getKeys(String sessionid) {
        Map<String, String> map = new HashMap<>();
        map.put(Constant.STR_UID, "");
        map.put(Constant.STR_CONFIRM, "");
        map.put(Constant.STR_TYPE, "");
        List<String> values = redisTemplate.opsForHash().multiGet(sessionid, Constant.KEYS);
        if (!values.isEmpty() && values.size() == 3) {
            String uid = values.get(0);
            String confirm = values.get(1);
            String type = values.get(2);
            System.out.println(uid + "--" + confirm + "--" + type);
            if (uid != null && confirm != null) {
                map.put(Constant.STR_UID, uid);
                map.put(Constant.STR_CONFIRM, confirm);
                map.put(Constant.STR_TYPE, type);
            } else {
                map.put(Constant.STR_STATE, Constant.STR_EXPIRE);
            }
        }
        return map;
    }

}
