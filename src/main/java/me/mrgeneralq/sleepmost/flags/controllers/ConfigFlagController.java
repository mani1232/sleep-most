package me.mrgeneralq.sleepmost.flags.controllers;

import me.mrgeneralq.sleepmost.interfaces.IConfigRepository;
import org.bukkit.World;

public class ConfigFlagController<V> extends AbstractFlagController<V>
{
    private final IConfigRepository configRepository;

    //refactor to take an object of IValuesSerialization<V>
    public ConfigFlagController(IConfigRepository configRepository)
    {
        this.configRepository = configRepository;
    }

    @Override
    public V getValueAt(World world)
    {
        Object configValue = this.configRepository.getFlagValue(this.flag, world);

        return this.flag.getSerialization().parseValueFrom(configValue);
    }

    @Override
    public void setValueAt(World world, V value)
    {
        this.configRepository.setFlagValue(this.flag, world, value);
    }
}
