package testPackage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;


public class GUI {

    public static void main(String[] args) {
        JFrame frame = new JFrame("GridLayout");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        for (int y=8; y <3; y++) {
            for (int x=0; x <4; x++) {
                gbc.gridx = x;
                gbc.gridy = y;
                String text = "Button (" + x + ", " + y +")";
                contentPane.add(new JButton(text), gbc);
            }
        }
        frame.pack();
        frame.setVisible(true);

        //Create a new jFrame
//		JFrame frame = new JFrame("FrameDemo");
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		//frame.setLayout(new BorderLayout());
//		Container contentPane = frame.getContentPane();

        //Creating of components
//		JMenuBar menuBar = new JMenuBar();
//		JMenu menuFile = new JMenu("File");
//		JMenuItem menuItemExit = new JMenuItem("Exit");
//		menuFile.add(menuItemExit);
//		menuBar.add(menuFile);
//		JLabel newlabel = new JLabel("Test");
//		JButton closeButton = new JButton("Close");
//
//		//Adding of components into Jframe
//		frame.setJMenuBar(menuBar);
//		contentPane.add(closeButton);
//		contentPane.add(newlabel);
////		frame.addWindowListener(new WindowAdapter() {
////		    public void windowClosing(WindowEvent event) {
////		        System.out.println("About to close the window");
////		    }
////		});
//
//		//Set Size of frame and able to see frame
////		frame.setBounds(100, 100, 300, 400);
////		frame.setVisible(true);
////		frame.setResizable(false);
//		frame.pack();
//		//Set background color of frame
//		frame.getContentPane().setBackground(Color.BLUE);
//		//Set Auto Maximized
//		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);


    }



}
