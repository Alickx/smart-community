package cn.goroute.smart.common.modules.trace;

import cn.goroute.smart.common.constant.LogConstant;
import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class TraceIdFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

		// 从请求头中获取 traceId 参数
		String traceId = request.getHeader(LogConstant.TRACE_ID);

		// 如果请求头中没有 traceId 参数，则生成一个
		if (traceId == null || traceId.isEmpty()){
			log.info("traceId is empty, generate a new one");
			traceId = IdUtil.objectId();
		}

		MDC.put(LogConstant.TRACE_ID, traceId);


		try {
			// 响应头中添加 traceId 参数，方便排查问题
			response.setHeader(LogConstant.TRACE_ID, traceId);
			filterChain.doFilter(request, response);
		}
		finally {
			MDC.clear();
		}

	}

}
