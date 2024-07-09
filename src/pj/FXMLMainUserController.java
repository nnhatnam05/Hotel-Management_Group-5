/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package pj;

import DB.ConnectDB;
import java.beans.Statement;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ADMIN
 */
public class FXMLMainUserController implements Initializable {
    
    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;
    Thuvien tv=new Thuvien();

    @FXML
    private AnchorPane fmainuser;
    
     @FXML
    private Button Edit_btn;

    @FXML
    private Button aboutus_btn;

    @FXML
    private AnchorPane aboutus_form;

    @FXML
    private Button blogs_btn;

    @FXML
    private AnchorPane blogs_form;

    @FXML
    private Button btnaboutus;

    @FXML
    private Button contactus_btn;

    @FXML
    private AnchorPane contactus_form;

    @FXML
    private AnchorPane homepage_form;

    @FXML
    private Button homepage_btn;

    @FXML
    private Button profileuser_btn;

    @FXML
    private AnchorPane profileuser_form;

    @FXML
    private Button roomdetail_btn;

    @FXML
    private AnchorPane roomdetail_form;

    @FXML
    private TextField txt_address;

    @FXML
    private TextField txt_phone;

    @FXML
    private TextField txt_email;

    @FXML
    private TextField txt_fullname;

    @FXML
    private TextField txt_username;

    @FXML
    private TextField txt_password;

    @FXML
    private TextField txt_role;
    
    @FXML
    private Button SignOut_btn;
    
     @FXML
    private Label user_name;
     
     @FXML
    private Label user_name_profile;

    public void displayUsername(){
        
        user_name.setText(getData.username);

        user_name_profile.setText(getData.username);
    }
    


    
    
    public void initialize() {
        loadUserData();
    }
       
