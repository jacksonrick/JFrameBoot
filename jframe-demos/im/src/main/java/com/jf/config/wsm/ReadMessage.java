package com.jf.config.wsm;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2020-08-04
 * Time: 13:46
 */
public class ReadMessage {

    private Long loginId;

    private String otherId;

    private Integer chatNo;

    public Long getLoginId() {
        return loginId;
    }

    public void setLoginId(Long loginId) {
        this.loginId = loginId;
    }

    public String getOtherId() {
        return otherId;
    }

    public void setOtherId(String otherId) {
        this.otherId = otherId;
    }

    public Integer getChatNo() {
        return chatNo;
    }

    public void setChatNo(Integer chatNo) {
        this.chatNo = chatNo;
    }

}
