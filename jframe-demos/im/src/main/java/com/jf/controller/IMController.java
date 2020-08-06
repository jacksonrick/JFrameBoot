package com.jf.controller;

import com.github.pagehelper.PageInfo;
import com.jf.config.ResMsg;
import com.jf.config.wsm.ReadMessage;
import com.jf.database.model.Chat;
import com.jf.database.model.Message;
import com.jf.database.model.User;
import com.jf.service.IMService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2020-07-30
 * Time: 10:49
 */
@Controller
public class IMController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private SimpUserRegistry simpUserRegistry;

    @Resource
    private IMService imService;

    @GetMapping("/")
    public String index(ModelMap map) {
        Set<SimpUser> users = simpUserRegistry.getUsers();
        map.addAttribute("onlines", users);
        return "index";
    }

    /**
     * 聊天页面
     *
     * @param loginId 登陆的id
     * @param otherId 对话人的id
     * @param map
     * @return
     */
    @GetMapping("/im/chat")
    public String chat(Long loginId, Long otherId, ModelMap map) {
        if (loginId == null) {
            map.put("err", "登陆ID为空");
            return "err";
        }
        User user = imService.findUserById(loginId);
        if (user == null) {
            map.put("err", "用户不存在");
            return "err";
        }
        if (otherId != null && otherId > 0) {
            // 直接对话模式
            Integer chatNo = imService.findChatNoByUidAndOid(loginId, otherId);
            if (chatNo == null) {
                // 新建聊天室
                chatNo = imService.addChat(loginId, otherId);
            }
            map.addAttribute("chatNo", chatNo);
            map.addAttribute("otherId", otherId);
            // 获取对话人名称
            map.addAttribute("otherName", imService.findNameById(otherId));
        }
        map.addAttribute("userId", loginId);
        return "chat";
    }

    /**
     * 对话列表
     *
     * @param loginId
     * @return
     */
    @PostMapping("/im/chatList")
    @ResponseBody
    public List<Chat> chatList(Long loginId) {
        if (loginId == null) {
            return Collections.emptyList();
        }
        List<Chat> chatList = imService.findChatList(loginId);
        Set<SimpUser> users = simpUserRegistry.getUsers();
        for (Chat chat : chatList) {
            for (SimpUser user : users) {
                if (user.getName().equals(String.valueOf(chat.getOtherId()))) {
                    chat.setOnline(true);
                    break;
                }
            }
        }
        return chatList;
    }

    @PostMapping("/im/queryNickname")
    @ResponseBody
    public ResMsg queryNickname(Long userId) {
        if (userId == null) {
            return ResMsg.fail();
        }
        return ResMsg.successdata(imService.findNameById(userId));
    }

    /**
     * 历史消息列表
     *
     * @param chatNo
     * @param pageNo
     * @return
     */
    @PostMapping("/im/msgList")
    @ResponseBody
    public PageInfo<Message> msgList(Integer chatNo, Integer pageNo) {
        if (chatNo == null) {
            return null;
        }
        PageInfo<Message> messageList = imService.findMessageList(chatNo, pageNo);
        return messageList;
    }


    /**
     * 发送消息
     *
     * @param message 自定义的接收对象
     */
    @MessageMapping("/send")
    public void send(Message message) {
        //SocketPrincipal user = (SocketPrincipal) principal;
        Long msgId = imService.sendMsg(message.getChatNo(), message.getFromId(), message.getToId(), message.getContent(), message.getMsgType(), message.getExtra());
        if (msgId == 0) {
            return;
        }
        messagingTemplate.convertAndSendToUser(String.valueOf(message.getToId()), "/receive", message);
    }

    /**
     * 已读 消息｜对话
     *
     * @param message
     */
    @MessageMapping("/read")
    public void read(ReadMessage message) {
        if (message.getChatNo() == null || message.getLoginId() == null || message.getOtherId() == null) {
            return;
        }
        imService.readMsg(message.getChatNo(), message.getLoginId());
        //Long otherId = imService.findOidByChatNoAndUid(message.getChatNo(), message.getLoginId());
        Map<String, Object> params = new HashMap<>();
        params.put("chatNo", message.getChatNo());
        messagingTemplate.convertAndSendToUser(message.getOtherId(), "/read", params);
    }


    /**
     * 未读消息数
     *
     * @param loginId
     * @return
     */
    @PostMapping("/im/unread")
    @ResponseBody
    public ResMsg unread(Long loginId) {
        if (loginId == null) {
            return ResMsg.fail();
        }
        return ResMsg.successdata(imService.findUnReadMsgCount(loginId));
    }

    /**
     * 图片上传（示例）
     *
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/upload")
    @ResponseBody
    public Map<String, Object> upload(@RequestParam("file") MultipartFile file) throws IOException {
        Map<String, Object> map = new HashMap<>();
        String filename = randomFilename(file.getOriginalFilename());
        FileUtils.copyInputStreamToFile(file.getInputStream(), new File("/Users/xujunfei/Downloads/tmp/static/upload/202007/", filename));
        map.put("error", 0);
        map.put("url", "/static/upload/202007/" + filename);
        map.put("message", "SUCCESS");
        return map;
    }

    static String staticPath = "/Users/xujunfei/Downloads/tmp";

    /**
     * 语音上传（示例）
     *
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/uploadAudio")
    @ResponseBody
    public Map<String, Object> uploadAudio(@RequestParam("file") MultipartFile file) throws IOException {
        Map<String, Object> map = new HashMap<>();
        String filename = System.currentTimeMillis() + "" + (int) (Math.random() * 90000 + 10000) + ".mp3";
        FileUtils.copyInputStreamToFile(file.getInputStream(), new File(staticPath + "/static/upload/202007/audio/", filename));
        map.put("error", 0);
        map.put("url", "/static/upload/202007/audio/" + filename);
        map.put("message", "SUCCESS");
        return map;
    }

    static String randomFilename(String file) {
        String temp = file.substring(file.lastIndexOf("."));
        return System.currentTimeMillis() + "" + (int) (Math.random() * 90000 + 10000) + temp;
    }

    /**
     * 群发
     *
     * @return
     */
    /*@MessageMapping("/sendall")
    public void sendall() {
        SocketMessage message = new SocketMessage();
        message.setDate(new Date());
        message.setUsername("SYSTEM");
        message.setMessage("Hello Everyone!");
        Set<SimpUser> sets = simpUserRegistry.getUsers();
        System.out.println("sendall count: " + sets.size());
        for (SimpUser user : sets) {
            messagingTemplate.convertAndSendToUser(user.getName(), "/chat", message);
        }
    }*/
}
