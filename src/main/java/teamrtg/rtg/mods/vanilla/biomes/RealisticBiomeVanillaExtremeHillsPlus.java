package teamrtg.rtg.mods.vanilla.biomes;

import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import teamrtg.rtg.mods.vanilla.surfaces.SurfaceVanillaExtremeHillsPlus;
import teamrtg.rtg.util.noise.CellNoise;
import teamrtg.rtg.util.noise.OpenSimplexNoise;
import teamrtg.rtg.world.biome.surface.SurfaceBase;
import teamrtg.rtg.world.biome.terrain.HeightEffect;
import teamrtg.rtg.world.biome.terrain.JitterEffect;
import teamrtg.rtg.world.biome.terrain.MountainsWithPassesEffect;
import teamrtg.rtg.world.biome.terrain.TerrainBase;
import teamrtg.rtg.world.gen.ChunkProviderRTG;
import teamrtg.rtg.world.gen.deco.*;

public class RealisticBiomeVanillaExtremeHillsPlus extends RealisticBiomeVanillaBase {

    public RealisticBiomeVanillaExtremeHillsPlus(ChunkProviderRTG chunkProvider) {

        super(
                Biomes.EXTREME_HILLS_WITH_TREES,
                Biomes.RIVER,
                chunkProvider
        );
        this.noLakes = true;
        this.noWaterFeatures = true;
    }

    @Override
    protected TerrainBase initTerrain() {
        return new TerrainBase() {
            private float width;
            private float strength;
            private float terrainHeight;
            private float spikeWidth = 30;
            private float spikeHeight = 40;
            private HeightEffect heightEffect;

            {
                width = 150f;
                strength = 120f;
                terrainHeight = 90f;
                MountainsWithPassesEffect mountainEffect = new MountainsWithPassesEffect();
                mountainEffect.mountainHeight = strength;
                mountainEffect.mountainWavelength = width;
                mountainEffect.spikeHeight = this.spikeHeight;
                mountainEffect.spikeWavelength = this.spikeWidth;

                heightEffect = new JitterEffect(7f, 10f, mountainEffect);
                heightEffect = new JitterEffect(3f, 6f, heightEffect);
            }

            @Override
            public float generateNoise(OpenSimplexNoise simplex, CellNoise cell, int x, int y, float border, float river) {
                return riverized(heightEffect.added(simplex, cell, x, y) + terrainHeight, river);
            }
        };
    }

    @Override
    protected SurfaceBase initSurface() {
        return new SurfaceVanillaExtremeHillsPlus(this, false, 0f, 1.5f, 60f, 65f, 1.5f, 0.08f);
    }

    @Override
    protected void initDecos() {
        DecoTree decoTrees = new DecoTree();
        decoTrees.strengthFactorForLoops = 4f;
        decoTrees.strengthNoiseFactorXForLoops = true;
        decoTrees.distribution.noiseDivisor = 100f;
        decoTrees.distribution.noiseFactor = 6f;
        decoTrees.distribution.noiseAddend = 0.8f;
        decoTrees.treeType = DecoTree.TreeType.PINE_EURO;
        decoTrees.treeCondition = DecoTree.TreeCondition.RANDOM_CHANCE;
        decoTrees.treeConditionChance = 24;
        decoTrees.maxY = 100;
        this.addDeco(decoTrees);

        DecoShrub decoShrub = new DecoShrub();
        decoShrub.maxY = 100;
        decoShrub.strengthFactor = 2f;
        this.addDeco(decoShrub);

        DecoFallenTree decoFallenTree = new DecoFallenTree();
        decoFallenTree.distribution.noiseDivisor = 100f;
        decoFallenTree.distribution.noiseFactor = 6f;
        decoFallenTree.distribution.noiseAddend = 0.8f;
        decoFallenTree.logCondition = DecoFallenTree.LogCondition.NOISE_GREATER_AND_RANDOM_CHANCE;
        decoFallenTree.logConditionNoise = 0f;
        decoFallenTree.logConditionChance = 6;
        decoFallenTree.maxY = 100;
        decoFallenTree.logBlock = Blocks.LOG;
        decoFallenTree.logMeta = (byte) 1;
        decoFallenTree.leavesBlock = Blocks.LEAVES;
        decoFallenTree.leavesMeta = (byte) -1;
        decoFallenTree.minSize = 3;
        decoFallenTree.maxSize = 6;
        this.addDeco(decoFallenTree);

        DecoBoulder decoBoulder = new DecoBoulder();
        decoBoulder.boulderBlock = Blocks.MOSSY_COBBLESTONE;
        decoBoulder.chance = 16;
        decoBoulder.maxY = 95;
        decoBoulder.strengthFactor = 3f;
        this.addDeco(decoBoulder);

        DecoPumpkin decoPumpkin = new DecoPumpkin();
        decoPumpkin.maxY = 90;
        decoPumpkin.randomType = teamrtg.rtg.world.gen.deco.DecoPumpkin.RandomType.USE_CHANCE_VALUE;
        decoPumpkin.chance = 28;
        this.addDeco(decoPumpkin);

        DecoFlowersRTG decoFlowersRTG = new DecoFlowersRTG();
        decoFlowersRTG.flowers = new int[] {9, 9, 9, 9, 3, 3, 3, 3, 3, 2, 2, 2, 11, 11, 11};
        decoFlowersRTG.maxY = 128;
        decoFlowersRTG.loops = 3;
        this.addDeco(decoFlowersRTG);

        DecoLargeFernDoubleTallgrass decoDoublePlants = new DecoLargeFernDoubleTallgrass();
        decoDoublePlants.maxY = 128;
        decoDoublePlants.fernChance = 3;
        decoDoublePlants.loops = 15;
        this.addDeco(decoDoublePlants);
    }

    @Override
    protected void initProperties() {
        config.addBlock(config.MIX_BLOCK).setDefault(Blocks.GRAVEL.getDefaultState());
        config.addBlock(config.BEACH_BLOCK).setDefault(Blocks.GRAVEL.getDefaultState());
        config.GENERATE_EMERALDS.setDefault(true);
    }
}
