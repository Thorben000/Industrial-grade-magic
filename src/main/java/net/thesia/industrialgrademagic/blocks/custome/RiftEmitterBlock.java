package net.thesia.industrialgrademagic.blocks.custome;


import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.thesia.industrialgrademagic.entity.RiftEmitterBlockEntity;
import net.thesia.industrialgrademagic.entity.RiftOriginBlockEntity;
import org.jetbrains.annotations.NotNull;


public class RiftEmitterBlock extends Block implements EntityBlock{
    public RiftEmitterBlock(Properties properties) {
        super(properties);
    }
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state){
        return new RiftEmitterBlockEntity(pos,state);
    }

}
