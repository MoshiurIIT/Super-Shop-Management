package views;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class UserManualPanel extends JPanel {

    private BufferedImage image;
    //private JTextField name;
    //private JTextField contact;


    public UserManualPanel(){
        setBackground(Color.WHITE);
        setBounds(0, 0, 1000, 700);
        setLayout(null);
        init();
    }

    public void init(){
        try {
            image = ImageIO.read(new File("assets\\images\\sadik.jpg"));

            //image.setBounds(10,128,219,78);
        } catch (IOException ex) {
            //ex.printStackTrace();
        }
        JLabel userNameLabel = new JLabel("Moshiur Rahman");
        userNameLabel.setBounds(30, 210, 100, 30);
        add(userNameLabel);

        JLabel contact = new JLabel("bsse0606@iit.du.ac.bd");
        contact.setBounds(30, 230, 400, 30);
        add(contact);

//        JLabel manual = new JLabel("Super Shop management system user manual");
//        manual.setBounds(300, 40, 600, 30);
//        add(manual);
//
//        JLabel suggest = new JLabel("A hard copy of user manual will provide");
//        suggest.setBounds(300, 60, 600, 30);
//        add(suggest);


    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }



}
