package mc.thelblack.custominventory;

import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;

public interface CInventoryExecutor extends EventExecutor, Listener {

    public void execute(Listener listener, Event event);
}
