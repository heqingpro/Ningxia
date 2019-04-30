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

import cn.ipanel.ningxia.entity.Letter;
import cn.ipanel.ningxia.response.ItemBean;
@CacheConfig
@Cacheable
public interface ILetterDao<T,ID extends Serializable> extends JpaRepository<Letter,Serializable> {

    @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
	@Query("select new cn.ipanel.ningxia.response.ItemBean(id,letterTitle,replyTime) from Letter")
	List<ItemBean> findAllItem(Pageable page);
}
