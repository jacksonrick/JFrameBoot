package com.jf.excel;

import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2018-08-30
 * Time: 11:36
 */
public class EConfig {

    private String name;
    private String type;
    private Content content;

    static class Content {
        List<Fields> fields;
        String[] jsons;

        public List<Fields> getFields() {
            return fields;
        }

        public void setFields(List<Fields> fields) {
            this.fields = fields;
        }

        public String[] getJsons() {
            return jsons;
        }

        public void setJsons(String[] jsons) {
            this.jsons = jsons;
        }

        @Override
        public String toString() {
            return "Content{" +
                    "fields=" + fields +
                    ", jsons='" + Arrays.asList(jsons) + '\'' +
                    '}';
        }
    }

    static class Fields {
        String name;
        String field;

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

        @Override
        public String toString() {
            return "Fields{" +
                    "name='" + name + '\'' +
                    ", field='" + field + '\'' +
                    '}';
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "EConfig{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", content=" + content +
                '}';
    }
}
