package kihira.tails.common.network;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import kihira.tails.common.TailInfo;
import kihira.tails.common.Tails;

public class TailInfoMessage implements IMessage {

    private TailInfo tailInfo;
    private boolean shouldRemove;

    public TailInfoMessage() {}
    public TailInfoMessage(TailInfo tailInfo, boolean shouldRemove) {
        this.tailInfo = tailInfo;
        this.shouldRemove = shouldRemove;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        String tailInfoJson = ByteBufUtils.readUTF8String(buf);
        try {
            this.tailInfo = new Gson().fromJson(tailInfoJson, TailInfo.class);
        } catch (JsonSyntaxException e) {
            Tails.logger.warn(e);
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        String tailInfoJson = new Gson().toJson(this.tailInfo);
        ByteBufUtils.writeUTF8String(buf, tailInfoJson);
    }

    public static class TailInfoMessageHandler implements IMessageHandler<TailInfoMessage, IMessage> {

        @Override
        public IMessage onMessage(TailInfoMessage message, MessageContext ctx) {
            //TODO test receiving message client side
            if (message.shouldRemove) Tails.proxy.removeTailInfo(message.tailInfo.id);
            else Tails.proxy.addTailInfo(message.tailInfo.id, message.tailInfo);
            return null;
        }
    }
}