/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package pj;

import DB.ConnectDB;
import java.beans.Statement;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author ADMIN
 */
public class FXMLCheckInController implements Initializable {

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Statement statement;

    @FXML
    private DatePicker checkin_Date;

    @FXML
    private AnchorPane checkin_form;

    @FXML
    private DatePicker checkout_Date;

    @FXML
    private Button close;

    @FXML
    private Label customersNumber;

    @FXML
    private TextField emailAddress;

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private TextField phoneNumber;

    @FXML
    private Label total;

    @FXML
    private Label totalDays;

    @FXML
    private ComboBox<?> roomNumber;

    @FXML
    private ComboBox<?> roomType;

    public void customerCheckIn() {
        String insertCustomerData = "INSERT INTO tbl_customer (customer_id,total,roomType,roomNumber,firstName,lastName,phoneNumber,email,checkin,checkout) VALUES (?,?,?,?,?,?,?,?,?,?)";

        connect = ConnectDB.getConnectDB();

        try {

            String customerNum = customersNumber.getText();
            String roomT = (String) roomType.getSelectionModel().getSelectedItem();
            String roomN = (String) roomNumber.getSelectionModel().getSelectedItem();
            String firstN = firstName.getText();
            String lastN = lastName.getText();
            String phoneNum = phoneNumber.getText();
            String mail = emailAddress.getText();
            String checkInDate = String.valueOf(checkin_Date.getValue());
            String checkOutDate = String.valueOf(checkout_Date.getValue());

            Alert alert;

            if (customerNum == null || firstN == null || lastN == null || phoneNum == null || mail == null || checkInDate == null || checkOutDate == null) {

                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {

                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure?");

                Optional<ButtonType> option = alert.showAndWait();
                
                String totalC = String.valueOf(totalP);
                
                if (option.get().equals(ButtonType.OK)) {
                    prepare = connect.prepareStatement(insertCustomerData);
                    prepare.setString(1, customerNum);
                    prepare.setString(2,totalC);
                    prepare.setString(3, roomT);
                    prepare.setString(4, roomN);
                    prepare.setString(5, firstN);
                    prepare.setString(6, lastN);
                    prepare.setString(7, phoneNum);
                    prepare.setString(8, mail);
                    prepare.setString(9, checkInDate);
                    prepare.setString(10, checkOutDate);

                    prepare.executeUpdate();

                    String date = String.valueOf(checkin_Date.getValue());
                    
                    String customerN = customersNumber.getText();

                    String customerReceipt = "INSERT INTO customer_receipt (customer_num,total,date)" + "VALUES(?,?,?)";

                    prepare = connect.prepareStatement(customerReceipt);
                    prepare.setString(1, customerN);
                    prepare.setString(2, totalC);
                    prepare.setString(3, date);

                    prepare.execute();

                    String sqlEditStatus = "UPDATE tbl_rooms SET Status = 'Occupied' WHERE Room_Number ='" + roomN + "'";

                    prepare = connect.prepareStatement(sqlEditStatus);
                    prepare.executeUpdate();
                    
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Checked-In!");
                    alert.showAndWait();
                    
                    reset();
                    
                } else {
                    return;
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void  reset(){
        
        firstName.setText("");
        lastName.setText("");
        phoneNumber.setText("");
        emailAddress.setText("");
        roomType.getSelectionModel().clearSelection();
        roomNumber.getSelectionModel().clearSelection();
        totalDays.setText("---");
        total.setText("$0.0");
        
    }
    
    public void totalDays() {

        Alert alert;

        if (checkout_Date.getValue().isAfter(checkin_Date.getValue())) {

            getData.totalDays = checkout_Date.getValue().compareTo(checkin_Date.getValue());

        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Invalid Check-Out Date");
            alert.showAndWait();

        }

    }

    private double totalP = 0;

    public void displayTotal() {
        totalDays();
        String totalID = String.valueOf(getData.totalDays);
        totalDays.setText(totalID);

        String selectItem = (String) roomNumber.getSelectionModel().getSelectedItem();

        String sql = "SELECT * FROM tbl_rooms WHERE Room_Number = '" + selectItem + "'";

        double priceData = 0;

        connect = ConnectDB.getConnectDB();

        try {

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                priceData = result.getDouble("Price");
            }

            totalP = ((priceData) * getData.totalDays);
            System.out.println("Total Payment: " + totalP);
            System.out.println("priceData: " + priceData);
            total.setText("$" + String.valueOf(totalP));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void CustomerNumber() {

        String customerNum = "SELECT customer_id FROM tbl_customer";

        connect = ConnectDB.getConnectDB();

        try {

            prepare = connect.prepareStatement(customerNum);
            result = prepare.executeQuery();

            while (result.next()) {
                getData.customerNum = result.getInt("customer_id");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void roomTypeList() {
        String listType = "SELECT * FROM tbl_rooms WHERE Status='Available'  "; //GROUP BY Room_Type ORDER BT Room_Type ASC

        connect = ConnectDB.getConnectDB();

        try {

            ObservableList listData = FXCollections.observableArrayList();
            prepare = connect.prepareStatement(listType);
            result = prepare.executeQuery();

            while (result.next()) {
                listData.add(result.getString("Room_Type"));
            }

            roomType.setItems(listData);

            roomNumberList();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void roomNumberList() {
        String item = (String) roomType.getSelectionModel().getSelectedItem();
        String availableRoomNumber = "SELECT * FROM tbl_rooms WHERE Room_Type ='" + item + "'  "; //and Status = ' Available ' ORDER BY Room_Number ASC
        connect = ConnectDB.getConnectDB();
        try {
            ObservableList listData = FXCollections.observableArrayList();
            prepare = connect.prepareStatement(availableRoomNumber);
            result = prepare.executeQuery();
            while (result.next()) {
                listData.add(result.getString("Room_Number"));
            }
            roomNumber.setItems(listData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void displayCustomerNumber() {
        CustomerNumber();
        customersNumber.setText(String.valueOf(getData.customerNum + 1));
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        displayCustomerNumber();
        roomTypeList();
        roomNumberList();
    }
}
