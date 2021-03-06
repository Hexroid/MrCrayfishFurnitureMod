package com.mrcrayfish.furniture.network.message;

import com.mrcrayfish.furniture.tileentity.TileEntityMicrowave;
import com.mrcrayfish.furniture.util.TileEntityUtil;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageMicrowave implements IMessage, IMessageHandler<MessageMicrowave, IMessage>
{
    private int type;
    private int x, y, z;

    public MessageMicrowave()
    {
    }

    public MessageMicrowave(int type, int x, int y, int z)
    {
        this.type = type;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.type = buf.readInt();
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(type);
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
    }

    @Override
    public IMessage onMessage(MessageMicrowave message, MessageContext ctx)
    {
        World world = ctx.getServerHandler().player.world;
        TileEntity tileEntity = world.getTileEntity(new BlockPos(message.x, message.y, message.z));
        if(tileEntity instanceof TileEntityMicrowave)
        {
            TileEntityMicrowave tileEntityMicrowave = (TileEntityMicrowave) tileEntity;
            if(message.type == 0)
            {
                tileEntityMicrowave.startCooking();
            }
            if(message.type == 1)
            {
                tileEntityMicrowave.stopCooking();
            }
            BlockPos pos = new BlockPos(message.x, message.y, message.z);
            TileEntityUtil.markBlockForUpdate(ctx.getServerHandler().player.world, pos);
        }
        return null;
    }
}
