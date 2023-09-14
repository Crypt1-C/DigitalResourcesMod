package net.cryptic.digital_resources.api;

import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.util.StringRepresentable;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public enum Resources implements StringRepresentable {

    BLANK("blank", 0xFFFFFF),
    COAL("coal", 0x242225),
    COPPER("copper", 0xb65d3f),
    IRON("iron", 0x9c9c9c),
    GOLD("gold", 0xdcb343),
    DIAMOND("diamond", 0x55beb5),
    EMERALD("emerald", 0x2da74f),
    NETHERITE("netherite", 0x3f393b),
    AMETHYST("amethyst", 0x9371c0),
    GLOWSTONE("glowstone", 0xb47d44),
    QUARTZ("quartz", 0xbaada1),
    LAPIS("lapis", 0x2a53a8),
    ZINC("zinc", 0x72907f),
    OSMIUM("osmium", 0x9aa8b5),
    ALUMINUM("aluminum", 0xa3a3a3),
    BRASS("brass", 0xb68958),
    COAL_COKE("coal_coke", 0x34373a),
    REFINED_GLOWSTONE("refined_glowstone", 0xdcc37f),
    REFINED_OBSIDIAN("refined_obsidian", 0x6d5887),
    BRONZE("bronze", 0xb36525),
    CONSTANTAN("constantan", 0xb37c3c),
    ELECTRUM("electrum", 0xc3b35f),
    ENDERIUM("enderium", 0x16576c),
    INVAR("invar", 0x869a97),
    LEAD("lead", 0x3a3e63),
    LUMIUM("lumium", 0xe2b773),
    NICKEL("nickel", 0xa28861),
    SIGNALUM("signalum", 0xce3912),
    SILVER("silver", 0x687481),
    TIN("tin", 0x698c97),
    URANIUM("uranium", 0x639333),
    CLAY("clay", 0x747a8b),
    STEEL("steel", 0x586672),
    APATITE("apatite", 0x2f8cad),
    NITER("niter", 0x99898d),
    SULFUR("sulfur", 0xd4c468),
    CINNABAR("cinnabar", 0x9a334b),
    REDSTONE("redstone", 0x7e0801),
    ANDESITE_ALLOY("andesite_alloy", 0x81897f);

    private final String resource;
    private final int color;

    Resources(String pResource, int pColor) {
        this.resource = pResource;
        this.color = pColor;
    }

    public int getColor() {
        return this.color;
    }

    @Override
    @Nonnull
    public String getSerializedName() {
        return resource;
    }

}
