package net.cryptic.digital_resources.api.Classes;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import org.w3c.dom.css.RGBColor;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class Util {
    public static String intToStringTimeFormat(int time) {
        String strTemp;
        int minutes    = time / 60;
        int seconds    = time % 60;

        if(minutes < 10)
            strTemp = "0" + minutes + ":";
        else
            strTemp = minutes + ":";

        if(seconds < 10)
            strTemp = strTemp + "0" + seconds;
        else
            strTemp = strTemp + seconds;

        return strTemp;
    }
}
