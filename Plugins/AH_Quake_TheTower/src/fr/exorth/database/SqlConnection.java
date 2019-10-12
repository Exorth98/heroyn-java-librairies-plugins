package fr.exorth.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.exorth.game.QuakeState;

public class SqlConnection {

	private Connection connection;
	private String urlbase,host,database,user,pass;
	private String arenaname;

	public SqlConnection(String urlbase, String host, String database, String user, String pass,String arenaname) {
		this.urlbase = urlbase;
		this.host = host;
		this.database = database;
		this.user = user;
		this.pass = pass;
		this.arenaname = arenaname;
		
	}

	public void connection() {
		if(!isConnected()){
			try {
				connection = DriverManager.getConnection(urlbase + host + "/" + database, user, pass);
				System.out.println("Connected ok");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
	}

	public void disconnect() {
		if(isConnected()){
			try {
				connection.close();
				System.out.println("connected off");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
	}
	
	public boolean isConnected(){
		return connection != null;
	}
	
	public int getplayers(){
		
		try {
			PreparedStatement q = connection.prepareStatement("SELECT joueurs FROM arenes WHERE nom = ?");
			q.setString(1, arenaname);
			
			int joueurs = 0;
			ResultSet rs = q.executeQuery();
			
			while(rs.next()){
				joueurs = rs.getInt("joueurs");
			}
			
			q.close();
			
			return joueurs;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	public void setPlayers(int nbr){
		
       
        try {
            PreparedStatement rs = connection.prepareStatement("UPDATE arenes SET joueurs = ? WHERE nom = ?");
            rs.setInt(1, nbr);
            rs.setString(2, arenaname);
            rs.executeUpdate();
            rs.close();
   
        } catch (SQLException e) {
            e.printStackTrace();
        }
		
	}
	
	public void setState(){
		
        String state = QuakeState.getState().toString();
       
        try {
            PreparedStatement rs = connection.prepareStatement("UPDATE arenes SET etat = ? WHERE nom = ?");
            rs.setString(1, state);
            rs.setString(2, arenaname);
            rs.executeUpdate();
            rs.close();
   
        } catch (SQLException e) {
            e.printStackTrace();
        }
		
	}

}
