/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DB;

import java.sql.Connection;

/**
 *
 * @author ADMIN
 */
public class ConnectDB {
     public static Connection getConnectDB(){ 
        try { 
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); 
            Connection conn=java.sql.DriverManager.getConnection("jdbc:sqlserver://localhost:1433; databaseName=DBQLKS2; user=sa; password=123456789; encrypt=false"); 
            return conn; 
        } catch (Exception e) { 
            e.printStackTrace();
        }
        return null; 
    } 
}
