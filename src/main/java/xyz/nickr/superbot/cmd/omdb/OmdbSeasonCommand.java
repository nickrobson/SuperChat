package xyz.nickr.superbot.cmd.omdb;

import java.util.LinkedList;
import java.util.List;

import xyz.nickr.jomdb.JavaOMDB;
import xyz.nickr.jomdb.model.SeasonEpisodeResult;
import xyz.nickr.jomdb.model.SeasonResult;
import xyz.nickr.superbot.SuperBotController;
import xyz.nickr.superbot.cmd.Command;
import xyz.nickr.superbot.sys.Group;
import xyz.nickr.superbot.sys.Message;
import xyz.nickr.superbot.sys.MessageBuilder;
import xyz.nickr.superbot.sys.Sys;
import xyz.nickr.superbot.sys.User;

public class OmdbSeasonCommand implements Command {

    @Override
    public String[] names() {
        return new String[]{ "omdbseason" };
    }

    @Override
    public String[] help(User user, boolean userchat) {
        return new String[]{ "[imdbId] [season]", "get season information" };
    }

    private String pad(String s, int len) {
        while (s.length() < len)
            s += " ";
        return s;
    }

    @Override
    public void exec(Sys sys, User user, Group group, String used, String[] args, Message message) {
        if (args.length < 2) {
            sendUsage(sys, user, group);
        } else {
            MessageBuilder<?> mb = sys.message();
            if (JavaOMDB.IMDB_ID_PATTERN.matcher(args[0]).matches()) {
                SeasonResult season = SuperBotController.OMDB.seasonById(args[0], args[1]);
                mb.escaped(season.title + ", season " + args[1] + ": ");
                List<String> infos = new LinkedList<>();
                for (SeasonEpisodeResult episode : season) {
                    MessageBuilder<?> emb = sys.message();
                    emb.bold(true).escaped("E" + episode.episode + " (" + episode.imdbRating + ")").bold(false);
                    emb.escaped(": " + episode.title);
                    infos.add(emb.build());
                }
                int maxLen = infos.subList(0, sys.columns() ? infos.size() / 2 : infos.size()).stream().mapToInt(s -> s.length()).max().orElse(0);
                for (int i = 0, j = sys.columns() ? infos.size() / 2 : infos.size(); i < j; i++) {
                    if (sys.columns() && i >= j/2) {
                        mb.escaped("   " + infos.get(i));
                    } else {
                        mb.newLine().raw(pad(infos.get(i), maxLen));
                    }
                }
            } else {
                mb.escaped("Invalid IMDB ID (" + args[0] + ")");
            }
            group.sendMessage(mb);
        }
    }

}