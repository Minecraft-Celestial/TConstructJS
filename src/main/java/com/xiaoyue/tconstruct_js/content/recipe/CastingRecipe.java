package com.xiaoyue.tconstruct_js.content.recipe;

import dev.latvian.mods.kubejs.fluid.InputFluid;
import dev.latvian.mods.kubejs.item.InputItem;
import dev.latvian.mods.kubejs.item.OutputItem;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.BooleanComponent;
import dev.latvian.mods.kubejs.recipe.component.FluidComponents;
import dev.latvian.mods.kubejs.recipe.component.ItemComponents;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;

public interface CastingRecipe {
    RecipeKey<InputFluid> FLUID = FluidComponents.INPUT.key("fluid").defaultOptional();
    RecipeKey<Boolean> CAST_CONSUME = BooleanComponent.BOOLEAN.key("cast_consumed").optional(false);
    RecipeKey<Boolean> SWITCH_SLOTS = BooleanComponent.BOOLEAN.key("switch_slots").optional(false);
    RecipeKey<Integer> COOLING_TIME = NumberComponent.INT.key("cooling_time").optional(20);
    RecipeKey<InputItem> CAST = ItemComponents.INPUT.key("cast").optional(InputItem.EMPTY);
    RecipeKey<InputItem> BOTTLE = ItemComponents.INPUT.key("bottle").optional(InputItem.EMPTY);
    RecipeKey<OutputItem> RESULT = ItemComponents.OUTPUT.key("result");

    RecipeSchema CASTING_TABLE = new RecipeSchema(RESULT, FLUID, CAST, CAST_CONSUME, COOLING_TIME, SWITCH_SLOTS);
    RecipeSchema CASTING_BASIN = new RecipeSchema(RESULT, FLUID, CAST, CAST_CONSUME, COOLING_TIME, SWITCH_SLOTS);
    RecipeSchema CASTING_TABLE_POTION = new RecipeSchema(RESULT, BOTTLE, FLUID, COOLING_TIME);
    RecipeSchema CASTING_BASIN_POTION = new RecipeSchema(RESULT, BOTTLE, FLUID, COOLING_TIME);
}