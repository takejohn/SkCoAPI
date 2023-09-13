package io.github.takejohn.skcoapi.elements.expressions;

import ch.njol.skript.aliases.ItemType;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.RequiredPlugins;
import ch.njol.skript.doc.Since;
import net.coreprotect.CoreProtectAPI;
import org.eclipse.jdt.annotation.Nullable;
import org.jetbrains.annotations.NotNull;

@Name("Block Type")
@Description("The Material of the block.")
@Since("0.1.0")
@RequiredPlugins("CoreProtect")
public class ExprBlockType extends LogPropertyExpression<ItemType> {

    private static final String PROPERTY_NAME = "block type[s]";

    @Override
    protected @NotNull String getPropertyName() {
        return PROPERTY_NAME;
    }

    @Override
    public @Nullable ItemType convert(CoreProtectAPI.ParseResult parseResult) {
        return new ItemType(parseResult.getType());
    }

    @Override
    public @NotNull Class<? extends ItemType> getReturnType() {
        return ItemType.class;
    }

    public static void register() {
        register(ExprBlockType.class, ItemType.class, PROPERTY_NAME);
    }

}
