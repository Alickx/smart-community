package cn.goroute.smart.gateway.filter;

import cn.hutool.core.util.IdUtil;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author: Alickx
 * @Date: 2023/02/11 11:14:40
 * @Description:
 */
@Component
public class TraceIdFilter implements GlobalFilter {

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

		// 每个请求检查是否有traceId，没有则生成一个
		String traceId = exchange.getRequest().getHeaders().getFirst("traceId");
		if (traceId == null || traceId.isEmpty()) {
			traceId = IdUtil.objectId();
		}

		// 将traceId放入header中
		ServerWebExchange newExchange = exchange
				.mutate()
				.request(exchange.getRequest().mutate().header("traceId", traceId).build())
				.build();

		return chain.filter(newExchange);
	}
}
