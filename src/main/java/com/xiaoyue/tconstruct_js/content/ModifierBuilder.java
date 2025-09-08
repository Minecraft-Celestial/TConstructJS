package com.xiaoyue.tconstruct_js.content;

import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
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
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InteractionSource;
import slimeknights.tconstruct.library.tools.context.*;
import slimeknights.tconstruct.library.tools.nbt.*;
import slimeknights.tconstruct.library.tools.stat.FloatToolStat;
import slimeknights.tconstruct.library.tools.stat.ModifierStatsBuilder;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.function.Predicate;

public class ModifierBuilder {

    public transient int priority;
    public transient boolean isSingleLevel;
    public transient VolatileDataFunction addVolatileData;
    public transient ValidateFunction validateTool;
    public transient ToolStatModifyFunction addToolStats;
    public transient ConditionalStatFunction conditionalStat;
    public transient GetRepairFactorFunction getRepairFactor;
    public transient UseActionFunction toolUseAction;
    public transient AttributesFunction addAttributes;
    public transient GetToolDamageFunction getToolDamage;
    public transient ProcessLootFunction processLoot;
    public transient OnModifierRemoveFunction onModifierRemove;
    public transient InventoryTickFunction inventoryTick;
    public transient OnUseToolFunction onUseTool;
    public transient OnUsingToolFunction onUsingTick;
    public transient OnUsingToolFunction onStoppedUsing;
    public transient OnFinishUsingFunction onFinishUsing;
    public transient GetUseTimeFunction getUseTime;
    public transient GetUseAnimFunction getUseAnim;
    public transient GetBreakSpeedFunction getBreakSpeed;
    public transient BreakBlockFunction onAfterBreak;
    public transient BeforeMeleeHitFunction beforeMeleeHit;
    public transient GetMeleeDamageFunction getMeleeDamage;
    public transient AfterMeleeHitFunction afterMeleeHit;
    public transient OnDamageDealtFunction onDamageDealt;
    public transient UpdateToolLootingFunction updateToolLooting;
    public transient UpdateArmorLootingFunction updateArmorLooting;
    public transient ProjectileHitEntityFunction projectileHitEntity;
    public transient ProjectileHitBlockFunction projectileHitBlock;
    public transient ProjectileLaunchFunction projectileLaunch;
    public transient FindBowAmmoFunction findBowAmmo;
    public transient TooltipSettingFunction tooltipSetting;
    public transient DurabilityShowBarFunction durabilityShowBar;
    public transient DurabilityDisplayFunction getDurabilityWidth;
    public transient DurabilityDisplayFunction getDurabilityRGB;
    public transient ArmorAttackedFunction canBlockAttacked;
    public transient ArmorAttackedFunction armorTakeAttacked;
    public transient ArmorDamageModifyFunction modifyProtection;
    public transient ArmorDamageModifyFunction modifyDamageTaken;
    public transient ElytraFlightFunction elytraFlight;
    public transient EquipmentChangeFunction onEquipTool;
    public transient EquipmentChangeFunction onUnequipTool;

    public ModifierBuilder() {
        this.priority = 100;
        this.isSingleLevel = false;
    }

    public AttributeModifier getAttributeModifier(String uuid, String name, double amount, String operation) {
        return new AttributeModifier(UUID.fromString(uuid), name, amount, getOperation(operation));
    }

    public ToolAction toolActionOf(String id) {
        return ToolAction.get(id);
    }

    private AttributeModifier.Operation getOperation(String id) {
        return AttributeModifier.Operation.valueOf(id.toUpperCase(Locale.ROOT));
    }

    public Modifier createModifier() {
        return new BasicModifier(this);
    }

    public ModifierBuilder priority(int newPriority) {
        this.priority = newPriority;
        return this;
    }

    public ModifierBuilder isSingleLevel() {
        this.isSingleLevel = true;
        return this;
    }

    public ModifierBuilder addVolatileData(VolatileDataFunction function) {
        this.addVolatileData = function;
        return this;
    }

    public ModifierBuilder validateTool(ValidateFunction function) {
        this.validateTool = function;
        return this;
    }

