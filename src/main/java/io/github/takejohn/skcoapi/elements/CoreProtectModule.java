package io.github.takejohn.skcoapi.elements;

import ch.njol.skript.registrations.Classes;
import io.github.takejohn.skcoapi.elements.conditions.*;
import io.github.takejohn.skcoapi.elements.effects.*;
import io.github.takejohn.skcoapi.elements.events.EvtPreLog;
import io.github.takejohn.skcoapi.elements.expressions.*;
import io.github.takejohn.skcoapi.elements.sections.EffSecLookup;
import io.github.takejohn.skcoapi.elements.sections.EffSecRestore;
import io.github.takejohn.skcoapi.elements.sections.EffSecRollback;
import io.github.takejohn.skcoapi.elements.types.CoreProtectLogs;
import org.jetbrains.annotations.NotNull;
import org.skriptlang.skript.addon.AddonModule;
import org.skriptlang.skript.addon.SkriptAddon;
import org.skriptlang.skript.bukkit.lang.eventvalue.EventValueRegistry;

/**
 * Registers all types and syntax elements of SkCoAPI.
 */
public final class CoreProtectModule implements AddonModule {

    @Override
    public void init(@NotNull SkriptAddon addon) {
        // Types have to be known before any syntax which uses them is registered.
        Classes.registerClass(CoreProtectLogs.classInfo());
    }

    @Override
    public void load(@NotNull SkriptAddon addon) {
        register(addon,
                ExprBlockType::register,
                ExprLoggedBlockData::register,
                ExprPlayerName::register,
                ExprDate::register,
                ExprActionId::register,
                ExprActionString::register,
                ExprAPIVersion::register,
                CondRolledBack::register,
                ExprLoggedWorldName::register,
                ExprBlockLocation::register,
                CondEnabled::register,
                EffTestAPI::register,
                EffSecLookup::register,
                EffSecRollback::register,
                EffSecRestore::register,
                ExprResults::register,
                ExprBlockLookup::register,
                ExprSessionLookup::register,
                ExprQueueLookup::register,
                EffLogChat::register,
                EffLogCommand::register,
                EffLogPlacement::register,
                EffLogRemoval::register,
                EffLogContainerTransaction::register,
                EffLogInteraction::register,
                CondLoggingSucceeded::register,
                CondPlaced::register,
                CondRemoved::register,
                EffPurge::register,
                EvtPreLog::register,
                ExprPreLogUserName::register
        );
        EvtPreLog.registerEventValues(addon.registry(EventValueRegistry.class));
    }

    @Override
    public @NotNull String name() {
        return "coreprotect";
    }

}
