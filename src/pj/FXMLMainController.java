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
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.beans.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.tools.DocumentationTool.Location;

/**
 * FXML Controller class
 *
 * @author ADMIN
 */
public class FXMLMainController implements Initializable {

//    private Connection conn;
//    private PreparedStatement pat;
//    private ResultSet rs;
    Thuvien tv = new Thuvien();

    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;
    

    @FXML
    private AnchorPane main_form;

    @FXML
    private Button AvailRoom_btn;

    @FXML
    private Button Customer_btn;

    @FXML
    private AnchorPane Customer_form;

    @FXML
    private Button Dashboard_btn;

    @FXML
    private Button SignOut_btn;

    @FXML
    private AnchorPane availableRoom_form;

    @FXML
    private Button availableroom_addbtn;

    @FXML
    private Button availableroom_checkinbtn;

    @FXML
    private Button availableroom_clearbtn;

    @FXML
    private TableColumn<roomData, String> availableroom_col_price;

    @FXML
    private TableColumn<roomData, String> availableroom_col_roomnumber;

    @FXML
    private TableColumn<roomData, String> availableroom_col_roomtype;

    @FXML
    private TableColumn<roomData, String> availableroom_col_status;

    @FXML
    private Button availableroom_deletebtn;

    @FXML
    private TextField availableroom_price;

    @FXML
    private TextField availableroom_room;

    @FXML
    private TextField availableroom_search;

    @FXML
    private TableView<roomData> availableroom_tableview;
    
    @FXML
    private TableView<customerData> customer_tableView;

    @FXML
    private Button availableroom_updatebtn;

    @FXML
    private ComboBox<?> avavailibleroom_roomtype;

    @FXML
    private ComboBox<?> avavailibleroom_status;

    @FXML
    private TableColumn<customerData, String> customer_checkin;

    @FXML
    private TableColumn<customerData, String> customer_checkout;

    @FXML
    private TableColumn<customerData, String> customer_customernumber;

    @FXML
    private TableColumn<customerData, String> customer_firstname;

    @FXML
    private TableColumn<customerData, String> customer_lastname;

    @FXML
    private TableColumn<customerData, String> customer_phone;
    
    @FXML
    private TableColumn<customerData, String> customer_total;

    @FXML
    private TextField customer_search;

    @FXML
    private AreaChart<?, ?> dashboard_areachart;

    @FXML
    private Label dashboard_booktoday;

    @FXML
    private AnchorPane dashboard_form;

    @FXML
    private Label dashboard_incometoday;

    @FXML
    private Label dashboard_totalincome;

    @FXML
    private Button exit_btn;

    @FXML
    private AnchorPane fmain;

    @FXML
    private Label username;
    
    private int count = 0;
    public void doashboardCountBookToday(){
        
        Date date = new Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        String sql = "SELECT COUNT(id) AS count FROM tbl_customer WHERE checkin = '"+sqlDate+"'";
        
        connect = ConnectDB.getConnectDB();
        
        count = 0;
        
        try{
            
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            while(result.next()){
                
                count = result.getInt("count");
                
            }
            System.out.println(count);
            
            
        }catch(Exception e){e.printStackTrace();}
        
    }
    
    public void doashboardDisplayBookToday(){
        doashboardCountBookToday();
        dashboard_booktoday.setText(String.valueOf(count));
        
    }
    
    private float sumToday=0;
    public void doashboardSumIncomeToday(){
        Date date = new Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        String sql = "SELECT SUM(total) AS total_income FROM customer_receipt WHERE date = '"+sqlDate+"'";
        
        connect = ConnectDB.getConnectDB();
        
        try{
            
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            while(result.next()){
                
                sumToday = result.getFloat("total_income");
                
            }
            
        }catch(Exception e){e.printStackTrace();}
        
    }
    
    public void dashboardDisplayIncomeToday(){
        doashboardSumIncomeToday();
        dashboard_incometoday.setText("$"+String.valueOf(sumToday));
        
    }
    
    private float overall = 0;
    public void dashboardSumTotalIncome(){
        
        String sql = "SELECT SUM(total) AS total FROM customer_receipt";
        
        connect = ConnectDB.getConnectDB();
        
        try{
            
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            while(result.next()){
                
                overall = result.getFloat("total");
                
            }
            
        }catch(Exception e){e.printStackTrace();}
        
    }
    
    public void dashboardTotalIncome(){
        dashboardSumTotalIncome();
        dashboard_totalincome.setText("$"+String.valueOf(overall));
        
    }
    