    public ModifierBuilder addToolStats(ToolStatModifyFunction function) {
        this.addToolStats = function;
        return this;
    }

    public ModifierBuilder conditionalStat(ConditionalStatFunction function) {
        this.conditionalStat = function;
        return this;
    }

    public ModifierBuilder getRepairFactor(GetRepairFactorFunction function) {
        this.getRepairFactor = function;
        return this;
    }

    public ModifierBuilder toolUseAction(UseActionFunction function) {
        this.toolUseAction = function;
        return this;
    }

    public ModifierBuilder addAttributes(AttributesFunction function) {
        this.addAttributes = function;
        return this;
    }

    public ModifierBuilder getToolDamage(GetToolDamageFunction function) {
        this.getToolDamage = function;
        return this;
    }

    public ModifierBuilder onModifierRemove(OnModifierRemoveFunction function) {
        this.onModifierRemove = function;
        return this;
    }

    public ModifierBuilder processLoot(ProcessLootFunction function) {
        this.processLoot = function;
        return this;
    }

    public ModifierBuilder onInventoryTick(InventoryTickFunction function) {
        this.inventoryTick = function;
        return this;
    }

    public ModifierBuilder onUseTool(OnUseToolFunction function) {
        this.onUseTool = function;
        return this;
    }

    public ModifierBuilder onUsingTick(OnUsingToolFunction function) {
        this.onUsingTick = function;
        return this;
    }

    public ModifierBuilder onStoppedUsing(OnUsingToolFunction function) {
        this.onStoppedUsing = function;
        return this;
    }

    public ModifierBuilder onFinishUsing(OnFinishUsingFunction function) {
        this.onFinishUsing = function;
        return this;
    }

    public ModifierBuilder getUseTime(GetUseTimeFunction function) {
        this.getUseTime = function;
        return this;
    }

    public ModifierBuilder getUseAnim(GetUseAnimFunction function) {
        this.getUseAnim = function;
        return this;
    }

    public ModifierBuilder getBreakSpeed(GetBreakSpeedFunction function) {
        this.getBreakSpeed = function;
        return this;
    }

    public ModifierBuilder onAfterBreak(BreakBlockFunction function) {
        this.onAfterBreak = function;
        return this;
    }

    public ModifierBuilder onBeforeMeleeHit(BeforeMeleeHitFunction function) {
        this.beforeMeleeHit = function;
        return this;
    }

    public ModifierBuilder getMeleeDamage(GetMeleeDamageFunction function) {
        this.getMeleeDamage = function;
        return this;
    }

    public ModifierBuilder onAfterMeleeHit(AfterMeleeHitFunction function) {
        this.afterMeleeHit = function;
        return this;
    }

    public ModifierBuilder onDamageDealt(OnDamageDealtFunction function) {
        this.onDamageDealt = function;
        return this;
    }

    public ModifierBuilder updateToolLooting(UpdateToolLootingFunction function) {
        this.updateToolLooting = function;
        return this;
    }

    public ModifierBuilder updateArmorLooting(UpdateArmorLootingFunction function) {
        this.updateArmorLooting = function;
        return this;
    }

    public ModifierBuilder projectileHitEntity(ProjectileHitEntityFunction function) {
        this.projectileHitEntity = function;
        return this;
    }

    public ModifierBuilder projectileHitBlock(ProjectileHitBlockFunction function) {
        this.projectileHitBlock = function;
        return this;
    }

    public ModifierBuilder projectileLaunch(ProjectileLaunchFunction function) {
        this.projectileLaunch = function;
        return this;
    }

    public ModifierBuilder findBowAmmo(FindBowAmmoFunction function) {
        this.findBowAmmo = function;
        return this;
    }

    public ModifierBuilder tooltipSetting(TooltipSettingFunction function) {
        this.tooltipSetting = function;
        return this;
    }

    public ModifierBuilder isDurabilityShowBar(DurabilityShowBarFunction function) {
        this.durabilityShowBar = function;
        return this;
    }

