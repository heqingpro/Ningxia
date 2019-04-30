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

import cn.ipanel.ningxia.entity.Suggest;
import cn.ipanel.ningxia.response.ItemBean;
@CacheConfig
@Cacheable
public interface ISuggestDao<T,ID extends Serializable> extends JpaRepository<Suggest,Serializable>{

    @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
	@Query("select new cn.ipanel.ningxia.response.ItemBean(id,title) from Suggest where topicName=?1")
	List<ItemBean> findItemBeanListByTopicName(String topicName,Pageable pageable);
    
    @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
	Long countByTopicName(String topicName);
    
    @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
	@Query("select new cn.ipanel.ningxia.response.ItemBean(id,title) from Suggest")
	List<ItemBean> findItemBeanList(Pageable pageable);
    
}
