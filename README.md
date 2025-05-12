<h1>Hardcore Helper Spigot Plugin</h1>
<br>
<h2>This plugin aims to make setting up hardcore multiplayer speedruns easy.</h2>
<br>
The plugin is addressed as 'HC', and all commands are called using '/hc'.
<br>
<h3>Currently implemented functions</h3>
- Kick all players with death message upon player death<br>
- Stop server after player death<br>
- Shared inventory<br>
- Shared health<br>
- Shared hunger<br>
- Shared experience<br>
- Shared effects<br>
- Player locator on the action bar (select a player via command)<br>
- Player locator on the side scoreboard (shows 5 closest players, may be CPU heavy)<br>
<br><br>
<h3>Config with default options explained</h3>

```
displayDamageMessages: true #Display the amount of damage a player takes to all players. Boolean value
damageMessageMinThreshold: 0 #Minimum amount of damage required to display the damage message. Double value
displayHealMessages: true #Display the amount of health a heals to all players. Boolean value
healMessageMinThreshold: 0.5 #Minimum amount of heal required to display the heal message. Double value
sharedHealth: false #Health sharing between all players. Boolean value
sharedHunger: false #Hunger sharing between all players. Boolean value
sharedEffects: false #Effect sharing between all players. Boolean value
sharedInventory: false #Inventory sharing between all players. Boolean value
sharedExperience: false #Experience sharing between all players. Boolean value
autoOp: false #Give OP to players on server join. Boolean value
autoScoreBoard: true #Automatically create a health scoreboard and display it on player list and above player heads. Boolean value
kickOnDeath: true #Kick all players on player death with the death message as the kick message. Boolean value
stopServerOnDeath: true #Stop server on player death. Boolean value
autoActionBar: true #Automatically enable the action bar locator for players upon joining with the default target being self. Boolean value
giveAllRecipes: true #Unlock all recipes in the recipe book to players upon joining. Boolean value
enableSideLocator: false #Enable the side scoreboard locator. Does not turn on for players automatially. May be CPU heavy. Boolean value
```

<br><br>
<h3>Permissions</h3>

```
hc.command #Allows the player to use the HC command
hc.command.locator #Allows the player to use the locator subcommand, used for toggling and setting target on the locator
hc.command.config #Allows the player to use the config subcommand, used for modifying and managing the plugin config file
hc.command.sidelocator #Allows the player to use the sidelocator subcommand, used for toggling the side locator, must be enabled in the config
hc.command.help #Allows the player to use the help subcommand, used for displaying all possible command usages
```

<br><br>
<h2>Example start scripts for auto-restart and new world generation</h2>
<h3>Linux</h3>

```
#!/bin/bash
while true; do
    rm -rf world
    rm -rf world_nether
    rm -rf world_the_end
    sleep 1
    java -Xms4096M -Xmx4096M -jar server.jar nogui
done
```

<h3>Windows</h3>

```
@echo off
:start
rmdir /s /q world
rmdir /s /q world_nether
rmdir /s /q world_the_end
timeout /t 1
java -Xms4096M -Xmx4096M -jar server.jar nogui
goto start
```
