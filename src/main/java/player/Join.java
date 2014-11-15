package player;

import com.carlgo11.sameip.Mysql;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPreLoginEvent;

public class Join implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerPreLoginEvent e)
    {
        String p = e.getName();
        System.out.println(e.getName());
        String getip = Mysql.getIp(e.getAddress().getHostAddress());
        if (getip != null && getip != Bukkit.getOfflinePlayer(p).getUniqueId().toString()) {
            UUID d = UUID.fromString(getip);
            String name;
            if (Bukkit.getOfflinePlayer(d).isOnline()) {
                name = ChatColor.GREEN + Bukkit.getOfflinePlayer(d).getName();
            } else {
                name = ChatColor.RED + Bukkit.getOfflinePlayer(d).getName();
            }
            Bukkit.broadcast("" + ChatColor.YELLOW + p + " is on the same ip as " + name + ChatColor.RESET, "sameip.notification");
        } else {
            System.out.println(e.getAddress().getHostAddress());
            System.out.println(Bukkit.getOfflinePlayer(p).getUniqueId().toString());
        }
        if (!Mysql.getPlayer(p)) {
            Mysql.addPlayer(p, e.getAddress().getHostAddress());
        }
    }
}
