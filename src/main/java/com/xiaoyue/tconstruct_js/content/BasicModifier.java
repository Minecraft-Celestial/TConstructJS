package com.xiaoyue.tconstruct_js.content;

import dev.latvian.mods.kubejs.registry.RegistryInfo;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.event.entity.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.armor.*;
import slimeknights.tconstruct.library.modifiers.hook.behavior.*;
import slimeknights.tconstruct.library.modifiers.hook.build.*;
import slimeknights.tconstruct.library.modifiers.hook.combat.*;
import slimeknights.tconstruct.library.modifiers.hook.display.DurabilityDisplayModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.display.TooltipModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.GeneralInteractionModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InteractionSource;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InventoryTickModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.mining.BlockBreakModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.mining.BreakSpeedModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.ranged.BowAmmoModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.ranged.ProjectileHitModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.ranged.ProjectileLaunchModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.*;
import slimeknights.tconstruct.library.tools.nbt.*;
import slimeknights.tconstruct.library.tools.stat.FloatToolStat;
import slimeknights.tconstruct.library.tools.stat.ModifierStatsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

public class BasicModifier extends Modifier implements VolatileDataModifierHook, ValidateModifierHook,
        ToolStatsModifierHook, ConditionalStatModifierHook, ModifierRemovalHook, RepairFactorModifierHook,
        ToolActionModifierHook, AttributesModifierHook, ToolDamageModifierHook, ProcessLootModifierHook,
        InventoryTickModifierHook, GeneralInteractionModifierHook, BreakSpeedModifierHook, BlockBreakModifierHook,
        MeleeHitModifierHook, MeleeDamageModifierHook, DamageDealtModifierHook, LootingModifierHook,
        ArmorLootingModifierHook, ProjectileHitModifierHook, ProjectileLaunchModifierHook, BowAmmoModifierHook,
        TooltipModifierHook, DamageBlockModifierHook, OnAttackedModifierHook, ProtectionModifierHook,
        ModifyDamageModifierHook, EquipmentChangeModifierHook, DurabilityDisplayModifierHook, ElytraFlightModifierHook {

    public final ModifierBuilder builder;

    public BasicModifier(ModifierBuilder builder) {
        this.builder = builder;
    }

    @Override
    protected void registerHooks(ModuleHookMap.Builder builder) {
        builder.addHook(this, ModifierHooks.VOLATILE_DATA, ModifierHooks.VALIDATE, ModifierHooks.TOOL_STATS, ModifierHooks.CONDITIONAL_STAT, ModifierHooks.REMOVE);
        builder.addHook(this, ModifierHooks.TOOL_DAMAGE, ModifierHooks.PROCESS_LOOT, ModifierHooks.REPAIR_FACTOR, ModifierHooks.TOOL_ACTION, ModifierHooks.ATTRIBUTES);
        builder.addHook(this, ModifierHooks.INVENTORY_TICK, ModifierHooks.GENERAL_INTERACT);
        builder.addHook(this, ModifierHooks.BREAK_SPEED, ModifierHooks.BLOCK_BREAK);
        builder.addHook(this, ModifierHooks.MELEE_HIT, ModifierHooks.MELEE_DAMAGE, ModifierHooks.DAMAGE_DEALT, ModifierHooks.WEAPON_LOOTING, ModifierHooks.ARMOR_LOOTING);
        builder.addHook(this, ModifierHooks.PROJECTILE_HIT, ModifierHooks.PROJECTILE_LAUNCH, ModifierHooks.BOW_AMMO);
        builder.addHook(this, ModifierHooks.TOOLTIP, ModifierHooks.DURABILITY_DISPLAY);
        builder.addHook(this, ModifierHooks.DAMAGE_BLOCK, ModifierHooks.ON_ATTACKED, ModifierHooks.PROTECTION, ModifierHooks.MODIFY_DAMAGE, ModifierHooks.EQUIPMENT_CHANGE, ModifierHooks.ELYTRA_FLIGHT);
    }

    @Override
    public @NotNull Component getDisplayName(int level) {
        if (builder.isSingleLevel) {
            return super.getDisplayName();
        }
        return super.getDisplayName(level);
    }

    @Override
    public int getPriority() {
        return this.builder.priority;
    }

    // build
    @Override
    public void addVolatileData(IToolContext context, ModifierEntry modifier, ToolDataNBT volatileData) {
        if (this.builder.addVolatileData != null) {
            this.builder.addVolatileData.addVolatileData(context, modifier.getLevel(), volatileData);
        }
    }

    @Override
    public Component validate(IToolStackView tool, ModifierEntry modifier) {
        if (this.builder.validateTool != null) {
            return this.builder.validateTool.validate(tool, modifier.getLevel());
        }
        return null;
    }

    @Override
    public void addToolStats(IToolContext tool, ModifierEntry modifier, ModifierStatsBuilder builder) {
        if (this.builder.addToolStats != null) {
            this.builder.addToolStats.addToolStats(tool, modifier.getLevel(), builder);
        }
    }

    @Override
    public float modifyStat(IToolStackView tool, ModifierEntry modifier, LivingEntity entity, FloatToolStat stat, float baseValue, float multiplier) {
        if (this.builder.conditionalStat != null) {
            return this.builder.conditionalStat.modifyStat(tool, modifier.getLevel(), entity, stat, baseValue, multiplier);
        }
        return baseValue;
    }

    @Override
    public Component onRemoved(IToolStackView tool, Modifier modifier) {
        if (this.builder.onModifierRemove != null) {
            return this.builder.onModifierRemove.onRemoved(tool, modifier);
        }
        return null;
    }

    // behavior
    @Override
    public float getRepairFactor(IToolStackView tool, ModifierEntry entry, float factor) {
        if (this.builder.getRepairFactor != null) {
            return this.builder.getRepairFactor.getRepairFactor(tool, entry.getLevel(), factor);
        }
        return factor;
    }

    @Override
    public void addAttributes(IToolStackView tool, ModifierEntry modifier, EquipmentSlot slot, BiConsumer<Attribute, AttributeModifier> consumer) {
        if (this.builder.addAttributes != null) {
            Map<String, AttributeModifier> modify = new HashMap<>();
            this.builder.addAttributes.addAttributes(tool, modifier.getLevel(), slot, modify).forEach((r, a) -> {
                consumer.accept(RegistryInfo.ATTRIBUTE.getValue(new ResourceLocation(r)), a);
            });
        }
    }

    @Override
    public boolean canPerformAction(IToolStackView tool, ModifierEntry modifier, ToolAction toolAction) {
        if (this.builder.toolUseAction != null) {
            return ToolAction.getActions().contains(this.builder.toolUseAction.canPerformAction(tool, modifier.getLevel()));
        }
        return false;
    }

    @Override
    public int onDamageTool(IToolStackView tool, ModifierEntry modifier, int amount, @Nullable LivingEntity entity) {
        if (this.builder.getToolDamage != null && entity != null) {
            return this.builder.getToolDamage.getToolDamage(tool, modifier.getLevel(), amount, entity);
        }
        return amount;
    }

    @Override
    public void processLoot(IToolStackView tool, ModifierEntry modifier, List<ItemStack> list, LootContext context) {
        if (this.builder.processLoot != null) {
            this.builder.processLoot.processLoot(tool, modifier.getLevel(), list, context);
        }
    }

    // interaction
    @Override
    public void onInventoryTick(IToolStackView tool, ModifierEntry modifier, Level world, LivingEntity entity, int index, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        if (this.builder.inventoryTick != null) {
            this.builder.inventoryTick.onInventoryTick(tool, modifier.getLevel(), world, entity, index, isSelected, isCorrectSlot, stack);
        }
    }

    @Override
    public InteractionResult onToolUse(IToolStackView tool, ModifierEntry modifier, Player player, InteractionHand hand, InteractionSource source) {
        if (this.builder.onUseTool != null) {
            if (this.builder.onUseTool.onToolUse(tool, modifier.getLevel(), player, hand, source)) {
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public void onUsingTick(IToolStackView tool, ModifierEntry modifier, LivingEntity entity, int timeLeft) {
        if (this.builder.onUsingTick != null) {
            this.builder.onUsingTick.onUsingTool(tool, modifier.getLevel(), entity, timeLeft);
        }
    }

    @Override
    public void onStoppedUsing(IToolStackView tool, ModifierEntry modifier, LivingEntity entity, int timeLeft) {
        if (this.builder.onStoppedUsing != null) {
            this.builder.onStoppedUsing.onUsingTool(tool, modifier.getLevel(), entity, timeLeft);
        }
    }

    @Override
    public void onFinishUsing(IToolStackView tool, ModifierEntry modifier, LivingEntity entity) {
        if (this.builder.onFinishUsing != null) {
            this.builder.onFinishUsing.onFinishUsing(tool, modifier.getLevel(), entity);
        }
    }

    @Override
    public int getUseDuration(IToolStackView tool, ModifierEntry modifier) {
        if (this.builder.getUseTime != null) {
            return this.builder.getUseTime.getUseDuration(tool, modifier.getLevel());
        }
        return 0;
    }

    @Override
    public UseAnim getUseAction(IToolStackView tool, ModifierEntry modifier) {
        if (this.builder.getUseAnim != null) {
            return this.builder.getUseAnim.getUseAction(tool, modifier.getLevel());
        }
        return UseAnim.NONE;
    }

    // mining
    @Override
    public void onBreakSpeed(IToolStackView tool, ModifierEntry modifier, PlayerEvent.BreakSpeed event, Direction sideHit, boolean isEffective, float miningSpeedModifier) {
        if (this.builder.getBreakSpeed != null) {
            this.builder.getBreakSpeed.getBreakSpeed(tool, modifier.getLevel(), event, sideHit, isEffective, miningSpeedModifier);
        }
    }

    @Override
    public void afterBlockBreak(IToolStackView tool, ModifierEntry modifier, ToolHarvestContext context) {
        if (this.builder.onAfterBreak != null) {
            this.builder.onAfterBreak.onAfterBlockBreak(tool, modifier.getLevel(), context);
        }
    }

    // combat
    @Override
    public float beforeMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damage, float baseKnockback, float knockback) {
        if (this.builder.beforeMeleeHit != null) {
            return this.builder.beforeMeleeHit.onBeforeMeleeHit(tool, modifier.getLevel(), context, damage, baseKnockback, knockback);
        }
        return knockback;
    }

    @Override
    public float getMeleeDamage(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float baseDamage, float damage) {
        if (this.builder.getMeleeDamage != null) {
            return this.builder.getMeleeDamage.getMeleeDamage(tool, modifier.getLevel(), context, baseDamage, damage);
        }
        return damage;
    }

    @Override
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        if (this.builder.afterMeleeHit != null) {
            this.builder.afterMeleeHit.onAfterMeleeHit(tool, modifier.getLevel(), context, damageDealt);
        }
    }

    @Override
    public void onDamageDealt(IToolStackView tool, ModifierEntry modifier, EquipmentContext context, EquipmentSlot slotType, LivingEntity target, DamageSource source, float amount, boolean isDirectDamage) {
        if (this.builder.onDamageDealt != null) {
            this.builder.onDamageDealt.onDamageDealt(tool, modifier.getLevel(), context, slotType, target, source, amount, isDirectDamage);
        }
    }

    @Override
    public int updateLooting(IToolStackView tool, ModifierEntry modifier, LootingContext context, int looting) {
        if (this.builder.updateToolLooting != null) {
            return this.builder.updateToolLooting.updateToolLooting(tool, modifier.getLevel(), context, looting);
        }
        return looting;
    }

    @Override
    public int updateArmorLooting(IToolStackView tool, ModifierEntry modifier, LootingContext context, EquipmentContext equipment, EquipmentSlot slot, int looting) {
        if (this.builder.updateArmorLooting != null) {
            return this.builder.updateArmorLooting.updateArmorLooting(tool, modifier.getLevel(), context, equipment, slot, looting);
        }
        return looting;
    }

    // ranged
    @Override
    public boolean onProjectileHitEntity(ModifierNBT modifiers, ModDataNBT persistentData, ModifierEntry modifier, Projectile projectile, EntityHitResult hit, @Nullable LivingEntity attacker, @Nullable LivingEntity target) {
        if (this.builder.projectileHitEntity != null && attacker != null && target != null) {
            return this.builder.projectileHitEntity.onProjectileHitEntity(modifiers, persistentData, modifier.getLevel(), projectile, hit, attacker, target);
        }
        return false;
    }

    @Override
    public void onProjectileHitBlock(ModifierNBT modifiers, ModDataNBT persistentData, ModifierEntry modifier, Projectile projectile, BlockHitResult hit, @Nullable LivingEntity attacker) {
        if (this.builder.projectileHitBlock != null && attacker != null) {
            this.builder.projectileHitBlock.onProjectileHitBlock(modifiers, persistentData, modifier.getLevel(), projectile, hit, attacker);
        }
    }

    @Override
    public void onProjectileLaunch(IToolStackView tool, ModifierEntry modifier, LivingEntity shooter, Projectile projectile, @Nullable AbstractArrow arrow, ModDataNBT persistentData, boolean primary) {
        if (this.builder.projectileLaunch != null) {
            this.builder.projectileLaunch.onProjectileLaunch(tool, modifier.getLevel(), shooter, projectile, arrow, persistentData, primary);
        }
    }

    @Override
    public ItemStack findAmmo(IToolStackView tool, ModifierEntry modifier, LivingEntity shooter, ItemStack standardAmmo, Predicate<ItemStack> ammoPredicate) {
        if (this.builder.findBowAmmo != null) {
            return this.builder.findBowAmmo.findAmmo(tool, modifier.getLevel(), shooter, standardAmmo, ammoPredicate);
        }
        return standardAmmo;
    }

    // display
    @Override
    public void addTooltip(IToolStackView tool, ModifierEntry modifier, @Nullable Player player, List<Component> tooltip, TooltipKey tooltipKey, TooltipFlag tooltipFlag) {
        if (this.builder.tooltipSetting != null) {
            this.builder.tooltipSetting.addTooltip(tool, modifier.getLevel(), player, tooltip, tooltipKey, tooltipFlag);
        }
    }

    @Override
    public @Nullable Boolean showDurabilityBar(IToolStackView tool, ModifierEntry modifier) {
        if (this.builder.durabilityShowBar != null) {
            return this.builder.durabilityShowBar.showDurabilityBar(tool, modifier.getLevel());
        }
        return null;
    }

    @Override
    public int getDurabilityWidth(IToolStackView tool, ModifierEntry modifier) {
        if (this.builder.getDurabilityWidth != null) {
            return this.builder.getDurabilityWidth.getDurabilityDisplay(tool, modifier.getLevel());
        }
        return -1;
    }

    @Override
    public int getDurabilityRGB(IToolStackView tool, ModifierEntry modifier) {
        if (this.builder.getDurabilityRGB != null) {
            return this.builder.getDurabilityRGB.getDurabilityDisplay(tool, modifier.getLevel());
        }
        return -1;
    }

    // armor
    @Override
    public boolean isDamageBlocked(IToolStackView tool, ModifierEntry modifier, EquipmentContext context, EquipmentSlot slotType, DamageSource source, float amount) {
        if (this.builder.canBlockAttacked != null) {
            return this.builder.canBlockAttacked.onAttacked(tool, modifier.getLevel(), context, slotType, source, amount);
        }
        return false;
    }

    @Override
    public void onAttacked(IToolStackView tool, ModifierEntry modifier, EquipmentContext context, EquipmentSlot slotType, DamageSource source, float amount, boolean isDirectDamage) {
        if (this.builder.armorTakeAttacked != null) {
            this.builder.armorTakeAttacked.onAttacked(tool, modifier.getLevel(), context, slotType, source, amount);
        }
    }

    @Override
    public float getProtectionModifier(IToolStackView tool, ModifierEntry modifier, EquipmentContext context, EquipmentSlot slotType, DamageSource source, float modifierValue) {
        if (this.builder.modifyProtection != null) {
            return this.builder.modifyProtection.getDamageModifier(tool, modifier.getLevel(), context, slotType, source, modifierValue);
        }
        return modifierValue;
    }

    public float modifyDamageTaken(IToolStackView tool, ModifierEntry modifier, EquipmentContext context, EquipmentSlot slotType, DamageSource source, float amount, boolean isDirectDamage) {
        if (this.builder.modifyDamageTaken != null) {
            return this.builder.modifyDamageTaken.getDamageModifier(tool, modifier.getLevel(), context, slotType, source, amount);
        }
        return amount;
    }

    @Override
    public boolean elytraFlightTick(IToolStackView tool, ModifierEntry modifier, LivingEntity entity, int flightTick) {
        if (this.builder.elytraFlight != null) {
            return this.builder.elytraFlight.onFlightTick(tool, modifier.getLevel(), entity, flightTick);
        }
        return true;
    }

    @Override
    public void onEquip(IToolStackView tool, ModifierEntry modifier, EquipmentChangeContext context) {
        if (this.builder.onEquipTool != null) {
            this.builder.onEquipTool.onToolChange(tool, modifier.getLevel(), context);
        }
    }

    @Override
    public void onUnequip(IToolStackView tool, ModifierEntry modifier, EquipmentChangeContext context) {
        if (this.builder.onUnequipTool != null) {
            this.builder.onUnequipTool.onToolChange(tool, modifier.getLevel(), context);
        }
    }

}
