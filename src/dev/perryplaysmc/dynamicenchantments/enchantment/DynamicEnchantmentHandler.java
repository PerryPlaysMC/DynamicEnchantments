package dev.perryplaysmc.dynamicenchantments.enchantment;


import dev.perryplaysmc.dynamicenchantments.UpdateItemsThread;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredListener;
import org.bukkit.plugin.java.JavaPlugin;
import dev.perryplaysmc.dynamicenchantments.compiler.DynamicJavaCompiler;
import org.bukkit.util.Vector;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class DynamicEnchantmentHandler implements Listener {

   private final JavaPlugin plugin;
   private Class<?> enchantWrapper;
   private DynamicJavaCompiler COMPILER;
   private final Field getFile;

   public DynamicEnchantmentHandler(JavaPlugin plugin) {
      this.plugin = plugin;
      PluginManager pm = Bukkit.getPluginManager();
      boolean isRegistered = false;
      for(RegisteredListener reg : HandlerList.getRegisteredListeners(plugin))
         if(reg.getListener().getClass().getName().equals(getClass().getName())) {
            isRegistered = true;
            break;
         }
      if(!isRegistered) pm.registerEvents(this, plugin);
      Field getFile1 = null;
      try {
         getFile1 = JavaPlugin.class.getDeclaredField("file");
         getFile1.setAccessible(false);
      } catch (NoSuchFieldException e) {
         e.printStackTrace();
      }
      getFile = getFile1;
      if(getFile!=null) getFile.setAccessible(true);
      load(plugin);
      new UpdateItemsThread(plugin,this);
   }

   public void unregisterEnchantments() {
      Method getKeyOrId = null;
      Field byIdOrKey = null;
      try{
         getKeyOrId=Enchantment.class.getMethod("getKey");
         byIdOrKey = Enchantment.class.getDeclaredField("byKey");
      }catch (Exception e1) {
         try{
            getKeyOrId=Enchantment.class.getMethod("getId");
            byIdOrKey = Enchantment.class.getDeclaredField("byId");
         }catch (Exception ignored) {}
      }
      if(getKeyOrId == null || byIdOrKey == null) return;
      getKeyOrId.setAccessible(true);
      byIdOrKey.setAccessible(true);
      try {
         HashMap<?,?> idOrKey = (HashMap<?,?>) byIdOrKey.get(null);
         Field byName = Enchantment.class.getDeclaredField("byName");
         byName.setAccessible(true);
         HashMap<?,?> bName = (HashMap<?,?>) byName.get(null);
         for(DynamicEnchantment enchantment : getEnchantments()) {
            idOrKey.remove(getKeyOrId.invoke(enchantment.getEnchantment()));
            bName.remove(enchantment.getEnchantment().getName());
         }
      }catch (Exception e1) {
         e1.printStackTrace();
      }
   }

   //didn't want the user to have to call "unregisterEnchantments" in onDisabled :P
   @EventHandler void onDisable(PluginDisableEvent e) {
      if(!e.getPlugin().getName().equals(plugin.getName()))return;
      unregisterEnchantments();
   }

   //Fishing support ig
   @EventHandler void onFish(PlayerFishEvent e) {
      Random random = ThreadLocalRandom.current();
      if(!(e.getCaught() instanceof Item)) return;
      Item item = (Item) e.getCaught();
      ItemStack it = item.getItemStack();
      if(it.getType() != Material.ENCHANTED_BOOK) {
         ItemMeta im = it.getItemMeta();
         if(im==null)return;
         for(DynamicEnchantment enchantment : enchants) {
            if(!enchantment.canEnchantItem(it))continue;
            if(((random.nextInt(100)+1)/100d) > enchantment.getChance()) continue;
            im.addEnchant(enchantment.getEnchantment(), enchantment.getStartLevel() +
               (enchantment.getMaxLevel() > enchantment.getStartLevel() ? random.nextInt(enchantment.getMaxLevel()-1) : 0), true);
         }
         it.setItemMeta(im);
         updateEnchants(it);
         item.setItemStack(it);
         return;
      }
      EnchantmentStorageMeta storage = (EnchantmentStorageMeta) it.getItemMeta();
      for(DynamicEnchantment enchantment : enchants) {
         if(((random.nextInt(100)+1)/100d) > enchantment.getChance()) continue;
         storage.addStoredEnchant(enchantment.getEnchantment(), enchantment.getStartLevel() +
            (enchantment.getMaxLevel() > enchantment.getStartLevel() ? random.nextInt(enchantment.getMaxLevel()-1) : 0), true);
      }
      it.setItemMeta(storage);
      updateEnchants(it);
      item.setItemStack(it);
   }

   @EventHandler void onEnchantItem(EnchantItemEvent e) {
      Random random = ThreadLocalRandom.current();
      A:for(DynamicEnchantment enchantment : enchants) {
         if(enchantment.getLevels() == null) continue;
         if(!enchantment.canEnchantItem(e.getItem()))continue;
         if(!enchantment.getLevels().accepts(e.getExpLevelCost())) continue;
         for(Enchantment enchantment1 : e.getEnchantsToAdd().keySet()) if(enchantment.conflictsWith(enchantment1))continue A;
         if(((random.nextInt(100)+1)/100d) > enchantment.getChance()) continue;
         e.getEnchantsToAdd().put(enchantment.getEnchantment(), enchantment.getStartLevel() +
            (enchantment.getMaxLevel() > enchantment.getStartLevel() ? random.nextInt(enchantment.getMaxLevel()-1) : 0));
      }
      Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> updateEnchants(e.getItem()));
   }

   public void updateEnchants(ItemStack item) {
      if(!shouldUpdate(item))return;
      ItemMeta im = item.getItemMeta();
      if(im==null)return;
      List<String> lore = new ArrayList<>();
      if(im.getLore() != null)
         for(String s : im.getLore()) {
            if(!s.startsWith("§-§")) lore.add(s);
         }
      im.getEnchants().forEach((e, l) -> {
         String ench = "§-" + fixName(true,e) + (l == 1 && e.getMaxLevel() == 1 ? " " + EnchantUtil.romanFromInt(l) : "");
         if(!lore.contains(ench)) lore.add(ench);
      });
      if(im instanceof EnchantmentStorageMeta) {
         ((EnchantmentStorageMeta) im).getStoredEnchants().forEach((e, l) -> {
            String ench = "§-" + fixName(true,e) + (l == 1 && e.getMaxLevel() == 1 ? " " + EnchantUtil.romanFromInt(l) : "");
            if(!lore.contains(ench)) lore.add(ench);
         });
      }
      im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
      im.setLore(lore);
      item.setItemMeta(im);
   }

   public boolean shouldUpdate(ItemStack item) {
      if(item == null) return false;
      ItemMeta im = item.getItemMeta();
      if(im==null)return false;
      List<String> lore = im.getLore() == null ? new ArrayList<>() : im.getLore();
      boolean shouldUpdate = false;
      for(Map.Entry<Enchantment, Integer> enchantmentIntegerEntry : im.getEnchants().entrySet()) {
         Enchantment e = enchantmentIntegerEntry.getKey();
         int l = enchantmentIntegerEntry.getValue();
         String ench = "§-" + fixName(true,e) + (l == 1 && e.getMaxLevel() == 1 ? " " + EnchantUtil.romanFromInt(l) : "");
         if(!lore.contains(ench)) {
            shouldUpdate = true;
            break;
         }
      }
      if(im instanceof EnchantmentStorageMeta) {
         for(Map.Entry<Enchantment, Integer> enchantmentIntegerEntry : ((EnchantmentStorageMeta) im).getStoredEnchants().entrySet()) {
            Enchantment e = enchantmentIntegerEntry.getKey();
            int l = enchantmentIntegerEntry.getValue();
            String ench = "§-" + fixName(true,e) + (l == 1 && e.getMaxLevel() == 1 ? " " + EnchantUtil.romanFromInt(l) : "");
            if(!lore.contains(ench)) {
               shouldUpdate = true;
               break;
            }
         }
      }
      return shouldUpdate;
   }

   @EventHandler void onAnvil(PrepareAnvilEvent e) {
      AnvilInventory inventory = e.getInventory();
      ItemStack slot1 = inventory.getItem(0);
      ItemStack slot2 = inventory.getItem(1);
      ItemStack result = e.getResult();
      if(slot1 != null && slot2 != null) {
         if(slot1.getType() != slot2.getType()) return;
         ItemMeta im1 = slot1.getItemMeta(), im2 = slot2.getItemMeta();
         if(im1 == null || im2 == null) return;
         Map<Enchantment, Integer> enchants = new HashMap<>(im1.getEnchants());
         im2.getEnchants().forEach((en, l) -> {
            DynamicEnchantment d = fromEnchantment(en);
            if(enchants.containsKey(en)) {
               if(enchants.get(en).intValue() == l && (d == null || d.allowsAnvil())) enchants.put(en, (l + 1) <= en.getMaxLevel() ? l + 1 : l);
               else if(enchants.get(en) < l) enchants.put(en, l);
            } else enchants.put(en, l);
         });
         if(result != null && result.hasItemMeta() && (!im1.getEnchants().entrySet().containsAll(enchants.entrySet())||
            (im1.hasDisplayName()&&inventory.getRenameText()!=null&&inventory.getRenameText().equals(im1.getDisplayName())))) {
            ItemMeta im = result.getItemMeta();
            im.getEnchants().keySet().forEach(im::removeEnchant);
            result.setItemMeta(im);
            updateEnchants(result);
            im = result.getItemMeta();
            for(Map.Entry<Enchantment, Integer> entry : enchants.entrySet())
               im.addEnchant(entry.getKey(), entry.getValue(), true);
            result.setItemMeta(im);
            updateEnchants(result);
            if(!result.isSimilar(e.getResult())) {
               e.setResult(result);
               Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> e.setResult(result));
               for(HumanEntity viewer : inventory.getViewers())
                  if(viewer instanceof Player) ((Player) viewer).updateInventory();
            }
         }
      }
   }

   public DynamicEnchantment fromEnchantment(Enchantment enchantment) {
      for(DynamicEnchantment enchant : getEnchantments())
         if(enchant.getEnchantment().equals(enchantment)) return enchant;
      return null;
   }

   public boolean hasEnchant(ItemStack item, DynamicEnchantment dynamicEnchantment) {
      return item.containsEnchantment(dynamicEnchantment.getEnchantment());
   }

   public String fixName(boolean color, Enchantment enchantment) {
      for(DynamicEnchantment enchant : getEnchantments())
         if(enchant.getEnchantment().equals(enchantment)) return (!enchant.getName().startsWith("§")&&color ? "§7":"")+enchant.getName();
      return (color?"§7":"") + EnchantUtil.ENCHANT_NAMES_CACHE.getOrDefault(enchantment, EnchantUtil.getNameFromCaps(enchantment.getName()));
   }



   public void load(JavaPlugin plugin) {
      if(COMPILER == null) {
         try {
            COMPILER = DynamicJavaCompiler.newInstance(plugin.getClass().getClassLoader())
               .useOptions("-cp", ((File)getFile.get(plugin)).getPath() + ":" + System.getProperty("java.class.path"));
         } catch (IllegalAccessException e) {
            e.printStackTrace();
         }
      }
      try {
         enchantWrapper = COMPILER.compile(getClass().getPackage().getName()+".DynamicEnchant", DynamicEnchantAsString.DYNAMIC_ENCHANTMENT);
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   private List<DynamicEnchantment> enchants;

   public List<DynamicEnchantment> getEnchantments() {
      if(enchants == null) enchants = new ArrayList<>();
      return enchants;
   }

   public DynamicEnchantment getDynamicEnchantment(String name) {
      for (DynamicEnchantment ench : getEnchantments()) {
         if (ench.getName().equalsIgnoreCase(name) ||
            ench.getLocalizedName().equalsIgnoreCase(ChatColor.stripColor(name))||
            ench.getEnchantment().getName().equalsIgnoreCase(ChatColor.stripColor(name))) {
            return ench;
         }
      }
      return null;
   }

   public void addDynamicEnchantments(DynamicEnchantment... enchantments) {
      for (DynamicEnchantment e : enchantments) {
         if (!getEnchantments().contains(e)) {
            getEnchantments().add(e);
            registerBukkitEnchantment(e);
         }
      }
   }

   private void registerBukkitEnchantment(DynamicEnchantment dynamicEnchantment) {
      if(enchantWrapper == null)  {
         load(plugin);
         if(enchantWrapper == null) return;
      }
      Enchantment enchantment;
      try {
         Constructor<?> con = enchantWrapper.getConstructor(DynamicEnchantment.class);
         enchantment = (Enchantment) con.newInstance(dynamicEnchantment);
         Field f = Enchantment.class.getDeclaredField("acceptingNew");
         boolean isAccess = f.isAccessible();
         f.setAccessible(true);
         f.set(null, true);
         Enchantment.registerEnchantment(enchantment);
         f.setAccessible(isAccess);
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

}
