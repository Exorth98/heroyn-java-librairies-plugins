package net.heroyn.heroynapi;

import net.heroyn.heroynapi.structures.StructureAPICommand;
import org.bukkit.plugin.java.JavaPlugin;

import net.heroyn.heroynapi.manager.ListenerManager;

public class HeroynAPI extends JavaPlugin {

	static HeroynAPI instance;
	static UserServeurSQL userInstance;
	static MoneyServerSQL moneyInstance;



	@Override
	public void onEnable() {
		instance = this;
		//userInstance = new UserServeurSQL("jdbc:mysql://", "ni-web-01.srv.nihost.fr:208", "heroynne_epistory", "heroynne_hingray", "thomashin90", "user_serveur");
		//userInstance.connection();
		
		moneyInstance = new MoneyServerSQL("jdbc:mysql://", "sql-7.verygames.net:3306", "db588976", "db588976", "9n4ey9jtf", "money");
		moneyInstance.connection();

		getCommand("structureapi").setExecutor(new StructureAPICommand());

		new ListenerManager(this);

	}

	@Override
	public void onDisable() {

	}
	
	public static UserServeurSQL getUserInstance() {
		return userInstance;
	}
	
	public static MoneyServerSQL getMoneyInstance() {
		return moneyInstance;
	}
	
	public static HeroynAPI getInstance() {
		return instance;
	}

}
