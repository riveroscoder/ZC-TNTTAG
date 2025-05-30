package org.riveros.coder.Utils;

import com.google.gson.Gson;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemBuilder {

    private ItemStack item;
    private ItemMeta meta;
    private Material material = Material.STONE;
    private int amount = 1;
    private MaterialData data;
    private short damage = 0;
    private Map<Enchantment, Integer> enchantments = new HashMap<>();
    private String displayname;
    private List<String> lore = new ArrayList<>();
    private List<ItemFlag> flags = new ArrayList<>();

    private boolean andSymbol = true;
    private boolean unsafeStackSize = false;

    public ItemBuilder(Material material) {
        if(material == null) material = Material.AIR;
        this.item = new ItemStack(material);
        this.material = material;
    }

    public ItemBuilder(Material material, int amount) {
        if(material == null) material = Material.AIR;
        if(((amount > material.getMaxStackSize()) || (amount <= 0)) && (!unsafeStackSize)) amount = 1;
        this.amount = amount;
        this.item = new ItemStack(material, amount);
        this.material = material;
    }

    public ItemBuilder(Material material, int amount, String displayname) {
        if(material == null) material = Material.AIR;
        Validate.notNull(displayname, "The Displayname is null.");
        this.item = new ItemStack(material, amount);
        this.material = material;
        if(((amount > material.getMaxStackSize()) || (amount <= 0)) && (!unsafeStackSize)) amount = 1;
        this.amount = amount;
        this.displayname = displayname;
    }

    public ItemBuilder(Material material, String displayname) {
        if(material == null) material = Material.AIR;
        Validate.notNull(displayname, "The Displayname is null.");
        this.item = new ItemStack(material);
        this.material = material;
        this.displayname = displayname;
    }

    public ItemBuilder(ItemStack item) {
        Validate.notNull(item, "The Item is null.");
        this.item = item;
        if(item.hasItemMeta())
            this.meta = item.getItemMeta();
        this.material = item.getType();
        this.amount = item.getAmount();
        this.data = item.getData();
        this.damage = item.getDurability();
        this.enchantments = item.getEnchantments();
        if(item.hasItemMeta())
            this.displayname = item.getItemMeta().getDisplayName();
        if(item.hasItemMeta())
            this.lore = item.getItemMeta().getLore();
        if(item.hasItemMeta())
            for (ItemFlag f : item.getItemMeta().getItemFlags()) {
                flags.add(f);
            }
    }

    /** Initalizes the ItemBuilder with a {@link org.bukkit.configuration.file.FileConfiguration} ItemStack in Path */
    public ItemBuilder(FileConfiguration cfg, String path) {
        this(cfg.getItemStack(path));
    }

    /**
     * Initalizes the ItemBuilder with an already existing {@link cc.acquized.itembuilder.api.ItemBuilder}
     * @deprecated Use the already initalized {@code ItemBuilder} Instance to improve performance
     */
    @Deprecated
    public ItemBuilder(ItemBuilder builder) {
        Validate.notNull(builder, "The ItemBuilder is null.");
        this.item = builder.item;
        this.meta = builder.meta;
        this.material = builder.material;
        this.amount = builder.amount;
        this.damage = builder.damage;
        this.data = builder.data;
        this.damage = builder.damage;
        this.enchantments = builder.enchantments;
        this.displayname = builder.displayname;
        this.lore = builder.lore;
        this.flags = builder.flags;
    }

    /**
     * Sets the Amount of the ItemStack
     * @param amount Amount for the ItemStack
     */
    public ItemBuilder amount(int amount) {
        if(((amount > material.getMaxStackSize()) || (amount <= 0)) && (!unsafeStackSize)) amount = 1;
        this.amount = amount;
        return this;
    }

    /**
     * Sets the {@link org.bukkit.material.MaterialData} of the ItemStack
     * @param data MaterialData for the ItemStack
     */
    public ItemBuilder data(MaterialData data) {
        Validate.notNull(data, "The Data is null.");
        this.data = data;
        return this;
    }

    /**
     * Sets the Damage of the ItemStack
     * @param damage Damage for the ItemStack
     * @deprecated Use {@code ItemBuilder#durability}
     */
    @Deprecated
    public ItemBuilder damage(short damage) {
        this.damage = damage;
        return this;
    }

    /**
     * Sets the Durability (Damage) of the ItemStack
     * @param damage Damage for the ItemStack
     */
    public ItemBuilder durability(short damage) {
        this.damage = damage;
        return this;
    }

    /**
     * Sets the {@link org.bukkit.Material} of the ItemStack
     * @param material Material for the ItemStack
     */
    public ItemBuilder material(Material material) {
        Validate.notNull(material, "The Material is null.");
        this.material = material;
        return this;
    }

    /**
     * Sets the {@link org.bukkit.inventory.meta.ItemMeta} of the ItemStack
     * @param meta Meta for the ItemStack
     */
    public ItemBuilder meta(ItemMeta meta) {
        Validate.notNull(meta, "The Meta is null.");
        this.meta = meta;
        return this;
    }

    /**
     * Adds a {@link org.bukkit.enchantments.Enchantment} to the ItemStack
     * @param enchant Enchantment for the ItemStack
     * @param level Level of the Enchantment
     */
    public ItemBuilder enchant(Enchantment enchant, int level) {
        Validate.notNull(enchant, "The Enchantment is null.");
        enchantments.put(enchant, level);
        return this;
    }

    /**
     * Adds a list of {@link org.bukkit.enchantments.Enchantment} to the ItemStack
     * @param enchantments Map containing Enchantment and Level for the ItemStack
     */
    public ItemBuilder enchant(Map<Enchantment, Integer> enchantments) {
        Validate.notNull(enchantments, "The Enchantments are null.");
        this.enchantments = enchantments;
        return this;
    }

    /**
     * Sets the Displayname of the ItemStack
     * @param displayname Displayname for the ItemStack
     */
    public ItemBuilder displayname(String displayname) {
        Validate.notNull(displayname, "The Displayname is null.");
        this.displayname = andSymbol ? ChatColor.translateAlternateColorCodes('&', displayname) : displayname;
        return this;
    }

    /**
     * Adds a Line to the Lore of the ItemStack
     * @param line Line of the Lore for the ItemStack
     */
    public ItemBuilder lore(String line) {
        Validate.notNull(line, "The Line is null.");
        lore.add(andSymbol ? ChatColor.translateAlternateColorCodes('&', line) : line);
        return this;
    }

    /**
     * Sets the Lore of the ItemStack
     * @param lore List containing String as Lines for the ItemStack Lore
     */
    public ItemBuilder lore(List<String> lore) {
        Validate.notNull(lore, "The Lores are null.");
        this.lore = lore;
        return this;
    }

    /**
     * Adds one or more Lines to the Lore of the ItemStack
     * @param lines One or more Strings for the ItemStack Lore
     * @deprecated Use {@code ItemBuilder#lore}
     */
    @Deprecated
    public ItemBuilder lores(String... lines) {
        Validate.notNull(lines, "The Lines are null.");
        for (String line : lines) {
            lore(andSymbol ? ChatColor.translateAlternateColorCodes('&', line) : line);
        }
        return this;
    }

    /**
     * Adds one or more Lines to the Lore of the ItemStack
     * @param lines One or more Strings for the ItemStack Lore
     */
    public ItemBuilder lore(String... lines) {
        Validate.notNull(lines, "The Lines are null.");
        for (String line : lines) {
            lore(andSymbol ? ChatColor.translateAlternateColorCodes('&', line) : line);
        }
        return this;
    }

    /**
     * Adds a String at a specified position in the Lore of the ItemStack
     * @param line Line of the Lore for the ItemStack
     * @param index Position in the Lore for the ItemStack
     */
    public ItemBuilder lore(String line, int index) {
        Validate.notNull(line, "The Line is null.");
        lore.set(index, andSymbol ? ChatColor.translateAlternateColorCodes('&', line) : line);
        return this;
    }

    /**
     * Adds a {@link org.bukkit.inventory.ItemFlag} to the ItemStack
     * @param flag ItemFlag for the ItemStack
     */
    public ItemBuilder flag(ItemFlag flag) {
        Validate.notNull(flag, "The Flag is null.");
        flags.add(flag);
        return this;
    }

    /**
     * Adds more than one {@link org.bukkit.inventory.ItemFlag} to the ItemStack
     * @param flags List containing all ItemFlags
     */
    public ItemBuilder flag(List<ItemFlag> flags) {
        Validate.notNull(flags, "The Flags are null.");
        this.flags = flags;
        return this;
    }

    /**
     * Makes or removes the Unbreakable Flag from the ItemStack
     * @param unbreakable If it should be unbreakable
     */
    public ItemBuilder unbreakable(boolean unbreakable) {
        meta.spigot().setUnbreakable(unbreakable);
        return this;
    }

    /** Makes the ItemStack Glow like it had a Enchantment */
    public ItemBuilder glow() {
        enchant(material != Material.BOW ? Enchantment.ARROW_INFINITE : Enchantment.LUCK, 10);
        flag(ItemFlag.HIDE_ENCHANTS);
        return this;
    }

    /**
     * Sets the Skin for the Skull
     * @param user Username of the Skull
     * @deprecated Make it yourself - This Meta destrys the already setted Metas
     */
    @Deprecated
    public ItemBuilder owner(String user) {
        Validate.notNull(user, "The Username is null.");
        if((material == Material.SKULL_ITEM) || (material == Material.SKULL)) {
            SkullMeta smeta = (SkullMeta) meta;
            smeta.setOwner(user);
            meta = smeta;
        }
        return this;
    }

    /** Returns the Unsafe Class containing NBT Methods */
    public Unsafe unsafe() {
        return new Unsafe(this);
    }

    /**
     * Toggles replacement of the '&' Characters in Strings
     * @deprecated Use {@code ItemBuilder#toggleReplaceAndSymbol}
     */
    @Deprecated
    public ItemBuilder replaceAndSymbol() {
        replaceAndSymbol(!andSymbol);
        return this;
    }

    /**
     * Enables / Disables replacement of the '&' Character in Strings
     * @param replace Determinates if it should be replaced or not
     */
    public ItemBuilder replaceAndSymbol(boolean replace) {
        andSymbol = replace;
        return this;
    }

    /** Toggles replacement of the '&' Character in Strings */
    public ItemBuilder toggleReplaceAndSymbol() {
        replaceAndSymbol(!andSymbol);
        return this;
    }

    /**
     * Allows / Disallows Stack Sizes under 1 and above 64
     * @param allow Determinates if it should be allowed or not
     */
    public ItemBuilder unsafeStackSize(boolean allow) {
        this.unsafeStackSize = allow;
        return this;
    }

    /** Toggles allowment of stack sizes under 1 and above 64*/
    public ItemBuilder toggleUnsafeStackSize() {
        unsafeStackSize(!unsafeStackSize);
        return this;
    }

    /** Returns the Displayname */
    public String getDisplayname() {
        return displayname;
    }

    /** Returns the Amount */
    public int getAmount() {
        return amount;
    }

    /** Returns all Enchantments */
    public Map<Enchantment, Integer> getEnchantments() {
        return enchantments;
    }

    /**
     * Returns the Damage
     * @deprecated Use {@code ItemBuilder#getDurability}
     */
    @Deprecated
    public short getDamage() {
        return damage;
    }

    /** Returns the Durability */
    public short getDurability() {
        return damage;
    }

    /** Returns the Lores */
    public List<String> getLores() {
        return lore;
    }

    /** Returns if the '&' Character will be replaced */
    public boolean getAndSymbol() {
        return andSymbol;
    }

    /** Returns all ItemFlags */
    public List<ItemFlag> getFlags() {
        return flags;
    }

    /** Returns the Material */
    public Material getMaterial() {
        return material;
    }

    /** Returns the ItemMeta */
    public ItemMeta getMeta() {
        return meta;
    }

    /** Returns the MaterialData */
    public MaterialData getData() {
        return data;
    }

    /**
     * Returns all Lores
     * @deprecated Use {@code ItemBuilder#getLores}
     */
    @Deprecated
    public List<String> getLore() {
        return lore;
    }

    /**
     * Converts the Item to a ConfigStack and writes it to path
     * @param cfg Configuration File to which it should be writed
     * @param path Path to which the ConfigStack should be writed
     */
    public ItemBuilder toConfig(FileConfiguration cfg, String path) {
        cfg.set(path, build());
        return this;
    }

    /**
     * Converts back the ConfigStack to a ItemBuilder
     * @param cfg Configuration File from which it should be read
     * @param path Path from which the ConfigStack should be read
     */
    public ItemBuilder fromConfig(FileConfiguration cfg, String path) {
        return new ItemBuilder(cfg, path);
    }

    /**
     * Converts the Item to a ConfigStack and writes it to path
     * @param cfg Configuration File to which it should be writed
     * @param path Path to which the ConfigStack should be writed
     * @param builder Which ItemBuilder should be writed
     */
    public static void toConfig(FileConfiguration cfg, String path, ItemBuilder builder) {
        cfg.set(path, builder.build());
    }

    /**
     * Converts the ItemBuilder to a JsonItemBuilder
     * @return The ItemBuilder as JSON String
     */
    public String toJson() {
        return new Gson().toJson(this);
    }

    /**
     * Converts the ItemBuilder to a JsonItemBuilder
     * @param builder Which ItemBuilder should be converted
     * @return The ItemBuilder as JSON String
     */
    public static String toJson(ItemBuilder builder) {
        return new Gson().toJson(builder);
    }

    /**
     * Converts the JsonItemBuilder back to a ItemBuilder
     * @param json Which JsonItemBuilder should be converted
     */
    public static ItemBuilder fromJson(String json) {
        return new Gson().fromJson(json, ItemBuilder.class);
    }

    /**
     * Applies the currently ItemBuilder to the JSONItemBuilder
     * @param json Already existing JsonItemBuilder
     * @param overwrite Should the JsonItemBuilder used now
     */
    public ItemBuilder applyJson(String json, boolean overwrite) {
        ItemBuilder b = new Gson().fromJson(json, ItemBuilder.class);
        if(overwrite)
            return b;
        if(b.displayname != null)
            displayname = b.displayname;
        if(b.data != null)
            data = b.data;
        if(b.material != null)
            material = b.material;
        if(b.lore != null)
            lore = b.lore;
        if(b.enchantments != null)
            enchantments = b.enchantments;
        if(b.item != null)
            item = b.item;
        if(b.flags != null)
            flags = b.flags;
        damage = b.damage;
        amount = b.amount;
        return this;
    }

    /** Converts the ItemBuilder to a {@link org.bukkit.inventory.ItemStack} */
    public ItemStack build() {
        item.setType(material);
        item.setAmount(amount);
        item.setDurability(damage);
        meta = item.getItemMeta();
        if(data != null) {
            item.setData(data);
        }
        if(enchantments.size() > 0) {
            item.addUnsafeEnchantments(enchantments);
        }
        if(displayname != null) {
            meta.setDisplayName(displayname);
        }
        if(lore.size() > 0) {
            meta.setLore(lore);
        }
        if(flags.size() > 0) {
            for (ItemFlag f : flags) {
                meta.addItemFlags(f);
            }
        }
        item.setItemMeta(meta);
        return item;
    }

    /** Contains NBT Tags Methods */
    public class Unsafe {

        /** Do not access using this Field*/
        protected final ReflectionUtils utils = new ReflectionUtils();

        /** Do not access using this Field */
        protected final ItemBuilder builder;

        /** Initalizes the Unsafe Class with a ItemBuilder */
        public Unsafe(ItemBuilder builder) {
            this.builder = builder;
        }

        /**
         * Sets a NBT Tag {@code String} into the NBT Tag Compound of the Item
         * @param key The Name on which the NBT Tag should be saved
         * @param value The Value that should be saved
         */
        public Unsafe setString(String key, String value) {
            builder.item = utils.setString(builder.item, key, value);
            return this;
        }

        /** Returns the String that is saved under the key */
        public String getString(String key) {
            return utils.getString(builder.item, key);
        }

        /**
         * Sets a NBT Tag {@code Integer} into the NBT Tag Compound of the Item
         * @param key The Name on which the NBT Tag should be savbed
         * @param value The Value that should be saved
         */
        public Unsafe setInt(String key, int value) {
            builder.item = utils.setInt(builder.item, key, value);
            return this;
        }

        /** Returns the Integer that is saved under the key */
        public int getInt(String key) {
            return utils.getInt(builder.item, key);
        }

        /**
         * Sets a NBT Tag {@code Double} into the NBT Tag Compound of the Item
         * @param key The Name on which the NBT Tag should be savbed
         * @param value The Value that should be saved
         */
        public Unsafe setDouble(String key, double value) {
            builder.item = utils.setDouble(builder.item, key, value);
            return this;
        }

        /** Returns the Double that is saved under the key */
        public double getDouble(String key) {
            return utils.getDouble(builder.item, key);
        }

        /**
         * Sets a NBT Tag {@code Boolean} into the NBT Tag Compound of the Item
         * @param key The Name on which the NBT Tag should be savbed
         * @param value The Value that should be saved
         */
        public Unsafe setBoolean(String key, boolean value) {
            builder.item = utils.setBoolean(builder.item, key, value);
            return this;
        }

        /** Returns the Boolean that is saved under the key */
        public boolean getBoolean(String key) {
            return utils.getBoolean(builder.item, key);
        }

        /** Returns a Boolean if the Item contains the NBT Tag named key */
        public boolean containsKey(String key) {
            return utils.hasKey(builder.item, key);
        }

        /** Accesses back the ItemBuilder and exists the Unsafe Class */
        public ItemBuilder builder() {
            return builder;
        }

        /** This Class contains highly sensitive NMS Code that should not be touched unless you want to break the ItemBuilder */
        public class ReflectionUtils {

            public String getString(ItemStack item, String key) {
                Object compound = getNBTTagCompound(getItemAsNMSStack(item));
                if(compound == null) {
                    compound = getNewNBTTagCompound();
                }
                try {
                    return (String) compound.getClass().getMethod("getString", String.class).invoke(compound, key);
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
                    ex.printStackTrace();
                }
                return null;
            }

            public ItemStack setString(ItemStack item, String key, String value) {
                Object nmsItem = getItemAsNMSStack(item);
                Object compound = getNBTTagCompound(nmsItem);
                if(compound == null) {
                    compound = getNewNBTTagCompound();
                }
                try {
                    compound.getClass().getMethod("setString", String.class, String.class).invoke(compound, key, value);
                    nmsItem = setNBTTag(compound, nmsItem);
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
                    ex.printStackTrace();
                }
                return getItemAsBukkitStack(nmsItem);
            }

            public int getInt(ItemStack item, String key) {
                Object compound = getNBTTagCompound(getItemAsNMSStack(item));
                if(compound == null) {
                    compound = getNewNBTTagCompound();
                }
                try {
                    return (Integer) compound.getClass().getMethod("getInt", String.class).invoke(compound, key);
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
                    ex.printStackTrace();
                }
                return -1;
            }

            public ItemStack setInt(ItemStack item, String key, int value) {
                Object nmsItem = getItemAsNMSStack(item);
                Object compound = getNBTTagCompound(nmsItem);
                if(compound == null) {
                    compound = getNewNBTTagCompound();
                }
                try {
                    compound.getClass().getMethod("setInt", String.class, Integer.class).invoke(compound, key, value);
                    nmsItem = setNBTTag(compound, nmsItem);
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
                    ex.printStackTrace();
                }
                return getItemAsBukkitStack(nmsItem);
            }

            public double getDouble(ItemStack item, String key) {
                Object compound = getNBTTagCompound(getItemAsNMSStack(item));
                if(compound == null) {
                    compound = getNewNBTTagCompound();
                }
                try {
                    return (Double) compound.getClass().getMethod("getDouble", String.class).invoke(compound, key);
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
                    ex.printStackTrace();
                }
                return Double.NaN;
            }

            public ItemStack setDouble(ItemStack item, String key, double value) {
                Object nmsItem = getItemAsNMSStack(item);
                Object compound = getNBTTagCompound(nmsItem);
                if(compound == null) {
                    compound = getNewNBTTagCompound();
                }
                try {
                    compound.getClass().getMethod("setDouble", String.class, Double.class).invoke(compound, key, value);
                    nmsItem = setNBTTag(compound, nmsItem);
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
                    ex.printStackTrace();
                }
                return getItemAsBukkitStack(nmsItem);
            }

            public boolean getBoolean(ItemStack item, String key) {
                Object compound = getNBTTagCompound(getItemAsNMSStack(item));
                if(compound == null) {
                    compound = getNewNBTTagCompound();
                }
                try {
                    return (Boolean) compound.getClass().getMethod("getBoolean", String.class).invoke(compound, key);
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
                    ex.printStackTrace();
                }
                return false;
            }

            public ItemStack setBoolean(ItemStack item, String key, boolean value) {
                Object nmsItem = getItemAsNMSStack(item);
                Object compound = getNBTTagCompound(nmsItem);
                if(compound == null) {
                    compound = getNewNBTTagCompound();
                }
                try {
                    compound.getClass().getMethod("setBoolean", String.class, Boolean.class).invoke(compound, key, value);
                    nmsItem = setNBTTag(compound, nmsItem);
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
                    ex.printStackTrace();
                }
                return getItemAsBukkitStack(nmsItem);
            }

            public boolean hasKey(ItemStack item, String key) {
                Object compound = getNBTTagCompound(getItemAsNMSStack(item));
                if(compound == null) {
                    compound = getNewNBTTagCompound();
                }
                try {
                    return (Boolean) compound.getClass().getMethod("hasKey", String.class).invoke(compound, key);
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    e.printStackTrace();
                }
                return false;
            }

            public Object getNewNBTTagCompound() {
                String ver = Bukkit.getServer().getClass().getPackage().getName().split(".")[3];
                try {
                    return Class.forName("net.minecraft.server." + ver + ".NBTTagCompound").newInstance();
                } catch (ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
                    ex.printStackTrace();
                }
                return null;
            }

            public Object setNBTTag(Object tag, Object item) {
                try {
                    item.getClass().getMethod("setTag", item.getClass()).invoke(item, tag);
                    return item;
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
                    ex.printStackTrace();
                }
                return null;
            }

            public Object getNBTTagCompound(Object nmsStack) {
                try {
                    return nmsStack.getClass().getMethod("getTag").invoke(nmsStack);
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
                    ex.printStackTrace();
                }
                return null;
            }

            public Object getItemAsNMSStack(ItemStack item) {
                try {
                    Method m = getCraftItemStackClass().getMethod("asNMSCopy", ItemStack.class);
                    return m.invoke(getCraftItemStackClass(), item);
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ex) {
                    ex.printStackTrace();
                }
                return null;
            }

            public ItemStack getItemAsBukkitStack(Object nmsStack) {
                try {
                    Method m = getCraftItemStackClass().getMethod("asCraftMirror", nmsStack.getClass());
                    return (ItemStack) m.invoke(getCraftItemStackClass(), nmsStack);
                } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException ex) {
                    ex.printStackTrace();
                }
                return null;
            }

            public Class<?> getCraftItemStackClass() {
                String ver = Bukkit.getServer().getClass().getPackage().getName().split(".")[3];
                try {
                    return Class.forName("org.bukkit.craftbukkit." + ver + ".inventory.CraftItemStack");
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
                return null;
            }

        }
    }

}
