package dev.perryplaysmc.dynamicenchantments.enchantment;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public enum EnchantTarget {


   //<editor-fold desc="Armor">
   //ARMOR

   ARMOR_HELMETS_ALL{
      @Override
      public boolean includes(Material mat) {
         for(EnchantTarget t : values()) {
            if(t == this) continue;
            if(t.name().contains("ARMOR_HELMET") && t.includes(mat)) return true;
         }
         return false;
      }
   },

   ARMOR_CHESTPLATES_ALL{
      @Override
      public boolean includes(Material mat) {
         for(EnchantTarget t : values()) {
            if(t == this) continue;
            if(t.name().contains("ARMOR_HELMET") && t.includes(mat)) return true;
         }
         return false;
      }
   },

   ARMOR_LEGGINGS_ALL{
      @Override
      public boolean includes(Material mat) {
         for(EnchantTarget t : values()) {
            if(t == this) continue;
            if(t.name().contains("ARMOR_HELMET") && t.includes(mat)) return true;
         }
         return false;
      }
   },

   ARMOR_BOOTS_ALL{
      @Override
      public boolean includes(Material mat) {
         for(EnchantTarget t : values()) {
            if(t == this) continue;
            if(t.name().contains("ARMOR_HELMET") && t.includes(mat)) return true;
         }
         return false;
      }
   },

   ARMOR_ALL{
      @Override
      public boolean includes(Material mat) {
         for(EnchantTarget t : values()) {
            if(t == this) continue;
            if(t.name().contains("ARMOR") && t.includes(mat)) return true;
         }
         return false;
      }
   },



   //NETHERITE
   NETHERITE_ARMOR_HELMET{
      @Override
      public boolean includes(Material mat) {
         if(MATERIALS.get("NETHERITE_HELMET") == null) return false;
         return mat == MATERIALS.get("NETHERITE_HELMET");
      }
   },
   NETHERITE_ARMOR_CHESTPLATE{
      @Override
      public boolean includes(Material mat) {
         if(MATERIALS.get("NETHERITE_CHESTPLATE") == null) return false;
         return mat == MATERIALS.get("NETHERITE_CHESTPLATE");
      }
   },
   NETHERITE_ARMOR_LEGGINGS{
      @Override
      public boolean includes(Material mat) {
         if(MATERIALS.get("NETHERITE_LEGGINGS") == null) return false;
         return mat == MATERIALS.get("NETHERITE_LEGGINGS");
      }
   },
   NETHERITE_ARMOR_BOOTS{
      @Override
      public boolean includes(Material mat) {
         if(MATERIALS.get("NETHERITE_BOOTS") == null) return false;
         return mat == MATERIALS.get("NETHERITE_BOOTS");
      }
   },
   NETHERITE_ARMOR_ALL{
      @Override
      public boolean includes(Material mat) {
         for(EnchantTarget t : values()) {
            if(t == this) continue;
            if(t.name().contains("NETHERITE_ARMOR") && t.includes(mat)) return true;
         }
         return false;
      }
   },

   //DIAMOND
   DIAMOND_ARMOR_HELMET{
      @Override
      public boolean includes(Material mat) {
         return mat == Material.DIAMOND_HELMET;
      }
   },
   DIAMOND_ARMOR_CHESTPLATE{
      @Override
      public boolean includes(Material mat) {
         return mat == Material.DIAMOND_CHESTPLATE;
      }
   },
   DIAMOND_ARMOR_LEGGINGS{
      @Override
      public boolean includes(Material mat) {
         return mat == Material.DIAMOND_LEGGINGS;
      }
   },
   DIAMOND_ARMOR_BOOTS{
      @Override
      public boolean includes(Material mat) {
         return mat == Material.DIAMOND_BOOTS;
      }
   },
   DIAMOND_ARMOR_ALL{
      @Override
      public boolean includes(Material mat) {
         for(EnchantTarget t : values()) {
            if(t == this) continue;
            if(t.name().contains("DIAMOND_ARMOR") && t.includes(mat)) return true;
         }
         return false;
      }
   },

   //IRON
   IRON_ARMOR_HELMET{
      @Override
      public boolean includes(Material mat) {
         return mat == Material.IRON_HELMET;
      }
   },
   IRON_ARMOR_CHESTPLATE{
      @Override
      public boolean includes(Material mat) {
         return mat == Material.IRON_CHESTPLATE;
      }
   },
   IRON_ARMOR_LEGGINGS{
      @Override
      public boolean includes(Material mat) {
         return mat == Material.IRON_LEGGINGS;
      }
   },
   IRON_ARMOR_BOOTS{
      @Override
      public boolean includes(Material mat) {
         return mat == Material.IRON_BOOTS;
      }
   },
   IRON_ARMOR_ALL{
      @Override
      public boolean includes(Material mat) {
         for(EnchantTarget t : values()) {
            if(t == this) continue;
            if(t.name().contains("IRON_ARMOR") && t.includes(mat)) return true;
         }
         return false;
      }
   },

   //GOLD
   GOLDEN_ARMOR_HELMET{
      @Override
      public boolean includes(Material mat) {
         return mat == Material.GOLDEN_HELMET;
      }
   },
   GOLDEN_ARMOR_CHESTPLATE{
      @Override
      public boolean includes(Material mat) {
         return mat == Material.GOLDEN_CHESTPLATE;
      }
   },
   GOLDEN_ARMOR_LEGGINGS{
      @Override
      public boolean includes(Material mat) {
         return mat == Material.GOLDEN_LEGGINGS;
      }
   },
   GOLDEN_ARMOR_BOOTS{
      @Override
      public boolean includes(Material mat) {
         return mat == Material.GOLDEN_BOOTS;
      }
   },
   GOLDEN_ARMOR_ALL{
      @Override
      public boolean includes(Material mat) {
         for(EnchantTarget t : values()) {
            if(t == this) continue;
            if(t.name().contains("GOLDEN_ARMOR") && t.includes(mat)) return true;
         }
         return false;
      }
   },

   //CHAINMAIL
   CHAINMAIL_ARMOR_HELMET{
      @Override
      public boolean includes(Material mat) {
         return mat == Material.CHAINMAIL_HELMET;
      }
   },
   CHAINMAIL_ARMOR_CHESTPLATE{
      @Override
      public boolean includes(Material mat) {
         return mat == Material.CHAINMAIL_CHESTPLATE;
      }
   },
   CHAINMAIL_ARMOR_LEGGINGS{
      @Override
      public boolean includes(Material mat) {
         return mat == Material.CHAINMAIL_LEGGINGS;
      }
   },
   CHAINMAIL_ARMOR_BOOTS{
      @Override
      public boolean includes(Material mat) {
         return mat == Material.CHAINMAIL_BOOTS;
      }
   },
   CHAINMAIL_ARMOR_ALL{
      @Override
      public boolean includes(Material mat) {
         for(EnchantTarget t : values()) {
            if(t == this) continue;
            if(t.name().contains("CHAINMAIL_ARMOR") && t.includes(mat)) return true;
         }
         return false;
      }
   },

   //LEATHER
   LEATHER_ARMOR_HELMET{
      @Override
      public boolean includes(Material mat) {
         return mat == Material.LEATHER_HELMET;
      }
   },
   LEATHER_ARMOR_CHESTPLATE{
      @Override
      public boolean includes(Material mat) {
         return mat == Material.LEATHER_CHESTPLATE;
      }
   },
   LEATHER_ARMOR_LEGGINGS{
      @Override
      public boolean includes(Material mat) {
         return mat == Material.LEATHER_LEGGINGS;
      }
   },
   LEATHER_ARMOR_BOOTS{
      @Override
      public boolean includes(Material mat) {
         return mat == Material.LEATHER_BOOTS;
      }
   },
   LEATHER_ARMOR_ALL{
      @Override
      public boolean includes(Material mat) {
         for(EnchantTarget t : values()) {
            if(t == this) continue;
            if(t.name().contains("LEATHER_ARMOR") && t.includes(mat)) return true;
         }
         return false;
      }
   },

   //</editor-fold>

   //<editor-fold desc="Tools">
   //TOOLS

   NETHERITE_TOOLS{
      @Override
      public boolean includes(Material mat) {
         for(EnchantTarget t : values()) {
            if(t == this) continue;
            if(t.name().contains("NETHERITE")&&!t.name().contains("ARMOR")&&t.includes(mat)) return true;
         }
         return false;
      }
   },
   DIAMOND_TOOLS{
      @Override
      public boolean includes(Material mat) {
         for(EnchantTarget t : values()) {
            if(t == this) continue;
            if(t.name().contains("DIAMOND")&&!t.name().contains("ARMOR")&&t.includes(mat)) return true;
         }
         return false;
      }
   },
   GOLD_TOOLS{
      @Override
      public boolean includes(Material mat) {
         for(EnchantTarget t : values()) {
            if(t == this) continue;
            if(t.name().contains("GOLD")&&!t.name().contains("ARMOR")&&t.includes(mat)) return true;
         }
         return false;
      }
   },
   IRON_TOOLS{
      @Override
      public boolean includes(Material mat) {
         for(EnchantTarget t : values()) {
            if(t == this) continue;
            if(t.name().contains("IRON")&&!t.name().contains("ARMOR")&&t.includes(mat)) return true;
         }
         return false;
      }
   },
   STONE_TOOLS{
      @Override
      public boolean includes(Material mat) {
         for(EnchantTarget t : values()) {
            if(t == this) continue;
            if(t.name().contains("STONE")&&!t.name().contains("ARMOR")&&t.includes(mat)) return true;
         }
         return false;
      }
   },
   WOOD_TOOLS{
      @Override
      public boolean includes(Material mat) {
         for(EnchantTarget t : values()) {
            if(t == this) continue;
            if(t.name().contains("WOOD")&&!t.name().contains("ARMOR")&&t.includes(mat)) return true;
         }
         return false;
      }
   },



   //NETHERITE
   NETHERITE_SWORD{
      @Override
      public boolean includes(Material mat) {
         if(MATERIALS.get("NETHERITE_SWORD") == null) return false;
         return mat == MATERIALS.get("NETHERITE_SWORD");
      }
   },
   NETHERITE_AXE{
      @Override
      public boolean includes(Material mat) {
         if(MATERIALS.get("NETHERITE_AXE") == null) return false;
         return mat == MATERIALS.get("NETHERITE_AXE");
      }
   },
   NETHERITE_SHOVEL{
      @Override
      public boolean includes(Material mat) {
         if(MATERIALS.get("NETHERITE_SHOVEL") == null) return false;
         return mat == MATERIALS.get("NETHERITE_SHOVEL");
      }
   },
   NETHERITE_HOE{
      @Override
      public boolean includes(Material mat) {
         if(MATERIALS.get("NETHERITE_HOE") == null) return false;
         return mat == MATERIALS.get("NETHERITE_HOE");
      }
   },
   NETHERITE_PICKAXE{
      @Override
      public boolean includes(Material mat) {
         if(MATERIALS.get("NETHERITE_PICKAXE") == null) return false;
         return mat == MATERIALS.get("NETHERITE_PICKAXE");
      }
   },

   //DIAMOND
   DIAMOND_SWORD{
      @Override
      public boolean includes(Material mat) {
         return mat == Material.DIAMOND_SWORD;
      }
   },
   DIAMOND_AXE{
      @Override
      public boolean includes(Material mat) {
         return mat == Material.DIAMOND_AXE;
      }
   },
   DIAMOND_SHOVEL{
      @Override
      public boolean includes(Material mat) {
         return mat == Material.DIAMOND_SHOVEL;
      }
   },
   DIAMOND_HOE{
      @Override
      public boolean includes(Material mat) {
         return mat == Material.DIAMOND_HOE;
      }
   },
   DIAMOND_PICKAXE{
      @Override
      public boolean includes(Material mat) {
         return mat == Material.DIAMOND_PICKAXE;
      }
   },

   //IRON
   IRON_SWORD{
      @Override
      public boolean includes(Material mat) {
         return mat == Material.IRON_SWORD;
      }
   },
   IRON_AXE{
      @Override
      public boolean includes(Material mat) {
         return mat == Material.IRON_AXE;
      }
   },
   IRON_SHOVEL{
      @Override
      public boolean includes(Material mat) {
         return mat == Material.IRON_SHOVEL;
      }
   },
   IRON_HOE{
      @Override
      public boolean includes(Material mat) {
         return mat == Material.IRON_HOE;
      }
   },
   IRON_PICKAXE{
      @Override
      public boolean includes(Material mat) {
         return mat == Material.IRON_PICKAXE;
      }
   },

   //GOLD
   GOLDEN_SWORD{
      @Override
      public boolean includes(Material mat) {
         return mat == Material.GOLDEN_SWORD;
      }
   },
   GOLDEN_AXE{
      @Override
      public boolean includes(Material mat) {
         return mat == Material.GOLDEN_AXE;
      }
   },
   GOLDEN_SHOVEL{
      @Override
      public boolean includes(Material mat) {
         return mat == Material.GOLDEN_SHOVEL;
      }
   },
   GOLDEN_HOE{
      @Override
      public boolean includes(Material mat) {
         return mat == Material.GOLDEN_HOE;
      }
   },
   GOLDEN_PICKAXE{
      @Override
      public boolean includes(Material mat) {
         return mat == Material.GOLDEN_PICKAXE;
      }
   },

   //STONE
   STONE_SWORD{
      @Override
      public boolean includes(Material mat) {
         return mat == Material.STONE_SWORD;
      }
   },
   STONE_AXE{
      @Override
      public boolean includes(Material mat) {
         return mat == Material.STONE_AXE;
      }
   },
   STONE_SHOVEL{
      @Override
      public boolean includes(Material mat) {
         return mat == Material.STONE_SHOVEL;
      }
   },
   STONE_HOE{
      @Override
      public boolean includes(Material mat) {
         return mat == Material.STONE_HOE;
      }
   },
   STONE_PICKAXE{
      @Override
      public boolean includes(Material mat) {
         return mat == Material.STONE_PICKAXE;
      }
   },

   //WOODEN
   WOODEN_SWORD{
      @Override
      public boolean includes(Material mat) {
         return mat == Material.WOODEN_SWORD;
      }
   },
   WOODEN_AXE{
      @Override
      public boolean includes(Material mat) {
         return mat == Material.WOODEN_AXE;
      }
   },
   WOODEN_SHOVEL{
      @Override
      public boolean includes(Material mat) {
         return mat == Material.WOODEN_SHOVEL;
      }
   },
   WOODEN_HOE{
      @Override
      public boolean includes(Material mat) {
         return mat == Material.WOODEN_HOE;
      }
   },
   WOODEN_PICKAXE{
      @Override
      public boolean includes(Material mat) {
         return mat == Material.WOODEN_PICKAXE;
      }
   },


   //</editor-fold>

   //<editor-fold desc="Misc">
   //MISC

   ALL_NETHERITE {
      @Override
      public boolean includes(Material mat) {
         for(EnchantTarget t : values()) {
            if(t == this) continue;
            if(t.name().contains("NETHERITE") && t.includes(mat))return true;
         }
         return false;
      }
   },
   ALL_DIAMOND {
      @Override
      public boolean includes(Material mat) {
         for(EnchantTarget t : values()) {
            if(t == this) continue;
            if(t.name().contains("DIAMOND") && t.includes(mat))return true;
         }
         return false;
      }
   },
   ALL_GOLD {
      @Override
      public boolean includes(Material mat) {
         for(EnchantTarget t : values()) {
            if(t == this) continue;
            if(t.name().contains("GOLD") && t.includes(mat))return true;
         }
         return false;
      }
   },
   ALL_IRON {
      @Override
      public boolean includes(Material mat) {
         for(EnchantTarget t : values()) {
            if(t == this) continue;
            if(t.name().contains("IRON") && t.includes(mat))return true;
         }
         return false;
      }
   },
   ALL_STONE {
      @Override
      public boolean includes(Material mat) {
         for(EnchantTarget t : values()) {
            if(t == this) continue;
            if(t.name().contains("STONE") && t.includes(mat))return true;
         }
         return false;
      }
   },
   ALL_WOOD {
      @Override
      public boolean includes(Material mat) {
         for(EnchantTarget t : values()) {
            if(t == this) continue;
            if(t.name().contains("WOOD") && t.includes(mat))return true;
         }
         return false;
      }
   },

   BOW {
      public boolean includes(Material item) {
         return item == Material.BOW;
      }
   },

   FISHING_ROD {
      public boolean includes(Material item) {
         return item == Material.FISHING_ROD;
      }
   },

   BREAKABLE {
      public boolean includes(Material item) {
         return item.getMaxDurability() > 0 && item.getMaxStackSize() == 1;
      }
   },

   WEARABLE_COSMETIC {
      public boolean includes(Material item) {
         return ARMOR_ALL.includes(item)
            || item == Material.ELYTRA
            || item == Material.PUMPKIN
            || item == Material.CARVED_PUMPKIN
            || item == Material.JACK_O_LANTERN
            || item == Material.SKELETON_SKULL
            || item == Material.WITHER_SKELETON_SKULL
            || item == Material.ZOMBIE_HEAD
            || item == Material.PLAYER_HEAD
            || item == Material.CREEPER_HEAD
            || item == Material.DRAGON_HEAD;
      }
   },

   TRIDENT {
      public boolean includes(Material item) {
         return item == Material.TRIDENT;
      }
   },

   WEAPON {
      public boolean includes(Material item) {
         return item == Material.WOODEN_SWORD || item == Material.STONE_SWORD || item == Material.IRON_SWORD || item == Material.DIAMOND_SWORD || item == Material.GOLDEN_SWORD;
      }
   },

   ELYTRA {
      public boolean includes(Material item) {
         return item == Material.ELYTRA;
      }
   },

   ALL {
      @Override
      public boolean includes(Material mat) {
         for(EnchantTarget t : values()) {
            if(t == this) continue;
            if(t.includes(mat)) return true;
         }
         return false;
      }
   };

   //</editor-fold>

   private static final HashMap<String, Material> MATERIALS = new HashMap<String, Material>() {{
      for(Material mat : Material.values())
         put(mat.name(),mat);
   }};

   public boolean includes(ItemStack item) {
      if(item == null) return false;
      return this.includes(item.getType());
   }

   public abstract boolean includes(Material mat);

}
