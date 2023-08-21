package io.github.takejohn.skcoapi.elements.types;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.expressions.base.EventValueExpression;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.util.Date;
import net.coreprotect.CoreProtectAPI;
import org.bukkit.block.data.BlockData;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class CoreProtectLogs {

    public static final String CODE_NAME = "coreprotectlog";

    @Contract("-> fail")
    private CoreProtectLogs() {
        throw new AssertionError(getClass().getName() + " is a util class");
    }

    public static ClassInfo<CoreProtectAPI.ParseResult> classInfo() {
        return new ClassInfo<>(CoreProtectAPI.ParseResult.class, CODE_NAME)
                .user("[Cc]ore[Pp]rotect ?logs?")
                .name("CoreProtect Log")
                .description("A result of lookup on CoreProtect logs.")
                .examples(
                        "function log_to_text(log: coreprotectlog) :: text:",
                        "    return \"%user name of {_log}% acted to %action of {_log}% a block at %date of {_log}%\""
                )
                .defaultExpression(new EventValueExpression<>(CoreProtectAPI.ParseResult.class))
                .parser(new Parser<>() {

                    @Override
                    public boolean canParse(@NotNull ParseContext context) {
                                                                     return false;
                                                                                  }

                    @Override
                    public @NotNull String toVariableNameString(CoreProtectAPI.ParseResult o) {
                        final BlockData blockData = o.getBlockData();
                        return o.getPlayer() + ": " + o.getActionString() +
                                (blockData != null ? ": " + blockData.getAsString() : "") +
                                " (x" + o.getX() + "/y" + o.getY() + "/z" + o.getZ() + "/" + o.worldName() + ") at " +
                                new Date(o.getTimestamp()) + (o.isRolledBack() ? " [rolled back]" : "");
                    }

                    @Override
                    public @NotNull String toString(CoreProtectAPI.ParseResult o, int flags) {
                        return toVariableNameString(o);
                    }

                });
    }

}
