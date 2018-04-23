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
import com.jf.system.conf.LogManager;
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

    /**
     * TAG ID推送
     *
     * @param devive
     * @param alert
     * @param extras
     * @param id     String...
     */
    @Async
    public void sendPush(int devive, String alert, Map<String, String> extras, String... id) {
        androidPush(devive, alert, extras, id);
        iosPush(devive, alert, extras, id);
    }

    private final Boolean iosProduct = false; // IOS开发环境-false，生产环境-true
    private final String alert = "JFRAME";

    // 注意： 不同APP的APPKEY是不一样的，需要进行标识区分，可以写死，也可以写在配置文件。

    /**
     * 安卓推送
     *
     * @param device
     * @param content
     * @param extras
     * @param id
     */
    private void androidPush(int device, String content, Map<String, String> extras, String... id) {
        String APP_KEY = "", MASTER_SECRET = "";
        if (device == 1) {
            APP_KEY = config.getJpush().getAppkey1();
            MASTER_SECRET = config.getJpush().getSecret1();
        } else if (device == 2) {

        } else {
            return;
        }

        ClientConfig clientConfig = ClientConfig.getInstance();
        String host = (String) clientConfig.get(ClientConfig.PUSH_HOST_NAME);
        final NettyHttpClient client = new NettyHttpClient(ServiceHelper.getBasicAuthorization(APP_KEY, MASTER_SECRET), null, clientConfig);
        try {
            URI uri = new URI(host + clientConfig.get(ClientConfig.PUSH_PATH));
            PushPayload payload = PushPayload.newBuilder()
                    .setPlatform(Platform.android())
                    .setAudience(Audience.tag(id))
                    //.setAudience(Audience.registrationId(id))
                    .setNotification(Notification.android(alert, content, extras)).build();
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
     * @param device
     * @param content
     * @param extras
     * @param id
     */
    private void iosPush(int device, String content, Map<String, String> extras, String... id) {
        String APP_KEY = "", MASTER_SECRET = "";
        if (device == 1) {
            APP_KEY = config.getJpush().getAppkey1();
            MASTER_SECRET = config.getJpush().getSecret1();
        } else if (device == 2) {

        } else {
            return;
        }

        ClientConfig clientConfig = ClientConfig.getInstance();
        String host = (String) clientConfig.get(ClientConfig.PUSH_HOST_NAME);
        final NettyHttpClient client = new NettyHttpClient(ServiceHelper.getBasicAuthorization(APP_KEY, MASTER_SECRET), null, clientConfig);
        try {
            URI uri = new URI(host + clientConfig.get(ClientConfig.PUSH_PATH));
            PushPayload payload2 = PushPayload.newBuilder()
                    .setPlatform(Platform.ios())
                    .setAudience(Audience.tag(id))
                    //.setAudience(Audience.registrationId(id))
                    .setNotification(Notification.newBuilder().addPlatformNotification(IosNotification.newBuilder().setAlert(alert).addExtras(extras).build()).build())
                    .setMessage(Message.newBuilder().setMsgContent(content).build())
                    .setOptions(Options.newBuilder().setApnsProduction(iosProduct).build()).build();
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
