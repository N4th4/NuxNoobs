NuxKits
=======

NuxNoobs is a plugin that sends a message every X seconds to your noobs.

Installation
------------

* First, you need the GroupManager plugin ([here](http://forums.bukkit.org/threads/326-490.4723/)).
* Download the latest jar [here](https://github.com/N4th4/NuxNoobs/downloads).
* Copy the downloaded jar file into the plugins folder and rename it to "NuxNoobs.jar".

Configuration
-------------

The configuration file is : plugins/NuxNoobs/config.yml

Example :

    timer : 600
    group : Noob
    message :
        - Your first line
        - Your second line
        - Your third line

The timer is in seconds. The group is optional (default is "Default"). __Never__ use tabulations, only four spaces.

Permissions' nodes
------------------

* nuxnoobs.reload

Commands
--------

* /NuxNoobs reload - Reload the configuration.
