package fr.exorth.game;

import fr.exorth.Quake;

public enum QuakeState {
	
	//Enumeration des status de la game possible 
	WAIT(true) , GAME(false) , FINISH(false) , RESTARTING(false);
	
	//ON place les Field
	private boolean canJoin;
	private static QuakeState currentState;
	
	private QuakeState(boolean canJoin) {
		this.canJoin = canJoin;
	}
	
	//On verifie si les joueurs peuvent rejoindre le jeu suivant l'enum
	public boolean canJoin(){
		return canJoin;
	}
	
	//modifier le state
	public static void setState(QuakeState state){
		QuakeState.currentState = state;
		Quake.sql.setState();
	}
	
	//est-ce a ce state ?
	public static boolean isState(QuakeState state){
		return QuakeState.currentState == state;
	}
	
	//recuperer le state
	public static QuakeState getState(){
		return currentState;
	}

}
