package com.jf.entity;

/**
 * 上传返回JSON
 * @author rick
 */
public class UploadRet {

	private Integer error;

	private String url;

	private String message;

	public UploadRet() {
	}

	public UploadRet(Integer error, String url, String message) {
		super();
		this.error = error;
		this.url = url;
		this.message = message;
	}

	public Integer getError() {
		return error;
	}

	public void setError(Integer error) {
		this.error = error;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