    public void dashboardChart(){
        
        dashboard_areachart.getData().clear();
            String sql = "SELECT CONVERT(DATETIME, date, 120) AS date, SUM(total) AS total " +
             "FROM customer_receipt " +
             "GROUP BY CONVERT(DATETIME, date, 120) " +
             "ORDER BY CONVERT(DATETIME, date, 120) ASC " +
             "OFFSET 0 ROWS FETCH NEXT 8 ROWS ONLY";
        connect = ConnectDB.getConnectDB();
        XYChart.Series chart = new XYChart.Series();
        
        try{
            
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            while(result.next()){
                
                chart.getData().add(new XYChart.Data(result.getString(1), result.getInt(2)));
                
            }
            
            dashboard_areachart.getData().add(chart);
            
        }catch(Exception e){e.printStackTrace();}
        
    }
    
    public ObservableList<roomData> availableRoomsListData() {
        ObservableList<roomData> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM tbl_rooms";

        connect = ConnectDB.getConnectDB();
        try {
            roomData roomD;

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                roomD = new roomData(result.getInt("Room_Number"),
                        result.getString("Room_Type"),
                        result.getString("Status"),
                        result.getDouble("Price"));
                listData.add(roomD);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    private ObservableList<roomData> roomDataList;

    public void availableRoomsShowData() {

        roomDataList = availableRoomsListData();
        availableroom_col_roomnumber.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        availableroom_col_roomtype.setCellValueFactory(new PropertyValueFactory<>("roomType"));
        availableroom_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        availableroom_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));

        availableroom_tableview.setItems(roomDataList);

    }

    @FXML
    void availableroomprice(ActionEvent event) {

    }

    @FXML
    void availableroomroom(ActionEvent event) {

    }

    @FXML
    void avavailibleroomroomtype(ActionEvent event) {

    }

    @FXML
    void avavailibleroomstatus(ActionEvent event) {

    }

    public void availableRoomsClear() {
        availableroom_room.setText("");
        avavailibleroom_roomtype.getSelectionModel().clearSelection();
        avavailibleroom_status.getSelectionModel().clearSelection();
        availableroom_price.setText("");
    }

    public void availableRoomsChekIn() {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("FXMLCheckIn.fxml"));
            Stage stage = new Stage();
            stage.setTitle("From CheckIn");
            stage.setScene(new Scene(root));
            stage.setFullScreen(false);
            stage.setResizable(false);
            stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
            stage.show();
            
            //ẩn form login
//            fmain.getScene().getWindow().hide();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void availableRoomsSelectData() {

