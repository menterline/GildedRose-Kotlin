package com.gildedrose

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class GildedRoseTest {

    val brie = "Aged Brie"
    val backstage = "Backstage passes to a TAFKAL80ETC concert"
    val sulfuras = "Sulfuras, Hand of Ragnaros"
    val normalItem = "normal item"
    @Test
    fun foo() {
        val items = listOf(Item("foo", 0, 0))
        val app = GildedRose(items)
        app.updateQuality()
        assertEquals("foo", app.items[0].name)
        assertEquals(0, app.items[0].quality)
        assertEquals(-1, app.items[0].sellIn)
    }

    @Test
    fun `normal item quality decreases`() {
        val items = listOf(Item(normalItem, 10, 5))
        val app = GildedRose(items)
        app.updateQuality();
        assertEquals(4, app.items[0].quality)
        assertEquals(9, app.items[0].sellIn)
    }

    @Test
    fun `normal item quality never goes negative`() {
        val items = listOf(Item(normalItem, 2, 0))
        val app = GildedRose(items)
        app.updateQuality();
        assertEquals(0, app.items[0].quality)
        assertEquals(1, app.items[0].sellIn)
    }

    @Test
    fun `normal item quality degrades twice as fast after expiration`() {
        val items = listOf(Item(normalItem, 0, 10))
        val app = GildedRose(items)
        app.updateQuality();
        assertEquals(8, app.items[0].quality)
        assertEquals(-1, app.items[0].sellIn)
    }

    @Test
    fun `Aged brie increases in quality as it gets older`() {
        val items = listOf(Item(brie, 10, 1))
        val app = GildedRose(items)
        app.updateQuality();
        assertEquals(2, app.items[0].quality)
        assertEquals(9, app.items[0].sellIn)
    }

    @Test
    fun `Aged brie increases in quality as it gets older, never over 50`() {
        val items = listOf(Item(brie, 10, 50))
        val app = GildedRose(items)
        app.updateQuality();
        assertEquals(50, app.items[0].quality)
        assertEquals(9, app.items[0].sellIn)
    }

    @Test
    fun `aged brie increases in quality twice as fast after expiration`() {
        val items = listOf(Item(brie, 0, 20))
        val app = GildedRose(items)
        app.updateQuality();
        assertEquals(22, app.items[0].quality)
        assertEquals(-1, app.items[0].sellIn)
    }

    @Test
    fun `aged brie increases in quality twice as fast after expiration up to 50`() {
        val items = listOf(Item(brie, 0, 49))
        val app = GildedRose(items)
        app.updateQuality();
        assertEquals(50, app.items[0].quality)
        assertEquals(-1, app.items[0].sellIn)
    }

    @Test
    fun `sulfuras never has to be sold and never decreases in quality`() {
        val items = listOf(Item(sulfuras, 10, 40))
        val app = GildedRose(items)
        app.updateQuality();
        assertEquals(40, app.items[0].quality)
        assertEquals(10, app.items[0].sellIn)
    }

    @Test
    fun `sulfuras never has to be sold and never decreases in quality even past its expiration`() {
        val items = listOf(Item(sulfuras, -1, 40))
        val app = GildedRose(items)
        app.updateQuality();
        assertEquals(40, app.items[0].quality)
        assertEquals(-1, app.items[0].sellIn)
    }

    @Test
    fun `Backstage pass increases in quality`() {
        val items = listOf(Item(backstage, 12, 20))
        val app = GildedRose(items)
        app.updateQuality();
        assertEquals(21, app.items[0].quality)
        assertEquals(11, app.items[0].sellIn)
    }

    @Test
    fun `Backstage pass increases in quality up to 50`() {
        val items = listOf(Item(backstage, 12, 50))
        val app = GildedRose(items)
        app.updateQuality();
        assertEquals(50, app.items[0].quality)
        assertEquals(11, app.items[0].sellIn)
    }

    @Test
    fun `Backstage pass increases in quality up to 50 when sellin less than 11`() {
        val items = listOf(Item(backstage, 10, 49))
        val app = GildedRose(items)
        app.updateQuality();
        assertEquals(50, app.items[0].quality)
        assertEquals(9, app.items[0].sellIn)
    }
    @Test
    fun `Backstage pass increases by 2 when 10 days left`() {
        val items = listOf(Item(backstage, 10, 20))
        val app = GildedRose(items)
        app.updateQuality();
        assertEquals(22, app.items[0].quality)
        assertEquals(9, app.items[0].sellIn)
    }

    @Test
    fun `Backstage pass increases by 2 when less than 10 days left`() {
        val items = listOf(Item(backstage, 9, 20))
        val app = GildedRose(items)
        app.updateQuality();
        assertEquals(22, app.items[0].quality)
        assertEquals(8, app.items[0].sellIn)
    }

    @Test
    fun `Backstage pass increases by 3 when 5 days left`() {
        val items = listOf(Item(backstage, 5, 20))
        val app = GildedRose(items)
        app.updateQuality();
        assertEquals(23, app.items[0].quality)
        assertEquals(4, app.items[0].sellIn)
    }

    @Test
    fun `Backstage pass increases by 3 when 5 days left, but maxes out at 50`() {
        val items = listOf(Item(backstage, 5, 48))
        val app = GildedRose(items)
        app.updateQuality();
        assertEquals(50, app.items[0].quality)
        assertEquals(4, app.items[0].sellIn)
    }

    @Test
    fun `Backstage pass increases by 3 when less than 5 days left`() {
        val items = listOf(Item(backstage, 4, 20))
        val app = GildedRose(items)
        app.updateQuality();
        assertEquals(23, app.items[0].quality)
        assertEquals(3, app.items[0].sellIn)
    }

    @Test
    fun `Backstage pass quality drops to 0 after concert`() {
        val items = listOf(Item(backstage, 0, 20))
        val app = GildedRose(items)
        app.updateQuality();
        assertEquals(0, app.items[0].quality)
        assertEquals(-1, app.items[0].sellIn)
    }


}


