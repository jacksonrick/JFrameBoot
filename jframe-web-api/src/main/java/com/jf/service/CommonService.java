package com.jf.service;

import com.jf.database.mapper.SmsMapper;
import com.jf.database.model.Sms;
import com.jf.string.IdGen;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2019-05-05
 * Time: 14:14
 */
@Service
public class CommonService {

    @Resource
    private SmsMapper smsMapper;

    /**
     * 新增短信
     *
     * @param phone
     * @param code
     * @param codeType
     * @return
     */
    public int insertSMS(String phone, String code, String codeType) {
        Sms sms = new Sms();
        sms.setId(IdGen.get().nextId());
        sms.setCode(code);
        sms.setPhone(phone);
        sms.setSource(codeType);
        return smsMapper.insert(sms);
    }

    /**
     * 校验发送频率
     *
     * @param phone
     * @return
     */
    public int findCountByPhone(String phone) {
        return smsMapper.findCountByPhone(phone);
    }

    /**
     * 检验验证码
     *
     * @param phone
     * @param code
     * @return
     */
    public boolean checkSmsCode(String phone, String code) {
        Sms sms = smsMapper.check(phone);
        if (sms == null || sms.getValided()) {
            return false;
        }
        if (code.equals(sms.getCode())) {
            smsMapper.update(sms.getId()); // 更新验证状态
            return true;
        }
        return false;
    }


    /**
     * 获取有效期内验证码
     * <p>未判断是否已验证</p>
     *
     * @param phone
     * @return
     */
    public String findValidCode(String phone) {
        Sms sms = smsMapper.check(phone);
        if (sms != null) {
            return sms.getCode();
        }
        return "";
    }

}