    public ModifierBuilder getDurabilityWidth(DurabilityDisplayFunction function) {
        this.getDurabilityWidth = function;
        return this;
    }

    public ModifierBuilder getDurabilityRGB(DurabilityDisplayFunction function) {
        this.getDurabilityRGB = function;
        return this;
    }

    public ModifierBuilder canBlockAttacked(ArmorAttackedFunction function) {
        this.canBlockAttacked = function;
        return this;
    }

    public ModifierBuilder armorTakeAttacked(ArmorAttackedFunction function) {
        this.armorTakeAttacked = function;
        return this;
    }

    public ModifierBuilder modifyProtection(ArmorDamageModifyFunction function) {
        this.modifyProtection = function;
        return this;
    }

    public ModifierBuilder modifyDamageTaken(ArmorDamageModifyFunction function) {
        this.modifyDamageTaken = function;
        return this;
    }

    public ModifierBuilder setElytraFlight(ElytraFlightFunction function) {
        this.elytraFlight = function;
        return this;
    }

    public ModifierBuilder onEquip(EquipmentChangeFunction function) {
        this.onEquipTool = function;
        return this;
    }

    public ModifierBuilder onUnequip(EquipmentChangeFunction function) {
        this.onUnequipTool = function;
        return this;
    }

    // build
    @FunctionalInterface
    public interface VolatileDataFunction {
        void addVolatileData(IToolContext context, int level, ToolDataNBT volatileData);
    }

    @FunctionalInterface
    public interface ValidateFunction {
        @Nullable
        Component validate(IToolStackView tool, int level);
    }

    @FunctionalInterface
    public interface ToolStatModifyFunction {
        void addToolStats(IToolContext context, int level, ModifierStatsBuilder builder);
    }

    @FunctionalInterface
    public interface ConditionalStatFunction {
        float modifyStat(IToolStackView tool, int level, LivingEntity entity, FloatToolStat stat, float baseValue, float multiplier);
    }

    @FunctionalInterface
    public interface OnModifierRemoveFunction {
        @Nullable
        Component onRemoved(IToolStackView tool, Modifier modifier);
    }

    // behavior
    @FunctionalInterface
    public interface GetRepairFactorFunction {
        float getRepairFactor(IToolStackView tool, int level, float factor);
    }

    @FunctionalInterface
    public interface UseActionFunction {
        ToolAction canPerformAction(IToolStackView tool, int level);
    }

    @FunctionalInterface
    public interface AttributesFunction {
        Map<String, AttributeModifier> addAttributes(IToolStackView tool, int level, EquipmentSlot slot, Map<String, AttributeModifier> map);
    }

    @FunctionalInterface
    public interface GetToolDamageFunction {
        int getToolDamage(IToolStackView tool, int level, int amount, LivingEntity entity);
    }

    @FunctionalInterface
    public interface ProcessLootFunction {
        void processLoot(IToolStackView tool, int level, List<ItemStack> list, LootContext context);
    }

    // interaction
    @FunctionalInterface
    public interface InventoryTickFunction {
        void onInventoryTick(IToolStackView tool, int level, Level world, LivingEntity entity, int index, boolean isSelected, boolean isCorrectSlot, ItemStack stack);
    }

    @FunctionalInterface
    public interface OnUseToolFunction {
        boolean onToolUse(IToolStackView tool, int level, Player player, InteractionHand hand, InteractionSource source);
    }

    @FunctionalInterface
    public interface OnUsingToolFunction {
        void onUsingTool(IToolStackView tool, int level, LivingEntity entity, int timeLeft);
    }

    @FunctionalInterface
    public interface OnFinishUsingFunction {
        void onFinishUsing(IToolStackView tool, int level, LivingEntity entity);
    }

    @FunctionalInterface
    public interface GetUseTimeFunction {
        int getUseDuration(IToolStackView tool, int level);
    }

    @FunctionalInterface
    public interface GetUseAnimFunction {
        UseAnim getUseAction(IToolStackView tool, int level);
    }

