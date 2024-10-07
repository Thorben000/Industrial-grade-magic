package net.thesia.industrialgrademagic.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.thesia.industrialgrademagic.IndustrialGradeMagic;
import net.thesia.industrialgrademagic.blocks.RiftBlock;

import java.util.function.Supplier;

public class RiftOriginBlockEntity extends BlockEntity {

    public RiftOriginBlockEntity(BlockPos pos, BlockState blockState) {
        super(RIFT_ORIGIN_BLOCK_ENTITY.get(), pos, blockState);
    }

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, IndustrialGradeMagic.MODID);

    public static final Supplier<BlockEntityType<RiftOriginBlockEntity>> RIFT_ORIGIN_BLOCK_ENTITY =
            BLOCK_ENTITY_TYPES.register(
            "rift_origin_block_entity",
            () -> BlockEntityType.Builder.of(
                    RiftOriginBlockEntity::new,
                    RiftBlock.RIFT_ORIGIN_BLOCK.get()
            ).build(null)
            );




}
