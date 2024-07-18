import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Customer extends JFrame {

    private JTextField txtID, txtFirst_name,txtLast_name,txtPhone;
    private JButton btnPrevious, btnNext;
    private JLabel lblid,lblFirstName,lblLastName,lblPhone;
    private Connection conn;
    private PreparedStatement pst;
    private ResultSet result;

    public Customer() {
        addComponents();
        ConnectionDB();
        GetCustomerData();

    }

    private void addComponents() {
        lblid = new JLabel("ID");
        lblFirstName = new JLabel("First Name");
        lblLastName = new JLabel("Last Name");
        lblPhone = new JLabel("Phone");

        txtID = new JTextField(5);
        txtFirst_name = new JTextField(25);
        txtLast_name = new JTextField(25);
        txtPhone = new JTextField(15);

        txtID.setEditable(false);
        txtFirst_name.setEditable(false);
        txtLast_name.setEditable(false);
        txtPhone.setEditable(false);

        btnPrevious = new JButton("Previous");
        btnNext = new JButton("Next");

        setLayout(new GridLayout(6, 2));
        add(lblid);
        add(txtID);
        add(lblFirstName);
        add(txtFirst_name);
        add(lblLastName);
        add(txtLast_name);
        add(lblPhone);
        add(txtPhone);
        add(btnPrevious);
        add(btnNext);

        btnPrevious.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getPreviousCustomerData();
            }
        });
        btnNext.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getNextCustomerData();
            }
        });

        setTitle("Customers");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    private void ConnectionDB(){
        try{
            conn= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/TestingDB","root","pwd123");
            System.out.println("Connected");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void GetCustomerData(){
        try{
            String query="select * from customer";
            pst=conn.prepareStatement(query,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            result=pst.executeQuery();
            if(result.next()){
                showCustomerData();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    private void getPreviousCustomerData(){
        try{
            if(result.previous()){
                showCustomerData();
            }else{
                result.next();
                JOptionPane.showMessageDialog(this, "Customer Not Found");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    private void getNextCustomerData(){
        try{
            if(result.next()){
                showCustomerData();
            }else{
                result.previous();
                JOptionPane.showMessageDialog(this, "Customer Not Found");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    private void showCustomerData(){
        try{
            txtID.setText(result.getString("id"));
            txtFirst_name.setText(result.getString("first_name"));
            txtLast_name.setText(result.getString("last_name"));
            txtPhone.setText(result.getString("phone"));

            btnPrevious.setEnabled(!result.isFirst());
            btnNext.setEnabled(!result.isLast());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}




