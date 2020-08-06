package com.jf.database.model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @date 2020年07月30日 下午 16:36:38
 * @author jfxu
 */
public class Chat implements Serializable {

	private static final long serialVersionUID = 1L;

	/** id */
	//@JsonSerialize(using = ToStringSerializer.class)
	private Long id;

	/** 编号 */
	private Integer chatNo;

	/** 用户1id */
	private Long userId;

	/** 用户2id */
	private Long otherId;
	private String otherName;

	private Integer unread;

	// 最后一条消息
	private Message lastMsg;

	private boolean online;

	public Chat() {
	}

	public Chat(Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getOtherId() {
		return otherId;
	}

	public void setOtherId(Long otherId) {
		this.otherId = otherId;
	}

    public String getOtherName() {
        return otherName;
    }

    public void setOtherName(String otherName) {
        this.otherName = otherName;
    }

    public Integer getChatNo() {
        return chatNo;
    }

    public void setChatNo(Integer chatNo) {
        this.chatNo = chatNo;
    }

	public Integer getUnread() {
		return unread;
	}

	public void setUnread(Integer unread) {
		this.unread = unread;
	}

	public Message getLastMsg() {
        return lastMsg;
    }

    public void setLastMsg(Message lastMsg) {
        this.lastMsg = lastMsg;
    }

	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}

	@Override
    public String toString() {
        return "Chat{" +
                "id=" + id +
                ", chatNo=" + chatNo +
                ", userId=" + userId +
                ", otherId=" + otherId +
                ", otherName=" + otherName +
                ", unread=" + unread +
                ", lastMsg=" + lastMsg +
                ", online=" + online +
                '}';
    }
}
