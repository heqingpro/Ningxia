package cn.ipanel.ningxia.listener;

import org.springframework.context.ApplicationEvent;

public class SpiderFinishEvent extends ApplicationEvent{
	private static final long serialVersionUID = 1L;
	public SpiderFinishEvent(Object source) {
		super(source);
	}

}
