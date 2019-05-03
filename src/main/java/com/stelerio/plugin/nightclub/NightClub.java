package com.stelerio.plugin.nightclub;

import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.artemis.Aspect;
import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.artemis.WorldConfigurationBuilder;
import com.artemis.managers.UuidEntityManager;
import com.artemis.utils.IntBag;
import com.stelerio.plugin.nightclub.processors.CreateProjectorProcessor;
import com.stelerio.plugin.nightclub.processors.Processor;
import com.stelerio.plugin.nightclub.processors.RemoveEntityProcessor;
import com.stelerio.plugin.nightclub.processors.TargetUpdateProcessor;
import com.stelerio.plugin.nightclub.processors.ToggleProcessor;
import com.stelerio.plugin.nightclub.systems.ProcessorSystem;
import com.stelerio.plugin.nightclub.systems.ProjectorSynchronizationSystem;
import com.stelerio.plugin.nightclub.systems.TargetSystem;
import com.stelerio.plugin.nightclub.systems.VelocitySystem;
import com.stelerio.plugin.nightclub.utils.Vector3f;

public class NightClub extends JavaPlugin {
    private World mWorld;
    private NightClubRunnable mNightClubRunnable;
    private Queue<Processor> mProcessorQueue = new ConcurrentLinkedQueue<Processor>();
    
    @Override
    public void onEnable() {
        ProcessorSystem processor = new ProcessorSystem(mProcessorQueue);
        WorldConfiguration worldConfig = new WorldConfigurationBuilder()
                /* Register systems and plugins */
                .with(new UuidEntityManager())
                .with(processor)
                .with(new TargetSystem())
                .with(new VelocitySystem())
                .with(new ProjectorSynchronizationSystem())
                .build();
        mWorld = new World(worldConfig);
        
        processor.setArchetypeManager(new ArchetypeManager(mWorld));
        
        getCommand("nc").setExecutor(this);
        
        mNightClubRunnable = new NightClubRunnable(mWorld);
        mNightClubRunnable.runTaskAsynchronously(this);
    }
    
    public void queueProcessor(Processor processor) {
        mProcessorQueue.offer(processor);
    }
    
    private void sendHelp(CommandSender sender) {
        sender.sendMessage("NC> /nc ADDPROJECTOR <x> <y> <z> - Adds a new projector to coordonates and returns its id");
        sender.sendMessage("NC> /nc REMOVEPROJECTOR <id> - Removes a projector using its id");
        sender.sendMessage("NC> /nc SETTARGET <id> <x> <y> <z> <rotX> <rotY> <rotZ> <time to target in MS> - Set the position target for an object");
        sender.sendMessage("NC> /nc TOGGLE <id> <status> - Set the toggle status of an object");
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sendHelp(sender);
            return true;
        }
        
        if (!(sender instanceof Player)) {
            sender.sendMessage("NC> Can only be executed by a player.");
            return true;
        }
        
        Player player = (Player) sender;
        
        try {
            if (args[0].equalsIgnoreCase("ADDPROJECTOR") && args.length >= 4) {
                UUID uuid = UUID.randomUUID();
                queueProcessor(new CreateProjectorProcessor(uuid, new Location(player.getWorld(),
                        Float.parseFloat(args[1]),
                        Float.parseFloat(args[2]),
                        Float.parseFloat(args[3]))));
                sender.sendMessage("NC> Added projector with ID " + uuid.toString());
            } else if (args[0].equalsIgnoreCase("REMOVEPROJECTOR") && args.length >= 2) {
                queueProcessor(new RemoveEntityProcessor(UUID.fromString(args[1])));
                sender.sendMessage("NC> Removed projector with ID " + args[1]);
            } else if (args[0].equals("SETTARGET") && args.length >= 9) {
                long travelTime = Long.parseUnsignedLong(args[8]);
                queueProcessor(new TargetUpdateProcessor(UUID.fromString(args[1]),
                        new Vector3f(Float.parseFloat(args[2]), Float.parseFloat(args[3]), Float.parseFloat(args[4])),
                        travelTime,
                        new Vector3f(Float.parseFloat(args[5]), Float.parseFloat(args[6]), Float.parseFloat(args[7])),
                        travelTime));
                sender.sendMessage("NC> Target set on entity with ID " + args[1]);
            } else if (args[0].equals("TOGGLE") && args.length >= 3) {
                queueProcessor(new ToggleProcessor(UUID.fromString(args[1]), Boolean.parseBoolean(args[2])));
                sender.sendMessage("NC> Toggle status set on entity with ID " + args[1]);
            } else {
                sendHelp(sender);
            }
        } catch (Exception e) {
            sender.sendMessage("NC> ERROR: " + e.getMessage());
            getLogger().log(Level.WARNING, "Error executing command", e);
        }
        
        return true;
    }
    
    @Override
    public void onDisable() {
        mNightClubRunnable.cancel();
        IntBag bag = mWorld.getAspectSubscriptionManager().get(Aspect.all()).getEntities();
        for (int i : bag.getData()) mWorld.delete(i);
    }
}
