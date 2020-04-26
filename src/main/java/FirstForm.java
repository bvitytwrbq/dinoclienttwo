import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FirstForm extends JFrame implements ActionListener {
    private JPanel panel;
    private JButton logButt;
    private JButton regButt;



    public FirstForm() {
        panel = new JPanel(new GridLayout(5,3));
        panel.add(new JLabel());
        panel.add(logButt);
        logButt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hide();
                Login login = new Login();
            }
        });
        panel.add(new JLabel());

        panel.add(regButt);
        regButt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hide();
                Register register = new Register("127.0.0.1",8090);
            }
        });
        panel.add(new JLabel());

        logButt.addActionListener(this);
        regButt.addActionListener(this);
        add(panel);
        setSize(444,444);
        setLocation(999,111);
        setVisible(true);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new FirstForm();
    }

    @Override
    public void actionPerformed(ActionEvent e) {


    }
}
