package com.jf.excel;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2018-08-30
 * Time: 11:25
 */
public class BankList {

    private String custname;
    private String bank;
    private String phone;
    private String step;
    private String jsons;

    public String getCustname() {
        return custname;
    }

    public void setCustname(String custname) {
        this.custname = custname;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public String getJsons() {
        return jsons;
    }

    public void setJsons(String jsons) {
        this.jsons = jsons;
    }

    @Override
    public String toString() {
        return "BankList{" +
                "custname='" + custname + '\'' +
                ", bank='" + bank + '\'' +
                ", phone='" + phone + '\'' +
                ", step='" + step + '\'' +
                ", jsons='" + jsons + '\'' +
                '}';
    }
}
