package io.github.takejohn.skcoapi.util;

import ch.njol.skript.entity.EntityData;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

public final class EntityTypes {

    private EntityTypes() {
        throw new AssertionError(getClass().getName() + " is a util class");
    }

    @Contract("null -> null")
    public static @Nullable EntityType fromEntityClass(@Nullable Class<? extends Entity>c) {
        if (c == null) {
            return null;
        }
        for (EntityType entityType : EntityType.values()) {
            final Class<? extends Entity> entityTypeClass = entityType.getEntityClass();
            if (entityTypeClass != null && entityTypeClass.isAssignableFrom(c)) {
                return entityType;
            }
        }
        return null;
    }


    @Contract("null -> null")
    public static @Nullable EntityType fromEntityData(@Nullable EntityData<?> data) {
        if (data == null) {
            return null;
        }
        return fromEntityClass(data.getType());
    }

    @Contract("null -> null")
    public static @Nullable EntityType fromSkriptEntityType(@Nullable ch.njol.skript.entity.EntityType src) {
        if (src == null) {
            return null;
        }
        return fromEntityData(src.data);
    }

}
