package cn.ipanel.ningxia.service;

import cn.ipanel.ningxia.entity.Letter;
import cn.ipanel.ningxia.page.BasePage;
import cn.ipanel.ningxia.response.RespListBean;

public interface ILetterService extends IBaseService<Letter>{
	RespListBean getListBean(BasePage page);
}
