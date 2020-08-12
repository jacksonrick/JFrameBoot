package com.jf.service;

import com.github.pagehelper.PageInfo;
import com.jf.database.mapper.ChatMapper;
import com.jf.database.mapper.MessageMapper;
import com.jf.database.mapper.UserMapper;
import com.jf.database.model.Chat;
import com.jf.database.model.Message;
import com.jf.database.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2020-07-30
 * Time: 10:50
 */
@Service
public class IMService {

    // 消息类型
    public static final String MSG_TYPE_TXT = "txt";
    public static final String MSG_TYPE_PIC = "pic";
    public static final String MSG_TYPE_PIC_MARK = "[图片]";
    public static final String MSG_TYPE_AUD = "aud";
    public static final String MSG_TYPE_AUD_MARK = "[语音]";
    public static final String MSG_TYPE_LINK = "link";
    public static final String MSG_TYPE_LINK_MARK = "[链接]";

    @Resource
    private ChatMapper chatMapper;
    @Resource
    private MessageMapper messageMapper;
    @Resource
    private UserMapper userMapper;


    /**
     * @param id
     * @return
     */
    public User findUserById(Long id) {
        return userMapper.findById(id);
    }

    /**
     * 获取用户昵称
     *
     * @param id
     * @return
     */
    public String findNameById(Long id) {
        String name = userMapper.findNameById(id);
        if (StringUtils.isEmpty(name)) {
            return String.valueOf(id);
        }
        return name;
    }


    /**
     * 对话列表
     *
     * @param meId
     * @return
     */
    public List<Chat> findChatList(Long userId) {
        List<Chat> list = chatMapper.findByUserId(userId);
        for (Chat chat : list) {
            if (StringUtils.isEmpty(chat.getOtherName())) {
                // 默认名称
                chat.setOtherName(String.valueOf(chat.getOtherId()));
            }
            // 处理最后消息
            if (chat.getLastMsg() == null) {
                continue;
            }
            if (MSG_TYPE_TXT.equals(chat.getLastMsg().getMsgType())) {
                String content = chat.getLastMsg().getContent().length() > 15 ? chat.getLastMsg().getContent().substring(0, 15) + "..." : chat.getLastMsg().getContent();
                chat.getLastMsg().setContent(content);
            } else if (MSG_TYPE_PIC.equals(chat.getLastMsg().getMsgType())) {
                chat.getLastMsg().setContent(MSG_TYPE_PIC_MARK);
            } else if (MSG_TYPE_AUD.equals(chat.getLastMsg().getMsgType())) {
                chat.getLastMsg().setContent(MSG_TYPE_AUD_MARK);
            } else if (MSG_TYPE_LINK.equals(chat.getLastMsg().getMsgType())) {
                chat.getLastMsg().setContent(MSG_TYPE_LINK_MARK);
            }
        }
        return list;
    }

    /**
     * 添加对话
     *
     * @param userId
     * @param otherId
     * @return
     */
    @Transactional
    public Integer addChat(Long userId, Long otherId) {
        Integer chatNo = this.findMaxChatNo();
        Chat chat = new Chat();
        chat.setChatNo(chatNo);
        chat.setUserId(userId);
        chat.setOtherId(otherId);
        chatMapper.insert(chat);
        // 双向记录
        Chat chat2 = new Chat();
        chat2.setChatNo(chatNo);
        chat2.setUserId(otherId);
        chat2.setOtherId(userId);
        chatMapper.insert(chat2);
        return chatNo;
    }

    /**
     * 查询聊天室
     *
     * @param userId
     * @param otherId
     * @return
     */
    public Chat findChatByUidAndOid(Long userId, Long otherId) {
        Chat chat = chatMapper.findChatByUidAndOid(userId, otherId);
        if (chat == null) {
            return null;
        }
        if (StringUtils.isEmpty(chat.getOtherName())) {
            chat.setOtherName(String.valueOf(chat.getOtherId()));
        }
        return chat;
    }

    /**
     * 查询聊天室编号
     *
     * @param userId
     * @param otherId
     * @return
     */
    public Integer findChatNoByUidAndOid(Long userId, Long otherId) {
        return chatMapper.findChatNoByUidAndOid(userId, otherId);
    }

    public Long findOidByChatNoAndUid(Integer chatNo, Long userId) {
        return chatMapper.findOidByChatNoAndUid(chatNo, userId);
    }

    public Long findUidByChatNoAndOid(Integer chatNo, Long otherId) {
        return chatMapper.findUidByChatNoAndOid(chatNo, otherId);
    }

    /**
     * 查询最大聊天编号
     * 可自定义，这里是递增的
     *
     * @return
     */
    public Integer findMaxChatNo() {
        Integer max = chatMapper.findMaxChatNo();
        if (max == null) {
            return 100;
        }
        return max + 1;
    }

    /**
     * 历史消息列表
     *
     * @param chatNo
     * @param pageNo
     * @return
     */
    public PageInfo<Message> findMessageList(Integer chatNo, Integer pageNo) {
        Message condition = new Message();
        condition.setPageNo(pageNo);
        condition.setPage(true);
        condition.setPageSize(20);
        condition.setChatNo(chatNo);
        List<Message> list = messageMapper.findByCondition(condition);
        return new PageInfo<>(list);
    }


    /**
     * 新增消息
     *
     * @param chatNo
     * @param fromId
     * @param toId
     * @param content
     * @param msgType
     * @param extra
     */
    public Long sendMsg(Integer chatNo, Long fromId, Long toId, String content, String msgType, String extra) {
        Message message = new Message();
        message.setChatNo(chatNo);
        message.setFromId(fromId);
        message.setToId(toId);
        message.setContent(content);
        message.setMsgType(msgType);
        message.setExtra(extra);
        if (messageMapper.insert(message) > 0) {
            // 更新最后消息
            chatMapper.update(chatNo, message.getId());
            return message.getId();
        }
        return 0L;
    }

    /**
     * 消息已读标记
     *
     * @param msgId
     */
    public void readMsg(Long msgId) {
        messageMapper.update(msgId);
    }

    /**
     * 批量更新已读
     *
     * @param chatNo
     * @param toId
     */
    public void readMsg(Integer chatNo, Long toId) {
        messageMapper.update2(chatNo, toId);
    }

    /**
     * 未读数量
     *
     * @param loginId
     * @return
     */
    public int findUnReadMsgCount(Long userId) {
        return messageMapper.findUnReadMsgCount(userId);
    }
}
