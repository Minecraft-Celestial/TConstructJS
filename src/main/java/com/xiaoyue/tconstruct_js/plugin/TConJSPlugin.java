package com.xiaoyue.tconstruct_js.plugin;

import com.xiaoyue.tconstruct_js.content.recipe.*;
import com.xiaoyue.tconstruct_js.event.TConEventGroup;
import com.xiaoyue.tconstruct_js.utils.SimpleTCon;
import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.recipe.schema.RegisterRecipeSchemasEvent;
import dev.latvian.mods.kubejs.script.BindingsEvent;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.tools.SlotType;
import slimeknights.tconstruct.library.tools.helper.ToolDamageUtil;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

public class TConJSPlugin extends KubeJSPlugin {

    @Override
    public void registerRecipeSchemas(RegisterRecipeSchemasEvent event) {
        event.namespace(TConstruct.MOD_ID)
                .register("casting_table", CastingRecipe.CASTING_TABLE)
                .register("casting_basin", CastingRecipe.CASTING_BASIN)
                .register("casting_table_potion", CastingRecipe.CASTING_TABLE_POTION)
                .register("casting_basin_potion", CastingRecipe.CASTING_BASIN_POTION)
                .register("table_duplication", DuplicationRecipe.TABLE_DUPLICATION)
                .register("basin_duplication", DuplicationRecipe.BASIN_DUPLICATION)
                .register("table_filling", FillingRecipe.TABLE_FILLING)
                .register("basin_filling", FillingRecipe.BASIN_FILLING)
                .register("molding_table", MoldingRecipe.MOLDING_TABLE)
                .register("molding_basin", MoldingRecipe.MOLDING_BASIN)
                .register("melting", MeltingRecipe.MELTING)
                .register("melting_fuel", MeltingRecipe.MELTING_FUEL)
                .register("alloy", MiscRecipe.ALLOY_RECIPE);
    }

    @Override
    public void registerEvents() {
        TConEventGroup.GROUP.register();
    }

    @Override
    public void registerBindings(BindingsEvent event) {
        event.add("SimpleTCon", SimpleTCon.class);
        event.add("TinkerToolStats", ToolStats.class);
        event.add("TinkerDamageHelper", ToolDamageUtil.class);
        event.add("TinkerSlotType", SlotType.class);
    }
}
