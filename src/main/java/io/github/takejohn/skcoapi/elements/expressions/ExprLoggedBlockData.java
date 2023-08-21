package io.github.takejohn.skcoapi.elements.expressions;

import net.coreprotect.CoreProtectAPI;
import org.bukkit.block.data.BlockData;
import org.eclipse.jdt.annotation.Nullable;
import org.jetbrains.annotations.NotNull;

public class ExprLoggedBlockData extends LogPropertyExpression<BlockData> {

    private static final String PROPERTY_NAME = "logged block data[s]";

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
