package com.ggj.encrypt.common.persistence;

import com.ggj.encrypt.common.constant.GlobalConstant;
import com.ggj.encrypt.common.utils.DesUtil;
import lombok.Getter;
import lombok.Setter;

import javax.servlet.http.HttpServletRequest;

/**
 * @author:gaoguangjin
 * @Description:
 * @Email:335424093@qq.com
 * @Date 2016/4/25 13:59
 */
@Getter
@Setter
public class HttpHeaderParam {
	// 签名串
	private String sign;
	private Long userId;
	private String appkey;
	private String timeStamp;
	
	public String toString() {
		StringBuilder instance = new StringBuilder();
		instance.append(" HttpHeader: {");
		instance.append(GlobalConstant.TIMESTAMP + "[").append(this.timeStamp).append("], ");
		instance.append(GlobalConstant.SIGN + "[").append(this.sign).append("] ");
		instance.append(GlobalConstant.USE_ID + "[").append(this.userId).append("] ");
		instance.append(GlobalConstant.APPKEY + "[").append(this.appkey).append("] ");
		instance.append(" }");
		return instance.toString();
	}
	
	/**
	 * 解析HttpServletRequest，生成HttpHeader对象
	 * @param request
	 * @return
	 */
	public static HttpHeaderParam parseRequestHeader(HttpServletRequest request) throws Exception {
		HttpHeaderParam header = new HttpHeaderParam();
		header.setTimeStamp(request.getHeader(GlobalConstant.TIMESTAMP));
		header.setSign(request.getHeader(GlobalConstant.SIGN));
		header.setAppkey(request.getHeader(GlobalConstant.APPKEY));
		// 用户ip都是加密的
		String userId = DesUtil.decrypt(request.getHeader(GlobalConstant.USE_ID), header.getAppkey());
		header.setUserId(Long.parseLong(userId));
		//将解密后的userId再保存起来等会用到
		request.getSession().setAttribute(GlobalConstant.USE_ID,userId);
		return header;
	}
	
}
