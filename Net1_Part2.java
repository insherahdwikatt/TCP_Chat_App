/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Net1Part2_Chatting;

import javax.swing.JFrame;

/**
 *
 * @author hp
 */
public class Net1_Part2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        Client client1 = new Client();
        client1.setVisible(true);
        client1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Client client2 = new Client();
        client2.setVisible(true);
        client2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Client client3 = new Client();
        client3.setVisible(true);
        client3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        ServerFrame ServerFrame = new ServerFrame();
        ServerFrame.setVisible(true);
        ServerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
