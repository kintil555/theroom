package com.kintil555.backrooms.item;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.consume.UseAction;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

/**
 * Backrooms staple item: quenches thirst-like hunger and grants a brief
 * regeneration buff. Consumption logic only, no world interactions yet.
 */
public class AlmondWaterItem extends Item {

    public AlmondWaterItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult use(World world, net.minecraft.entity.player.PlayerEntity user, Hand hand) {
        return ItemUsage.consumeHeldItem(world, user, hand);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, net.minecraft.entity.LivingEntity user) {
        if (!world.isClient) {
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 100, 0));
            world.playSound(null, user.getBlockPos(), SoundEvents.ENTITY_GENERIC_DRINK,
                    SoundCategory.PLAYERS, 1.0F, 1.0F);
        }
        if (user instanceof net.minecraft.entity.player.PlayerEntity player && !player.getAbilities().creativeMode) {
            stack.decrement(1);
        }
        return stack;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }

    @Override
    public int getMaxUseTime(ItemStack stack, net.minecraft.entity.LivingEntity user) {
        return 32;
    }
}
