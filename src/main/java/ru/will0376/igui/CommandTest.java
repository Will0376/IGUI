package ru.will0376.igui;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

//@SideOnly(Side.CLIENT)
public class CommandTest extends CommandBase {
	@Override
	public String getName() {
		return "t";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		//net.minecraft.client.Minecraft.getMinecraft().addScheduledTask(() -> net.minecraft.client.Minecraft.getMinecraft().displayGuiScreen(new TestGui()));
	}
}
