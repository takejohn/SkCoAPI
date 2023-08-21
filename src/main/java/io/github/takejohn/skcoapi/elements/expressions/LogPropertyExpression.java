package io.github.takejohn.skcoapi.elements.expressions;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import io.github.takejohn.skcoapi.elements.types.CoreProtectLogs;
import net.coreprotect.CoreProtectAPI;

public abstract class LogPropertyExpression<T> extends SimplePropertyExpression<CoreProtectAPI.ParseResult, T> {

    protected static <T> void register(Class<? extends LogPropertyExpression<T>> c, Class<T> type, String property) {
        register(c, type, property, CoreProtectLogs.CODE_NAME);
    }

}
