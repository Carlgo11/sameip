package com.carlgo11.sameip;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;

public class Mysql {

    public static String url;
    public static String username;
    public static String password;
    public static String database;
    public static String table;

    public static void updateStrings(String url, String username, String password, String database, String table)
    {
        Mysql.url = url;
        Mysql.username = username;
        Mysql.password = password;
        Mysql.database = database;
        Mysql.table = table;

    }

    public static void createTables()
    {
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            con = DriverManager.getConnection(Mysql.url + Mysql.database, Mysql.username, Mysql.password);
            st = con.createStatement();
            st.execute("CREATE TABLE IF NOT EXISTS " + Mysql.database + "." + Mysql.table + " (player text, ip text);");
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Mysql.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (con != null) {
                    con.close();
                }

            } catch (SQLException ex) {
                Logger lgr = Logger.getLogger(Mysql.class.getName());
                lgr.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
    }

    public static String getIp(String ip)
    {
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            con = DriverManager.getConnection(Mysql.url + Mysql.database, Mysql.username, Mysql.password);
            st = con.createStatement();
            rs = st.executeQuery("SELECT * from " + Mysql.table);
            while (true) {
                if (rs.next()) {
                    if (rs.getString(2).equalsIgnoreCase(ip)) {
                        return rs.getString(1).toString();
                    }
                } else {
                    break;
                }
            }
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Mysql.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (con != null) {
                    con.close();
                }

            } catch (SQLException ex) {
                Logger lgr = Logger.getLogger(Mysql.class.getName());
                lgr.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
        return null;
    }

    public static boolean getPlayer(String player)
    {
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            con = DriverManager.getConnection(Mysql.url + Mysql.database, Mysql.username, Mysql.password);
            st = con.createStatement();
            rs = st.executeQuery("SELECT * from " + Mysql.table);
            while (true) {
                if (rs.next()) {
                    if (rs.getString(1).equalsIgnoreCase(Bukkit.getOfflinePlayer(player).getUniqueId().toString())) {
                        return true;
                    }
                } else {
                    break;
                }
            }
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Mysql.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (con != null) {
                    con.close();
                }

            } catch (SQLException ex) {
                Logger lgr = Logger.getLogger(Mysql.class.getName());
                lgr.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
        return false;
    }

    public static void addPlayer(String player, String ip)
    {
        Connection con = null;
        Statement st = null;

        try {
            con = DriverManager.getConnection(url + database, Mysql.username, Mysql.password);
            st = con.createStatement();
            PreparedStatement ps = con.prepareStatement("INSERT INTO " + Mysql.table + " (`player`, `ip`) VALUES (?, ?);");
            ps.setString(1, Bukkit.getOfflinePlayer(player).getUniqueId().toString());
            ps.setString(2, ip);
            ps.execute();

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Mysql.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        } finally {
            try {
                if (st != null) {
                    st.close();
                }
                if (con != null) {
                    con.close();
                }

            } catch (SQLException ex) {
                Logger lgr = Logger.getLogger(Mysql.class.getName());
                lgr.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
    }
}
