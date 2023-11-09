package com.ollicafe.ollissupertools;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class Tool {
	
	
	public void init(OllisSuperTools plugin) {
		plugin.getServer().addRecipe(pickaxe());
	}
	
	private ShapedRecipe pickaxe() {
		ItemStack pick = new ItemStack(Material.NETHERITE_PICKAXE);
		ItemMeta meta = pick.getItemMeta();
		meta.setDisplayName("Super Pickaxe");
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
		axe.setItemMeta(meta);
		
		ShapedRecipe superAxe = new ShapedRecipe(axe);
		
		superAxe.shape("ttt"," s "," a ");

		superAxe.setIngredient('t', Material.NETHERITE_AXE);
		superAxe.setIngredient('s', Material.NETHER_STAR);
		superAxe.setIngredient('a', Material.ANVIL);
		
		return superAxe;
		
	}

}
