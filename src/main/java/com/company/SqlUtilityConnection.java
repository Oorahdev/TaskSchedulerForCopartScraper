package com.company;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Consumer;

/**
 * Created by slan on 12/4/2017.
 */
public class SqlUtilityConnection {


    ArrayList<String> list = consumer.getCurrentLotNumbers();
    public static void sqlQueryExecute(String query) {

        Connection conn = null;
        Statement st = null;

        try {
            conn = getConn();
            st = conn.createStatement();
            st.executeQuery(query);

        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {

            try {
                if (st != null) {
                    st.close();

                }
                if (conn != null) {
                    conn.close();
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public static Connection getConn() {
                String db_connect_string = "jdbc:jtds:sqlserver://oorah-admire03:1433/AdmireTempData/";
                Connection conn = null;
                try {

                    Class.forName("net.sourceforge.jtds.jdbc.Driver");
                    conn = DriverManager.getConnection(db_connect_string, "yunion", "421kirby#");

                } catch (ClassNotFoundException | SQLException e) {
                    e.printStackTrace();
                }
                return conn;
            }

        //String insert = "INSERT INTO ArrayListBackupForCopartNotes  Values('" + element +"')";
        //String select = "Select * from ArrayListBackupForCopartNotes";
        //String truncate = "Truncate * from ArrayListBackupForCopartNotes";

    public static void sqlQueryUpdate(String query, String catalog) {
        Connection conn = null;
        Statement st = null;
        try {
            conn = getConn();
            conn.setCatalog(catalog);
            st = conn.createStatement();
            System.out.println(query);
            st.executeUpdate(query);

        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            try {
                if (st != null) {
                    st.close();
                }
                if (conn != null) {
                    conn.close();
                }
            }
            catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static ResultSet sqlGetResult(String query) {
        ResultSet rs = null;
        Connection conn = null;
        Statement st = null;
        try {
            conn = getConn();

            st = conn.createStatement();
            System.out.println(query);
            rs = st.executeQuery(query);
        }
        catch (SQLException ex) {
           ex.printStackTrace();
        }
        finally {
            try {
                if (st != null) {
                    st.close();
                }
                if (conn != null) {
                    conn.close();
                }
            }
            catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return rs;
    }
    public interface executeJob {
        void func(ResultSet resultSet);
    }



    }







