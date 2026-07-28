package com.sidav.moa.game.tech

import com.sidav.moa.game.space.PlanetGrowth
import com.sidav.moa.game.space.PlanetType
import com.sidav.moa.game.tech.items.CloningTech
import com.sidav.moa.game.tech.items.ControlledEnvironmentTech
import com.sidav.moa.game.tech.items.EcoRestorationTech
import com.sidav.moa.game.tech.items.ImprovedIndustrialTech
import com.sidav.moa.game.tech.items.ImprovedRoboticControlsTech
import com.sidav.moa.game.tech.items.ImprovedTerraformingTech
import com.sidav.moa.game.tech.items.ReducedIndustrialWasteTech
import com.sidav.moa.game.tech.items.SoilEnrichmentTech
import com.sidav.moa.game.tech.items.BaseTechItem
import com.sidav.moa.game.tech.items.ship_parts_tech.BattleComputerTech
import com.sidav.moa.game.tech.items.ship_parts_tech.EcmJammerTech
import com.sidav.moa.game.tech.items.ship_parts_tech.ShipArmorTech
import com.sidav.moa.game.tech.items.ship_parts_tech.ShipBeamWeaponTech
import com.sidav.moa.game.tech.items.ship_parts_tech.ShipDeflectorShieldTech
import com.sidav.moa.game.tech.items.ship_parts_tech.ShipEnginesTech

