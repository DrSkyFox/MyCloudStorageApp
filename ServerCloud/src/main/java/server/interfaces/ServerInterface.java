package server.interfaces;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.group.ChannelGroup;

import java.util.HashSet;
import java.util.logging.Logger;

public interface ServerInterface {

    LoggerHandlerService getLogger();
    SettingServer getSettings();
    ChannelGroup getChannelGroup();

}
