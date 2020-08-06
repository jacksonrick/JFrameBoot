package com.jf.database.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户
 *
 * @date 2020年07月30日 上午 10:42:59
 * @author jfxu
 */
public class User extends BaseVo implements Serializable {

	private static final long serialVersionUID = 1L;

	/** id */
	private Long id;

	/** 昵称 */
	private String nickname;

	public User() {
	}

	public User(Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNickname() {
		return this.nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", nickname='" + nickname + '\'' +
				'}';
	}
}
