package net.thesia.industrialgrademagic.blocks.custome;


import java.lang.Math;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.thesia.industrialgrademagic.entity.RiftOriginBlockEntity;
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

    private static void expandRift(BlockEntity entity,Integer subLevel){

    }
    private static BlockPos pointSymmetry(BlockPos mirror, BlockPos pos){
        int x,y,z;
        x = (mirror.getX()-pos.getX())*2;
        y = (mirror.getY()-pos.getY())*2;
        z = (mirror.getZ()-pos.getZ())*2;
        pos = pos.offset(x,y,z);
        return pos;
    }
    private static void calculateCorners(BlockEntity entity){
        ArrayList<Double> normal = entity.getData(RIFT_PLAIN_NORMAL);
        ArrayList<Double> line = entity.getData(RIFT_LINE);
        Integer riftSize = entity.getData(RIFT_SIZE);
        double normalX=normal.get(0),normalY=normal.get(1),normalZ=normal.get(2);
        double lineX=line.get(0),lineY=line.get(1),lineZ=line.get(2);
        double crossX=normalY*lineZ-normalZ*lineY;
        double crossY=normalZ*lineX-normalX*lineZ;
        double crossZ=normalX*lineY-normalY*lineX;
        BlockPos corePos = entity.getBlockPos();
        //calculate A1 and A2 based on the normal of the plain
        double s= (riftSize*3.0)/ (Math.pow(Math.pow(normalX,2)+Math.pow(normalY,2)+Math.pow(normalZ,2),0.5));
        System.out.println(s);
        BlockPos A1 = corePos;
        A1 = A1.offset((int)Math.round(s*normalX),
                (int)Math.round(s*normalY),
                (int)Math.round(s*normalZ));
        BlockPos A2 = pointSymmetry(corePos,A1);
        //calculate B1 and B2 based on the line
        s=  (riftSize*1.0) / (Math.pow(Math.pow(lineX,2)+Math.pow(lineY,2)+Math.pow(lineZ,2),0.5));
        System.out.println(s);
        BlockPos B1 = corePos;
        B1 = B1.offset((int)Math.round(s*lineX),
                (int)Math.round(s*lineY),
                (int)Math.round(s*lineZ));
        BlockPos B2 = pointSymmetry(corePos,B1);
        //calculate C1 and C2 based on the cross product of line and plain normals
        s= (riftSize*2.0)/(Math.pow(Math.pow(crossX,2)+Math.pow(crossY,2)+Math.pow(crossZ,2),0.5));
        System.out.println(s);
        BlockPos C1 = corePos;
        C1 = C1.offset((int)Math.round(s*crossX),
                (int)Math.round(s*crossY),
                (int)Math.round(s*crossZ));
        BlockPos C2 = pointSymmetry(corePos,C1);
        //now find the max X,Y,Z values ^^
        int maxX;int maxY;int maxZ;
        ArrayList<Integer> helperArrayList= new ArrayList<Integer>();
        //add all X into an arraylist and sort based on value of int
        helperArrayList.add(A1.getX());
        helperArrayList.add(A2.getX());
        helperArrayList.add(B1.getX());
        helperArrayList.add(B2.getX());
        helperArrayList.add(C1.getX());
        helperArrayList.add(C2.getX());
        helperArrayList.sort(null);
        System.out.println(helperArrayList);
        maxX = helperArrayList.getLast();
        helperArrayList.clear();
        //now for Y
        helperArrayList.add(A1.getY());
        helperArrayList.add(A2.getY());
        helperArrayList.add(B1.getY());
        helperArrayList.add(B2.getY());
        helperArrayList.add(C1.getY());
        helperArrayList.add(C2.getY());
        helperArrayList.sort(null);
        System.out.println(helperArrayList);
        maxY = helperArrayList.getLast();
        helperArrayList.clear();
        //now for Z
        helperArrayList.add(A1.getZ());
        helperArrayList.add(A2.getZ());
        helperArrayList.add(B1.getZ());
        helperArrayList.add(B2.getZ());
        helperArrayList.add(C1.getZ());
        helperArrayList.add(C2.getZ());
        helperArrayList.sort(null);
        System.out.println(helperArrayList);
        maxZ = helperArrayList.getLast();
        helperArrayList.clear();
        //save relative vectors^^
        BlockPos place = entity.getBlockPos();
        place = place.offset(-1*place.getX(),-1*place.getY(),-1*place.getZ());
        place = place.offset(maxX,maxY,maxZ);
        entity.getLevel().setBlockAndUpdate(place,Blocks.END_STONE.defaultBlockState());
        BlockPos place1 = pointSymmetry(corePos,place);
        entity.getLevel().setBlockAndUpdate(place1,Blocks.END_STONE.defaultBlockState());

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
        entity.setData(RIFT_SIZE,6);//this represents the max distance any rift object can have from the rift
        ArrayList<Double> data = new ArrayList<Double>();
        data.add(0.0);data.add(1.0);data.add(0.0);
        entity.setData(RIFT_PLAIN_NORMAL,data);
        ArrayList<Double> data2 = new ArrayList<Double>();
        data2.add(1.0);data2.add(0.0);data2.add(0.0);
        entity.setData(RIFT_LINE,data2);
        ArrayList<ArrayList<Integer>> empty = new ArrayList<ArrayList<Integer>>();
        entity.setData(RIFT_POSSIBLE_LOCATIONS,empty);
        calculateCorners(entity);
        if (!level.isClientSide) {
            // Schedule a tick for the block every second (20 ticks)
            level.scheduleTick(pos, this, 2);
        }
        super.onPlace(state, level, pos, oldState, movedByPiston);
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        BlockEntity entity = level.getBlockEntity(pos);
        if(!entity.getData(RIFT_EXPANSION_DONE)) {
            expandRift(entity,entity.getData(RIFT_EXPANSION_SUBLEVEL));
            level.scheduleTick(pos, this, 2);
        }
        super.tick(state, level, pos, random);
    }
}
