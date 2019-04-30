package cn.ipanel.ningxia.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.ipanel.ningxia.response.RespConstants;
import cn.ipanel.ningxia.response.RespListBean;
import cn.ipanel.ningxia.response.RespSingleBean;
import cn.ipanel.ningxia.service.IBaseService;

public class BaseServiceImpl<T> implements IBaseService<T>{

	
	private JpaRepository<T,Serializable> baseDao;
	
	public void setBaseDao(JpaRepository<T, Serializable> baseDao) {
		this.baseDao = baseDao;
	}
	@Override
	public void insert(T t) {
		baseDao.save(t);
	}

	@Override
	public RespSingleBean<T> selectOneById(Serializable id) {
		T t = baseDao.findOne(id);
		return new RespSingleBean<T>(t, RespConstants.SUCCESS_CODE, RespConstants.SUCCESS_MSG);
	}

	@Override
	public void insertList(List<T> list) {
		baseDao.save(list);
	}
	
	protected RespListBean getRespListBean(List<?> list , int total) {
		RespListBean resp = new RespListBean();
		resp.setList(list);
		resp.setTotal(total);
		resp.setCode(RespConstants.SUCCESS_CODE);
		resp.setMsg(RespConstants.SUCCESS_MSG);
		return resp;
	}
}
