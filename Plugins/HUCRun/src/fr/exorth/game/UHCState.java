package fr.exorth.game;

public enum UHCState {
	
	//Enumeration des status de la game possible 
	WAIT(true) , PREGAME(false) , GAME(false), GAMEPVP(false) , FINISH(false);
	
	//ON place les Field
	private boolean canJoin;
	private static UHCState currentState;
	
	
	private UHCState(boolean canJoin) {
		this.canJoin = canJoin;
	}
	
	//On verifie si les joueurs peuvent rejoindre le jeu suivant l'enum
	public boolean canJoin(){
		return canJoin;
	}
	
	//modifier le state
	public static void setState(UHCState state){
		UHCState.currentState = state;
	}
	
	//est-ce a ce state ?
	public static boolean isState(UHCState state){
		return UHCState.currentState == state;
	}
	
	//recuperer le state
	public static UHCState getState(){
		return currentState;
	}

}
