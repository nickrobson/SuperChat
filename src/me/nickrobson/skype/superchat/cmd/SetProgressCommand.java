package me.nickrobson.skype.superchat.cmd;

import java.util.Map;

import me.nickrobson.skype.superchat.SuperChatController;
import me.nickrobson.skype.superchat.SuperChatListener;
import me.nickrobson.skype.superchat.SuperChatShows;
import me.nickrobson.skype.superchat.SuperChatShows.Show;
import xyz.gghost.jskype.Group;
import xyz.gghost.jskype.message.Message;
import xyz.gghost.jskype.user.User;

public class SetProgressCommand implements Command {

	@Override
	public String[] names() {
		return new String[]{ "me" };
	}

	@Override
	public String[] help(User user) {
		return new String[]{"[show] [episode]", "set your progress on [show] to [episode]"};
	}

	@Override
	public void exec(User user, Group group, String used, String[] args, Message message) {
		if (args.length < 2) {
			sendMessage(group, "Incorrect usage: `~me [show] [episode]`", true);
		} else {
			Show show = SuperChatShows.getShow(args[0]);
			String ep = args[1].toUpperCase();
			
			if (show == null) {
				sendMessage(group, "Invalid show name: " + args[0], true);
			} else if (!SuperChatShows.EPISODE_PATTERN.matcher(ep).matches()) {
				sendMessage(group, "Invalid episode: " + ep + " (doesn't match SxEyy format)", true);
			} else {
				Map<String, String> prg = SuperChatListener.getProgress(show);
				prg.put(user.getUsername(), ep);
				SuperChatListener.PROGRESS.put(show.getMainName(), prg);
				sendMessage(group, "Set " + user.getDisplayName() + "'s progress on " + show.getDisplay() + " to " + ep, true);
				SuperChatController.save();
			}
		}
	}

}
