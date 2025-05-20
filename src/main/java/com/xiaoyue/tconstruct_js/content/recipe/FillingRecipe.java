package com.xiaoyue.tconstruct_js.content.recipe;

import dev.latvian.mods.kubejs.item.InputItem;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.ItemComponents;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;

public interface FillingRecipe {
    RecipeKey<Integer> FLUID_AMOUNT = NumberComponent.INT.key("fluid_amount").optional(100);
    RecipeKey<InputItem> CONTAINER = ItemComponents.INPUT.key("container").optional(InputItem.EMPTY);

    RecipeSchema TABLE_FILLING = new RecipeSchema(FLUID_AMOUNT, CONTAINER);
    RecipeSchema BASIN_FILLING = new RecipeSchema(FLUID_AMOUNT, CONTAINER);
}