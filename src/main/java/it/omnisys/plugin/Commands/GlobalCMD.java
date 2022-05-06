package it.omnisys.plugin.Commands;

import net.luckperms.api.model.user.User;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import static it.omnisys.plugin.GlobalX.messagesConfig;
import static it.omnisys.plugin.GlobalX.plugin;
import static it.omnisys.plugin.Utils.ColorUtils.color;
import static it.omnisys.plugin.Utils.RankUtils.getPlayerGroup;

public class GlobalCMD extends Command {
    public GlobalCMD () {
        super("global", "", "gc");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length >= 0) {
            String replace = args.toString().replace("[", "").replace("]", "");

            if (sender instanceof ProxiedPlayer) {
                ProxiedPlayer p = (ProxiedPlayer) sender;


                if (p.hasPermission("globalx.command.globalchat")) {
                    TextComponent clickable = new TextComponent(messagesConfig.getString("ServerNameFormat").replaceAll("%serverName%", p.getServer().getInfo().getName()));
                    clickable.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/server " + p.getServer().getInfo().getName()));

                    String OutPutMessage = color(
                            messagesConfig.getString("GlobalFormat")
                                    .replaceAll("%prefix%", messagesConfig.getString("Prefix")
                                            .replaceAll("%serverNameFormat%", String.valueOf(clickable))
                                            .replaceAll("%luckperms_prefix%", getPlayerGroup(p))
                                            .replaceAll("%luckperms_displayname%", getPlayerGroup(p))
                                            .replaceAll("%playerName%", p.getName())
                                            .replaceAll("%message%", replace)
                                    ));


                    plugin.getProxy().broadcast(OutPutMessage);


                }
            } else {
                String OutPutMessage = color(
                        messagesConfig.getString("GlobalFormat")
                                .replaceAll("%prefix%", messagesConfig.getString("Prefix")
                                        .replaceAll("%serverNameFormat%", messagesConfig.getString("ConsoleServer"))
                                        .replaceAll("%luckperms_prefix%", "")
                                        .replaceAll("%luckperms_displayname%", "")
                                        .replaceAll("%playerName%", messagesConfig.getString("ConsoleNameFormat"))
                                        .replaceAll("%message%", replace)
                                ));


                plugin.getProxy().broadcast(color(OutPutMessage));
            }
        } else {
            sender.sendMessage(color(messagesConfig.getString("InsuffArgs")));
        }
    }
}
