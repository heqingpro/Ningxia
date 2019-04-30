package cn.ipanel.ningxia.service;

import java.io.Serializable;
import java.util.List;

import cn.ipanel.ningxia.response.RespSingleBean;

public interface IBaseService<T> {

	void insert(T t);
	
	RespSingleBean<T> selectOneById(Serializable id);
	
	void insertList(List<T> list);
	
}