        roomData roomD = availableroom_tableview.getSelectionModel().getSelectedItem();
        int num = availableroom_tableview.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }
        availableroom_room.setText(String.valueOf(roomD.getRoomNumber()));
        availableroom_price.setText(String.valueOf(roomD.getPrice()));
    }
    
    public void availableRoomsSearch(){
        
        FilteredList<roomData> filter = new FilteredList<>(roomDataList,e -> true);
        
        availableroom_search.textProperty().addListener((Observable, oldValue, newValue) -> {
        
            filter.setPredicate(predicateRoomData -> {
            
                    if(newValue == null){  
                        return true;
                    }
                    String searchKey = newValue.toLowerCase();
                    
                    if(predicateRoomData.getRoomNumber().toString().contains(searchKey)){
                        return true;
                    }else if(predicateRoomData.getRoomType().toLowerCase().contains(searchKey)){
                        return true;
                    }else if(predicateRoomData.getPrice().toString().contains(searchKey)){
                        return true;
                    }else if(predicateRoomData.getStatus().toLowerCase().contains(searchKey)){
                        return true;
                    }else return false;
            });
        });
        SortedList<roomData> sortList = new SortedList<>(filter);
        sortList.comparatorProperty().bind(availableroom_tableview.comparatorProperty());
        availableroom_tableview.setItems(sortList);
    }
    
    public void availableRoomsAdd() {
        String sql = "INSERT INTO tbl_rooms (Room_Number,Room_Type,Status,Price) VALUES(?,?,?,?)";

        connect = ConnectDB.getConnectDB();

        try {

            String roomNumber = availableroom_room.getText();
            String type = (String) avavailibleroom_roomtype.getSelectionModel().getSelectedItem();
            String status = (String) avavailibleroom_status.getSelectionModel().getSelectedItem();
            String price = availableroom_price.getText();

            prepare = connect.prepareStatement(sql);
            prepare.setString(1, roomNumber);
            prepare.setString(2, type);
            prepare.setString(3, status);
            prepare.setString(4, price);

            Alert alert;

            if (roomNumber == null || type == null || status == null || price == null) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {

                String check = "SELECT Room_Number FROM tbl_rooms WHERE Room_Number = '" + roomNumber + "'";

                prepare = connect.prepareStatement(check);
                result = prepare.executeQuery();

                if (result.next()) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Room #" + roomNumber + " Was Already Exist");
                    alert.showAndWait();

                } else {

                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, roomNumber);
                    prepare.setString(2, type);
                    prepare.setString(3, status);
                    prepare.setString(4, price);

                    prepare.executeUpdate();

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Succsessfully Added!");
                    alert.showAndWait();

                    availableRoomsShowData();

                    availableRoomsClear();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void availableRoomsDelete() {

        String type1 = (String) avavailibleroom_roomtype.getSelectionModel().getSelectedItem();
        String status1 = (String) avavailibleroom_status.getSelectionModel().getSelectedItem();
        String price1 = availableroom_price.getText();
        String roomNum = availableroom_room.getText();

        String deleteData = "DELETE FROM tbl_rooms WHERE Room_Number = '" + roomNum + "'";

        connect = ConnectDB.getConnectDB();

        try {

            Alert alert;

            if (type1 == null || status1 == null || price1 == null || roomNum == null) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please Select The Data First");
                alert.showAndWait();
            } else {

                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are You Sure You Want To Delete Room #" + roomNum + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {

                    prepare = connect.prepareStatement(deleteData);
                    prepare.executeUpdate();

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Deleted!");
                    alert.showAndWait();

                    availableRoomsShowData();
                    availableRoomsClear();

                } else {
                    return;
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void avilableRoomsUpdate() {

        String type1 = (String) avavailibleroom_roomtype.getSelectionModel().getSelectedItem();
        String status1 = (String) avavailibleroom_status.getSelectionModel().getSelectedItem();
        String price1 = availableroom_price.getText();
        String roomNum = availableroom_room.getText();

        String sql = "UPDATE tbl_rooms SET Room_Type = '" + type1 + "',"
                + "Status = '" + status1 + "',"
                + "Price = '" + price1 + "' "
                + "WHERE Room_Number = '" + roomNum + "'";

        connect = ConnectDB.getConnectDB();

        try {

            Alert alert;

            if (type1 == null || status1 == null || price1 == null || roomNum == null) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please Select The Data First");
                alert.showAndWait();
            } else {
                prepare = connect.prepareStatement(sql);
                prepare.executeUpdate();

                alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Successfully Update!");
                alert.showAndWait();

                availableRoomsShowData();
                availableRoomsClear();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void switchForm(ActionEvent event) {

        if (event.getSource() == Dashboard_btn) {
            dashboard_form.setVisible(true);
            availableRoom_form.setVisible(false);
            Customer_form.setVisible(false);
            
            Dashboard_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #5068c9, #bc59e4)");
            AvailRoom_btn.setStyle("-fx-background-color:transparent");
            Customer_btn.setStyle("-fx-background-color:transparent");
            
            doashboardDisplayBookToday();
            dashboardDisplayIncomeToday();
            dashboardTotalIncome();
            dashboardChart();
            

        } else if (event.getSource() == AvailRoom_btn) {
            dashboard_form.setVisible(false);
            availableRoom_form.setVisible(true);
            Customer_form.setVisible(false);
            
            Dashboard_btn.setStyle("-fx-background-color:transparent");
            AvailRoom_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #5068c9, #bc59e4)");
            Customer_btn.setStyle("-fx-background-color:transparent");
            
            availableRoomsSearch();
            availableRoomsShowData();
            

        } else if (event.getSource() == Customer_btn) {
            dashboard_form.setVisible(false);
            availableRoom_form.setVisible(false);
            Customer_form.setVisible(true);
            
            Dashboard_btn.setStyle("-fx-background-color:transparent");
            AvailRoom_btn.setStyle("-fx-background-color:transparent");
            Customer_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #5068c9, #bc59e4)");
            
            customerSearch();
            customerShowData();
            
        }
    }
    
    public void defautNavBtn(){
        
        Dashboard_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #5068c9, #bc59e4)");
        
    }
    
    public void displayUsername(){
        
        username.setText(getData.username);
        
    }
    
    public ObservableList<customerData>customerListData(){
        
        ObservableList<customerData> listData = FXCollections.observableArrayList();
        
        String sql = "SELECT * FROM tbl_customer";
        
        connect = ConnectDB.getConnectDB();
        
        try{
            
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            customerData custD;
            
            while (result.next()){
                
                custD = new customerData(result.getInt("customer_id")
                        , result.getString("firstName")
                        , result.getString("lastName")
                        , result.getString("phoneNumber")
                        , result.getFloat("total")
                        , result.getDate("checkin")
                        , result.getDate("checkout"));
                
                listData.add(custD);
                
            }
            
        }catch(Exception e){e.printStackTrace();}
        
        return listData;
        
    }
    
    private ObservableList<customerData>listCustomerData;
    public void customerShowData(){
        
        listCustomerData = customerListData();
        
        customer_customernumber.setCellValueFactory(new PropertyValueFactory<>("customerNum"));
        customer_firstname.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        customer_lastname.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        customer_phone.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        customer_total.setCellValueFactory(new PropertyValueFactory<>("total"));
        customer_checkin.setCellValueFactory(new PropertyValueFactory<>("checkIn"));
        customer_checkout.setCellValueFactory(new PropertyValueFactory<>("checkOut"));
        
        customer_tableView.setItems(listCustomerData);
        
    }
    
    public void customerSearch(){
        
        FilteredList<customerData> filter = new FilteredList<>(listCustomerData, e -> true);
        
        customer_search.textProperty().addListener((Observable, oldValue, newValue) -> {
            
            filter.setPredicate(predicateCustomer -> {
            
                if(newValue == null && newValue.isEmpty()){
                    
                    return true;
                    
                }
                
                String searchKey = newValue.toLowerCase();
                
                if(predicateCustomer.getCustomerNum().toString().contains(searchKey)){
                    
                    return true;
                    
                }else if(predicateCustomer.getFirstName().toLowerCase().contains(searchKey)){
                    
                    return true;
                    
                }else if(predicateCustomer.getLastName().toLowerCase().contains(searchKey)){
                    
                    return true;
                    
                }else if(predicateCustomer.getTotal().toString().contains(searchKey)){
                    
                    return true;
                    
                }else if(predicateCustomer.getPhoneNumber().toLowerCase().contains(searchKey)){
                    
                    return true;
                    
                }else if(predicateCustomer.getCheckIn().toString().contains(searchKey)){
                    
                    return true;
                    
                }else if(predicateCustomer.getCheckOut().toString().contains(searchKey)){
                    
                    return true;
                    
                }else return false;
            });
            
        });
        
        SortedList<customerData> sortList = new SortedList<>(filter);
        
        sortList.comparatorProperty().bind(customer_tableView.comparatorProperty());
        
        customer_tableView.setItems(sortList);
        
    }
    
    @FXML
    void nutthoat_formmain(ActionEvent event) throws IOException {
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
        fmain.getScene().getWindow().hide();
    }

    @FXML
    void nutx_formmain(ActionEvent event) {
        tv.showAlert("Thoát chương trình");
        System.exit(0);
    }

    @FXML
    private TableView<?> table_avai;

    private final String type[] = {"Single Room", "Double Room", "Tripple Room", "Quad Room", "King Room"};

    @FXML
    public void avavailibleroomroomtype() {
        List<String> listData = new ArrayList<>();

        for (String data : type) {
            listData.add(data);
        }

        ObservableList list = FXCollections.observableArrayList(listData);
        avavailibleroom_roomtype.setItems(list);
    }

    private String status[] = {"Available", "Not Available", "Occupied"};

    @FXML
    public void avavailibleroomstatus() {
        List<String> listData = new ArrayList<>();

        for (String data : status) {
            listData.add(data);
        }

        ObservableList list = FXCollections.observableArrayList(listData);
        avavailibleroom_status.setItems(list);
    }

    public void minimize() {
        Stage stage = (Stage) main_form.getScene().getWindow();
        stage.setIconified(true);
    }

//    public void username_form(){
//        
//        String sqlusername = "SELECT * FROM tbl_account WHERE Username";
//        connect = ConnectDB.getConnectDB();
//
//        try {
//
//            prepare = connect.prepareStatement(sqlusername);
//            result = prepare.executeQuery();
//
//
//            username.setText("Username");
//           
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        
//    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        avavailibleroomroomtype();
        avavailibleroomstatus();
        availableRoomsShowData();
        customerShowData();
        doashboardDisplayBookToday();
        dashboardDisplayIncomeToday();
        dashboardTotalIncome();
        dashboardChart();
        defautNavBtn();
        displayUsername();
    }
}
