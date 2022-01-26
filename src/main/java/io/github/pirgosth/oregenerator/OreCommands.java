package io.github.pirgosth.oregenerator;

import io.github.pirgosth.liberty.core.api.commands.ICommandArgument;
import io.github.pirgosth.liberty.core.api.commands.ICommandListener;
import io.github.pirgosth.liberty.core.api.commands.annotations.LibertyCommand;
import io.github.pirgosth.liberty.core.api.commands.annotations.LibertyCommandArgument;
import io.github.pirgosth.liberty.core.api.commands.annotations.LibertyCommandPermission;
import io.github.pirgosth.liberty.core.api.utils.ChatUtils;
import io.github.pirgosth.liberty.core.commands.CommandParameters;
import org.bukkit.entity.Player;

public class OreCommands implements ICommandListener {

    @LibertyCommand(command = "og.reload")
    @LibertyCommandPermission(permission = "oregenerator.commands.reload")
    public boolean reloadCommand(CommandParameters params) {
        OreGenerator.getMainConfig().reload();
        ChatUtils.sendColorMessage(params.sender, "&7[&cOreGenerator&7]: &aReloaded successfully !");
        return true;
    }

    @LibertyCommand(command = "og.enable")
    @LibertyCommandPermission(permission = "oregenerator.commands.enable")
    @LibertyCommandArgument(type = ICommandArgument.ArgumentType.String)
    public boolean enableWorldCommand(CommandParameters params) {
        String worldName = this.getToggleWorldName(params);
        if (worldName == null) {
            ChatUtils.sendColorMessage(params.sender, "&4This command can only be performed by a player!");
            return true;
        }

        if (!Config.doesWorldExist(worldName)) {
            ChatUtils.sendColorMessage(params.sender, "&4You must specify an existing world name !");
        }
        if (OreGenerator.getMainConfig().addActiveWorld(worldName)) {
            ChatUtils.sendColorMessage(params.sender, String.format("&7World &a%s &7enabled successfully", worldName));
        } else {
            ChatUtils.sendColorMessage(params.sender, String.format("&7World &a%s &7is already enabled", worldName));
        }
        return true;
    }

    @LibertyCommand(command = "og.disable")
    @LibertyCommandPermission(permission = "oregenerator.commands.disable")
    @LibertyCommandArgument(type = ICommandArgument.ArgumentType.String)
    public boolean disableWorldCommand(CommandParameters params) {
        String worldName = this.getToggleWorldName(params);
        if (worldName == null) {
            ChatUtils.sendColorMessage(params.sender, "&4This command can only be performed by a player!");
            return true;
        }
        if (!OreGenerator.getMainConfig().getActiveWorlds().contains(worldName)) {
            ChatUtils.sendColorMessage(params.sender, "&4You must specify an active world name !");
        }
        if (OreGenerator.getMainConfig().delActiveWorld(worldName)) {
            ChatUtils.sendColorMessage(params.sender, String.format("&7World &a%s &7disabled successfully", worldName));
        } else {
            ChatUtils.sendColorMessage(params.sender, String.format("&7World &a%s &7is already disabled", worldName));
        }
        return true;
    }

    private String getToggleWorldName(CommandParameters params) {
        if (params.args.length > 0) {
            return params.args[0];
        }

        return (params.sender instanceof Player player) ? player.getWorld().getName() : null;
    }

    @LibertyCommand(command = "og.list")
    @LibertyCommandPermission(permission = "oregenerator.commands.list")
    public boolean listWorldsCommand(CommandParameters params) {
        ChatUtils.sendColorMessage(params.sender, String.format("&7Active worlds: &3%s", OreGenerator.getMainConfig().getActiveWorlds().toString()));
        return true;
    }
}
