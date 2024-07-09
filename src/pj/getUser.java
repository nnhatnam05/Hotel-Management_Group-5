/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pj;

import java.util.Date;
import javafx.scene.text.Text;

/**
 *
 * @author ADMIN
 */
public class getUser {
    private Integer id;
    private String username;
    private String fullname;
    private String pass;
    private String mail;
    private String address;
    private Integer phone;
    private Integer role;
    
    
    
    
    public getUser(Integer id, String username, String fullname, String pass, String mail, String address, Integer phone, Integer role){
        
        this.id = id;
        this.username = username;
        this.fullname = fullname;
        this.pass = pass;
        this.mail = mail;
        this.address = address;
        this.phone = phone;
        this.role = role;
        
    }
    
    public Integer getId(){
        return id;
    }
    public String getUsername(){
     return username;   
    }
    public String getFullname(){
        return fullname;
    }
    public String getPass(){
        return pass;
    }
    public String getMail(){
        return mail;
    }
    public String getAddress(){
        return address;
    }
    public Integer getPhone(){
        return phone;
    }
    public Integer getRole(){
        return role;
    }
}
