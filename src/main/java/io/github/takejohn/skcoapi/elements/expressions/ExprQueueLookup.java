package io.github.takejohn.skcoapi.elements.expressions;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.RequiredPlugins;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import io.github.takejohn.skcoapi.SkCoAPI;
import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Name("Lookup Queue")
@Description("Searches the consumer queue for changes on a block not yet saved in the database.")
@Since("0.1.0")
@RequiredPlugins("CoreProtect")
public class ExprQueueLookup extends PerformExpression {

    public static final String PATTERN = "lookup[ consumer] queue on %block%";

    private Expression<Block> block;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean isDelayed,
                        SkriptParser.@NotNull ParseResult parseResult) {
        block = (Expression<Block>)exprs[0];
        return true;
    }

    @Override
    public @NotNull String toString(@Nullable Event e, boolean debug) {
        return "lookup consumer queue on " + block.toString(e, debug);
    }

    @Override
    protected @Nullable List<String[]> perform(@NotNull Event e) {
        return SkCoAPI.coreProtectAPI.queueLookup(block.getSingle(e));
    }

    public static void register() {
        register(ExprQueueLookup.class, PATTERN);
    }

}
