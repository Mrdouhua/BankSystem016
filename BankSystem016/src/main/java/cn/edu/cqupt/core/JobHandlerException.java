/**
 * 
 */
package cn.edu.cqupt.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Leo
 * 异常处理
 */
public class JobHandlerException implements HandlerExceptionResolver {
	static Logger log = LoggerFactory.getLogger(JobHandlerException.class);
	private String errorpage;
	
	
	public String getErrorpage() {
		return errorpage;
	}


	public void setErrorpage(String errorpage) {
		this.errorpage = errorpage;
	}


	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.HandlerExceptionResolver#resolveException(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)
	 */
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object paramhandler, Exception e) {
		log.info("######exception paramhandler: "+paramhandler);
		log.error("######exception: ",e);
		ModelAndView mv = new ModelAndView(errorpage);
		return mv;
	}

}
