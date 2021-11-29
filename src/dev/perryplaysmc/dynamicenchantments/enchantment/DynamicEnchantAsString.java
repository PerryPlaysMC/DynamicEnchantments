package dev.perryplaysmc.dynamicenchantments.enchantment;

import dev.perryplaysmc.dynamicenchantments.Version;
import org.intellij.lang.annotations.Language;


/**
 * Creator: PerryPlaysMC
 * Created: 02/2021
 **/


//DON'T YOU FRICKEN DARE MESS WITH THIS CLASS, IF YOU DO, YOU'RE BASICALLY FKED, K? JUST LEAVE IT ALONE.
class DynamicEnchantAsString {

    @Language("java")
    static final String DYNAMIC_ENCHANTMENT =
            "package "+DynamicEnchantAsString.class.getPackage().getName()+";"
                    + "\n\nimport org.bukkit.enchantments.Enchantment;" +
                    (Version.isCurrentHigher(Version.v1_12) ? "\nimport org.bukkit.NamespacedKey;" : "")
                    + "\nimport org.bukkit.inventory.ItemStack;"
                    + "\nimport org.bukkit.enchantments.EnchantmentTarget;"
                    + "\nimport dev.perryplaysmc.dynamicenchantments.enchantment.DynamicEnchantment;"
                    + "\n\npublic class DynamicEnchant extends Enchantment {"
                    + (Version.isCurrentHigher(Version.v1_12) ?"":"\n  private static int enchantments;")
                    + "\n  private DynamicEnchantment enchantment;"
                    + "\n"
                    + "\n  public DynamicEnchant(DynamicEnchantment enchantment) {"
                    + "\n    super(" +
                    (Version.isCurrentHigher(Version.v1_12) ? "NamespacedKey.minecraft(enchantment.getLocalizedName().replace(\" \", \"_\"))"
                            : "71 + Enchantment.values().length + (DynamicEnchant.enchantments++)")
                    + ");"
                    + "\n    this.enchantment = enchantment;"
                    + "\n    try {"
                    + "\n      enchantment.setEnchantment(this);"
                    + "\n    }catch(Exception e) {"
                    + "\n      e.printStackTrace();"
                    + "\n    }"
                    + "\n  }"
                    + "\n"
                    + "\n"
                    + "\n  public String getName() {"
                    + "\n    "+(Version.isCurrentHigher(Version.v1_12) ?"return getKey().getKey().toUpperCase();":"return enchantment.getName();")
                    + "\n  }"
                    + "\n"
                    + "\n  public int getMaxLevel() {"
                    + "\n    return enchantment.getMaxLevel();"
                    + "\n  }"
                    + "\n"
                    + "\n  public int getStartLevel() {"
                    + "\n    return 1;"
                    + "\n  }"
                    + "\n"
                    + "\n  public EnchantmentTarget getItemTarget() {"
                    + "\n    return enchantment.getItemTarget();"
                    + "\n  }"
                    + "\n"
                    + "\n  public boolean isTreasure() {"
                    + "\n    return enchantment.isTreasure();"
                    + "\n  }"
                    + "\n"
                    + "\n  public boolean isCursed() {"
                    + "\n    return enchantment.isCursed();"
                    + "\n  }"
                    + "\n"
                    + "\n  public DynamicEnchantment getEnchantment() {"
                    + "\n    return this.enchantment;"
                    + "\n  }"
                    + "\n"
                    + "\n  public boolean conflictsWith(Enchantment enchant) {"
                    + "\n    return enchantment.conflictsWith(enchant);"
                    + "\n  }"
                    + "\n"
                    + "\n  public boolean canEnchantItem(ItemStack itemStack) {"
                    + "\n    return enchantment.canEnchantItem(itemStack);"
                    + "\n  }"
                    + "\n"
                    + "}";

}
