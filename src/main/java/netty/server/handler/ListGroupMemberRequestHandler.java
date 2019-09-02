package netty.server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import netty.protocol.request.ListGroupMemberRequestPacket;
import netty.protocol.response.ListGroupMemberResponsePacket;
import netty.session.Session;
import netty.util.SessionUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @param: none
 * @description:
 * @author: KingJ
 * @create: 2019-09-02 21:30
 **/
public class ListGroupMemberRequestHandler extends SimpleChannelInboundHandler<ListGroupMemberRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ListGroupMemberRequestPacket listGroupMemberRequestPacket) throws Exception {
        // 获取群名以及对应的Channel Group
        String groupName = listGroupMemberRequestPacket.getGroupName();
        ChannelGroup channels = SessionUtil.getChannelGroup(groupName);

        // 遍历群成员的Channel，获取对应的Session即成员信息
        List<Session> sessionList = new ArrayList<>();
        for (Channel channel : channels) {
            Session session = SessionUtil.getSession(channel);
            sessionList.add(session);
        }

        // 构建成员列表写回响应信息
        ListGroupMemberResponsePacket listGroupMemberResponsePacket = new ListGroupMemberResponsePacket();

        listGroupMemberRequestPacket.setGroupName(groupName);
        listGroupMemberResponsePacket.setSessionList(sessionList);

        ctx.channel().writeAndFlush(listGroupMemberRequestPacket);
    }
}
