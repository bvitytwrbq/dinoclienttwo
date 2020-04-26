import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.InputStream;
import java.net.Socket;
import java.util.Properties;

public class Login extends JFrame implements ActionListener {

    private String server;
    private int port;
    JPanel panel;
    JLabel user_label, password_label, info_label;
    JTextField userName_text;
    JPasswordField password_text;
    JButton submit, cancel;

    public Login(){
        this.server="127.0.0.1";
        this.port=8090;

        user_label = new JLabel();
        user_label.setText("username here");
        userName_text = new JTextField();

        password_label = new JLabel();
        password_label.setText("pwd");
        password_text = new JPasswordField();

        submit = new JButton("login");

        panel =  new JPanel(new GridLayout(3,1));
        panel.add(user_label);
        panel.add(userName_text);
        panel.add(password_label);
        panel.add(password_text);

        info_label = new JLabel();
        panel.add(info_label);
        panel.add(submit);

        setDefaultCloseOperation(HIDE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                /*int result = JOptionPane.showConfirmDialog(new JFrame(),"Are you sure?");
                if( result==JOptionPane.OK_OPTION){
                    setDefaultCloseOperation(
                            JFrame.DISPOSE_ON_CLOSE);}*/
                    setVisible(false);
                    dispose();
                    new FirstForm();

            }
        });


        submit.addActionListener(this);
        add(panel, BorderLayout.CENTER);
        setTitle("login, yo");
        setLocation(999,111);
        setSize(444,444);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Login();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String userName = userName_text.getText();
        String pwd = String.valueOf(password_text.getPassword());
        try {
            sendAndGetMessage("login",userName,pwd);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void sendAndGetMessage(String sender,String name, String messageText) throws Exception {
        try (Connection connection = new Connection(new Socket(server, port))){
            Message message = new Message(sender,name, messageText);
            connection.sendMessage(message);
            message = connection.readMessage();
            if("log".equals(message.getType())){
                hide();
                try (InputStream inputStream = Klient.class.getClassLoader().getResourceAsStream("config.properties")){
                    Properties properties = new Properties();
                    properties.load(inputStream);
                    String server = properties.getProperty("server");//"127.0.0.1";
                    int port = Integer.parseInt(properties.getProperty("port")); //8090;
                    System.out.println(server);
                    Klient klient = new Klient(server, port);
                    klient.start();
            }
        }
            else info_label.setText(message.getText());
    }
}}