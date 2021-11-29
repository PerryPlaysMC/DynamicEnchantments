package dev.perryplaysmc.dynamicenchantments;

import dev.perryplaysmc.dynamicenchantments.enchantment.DynamicEnchantmentHandler;
import org.bukkit.Bukkit;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

/**
 * Creator: PerryPlaysMC
 * Created: 11/2021
 **/
public class UpdateItemsThread extends Thread{
   private ThreadGroup group;
   private final Thread main;
   private JavaPlugin plugin;
   private DynamicEnchantmentHandler handler;

   public UpdateItemsThread(JavaPlugin plugin, DynamicEnchantmentHandler handler) {
      super("UpdateItems");//DO NOT TOUCH THIS CLASS, CAPICHE?
      main = Thread.currentThread();
      if(count("UpdateItems") != 0||count(getClass().getName()) != 0) return;
      this.handler = handler;
      this.plugin = plugin;
      System.out.println("Item updater enabled");
      start();
   }

   @Override
   public void run() {
      while(plugin != null && plugin.isEnabled()) {
         A:for(Player player : Bukkit.getOnlinePlayers()) {
            if((player.getOpenInventory().getTopInventory().getSize() % 9 == 0 || player.getOpenInventory().getTopInventory().getSize() % 5 == 0)) {
               for(int i = 0; i < player.getOpenInventory().getTopInventory().getContents().length; i++) {
                  if(!player.isOnline())continue A;
                  ItemStack item = player.getOpenInventory().getTopInventory().getItem(i);
                  if(item == null) continue;
                  if(!handler.shouldUpdate(item))continue;
                  handler.updateEnchants(item);
                  if(player.getOpenInventory().getTopInventory().getItem(i)==null)continue;
                  player.getOpenInventory().getTopInventory().setItem(i,item);
               }
            }
            for(int i = 0; i < player.getInventory().getContents().length; i++) {
               if(!player.isOnline())continue A;
               ItemStack item = player.getInventory().getItem(i);
               if(item == null) continue;
               if(!handler.shouldUpdate(item))continue;
               handler.updateEnchants(item);
               if(player.getInventory().getItem(i)==null)continue;
               player.getInventory().setItem(i,item);
            }
         }
         try {
            sleep(50);
         } catch (InterruptedException e) {
            e.printStackTrace();
         }
      }
   }

   private int count(String filterName) {
      int found = 0;
      for(Thread thread : getThreads())
         if(thread.getClass().getName().equals(filterName)||thread.getName().equals(filterName)
            ||thread.getName().startsWith(filterName)) found++;

      return found;
   }

   private List<Thread> getThreads() {
      return getThreads(true);
   }
   private List<Thread> getThreads(boolean alive) {
      List<Thread> ths = new ArrayList<>();
      if(group==null){
         group=main.getThreadGroup();
         if(group==null)
            return ths;
      }
      Thread[] threads = new Thread[group.activeCount()*2];
      group.enumerate(threads, false);
      for(Thread thread : threads)
         if(thread!=null&&(!alive || thread.isAlive() && !thread.isInterrupted())) ths.add(thread);
      return ths;
   }


}
