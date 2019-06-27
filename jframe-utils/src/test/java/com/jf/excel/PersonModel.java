package com.jf.excel;

import com.jf.annotation.excel.Excel;
import com.jf.annotation.excel.Fields;

/**
 * Created with IntelliJ IDEA.
 * Description: mybatis -> model -> excel
 * User: xujunfei
 * Date: 2018-06-27
 * Time: 11:15
 */
@Excel(name = "人员明细列表")
public class PersonModel {

    @Fields(value = "姓名")
    private String mannames;
    @Fields(value = "组别")
    private String groups;
    @Fields(value = "组长")
    private String headman;
    @Fields(value = "主管")
    private String charge;
    @Fields(value = "经理")
    private String manager;
    @Fields(value = "员工工号")
    private String personid;
    @Fields(value = "坐席号")
    private String seatid;
    @Fields(value = "上班")
    private String works;
    @Fields(value = "入项目日期")
    private String projectdate;
    @Fields(value = "工作日期截止到")
    private String workdate;
    @Fields(value = "员工性质")
    private String nature;

    public String getMannames() {
        return mannames;
    }

    public void setMannames(String mannames) {
        this.mannames = mannames;
    }

    public String getGroups() {
        return groups;
    }

    public void setGroups(String groups) {
        this.groups = groups;
    }

    public String getHeadman() {
        return headman;
    }

    public void setHeadman(String headman) {
        this.headman = headman;
    }

    public String getCharge() {
        return charge;
    }

    public void setCharge(String charge) {
        this.charge = charge;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getPersonid() {
        return personid;
    }

    public void setPersonid(String personid) {
        this.personid = personid;
    }

    public String getSeatid() {
        return seatid;
    }

    public void setSeatid(String seatid) {
        this.seatid = seatid;
    }

    public String getWorks() {
        return works;
    }

    public void setWorks(String works) {
        this.works = works;
    }

    public String getProjectdate() {
        return projectdate;
    }

    public void setProjectdate(String projectdate) {
        this.projectdate = projectdate;
    }

    public String getWorkdate() {
        return workdate;
    }

    public void setWorkdate(String workdate) {
        this.workdate = workdate;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    @Override
    public String toString() {
        return "PersonModel{" +
                "mannames='" + mannames + '\'' +
                ", groups='" + groups + '\'' +
                ", headman='" + headman + '\'' +
                ", charge='" + charge + '\'' +
                ", manager='" + manager + '\'' +
                ", personid='" + personid + '\'' +
                ", seatid='" + seatid + '\'' +
                ", works='" + works + '\'' +
                ", projectdate='" + projectdate + '\'' +
                ", workdate='" + workdate + '\'' +
                ", nature='" + nature + '\'' +
                '}';
    }
}
