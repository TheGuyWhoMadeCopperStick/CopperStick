package com.example;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;

public class GolemFindChestGoal extends MoveToBlockGoal {
    private final PathAwareEntity golem;

    // We changed the constructor to match the exact signature Minecraft's MoveToBlockGoal expects!
    public GolemFindChestGoal(PathAwareEntity golem, double speed, int range, int maxYDifference) {
        super(golem, speed, range, maxYDifference);
        this.golem = golem;
    }

    @Override
    protected boolean isTargetPos(WorldView world, BlockPos pos) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof ChestBlockEntity) {
            NbtCompound nbt = blockEntity.createNbtWithId(this.golem.getWorld().getRegistryManager());
            return !nbt.getBoolean("GolemInvisible");
        }
        return false;
    }
}
