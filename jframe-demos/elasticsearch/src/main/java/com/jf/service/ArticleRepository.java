package com.jf.service;

import com.jf.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Created with IntelliJ IDEA.
 * Description: 自定义搜索方法
 * User: xujunfei
 * Date: 2019-07-16
 * Time: 11:32
 */
public interface ArticleRepository extends ElasticsearchRepository<Article, Long> {

    @Override
    Page<Article> findAll(Pageable pageable);

    Page<Article> findByTitle(String title, Pageable pageable);

    Page<Article> findByTitleOrContent(String title, String content, Pageable pageable);

    // 写法 findBy...Or...

}
