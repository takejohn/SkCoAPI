# SkCoAPI

## Overview

SkCoAPI is an adapter of CoreProtect API v9 for Skript.

Refer [CoreProtect API v9 Documentation](https://docs.coreprotect.net/api/version/v9/) for information of each Java method.

## Minimum Supported Versions

* Java: 11
* Spigot: 1.15 R0.1
* Skript: 2.6.4
* CoreProtect: 22.1

## Documentation

[![SkriptHubViewTheDocs](http://skripthub.net/static/addon/ViewTheDocsButton.png)](http://skripthub.net/docs/?addon=SkCoAPI)
[![Get on skUnity](https://docs.skunity.com/skunity/library/Docs/Assets/assets/images/buttons/v2/get-the-syntax-black.png)](https://docs.skunity.com/syntax/search/addon:SkCoAPI)

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
