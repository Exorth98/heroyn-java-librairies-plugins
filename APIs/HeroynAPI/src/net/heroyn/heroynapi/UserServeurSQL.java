package net.heroyn.heroynapi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.entity.Player;

public class UserServeurSQL
{
  private String url_base;
  private String host;
  private String name;
  private String username;
  private String password;
  private static String table;
  private static Connection connection;
  
  @SuppressWarnings("static-access")
public UserServeurSQL(String url_base, String host, String name, String username, String password, String table)
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
  
  public void createAcount(Player player)
  {
    try
    {
      PreparedStatement sts = getConnection().prepareStatement("SELECT online FROM " + table + " WHERE uuid = ?");
      sts.setString(1, player.getUniqueId().toString());
      ResultSet rs = sts.executeQuery();
      if (!rs.next())
      {
        sts.close();
        sts = getConnection().prepareStatement("INSERT INTO " + table + " (uuid, online) VALUES (?, ?)");
        sts.setString(1, player.getUniqueId().toString());
        sts.setBoolean(2, true);
        sts.executeUpdate();
        sts.close();
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
  }
}
