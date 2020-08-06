package com.jf.service;

import com.jf.entity.Article;
import com.jf.handler.CustomResultMapper;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2019-07-16
 * Time: 11:35
 */
@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private CustomResultMapper customResultMapper;

    public void save(Article article) {
        articleRepository.save(article);
    }

    public Page<Article> search(Pageable pageable) {
        return articleRepository.findAll(pageable);
    }

    public Page<Article> search(String title, Pageable pageable) {
        return articleRepository.findByTitle(title, pageable);
    }

    public Page<Article> search(String title, String content, Pageable pageable) {
        return articleRepository.findByTitleOrContent(title, content, pageable);
    }

    /**
     * 高亮搜索
     *
     * @param keyword
     * @param pageable
     * @return
     */
    public Page<Article> searchHighlight(String keyword, Pageable pageable) {
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        MultiMatchQueryBuilder matchQuery = QueryBuilders.multiMatchQuery(keyword, "title", "content");
        boolQuery.must(matchQuery);
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(boolQuery)
                .withHighlightFields(
                        new HighlightBuilder.Field("title").preTags("<em>").postTags("</em>"),
                        new HighlightBuilder.Field("content").preTags("<em>").postTags("</em>"))
                .withPageable(pageable)
                .build();
        return elasticsearchTemplate.queryForPage(searchQuery, Article.class, customResultMapper);
//        return articleRepository.search(searchQuery);
    }

    /**
     * 分词、拼音搜索
     *
     * @param keyword
     * @param pageable
     * @return
     */
    public Page<Article> searchPinyin(String keyword, Pageable pageable) {
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        MultiMatchQueryBuilder matchQuery = QueryBuilders.multiMatchQuery(keyword, "title", "title.pinyin", "content", "tag");
        boolQuery.must(matchQuery);
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(matchQuery)
                .withHighlightFields(
                        new HighlightBuilder.Field("title").preTags("<em>").postTags("</em>"),
                        new HighlightBuilder.Field("content").preTags("<em>").postTags("</em>"))
                .withPageable(pageable)
                .build();
        return elasticsearchTemplate.queryForPage(searchQuery, Article.class, customResultMapper);
    }

    // 写法2
    public Page<Article> searchPinyin2(String keyword, Pageable pageable) {
        // 使用dis_max直接取多个query中，分数最高的那一个query的分数即可
        DisMaxQueryBuilder disMaxQueryBuilder = QueryBuilders.disMaxQuery();
        // boost 设置权重,只搜索匹配name和disrector字段
        QueryBuilder ikNameQuery = QueryBuilders.matchQuery("title", keyword).boost(2f);
        QueryBuilder ikPinyiQuery = QueryBuilders.matchQuery("title.pinyin", keyword);
        QueryBuilder ikContentQuery = QueryBuilders.matchQuery("content", keyword).boost(2f);
        disMaxQueryBuilder.add(ikNameQuery);
        disMaxQueryBuilder.add(ikPinyiQuery);
        disMaxQueryBuilder.add(ikContentQuery);

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(disMaxQueryBuilder)
                .withHighlightFields(
                        new HighlightBuilder.Field("title").preTags("<em>").postTags("</em>"),
                        new HighlightBuilder.Field("content").preTags("<em>").postTags("</em>"))
                .withPageable(pageable)
                .build();
        return elasticsearchTemplate.queryForPage(searchQuery, Article.class, customResultMapper);
    }

}
