package netty.protocol.response;

import lombok.Data;
import netty.enums.CommandEnum;
import netty.protocol.Packet;

import java.util.List;

/**
 * @param: none
 * @description:
 * @author: KingJ
 * @create: 2019-09-02 21:27
 **/
@Data
public class ListGroupMemberResponsePacket extends Packet {
    private String groupName;
    private List<String> userList;

    @Override
    public Byte getCommand() {
        return CommandEnum.LIST_GROUP_MEMBER_RESPONSE.getValue();
    }
}
