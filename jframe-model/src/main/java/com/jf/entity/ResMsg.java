package com.jf.entity;

/**
 * 前台返回结果封装类
 * 
 * @author rick
 */
public class ResMsg {

	/** 返回码 */
	private Integer code;

	/** 返回信息 */
	private String msg;

	/** 数据 */
	private Object data;

	public ResMsg(String msg) {
		super();
		this.code = 1;
		this.msg = msg;
	}

	public ResMsg(Integer code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}

	public ResMsg(Integer code, String msg, Object data) {
		super();
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "ResMsg [code=" + code + ", msg=" + msg + ", data=" + data + "]";
	}

}
