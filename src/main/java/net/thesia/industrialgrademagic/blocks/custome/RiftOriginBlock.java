package net.thesia.industrialgrademagic.blocks.custome;


import java.lang.Math;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.fml.common.EventBusSubscriber;
import net.thesia.industrialgrademagic.blocks.RiftBlock;
import net.thesia.industrialgrademagic.entity.RiftOriginBlockEntity;
import org.checkerframework.checker.units.qual.A;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import static net.thesia.industrialgrademagic.utility.RiftHandler.*;


public class RiftOriginBlock extends Block implements EntityBlock {
    public RiftOriginBlock(Properties properties){
        super(properties);
    }
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state){
        return new RiftOriginBlockEntity(pos,state);
    }

    private static void setPossible(BlockEntity entity,BlockPos pos){
        BlockPos entityPos = entity.getBlockPos();
        ArrayList<ArrayList<Integer>> originalArrayList = entity.getData(RIFT_POSSIBLE_LOCATIONS);
        ArrayList<Integer> relativeVector = new ArrayList<Integer>();
        relativeVector.add(pos.getX()-entityPos.getX());
        relativeVector.add(pos.getY()-entityPos.getY());
        relativeVector.add(pos.getZ()-entityPos.getZ());
        Level level = entity.getLevel();
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
                    toAdd.add(relativeVector.get(0) + e);
                    toAdd.add(relativeVector.get(1) + r);
                    toAdd.add(relativeVector.get(2) + t);
                    BlockPos testSpot;
                    testSpot = entityPos.offset(toAdd.get(0),toAdd.get(1),toAdd.get(2));
                    if(testSpot!=entityPos && level.getBlockState(testSpot)!=Blocks.END_STONE.defaultBlockState()&& !originalArrayList.contains(toAdd)) {
                        ArrayList<ArrayList<Integer>> newData = entity.getData(RIFT_POSSIBLE_LOCATIONS);
                        newData.add(toAdd);
                        entity.setData(RIFT_POSSIBLE_LOCATIONS, newData);
                    }
                }
            }
        }
    private static void expandRift(BlockEntity entity){
        ArrayList<ArrayList<Integer>> possibleLocations = entity.getData(RIFT_POSSIBLE_LOCATIONS);
        Level level = entity.getLevel();
        BlockPos entityPos = entity.getBlockPos();
        BlockPos Target = null;
        int originalSize=possibleLocations.size();
        double Total = 0.0;
        for(int i=0;i<originalSize;i++){
            Target = entityPos.offset(entity.getData(RIFT_POSSIBLE_LOCATIONS).get(i).get(0),
                    entity.getData(RIFT_POSSIBLE_LOCATIONS).get(i).get(1),
                    entity.getData(RIFT_POSSIBLE_LOCATIONS).get(i).get(2));
            Total += 1.0/(distanceCore(entity,Target) + distancePlain(entity,Target) +distanceLine(entity,Target));
        }
        Random random = new Random();
        double randomNumber = random.nextDouble(Total);
        int i=0;
        while(Total>0){
            if(i<entity.getData(RIFT_POSSIBLE_LOCATIONS).size()-1) {
                i++;
            }
            Target = entityPos.offset(entity.getData(RIFT_POSSIBLE_LOCATIONS).get(i).get(0),
                    entity.getData(RIFT_POSSIBLE_LOCATIONS).get(i).get(1),
                    entity.getData(RIFT_POSSIBLE_LOCATIONS).get(i).get(2));
            Total -= 1.0/((distanceCore(entity,Target))*0.0 + Math.pow(distancePlain(entity,Target),4) +distanceLine(entity,Target));
            if(Total<randomNumber){
                break;
            }
        }
        if(Target!=null) {
            level.setBlockAndUpdate(Target, Blocks.END_STONE.defaultBlockState());
            setPossible(entity, Target);
            ArrayList<Integer> remover = new ArrayList<Integer>();
            remover.add(Target.getX());
            remover.add(Target.getY());
            remover.add(Target.getZ());
            possibleLocations = entity.getData(RIFT_POSSIBLE_LOCATIONS);
            possibleLocations.remove(remover);
            entity.setData(RIFT_POSSIBLE_LOCATIONS,possibleLocations);
            entity.setData(RIFT_SIZE,entity.getData(RIFT_SIZE)+1);
            return;
        }
    }
    private static double distancePlain(BlockEntity entity,BlockPos pos){
        BlockPos entityPos = entity.getBlockPos();
        ArrayList<Double> normal = entity.getData(RIFT_PLAIN_NORMAL);
        double toReturn = 0.0;
        double s = 0.0;
        double normalX=normal.get(0),normalY=normal.get(1),normalZ=normal.get(2);
        s= ((normalX*entityPos.getX()+
                normalY*entityPos.getY()+
                normalZ*entityPos.getZ())-
                (normalX*pos.getX()+
                        normalY*pos.getY()+
                        normalZ*pos.getZ()))/
                (Math.pow(normalX,2)+
                        Math.pow(normalY,2)+
                        Math.pow(normalZ,2));
        double x = pos.getX() + s*normalX;
        double y = pos.getY() + s*normalY;
        double z = pos.getZ() + s*normalZ;
        toReturn = Math.pow(
                (Math.pow((entityPos.getX()-x),2)+
                Math.pow((entityPos.getY()-y),2)+
                Math.pow((entityPos.getZ()-z),2)),
                0.5);
        return toReturn;
    }

    private static double distanceLine(BlockEntity entity,BlockPos pos){
        BlockPos entityPos = entity.getBlockPos();
        double toReturn = 0.0;
        toReturn =0.0;
        return toReturn;
    }

    private static double distanceCore(BlockEntity entity,BlockPos pos){
        BlockPos entityPos = entity.getBlockPos();
        double toReturn = 0.0;
        toReturn = Math.pow((Math.pow((entityPos.getX()-pos.getX()),2)+
                Math.pow((entityPos.getY()-pos.getY()),2)+
                Math.pow((entityPos.getZ()-pos.getZ()),2)),0.5);
        return toReturn;
    }

    @Override
    protected void onPlace(BlockState state,  Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        BlockEntity entity = level.getBlockEntity(pos);
        entity.setData(RIFT_SIZE,1);
        entity.setData(RIFT_MAX_SIZE,10000);
        ArrayList<Double> data = new ArrayList<Double>();
        data.add(0.0);data.add(1.0);data.add(0.0);
        entity.setData(RIFT_PLAIN_NORMAL,data);
        ArrayList<Double> data2 = new ArrayList<Double>();
        data2.add(1.0);data2.add(0.0);data2.add(0.0);
        entity.setData(RIFT_LINE,data2);
        ArrayList<ArrayList<Integer>> empty = new ArrayList<ArrayList<Integer>>();
        entity.setData(RIFT_POSSIBLE_LOCATIONS,empty);
        setPossible(entity,pos);
        if (!level.isClientSide) {
            // Schedule a tick for the block every second (20 ticks)
            level.scheduleTick(pos, this, 2);
        }
        super.onPlace(state, level, pos, oldState, movedByPiston);
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        BlockEntity entity = level.getBlockEntity(pos);
        if(entity.getData(RIFT_SIZE)<=entity.getData(RIFT_MAX_SIZE)) {
            expandRift(entity);
            level.scheduleTick(pos, this, 2);
        }
        super.tick(state, level, pos, random);
    }
}
