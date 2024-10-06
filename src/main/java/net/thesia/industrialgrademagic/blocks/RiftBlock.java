package net.thesia.industrialgrademagic.blocks;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.thesia.industrialgrademagic.IndustrialGradeMagic;
import net.thesia.industrialgrademagic.blocks.custome.RiftOriginBlock;
import net.thesia.industrialgrademagic.item.ModItems;

import java.util.function.Supplier;

public class RiftBlock {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(IndustrialGradeMagic.MODID);

    public static final DeferredBlock<Block> RIFT_BLOCK = registerBlock("rift_block", () -> new Block(BlockBehaviour.Properties.of().strength(100000f)));

    public static final DeferredBlock<Block> RIFT_ORIGIN_BLOCK = registerBlock("rift_origin_block", () -> new RiftOriginBlock(BlockBehaviour.Properties.of().strength(100000f)));

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
