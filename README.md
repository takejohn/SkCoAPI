# SkCoAPI

## Overview

SkCoAPI is an adapter of CoreProtect API v9 for Skript.

Refer [CoreProtect API v9 Documentation](https://docs.coreprotect.net/api/version/v9/) for information of each Java method.

## Minimum Supported Versions

* Spigot: 1.15 R0.1
* Skript: 2.6.4
* CoreProtect: 22.1

## Usage

### Events

#### On CoreProtect Logging
```skript
[on] CoreProtect log[ging] [(for %strings%|[an] action [by %strings%])]
```
Equivalent: `CoreProtectPreLogEvent`

Fired when a CoreProtect logger is about to log an action.
Cancellable.

---

### Conditions

#### Is Enabled
```skript
[the] CoreProtect API is[(n't| not)] (enabled|disabled)
```
Equivalent: `isEnabled()`

Checks if the server has the CoreProtect API enabled, and false if it does not.

---

#### Is Rolled Back
```skript
%coreprotectlog% is[(n't| not)] rolled back
```
Equivalent: `ParseResult.isRolledBack()`

Checks If the block is currently rolled back or not.

---

#### Has Placed/Built
```skript
%string% has[(n't | not)] (placed|built) %block% in[ last] %timespan% [(until|till) %-timespan%]
```
Equivalent: `hasPlaced()`

Checks if a user has already placed a block at the location within the specified time limit.

---

#### Has Broken/Removed
```skript
%string% has[(n't | not)] (broken|removed) %block% in [last] %timespan% [(until|till) %-timespan%]
```
Equivalent: `hasRemoved()`

Checks if a user has already removed a block at the location within the specified time limit.

---

### Logging Succeeded
```skript
logging (succeeded|did(n't| not) fail|failed|din(n't| not) succeed)
```

Checks if the log said/executed/placed/broke/container transaction/interaction effect lastly ran was successful.

---

### Effects

#### Test CoreProtect API
```skript
test [the] CoreProtect API
```
Equivalent: `testAPI()`

Prints out "[CoreProtect] API Test Successful." in the server console.

---

#### Log Said
```skript
log [that] %string% (said|sent [the] message) %string%
```
Equivalent: `logChat()`

---

#### Log Executed
```skript
log [that] %player% executed [[the] command] %string%
```
Equivalent: `logCommand()`

---

#### Log Placed/Built
```skript
log [that] %string% (placed|built) %block data% at %location%
```
Equivalent: `logPlacement()`

Logs a block as being placed.

---

#### Log Broke/Removed
```skript
log [that] %string% (broke|removed) %block data% at %location%
```
Equivalent: `logRemoval()`

Logs a block as being removed/broken, and log the block's inventory (if applicable).

---

#### Log Container Transaction
```skript
log [that] %string% made [a] transaction [to [a] container] at %location%
```
Equivalent: `logContainerTransaction()`

Logs any transactions made to a block's inventory immediately after calling the method.

---

#### Log Interaction
```skript
log [that] %string% interacted [[a] block] at %location%
```
Equivalent: `logInteraction()`

Logs a block as having been interacted with.

---

### Expressions

#### API Version
```skript
[the] CoreProtect API['s] version
[the] version of CoreProtect API
```
Return Type: Integer

Equivalent: `APIVersion()`

---

#### User/Player Name
```skript
[the] (user|player) name
```
Return Type: Text

Equivalent: `CoreProtectPreLogEvent.getUser()`, `CoreProtectPreLogEvent.setUser()`

Usable only in CoreProtect logging event.
This value can be changed.
This can be referred as `event-string`, which only the getter is provided.

---

#### Lookup
```skript
lookup [action] (data|logs) in [last] %timespan% [for %strings%] [except for %strings%] [on %objects%] [except on %objects%] [with ([an] action|actions) %integers%] [in radius %integer%] [at %location%]
```
Return type: CoreProtect Log

Equivalent: Every element of `performLookup()` parsed by `parseResult()`

Performs a lookup.
Objects specified with `on` and `expect on` can be Entity Types, Item Types or Block Data.

---

#### Rollback
```skript
rollback [action] (data|logs) in [last] %timespan% [for %strings%] [except for %strings%] [on %objects%] [except on %objects%] [with ([an] action|actions) %integers%] [in radius %integer%] [at %location%]
```
Return type: CoreProtect Log

Equivalent: Every element of `performRollback()` parsed by `parseResult()`

Performs a rollback.
Must be called async.
Objects specified with `on` and `expect on` can be Entity Types, Item Types or Block Data.

---

#### Restore
```skript
restore [action] (data|logs) in [last] %timespan% [for %strings%] [except for %strings%] [on %objects%] [except on %objects%] [with ([an] action|actions) %integers%] [in radius %integer%] [at %location%]
```
Return type: CoreProtect Log

Equivalent: Every element of `performRestore()` parsed by `parseResult()`

Performs a restore.
Calls `performRestore()` and returns the CoreProtect logs parsed with `parseResult()`.
Objects specified with `on` and `expect on` can be Entity Data, Entity Types, Item Types or Block Data.

---

#### Lookup Block
```skript
lookup [action] (data|logs) on %block% in [last] %timespan%
```
Return type: CoreProtect Log

Equivalent: Every element of `blockLookup()` parsed by `parseResult()`

Performs a full lookup on a single block.

---

#### Lookup Sessions
```skript
lookup session[s] [(data|logs)] for %string% in[ last] %timespan%
```
Return type: CoreProtect Log

Equivalent: Every element of `sessionLookup()` parsed by `parseResult()`

Performs a session lookup on a single player.

---

#### Lookup Queue
```skript
lookup [consumer] queue on %block%
```
Return type: CoreProtect Log

Equivalent: Every element of `queueLookup()` parsed by `parseResult()`

Searches the consumer queue for changes on a block not yet saved in the database.

---

#### Block Coordinate
```skript
[the] block ((x|y|z)(-| )(coord[inate]|pos[ition]|loc[ation])|altitude)[s] of %coreprotectlogs%
%coreprotectlogs%'[s] block ((x|y|z)(-| )(coord[inate]|pos[ition]|loc[ation])|altitude)[s]
```
Return type: Integer

Equivalents: `ParseResult.getX()`, `ParseResult.getY()`, `ParseResult.getZ()`

The X/Y/Z coordinate of the block.
`block altitude` is an alias of `block y-coordinate`.

---

#### Block Type
```skript
[the] block type[s] of %coreprotectlogs%
%coreprotectlogs%'[s] block type[s]
```
Return type: Item Type

Equivalent: `new ItemType(ParseResult.getType())`

The Material of the block.

---

#### Logged Block Data
```skript
[the] logged block data[s] of %coreprotectlogs%
%coreprotectlogs%'[s] logged block data[s]
```
Return type: Block Data

Equivalent: `ParseResult.getBlockData()`

The BlockData of the block.

---

#### Player Name
```skript
[the] (player|user) name[s] of %coreprotectlogs%
%coreprotectlogs%'[s] (player|user) name[s]
```
Return type: Text

Equivalent: `ParseResult.getPlayer()`

The username as a string.

---

#### Timestamp
```skript
[the] timestamp[s] of %coreprotectlogs%
%coreprotectlogs%'[s] timestamp[s]
```
Return type: Number

Equivalent: `ParseResult.getTimestamp()`

The time of the action.

---

#### Date
```skript
[the] date[s] of %coreprotectlogs%
%coreprotectlogs%'[s] date[s]
```
Return type: Date

Equivalent: `new Date(ParseResult.getTimestamp())`

The time of the action.

---

#### Action ID
```skript
[the] action ID[s] of %coreprotectlogs%
%coreprotectlogs%'[s] action ID[s]
```
Return type: Integer

Equivalent: `ParseResult.getActionId()`

Get the action ID. (0=removed, 1=placed, 2=interaction)

---

#### Action String
```skript
[the] action[ (string|text)][s] of %coreprotectlogs%
%coreprotectlogs%'[s] action[ (string|text)][s]
```
Return type: Text

Equivalent: `ParseResult.getActionString()`

Get the action as a string. (Removal, Placement, Interaction)

---

#### Logged World Name
```skript
[the] logged world name[s] of %coreprotectlogs%
%coreprotectlogs%'[s] logged world name[s]
```
Return type: Text

Equivalent: `ParseResult.worldName()`

The name of the world the block is located in.

---

#### Logged World
```skript
[the] logged world[s] of %coreprotectlogs%
%coreprotectlogs%'[s] logged world[s]
```
Return type: World

Equivalent: `Bukkit.getWorld(ParseResult.worldName())`

The world the block is located in.

---

#### Block Location
```skript
[the] block (location|position)[s] of %coreprotectlogs%
%coreprotectlogs%'[s] block (location|position)[s]
```
Return type: Location

Equivalent: `new Location(Bukkit.getWorld(ParseResult.worldName()), ParseResult.getX(), ParseResult.getY(), ParseResult.getZ());`

The location of the block.

---

### Types

#### CoreProtect Log
A result of lookup on CoreProtect logs.

---

## Examples

* Get the last 24 hours of block data for endermans.
```skript
set {_lookup::*} to lookup action logs in 24 hours for "##enderman"
```

---

* Get the last 24 hours of block data for endermans excluding dirt and grass blocks.
```skript
set {_exclude::*} to grass and dirt
set {_lookup::*} to lookup action logs in 24 hours for "##enderman" except on {_exclude::*}
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
    set {_lookup::*} to lookup logs in a month in radius 5 at location of player
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
            set {_action} to action_past(action id of {_log})
            set {_type} to block type of {_log}
            set {_age} to difference between now and date of {_log}
            if {_log} is rolled back:
                send "&m%{_user}% %{_action}% %{_type}% %{_age}% ago" to player
            else:
                send "%{_user}% %{_action}% %{_type}% %{_age}% ago" to player

function action_past(action_id: integer) :: text:
    if {_action_id} is 0:
        return "broke"
    else if {_action_id} is 1:
        return "placed"
    else if {_action_id} is 2:
        return "interacted with"
    else:
        return "[unknown]"
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

## License

The source files are distributed under Apache 2.0.
Detail information is in [`LICENSE`](/LICENSE).
