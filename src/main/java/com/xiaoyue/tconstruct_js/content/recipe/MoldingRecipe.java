package com.xiaoyue.tconstruct_js.content.recipe;

import dev.latvian.mods.kubejs.item.InputItem;
import dev.latvian.mods.kubejs.item.OutputItem;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.ItemComponents;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;

public interface MoldingRecipe {
    RecipeKey<OutputItem> RESULT = ItemComponents.OUTPUT.key("result");
    RecipeKey<InputItem> PATTERN = ItemComponents.INPUT.key("pattern").optional(InputItem.EMPTY);
    RecipeKey<InputItem> MATERIAL = ItemComponents.INPUT.key("material").optional(InputItem.EMPTY);

    RecipeSchema MOLDING_TABLE = new RecipeSchema(RESULT, PATTERN, MATERIAL);
    RecipeSchema MOLDING_BASIN = new RecipeSchema(RESULT, PATTERN, MATERIAL);
}