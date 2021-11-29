package dev.perryplaysmc.dynamicenchantments.enchantment;

import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Creator: PerryPlaysMC
 * Created: 02/2021
 **/

public class DynamicEnchantment {

   private EnchantTarget target;

   private final String name;
   private final int max;
   private final Levels levels;
   private final boolean isTreasure;
   private final boolean isCursed;
   private boolean allowsAnvil;
   private final float chance;
   //Don't fricken set it to null or some other enchantment class with reflection, that's stupid and useless.
   private Enchantment enchantment;
   private final DynamicEnchantmentHandler enchantmentHandler;
   private List<Enchantment> conflicts;

   public DynamicEnchantment(DynamicEnchantmentHandler enchantmentHandler, String name, int maxLevel, float chance, Enchantment... conflicts) {
      this(enchantmentHandler, name, null, maxLevel, chance, EnchantTarget.ALL, true, conflicts);
   }

   public DynamicEnchantment(DynamicEnchantmentHandler enchantmentHandler, String name, Levels levels, int maxLevel, float chance, Enchantment... conflicts) {
      this(enchantmentHandler, name, levels, maxLevel, chance, EnchantTarget.ALL, conflicts);
   }

   public DynamicEnchantment(DynamicEnchantmentHandler enchantmentHandler, String name, Levels levels, int maxLevel, float chance, EnchantTarget target, Enchantment... conflicts) {
      this(enchantmentHandler, name, levels, maxLevel, chance, target, false, conflicts);
   }

   public DynamicEnchantment(DynamicEnchantmentHandler enchantmentHandler, String name, Levels levels, int maxLevel, float chance, EnchantTarget target, boolean isTreasure, Enchantment... conflicts) {
      this(enchantmentHandler, name, levels, maxLevel, chance, target, isTreasure, false, conflicts);
   }

   public DynamicEnchantment(DynamicEnchantmentHandler enchantmentHandler, String name, Levels levels, int maxLevel, float chance, EnchantTarget target, boolean isTreasure, boolean isCursed, Enchantment... conflicts) {
      this.name = ChatColor.translateAlternateColorCodes('&', name);
      this.max = maxLevel;
      this.target = target;
      this.isTreasure = isTreasure;
      this.isCursed = isCursed;
      this.chance = chance;
      this.levels = levels;
      this.enchantmentHandler = enchantmentHandler;
      addConflictingEnchantments(conflicts);
      enchantmentHandler.addDynamicEnchantments(this);
   }

   public DynamicEnchantment addConflictingEnchantments(Enchantment... enchants) {
      if(conflicts == null) conflicts = new ArrayList<>();
      if(enchants == null) return this;
      for(Enchantment e : enchants) if(!conflicts.contains(e)) conflicts.add(e);
      return this;
   }

   public DynamicEnchantment addConflictingEnchantments(DynamicEnchantment... enchants) {
      if(conflicts == null) conflicts = new ArrayList<>();
      if(enchants == null) return this;
      for(DynamicEnchantment de : enchants) if(!conflicts.contains(de.getEnchantment())) conflicts.add(de.getEnchantment());
      return this;
   }

   public float getChance() {
      return chance;
   }

   public String getLocalizedName() {
      return ChatColor.stripColor(getName().toLowerCase());
   }

   //Don't try to reset this, it's a dumb idea
   public DynamicEnchantment setEnchantment(Enchantment enchantment) throws Exception {
      if(this.enchantment == null) this.enchantment = enchantment;
      else throw new Exception("You cannot reset the enchantment class!");
      return this;
   }

   public DynamicEnchantment allowAnvil() {
      allowsAnvil = true;
      return this;
   }

   public boolean allowsAnvil() {
      return allowsAnvil;
   }

   public Enchantment getEnchantment() {
      return enchantment;
   }

   public String getName() {
      return name;
   }

   public int getMaxLevel()
   {
      return max;
   }

   public int getStartLevel()
   {
      return 1;
   }

   public EnchantmentTarget getItemTarget()
   {
      return EnchantmentTarget.ALL;
   }

   public EnchantTarget getTarget()
   {
      return target;
   }

   public DynamicEnchantment setTarget(EnchantTarget target) {
      this.target = target;
      return this;
   }

   public boolean isTreasure()
   {
      return isTreasure;
   }

   public boolean isCursed()
   {
      return isCursed;
   }

   public boolean conflictsWith(Enchantment enchantment) {
      if(conflicts == null) return false;
      return conflicts.contains(enchantment);
   }

   public boolean canEnchantItem(ItemStack itemStack) {
      return target.includes(itemStack);
   }

   public Levels getLevels() {
      return levels;
   }

   public DynamicEnchantmentHandler getEnchantmentHandler() {
      return enchantmentHandler;
   }

   @Override
   public String toString() {
      return "DynamicEnchantment{" +
         "name='" + name + '\'' +
         ", max=" + max +
         ", levels=" + levels +
         ", target=" + target +
         ", isTreasure=" + isTreasure +
         ", isCursed=" + isCursed +
         ", chance=" + chance +
         ", enchantment=" + enchantment +
         ", conflicts=" + conflicts +
         '}';
   }
}

