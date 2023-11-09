package com.ollicafe.ollissupertools;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class Tool {
	
	
	public void init(OllisSuperTools plugin) {
		plugin.getServer().addRecipe(pickaxe());
		plugin.getServer().addRecipe(axe());
		plugin.getServer().addRecipe(shovel());
		plugin.getServer().addRecipe(hoe());
	}
	
	private ShapedRecipe pickaxe() {
		ItemStack pick = new ItemStack(Material.NETHERITE_PICKAXE);
		ItemMeta meta = pick.getItemMeta();
		meta.setDisplayName("Super Pickaxe");
		List<String> lore = meta.getLore();
		if(lore==null)
			lore = new ArrayList<String>();
		lore.add("Super");
		meta.setLore(lore);
		pick.setItemMeta(meta);
		
		
		ShapedRecipe superPick = new ShapedRecipe(pick);
		
		superPick.shape("ttt"," s "," a ");

		superPick.setIngredient('t', Material.NETHERITE_PICKAXE);
		superPick.setIngredient('s', Material.NETHER_STAR);
		superPick.setIngredient('a', Material.ANVIL);
		
		return superPick;
	}
	
	private ShapedRecipe axe() {
		ItemStack axe = new ItemStack(Material.NETHERITE_AXE);
		ItemMeta meta = axe.getItemMeta();
		meta.setDisplayName("Super Axe");
		List<String> lore = meta.getLore();
		if(lore==null)
			lore = new ArrayList<String>();
		lore.add("Super");
		meta.setLore(lore);
		axe.setItemMeta(meta);
		
		ShapedRecipe superAxe = new ShapedRecipe(axe);
		
		superAxe.shape("ttt"," s "," a ");

		superAxe.setIngredient('t', Material.NETHERITE_AXE);
		superAxe.setIngredient('s', Material.NETHER_STAR);
		superAxe.setIngredient('a', Material.ANVIL);
		
		return superAxe;
		
	}
	
	private ShapedRecipe shovel() {
		ItemStack shovel = new ItemStack(Material.NETHERITE_SHOVEL);
		ItemMeta meta = shovel.getItemMeta();
		meta.setDisplayName("Super Shovel");
		List<String> lore = meta.getLore();
		if(lore==null)
			lore = new ArrayList<String>();
		lore.add("Super");
		meta.setLore(lore);
		shovel.setItemMeta(meta);
		
		ShapedRecipe superShovel = new ShapedRecipe(shovel);
		
		superShovel.shape("ttt"," s "," a ");

		superShovel.setIngredient('t', Material.NETHERITE_SHOVEL);
		superShovel.setIngredient('s', Material.NETHER_STAR);
		superShovel.setIngredient('a', Material.ANVIL);
		
		return superShovel;
		
	}
	

	private ShapedRecipe hoe() {
		ItemStack hoe = new ItemStack(Material.NETHERITE_HOE);
		ItemMeta meta = hoe.getItemMeta();
		meta.setDisplayName("Super Hoe");
		List<String> lore = meta.getLore();
		if(lore==null)
			lore = new ArrayList<String>();
		lore.add("Super");
		meta.setLore(lore);
		hoe.setItemMeta(meta);
		
		ShapedRecipe superHoe = new ShapedRecipe(hoe);
		
		superHoe.shape("ttt"," s "," a ");

		superHoe.setIngredient('t', Material.NETHERITE_HOE);
		superHoe.setIngredient('s', Material.NETHER_STAR);
		superHoe.setIngredient('a', Material.ANVIL);
		
		return superHoe;
		
	}

}
