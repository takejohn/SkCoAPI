package io.github.takejohn.skcoapi.elements.expressions;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.RequiredPlugins;
import ch.njol.skript.doc.Since;
import io.github.takejohn.skcoapi.elements.types.CoreProtectLogs;
import net.coreprotect.CoreProtectAPI;
import org.bukkit.block.data.BlockData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.skriptlang.skript.registration.SyntaxRegistry;

@Name("Logged Block Data")
@Description("The BlockData of the block.")
@Since("0.1.0")
@RequiredPlugins("CoreProtect")
public class ExprLoggedBlockData extends LogPropertyExpression<BlockData> {

    private static final String PROPERTY_NAME = "CoreProtect block data[s]";

    public static void register(@NotNull SyntaxRegistry registry) {
        registerProperty(registry, ExprLoggedBlockData.class, BlockData.class, PROPERTY_NAME, ExprLoggedBlockData::new);
    }

    @Override
    protected @NotNull String getPropertyName() {
        return PROPERTY_NAME;
    }

    @Override
    public @NotNull Class<? extends BlockData> getReturnType() {
        return BlockData.class;
    }

    @Override
    public @Nullable BlockData convert(CoreProtectAPI.ParseResult parseResult) {
        return CoreProtectLogs.getBlockDataOrNull(parseResult);
    }

}
