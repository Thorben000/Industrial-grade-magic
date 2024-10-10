package net.thesia.industrialgrademagic.utility;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.thesia.industrialgrademagic.IndustrialGradeMagic;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Supplier;

public class RiftHandler {
    private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, IndustrialGradeMagic.MODID);
    public static final Supplier<AttachmentType<ArrayList<ArrayList<Integer>>>> RIFT_POSSIBLE_LOCATIONS =
            ATTACHMENT_TYPES.register("rift_possible_locations",
                    () -> AttachmentType.builder(() -> new ArrayList<ArrayList<Integer>>()).build()
            );
    public static final Supplier<AttachmentType<Integer>> RIFT_SIZE =
            ATTACHMENT_TYPES.register("rift_size",
                    () -> AttachmentType.builder(()-> 1).build()
            );

    public static final Supplier<AttachmentType<Integer>> RIFT_MAX_SIZE =
            ATTACHMENT_TYPES.register("rift_max_size",
            () -> AttachmentType.builder(()->1).build()
            );

    public static final Supplier<AttachmentType<ArrayList<Double>>> RIFT_LINE =
            ATTACHMENT_TYPES.register("rift_line",
                    () -> AttachmentType.builder(() -> new ArrayList<Double>()).build()
            );

    public static final Supplier<AttachmentType<ArrayList<Double>>> RIFT_PLAIN_NORMAL =
            ATTACHMENT_TYPES.register("rift_plain_normal",
                    () -> AttachmentType.builder(() -> new ArrayList<Double>()).build()
            );



    public static void register(IEventBus eventbus){
        ATTACHMENT_TYPES.register(eventbus);
    }
}
