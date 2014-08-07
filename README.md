TestPlugin
==========
v. 0.0.2
Creating a Test Plugin for Bukkit Minecraft

commands:
    fly:
        description: Toggle Fly
        usage: "Usage: /fly [player]"
    heal:
        description: Heal player or yourself
        usage: "Usage: /heal [player]
    home:
        description: Spawn at home location
        usage: "Usage: /home"
    kill:
        description: Kill a player.
        usage: "Usage: /kill <player>"
    sethome:
        description: Set your home location
        usage: "Usage: /kill <player>"
    test:
        description: Test if Plugin is working.
        usage: "Usage: /test"
    tp:
        description: Teleport to someone or somewhere
        usage: "Usage: /tp <player> || <player1> <player2> || <x> <y> <z>"
    who:
        description: Show who's online
        usage: "Usage: /who"
    w:
        description: Whisper to a player.
        usage: "Usage: /w <player>"
        
Permissions:
    TestPlugin.*:
        description: Gives access to all TestPlugin commands
        children:
            TestPlugin.fly: true
            TestPlugin.home: true
            TestPlugin.kill: true
            TestPlugin.sethome: true
            TestPlugin.test: true
            TestPlugin.tp: true
            TestPlugin.who: true
            TestPlugin.whisper: true
    TestPlugin.fly
        description: Give player all Fly Related permissions
        children:
            TestPlugin.fly.me
            TestPlugin.fly.others
    TestPlugin.fly.me
        description: Allow player to toggle personal fly
        default: op
    TestPlugin.fly.others
        description: Allow player to toggle other player's fly
        default: op
    TestPlugin.heal
        desciption: Give all heal commands
        children:
            TestPlugin.heal.me
            TestPlugin.heal.others
    TestPlugin.heal.me
        description: Heal yourself
        default: op
    TestPlugin.heal.others
        description: Heal others (ability to use player syntax)
        default: op
    TestPlugin.home:
        description: Teleport to home
        default: true
    TestPlugin.kill:
        description: Permission to kill players by command.
        default: op
    TestPlugin.sethome:
        description: Permission to set home.
        default: true
    TestPlugin.test:
        description: Test if plugin is working
        default: op
    TestPlugin.tp:
        description: Gives access to all TP commands
        children:
            TestPlugin.tp.me: true
            TestPlugin.tp.others: true
            TestPlugin.tp.coord: true
    TestPlugin.tp.me:
        description: Teleport yourself to a player
        default: op
    TestPlugin.tp.others:
        description: Teleport Player1 to Player2
        default: op
    TestPlugin.tp.coord:
        description: Teleport Player to x,y,z coordinate
        default: op
    TestPlugin.who:
        description: Displays Players that are online
        default: true
    TestPlugin.whisper:
        description: Allow Player to Whisper
        default: true