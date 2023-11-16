package com.ollicafe.ollissupertools;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class ToolListener implements Listener{
	
	
	OllisSuperTools plugin;
	
	public ToolListener(OllisSuperTools plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onBreakBlock(BlockBreakEvent e) {
		Player player = e.getPlayer();
		if(player==null)return;
		ItemStack item = player.getInventory().getItemInMainHand();
		if(!item.hasItemMeta()) return;
		if(!item.getItemMeta().hasLore()) return;
		if(!item.getItemMeta().getLore().contains("Super")) return;
		switch(item.getType()) {
		case NETHERITE_PICKAXE:
		case NETHERITE_SHOVEL:
			e.setCancelled(true);
			mineBlocksSuper(e.getBlock(), player);
			break;
		case NETHERITE_AXE:
			if(e.getBlock().getType().toString().endsWith("LOG")) {
				e.setCancelled(true);
				breakTree(e.getBlock(), player);
			}
			break;
		case NETHERITE_HOE:
			//harvestSuper(e.getBlock(),player);
			break;
		}
	}
	
	@EventHandler
	public void onRightClick(PlayerInteractEvent e) {
		//For the Super Hoe
		//Flood scans a field to harvest and replace full grown crops
		if(!e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
		if(!e.getHand().equals(EquipmentSlot.HAND)) return;
		Block block = e.getClickedBlock();
		Player player = e.getPlayer();
		if(player==null)return;
		ItemStack item = player.getInventory().getItemInMainHand();
		if(!item.hasItemMeta()) return;
		if(!item.getItemMeta().hasLore()) return;
		if(!item.getItemMeta().getLore().contains("Super")) return;
		switch(item.getType()) {
		case NETHERITE_HOE:
			harvestSuper(block,player);
			break;
		}
		
	}
	
	private BlockFace getFaceLookingAt(Location loc) {
		float pitch = loc.getPitch();
		float yaw = loc.getYaw();
		
		if(pitch <= -45) 
			return BlockFace.DOWN;
		else if(pitch >= 45)
			return BlockFace.UP;
		else if(yaw >= 135 || yaw <= -135) 
			return BlockFace.SOUTH;
		else if(yaw >= -135 && yaw <= -45)
			return BlockFace.WEST;
		else if(yaw >= 45 && yaw <= 135)
			return BlockFace.EAST;
		else
			return BlockFace.NORTH;
		
	}
	
	private void mineBlocksSuper(Block block, Player player) {
		World world = player.getWorld();
		BlockFace face = getFaceLookingAt(player.getEyeLocation());
		int x = block.getX();
		int y = block.getY();
		int z = block.getZ();
		//One day I'll change this to be a for loop instead
		switch(face) {
		case UP:
		case DOWN:
			mineBlock(world.getBlockAt(x+1,y,z+1), player);
			mineBlock(world.getBlockAt(x,y,z+1), player);
			mineBlock(world.getBlockAt(x-1,y,z+1), player);
			mineBlock(world.getBlockAt(x+1,y,z), player);
			mineBlock(world.getBlockAt(x,y,z), player);
			mineBlock(world.getBlockAt(x-1,y,z), player);
			mineBlock(world.getBlockAt(x+1,y,z-1), player);
			mineBlock(world.getBlockAt(x,y,z-1), player);
			mineBlock(world.getBlockAt(x-1,y,z-1), player);
			break;
		case SOUTH:
		case NORTH:
			mineBlock(world.getBlockAt(x+1,y+1,z), player);
			mineBlock(world.getBlockAt(x,y+1,z), player);
			mineBlock(world.getBlockAt(x-1,y+1,z), player);
			mineBlock(world.getBlockAt(x+1,y,z), player);
			mineBlock(world.getBlockAt(x,y,z), player);
			mineBlock(world.getBlockAt(x-1,y,z), player);
			mineBlock(world.getBlockAt(x+1,y-1,z), player);
			mineBlock(world.getBlockAt(x,y-1,z), player);
			mineBlock(world.getBlockAt(x-1,y-1,z), player);
			break;
		case EAST:
		case WEST:
			mineBlock(world.getBlockAt(x,y+1,z+1), player);
			mineBlock(world.getBlockAt(x,y+1,z), player);
			mineBlock(world.getBlockAt(x,y+1,z-1), player);
			mineBlock(world.getBlockAt(x,y,z+1), player);
			mineBlock(world.getBlockAt(x,y,z), player);
			mineBlock(world.getBlockAt(x,y,z-1), player);
			mineBlock(world.getBlockAt(x,y-1,z+1), player);
			mineBlock(world.getBlockAt(x,y-1,z), player);
			mineBlock(world.getBlockAt(x,y-1,z-1), player);
			break;
		}
	}
	
	private void harvestSuper(Block startBlock, Player player) {
		//List of the harvestable crops
		List<Material> crops = new ArrayList<Material>();
		crops.add(Material.WHEAT);
		crops.add(Material.POTATOES);
		crops.add(Material.CARROTS);
		crops.add(Material.BEETROOTS);
		//Cancel if block isn't a crop
		if(!crops.contains(startBlock.getType())) return;
		//Initializing our lists
		ArrayList<Block> blockList = new ArrayList<Block>();
		ArrayList<Block> tempBlockList = new ArrayList<Block>();
		blockList.add(startBlock);
		//Creating a runnable so the server doesn't freeze processing a huge farm
		new BukkitRunnable() {
			int count = 0; //Create count to make sure we don't mine too much
			int harvested = 0;
			public void run() {
				for(Block block: blockList) {
					for(int i = -1; i <= 1;i++) {
						for(int j = -1; j <= 1;j++) {
							for(int k = -1; k <= 1;k++) {
								Block b = block.getRelative(i,j,k);
								if(blockList.size() >= 1024 || tempBlockList.size() >= 1024) {
									plugin.getLogger().info("Super Hoe reached limit of blocks");
									player.sendMessage("Limit reached");
									cancel();
								}
								if(harvested >= 64) cancel();
								if(b != null)
									if(b.getType().toString()!=null) 
										if(crops.contains(b.getType()))
											if(!blockList.contains(b)) 
												if(!tempBlockList.contains(b)) {
													player.sendMessage("added " + b.getType().toString());
													tempBlockList.add(b);
													harvested++;
												}
							}
						}
					}
					harvestBlock(block,player);
					player.sendMessage("on Block: "+ block.getType().toString());
				}
				blockList.clear();
				for(Block block:tempBlockList) {
					blockList.add(block);
				}
				tempBlockList.clear();
				
				//Ends the loop once all logs are iterated through
				if(blockList.isEmpty() || count >= 128 || harvested >= 64) {
					cancel();
				}
				count++;
			}
		}.runTaskTimer(plugin.getPlugin(plugin.getClass()), 0 ,0);
		
	}
	
	private void mineBlock(Block block, Player player) {
		block.breakNaturally(player.getInventory().getItemInMainHand());
	}
	
	private void harvestBlock(Block block, Player player) {
		Material type = block.getType();
		Ageable ageData = (Ageable) block.getBlockData();
		if(ageData.getAge()==ageData.getMaximumAge()) {
			block.breakNaturally(player.getInventory().getItemInMainHand());
			block.setType(type);
			
		}
	}
	
	private void breakTree(Block startBlock, Player player) {
		//Initializing our lists
		ArrayList<Block> blockList = new ArrayList<Block>();
		ArrayList<Block> tempBlockList = new ArrayList<Block>();
		blockList.add(startBlock);
		//Creating a runnable so the server doesn't freeze processing a huge tree
		new BukkitRunnable() {
			int count = 0; //Create count to make sure we don't mine too much
			public void run() {
				for(Block block: blockList) {
					for(int i = -1; i <= 1;i++) {
						for(int j = -1; j <= 1;j++) {
							for(int k = -1; k <= 1;k++) {
								Block b = block.getRelative(i,j,k);
								if(blockList.size() >= 256 ||
										tempBlockList.size() >= 256) {
									plugin.getLogger().info("Super Axe reached limit of blocks");
									player.sendMessage("Limit reached");
									cancel();
									}
								if(b != null)
									if(b.getType().toString()!=null)
										if(b.getType().toString().endsWith("LOG"))
											if(!blockList.contains(b)) 
												if(!tempBlockList.contains(b))
													tempBlockList.add(b);
							}
						}
					}
					mineBlock(block,player);
				}
				blockList.clear();
				for(Block block:tempBlockList) {
					blockList.add(block);
				}
				tempBlockList.clear();
				
				//Ends the loop once all logs are iterated through
				if(blockList.isEmpty() || count >= 64) {
					cancel();
				}
				count++;
			}
		}.runTaskTimer(plugin.getPlugin(plugin.getClass()), 0 ,0);
		
	}

	

}
