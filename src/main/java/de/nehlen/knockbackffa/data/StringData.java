package de.nehlen.knockbackffa.data;

import de.nehlen.knockbackffa.KnockbackFFA;
import lombok.Getter;

public class StringData {

    @Getter private static String prefix = KnockbackFFA.getKnockbackFFA().getGeneralConfig().getOrSetDefault("config.prefix", "§6KnockKnock §8◆ §7");

    @Getter private static String noPerms = KnockbackFFA.getKnockbackFFA().getGeneralConfig().getOrSetDefault("config.noPerms", "Keine Rechte!");

    @Getter private static String noPerm = KnockbackFFA.getKnockbackFFA().getGeneralConfig().getOrSetDefault("config.noPerms", "Darauf hast du keine Rechte.");

    public static String combinate() { return prefix + noPerm; }
}
