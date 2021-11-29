package dev.perryplaysmc.dynamicenchantments.enchantment;

import dev.perryplaysmc.dynamicenchantments.Version;
import org.bukkit.enchantments.Enchantment;

import java.util.HashMap;

public class EnchantUtil {

   public static final HashMap<Enchantment, String> ENCHANT_NAMES_CACHE = new HashMap<Enchantment,String>() {{
      {
         put(Enchantment.PROTECTION_ENVIRONMENTAL, "Protection");
         put(Enchantment.PROTECTION_FIRE, "Fire Protection");
         put(Enchantment.PROTECTION_FALL, "Feather Falling");
         put(Enchantment.PROTECTION_EXPLOSIONS, "Blast Protection");
         put(Enchantment.PROTECTION_PROJECTILE, "Projectile Protection");
         put(Enchantment.OXYGEN, "Respiration");
         put(Enchantment.WATER_WORKER, "Aqua Affinity");
         put(Enchantment.THORNS, "Thorns");
         put(Enchantment.DAMAGE_ALL, "Sharpness");
         put(Enchantment.DAMAGE_UNDEAD, "Smite");
         put(Enchantment.DAMAGE_ARTHROPODS, "Bane of Arthropods");
         put(Enchantment.KNOCKBACK, "Knockback");
         put(Enchantment.FIRE_ASPECT, "Fire Aspect");
         put(Enchantment.LOOT_BONUS_MOBS, "Looting");
         put(Enchantment.DIG_SPEED, "Efficiency");
         put(Enchantment.SILK_TOUCH, "Silk Touch");
         put(Enchantment.DURABILITY, "Unbreaking");
         put(Enchantment.LOOT_BONUS_BLOCKS, "Fortune");
         put(Enchantment.ARROW_DAMAGE, "Power");
         put(Enchantment.ARROW_KNOCKBACK, "Punch");
         put(Enchantment.ARROW_FIRE, "Flame");
         put(Enchantment.ARROW_INFINITE, "Infinity");
      }
      if(Version.isCurrentOrHigher(Version.v1_6_R2)){
         put(Version.current().is(Version.v1_6_R3) ? Enchantment.getByName("LUCK_OF_THE_SEA") : Enchantment.LUCK, "Luck of the sea");
         put(Enchantment.LURE, "Lure");
      }
      if(Version.isCurrentHigher(Version.v1_7)) {
         put(Enchantment.DEPTH_STRIDER, "Depth Strider");
      }
      if(Version.isCurrentHigher(Version.v1_8)) {
         put(Enchantment.MENDING, "Mending");
         put(Enchantment.FROST_WALKER, "Frost Walker");
      }
      if(Version.isCurrentHigher(Version.v1_10)) {
         put(Enchantment.VANISHING_CURSE, "Curse of Vanishing");
         put(Enchantment.BINDING_CURSE, "Curse of Binding");
         put(Enchantment.SWEEPING_EDGE, "Sweeping Edge");
      }
      if(Version.isCurrentHigher(Version.v1_12)) {
         put(Enchantment.LOYALTY, "Loyalty");
         put(Enchantment.IMPALING, "Curse of Binding");
         put(Enchantment.RIPTIDE, "Riptide");
         put(Enchantment.CHANNELING, "Channeling");
      }
      if(Version.isCurrentHigher(Version.v1_13)) {
         put(Enchantment.MULTISHOT, "Multishot");
         put(Enchantment.QUICK_CHARGE, "Quick Charge");
         put(Enchantment.PIERCING, "Piercing");
      }
      if(Version.isCurrentHigher(Version.v1_15)) put(Enchantment.SOUL_SPEED, "Soul Speed");
   }};


   final static int[] numbers = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
   final static String[] letters = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

   public static String romanFromInt(int N) {
      StringBuilder roman = new StringBuilder();
      for(int i = 0; i < numbers.length; i++) while (N >= numbers[i]) {
         roman.append(letters[i]);
         N -= numbers[i];
      }

      return roman.toString();
   }

   public static String getNameFromCaps(String e) {
      String f = e.replace(" ", "_");
      String a = f.charAt(0) + f.substring(1).toLowerCase().split("_")[0];
      if(f.contains("_")) {
         for(int z = 1; z < f.split("_").length; z++) {
            String p = f.split("_")[z];
            a+=" " + (p.charAt(0)+"").toUpperCase() + p.substring(1).toLowerCase();
         }
      }
      return a;
   }

}
