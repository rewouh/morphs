package me.rewu.morphs.Utils;

import me.rewu.morphs.Morphs.Morph;
import me.rewu.morphs.Morphs.MorphType;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;

public class TextUtils {

    public static String Nameify(MorphType type, boolean noSpaces) {

        String uReplacer = noSpaces ? "" : " ";

        String base = type.name();
        String name = Character.toUpperCase(base.charAt(0)) + "";

        for (int i=1; i<base.length(); i++) {
            char c = base.charAt(i);

            if (c == '_') {
                name += uReplacer + Character.toUpperCase(base.charAt(i+1));

                i++;
            } else
                name += Character.toLowerCase(c);
        }

        return name;
    }

    public static String Nameify(MorphType type) {
        return Nameify(type, false);
    }

    public static String Nameify(Morph morph) {

        if (morph == null)
            return "NONE";

        return morph.getColor() + Nameify(morph.getType(), false) + ChatColor.RESET;
    }
}
