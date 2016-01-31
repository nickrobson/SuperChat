package xyz.nickr.superbot.cmd.util;

import xyz.nickr.superbot.cmd.Command;
import xyz.nickr.superbot.sys.Group;
import xyz.nickr.superbot.sys.Message;
import xyz.nickr.superbot.sys.Sys;
import xyz.nickr.superbot.sys.User;

public class GitCommand implements Command {

    @Override
    public String[] names() {
        return new String[] { "git" };
    }

    @Override
    public String[] help(User user, boolean userChat) {
        return new String[] { "", "tells you the bot's git repo" };
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
        group.sendMessage(sys.message().link("http://github.com/nickrobson/SuperChat").html("http://github.com/nickrobson/SuperChat").build());
    }

}
