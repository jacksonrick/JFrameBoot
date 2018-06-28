package com.jf.database.model.excel;

import com.jf.system.annotation.excel.Excel;
import com.jf.system.annotation.excel.FieldType;
import com.jf.system.annotation.excel.Fields;
import com.jf.system.annotation.excel.TypeValue;

/**
 * Created with IntelliJ IDEA.
 * Description: mybatis -> model -> excel
 * User: xujunfei
 * Date: 2018-06-27
 * Time: 11:15
 */
@Excel(name = "员工列表")
public class UserModel {

    @Fields(value = "员工编号")
    private String number;
    @Fields(value = "职场")
    private String role_name;
    @Fields(value = "部门")
    private String parent_name;
    @Fields(value = "姓名")
    private String username;
    @Fields(value = "手机号")
    private String phone;
    @Fields(value = "状态", type = FieldType.ENUM, typeValues = {
            @TypeValue(name = "在职", value = "1"),
            @TypeValue(name = "离职", value = "0")
    })
    private String status;
    @Fields(value = "档案编号")
    private String dabh;
    @Fields(value = "工作地点")
    private String job_addr;
    @Fields(value = "职位")
    private String profes;
    @Fields(value = "项目")
    private String proj;
    @Fields(value = "组别")
    private String grade;
    @Fields(value = "员工性质", type = FieldType.ENUM, typeValues = {
            @TypeValue(name = "正式员工", value = "1"),
            @TypeValue(name = "试用期", value = "2"),
            @TypeValue(name = "实习生", value = "3")
    })
    private String ygxz;
    @Fields(value = "入职时间", type = FieldType.DATE)
    private String join_time;
    @Fields(value = "培训时间")
    private String train_time;
    @Fields(value = "转正时间")
    private String become_time;
    @Fields(value = "社保", type = FieldType.BOOLEAN)
    private String social;
    @Fields(value = "身份证号码")
    private String idcard;
    @Fields(value = "", type = FieldType.ENUM, typeValues = {
            @TypeValue(name = "男", value = "1"),
            @TypeValue(name = "女", value = "0")
    })
    private String gender;
    @Fields(value = "出生日期")
    private String birth;
    @Fields(value = "民族")
    private String nation;
    @Fields(value = "婚姻状况", type = FieldType.ENUM, typeValues = {
            @TypeValue(name = "已婚", value = "1"),
            @TypeValue(name = "未婚", value = "0")
    })
    private String marry;
    @Fields(value = "政治面貌")
    private String zzmm;
    @Fields(value = "户籍所在地")
    private String hjszd;
    @Fields(value = "户口地址")
    private String address;
    @Fields(value = "现住址")
    private String address_now;
    @Fields(value = "户口类别", type = FieldType.ENUM, typeValues = {
            @TypeValue(name = "农业", value = "1"),
            @TypeValue(name = "非农业", value = "2")
    })
    private String hklx;
    @Fields(value = "毕业学校")
    private String school;
    @Fields(value = "专业")
    private String major;
    @Fields(value = "学历")
    private String educate;
    @Fields(value = "毕业时间")
    private String by_time;
    @Fields(value = "前工作单位")
    private String last_comp;
    @Fields(value = "前工作岗位")
    private String last_jon;
    @Fields(value = "合同起始日期")
    private String contract_stime;
    @Fields(value = "合同到期日期")
    private String contract_etime;
    @Fields(value = "合同签订次数")
    private String contract_count;
    @Fields(value = "紧急联系人")
    private String urgent;
    @Fields(value = "紧急联系方式")
    private String urgent_phone;
    @Fields(value = "备注")
    private String remarks;
    @Fields(value = "离职时间")
    private String leave_time;
    @Fields(value = "离职原因")
    private String leave_reason;
    @Fields(value = "银行卡号1")
    private String bank_card;
    @Fields(value = "银行卡号2")
    private String bank_card2;
    @Fields(value = "卡号备注")
    private String bank_rk;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDabh() {
        return dabh;
    }

