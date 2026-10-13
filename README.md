# SkCoAPI

## Overview

SkCoAPI is an adapter of CoreProtect API (v9 or later) for Skript.

Refer [CoreProtect API Documentation](https://docs.coreprotect.net/api/) for information of each Java method.

## Minimum Supported Versions

Since 0.3.1:

* Java: 25
* Paper: 26.2
* Skript: 2.16.2
* CoreProtect: 23.2

Versions up to 0.3.0 target Java 11, Spigot 1.15, Skript 2.6.4 and CoreProtect 22.1.

## Building

```
./gradlew build
```

The jar is written to `build/libs/SkCoAPI-<version>.jar`.
A JDK 25 is downloaded automatically by Gradle if none is installed.

## Changes in 0.3.1

* Built against Paper 26.2, Skript 2.16.2 and CoreProtect 23.2 (API v11) for Java 25.
* Registers syntax through the addon API of Skript 2.14+ (`SkriptAddon`, `SyntaxRegistry`, `EventValueRegistry`)
  instead of the removed / deprecated `Skript.register*` and `EventValues` methods.
  0.3.0 failed on Skript 2.16 because `EventValues.registerEventValue(..., Getter, int)` no longer exists.
* Lookup / rollback / restore sections evaluate their arguments on the main thread and only query CoreProtect
  asynchronously. The section body now also runs when the section is the last statement of a trigger,
  and the trigger continues even if CoreProtect throws.
* `on CoreProtect logging action by "<user>"` now filters by the user (previously ignored).
* `logging didn't succeed` now parses (the pattern had a typo).
* Literals in `on` / `except on` (e.g. `on stone`, `except on dirt and grass block`, `on campfire[lit=false]`)
  are parsed as item types, block data or entity types. Previously they threw "UnparsedLiterals must be converted
  before use" at runtime. Unknown literals are now a parse error.
* If `for`, `on`, `with action` or `in radius` is given but evaluates to nothing (e.g. an unset variable),
  the lookup / rollback / restore does nothing and returns no results.
  CoreProtect treats an empty filter as no filter, so previously e.g. `rollback ... for {_unset}` rolled back every user.
* Item types in `on` / `except on` pass every material they represent instead of a random one.
* Entity types in `on` / `except on` resolve to the closest Bukkit entity type, and entities resolve to their type.
  Write entity types as `a zombie` or `zombies`: a bare `zombie` means "the zombie" of the event in Skript.
* If CoreProtect is installed but failed to start (e.g. it does not support the Minecraft version),
  SkCoAPI logs a clear message instead of a `NoClassDefFoundError`.
* Null values (e.g. an unset variable) make logging effects fail instead of throwing.
* `block type` / `CoreProtect block data` of logs without a block (e.g. session logs) are empty instead of throwing.

## Usage

Documentation will be available soon.

## Examples

* Get the last 24 hours of block data for endermans.
```skript
lookup action logs in 24 hours for "##enderman":
    set {_lookup::*} to results
send {_lookup::*}
```

---

* Get the last 24 hours of block data for endermans excluding dirt and grass blocks.
```skript
set {_exclude::*} to grass and dirt
lookup action logs in 24 hours for "##enderman" except on {_exclude::*}:
    set {_lookup::*} to results        
```

---

* Add "'s hand" to usernames when logging.
```skript
on CoreProtect logging:
    set user name to "%user name%'s hand"
```

---

* When a player executes the command `/lookup`,
  the player sees the last 30 days of block data within 5 blocks of their location.
```skript
command /lookup:
executable by: players
trigger:
    lookup logs in a month in radius 5 at location of player:
        set {_lookup::*} to results
    send "Lookup results:" to player
    send {_lookup::*} to player
```

---

* When a player executes the command `/inspect`,
  the player sees the 30 days of block data on the block they are looking at.
```skript
command /inspect:
    executable by: players
    trigger:
        set {_logs::*} to lookup logs on target block of player in a month
        loop {_logs::*}:
            set {_log} to loop-value
            set {_user} to player name of {_log}
            set {_action_id} to action id of {_log}
            if {_action_id} is 0:
                set {_action} to "broke"
            else if {_action_id} is 1:
                set {_action} to "placed"
            else if {_action_id} is 2:
                set {_action} to "interacted with"
            set {_type} to block type of {_log}
            set {_age} to difference between now and date of {_log}
            if {_log} is rolled back:
                send "&m%{_user}% %{_action}% %{_type}% %{_age}% ago" to player
            else:
                send "%{_user}% %{_action}% %{_type}% %{_age}% ago" to player
```

---

* When executing the command `/sessions`, get the last 1-day session data for the specified user.
```skript
command /sessions <text>:
    trigger:
        set {_lookup::*} to lookup sessions for arg-1 in a day
        send {_lookup::*} to sender
```

---

* When a player executes the command `/extinguishedcampfire`,
  place a block with a block data `minecraft:campfire[lit=false]` to the location of the player,
  and log the placement of the block.
```skript
command /extinguishedcampfire:
    executable by: players
    trigger:
        set {_block} to campfire[lit=false]
        set block at player to {_block}
        log that player's name placed {_block} at location of player
```

---

* When a player executes the command `/takeall`,
  move items from a container block to the inventory of the player, and log that they removed items from the container.
```skript
command /takeall:
    executable by: players
    trigger:
        set {_block} to target block of player
        send "&aTaking items from %{_block}%" to player
        loop all items in the inventory of {_block}:
            if player cannot hold loop-item:
                send "&cStopped taking items because your inventory is full!" to player
                stop
            give loop-item to player
            remove loop-item from the inventory of {_block}
            log that player's name made a transaction to a container at location of {_block}
```

---

* A function which returns true if the user has already placed the block within the specified time.
```skript
function has_placed(p: string, b: block, t: timespan) :: boolean:
    if {_p} has placed {_b} in {_t}:
        return true
    else:
        set {_lookup::*} to lookup queue on {_b}
        loop {_lookup::*}:
            set {_action} to action id of loop-value
            if {_action} is 1:
                set {_name} to player name of loop-value
                if {_name} is {_p}:
                    return true
    return false
```

---

## License

The source files are distributed under Apache 2.0.
Detail information is in [`LICENSE`](/LICENSE).
