package net.cryptic.digital_resources.api;

import net.minecraft.util.StringRepresentable;

import javax.annotation.Nonnull;

public enum Resources implements StringRepresentable {

    EMPTY("empty"),
    COAL("coal"),
    COPPER("copper"),
    IRON("iron"),
    GOLD("gold"),
    DIAMOND("diamond"),
    EMERALD("emerald"),
    NETHERITE("netherite"),
    AMETHYST("amethyst"),
    GLOWSTONE("glowstone"),
    QUARTZ("quartz"),
    LAPIS("lapis"),
    ZINC("zinc"),
    OSMIUM("osmium"),
    ALUMINUM("aluminum"),
    BRASS("brass"),
    COAL_COKE("coal_coke"),
    REFINED_GLOWSTONE("refined_glowstone"),
    REFINED_OBSIDIAN("refined_obsidian"),
    BRONZE("bronze"),
    CONSTANTAN("constantan"),
    ELECTRUM("electrum"),
    ENDERIUM("enderium"),
    INVAR("invar"),
    LEAD("lead"),
    LUMIUM("lumium"),
    NICKEL("nickel"),
    SIGNALUM("signalum"),
    SILVER("silver"),
    TIN("tin"),
    URANIUM("uranium"),
    CLAY("clay"),
    STEEL("steel"),
    APATITE("apatite"),
    NITER("niter"),
    SULFUR("sulfur"),
    CINNABAR("cinnabar"),
    REDSTONE("redstone");

    private final String resource;

    Resources(String pResource) {
        this.resource = pResource;
    }

    @Override
    @Nonnull
    public String getSerializedName() {
        return resource;
    }

}
