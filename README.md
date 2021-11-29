# DynamicEnchants

Example:
```java
@Override
public void onEnable() {
	DynamicEnchantmentHandler handler = new DynamicEnchantmentHandler(this);
	new DynamicEnchantment(handler,("&bHello there"), Levels.LEVELS_25_30, 5, 1)
		.allowAnvil();//Enables anvil support (combining levels)
	//Set Levels to null to disable EnchantingTable support
}
```

## Constructors

```java
DynamicEnchantment(DynamicEnchantmentHandler enchantmentHandler, String name, int maxLevel, float chance, Enchantment... conflicts)
DynamicEnchantment(DynamicEnchantmentHandler enchantmentHandler, String name, Levels levels, int maxLevel, float chance, Enchantment... conflicts)
DynamicEnchantment(DynamicEnchantmentHandler enchantmentHandler, String name, Levels levels, int maxLevel, float chance, EnchantTarget target, Enchantment... conflicts)
DynamicEnchantment(DynamicEnchantmentHandler enchantmentHandler, String name, Levels levels, int maxLevel, float chance, EnchantTarget target, boolean isTreasure, Enchantment... conflicts)
DynamicEnchantment(DynamicEnchantmentHandler enchantmentHandler, String name, Levels levels, int maxLevel, float chance, EnchantTarget target, boolean isTreasure, boolean isCursed, Enchantment... conflicts)
```

# How it works
It compiles a class at runtime called "DynamicEnchant" and uses this class as a wrapper for the [org.bukkit.enchantment.Enchantment](https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/enchantments/Enchantment.html) class
Then it uses the default bukkit system for enchantment registering.

It will also update all Enchantments to use the Lore to display the enchantments instead of default MC(Only if needed!). It may break other enchantment plugins, not 100% sure on that



# TO-DO?
- [ ] Add Enchantment name coloring for rarity? (maybe, might do that one day)



