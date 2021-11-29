# DynamicEnchantments

Example:
```java

DynamicEnchantmentHandler handler;

//Setup
@Override
public void onEnable() {
   this.handler = new DynamicEnchantmentHandler(this);
   new DynamicEnchantment(handler,("&bHello there"), Levels.LEVELS_25_30, 5, 1)
      .allowAnvil();//Enables anvil support (combining levels)
   //Set Levels to null to disable EnchantingTable support
}



//Usage
@EventHandler 
public void onAttack(EntityDamageByEntityEvent e){
   if(!(e.getDamager()instanceof LivingEntity)) return;
   LivingEntity entity=(LivingEntity)e.getDamager();
   ItemStack item=entity.getEquipment().getItemInMainHand();
   if(hasEnchant(item,getDynamicEnchantment("hello_there")))
      e.getEntity().setVelocity(new Vector(0,1,0));
   //OR
   if(item.containsEnchantment(getDynamicEnchantment("hello_there").getEnchantment()))
      e.getEntity().setVelocity(new Vector(0,1,0));
   //OR
   if(item.getItemMeta().hasEnchant(getDynamicEnchantment("hello_there").getEnchantment()))
      e.getEntity().setVelocity(new Vector(0,1,0));
   //OR
   if(item.getItemMeta().hasEnchant(Enchantment.getByName("hello_there")))
      e.getEntity().setVelocity(new Vector(0,1,0));

   //You do not need all 4 if statements, just one of them.
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
