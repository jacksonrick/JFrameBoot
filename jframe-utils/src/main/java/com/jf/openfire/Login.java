package com.jf.openfire;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2018-01-24
 * Time: 15:06
 */
public class Login extends JFrame implements ActionListener {

    public static void main(String[] args) {
        new Login();
    }

    private JLabel label1;
    private JLabel label2;
    private JTextArea username;
    private JTextArea password;
    private JButton login;

    public Login() {
        setVisible(true);
        setSize(350, 150);
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("登录聊天室");
        getContentPane().setLayout(null);
        setDefaultCloseOperation(this.EXIT_ON_CLOSE);

        label1 = new JLabel("用户名");
        label1.setBounds(10, 10, 70, 20);
        label1.setForeground(Color.RED);
        getContentPane().add(label1);
        label2 = new JLabel("密码");
        label2.setBounds(10, 35, 70, 20);
        label2.setForeground(Color.RED);
        getContentPane().add(label2);

        username = new JTextArea();
        getContentPane().add(username);
        username.setBounds(90, 10, 120, 20);

        password = new JTextArea();
        getContentPane().add(password);
        password.setBounds(90, 35, 120, 20);

        login = new JButton("登录");
        getContentPane().add(login);
        login.setBounds(90, 70, 50, 20);
        login.addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == login) {
            this.dispose();
            OpenMain main = new OpenMain(username.getText(), password.getText());
        }
    }

}
