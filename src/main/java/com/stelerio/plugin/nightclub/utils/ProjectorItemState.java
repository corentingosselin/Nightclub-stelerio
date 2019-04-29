package com.stelerio.plugin.nightclub.utils;

import org.bukkit.inventory.ItemStack;

public enum ProjectorItemState {


    GREEN(SkullCreator.itemFromBase64("MzRkNDZjM2ZmODhlMGRiYzUxZjY2MjE5M2UxOWViNGEwMWNkMWY0MGFkZDNhMWJiMTU2NjcwOTlhOGJiYjIifX19")),
    OFF(SkullCreator.itemFromBase64("NDhjZjIyZDZlYjExYzQ2M2UyZDI1ZDhhODFlOTY3NWY5MTg2ODgzMmIwNDM4ODc4YzczOWZkZGRjNDJhIn19fQ=="));


    private ItemStack head;
    ProjectorItemState(ItemStack head) {
        this.head = head;
    }

    public ItemStack getHead() {
        return head;
    }
}
