package io.github.takejohn.skcoapi.elements.expressions;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.RequiredPlugins;
import ch.njol.skript.doc.Since;
import net.coreprotect.CoreProtectAPI;
import org.bukkit.block.data.BlockData;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;

@Name("Block Data")
@Description("The BlockData of the block.")
@Since("0.1.0")
@RequiredPlugins("CoreProtect")
public class ExprLoggedBlockData extends LogPropertyExpression<BlockData> {

    private static final String PROPERTY_NAME = "CoreProtect block data[s]";

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
        return parseResult.getBlockData();
    }

    public static void register() {
        register(ExprLoggedBlockData.class, BlockData.class, PROPERTY_NAME);
    }

}
