# SkCoAPI

## Overview

SkCoAPI is an adapter of CoreProtect API v9 for Skript.

Refer [CoreProtect API v9 Documentation](https://docs.coreprotect.net/api/version/v9/) for information of each Java method.

## Minimum Supported Versions

Spigot: 1.18.2 R0.1
Skript: 2.6.4
CoreProtect: 22.0

## Usage

### Events

#### On CoreProtect Logging
```skript
[on] CoreProtect log[ging] [(for %strings%|[an] action [by %strings%])]
```
An equivalent to `CoreProtectPreLogEvent`.

### Conditions

#### Is Enabled
```skript
[the] CoreProtect API is[(n't| not)] (enabled|disabled)
```
Calls `isEnabled()`.

#### Is Rolled Back
```skript
%coreprotectlog% is[(n't| not)] rolled back
```
Calls `ParseResult.isRolledBack()`.

#### Has Placed/Built
```skript
%string% has[(n't | not)] (placed|built) %block% in[ last] %timespan% [(until|till) %-timespan%]
```
Calls `hasPlaced()`.

#### Has Broken/Removed
```skript
%string% has[(n't | not)] (broken|removed) %block% in [last] %timespan% [(until|till) %-timespan%]
```
Calls `hasRemoved()`.

### Effects

#### Test CoreProtect API
```skript
test [the] CoreProtect API
```
Calls `testAPI()`.

#### Log Said
```skript
log [that] %string% (said|sent [the] message) %string%
```
Calls `logChat()`.

#### Log Executed
```skript
log [that] %player% executed [[the] command] %string%
```
Calls `logCommand()`.

#### Log Placed/Built
```skript
log [that] %string% (placed|built) %block data% at %location%
```
Calls `logPlacement()`.

#### Log Broke/Removed
```skript
log [that] %string% (broke|removed) %block data% at %location%
```
Calls `logRemoval()`.

#### Log Container Transaction
```skript
log [that] %string% made [a] transaction [to [a] container] at %location%
```
Calls `logContainerTransaction()`.

#### Log Interaction
```skript
log [that] %string% interacted [[a] block] at %location%
```
Calls `logInteraction()`.

### Expressions

#### API Version
```skript
[the] CoreProtect API['s] version
[the] version of CoreProtect API
```
Return Type: Integer

Returns `APIVersion()`.

#### User/Player Name
```skript
[the] (user|player) name
```
Return Type: Text

Usable only in CoreProtect logging event.
Returns `getUser()`.
Can be changed which calls `setUser()`.
This can be referred as `event-string`, which only the getter is provided.

#### Lookup
```skript
lookup [action] (data|logs) in [last] %timespan% [for %strings%] [except for %strings%] [on %objects%] [except on %objects%] [with ([an] action|actions) %integers%] [in radius %integer%] [at %location%]
```
Return type: CoreProtect Log

Calls `performLookup()` and returns the CoreProtect logs parsed with `parseResult()`.
Objects specified with `on` and `expect on` can be Entity Types, Item Types or Block Data.

#### Rollback
```skript
rollback [action] (data|logs) in [last] %timespan% [for %strings%] [except for %strings%] [on %objects%] [except on %objects%] [with ([an] action|actions) %integers%] [in radius %integer%] [at %location%]
```
Return type: CoreProtect Log

Calls `performRollback()` and returns the CoreProtect logs parsed with `parseResult()`.
Objects specified with `on` and `expect on` can be Entity Types, Item Types or Block Data.

#### Restore
```skript
restore [action] (data|logs) in [last] %timespan% [for %strings%] [except for %strings%] [on %objects%] [except on %objects%] [with ([an] action|actions) %integers%] [in radius %integer%] [at %location%]
```
Return type: CoreProtect Log

Calls `performRestore()` and returns the CoreProtect logs parsed with `parseResult()`.
Objects specified with `on` and `expect on` can be Entity Data, Entity Types, Item Types or Block Data.

#### Lookup Block
```skript
lookup [action] (data|logs) on %block% in [last] %timespan%
```
Return type: CoreProtect Log

Calls `blockLookup()` and returns the CoreProtect logs parsed with `parseResult()`.

#### Lookup Sessions
```skript
lookup session[s] [(data|logs)] for %string% in[ last] %timespan%
```
Return type: CoreProtect Log

Calls `sessionLookup()` and returns the CoreProtect logs parsed with `parseResult()`.

#### Lookup Queue
```skript
lookup [consumer] queue on %block%
```
Return type: CoreProtect Log

Calls `queueLookup()` and returns the CoreProtect logs parsed with `parseResult()`.

#### Block Coordinate
```skript
[the] block ((x|y|z)(-| )(coord[inate]|pos[ition]|loc[ation])|altitude)[s] of %coreprotectlogs%
%coreprotectlogs%'[s] block ((x|y|z)(-| )(coord[inate]|pos[ition]|loc[ation])|altitude)[s]
```
Return type: Integer

Returns `ParseResult.getX()` for the x-coordinate,
`ParseResult.getY()` for the y-coordinate or altitude,
or `ParseResult.getZ()` for the z-coordinate.

#### Block Type
```skript
[the] block type[s] of %coreprotectlogs%
%coreprotectlogs%'[s] block type[s]
```
Return type: Item Type

Returns `new ItemType(ParseResult.getType())`.

#### Logged Block Data
```skript
[the] logged block data[s] of %coreprotectlogs%
%coreprotectlogs%'[s] logged block data[s]
```
Return type: Block Data

Returns `ParseResult.getBlockData()`.

#### Player Name
```skript
[the] (player|user) name[s] of %coreprotectlogs%
%coreprotectlogs%'[s] (player|user) name[s]
```
Return type: Text

Returns `ParseResult.getPlayer()`.

#### Timestamp
```skript
[the] timestamp[s] of %coreprotectlogs%
%coreprotectlogs%'[s] timestamp[s]
```
Return type: Number

Returns `ParseResult.getTimestamp()`.

#### Date
```skript
[the] date[s] of %coreprotectlogs%
%coreprotectlogs%'[s] date[s]
```
Return type: Date

Returns `new Date(ParseResult.getTimestamp())`.

#### Action ID
```skript
[the] action ID[s] of %coreprotectlogs%
%coreprotectlogs%'[s] action ID[s]
```
Return type: Integer

Returns `ParseResult.getActionId()`.

#### Action String
```skript
[the] action[ (string|text)][s] of %coreprotectlogs%
%coreprotectlogs%'[s] action[ (string|text)][s]
```
Return type: Text

Returns `ParseResult.getActionString()`.

#### Logged World Name
```skript
[the] logged world name[s] of %coreprotectlogs%
%coreprotectlogs%'[s] logged world name[s]
```
Return type: Text

Returns `ParseResult.worldName()`.

#### Logged World
```skript
[the] logged world[s] of %coreprotectlogs%
%coreprotectlogs%'[s] logged world[s]
```
Return type: World

Returns `Bukkit.getWorld(ParseResult.worldName())`.

#### Block Location
```skript
[the] block (location|position)[s] of %coreprotectlogs%
%coreprotectlogs%'[s] block (location|position)[s]
```
Return type: Location

Returns `new Location(Bukkit.getWorld(ParseResult.worldName()), ParseResult.getX(), ParseResult.getY(), ParseResult.getZ());`.

### Types

#### CoreProtect Log
A result of lookup on CoreProtect logs.

## Examples

* Get the last 24 hours of block data for endermans.
```skript
set {_lookup::*} to lookup action logs in 24 hours for "##enderman"
```

* Get the last 24 hours of block data for endermans excluding dirt and grass blocks.
```skript
set {_exclude::*} to grass and dirt
set {_lookup::*} to lookup action logs in 24 hours for "##enderman" except on {_exclude::*}
```

* Add "'s hand" to usernames when logging.
```skript
on CoreProtect logging:
    set user name to "%user name%'s hand"
```

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

* When executing the command `/sessions`, get the last 1-day session data for the specified user.
```skript
command /sessions <text>:
    trigger:
        set {_lookup::*} to lookup sessions for arg-1 in a day
        send {_lookup::*} to sender
```

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
