import java.io.*;
import java.sql.*;
import java.net.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;

public class Server extends Thread {
    private static Lock lock = new ReentrantLock();

    Statement stmt=null;
    Vector records = new Vector(10,10);
    ResultSet rs = null;
    ServerSocket server = null;
    Socket client = null;
    Connection con = null;
    ObjectOutputStream out =null;
    ObjectInputStream br = null;
    String str = null;
    String recievedQuery = null;
    accountInfo pub = null;
    itemInfo item = null;
    historyInfo history = null;
    cartInfo cart = null;
    String validation;
    
    public Server()
    {
      try {
            server = new ServerSocket(1400);
            System.out.println("Starting the Server");
            start();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
  @Override
    public void run()
    {
        while(true)
        {
            try {
                client = server.accept();
                br = new ObjectInputStream(client.getInputStream());
                recievedQuery = (String) br.readObject();
                if(recievedQuery.substring(0,6).equals("Select")){
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    con = DriverManager.getConnection("jdbc:derby://localhost:1527/MarketDB ","zyad","zyad");
                    stmt = con.createStatement();
                    rs = stmt.executeQuery(recievedQuery);
                    out = new ObjectOutputStream(client.getOutputStream());
                    records.removeAllElements();
                    if(recievedQuery.substring(0,15).equals("Select * from A")){
                    while(rs.next())
                    {
                        pub = new accountInfo();
                        pub.userName = rs.getString(1);
                        pub.email = rs.getString(2);
                        pub.address = rs.getString(4);
                        pub.cash = rs.getString(5);
                        pub.password = rs.getString(3);
                        records.addElement(pub);
                        System.out.println("row returned");
                    }

                  out.writeObject(records);
                  out.close();
                    }
                    else if(recievedQuery.substring(0,15).equals("Select * from I")){
                        while(rs.next())
                    {
                        item = new itemInfo();
                        item.id = rs.getString(1);
                        item.name = rs.getString(2);
                        item.price = rs.getString(3);
                        item.quantity = rs.getString(4);
                        item.category = rs.getString(5);
                        records.addElement(item);
                        System.out.println("row returned");
                    }

                  out.writeObject(records);
                  out.close();
                    }
                    else if(recievedQuery.substring(0,15).equals("Select * from C")){
                         while(rs.next())
                    {
                        cart = new cartInfo();
                        cart.id = rs.getString(1);
                        cart.itemName = rs.getString(3);
                        cart.userName = rs.getString(2);
                        cart.price = rs.getString(4);
                        cart.amount = rs.getString(5);
                        cart.total = rs.getString(6);
                        lock.lock();
                             try {
                                 records.addElement(cart);
                                 System.out.println("row returned");
                             } finally {
                                 lock.unlock();
                             }
                    }

                  out.writeObject(records);
                  out.close();
                        
                    }
                    else if(recievedQuery.substring(0,15).equals("Select * from H")){
                         while(rs.next())
                    {
                        cart = new cartInfo();
                        cart.id = rs.getString(1);
                        cart.itemName = rs.getString(2);
                        cart.userName = rs.getString(3);
                        cart.price = rs.getString(4);
                        cart.amount = rs.getString(5);
                        cart.total = rs.getString(6);
                        lock.lock();
                             try {
                                 records.addElement(cart);
                                 System.out.println("row returned");
                             } finally {
                                 lock.unlock();
                             }
                    }

                  out.writeObject(records);
                  out.close();
                        
                    }
                    else if(recievedQuery.substring(0,15).equals("Select COUNT(*)")){
                         while(rs.next())
                    {
                        validation = rs.getString(1);
                        records.addElement(validation);
                        System.out.println("row returned");
                    }

                  out.writeObject(records);
                  out.close();
                        
                    }
                    
                    
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
               }
              
                else {
              
                 try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    con = DriverManager.getConnection("jdbc:derby://localhost:1527/MarketDB ","zyad","zyad");
                    stmt = con.createStatement();
                    stmt.executeUpdate(recievedQuery);
                                       } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }          
                }
            } 
            catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
                  
    }
  
  
  
   public static void main(String[] args) throws SQLException  {        
         new Server();
    }
}
