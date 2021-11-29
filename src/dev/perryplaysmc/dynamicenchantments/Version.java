package dev.perryplaysmc.dynamicenchantments;

import org.bukkit.Bukkit;

import java.util.HashMap;

/**
 * Creator: PerryPlaysMC
 * Created: 10/2021
 **/
public enum Version {
   v1_6(160), v1_6_R1(161), v1_6_R2(162), v1_6_R3(163),
   v1_7(170), v1_7_R2(172), v1_7_R4(174), v1_7_R10(1710),
   v1_8(180), v1_8_R1(181), v1_8_R2(182), v1_8_R3(183),
   v1_9(190), v1_9_R1(191), v1_9_R2(192),
   v1_10(1100), v1_10_R1(1101),
   v1_11(1110), v1_11_R1(1111),
   v1_12(1120), v1_12_R1(1121),
   v1_13(1130), v1_13_R1(1131), v1_13_R2(1132),
   v1_14(1140), v1_14_R1(1141),
   v1_15(1150), v1_15_R1(1151),
   v1_16(1160), v1_16_R1(1161), v1_16_R2(1162), v1_16_R3(1163),
   v1_17(1170), v1_17_R1(1171), UNKNOWN(Integer.MAX_VALUE, "Unknown");

   private int ver;
   private String version;

   Version(int ver) {
      this.ver = ver;
      this.version = "v" + name().substring(1).toUpperCase();
   }

   Version(int ver, String version) {
      this.version = version;
      this.ver = ver;
   }

   public String getVersion() {
      return version;
   }

   public int getVersionInt() {
      return ver;
   }

   public boolean is(Version v) {
      return getVersionInt() == v.getVersionInt();
   }

   public boolean isHigher(Version v) {
      return getVersionInt() > v.getVersionInt();
   }

   public boolean isOrHigher(Version v) {
      return getVersionInt() >= v.getVersionInt();
   }

   public boolean isLower(Version v) {
      return getVersionInt() < v.getVersionInt();
   }

   public static boolean isCurrentHigher(Version v) {
      if(v.name().contains("R"))
         return currentExact().isHigher(v);
      return current().isHigher(v);
   }
   public static boolean isCurrentOrHigher(Version v) {
      if(v.name().contains("R"))
         return currentExact().isHigher(v);
      return current().isHigher(v);
   }

   public static String getNMSPackage() {
      if(Version.isCurrentHigher(v1_16_R3)) return "net.minecraft";
      return "net.minecraft.server." + currentExact().getVersion();
   }

   public static String getCBPackage() {
      return "org.bukkit.craftbukkit." + currentExact().getVersion();
   }


   private static Class<?> from(String name) {
      try {
         return Class.forName(name);
      } catch (Exception e) {
         e.printStackTrace();
         return null;
      }
   }

   public static class CraftBukkit {
      private static final HashMap<String, Class<?>> CACHE = new HashMap<>();

      public static Class<?> getClass(String clazz) {
         if(CACHE.containsKey(clazz)) return CACHE.get(clazz);
         Class<?> cls = Version.from(getCBPackage() + "." + clazz);
         CACHE.put(clazz, cls);
         return cls;
      }
   }

   public static class Minecraft {
      private static final HashMap<String, Class<?>> CACHE = new HashMap<>();

      public static Class<?> getClass(String clazz) {
         if(CACHE.containsKey(clazz)) return CACHE.get(clazz);
         String nms = Version.isCurrentHigher(v1_16_R3) ? getNMSPackage() : getNMSPackage() + ".server." + currentExact().getVersion();
         Class<?> cls = null;
         if(!Version.isCurrentHigher(v1_16_R3))
            if(clazz.contains("."))
               cls = Version.from(nms + "." + clazz.split("\\.")[clazz.split("\\.").length - 1]);
         if(cls == null)
            cls = Version.from(nms + "." + clazz);
         if(cls != null)
            CACHE.put(clazz, cls);
         return cls;
      }
   }


   public static class Mojang {
      private static final HashMap<String, Class<?>> CACHE = new HashMap<>();

      public static Class<?> getClass(String clazz) {
         if(CACHE.containsKey(clazz)) return CACHE.get(clazz);
         String nms = Version.isCurrentHigher(v1_7) ? "com.mojang" : "net.minecraft.util.com.mojang";
         Class<?> cls = Version.from(nms + "." + clazz);
         CACHE.put(clazz, cls);
         return cls;
      }
   }


   public static Version currentExact() {
      String pack = Bukkit.getServer().getClass().getPackage().getName();
      String version = pack.substring(pack.lastIndexOf('.') + 1);
      Version ret = value(version);
      if(ret == null) {
         ret = Version.UNKNOWN;
         ret.version = version.startsWith("v") ? version : "v" + version;
         ret.ver = Integer.parseInt(version.toLowerCase().replace(("_"), ("")).replace(("r"), ("")));
      }
      return ret;
   }

   public static Version current() {
      String pack = Bukkit.getServer().getClass().getPackage().getName();
      String version = pack.substring(pack.lastIndexOf('.') + 1);
      Version ret = value(version.split("_R")[0]);
      if(ret == null) {
         ret = Version.UNKNOWN;
         ret.version = version.startsWith("v") ? version : "v" + version;
         ret.ver = Integer.parseInt(version.toLowerCase().split(("r"))[0].replace(("_"), ("")));
      }
      return ret;
   }


   public static Version value(String versionId) {
      for(Version version : values())
         if(versionId.equalsIgnoreCase(version.name()) || versionId.equalsIgnoreCase(version.getVersion()))
            return version;
      return null;
   }

}