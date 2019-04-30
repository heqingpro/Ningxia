package cn.ipanel.ningxia.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import cn.ipanel.ningxia.dao.ILetterDao;
import cn.ipanel.ningxia.entity.Letter;
import cn.ipanel.ningxia.page.BasePage;
import cn.ipanel.ningxia.response.ItemBean;
import cn.ipanel.ningxia.response.RespListBean;
import cn.ipanel.ningxia.service.ILetterService;

@Service
public class LetterServiceImpl extends BaseServiceImpl<Letter> implements ILetterService{

	private ILetterDao<Letter, Integer> letterDao;
	
	@Autowired
	public void setLetterDao(ILetterDao<Letter, Integer> letterDao) {
		super.setBaseDao(letterDao);
		this.letterDao = letterDao;
	}

	@Override
	public RespListBean getListBean(BasePage page) {
		Pageable pageable = new PageRequest(page.getPage(), page.getRows(), Direction.DESC, "replyTime");
		List<ItemBean> list = letterDao.findAllItem(pageable);
		Long total = letterDao.count();
		RespListBean resp = getRespListBean(list, total.intValue());
		return resp;
	}

}