val AllTechs: Map<TechField, List<BaseTechItem>> = mapOf(
    TechField.COMPUTERS to listOf(
//        TECH_COMP_NONE,
//        TECH_COMP_BATTLE_SCANNER, /*1*/
        EcmJammerTech(2, 1),
//        TECH_COMP_UNUSED_2, /*3*/
//        TECH_COMP_DEEP_SPACE_SCANNER, /*4*/
        BattleComputerTech(5, 2),
//        TECH_COMP_UNUSED_5, /*6*/
        EcmJammerTech(7, 2),
        ImprovedRoboticControlsTech(8, 3),
//        TECH_COMP_UNUSED_8, /*9*/
        BattleComputerTech(10, 3),
//        TECH_COMP_UNUSED_10, /*11*/
        EcmJammerTech(12, 3),
//        TECH_COMP_IMPROVED_SPACE_SCANNER, /*13*/
//        TECH_COMP_UNUSED_13, /*14*/
        BattleComputerTech(15, 4),
//        TECH_COMP_UNUSED_15, /*16*/
        EcmJammerTech(17, 4),
        ImprovedRoboticControlsTech(18, 4),
//        TECH_COMP_UNUSED_18, /*19*/
        BattleComputerTech(20, 5),
//        TECH_COMP_UNUSED_20, /*21*/
        EcmJammerTech(22, 5),
//        TECH_COMP_ADVANCED_SPACE_SCANNER, /*23*/
//        TECH_COMP_UNUSED_23, /*24*/
        BattleComputerTech(25, 6),
//        TECH_COMP_UNUSED_25, /*26*/
        EcmJammerTech(27, 6),
        ImprovedRoboticControlsTech(28, 5),
//        TECH_COMP_UNUSED_28, /*29*/
        BattleComputerTech(30, 7),
//        TECH_COMP_UNUSED_30, /*31*/
        EcmJammerTech(32, 7),
//        TECH_COMP_UNUSED_32, /*33*/
//        TECH_COMP_HYPERSPACE_COMMUNICATIONS, /*34*/
        BattleComputerTech(35, 8),
//        TECH_COMP_UNUSED_35, /*36*/
        EcmJammerTech(37, 8),
        ImprovedRoboticControlsTech(38, 6),
//        TECH_COMP_UNUSED_38, /*39*/
        BattleComputerTech(40, 9),
//        TECH_COMP_UNUSED_40, /*41*/
        EcmJammerTech(42, 9),
//        TECH_COMP_UNUSED_42, /*43*/
//        TECH_COMP_UNUSED_43, /*44*/
        BattleComputerTech(45, 10),
//        TECH_COMP_ORACLE_INTERFACE, /*46*/
        EcmJammerTech(47, 10),
        ImprovedRoboticControlsTech(48, 7),
//        TECH_COMP_TECHNOLOGY_NULLIFIER, /*49*/
        BattleComputerTech(50, 11),
    ),
    TechField.CONSTRUCTION to listOf(
//       TECH_CONS_NONE = 0,
//        TECH_CONS_RESERVE_FUEL_TANKS, /*1*/
//        TECH_CONS_UNUSED_1, /*2*/
        ImprovedIndustrialTech(3, 9),
//        TECH_CONS_UNUSED_3, /*4*/
        ReducedIndustrialWasteTech(5, 80),
//        TECH_CONS_UNUSED_5, /*6*/
//        TECH_CONS_UNUSED_6, /*7*/
        ImprovedIndustrialTech(8, 8),
//        TECH_CONS_UNUSED_8, /*9*/
        ShipArmorTech(10, 1), // TECH_CONS_DURALLOY_ARMOR, /*10*/
//        TECH_CONS_BATTLE_SUITS, /*11*/
//        TECH_CONS_UNUSED_11, /*12*/
        ImprovedIndustrialTech(13, 7),
//        TECH_CONS_AUTOMATED_REPAIR_SYSTEM, /*14*/
        ReducedIndustrialWasteTech(15, 60),
//        TECH_CONS_UNUSED_15, /*16*/
        ShipArmorTech(17, 2), // TECH_CONS_ZORTIUM_ARMOR, /*17*/
        ImprovedIndustrialTech(18, 6),
//        TECH_CONS_UNUSED_18, /*19*/
//        TECH_CONS_UNUSED_19, /*20*/
//        TECH_CONS_UNUSED_20, /*21*/
//        TECH_CONS_UNUSED_21, /*22*/
        ImprovedIndustrialTech(23, 5),
//        TECH_CONS_ARMORED_EXOSKELETON, /*24*/
        ReducedIndustrialWasteTech(25, 40),
        ShipArmorTech(26, 3), // TECH_CONS_ANDRIUM_ARMOR, /*26*/
//        TECH_CONS_UNUSED_26, /*27*/
        ImprovedIndustrialTech(28, 4),
//        TECH_CONS_UNUSED_28, /*29*/
//        TECH_CONS_UNUSED_29, /*30*/
//        TECH_CONS_UNUSED_30, /*31*/
//        TECH_CONS_UNUSED_31, /*32*/
        ImprovedIndustrialTech(33, 3),
        ShipArmorTech(34, 4), // TECH_CONS_TRITANIUM_ARMOR, /*34*/
        ReducedIndustrialWasteTech(35, 20),
//        TECH_CONS_ADVANCED_DAMAGE_CONTROL, /*36*/
//        TECH_CONS_UNUSED_36, /*37*/
        ImprovedIndustrialTech(38, 2),
//        TECH_CONS_UNUSED_38, /*39*/
//        TECH_CONS_POWERED_ARMOR, /*40*/
//        TECH_CONS_UNUSED_40, /*41*/
        ShipArmorTech(42, 5),// TECH_CONS_ADAMANTIUM_ARMOR, /*42*/
//        TECH_CONS_UNUSED_42, /*43*/
//        TECH_CONS_UNUSED_43, /*44*/
        ReducedIndustrialWasteTech(45, 0),
//        TECH_CONS_UNUSED_45, /*46*/
//        TECH_CONS_UNUSED_46, /*47*/
//        TECH_CONS_UNUSED_47, /*48*/
//        TECH_CONS_UNUSED_48, /*49*/
        ShipArmorTech(50, 6)//        TECH_CONS_NEUTRONIUM_ARMOR /*50*/
    ),
    TechField.FORCE_FIELDS to listOf(
//        TECH_FFLD_NONE = 0,
        ShipDeflectorShieldTech(1, 1),
//        TECH_FFLD_UNUSED_1, /*2*/
//        TECH_FFLD_UNUSED_2, /*3*/
        ShipDeflectorShieldTech(4, 2),
//        TECH_FFLD_UNUSED_4, /*5*/
//        TECH_FFLD_UNUSED_5, /*6*/
//        TECH_FFLD_UNUSED_6, /*7*/
//        TECH_FFLD_PERSONAL_DEFLECTOR_SHIELD, /*8*/
//        TECH_FFLD_UNUSED_8, /*9*/
        ShipDeflectorShieldTech(10, 3),
//        TECH_FFLD_UNUSED_10, /*11*/
//        TECH_FFLD_CLASS_V_PLANETARY_SHIELD, /*12*/
//        TECH_FFLD_UNUSED_12, /*13*/
        ShipDeflectorShieldTech(14, 4),
//        TECH_FFLD_UNUSED_14, /*15*/
//        TECH_FFLD_REPULSOR_BEAM, /*16*/
//        TECH_FFLD_UNUSED_16, /*17*/
//        TECH_FFLD_UNUSED_17, /*18*/
//        TECH_FFLD_UNUSED_18, /*19*/
        ShipDeflectorShieldTech(20, 5),
//        TECH_FFLD_PERSONAL_ABSORPTION_SHIELD, /*21*/
//        TECH_FFLD_CLASS_X_PLANETARY_SHIELD, /*22*/
//        TECH_FFLD_UNUSED_22, /*23*/
        ShipDeflectorShieldTech(24, 6),
//        TECH_FFLD_UNUSED_24, /*25*/
//        TECH_FFLD_UNUSED_25, /*26*/
//        TECH_FFLD_CLOAKING_DEVICE, /*27*/
//        TECH_FFLD_UNUSED_27, /*28*/
//        TECH_FFLD_UNUSED_28, /*29*/
        ShipDeflectorShieldTech(30, 7),
//        TECH_FFLD_ZYRO_SHIELD, /*31*/
//        TECH_FFLD_CLASS_XV_PLANETARY_SHIELD, /*32*/
//        TECH_FFLD_UNUSED_32, /*33*/
        ShipDeflectorShieldTech(34, 9),
//        TECH_FFLD_UNUSED_34, /*35*/
//        TECH_FFLD_UNUSED_35, /*36*/
//        TECH_FFLD_STASIS_FIELD, /*37*/
//        TECH_FFLD_PERSONAL_BARRIER_SHIELD, /*38*/
//        TECH_FFLD_UNUSED_38, /*39*/
        ShipDeflectorShieldTech(40, 11),
//        TECH_FFLD_UNUSED_40, /*41*/
//        TECH_FFLD_CLASS_XX_PLANETARY_SHIELD, /*42*/
//        TECH_FFLD_BLACK_HOLE_GENERATOR, /*43*/
        ShipDeflectorShieldTech(44, 13),
//        TECH_FFLD_UNUSED_44, /*45*/
//        TECH_FFLD_LIGHTNING_SHIELD, /*46*/
//        TECH_FFLD_UNUSED_46, /*47*/
//        TECH_FFLD_UNUSED_47, /*48*/
//        TECH_FFLD_UNUSED_48, /*49*/
        ShipDeflectorShieldTech(50, 15),
    ),
    TechField.PLANETOLOGY to listOf(
//        TECH_PLAN_NONE = 0,
        EcoRestorationTech(1, 2),
        ImprovedTerraformingTech(2, 10, 5),
        ControlledEnvironmentTech(3, PlanetType.BARREN),
//        TECH_PLAN_UNUSED_3, /*4*/
        EcoRestorationTech(5, 3),
        ControlledEnvironmentTech(6, PlanetType.TUNDRA),
//        TECH_PLAN_UNUSED_6, /*7*/
        ImprovedTerraformingTech(8, 20, 5),
        ControlledEnvironmentTech(9, PlanetType.DEAD),
//        TECH_PLAN_DEATH_SPORES, /*10*/
//        TECH_PLAN_UNUSED_10, /*11*/
        ControlledEnvironmentTech(12, PlanetType.INFERNO),
        EcoRestorationTech(13, 5),
        ImprovedTerraformingTech(14, 30, 4),
        ControlledEnvironmentTech(15, PlanetType.TOXIC),
        SoilEnrichmentTech(16, PlanetGrowth.FERTILE),
//        TECH_PLAN_BIO_TOXIN_ANTIDOTE, /*17*/
        ControlledEnvironmentTech(18, PlanetType.RADIATED),
//        TECH_PLAN_UNUSED_18, /*19*/
        ImprovedTerraformingTech(20, 40, 4),
        CloningTech(21, 10),
//        TECH_PLAN_ATMOSPHERIC_TERRAFORMING, /*22*/
//        TECH_PLAN_UNUSED_22, /*23*/
        EcoRestorationTech(24, 10),
//        TECH_PLAN_UNUSED_24, /*25*/
        ImprovedTerraformingTech(26, 50, 3),
//        TECH_PLAN_DOOM_VIRUS, /*27*/
//        TECH_PLAN_UNUSED_27, /*28*/
//        TECH_PLAN_UNUSED_28, /*29*/
        SoilEnrichmentTech(30, PlanetGrowth.GAIA),
//        TECH_PLAN_UNUSED_30, /*31*/
        ImprovedTerraformingTech(32, 60, 3),
//        TECH_PLAN_UNUSED_32, /*33*/
        EcoRestorationTech(34, 20),
//        TECH_PLAN_UNUSED_34, /*35*/
//        TECH_PLAN_UNIVERSAL_ANTIDOTE, /*36*/
//        TECH_PLAN_UNUSED_36, /*37*/
        ImprovedTerraformingTech(38, 80, 2),
//        TECH_PLAN_UNUSED_38, /*39*/
//        TECH_PLAN_BIO_TERMINATOR, /*40*/
//        TECH_PLAN_UNUSED_40, /*41*/
        CloningTech(42, 5),
//        TECH_PLAN_UNUSED_42, /*43*/
        ImprovedTerraformingTech(44, 100, 2),
//        TECH_PLAN_UNUSED_44, /*45*/
//        TECH_PLAN_UNUSED_45, /*46*/
//        TECH_PLAN_UNUSED_46, /*47*/
//        TECH_PLAN_UNUSED_47, /*48*/
//        TECH_PLAN_UNUSED_48, /*49*/
        ImprovedTerraformingTech(50, 120, 2),
    ),
    TechField.PROPULSION to listOf(
//        TECH_PROP_NONE = 0,
        ShipEnginesTech(1, 1), //        TECH_PROP_RETRO_ENGINES, /*1*/
//        TECH_PROP_UNUSED_1, /*2*/
//        TECH_PROP_HYDROGEN_FUEL_CELLS, /*3*/
//        TECH_PROP_UNUSED_3, /*4*/
//        TECH_PROP_DEUTERIUM_FUEL_CELLS, /*5*/
        ShipEnginesTech(6, 2),//        TECH_PROP_NUCLEAR_ENGINES, /*6*/
//        TECH_PROP_UNUSED_6, /*7*/
//        TECH_PROP_UNUSED_7, /*8*/
//        TECH_PROP_IRRIDIUM_FUEL_CELLS, /*9*/
//        TECH_PROP_INERTIAL_STABILIZER, /*10*/
//        TECH_PROP_UNUSED_10, /*11*/
        ShipEnginesTech(12, 3),//        TECH_PROP_SUB_LIGHT_DRIVES, /*12*/
//        TECH_PROP_UNUSED_12, /*13*/
//        TECH_PROP_DOTOMITE_CRYSTALS, /*14*/
//        TECH_PROP_UNUSED_14, /*15*/
//        TECH_PROP_ENERGY_PULSAR, /*16*/
//        TECH_PROP_UNUSED_16, /*17*/
        ShipEnginesTech(18, 4),//        TECH_PROP_FUSION_DRIVES, /*18*/
//        TECH_PROP_URIDIUM_FUEL_CELLS, /*19*/
//        TECH_PROP_WARP_DISSIPATOR, /*20*/
//        TECH_PROP_UNUSED_20, /*21*/
//        TECH_PROP_UNUSED_21, /*22*/
//        TECH_PROP_REAJAX_II_FUEL_CELLS, /*23*/
        ShipEnginesTech(24, 5),//        TECH_PROP_IMPULSE_DRIVES, /*24*/
//        TECH_PROP_UNUSED_24, /*25*/
//        TECH_PROP_UNUSED_25, /*26*/
//        TECH_PROP_INTERGALACTIC_STAR_GATES, /*27*/
//        TECH_PROP_UNUSED_27, /*28*/
//        TECH_PROP_TRILITHIUM_CRYSTALS, /*29*/
        ShipEnginesTech(30, 6),//        TECH_PROP_ION_DRIVES, /*30*/
//        TECH_PROP_UNUSED_30, /*31*/
//        TECH_PROP_UNUSED_31, /*32*/
//        TECH_PROP_UNUSED_32, /*33*/
//        TECH_PROP_HIGH_ENERGY_FOCUS, /*34*/
//        TECH_PROP_UNUSED_34, /*35*/
        ShipEnginesTech(36, 7),//        TECH_PROP_ANTI_MATTER_DRIVES, /*36*/
//        TECH_PROP_UNUSED_36, /*37*/
//        TECH_PROP_SUB_SPACE_TELEPORTER, /*38*/
//        TECH_PROP_UNUSED_38, /*39*/
//        TECH_PROP_IONIC_PULSAR, /*40*/
//        TECH_PROP_THORIUM_CELLS, /*41*/
        ShipEnginesTech(42, 8),//        TECH_PROP_INTER_PHASED_DRIVES, /*42*/
//        TECH_PROP_SUB_SPACE_INTERDICTOR, /*43*/
//        TECH_PROP_UNUSED_43, /*44*/
//        TECH_PROP_COMBAT_TRANSPORTERS, /*45*/
//        TECH_PROP_INERTIAL_NULLIFIER, /*46*/
//        TECH_PROP_UNUSED_46, /*47*/
        ShipEnginesTech(48, 9),//        TECH_PROP_HYPER_DRIVES, /*48*/
//        TECH_PROP_UNUSED_48, /*49*/
//        TECH_PROP_DISPLACEMENT_DEVICE /*50*/
    ),
    TechField.WEAPONS to listOf(
//        TECH_WEAP_NONE = 0,
        ShipBeamWeaponTech(1, 0), //        TECH_WEAP_LASERS, /*1*/
        ShipBeamWeaponTech(1, 1), // Heavy laser, starting tech as well
//        TECH_WEAP_HAND_LASERS, /*2*/
//        TECH_WEAP_UNUSED_2, /*3*/
//        TECH_WEAP_HYPER_V_ROCKETS, /*4*/
        ShipBeamWeaponTech(5, 1), //        TECH_WEAP_GATLING_LASER, /*5*/
//        TECH_WEAP_ANTI_MISSILE_ROCKETS, /*6*/
//        TECH_WEAP_NEUTRON_PELLET_GUN, /*7*/
//        TECH_WEAP_HYPER_X_ROCKETS, /*8*/
//        TECH_WEAP_FUSION_BOMB, /*9*/
//        TECH_WEAP_ION_CANNON, /*10*/
//        TECH_WEAP_SCATTER_PACK_V_ROCKETS, /*11*/
//        TECH_WEAP_ION_RIFLE, /*12*/
//        TECH_WEAP_MASS_DRIVER, /*13*/
//        TECH_WEAP_MERCULITE_MISSILES, /*14*/
//        TECH_WEAP_NEUTRON_BLASTER, /*15*/
//        TECH_WEAP_ANTI_MATTER_BOMB, /*16*/
//        TECH_WEAP_GRAVITON_BEAM, /*17*/
//        TECH_WEAP_STINGER_MISSILES, /*18*/
//        TECH_WEAP_HARD_BEAM, /*19*/
//        TECH_WEAP_FUSION_BEAM, /*20*/
//        TECH_WEAP_ION_STREAM_PROJECTOR, /*21*/
//        TECH_WEAP_OMEGA_V_BOMB, /*22*/
//        TECH_WEAP_ANTI_MATTER_TORPEDOES, /*23*/
//        TECH_WEAP_FUSION_RIFLE, /*24*/
//        TECH_WEAP_MEGABOLT_CANNON, /*25*/
//        TECH_WEAP_PHASOR, /*26*/
//        TECH_WEAP_SCATTER_PACK_VII_MISSILES, /*27*/
//        TECH_WEAP_AUTO_BLASTER, /*28*/
//        TECH_WEAP_PULSON_MISSILES, /*29*/
//        TECH_WEAP_TACHYON_BEAM, /*30*/
//        TECH_WEAP_HAND_PHASOR, /*31*/
//        TECH_WEAP_GAUSS_AUTOCANNON, /*32*/
//        TECH_WEAP_PARTICLE_BEAM, /*33*/
//        TECH_WEAP_HERCULAR_MISSILES, /*34*/
//        TECH_WEAP_PLASMA_CANNON, /*35*/
//        TECH_WEAP_DEATH_RAY, /*36*/
//        TECH_WEAP_DISRUPTOR, /*37*/
//        TECH_WEAP_PULSE_PHASOR, /*38*/
//        TECH_WEAP_NEUTRONIUM_BOMB, /*39*/
//        TECH_WEAP_HELLFIRE_TORPEDOES, /*40*/
//        TECH_WEAP_ZEON_MISSILES, /*41*/
//        TECH_WEAP_PLASMA_RIFLE, /*42*/
//        TECH_WEAP_PROTON_TORPEDOES, /*43*/
//        TECH_WEAP_SCATTER_PACK_X_MISSILES, /*44*/
//        TECH_WEAP_TRI_FOCUS_PLASMA_CANNON, /*45*/
//        TECH_WEAP_STELLAR_CONVERTER, /*46*/
//        TECH_WEAP_NEUTRON_STREAM_PROJECTOR, /*47*/
//        TECH_WEAP_MAULER_DEVICE, /*48*/
//        TECH_WEAP_UNUSED_48, /*49*/
//        TECH_WEAP_PLASMA_TORPEDOES /*50*/
    )
)
