package fihgu.teleport.commands;

import net.minecraft.entity.player.EntityPlayerMP;
import fihgu.core.elements.CommandBase;
import fihgu.core.elements.Location;
import fihgu.core.elements.Player;
import fihgu.core.elements.Request;
import fihgu.core.functions.Language;
import fihgu.core.functions.McColor;
import fihgu.core.functions.PlayerManager;
import fihgu.core.functions.Warp;

public class WarpCommand extends CommandBase
{
	Warp warp;
	private EntityPlayerMP sender;

	public WarpCommand()
	{
		warp = new Warp();
		name = "warp";
		usage = Language.translate(" <Warp Name>: Teleport to a set Warp");
	}

	@Override
	public void processPlayer(EntityPlayerMP player, String[] args)
	{
		EntityPlayerMP player2 = PlayerManager.getPlayer(args[0], true);
		if (args.length < 1 || args.length > 1)
		{
			player.sendChatToPlayer(McColor.red
					+ Language.translate("Invalad command arguments."));
			player.sendChatToPlayer(Language
					.translate("Usage: /warp <Warp Name | Player>"));
		} else if (args.length == 1)
		{
			if (player2 != null)
			{
				sender = player;
				new Request(new Player(player2), 30) {
					@Override
					public void accepted()
					{
						warp.warpTo(sender, player.getEntity());
						sender.sendChatToPlayer(McColor.blue + player
								+ McColor.green
								+ Language.translate(" has accepted!"));
						player.msg(McColor.green
								+ Language.translate("Warpped to ")
								+ McColor.blue + sender.username);
					}
				};
			} else if (warp.warpTo(player, args[0]))
			{
				player.sendChatToPlayer(McColor.green
						+ Language.translate("Warped to ") + args[0] + ".");
			} else
			{
				player.sendChatToPlayer(McColor.red
						+ Language.translate("Warp ") + McColor.blue + args[0]
						+ McColor.red + Language.translate(" does not exist!"));
			}
		}
	}
}
