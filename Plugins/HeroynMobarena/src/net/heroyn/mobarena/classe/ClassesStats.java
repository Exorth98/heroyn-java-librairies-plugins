package net.heroyn.mobarena.classe;

public enum ClassesStats {
	
	
	
//	 CLASS_LVL  		NAME    LEVEL   HEALTH   RESISTANCE    DAMAGE    SECONDARY DAMAGE  SPEED  JUMP  FIRE_RESISTANCE
//					|		   |     |         |           |           |		          |      |     |                |
	MAGE_LVL_1		(  "Mage"  ,  1  ,  30.00  ,    0.05   ,   05.00   ,       00.00      ,  0   ,  0  ,      true      ),
	MAGE_LVL_2		(  "Mage"  ,  2  ,  33.00  ,    0.09   ,   06.50   ,       00.00      ,  1   ,  1  ,      true      ),
	MAGE_LVL_3		(  "Mage"  ,  3  ,  36.00  ,    0.14   ,   08.00   ,       00.00      ,  2   ,  2  ,      true      ),
	MAGE_LVL_4		(  "Mage"  ,  4  ,  40.00  ,    0.19   ,   09.50   ,       00.00      ,  2   ,  2  ,      true      ),
	MAGE_LVL_5		(  "Mage"  ,  5  ,  44.00  ,    0.25   ,   12.00   ,       00.00      ,  3   ,  3  ,      true      ),
//					|		   |     |         |           |           |		          |      |     |                |
//					|		   |     |         |           |           |		          |      |     |                |
	TANK_LVL_1		(  "Tank"  ,  1  ,  35.00  ,    0.10   ,   05.00   ,       00.00      ,  -1  ,  -1 ,      false     ),
	TANK_LVL_2		(  "Tank"  ,  2  ,  45.00  ,    0.30   ,   06.00   ,       00.00      ,  -1  ,  -1 ,      false     ),
	TANK_LVL_3		(  "Tank"  ,  3  ,  55.00  ,    0.50   ,   07.00   ,       00.00      ,  -1  ,  -1 ,      false     ),
	TANK_LVL_4		(  "Tank"  ,  4  ,  65.00  ,    0.70   ,   08.00   ,       00.00      ,  -1  ,  -1 ,      false     ),
	TANK_LVL_5		(  "Tank"  ,  5  ,  80.00  ,    0.90   ,   10.00   ,       00.00      ,   0  ,   0 ,      false     ),
//					|		   |     |         |           |           |		          |      |     |                |
//					|		   |     |         |           |           |		          |      |     |                |
	KNIGHT_LVL_1	( "Knight" ,  1  ,  30.00  ,    0.05   ,   05.00   ,       00.00      ,  -1  ,  -1 ,      false     ),
	KNIGHT_LVL_2	( "Knight" ,  2  ,  33.00  ,    0.12   ,   09.00   ,       00.00      ,  -1  ,  -1 ,      false     ),
	KNIGHT_LVL_3	( "Knight" ,  3  ,  36.00  ,    0.30   ,   14.00   ,       00.00      ,  -1  ,  -1 ,      false     ),
	KNIGHT_LVL_4	( "Knight" ,  4  ,  40.00  ,    0.38   ,   19.00   ,       00.00      ,   0  ,   0 ,      false     ),
	KNIGHT_LVL_5	( "Knight" ,  5  ,  44.00  ,    0.50   ,   25.00   ,       00.00      ,   1  ,   1 ,      false     ),
//					|		   |     |         |           |           |		          |      |     |                |
//					|		   |     |         |           |           |		          |      |     |                |
	RANGER_LVL_1	( "Ranger" ,  1  ,  30.00  ,    0.05   ,   03.00   ,       05.00      ,   0  ,  -1 ,      false     ),
	RANGER_LVL_2	( "Ranger" ,  2  ,  33.00  ,    0.12   ,   04.00   ,       06.50      ,   0  ,  -1 ,      false     ),
	RANGER_LVL_3	( "Ranger" ,  3  ,  36.00  ,    0.30   ,   05.00   ,       08.00      ,   1  ,   0 ,      false     ),
	RANGER_LVL_4	( "Ranger" ,  4  ,  40.00  ,    0.38   ,   06.00   ,       09.50      ,   1  ,   0 ,      false     ),
	RANGER_LVL_5	( "Ranger" ,  5  ,  44.00  ,    0.50   ,   07.00   ,       12.00      ,   2  ,   1 ,      false     );	
	
	
	
	private 	String 		name;
	private 	int 		level;
	private 	double 		life;
	private 	double 		resistance;
	private 	double 		damage;
	private 	double 		secondaryDamage;
	private 	int 		speed;
	private 	int 		jump;
	private 	boolean 	fireResistance;
	
	private ClassesStats(String name, int level, double life, double resistance, double damage,double sDamage, int speed, int jump, boolean fireResistance){
		this.name = name;
		this.level = level;
		this.life = life;
		this.resistance = resistance;
		this.damage = damage;
		this.secondaryDamage = sDamage;
		this.speed = speed;
		this.jump = jump;
		this.fireResistance = fireResistance;
	}

	public String getName() {
		return name;
	}

	public int getLevel() {
		return level;
	}

	public double getLife() {
		return life;
	}

	public double getResistance() {
		return resistance;
	}

	public double getDamage() {
		return damage;
	}
	
	public double getSecondaryDamage() {
		return secondaryDamage;
	}

	public int getSpeed() {
		return speed;
	}

	public int getJump() {
		return jump;
	}

	public boolean isFireResistant() {
		return fireResistance;
	}
	
	public static ClassesStats getStats(String name, int level) {
		
		ClassesStats stats = null;	
		
		for(ClassesStats classStats : ClassesStats.values())		
			if(classStats.getName().equals(name) && classStats.getLevel() == level)
				stats = classStats;
		
		return stats;		
	}
	
	
	

}
