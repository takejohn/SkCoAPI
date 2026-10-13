package io.github.takejohn.skcoapi.util;

import ch.njol.skript.bukkitutil.EntityUtils;
import ch.njol.skript.entity.EntityData;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

public final class EntityTypes {

    @Contract("-> fail")
    private EntityTypes() {
        throw new AssertionError(getClass().getName() + " is a util class");
    }

    @Contract("null -> null")
    public static @Nullable EntityType fromEntityData(@Nullable EntityData<?> data) {
        if (data == null) {
            return null;
        }
        // Skript picks the closest entity type instead of the first assignable one (e.g. HUSK rather than ZOMBIE).
        return EntityUtils.toBukkitEntityType(data);
    }

    @Contract("null -> null")
    public static @Nullable EntityType fromSkriptEntityType(@Nullable ch.njol.skript.entity.EntityType src) {
        if (src == null) {
            return null;
        }
        return fromEntityData(src.data);
    }

}
