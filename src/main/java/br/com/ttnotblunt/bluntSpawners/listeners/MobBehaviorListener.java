package br.com.ttnotblunt.bluntSpawners.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.util.Vector;

public class MobBehaviorListener implements Listener {

    @EventHandler
    public void aoSpawnar(CreatureSpawnEvent e) {
        if (e.getSpawnReason() != CreatureSpawnEvent.SpawnReason.SPAWNER) return;
        if (!(e.getEntity() instanceof LivingEntity)) return;

        LivingEntity mob = (LivingEntity) e.getEntity();
        mob.setAI(false);
        mob.setSilent(true);
    }

    @EventHandler
    public void aoTomarDano(EntityDamageEvent e) {
        // Aplica apenas para mobs vivos
        if (!(e.getEntity() instanceof LivingEntity)) return;
        e.getEntity().setVelocity(new Vector(0, 0.01, 0));
    }

    @EventHandler
    public void aoMirar(EntityTargetEvent e) {
        // Cancela apenas para mobs vivos
        if (!(e.getEntity() instanceof LivingEntity)) return;
        e.setCancelled(true);
    }
}