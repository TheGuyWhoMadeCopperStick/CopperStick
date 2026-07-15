package com.example;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.world.World;

public class CopperGolemEntity extends IronGolemEntity {

    public CopperGolemEntity(EntityType<? extends IronGolemEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createCopperGolemAttributes() {
        return IronGolemEntity.createIronGolemAttributes()
                .add(EntityAttributes.MAX_HEALTH, 30.0)
                .add(EntityAttributes.MOVEMENT_SPEED, 0.25);
    }

    @Override
    protected void initGoals() {
        // This adds our custom chest search AI goal!
        this.goalSelector.add(1, new GolemFindChestGoal(this, 1.2, 16, 2));
        this.goalSelector.add(2, new WanderAroundFarGoal(this, 0.6));
        this.goalSelector.add(3, new LookAroundGoal(this));
    }
}
