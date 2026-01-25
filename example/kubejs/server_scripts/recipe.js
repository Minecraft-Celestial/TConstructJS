ServerEvents.recipes((event) => {
  const { tconstruct } = event.recipes;

  // tconstruct:alloy
  tconstruct.alloy(Fluid.of("tconstruct:molten_obsidian", 1000), [Fluid.water(500), Fluid.lava(500)]);
  tconstruct.alloy(Fluid.of("tconstruct:molten_obsidian", 1000), [Fluid.water(500), Fluid.lava(500)], 200);
  tconstruct.alloy(Fluid.of("tconstruct:molten_obsidian", 1000), [Fluid.water(500), Fluid.lava(500)]).temperature(400);

  // tconstruct:basin
  tconstruct.basin_duplication("diamond", "tconstruct:molten_diamond");
  tconstruct.basin_duplication("diamond", "tconstruct:molten_diamond", 200);
  tconstruct.basin_duplication("diamond", "tconstruct:molten_diamond").cooling_time(400);

  // tconstruct:basin_filling
  tconstruct.basin_filling(1000, "apple");

  // tconstruct:casting_basin
  tconstruct.casting_basin("raw_gold", Fluid.of("tconstruct:molten_glass", 200)).cooling_time(200);
  tconstruct.casting_basin("diamond", Fluid.of("tconstruct:molten_diamond", 1000), "apple", true, 200);
  tconstruct
    .casting_basin("diamond", Fluid.of("tconstruct:molten_diamond", 1000), "apple")
    .cast_consumed(true)
    .cooling_time(400);
  tconstruct.casting_basin("diamond", Fluid.of("tconstruct:molten_diamond", 1000), "apple", true, 200, true);
  tconstruct
    .casting_basin("diamond", Fluid.of("tconstruct:molten_diamond", 1000), "apple")
    .cast_consumed(true)
    .cooling_time(400)
    .switch_slots(true);

  // tconstruct:casting_basin_potion
  tconstruct.casting_basin_potion("diamond", "apple", Fluid.of("tconstruct:molten_diamond", 1000));
  tconstruct.casting_basin_potion("diamond", "apple", Fluid.of("tconstruct:molten_diamond", 1000), 200);
  tconstruct.casting_basin_potion("diamond", "apple", Fluid.of("tconstruct:molten_diamond", 1000)).cooling_time(400);

  // tconstruct:casting_table
  tconstruct.casting_table("diamond", Fluid.of("tconstruct:molten_diamond", 1000), "apple", true, 200);
  tconstruct
    .casting_table("diamond", Fluid.of("tconstruct:molten_diamond", 1000), "apple")
    .cast_consumed(true)
    .cooling_time(400);
  tconstruct.casting_table("diamond", Fluid.of("tconstruct:molten_diamond", 1000), "apple", true, 200, true);
  tconstruct
    .casting_table("diamond", Fluid.of("tconstruct:molten_diamond", 1000), "apple")
    .cast_consumed(true)
    .cooling_time(400)
    .switch_slots(true);

  // tconstruct:casting_table_potion
  tconstruct.casting_table_potion("diamond", "apple", Fluid.of("tconstruct:molten_diamond", 1000));
  tconstruct.casting_table_potion("diamond", "apple", Fluid.of("tconstruct:molten_diamond", 1000), 200);
  tconstruct.casting_table_potion("diamond", "apple", Fluid.of("tconstruct:molten_diamond", 1000)).cooling_time(400);

  // tconstruct:melting
  tconstruct.melting(Fluid.of("tconstruct:molten_diamond", 1000), "diamond");
  tconstruct.melting(Fluid.of("tconstruct:molten_diamond", 1000), "diamond", 200);
  tconstruct.melting(Fluid.of("tconstruct:molten_diamond", 1000), "diamond").temperature(400);
  tconstruct.melting(Fluid.of("tconstruct:molten_diamond", 1000), "diamond", 600, 200);
  tconstruct.melting(Fluid.of("tconstruct:molten_diamond", 1000), "diamond").temperature(400).time(200);

  // tconstruct:melting_fuel
  tconstruct.melting_fuel(1000, Fluid.of("milk", 1000), 20, 2000);
  tconstruct.melting_fuel(1000, Fluid.of("milk", 1000)).temperature(4000).rate(20);

  // tconstruct:molding_basin
  tconstruct.molding_table("diamond", "apple", "stick");

  // tconstruct:table_duplication
  tconstruct.table_duplication("diamond", "tconstruct:molten_diamond");
  tconstruct.table_duplication("diamond", "tconstruct:molten_diamond", 200);
  tconstruct.table_duplication("diamond", "tconstruct:molten_diamond").cooling_time(400);

  // tconstruct:table_filling
  tconstruct.table_filling(1000, "apple");
});