    public void setDabh(String dabh) {
        this.dabh = dabh;
    }

    public String getJob_addr() {
        return job_addr;
    }

    public void setJob_addr(String job_addr) {
        this.job_addr = job_addr;
    }

    public String getProfes() {
        return profes;
    }

    public void setProfes(String profes) {
        this.profes = profes;
    }

    public String getProj() {
        return proj;
    }

    public void setProj(String proj) {
        this.proj = proj;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getYgxz() {
        return ygxz;
    }

    public void setYgxz(String ygxz) {
        this.ygxz = ygxz;
    }

    public String getJoin_time() {
        return join_time;
    }

    public void setJoin_time(String join_time) {
        this.join_time = join_time;
    }

    public String getTrain_time() {
        return train_time;
    }

    public void setTrain_time(String train_time) {
        this.train_time = train_time;
    }

    public String getBecome_time() {
        return become_time;
    }

    public void setBecome_time(String become_time) {
        this.become_time = become_time;
    }

    public String getSocial() {
        return social;
    }

    public void setSocial(String social) {
        this.social = social;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getMarry() {
        return marry;
    }

    public void setMarry(String marry) {
        this.marry = marry;
    }

    public String getZzmm() {
        return zzmm;
    }

    public void setZzmm(String zzmm) {
        this.zzmm = zzmm;
    }

    public String getHjszd() {
        return hjszd;
    }

    public void setHjszd(String hjszd) {
        this.hjszd = hjszd;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress_now() {
        return address_now;
    }

    public void setAddress_now(String address_now) {
        this.address_now = address_now;
    }

    public String getHklx() {
        return hklx;
    }

    public void setHklx(String hklx) {
        this.hklx = hklx;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getEducate() {
        return educate;
    }

    public void setEducate(String educate) {
        this.educate = educate;
    }

    public String getBy_time() {
        return by_time;
    }

    public void setBy_time(String by_time) {
        this.by_time = by_time;
    }

    public String getLast_comp() {
        return last_comp;
    }

    public void setLast_comp(String last_comp) {
        this.last_comp = last_comp;
    }

    public String getLast_jon() {
        return last_jon;
    }

    public void setLast_jon(String last_jon) {
        this.last_jon = last_jon;
    }

    public String getContract_stime() {
        return contract_stime;
    }

    public void setContract_stime(String contract_stime) {
        this.contract_stime = contract_stime;
    }

    public String getContract_etime() {
        return contract_etime;
    }

    public void setContract_etime(String contract_etime) {
        this.contract_etime = contract_etime;
    }

    public String getContract_count() {
        return contract_count;
    }

    public void setContract_count(String contract_count) {
        this.contract_count = contract_count;
    }

    public String getUrgent() {
        return urgent;
    }

    public void setUrgent(String urgent) {
        this.urgent = urgent;
    }

    public String getUrgent_phone() {
        return urgent_phone;
    }

    public void setUrgent_phone(String urgent_phone) {
        this.urgent_phone = urgent_phone;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getLeave_time() {
        return leave_time;
    }

    public void setLeave_time(String leave_time) {
        this.leave_time = leave_time;
    }

    public String getLeave_reason() {
        return leave_reason;
    }

    public void setLeave_reason(String leave_reason) {
        this.leave_reason = leave_reason;
    }

    public String getBank_card() {
        return bank_card;
    }

    public void setBank_card(String bank_card) {
        this.bank_card = bank_card;
    }

    public String getBank_card2() {
        return bank_card2;
    }

    public void setBank_card2(String bank_card2) {
        this.bank_card2 = bank_card2;
    }

    public String getBank_rk() {
        return bank_rk;
    }

    public void setBank_rk(String bank_rk) {
        this.bank_rk = bank_rk;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public String getParent_name() {
        return parent_name;
    }

    public void setParent_name(String parent_name) {
        this.parent_name = parent_name;
    }
}
