package dev.perryplaysmc.dynamicenchantments.enchantment;

public enum Levels {

    LEVELS_25_30, LEVELS_20_25, LEVELS_15_20, LEVELS_10_15, LEVELS_6_10, LEVELS_0_6;

    public boolean accepts(int level) {
       switch(this) {
          case LEVELS_25_30: {
             return  level > 25 && level <= 30;
          }
          case LEVELS_20_25: {
             return  level > 20 && level <= 25;
          }
          case LEVELS_15_20: {
             return  level > 15 && level <= 20;
          }
          case LEVELS_10_15: {
             return  level > 10 && level <= 15;
          }
          case LEVELS_6_10: {
             return  level > 6 && level <= 10;
          }
          case LEVELS_0_6: {
             return  level > 0 && level <= 6;
          }
       }
       return false;
    }

}
