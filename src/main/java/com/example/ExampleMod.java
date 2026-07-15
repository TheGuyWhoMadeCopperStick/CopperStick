package com.example;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;

public class ExampleMod implements ModInitializer {
    public static final String MOD_ID = "copperstick"; 

    public static final Item COPPER_STICK = new Item(new Item.Settings());

    public static final EntityType<CopperGolemEntity> COPPER_GOLEM = Registry.register(
        Registries.ENTITY_TYPE,
        Identifier.of(MOD_ID, "copper_golem"),
        EntityType.Builder.create(CopperGolemEntity::new, SpawnGroup.MISC)
            .dimensions(0.6f, 1.2f)
            .build()
    );

    @Override
    public void onInitialize() {
        Registry.register(
            Registries.ITEM, 
            Identifier.of(MOD_ID, "copper_stick"), 
            COPPER_STICK
        );

        FabricDefaultAttributeRegistry.register(COPPER_GOLEM, CopperGolemEntity.createCopperGolemAttributes());

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.add(COPPER_STICK);
        });

        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            if (world.isClient() || hand != Hand.MAIN_HAND) {
                return ActionResult.PASS;
            }

            ItemStack stack = player.getStackInHand(hand);
            if (!stack.isOf(COPPER_STICK)) {
                return ActionResult.PASS;
            }

            BlockEntity blockEntity = world.getBlockEntity(hitResult.getBlockPos());
            if (blockEntity instanceof ChestBlockEntity) {
                NbtCompound nbt = blockEntity.createNbtWithId(world.getRegistryManager());
                nbt.putBoolean("GolemInvisible", true);
                blockEntity.readNbt(nbt, world.getRegistryManager());
                blockEntity.markDirty();
                
                player.sendMessage(Text.literal("Chest invisible"), true);
                return ActionResult.SUCCESS;
            }

            return ActionResult.PASS;
        });

        AttackBlockCallback.EVENT.register((player, world, hand, pos, direction) -> {
            if (world.isClient() || hand != Hand.MAIN_HAND) {
                return ActionResult.PASS;
            }

            ItemStack stack = player.getStackInHand(hand);
            if (!stack.isOf(COPPER_STICK)) {
                return ActionResult.PASS;
            }

            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof ChestBlockEntity) {
                NbtCompound nbt = blockEntity.createNbtWithId(world.getRegistryManager());
                nbt.putBoolean("GolemInvisible", false);
                blockEntity.readNbt(nbt, world.getRegistryManager());
                blockEntity.markDirty();

                player.sendMessage(Text.literal("Chest visible"), true);
                return ActionResult.SUCCESS;
            }

            return ActionResult.PASS;
        });
    }
}
