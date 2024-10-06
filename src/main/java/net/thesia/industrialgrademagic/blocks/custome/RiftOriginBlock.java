package net.thesia.industrialgrademagic.blocks.custome;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class RiftOriginBlock extends Block implements EntityBlock {
    public RiftOriginBlock(BlockBehaviour.Properties properties){
        super(properties);
    }
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state){
        return new RiftOriginBlockEntity(pos,state);
    }
}
