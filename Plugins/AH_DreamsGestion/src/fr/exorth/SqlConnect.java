package fr.exorth;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;


public class SqlConnect {
	
	private Connection connection;
	private String urlbase,host,database,user,pass;

	public SqlConnect(String urlbase, String host, String database, String user, String pass) {
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
				Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Connected ok");
			} catch (SQLException e) {
				e.printStackTrace();
				Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Echec connection");
			}
		}		
	}

	public void disconnect() {
		if(isConnected()){
			try {
				connection.close();
				Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "connected off");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
	}
	
	public boolean isConnected(){
		return connection != null;
	}
	
	public boolean hasAccount(Player player){
		
		try {
			PreparedStatement q = connection.prepareStatement("SELECT uuid FROM dreams WHERE uuid = ?");
			q.setString(1, player.getUniqueId().toString());
			ResultSet resultat = q.executeQuery();
			boolean hasAccount = resultat.next();
			q.close();
			
			return hasAccount;
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public void createNewAccount(Player player){
		if(!hasAccount(player)){
			
			try {
				PreparedStatement q = connection.prepareStatement("INSERT INTO dreams(uuid,dreams) VALUES (?,?)");
				q.setString(1, player.getUniqueId().toString());
				q.setDouble(2, 1500.00);
				q.execute();
				q.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public double getBalance(Player player){
		
		try {
			PreparedStatement q = connection.prepareStatement("SELECT dreams FROM dreams WHERE uuid = ?");
			q.setString(1, player.getUniqueId().toString());
			
			double balance = 0;
			ResultSet rs = q.executeQuery();
			
			while(rs.next()){
				balance = rs.getDouble("dreams");
			}
			
			q.close();
			
			return balance;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	public void addMoney(Player player, double amount){
		
		double balance = getBalance(player);
		double newbalance = balance + amount;
       
        try {
            PreparedStatement rs = connection.prepareStatement("UPDATE dreams SET dreams = ? WHERE uuid = ?");
            rs.setDouble(1, newbalance);
            rs.setString(2, player.getUniqueId().toString());
            rs.executeUpdate();
            rs.close();
   
        } catch (SQLException e) {
            e.printStackTrace();
        }
		
	}
	
	public void removeMoney(Player player, double amount){
		
		double balance = getBalance(player);
		double newbalance = balance - amount;
       
        if(newbalance <= 0){
            return;
        }
       
        try {
            PreparedStatement rs = connection.prepareStatement("UPDATE dreams SET dreams = ? WHERE uuid = ?");
            rs.setDouble(1, newbalance);
            rs.setString(2, player.getUniqueId().toString());
            rs.executeUpdate();
            rs.close();
   
        } catch (SQLException e) {
            e.printStackTrace();
        }
		
	}



}
