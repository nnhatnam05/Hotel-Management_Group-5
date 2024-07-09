/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package pj;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author ADMIN
 */
public class FXMLDocumentController implements Initializable {
    
    
    private Connection conn;
    private PreparedStatement pat;
    private ResultSet rs;
    Thuvien tv=new Thuvien();
    
    private double x = 0;
    private double y = 0;
    
    @FXML
    private AnchorPane fdocument;
     
    
//    LOGIN
    
    
    
    @FXML
    private AnchorPane flogin;
    
    @FXML
    private TextField txtname_dn;
    
    @FXML
    private PasswordField txtpass_dn;
    


    @FXML
    private Button signup_btn;
    

    
    
    
    @FXML
    void nutdangnhap() throws SQLException {
        if(txtname_dn.getText().isEmpty() || txtpass_dn.getText().isEmpty()){
            tv.showAlert("Vui lòng nhập đầy đủ thông tin");
        }else{
            try {
                String sql="select * from tbl_account where Username=? and Password=?";
                pat=conn.prepareStatement(sql);
                pat.setString(1, txtname_dn.getText());
                pat.setString(2, txtpass_dn.getText());
                rs=pat.executeQuery();
                if(rs.next()){
                    
                    getData.username = txtname_dn.getText();
                    
                    int role = rs.getInt("role");
                    if (role == 0) {
                        Parent root = FXMLLoader.load(getClass().getResource("FXMLMain.fxml"));
                    Stage stage = new Stage();

                    
                    stage.setScene(new Scene(root));
                    stage.setFullScreen(false);
                    stage.setResizable(false);
                    stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
                    
                    root.setOnMousePressed((MouseEvent event) ->{
                    
                        x = event.getSceneX();
                        y = event.getSceneY();
                        
                    });
                    
                    root.setOnMouseDragged((MouseEvent event) ->{
                    
                        stage.setX(event.getSceneX() - x);
                        stage.setY(event.getSceneY() - y);
                    
                    });
                    
                    stage.initStyle(StageStyle.TRANSPARENT);
                    

                    stage.show();
                    
                    
                    
                    
                    
                    } else if (role == 1) {
                        Parent root = FXMLLoader.load(getClass().getResource("FXMLMainUser.fxml"));
                    Stage stage = new Stage();
                    stage.setTitle("From Main User");
                    stage.setScene(new Scene(root));
                    stage.setFullScreen(false);
                    stage.setResizable(false);
                    stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
                    stage.show();
                    }
                    //ẩn form login
                    flogin.getScene().getWindow().hide();
                    
                }else{
                    tv.showAlert("Tài khoản đăng nhập không đúng");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }finally{
                conn.close();
            }
        }
    }
    
    @FXML
    void nutdangky_dn(ActionEvent event) {
        if(event.getSource() == signup_btn){
            fregister.setVisible(true);
            flogin.setVisible(false);

            
//            fregister.setStyle("-fx-background-color:linear-gradient(to top right, #72513c, #ab853e);");
//            flogin.setStyle("-fx-background-color: transparent");
//            fforget.setStyle("-fx-background-color: transparent");
        }
    }
    
    @FXML
    void nutthoat_dn(ActionEvent event) {
        tv.showAlert("Thoát chương trình");
        System.exit(0);
    }
    
    
    
//    REGISTER
    
    
    
     @FXML
    private AnchorPane fregister;
    
    @FXML
    private TextField txtusername_dk;
    
    @FXML
    private PasswordField txtpass_dk;
    
    @FXML
    private TextField txtemail_dk;
    
    @FXML
    private TextField txtaddress_dk;
    
    @FXML
    private TextField txtphone_dk;
    
   @FXML
    private RadioButton rdmale_dk;
    
    @FXML
    private RadioButton rdfemale_dk;

    @FXML
    private RadioButton rdother_dk;
    
    @FXML
    private Button canceldk_btn;
    
    @FXML
    private Button createacc_btn;
    
    @FXML
    private Button logindk_btn;
    
    @FXML
    private TextField txtfullname_dk;
    
    @FXML
    void nutdangky(ActionEvent event) {
        if (txtusername_dk.getText().isEmpty() 
                || txtfullname_dk.getText().isEmpty()
                || txtpass_dk.getText().isEmpty() 
                || txtemail_dk.getText().isEmpty()
                || txtaddress_dk.getText().isEmpty()
                || txtphone_dk.getText().isEmpty()) {
            tv.showAlert("VUI LÒNG NHẬP ĐẦY ĐỦ THÔNG TIN");
        }else{
            try {
                String sqlCheckemail="select * from tbl_account where Username='"+txtusername_dk.getText()+"'"  ;
                pat=conn.prepareStatement(sqlCheckemail);
                rs=pat.executeQuery();
                if(rs.next()){
                    tv.showAlert("UserName này đã được đăng kí");
                }else{
                    int gt=0;
                    if(rdmale_dk.isSelected()){
                        gt=1;
                    }
                    if(rdfemale_dk.isSelected()){
                        gt=2;
                    }
                    if(rdother_dk.isSelected()){
                        gt=3;
                    }
                    String sql="insert into tbl_account(Username,fullname,Password,Email,Address,Phone,Gioitinh,role)"
                            + " values(?,?,?,?,?,?,?,?)";
                    pat=conn.prepareStatement(sql);
                    pat.setString(1, txtusername_dk.getText());
                    pat.setString(2, txtfullname_dk.getText());
                    pat.setString(3, txtpass_dk.getText());
                    pat.setString(4, txtemail_dk.getText());
                    pat.setString(5, txtaddress_dk.getText());
                    pat.setString(6, txtphone_dk.getText());
                    pat.setInt(7, gt);
                    pat.setInt(8, 1);
                    pat.executeUpdate();
                                                            
                    tv.showAlert("Hoàn tất đăng ký");
                    // Chuyển về trang LOGIN
                    if(event.getSource() == createacc_btn){
                        flogin.setVisible(true);
                        fregister.setVisible(false);
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            } finally {
                
            }
        }
    }

    @FXML
    void nutthoat_dk(ActionEvent event) {
         if(event.getSource() == canceldk_btn){
            flogin.setVisible(true);
            fregister.setVisible(false);
        }
    }
    
    @FXML
    void nutdangnhap_dk(ActionEvent event) {
         if(event.getSource() == logindk_btn){
            flogin.setVisible(true);
            fregister.setVisible(false);
        }
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        conn=DB.ConnectDB.getConnectDB();
    }       
    
}
