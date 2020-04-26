import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Properties;
import java.util.Scanner;

public class Klient extends Thread {
    private String server;
    private int port;
    private Scanner scanner;
    private int id;
    private JPanel dinopanel;
    private JButton brontobutton;
    private JButton diplobutton;
    private JButton stegobutton;
    private JLabel brontoLabel;
    private JLabel diploLabel;
    private JLabel stegoLabel;
    private JSpinner brontoSpinner;
    private JSpinner diploSpinner;
    private JSpinner stegoSpinner;
    private JButton brontoOrderButton;
    private JButton diploOrderButton;
    private JButton stegoOrderButton;
    private JComboBox comboBox1;
    private JButton getInfoButton;
    private JButton orderButton;
    private JSpinner orderSpinner;
    private JLabel infoLabel;
    private JButton comboBoxFillerButton;

    public Klient(String server, int port) {
        this.server = server;
        this.port = port;

        this.scanner = new Scanner(System.in);
        brontobutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    sendAndGetMessage("bronto","quantity");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        });
        diplobutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    sendAndGetMessage("diplo","quantity");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        stegobutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    sendAndGetMessage("stego","quantity");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        brontoOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    sendAndGetMessage("bronto",brontoSpinner.getValue().toString());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        });
        diploOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    sendAndGetMessage("diplo",diploSpinner.getValue().toString());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        });
        stegoOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    sendAndGetMessage("stego",brontoSpinner.getValue().toString());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        });

        comboBoxFillerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    sendAndGetMessage("comboBoxFiller","need a list here");

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    sendAndGetMessage("comboBox1","FIRST LISTENER");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        comboBox1.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                try {
                    sendAndGetMessage("comboBox1","dino_table");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {

            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {

            }
        });

        getInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    sendAndGetMessage("getInfoButton", (String) comboBox1.getSelectedItem());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        orderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    sendAndGetMessage((String) comboBox1.getSelectedItem(),orderSpinner.getValue().toString());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        });
    }


    @Override
    public void run(){
        System.out.println(Thread.currentThread().getClass());
        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
/*                int result = JOptionPane.showConfirmDialog(
                        frame, "Are you sure?");
                if( result==JOptionPane.OK_OPTION){
                    frame.setDefaultCloseOperation(
                            JFrame.DISPOSE_ON_CLOSE);
                    frame.setVisible(false);
                    frame.dispose();
                    new FirstForm();
                }*/
                frame.setVisible(false);
                frame.dispose();
                new FirstForm();
            }
        });
        frame.setVisible(true);
        frame.setTitle(Thread.currentThread().getName());
        frame.add(dinopanel);
        frame.setSize(444,444);
        frame.setLocation(999,111);
        //System.out.println("Enter name");
/*        String name = scanner.nextLine();
        String messageText = null;
        try {
            sendAndGetMessage(name, messageText);
        } catch (Exception e) {
            e.printStackTrace();
        }*/

    }

    private void sendAndGetMessage(String name, String messageText) throws Exception {
        try (Connection connection = new Connection(new Socket(server, port))){
            Message message = new Message(name, messageText);
            connection.sendMessage(message);
            message = connection.readMessage();
            System.out.println(message);
            if("bronto".equals(message.getType())){
                brontoLabel.setText(message.getText());
            }
            if("diplo".equals(message.getType())){
                diploLabel.setText(message.getText());
            }
            if("stego".equals(message.getType())){
                stegoLabel.setText(message.getText());
            }
            if("comboBox1Stuff".equals(message.getType())){
                for (int i = 0; i <message.getList().size() ; i++) {
                    comboBox1.addItem(message.getList().get(i));
                }
            }
            if("forInfoButt".equals(message.getType())){
                infoLabel.setText(message.getText());
            }

        }
    }


    public static void main(String[] args) {
        //System.out.println(Thread.currentThread().getName());
        try (InputStream inputStream = Klient.class.getClassLoader().getResourceAsStream("config.properties")){
            Properties properties = new Properties();
            properties.load(inputStream);
            String server = properties.getProperty("server");//"127.0.0.1";
            int port = Integer.parseInt(properties.getProperty("port")); //8090;
            //System.out.println(server);
            Klient klient = new Klient(server, port);
            klient.start();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}


