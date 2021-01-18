package me.mrgeneralq.sleepmost.runnables;

import me.mrgeneralq.sleepmost.statics.DataContainer;
import me.mrgeneralq.sleepmost.enums.SleepSkipCause;
import me.mrgeneralq.sleepmost.events.SleepSkipEvent;
import me.mrgeneralq.sleepmost.interfaces.ISleepService;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

public class NightcycleAnimationTask extends BukkitRunnable {

    private final ISleepService sleepService;
    private final DataContainer dataContainer = DataContainer.getContainer();
    private final World world;
    private final String lastSleeperName;

    public NightcycleAnimationTask(ISleepService sleepService, World world, String lastSleeperName) {
        this.sleepService = sleepService;
        this.world = world;
        this.lastSleeperName = lastSleeperName;
    }

    @Override
    public void run() {

        if(!sleepService.isNight(world)){

            //remove animation checker
            this.dataContainer.setAnimationRunning(world, false);

            world.setThundering(false);
            world.setStorm(false);

            Bukkit.getServer().getPluginManager().callEvent(new SleepSkipEvent(world, SleepSkipCause.NIGHT_TIME, "",""));
            this.cancel();
        }

        world.setTime(world.getTime() + 85);
    }
}
