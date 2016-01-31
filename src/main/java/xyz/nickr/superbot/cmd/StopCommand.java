package xyz.nickr.superbot.cmd;

import xyz.nickr.superbot.SuperBotController;
import xyz.nickr.superbot.sys.Group;
import xyz.nickr.superbot.sys.Message;
import xyz.nickr.superbot.sys.Sys;
import xyz.nickr.superbot.sys.User;

public class StopCommand implements Command {

    @Override
    public String[] names() {
        return new String[] { "stop", "restart", "kys" };
    }

    @Override
    public String[] help(User user, boolean userChat) {
        return new String[] { "", "stops the bot (restarting through a script)" };
    }

    @Override
    public Permission perm() {
        return string("admin.stop");
    }

    @Override
    public boolean userchat() {
        return true;
    }

    @Override
    public boolean alwaysEnabled() {
        return true;
    }

    @Override
    public void exec(Sys sys, User user, Group group, String used, String[] args, Message message) {
        if (used.equalsIgnoreCase("kys"))
            group.sendMessage(sys.message().text("ded"));
        else
            group.sendMessage(sys.message().text("Goodbye!"));
        SuperBotController.saveProgress();
        System.exit(0);
    }

}