    private void loadUserData() {
        String sql = "SELECT * FROM tbl_account WHERE ID = 17"; // Thay ID = 1 bằng ID của user bạn muốn load
        connect = ConnectDB.getConnectDB();
        try {
                
                prepare = connect.prepareStatement(sql);
                result = prepare.executeQuery();
            
            if (result.next()) {
                txt_username.setText(result.getString("Username"));
                txt_fullname.setText(result.getString("fullname"));
                txt_password.setText(result.getString("Password"));
                txt_email.setText(result.getString("Email"));
                txt_address.setText(result.getString("Address"));
                txt_phone.setText(result.getString("Phone"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
        public void profileUserEdit() {
        
        String username1 = txt_username.getText();
        String fullname1 = txt_fullname.getText();
        String password1 = txt_password.getText();
        String email1 = txt_email.getText();
        String address1 = txt_address.getText();
        String phone1 = txt_phone.getText();
        

        String sql = "UPDATE tbl_account SET Username = '" + username1 + "',"
                + "fullname = '" + fullname1 + "',"
                + "Password = '" + password1 + "',"
                + "Email = '" + email1 + "', "
                + "Address= '" + address1 + "',"
                + "Phone = '" + phone1 + "'"
                + "WHERE ID = 17 ";

        connect = ConnectDB.getConnectDB();

        try {

            Alert alert;

            if (username1 == null || fullname1 == null || password1 == null || email1 == null || address1 == null || phone1 == null) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please Select The Data First");
                alert.showAndWait();
            } else {
                prepare = connect.prepareStatement(sql);
                prepare.executeUpdate();

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Successfully Update!");
                alert.showAndWait();

                loadUserData();
                displayUsername();
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    
    
    
//    ---------------------------------
    
   
    public void switchForm(ActionEvent event) {

        if (event.getSource() == homepage_btn) {
            homepage_form.setVisible(true);
            roomdetail_form.setVisible(false);
            contactus_form.setVisible(false);
            profileuser_form.setVisible(false);
            blogs_form.setVisible(false);
            aboutus_form.setVisible(false);
            
            homepage_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #5068c9, #bc59e4)");
            roomdetail_btn.setStyle("-fx-background-color:transparent");
            contactus_btn.setStyle("-fx-background-color:transparent");
            profileuser_btn.setStyle("-fx-background-color:transparent");
            blogs_btn.setStyle("-fx-background-color:transparent");
            aboutus_btn.setStyle("-fx-background-color:transparent");
            

            

        } else if (event.getSource() == roomdetail_btn) {
            homepage_form.setVisible(false);
            roomdetail_form.setVisible(true);
            contactus_form.setVisible(false);
            profileuser_form.setVisible(false);
            blogs_form.setVisible(false);
            aboutus_form.setVisible(false);
            
            homepage_btn.setStyle("-fx-background-color:transparent");
            roomdetail_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #5068c9, #bc59e4)");
            contactus_btn.setStyle("-fx-background-color:transparent");
            profileuser_btn.setStyle("-fx-background-color:transparent");
            blogs_btn.setStyle("-fx-background-color:transparent");
            aboutus_btn.setStyle("-fx-background-color:transparent");
            

            

        } else if (event.getSource() == contactus_btn) {
            homepage_form.setVisible(false);
            roomdetail_form.setVisible(false);
            contactus_form.setVisible(true);
            profileuser_form.setVisible(false);
            blogs_form.setVisible(false);
            aboutus_form.setVisible(false);
            
            homepage_btn.setStyle("-fx-background-color:transparent");
            roomdetail_btn.setStyle("-fx-background-color:transparent");
            contactus_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #5068c9, #bc59e4)");
            profileuser_btn.setStyle("-fx-background-color:transparent");
            blogs_btn.setStyle("-fx-background-color:transparent");
            aboutus_btn.setStyle("-fx-background-color:transparent");
            

            
        }else if (event.getSource() == profileuser_btn) {
            homepage_form.setVisible(false);
            roomdetail_form.setVisible(false);
            contactus_form.setVisible(false);
            profileuser_form.setVisible(true);
            blogs_form.setVisible(false);
            aboutus_form.setVisible(false);
            
            homepage_btn.setStyle("-fx-background-color:transparent");
            roomdetail_btn.setStyle("-fx-background-color:transparent");
            contactus_btn.setStyle("-fx-background-color:transparent");
            profileuser_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #5068c9, #bc59e4)");
            blogs_btn.setStyle("-fx-background-color:transparent");
            aboutus_btn.setStyle("-fx-background-color:transparent");
            

            
        }else if (event.getSource() == blogs_btn) {
            homepage_form.setVisible(false);
            roomdetail_form.setVisible(false);
            contactus_form.setVisible(false);
            profileuser_form.setVisible(false);
            blogs_form.setVisible(true);
            aboutus_form.setVisible(false);
            
            homepage_btn.setStyle("-fx-background-color:transparent");
            roomdetail_btn.setStyle("-fx-background-color:transparent");
            contactus_btn.setStyle("-fx-background-color:transparent");
            profileuser_btn.setStyle("-fx-background-color:transparent");
            blogs_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #5068c9, #bc59e4)");
            aboutus_btn.setStyle("-fx-background-color:transparent");
            

            
        }else if (event.getSource() == aboutus_btn) {
            homepage_form.setVisible(false);
            roomdetail_form.setVisible(false);
            contactus_form.setVisible(false);
            profileuser_form.setVisible(false);
            blogs_form.setVisible(false);
            aboutus_form.setVisible(true);
            
            homepage_btn.setStyle("-fx-background-color:transparent");
            roomdetail_btn.setStyle("-fx-background-color:transparent");
            contactus_btn.setStyle("-fx-background-color:transparent");
            profileuser_btn.setStyle("-fx-background-color:transparent");
            blogs_btn.setStyle("-fx-background-color:transparent");
            aboutus_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #5068c9, #bc59e4)");
            

            
        }else if (event.getSource() == btnaboutus) {
            homepage_form.setVisible(false);
            roomdetail_form.setVisible(false);
            contactus_form.setVisible(false);
            profileuser_form.setVisible(false);
            blogs_form.setVisible(false);
            aboutus_form.setVisible(true);
            
            homepage_btn.setStyle("-fx-background-color:transparent");
            roomdetail_btn.setStyle("-fx-background-color:transparent");
            contactus_btn.setStyle("-fx-background-color:transparent");
            profileuser_btn.setStyle("-fx-background-color:transparent");
            blogs_btn.setStyle("-fx-background-color:transparent");
            aboutus_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #5068c9, #bc59e4)");
        
        }
    
    }
    
    @FXML
    void nutthoat_formuser(ActionEvent event) throws IOException {
        //hiện form Login
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Stage stage = new Stage();
        stage.setTitle("From Login");
        stage.setScene(new Scene(root));
        stage.setFullScreen(false);
        stage.setResizable(false);
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        stage.show();
        tv.showAlert("Quay về LOGIN");
        //ẩn form Main
        fmainuser.getScene().getWindow().hide();
    }

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        displayUsername();
        loadUserData();
        profileUserEdit();
//        profileUserClear();
//        displayProfileUser();
    }    
    
}