    // mining
    @FunctionalInterface
    public interface GetBreakSpeedFunction {
        void getBreakSpeed(IToolStackView tool, int level, PlayerEvent.BreakSpeed event, Direction sideHit, boolean isEffective, float miningSpeedModifier);
    }

    @FunctionalInterface
    public interface BreakBlockFunction {
        void onAfterBlockBreak(IToolStackView tool, int level, ToolHarvestContext context);
    }

    // combat
    @FunctionalInterface
    public interface BeforeMeleeHitFunction {
        float onBeforeMeleeHit(IToolStackView tool, int level, ToolAttackContext context, float damage, float baseKnockback, float knockback);
    }

    @FunctionalInterface
    public interface GetMeleeDamageFunction {
        float getMeleeDamage(IToolStackView tool, int level, ToolAttackContext context, float baseDamage, float damage);
    }

    @FunctionalInterface
    public interface AfterMeleeHitFunction {
        void onAfterMeleeHit(IToolStackView tool, int level, ToolAttackContext context, float damageDealt);
    }

    @FunctionalInterface
    public interface OnDamageDealtFunction {
        void onDamageDealt(IToolStackView tool, int level, EquipmentContext context, EquipmentSlot slotType, LivingEntity target, DamageSource source, float amount, boolean isDirectDamage);
    }

    @FunctionalInterface
    public interface UpdateToolLootingFunction {
        int updateToolLooting(IToolStackView tool, int level, LootingContext context, int looting);
    }

    @FunctionalInterface
    public interface UpdateArmorLootingFunction {
        int updateArmorLooting(IToolStackView tool, int level, LootingContext context, EquipmentContext equipment, EquipmentSlot slot, int looting);
    }

    // ranged
    @FunctionalInterface
    public interface ProjectileHitEntityFunction {
        boolean onProjectileHitEntity(ModifierNBT modifiers, ModDataNBT persistentData, int level, Projectile projectile, EntityHitResult hit, LivingEntity attacker, LivingEntity target);
    }

    @FunctionalInterface
    public interface ProjectileHitBlockFunction {
        void onProjectileHitBlock(ModifierNBT modifiers, ModDataNBT persistentData, int level, Projectile projectile, BlockHitResult hit, LivingEntity attacker);
    }

    @FunctionalInterface
    public interface ProjectileLaunchFunction {
        void onProjectileLaunch(IToolStackView tool, int level, LivingEntity shooter, Projectile projectile, @Nullable AbstractArrow arrow, ModDataNBT persistentData, boolean primary);
    }

    @FunctionalInterface
    public interface FindBowAmmoFunction {
        ItemStack findAmmo(IToolStackView tool, int level, LivingEntity shooter, ItemStack standardAmmo, Predicate<ItemStack> ammoPredicate);
    }

    // display
    @FunctionalInterface
    public interface TooltipSettingFunction {
        void addTooltip(IToolStackView tool, int level, @Nullable Player player, List<Component> tooltip, TooltipKey tooltipKey, TooltipFlag tooltipFlag);
    }

    @FunctionalInterface
    public interface DurabilityShowBarFunction {
        boolean showDurabilityBar(IToolStackView tool, int level);
    }

    @FunctionalInterface
    public interface DurabilityDisplayFunction {
        int getDurabilityDisplay(IToolStackView tool, int level);
    }

    // armor
    @FunctionalInterface
    public interface ArmorAttackedFunction {
        boolean onAttacked(IToolStackView tool, int level, EquipmentContext context, EquipmentSlot slotType, DamageSource source, float amount);
    }

    @FunctionalInterface
    public interface ArmorDamageModifyFunction {
        float getDamageModifier(IToolStackView tool, int level, EquipmentContext context, EquipmentSlot slotType, DamageSource source, float modifyValue);
    }

    @FunctionalInterface
    public interface ElytraFlightFunction {
        boolean onFlightTick(IToolStackView tool, int level, LivingEntity entity, int flightTime);
    }

    @FunctionalInterface
    public interface EquipmentChangeFunction {
        void onToolChange(IToolStackView tool, int level, EquipmentChangeContext context);
    }
}
