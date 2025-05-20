package com.xiaoyue.tconstruct_js.content.recipe;

import dev.latvian.mods.kubejs.fluid.InputFluid;
import dev.latvian.mods.kubejs.fluid.OutputFluid;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.FluidComponents;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;

public interface MiscRecipe {
    RecipeKey<Double> TEMPERATURE = NumberComponent.DOUBLE.key("temperature").optional(100d);
    RecipeKey<InputFluid[]> FLUID_INPUTS = FluidComponents.INPUT_ARRAY.key("inputs").defaultOptional();
    RecipeKey<OutputFluid> FLUID_RESULT = FluidComponents.OUTPUT.key("result");

    RecipeSchema ALLOY_RECIPE = new RecipeSchema(FLUID_RESULT, FLUID_INPUTS, TEMPERATURE);
}