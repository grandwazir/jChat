jChat - Simple chat prefix and suffixes
====================================

## Description

jChat is a simple and flexible plugin for the Minecraft wrapper [Bukkit](http://bukkit.org/) designed to allow adding prefixes and suffixes to players names. Currently it is only compatible with the inbuilt permissions in Bukkit. 

## Features

- Lightweight: only manages prefixes and suffixes.
- Modifies join, death and quit messages to reflect player's display names.
- Fast: Formats a players' name permanently on login and when a player changes worlds.
- Good citizen: Does not interfere with other chat plugins.
- Simple: Very easy to configure, simply assign the permission nodes to players.

## Installation

### Ensure you are using the latest recommended build.

Before you installing jChat, you need to make sure you are running at least the latest [recommended build](http://ci.bukkit.org/job/dev-CraftBukkit/Recommended/) for Bukkit. 

### Getting jChat

The best way to install jChat is to use the direct download link to the latest version:

    http://downloads.james.richardson.name/public/binaries/jchat/jChat.jar
    
Older versions are available as well, however they are not supported.

    http://downloads.james.richardson.name/public/binaries/jchat/

## Configuration

Configure your settings in config.yml. The configuration file can be found in the datafolder for the plugin which is usually: 

    plugins/jChat/config.yml

You add additional prefixes and suffixes to the lists by using the defaults as an example.

### Commands

- /jchat refresh (refresh your display name)

Further [instructions](https://github.com/grandwazir/DynamicMOTD/wiki/instructions) are available on wiki. 
