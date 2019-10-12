package net.heroyn.heroynapi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.entity.Player;

public class MoneyServerSQL
{
  private String url_base;
  private String host;
  private String name;
  private String username;
  private String password;
  private static String table;
  private static Connection connection;
  
  @SuppressWarnings("static-access")
public MoneyServerSQL(String url_base, String host, String name, String username, String password, String table)
  {
    this.url_base = url_base;
    this.host = host;
    this.name = name;
    this.username = username;
    this.password = password;
    this.table = table;
  }
  
  public void connection()
  {
    if (!isConnected()) {
      try
      {
        connection = DriverManager.getConnection(this.url_base + this.host + "/" + this.name, this.username, this.password);
      }
      catch (SQLException e)
      {
        e.printStackTrace();
      }
    }
  }
  
  public void deconnection()
  {
    if (isConnected()) {
      try
      {
        connection.close();
      }
      catch (SQLException e)
      {
        e.printStackTrace();
      }
    }
  }
  
  private boolean isConnected()
  {
    try
    {
      if ((connection == null) || (connection.isClosed()) || (connection.isValid(5))) {
        return false;
      }
      return true;
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return false;
  }
  
  private static Connection getConnection()
  {
    return connection;
  }
  
  
  //ACTIONS
  
  public void createAcount(Player player)
  {
    try
    {
      PreparedStatement sts = getConnection().prepareStatement("INSERT INTO " + table + " (uuid, money) VALUES (?, ?)");
      sts.setString(1, player.getUniqueId().toString());
      sts.setDouble(2, 2000.0);
      sts.execute();
      sts.close();
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
  }
  
  
  public boolean hasAccount(Player p) {
	  
	    try
	    {
	      PreparedStatement sts = getConnection().prepareStatement("SELECT money FROM " + table + " WHERE uuid = ?");
	      sts.setString(1, p.getUniqueId().toString());
	      ResultSet rs = sts.executeQuery();
	      if (rs.next())
	      {return true;}
	    }
	    catch (SQLException e)
	    {
	      e.printStackTrace();
	    }
	  
	  return false;
  }

  
  public void updateBalance(String uuid , double balance) {
	  
	    try
	    {
	      PreparedStatement sts = getConnection().prepareStatement("UPDATE " + table + " SET money = ? WHERE uuid = ?");
	      sts.setDouble(1, balance);
	      sts.setString(2, uuid);
	      sts.execute();
	      sts.close();
	    }
	    catch (SQLException e)
	    {
	      e.printStackTrace();
	    }
	  
  }

  public double getBalance(String uuid) {
	  
	    try
	    {
	      PreparedStatement sts = getConnection().prepareStatement("SELECT money FROM " + table + " WHERE uuid = ?");
	      sts.setString(1, uuid);
	      ResultSet rs = sts.executeQuery();
      
	      if(rs.next()) {	    	  
	    	  return rs.getDouble(1);
	      }

	      sts.close();
	    }
	    catch (SQLException e)
	    {
	      e.printStackTrace();
	    }
	    
	    return 0;
	  
  }
  
  public void addMoney(String uuid, double amount) {
	  
	  double balance = getBalance(uuid);
	  balance += amount;
	  updateBalance(uuid, balance);	  	  
  }
  
  public void removeMoney(String uuid, double amount) {
	  
	  double balance = getBalance(uuid);
	  balance -= amount;
	  updateBalance(uuid, balance);	  
  }
  
  public void exchangeMoney(String senderUUID, String receiverUUID, double amount) {
	  
	  removeMoney(senderUUID, amount);
	  addMoney(receiverUUID, amount);  
  }









}
