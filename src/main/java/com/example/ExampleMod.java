package com.example;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ExampleMod implements ModInitializer {
    public static final String MOD_ID = "copperstick"; 

    public static final Item COPPER_STICK = new Item(new Item.Settings());

    @Override
    public void onInitialize() {
        Registry.register(
            Registries.ITEM, 
            Identifier.of(MOD_ID, "copper_stick"), 
            COPPER_STICK
        );

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.add(COPPER_STICK);
        });
    }
}
