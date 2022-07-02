/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

package market_place;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.ResultSet;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alaa
 */

public class Market_Place {
    public static String userNameSaved;
    public static String cashSave;
    public static int id=0;
  
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       login.main(null);
    }
    
}
