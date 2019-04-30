package cn.ipanel.ningxia.pipeline;

import java.util.UUID;

import org.springframework.util.DigestUtils;

public abstract class AbstractKeyGenerator<T> {

	/**
	 * @Description  产生对象id
	 * @author qinmian
	 * @Date 2017年11月30日 下午2:00:16
	 * @param entity
	 * @return
	 */
	public String createId(T entity){
		if(entity == null ){
			return DigestUtils.md5DigestAsHex(UUID.randomUUID().toString().getBytes());
		}
		
		return DigestUtils.md5DigestAsHex(getKeyString(entity).getBytes());
	}
	
	public abstract String  getKeyString(T entity); 
}
