package net.heroyn.mobarena.commands;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.heroyn.mobarena.armorstands.MaClassArmorstand;
import net.heroyn.mobarena.classe.ClasseBase;
import net.heroyn.mobarena.classe.ClassesInfos;
import net.heroyn.mobarena.utils.Arena;
import net.heroyn.mobarena.utils.HeroynMobarenaUtils;

public class maconfCommand implements CommandExecutor {

	@SuppressWarnings({ "deprecation", "unchecked", "rawtypes" })
	@Override
	public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
		
		if(cmd.getName().equals("maconf")) {
			
			if(s instanceof Player) {
				
				Player p = (Player) s;
				
				
				//Aucun argument --> aide
				if(args.length == 0) {
					
					p.sendMessage("�e========= MobArena Configuration ==========");
					p.sendMessage("");
					p.sendMessage("�ePas besoin de pr�ciser le nom de l'ar�ne si la zone est configur�e et que vous �tes dedans");
					p.sendMessage("");
					p.sendMessage("�e/maconf cubzone setpos1 <arena>");
					p.sendMessage("�e/maconf setkit <Class>");
					p.sendMessage("�e/maconf cubzone setpos2 <arena>");
					p.sendMessage("�c A VENIR : /maconf setcylzone <arena> <raduis> <height> ");
					p.sendMessage("�e/maconf create <arena> <cuboid/cyl>");
					p.sendMessage("�e/maconf delete [arena]");
					p.sendMessage("�e/maconf <enable/disable> [arena]");
					p.sendMessage("�e/maconf setspectate [arena]");
					p.sendMessage("�e/maconf setsuitup [arena]");
					p.sendMessage("�e/maconf addspawn [arena]");
					p.sendMessage("�e/maconf addbonusbutton [arena] , puis taper le bouton");
					p.sendMessage("�e/maconf addlootpoint [arena]");
					p.sendMessage("�e/maconf addbonus [arena], bonus en main");
					p.sendMessage("�e/maconf addloot [arena], bonus en main");
					p.sendMessage("�e/maconf addas <Class Name>");
					p.sendMessage("");
					p.sendMessage("�e/maconf check [arena]");
					p.sendMessage("");
					p.sendMessage("�e===========================================");
				}
				
				//Max arguments dans les commandes = 4
				else if(args.length < 4) {
					
					
					if(args[0].equalsIgnoreCase("addas")) {
						
						if(args.length == 2) {
							
							String className = args[1];
							if(HeroynMobarenaUtils.classExists(className)){
								
								ClasseBase clas = ClassesInfos.getClasseBase(className, p, 1);
								ArmorStand as = ((ArmorStand) p.getWorld().spawn(p.getLocation(), (Class)ArmorStand.class));
								as.setCustomName("�a"+className);
								as.setCustomNameVisible(true);
								as.setBasePlate(false);
								as.setItemInHand((clas.getSecondWeapon().getType() != Material.AIR ? clas.getSecondWeapon() : clas.getWeapon()));
								as.setBoots(clas.getArmor()[0]);
								as.setLeggings(clas.getArmor()[1]);
								as.setChestplate(clas.getArmor()[2]);
								as.setHelmet(clas.getArmor()[3]);
								as.setArms(true);
								as.setCollidable(false);
								as.setGravity(false);
								as.setCanPickupItems(false);
								as.setInvulnerable(true);
								
								MaClassArmorstand maAs = new MaClassArmorstand(className, p.getLocation());
								maAs.register();
								maAs.registerInConfig();									
								
								p.sendMessage("�aArmorstand ajout�e !");
								
							}else {
								p.sendMessage("�cCette classe n'existe pas");
							}
						}else {
							p.sendMessage("�c/maconf addas <Class Name>");
						}
					}
					//Si c'est cubzone le 1er argument
					else if(args[0].equalsIgnoreCase("cubzone")) {
						
						if(args.length == 3) {
							
							if(Arena.exists(args[2])){
								
								Arena arena = new Arena(args[2]);
								
								if(args[1].equalsIgnoreCase("setpos1")) {
									
									Location pos1 = p.getLocation();
									arena.setCuboidPos1(pos1);	
									p.sendMessage("�aPosition 1 d�finie � votre position");																		
								}
								else if(args[1].equalsIgnoreCase("setpos2")) {
									
									Location pos2 = p.getLocation();
									arena.setCuboidPos2(pos2);		
									p.sendMessage("�aPosition 2 d�finie � votre position");								
								}
								else {
									p.sendMessage("�e/maconf cubzone setpos1");
									p.sendMessage("�e/maconf cubzone setpos2");							
								}
							}else {
								p.sendMessage("�cCette ar�ne n'existe pas");
							}
						}else {
							p.sendMessage("�e/maconf cubzone setpos1");
							p.sendMessage("�e/maconf cubzone setpos2");							
						}

		
					}
					else if(args[0].equalsIgnoreCase("create")) {
						
						if(args[2].equalsIgnoreCase("cuboid") || args[2].equalsIgnoreCase("cyl")) {
							
							String name = args[1];
							String type = args[2].toUpperCase();
							
							if(!Arena.exists(name)) {
								
								if(type.equals("CUBOID")) {
									
									new Arena(name,type).save();
									p.sendMessage("�aAr�ne �b" + name + " �acr�e (�b" + type + "�a)");
								}
								
								else p.sendMessage("�cAr�nes cylindriques � venir");
								
								
							}else {
								
								p.sendMessage("�cUne ar�ne avec ce nom exist d�j�");
								
							}
						}else {
							p.sendMessage("�cMerci de pr�ciser un type de zone correct : Cuboid | cyl");
						}
						
					}
					else if(args[0].equalsIgnoreCase("setkit")) {
						
						if(args.length == 2) {
							
							String clas = args[1];
							
							if(HeroynMobarenaUtils.classExists(clas)) {
								
								if(p.getItemInHand() != null) {
									
									if(p.getItemInHand().getType() != Material.AIR) {
										
										ItemStack weapon = p.getEquipment().getItemInMainHand();
										ItemStack sWeapon = p.getEquipment().getItemInOffHand();
										ItemStack[] armor = p.getInventory().getArmorContents();
										ItemStack[] contents = p.getInventory().getContents();
										ClasseBase.setKit(weapon, sWeapon, armor, contents, clas);
										
										p.sendMessage("�aKit mis � jour selon votre inventaire pour la classe �b" + clas);
										
									}else {
										p.sendMessage("Merci de prendre l'arme du kit en main");
									}									
								}else {
									p.sendMessage("Merci de prendre l'arme du kit en main");
								}															
							}else {
								p.sendMessage("�cCe kit n'existe pas");
							}													
						}else {
							p.sendMessage("�c/maconf setkit <Class>");
						}
						
						
					}
					//sinon, il s'agit d'une commande ou le troisi�me argument optionnel peut repr�senter l'ar�ne					
					else {
						
						// On r�cup�re l'ar�ne en fonction de si elle est pr�cis�e, sinon si le joueur est dans une ar�ne en particulier
						String arenaSt;
						if(args.length== 2) arenaSt = args[1];
						else arenaSt = "X";
						
						Arena arena = Arena.getArena(arenaSt, p);
						
						//On v�rifie sa validit�
						if(arena != null) {
							
							if(args[0].equalsIgnoreCase("delete")) {						
								
								arena.delete();
								p.sendMessage("�aAr�ne supprim�e");
								
							}
							else if(args[0].equalsIgnoreCase("setspectate")) {
								
								Location spectateLoc = p.getLocation();
								arena.setSpectateLocation(spectateLoc);
								p.sendMessage("�aPosition sp�ctateur d�finie � votre position");
								
							}
							else if(args[0].equalsIgnoreCase("setsuitup")) {
								
								Location suitupLoc = p.getLocation();
								arena.setSuitupLocation(suitupLoc);
								p.sendMessage("�aPosition d'�quipement d�finie � votre position");
								
							}
							else if(args[0].equalsIgnoreCase("addspawn")) {
								
								Location spawnPointLoc = p.getLocation();
								arena.addSpawnPoint(spawnPointLoc);
								p.sendMessage("�aPoint de spawn ajout� � votre position");
								
							}
							else if(args[0].equalsIgnoreCase("addlootpoint")) {
								
								Location lootLoc = p.getLocation();
								arena.addLootLocation(lootLoc);
								p.sendMessage("�aPoint de loot ajout� � votre position");
								
							}
							else if(args[0].equalsIgnoreCase("addbonusbutton")) {
								
								p.sendMessage("�aTu as 5 secondes pour appuyer sur le bouton � configurer");
								HeroynMobarenaUtils.configureButton(p, arena);
								
							}
							else if(args[0].equalsIgnoreCase("enable")) {
								
								arena.setEnable(true);
								p.sendMessage("�aAr�ne activ�e");
								
							}
							else if(args[0].equalsIgnoreCase("disable")) {
								
								arena.setEnable(false);
								p.sendMessage("�aAr�ne d�sactiv�e");
								
							}
							else if(args[0].equalsIgnoreCase("addbonus")) {
								
								ItemStack item = p.getItemInHand();
								if(item != null) {
									
									if(item.getType() != Material.AIR) {
										
										arena.addBonusItem(item);
										p.sendMessage("�aL'item dans votre main a �t� ajout� comme Bonus");
										
									}else {
										p.sendMessage("�cMerci de prendre l'item en main");
									}
								}else {
									p.sendMessage("�cMerci de prendre l'item en main");
								}
							}
							else if(args[0].equalsIgnoreCase("addloot")) {
								
								ItemStack item = p.getItemInHand();
								if(item != null) {
									
									if(item.getType() != Material.AIR) {
										
										arena.addLootItem(item);
										p.sendMessage("�aL'item dans votre main a �t� ajout� comme loot");
										
									}else {
										p.sendMessage("�cMerci de prendre l'item en main");
									}
								}else {
									p.sendMessage("�cMerci de prendre l'item en main");
								}
								
							}
							else if(args[0].equalsIgnoreCase("check")) {
								
								p.sendMessage(arena.display());
								
							}
							else {
								p.performCommand("maconf");
							}
							
						}else {
							p.sendMessage("�cMerci de te placer dans une zone d'ar�ne configur�e ou de pr�ciser un nom d'ar�ne valide");
						}

						
					}
									
				}
				else if(args.length == 4) {

					if(args[0].equalsIgnoreCase("setcylzone")) {
						
						p.sendMessage("�cZone sycindrique non dispo pour l'instant");
						
						//CYLZONE
						
					
					//Aucune correspondance --> aide
					}else {
						p.performCommand("maconf");
					}
					
					
				
				//Trop d'arguments --> aide
				}else {
					
					p.performCommand("maconf");
				}
				
			}else {
				s.sendMessage("�cCommande reserv�e aux joueurs");
			}
			
			
		}
		
		
		return false;
	}

}
