package net.thesia.industrialgrademagic.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.thesia.industrialgrademagic.IndustrialGradeMagic;
import net.thesia.industrialgrademagic.blocks.RiftBlock;

import java.util.function.Supplier;

public class RiftEmitterBlockEntity extends BlockEntity {
    public RiftEmitterBlockEntity(BlockPos pos, BlockState blockState) {
        super(RIFT_EMITTER_BLOCK_ENTITY.get(), pos, blockState);
    }
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, IndustrialGradeMagic.MODID);

    public static final Supplier<BlockEntityType<RiftEmitterBlockEntity>> RIFT_EMITTER_BLOCK_ENTITY =
            BLOCK_ENTITY_TYPES.register(
                    "rift_emitter_block_entity",
                    () -> BlockEntityType.Builder.of(
                            RiftEmitterBlockEntity::new,
                            RiftBlock.RIFT_EMITTER_BLOCK.get()
                    ).build(null)
            );
    public static void register(IEventBus eventBus){
        BLOCK_ENTITY_TYPES.register(eventBus);
    }
}
