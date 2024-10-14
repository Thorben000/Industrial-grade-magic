package net.thesia.industrialgrademagic.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.thesia.industrialgrademagic.IndustrialGradeMagic;
import net.thesia.industrialgrademagic.blocks.custome.RiftEmitterBlock;
import net.thesia.industrialgrademagic.blocks.custome.RiftOriginBlock;
import net.thesia.industrialgrademagic.item.ModItems;

import java.util.function.Supplier;

public class RiftBlock {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(IndustrialGradeMagic.MODID);
    private static boolean never(BlockState state, BlockGetter blockGetter, BlockPos pos) {
        return false;
    }
    public static final DeferredBlock<Block> RIFT_BLOCK = registerBlock("rift_block", () -> new Block(BlockBehaviour.Properties.of().strength(100000f).noOcclusion().noCollission().isValidSpawn(Blocks::never)));


    public static final DeferredBlock<Block> RIFT_ORIGIN_BLOCK = registerBlock("rift_origin_block", () -> new RiftOriginBlock(BlockBehaviour.Properties.of().strength(100000f)));

    public static final DeferredBlock<Block> RIFT_EMITTER_BLOCK = registerBlock("rift_emitter_block", () -> new RiftEmitterBlock(BlockBehaviour.Properties.of()));

    public static final DeferredBlock<Block> RIFT_BASIC_STABILIZER_BLOCK = registerBlock("rift_basic_stabilizer_block", () -> new Block(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> RIFT_BEAM_GENERATOR_BLOCK = registerBlock("rift_beam_generator_block",() -> new Block(BlockBehaviour.Properties.of()));

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block){
        DeferredBlock<T> toReturn = BLOCKS.register(name,block);
        registerBlockItem(name,toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block){
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventbus){
        BLOCKS.register(eventbus);
    }
}
