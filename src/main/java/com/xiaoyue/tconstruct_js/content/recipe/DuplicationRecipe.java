package com.xiaoyue.tconstruct_js.content.recipe;

import dev.latvian.mods.kubejs.fluid.InputFluid;
import dev.latvian.mods.kubejs.item.InputItem;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.FluidComponents;
import dev.latvian.mods.kubejs.recipe.component.ItemComponents;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;

public interface DuplicationRecipe {
    RecipeKey<InputItem> CAST = ItemComponents.INPUT.key("cast").optional(InputItem.EMPTY);
    RecipeKey<InputFluid> FLUID = FluidComponents.INPUT.key("fluid").defaultOptional();
    RecipeKey<Double> COOLING_TIME = NumberComponent.DOUBLE.key("cooling_time").optional(1.0);

    RecipeSchema TABLE_DUPLICATION = new RecipeSchema(CAST, FLUID, COOLING_TIME);
    RecipeSchema BASIN_DUPLICATION = new RecipeSchema(CAST, FLUID, COOLING_TIME);
}