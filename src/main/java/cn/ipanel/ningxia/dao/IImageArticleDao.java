package cn.ipanel.ningxia.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import cn.ipanel.ningxia.entity.ImageArticle;
import cn.ipanel.ningxia.response.ImageItemBean;
@CacheConfig
@Cacheable
public interface IImageArticleDao<T,ID extends Serializable> extends JpaRepository<ImageArticle,Serializable>{
	
    @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
	@Query("select new cn.ipanel.ningxia.response.ImageItemBean(id,title,img) from ImageArticle where topicName = ?1")
	List<ImageItemBean> findByTopicName(String topicName,Pageable pageable);
    @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
	Long countByTopicName(String topicName);
}
