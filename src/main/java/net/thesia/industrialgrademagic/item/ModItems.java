package net.thesia.industrialgrademagic.item;

import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.thesia.industrialgrademagic.IndustrialGradeMagic;

public class ModItems {
    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(IndustrialGradeMagic.MODID);

    public static final DeferredItem<Item> RIFTBLOCKITEM = ITEMS.register("riftblockitem",() -> new Item(new Item.Properties()));
    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
