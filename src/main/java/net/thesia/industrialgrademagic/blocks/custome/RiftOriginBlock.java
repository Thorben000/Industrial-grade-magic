package net.thesia.industrialgrademagic.blocks.custome;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;

import static net.thesia.industrialgrademagic.utility.RiftHandler.RIFT_POSSIBLE_LOCATIONS;
import static net.thesia.industrialgrademagic.utility.RiftHandler.RIFT_SIZE;

public class RiftOriginBlock extends Block implements EntityBlock {
    public RiftOriginBlock(BlockBehaviour.Properties properties){
        super(properties);
    }
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state){
        return new RiftOriginBlockEntity(pos,state);
    }

    private static void setPossible(BlockEntity entity,BlockPos pos){

        for (int i = -1; i<=1; i+=2){
            for(int q = 0;q<3;q++) {
                int e=0,r=0,t = 0;
                switch (q){
                    case 0:
                        e = i;
                        break;
                    case 1:
                        r=i;
                        break;
                    case 2:
                        t=i;
                        break;
                }

                ArrayList<Integer> toAdd = new ArrayList<Integer>();
                toAdd.add(pos.getX() + e);
                toAdd.add(pos.getY() + r);
                toAdd.add(pos.getZ() + t);
                ArrayList<ArrayList<Integer>> newData = entity.getData(RIFT_POSSIBLE_LOCATIONS);
                newData.add(toAdd);
                entity.setData(RIFT_POSSIBLE_LOCATIONS, newData);
            }
        }
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        BlockEntity entity = level.getBlockEntity(pos);
        entity.setData(RIFT_SIZE,1);
        ArrayList<ArrayList<Integer>> empty = new ArrayList<ArrayList<Integer>>();
        entity.setData(RIFT_POSSIBLE_LOCATIONS,empty);
        setPossible(entity,pos);

        super.onPlace(state, level, pos, oldState, movedByPiston);
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        BlockEntity entity = level.getBlockEntity(pos);
        int riftSize = entity.getData(RIFT_SIZE);
        if(riftSize<=10000){

        }
        super.randomTick(state, level, pos, random);
    }
}
