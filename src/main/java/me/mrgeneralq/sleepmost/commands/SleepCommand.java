package me.mrgeneralq.sleepmost.commands;

import me.mrgeneralq.sleepmost.interfaces.IMessageService;
import me.mrgeneralq.sleepmost.interfaces.ISleepService;
import me.mrgeneralq.sleepmost.messages.MessageTemplate;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SleepCommand implements CommandExecutor {
    private final ISleepService sleepService;
    private final IMessageService messageService;

    public SleepCommand(ISleepService sleepService, IMessageService messageService) {
        this.sleepService = sleepService;
        this.messageService = messageService;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(messageService.fromTemplate(MessageTemplate.ONLY_PLAYERS_COMMAND));
            return true;
        }
        Player player = (Player) sender;

        if (!player.hasPermission("sleepmost.sleep")) {
            player.sendMessage(this.messageService.fromTemplate(MessageTemplate.NO_PERMISSION));
            return true;
        }
        World world = player.getWorld();

        if (!this.sleepService.resetRequired(world)) {
            player.sendMessage(messageService.fromTemplate(MessageTemplate.CANNOT_SLEEP_NOW));
            return true;
        }

        if (this.sleepService.isPlayerAsleep(player)) {
            player.sendMessage(this.messageService.fromTemplate(MessageTemplate.ALREADY_SLEEPING));
            return true;
        }
        player.sendMessage(this.messageService.fromTemplate(MessageTemplate.SLEEP_SUCCESS));
        this.sleepService.setSleeping(player, true);
        return true;
    }

}
