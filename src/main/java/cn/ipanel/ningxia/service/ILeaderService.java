package cn.ipanel.ningxia.service;

import cn.ipanel.ningxia.entity.Leader;
import cn.ipanel.ningxia.response.RespListBean;

public interface ILeaderService extends IBaseService<Leader>{
	RespListBean getListBean(String belong);
}
