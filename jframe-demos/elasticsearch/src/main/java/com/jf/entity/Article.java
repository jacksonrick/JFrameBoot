package com.jf.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.annotations.Setting;

/**
 * Created with IntelliJ IDEA.
 * Description: ES文档
 * User: xujunfei
 * Date: 2019-07-16
 * Time: 11:22
 */
// indexName（索引）相当于数据库，type相当于数据库的表
// shards设置分片 ；replicas设置备份，也可以通过put创建索引时指定
@Document(indexName = "es", type = "article") // 指定索引，查询 es/article/_search
// 指定配置文件（ES原生写法，版本6）
@Setting(settingPath = "/json/es-setting.json")
@Mapping(mappingPath = "/json/article-mapping.json")
public class Article {

    @Id
    private long id;
    // 指定分词器
    //@Field(type = FieldType.Text, searchAnalyzer = "ikSearchAnalyzer", analyzer = "ikSearchAnalyzer")
    private String title;
    private String content;
    private String tag;

    // @Field(type = FieldType.Date)
    // private Date createTime;

    public Article(long id, String title, String content, String tag) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.tag = tag;
    }

    public Article(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Article() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", tag='" + tag + '\'' +
                '}';
    }

}
