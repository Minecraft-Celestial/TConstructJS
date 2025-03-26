package com.xiaoyue.tconstruct_js.content.recipe;

import dev.latvian.mods.kubejs.fluid.InputFluid;
import dev.latvian.mods.kubejs.fluid.OutputFluid;
import dev.latvian.mods.kubejs.item.InputItem;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.FluidComponents;
import dev.latvian.mods.kubejs.recipe.component.ItemComponents;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;

public interface MeltingRecipe {

    RecipeKey<InputItem> INPUT = ItemComponents.INPUT.key("ingredient");
    RecipeKey<Double> TEMPERATURE = NumberComponent.DOUBLE.key("temperature").optional(100d);
    RecipeKey<Double> TIME = NumberComponent.DOUBLE.key("time").optional(100d);
    RecipeKey<Integer> DURATION = NumberComponent.INT.key("duration").optional(100);
    RecipeKey<InputFluid> FLUID = FluidComponents.INPUT.key("fluid");
    RecipeKey<OutputFluid> FLUID_RESULT = FluidComponents.OUTPUT.key("result");

    RecipeSchema MELTING = new RecipeSchema(FLUID_RESULT, INPUT, TEMPERATURE, TIME);
    RecipeSchema MELTING_FUEL = new RecipeSchema(FLUID, TEMPERATURE, DURATION);

}
