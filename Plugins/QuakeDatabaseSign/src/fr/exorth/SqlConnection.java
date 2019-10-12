package fr.exorth;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlConnection {
	
	private Connection connection;
	private String urlbase,host,database,user,pass;

	public SqlConnection(String urlbase, String host, String database, String user, String pass) {
		this.urlbase = urlbase;
		this.host = host;
		this.database = database;
		this.user = user;
		this.pass = pass;
		
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
	
	public int getplayers(String arenaname){
		
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
	
	public int getmaxplayers(String arenaname){
		
		try {
			PreparedStatement q = connection.prepareStatement("SELECT joueursmax FROM arenes WHERE nom = ?");
			q.setString(1, arenaname);
			
			int joueursmax = 0;
			ResultSet rs = q.executeQuery();
			
			while(rs.next()){
				joueursmax = rs.getInt("joueursmax");
			}
			
			q.close();
			
			return joueursmax;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	public String getstate(String arenaname){
		
		try {
			PreparedStatement q = connection.prepareStatement("SELECT etat FROM arenes WHERE nom = ?");
			q.setString(1, arenaname);
			
			String state = " ";
			ResultSet rs = q.executeQuery();
			
			while(rs.next()){
				state = rs.getString("etat");
			}
			
			q.close();
			
			return state;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return " ";
	}

	public void createArena(String arenaname, int joueursmax) {
		
		try {
			PreparedStatement q = connection.prepareStatement("INSERT INTO arenes(nom,joueursmax) VALUES (?,?)");
			q.setString(1,arenaname);
			q.setInt(2,joueursmax);
			q.execute();
			q.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public void delete(String arenaname) {
		
		try {
			PreparedStatement q = connection.prepareStatement("DELETE FROM arenes WHERE nom = ?");
			q.setString(1,arenaname);
			q.execute();
			q.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
