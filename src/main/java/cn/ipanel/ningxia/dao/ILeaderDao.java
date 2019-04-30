package cn.ipanel.ningxia.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import cn.ipanel.ningxia.entity.Leader;
import cn.ipanel.ningxia.response.LeaderItemBean;
@CacheConfig
@Cacheable
public interface ILeaderDao<T,ID extends Serializable> extends JpaRepository<Leader,Serializable> {
	
    @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
	@Query("select new cn.ipanel.ningxia.response.LeaderItemBean(id,name,duties,headImage) from Leader where belong=?")
	List<LeaderItemBean> findByBelong(String belong,Sort sort);
    @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
	Integer countByBelong(String belong);
	
	/**
	 * 在插入之前需要先清除表之中所有东西
	 */
	@Modifying
	@Query(value="TRUNCATE leader",nativeQuery=true)
	void truncateLeader();
}
