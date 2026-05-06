/**
 * 系统名称: SmartWeb平台
 * 模块名称: 流程结束通用处理类
 * 类  名  称: CommonEndEvent.java
 * 软件版权: 北京先进数通信息技术股份公司
 * 开发人员: chenyl <br>
 * 开发时间: 2018年9月10日 下午3:05:57<br>
 * 系统版本: V1.0.0<br>
 ** 修改记录:
 * 修改日期                            修改人员          修改说明 <br>
 * ========     ======  ============================================
 * 
 * ========     ======  ============================================
 */
package com.adtec.sys.modules.flow.event;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adtec.framework.common.util.DataUtil;
import com.adtec.sys.modules.flow.service.FlowService;

/**
 * @author chenyl
 *
 */
public class CommonEndEvent implements IFlowEvent {
	private final static Logger log = LoggerFactory.getLogger(CommonEndEvent.class);
	/* (non-Javadoc)
	 * @see com.adtec.sys.modules.flow.event.IFlowEvent#passEvent(java.util.HashMap)
	 */
	@Override
	public void passEvent(HashMap<String, Object> dataMap) {
		// TODO Auto-generated method stub
		log.info("流程结束通用处理类->审批通过事件："+dataMap);
		String globalSeq = (String) dataMap.get(KEY_GLOBAL_SEQ_NO);
		if(!DataUtil.isNullStr(globalSeq)){
			// 主动释放外部设置的处理人员
			FlowService.STEP_DEAL_MAP.remove(globalSeq);
		}
	}

	/* (non-Javadoc)
	 * @see com.adtec.sys.modules.flow.event.IFlowEvent#refuseEvent(java.util.HashMap)
	 */
	@Override
	public void refuseEvent(HashMap<String, Object> dataMap) {
		// TODO Auto-generated method stub
		log.info("流程结束通用处理类->审批拒绝事件："+dataMap);
		String globalSeq = (String) dataMap.get(KEY_GLOBAL_SEQ_NO);
		if(!DataUtil.isNullStr(globalSeq)){
			// 主动释放外部设置的处理人员
			FlowService.STEP_DEAL_MAP.remove(globalSeq);
		}
	}

}
