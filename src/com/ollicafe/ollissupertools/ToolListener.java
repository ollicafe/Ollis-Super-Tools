package com.ollicafe.ollissupertools;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class ToolListener implements Listener{
	
	
	OllisSuperTools plugin;
	
	public ToolListener(OllisSuperTools plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPickaxe(BlockBreakEvent e) {
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
	
	private void harvestSuper() {
		
	}
	
	private void mineBlock(Block block, Player player) {
		block.breakNaturally(player.getInventory().getItemInMainHand());
	}
	
	private void breakTree(Block startBlock, Player player) {
		

		ArrayList<Block> blockList = new ArrayList<Block>();
		ArrayList<Block> tempBlockList = new ArrayList<Block>();
		blockList.add(startBlock);
		new BukkitRunnable() {
			BlockFace faces[] = {BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST,
					BlockFace.UP, BlockFace.DOWN};
			int count = 0;
			public void run() {
				for(Block block: blockList) {
					for(int i = -1; i <= 1;i++) {
						for(int j = -1; j <= 1;j++) {
							for(int k = -1; k <= 1;k++) {
								Block b = block.getRelative(i,j,k);
								if(b != null)
									if(b.getType().toString()!=null)
										if(b.getType().toString().endsWith("LOG"))
											if(!blockList.contains(b)) 
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
				player.sendMessage("Next Loop");
				//Ends the loop once all logs are iterated through
				if(blockList.isEmpty()) {
					cancel();
					player.sendMessage("Done");
				}
				count++;
			}
		}.runTaskTimer(plugin.getPlugin(plugin.getClass()), 0 ,0);
		
	}
	

}
