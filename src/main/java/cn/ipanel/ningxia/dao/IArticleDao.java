package cn.ipanel.ningxia.dao;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Cacheable;
import javax.persistence.QueryHint;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import cn.ipanel.ningxia.entity.Article;
import cn.ipanel.ningxia.response.ItemBean;

@Cacheable
public interface IArticleDao<T,ID extends Serializable> extends JpaRepository<Article,Serializable>{
	
	/** 获取所属专题最新新闻
	 * @param topicName 专题名
	 * @return
	 */	
    @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
	@Query("select new cn.ipanel.ningxia.response.ItemBean(id,title,releaseTime) from Article where topicName like ?1")
	List<ItemBean> findByTopicNameLike(String topicName,Pageable pageable);
    @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
    Long countByTopicNameLike(String topicName);
	
	/** 获取栏目下新闻列表
	 * @param belong 栏目名
	 * @param pageable
	 * @return
	 */
  /*  @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
	@Query("select new cn.ipanel.ningxia.response.ItemBean(id,title,releaseTime) from Article where belong = ?1")
	List<ItemBean> findByBelong(String belong,Pageable pageable);
    @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
    Long countByBelong(String belong);
	*/
    @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
	@Query("from Article where topicName like ?1 and content like '%<img%'")
	List<Article> getArticlesImgListByTopicName(String topicName,Pageable pageable); 
    @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
    @Query("select count(id) from Article where topicName like ?1 and content like '%<img%' ")
	Long getCountImgByTopicName(String topicName);
	
}
