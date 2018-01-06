package com.alipay.api.domain;

import java.util.List;

import com.alipay.api.AlipayObject;
import com.alipay.api.internal.mapping.ApiField;
import com.alipay.api.internal.mapping.ApiListField;

/**
 * alipay.open.public. message.total.send（群发消息）
 *
 * @author auto create
 * @since 1.0, 2017-01-06 11:37:37
 */
public class AlipayOpenPublicMessageTotalSendModel extends AlipayObject {

	private static final long serialVersionUID = 3527856795322329592L;

	/**
	 * 图文消息，当msg_type为image-text，该值必须设置
	 */
	@ApiListField("articles")
	@ApiField("article")
	private List<Article> articles;

	/**
	 * 消息类型，text：文本消息，image-text：图文消息
	 */
	@ApiField("msg_type")
	private String msgType;

	/**
	 * 文本消息内容，当msg_type为text，必须设置该值
	 */
	@ApiField("text")
	private Text text;

	public List<Article> getArticles() {
		return this.articles;
	}
	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}

	public String getMsgType() {
		return this.msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public Text getText() {
		return this.text;
	}
	public void setText(Text text) {
		this.text = text;
	}

}
