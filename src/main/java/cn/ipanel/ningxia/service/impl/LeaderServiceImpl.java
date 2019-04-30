package cn.ipanel.ningxia.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.ipanel.ningxia.dao.ILeaderDao;
import cn.ipanel.ningxia.entity.Leader;
import cn.ipanel.ningxia.response.LeaderItemBean;
import cn.ipanel.ningxia.response.RespListBean;
import cn.ipanel.ningxia.service.ILeaderService;

@Service
public class LeaderServiceImpl extends BaseServiceImpl<Leader> implements ILeaderService {

	private ILeaderDao<Leader, Integer> leaderDao;

	@Autowired
	public void setLeaderDao(ILeaderDao<Leader, Integer> leaderDao) {
		super.setBaseDao(leaderDao);
		this.leaderDao = leaderDao;
	}

	@Override
	public RespListBean getListBean(String belong) {
		Sort sort = new Sort(Direction.ASC, "id");
		List<LeaderItemBean> list = leaderDao.findByBelong(belong, sort);
		Integer total = leaderDao.countByBelong(belong).intValue();
		RespListBean resp = getRespListBean(list, total);
		return resp;
	}

	@Transactional
	@Override
	public void insertList(List<Leader> list) {
	/*	Long total = leaderDao.count();
		int count = total !=null ? total.intValue() : 0;
		if(count != 0 && count != list.size()){//如果数量不对等，可能抓取之中出现了问题，则不更新
			return ;
		}*/
		if(list != null && list.size() > 0){
			//批量插入前先删除
			leaderDao.truncateLeader();
			super.insertList(list);			
		}
	}
	
	
}
