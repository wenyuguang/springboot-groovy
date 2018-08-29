package springboot.netty.reconnect.product;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

@Sharable
public class AcceptorIdleStateTrigger extends ChannelInboundHandlerAdapter {

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if(evt instanceof IdleStateEvent) {
			IdleState idleState = ((IdleStateEvent)evt).state();
			if(idleState == IdleState.READER_IDLE) {
				throw new Exception("idle exception");
			}
		}else {
			super.userEventTriggered(ctx, evt);
		}
	}
}
