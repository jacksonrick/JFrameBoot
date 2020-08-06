package com.jf.database.model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @date 2020年07月30日 上午 10:42:59
 * @author jfxu
 */
public class Message extends BaseVo implements Serializable {

	private static final long serialVersionUID = 1L;

	/** id */
	private Long id;

	/** 聊天室编号 */
	private Integer chatNo;

	/** 发信人id */
	private Long fromId;

	/** 收信人id */
	private Long toId;

	/** 内容 */
	private String content;

	/** txt,pic */
	private String msgType;

	/** 收信人已读 */
	private Boolean readed;

	/** createTime */
	private Date createTime;

	/** 额外数据 */
	private String extra;

	public Message() {
	}

	public Message(Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getChatNo() {
		return chatNo;
	}

	public void setChatNo(Integer chatNo) {
		this.chatNo = chatNo;
	}

	public Long getFromId() {
		return fromId;
	}

	public void setFromId(Long fromId) {
		this.fromId = fromId;
	}

	public Long getToId() {
		return toId;
	}

	public void setToId(Long toId) {
		this.toId = toId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public Boolean getReaded() {
		return readed;
	}

	public void setReaded(Boolean readed) {
		this.readed = readed;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	@Override
	public String toString() {
		return "Message{" +
				"id=" + id +
				", chatNo=" + chatNo +
				", fromId=" + fromId +
				", toId=" + toId +
				", content='" + content + '\'' +
				", msgType='" + msgType + '\'' +
				", readed=" + readed +
				", createTime=" + createTime +
				", extra=" + extra +
				'}';
	}
}
