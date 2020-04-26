import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.InputStream;
import java.net.Socket;
import java.util.Properties;

public class Register extends JFrame implements ActionListener {

    private String server;
    private int port;
    private JPanel panel;
    private JLabel user_label, password_label, info_label;
    private JTextField userName_text;
    private JPasswordField password_text;
    private JButton submit, cancel;

    public Register(String server, int port){
        this.server = server;
        this.port = port;
        user_label = new JLabel();
        user_label.setText("username here");
        userName_text = new JTextField();

        password_label = new JLabel();
        password_label.setText("pwd");
        password_text = new JPasswordField();

        submit = new JButton("register");

        panel =  new JPanel(new GridLayout(3,1));
        panel.add(user_label);
        panel.add(userName_text);
        panel.add(password_label);
        panel.add(password_text);

        info_label = new JLabel();
        panel.add(info_label);
        panel.add(submit);

        submit.addActionListener(this);
        add(panel, BorderLayout.CENTER);
        setTitle("register, yo");
        setSize(444,444);
        setLocation(999,111);
        setVisible(true);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                //int result = JOptionPane.showConfirmDialog(new JFrame(),"Are you sure?");
                /*if( result==JOptionPane.OK_OPTION){
                    setDefaultCloseOperation(
                            JFrame.DISPOSE_ON_CLOSE);
                }*/
                    setVisible(false);
                    dispose();
                    new FirstForm();

            }
        });
    }

    public static void main(String[] args) {
        try (InputStream inputStream = Klient.class.getClassLoader().getResourceAsStream("config.properties")){
            Properties properties = new Properties();
            properties.load(inputStream);
            String server = properties.getProperty("server");//"127.0.0.1";
            int port = Integer.parseInt(properties.getProperty("port")); //8090;
            Register register = new Register(server,port);

        } catch (Exception ex) {

        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String userName = userName_text.getText();
        String pwd = String.valueOf(password_text.getPassword());
        try (InputStream inputStream = Klient.class.getClassLoader().getResourceAsStream("config.properties")){
            Properties properties = new Properties();
            properties.load(inputStream);
/*            String server = properties.getProperty("server");//"127.0.0.1";
            int port = Integer.parseInt(properties.getProperty("port")); //8090;*/
            sendAndGetMessage("register",userName,pwd);

            /*if() {
                info_label.setText("not unique");
            }*/


        } catch (Exception ex) {

        }
}

    private void sendAndGetMessage(String sender, String name, String messageText) throws Exception {
        try (Connection connection = new Connection(new Socket(server, port))){
            Message message = new Message(sender,name, messageText);
            connection.sendMessage(message);
            message = connection.readMessage();
            System.out.println(message);
            if("not unique".equals(message.getText())){
                info_label.setText("not a unique id");
            }
            else{
                info_label.setText("success");
            }
        }
    }
}
