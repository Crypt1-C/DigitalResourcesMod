package net.cryptic.digital_resources.api;

import net.minecraft.util.StringRepresentable;

import javax.annotation.Nonnull;

public enum UpgradeTypes implements StringRepresentable {
    EMPTY("empty"),
    SPEED("speed"),
    STACK("stack");

    private final String type;

    UpgradeTypes(String pType) {
        this.type = pType;
    }

    @Override
    @Nonnull
    public String getSerializedName() {
        return type;
    }
}
