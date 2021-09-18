package dev.foraged.game.util

import com.google.common.collect.Lists
import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.inventory.meta.LeatherArmorMeta
import org.bukkit.inventory.meta.SkullMeta
import org.bukkit.material.MaterialData

class ItemBuilder {

    private ItemStack is

    ItemBuilder(Material mat) {
        is = new ItemStack(mat)
    }

    ItemBuilder(ItemStack is) {
        this.is = is
    }

    ItemBuilder amount(int amount) {
        is.setAmount(amount)
        return this
    }

    ItemBuilder name(String name) {
        ItemMeta meta = is.getItemMeta()
        meta.setDisplayName(CC.translate(name))
        is.setItemMeta(meta)
        return this
    }

    ItemBuilder lore(String name) {
        ItemMeta meta = is.getItemMeta()
        List<String> lore = meta.getLore()
        if (lore == null) lore = Lists.newArrayList()
        if (lore.contains("\n")) {
            this.lore(Arrays.asList(name.split("\n")))
        } else {
            lore.add(name)
            meta.setLore(CC.translate(lore))
            is.setItemMeta(meta)
        }
        return this
    }

    ItemBuilder lore(List<String> lore) {
        List<String> toSet = new ArrayList<>()
        ItemMeta meta = is.getItemMeta()

        for (String string : lore) {
            toSet.add(CC.translate(string))
        }

        meta.setLore(toSet)
        is.setItemMeta(meta)
        return this
    }

    ItemBuilder durability(int durability) {
        is.setDurability((short) durability)
        return this
    }

    ItemBuilder data(int data) {
        is.setData(new MaterialData(is.getType(), (byte) data))
        return this
    }

    ItemBuilder enchantment(Enchantment enchantment, int level) {
        is.addUnsafeEnchantment(enchantment, level)
        return this
    }

    ItemBuilder enchantment(Enchantment enchantment) {
        is.addUnsafeEnchantment(enchantment, 1)
        return this
    }

    ItemBuilder type(Material material) {
        is.setType(material)
        return this
    }

    ItemBuilder clearLore() {
        ItemMeta meta = is.getItemMeta()
        meta.setLore(new ArrayList<>())
        is.setItemMeta(meta)
        return this
    }

    ItemBuilder clearEnchantments() {
        for (Enchantment e : is.getEnchantments().keySet()) is.removeEnchantment(e)
        return this
    }

    ItemBuilder color(Color color) {
        if (is.getType() == Material.LEATHER_BOOTS || is.getType() == Material.LEATHER_CHESTPLATE || is.getType() == Material.LEATHER_HELMET
                || is.getType() == Material.LEATHER_LEGGINGS) {
            LeatherArmorMeta meta = (LeatherArmorMeta) is.getItemMeta()
            meta.setColor(color)
            is.setItemMeta(meta)
            return this
        } else throw new IllegalArgumentException("color() only applicable for leather armor!")
    }

    ItemBuilder owner(String owner) {
        SkullMeta meta = (SkullMeta) is.getItemMeta()
        meta.setOwner(owner)
        is.setItemMeta(meta)
        return this
    }

    ItemStack build() {
        return is
    }
}