package io.github.takejohn.skcoapi.elements.expressions;

import ch.njol.skript.aliases.ItemType;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.RequiredPlugins;
import ch.njol.skript.doc.Since;
import io.github.takejohn.skcoapi.elements.types.CoreProtectLogs;
import net.coreprotect.CoreProtectAPI;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.skriptlang.skript.registration.SyntaxRegistry;

@Name("Block Type")
@Description("The Material of the block.")
@Since("0.1.0")
@RequiredPlugins("CoreProtect")
public class ExprBlockType extends LogPropertyExpression<ItemType> {

    private static final String PROPERTY_NAME = "[CoreProtect] block type[s]";

    public static void register(@NotNull SyntaxRegistry registry) {
        registerProperty(registry, ExprBlockType.class, ItemType.class, PROPERTY_NAME, ExprBlockType::new);
    }

    @Override
    protected @NotNull String getPropertyName() {
        return PROPERTY_NAME;
    }

    @Override
    public @Nullable ItemType convert(CoreProtectAPI.ParseResult parseResult) {
        final @Nullable Material type = CoreProtectLogs.getTypeOrNull(parseResult);
        return type != null ? new ItemType(type) : null;
    }

    @Override
    public @NotNull Class<? extends ItemType> getReturnType() {
        return ItemType.class;
    }

}
