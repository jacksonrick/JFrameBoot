package com.jf.system.third.jpush;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.ServiceHelper;
import cn.jiguang.common.connection.NettyHttpClient;
import cn.jiguang.common.resp.ResponseWrapper;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import com.jf.system.LogManager;
import com.jf.system.conf.SysConfig;
import io.netty.handler.codec.http.HttpMethod;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * Created by xujunfei on 2017/3/30.
 */
@Component
public class JPushService {

    @Resource
    private SysConfig config;

    private final String DEFAULT_ALERT = "JFRAME";

    @Async
    public void sendPush(String alert, String msg, String... id) {
        androidPush(alert, msg, null, id);
        iosPush(alert, msg, null, id);
    }

    @Async
    public void sendPush(String alert, String msg, Map<String, String> extras, String... id) {
        androidPush(alert, msg, extras, id);
        iosPush(alert, msg, extras, id);
    }

    @Async
    public void sendPush(String msg, Map<String, String> extras, String... id) {
        androidPush(DEFAULT_ALERT, msg, extras, id);
        iosPush(DEFAULT_ALERT, msg, extras, id);
    }

    // 注意： 不同APP的APPKEY是不一样的，需要进行标识区分，可以写死，也可以写在配置文件。

    /**
     * 安卓推送
     *
     * @param alert
     * @param msg
     * @param extras
     * @param id
     */
    private void androidPush(String alert, String msg, Map<String, String> extras, String... id) {
        String APP_KEY = config.getJpush().getAppkey(), MASTER_SECRET = config.getJpush().getSecret();
        ClientConfig clientConfig = ClientConfig.getInstance();
        String host = (String) clientConfig.get(ClientConfig.PUSH_HOST_NAME);
        final NettyHttpClient client = new NettyHttpClient(ServiceHelper.getBasicAuthorization(APP_KEY, MASTER_SECRET), null, clientConfig);
        try {
            URI uri = new URI(host + clientConfig.get(ClientConfig.PUSH_PATH));
            PushPayload payload = PushPayload.newBuilder()
                    .setPlatform(Platform.android())
                    .setAudience(Audience.tag(id))
                    //.setAudience(Audience.all())
                    //.setAudience(Audience.alias(id))
                    //.setAudience(Audience.registrationId(id))
                    .setNotification(Notification.android(alert, msg, extras)).build();
            client.sendRequest(HttpMethod.POST, payload.toString(), uri, new NettyHttpClient.BaseCallback() {
                @Override
                public void onSucceed(ResponseWrapper responseWrapper) {
                    LogManager.info(responseWrapper.toString(), JPushService.class);
                }
            });
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * IOS推送
     *
     * @param alert
     * @param msg
     * @param extras
     * @param id
     */
    private void iosPush(String alert, String msg, Map<String, String> extras, String... id) {
        String APP_KEY = config.getJpush().getAppkey(), MASTER_SECRET = config.getJpush().getSecret();
        ClientConfig clientConfig = ClientConfig.getInstance();
        String host = (String) clientConfig.get(ClientConfig.PUSH_HOST_NAME);
        final NettyHttpClient client = new NettyHttpClient(ServiceHelper.getBasicAuthorization(APP_KEY, MASTER_SECRET), null, clientConfig);
        try {
            URI uri = new URI(host + clientConfig.get(ClientConfig.PUSH_PATH));
            PushPayload payload2 = PushPayload.newBuilder()
                    .setPlatform(Platform.ios())
                    .setAudience(Audience.tag(id))
                    .setNotification(Notification.newBuilder().addPlatformNotification(IosNotification.newBuilder().setAlert(alert).addExtras(extras).build()).build())
                    .setMessage(Message.newBuilder().setMsgContent(msg).build())
                    .setOptions(Options.newBuilder().setApnsProduction(config.getJpush().getIosProduct()).build()).build();
            client.sendRequest(HttpMethod.POST, payload2.toString(), uri, new NettyHttpClient.BaseCallback() {
                @Override
                public void onSucceed(ResponseWrapper responseWrapper) {
                    LogManager.info(responseWrapper.toString(), JPushService.class);
                }
            });
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

}
