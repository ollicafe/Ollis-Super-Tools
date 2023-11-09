package com.ollicafe.ollissupertools;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class Tool {
	
	
	public void init(OllisSuperTools plugin) {
		plugin.getServer().addRecipe(pickaxe());
	}
	
	private ShapedRecipe pickaxe() {
		ItemStack pick = new ItemStack(Material.NETHERITE_PICKAXE);
		
		
		ShapedRecipe superPick = new ShapedRecipe(pick);
		
		superPick.shape("ttt"," s "," a ");

		superPick.setIngredient('t', Material.NETHERITE_PICKAXE);
		superPick.setIngredient('s', Material.NETHER_STAR);
		superPick.setIngredient('a', Material.ANVIL);
		
		return superPick;
	}

}
