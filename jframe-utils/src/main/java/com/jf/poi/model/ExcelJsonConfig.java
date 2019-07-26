package com.jf.poi.model;

import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description: Excel JSON配置
 * User: xujunfei
 * Date: 2018-08-30
 * Time: 11:36
 */
public class ExcelJsonConfig {

    private String name;
    private String field;
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ExcelJsonConfig{" +
                "name='" + name + '\'' +
                ", field='" + field + '\'' +
                '}';
    }

}
