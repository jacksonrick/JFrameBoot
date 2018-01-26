package com.jf.openfire;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.StanzaListener;
import org.jivesoftware.smack.chat2.Chat;
import org.jivesoftware.smack.chat2.ChatManager;
import org.jivesoftware.smack.chat2.IncomingChatMessageListener;
import org.jivesoftware.smack.filter.StanzaFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Stanza;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jxmpp.jid.EntityBareJid;
import org.jxmpp.jid.impl.JidCreate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2018-01-24
 * Time: 14:01
 */
public class OpenMain extends JFrame implements ActionListener, KeyListener {

    public static void main(String[] args) {
        new OpenMain();
    }

    private String username;
    private String password;

    private JButton conn;
    private JButton disconn;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JLabel label4;
    private JTextArea to;
    private JTextArea msg;
    private JButton send;
    private JTextArea area;

    private AbstractXMPPConnection connection;
    private ChatManager chatmanager;

    public OpenMain() {
    }

    public OpenMain(String username, String password) {
        this.username = username;
        this.password = password;

        setVisible(true);
        setSize(350, 300);
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("聊天室");
        getContentPane().setLayout(null);
        setDefaultCloseOperation(this.EXIT_ON_CLOSE);

        label1 = new JLabel("发送给:");
        label1.setBounds(10, 30, 60, 25);
        label1.setForeground(Color.RED);
        getContentPane().add(label1);
        label2 = new JLabel("消息:");
        label2.setBounds(10, 60, 60, 25);
        label2.setForeground(Color.RED);
        getContentPane().add(label2);
        label3 = new JLabel("");
        label3.setBounds(250, 0, 100, 20);
        label3.setForeground(Color.RED);
        getContentPane().add(label3);
        label3.setText("当前登录:" + username);
        label4 = new JLabel("@jf.com");
        label4.setBounds(230, 30, 60, 20);
        getContentPane().add(label4);

        conn = new JButton("连接");
        getContentPane().add(conn);
        conn.setBounds(0, 0, 50, 25);
        conn.addActionListener(this);

        disconn = new JButton("断开");
        getContentPane().add(disconn);
        disconn.setEnabled(false);
        disconn.setBounds(55, 0, 50, 25);
        disconn.addActionListener(this);

        to = new JTextArea();
        getContentPane().add(to);
        to.setBounds(60, 30, 160, 25);

        msg = new JTextArea();
        getContentPane().add(msg);
        msg.setBounds(60, 60, 160, 25);

        send = new JButton("发送");
        getContentPane().add(send);
        send.setBounds(60, 95, 80, 25);
        send.addActionListener(this);
        //send.addKeyListener(this);

        area = new JTextArea("");
        getContentPane().add(area);
        //area.setBounds(10, 120, 320, 150);
        area.setEditable(false);
        JScrollPane scroll = new JScrollPane(area);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setBounds(10, 120, 320, 150);
        getContentPane().add(scroll);
    }

    public void startServer() throws Exception {
        XMPPTCPConnectionConfiguration.Builder configBuilder = XMPPTCPConnectionConfiguration.builder()
                .setXmppDomain("jf.com")
                .setHost("test.jf.com")
                .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
                .setDebuggerEnabled(true);

        connection = new XMPPTCPConnection(configBuilder.build());
        /*connection.addPacketSendingListener(new StanzaListener() {
            @Override
            public void processStanza(Stanza stanza) throws SmackException.NotConnectedException, InterruptedException, SmackException.NotLoggedInException {
                System.out.println("processStanza:" + stanza.getFrom());
            }
        }, new StanzaFilter() {
            @Override
            public boolean accept(Stanza stanza) {
                System.out.println("accept from:" + stanza.getFrom() + ",to:" + stanza.getTo());
                return false;
            }
        });*/

        // 连接-登录
        connection.connect();
        connection.login(username, password);

        chatmanager = ChatManager.getInstanceFor(connection);
        chatmanager.addIncomingListener(new IncomingChatMessageListener() {
            @Override
            public void newIncomingMessage(EntityBareJid entityBareJid, Message message, Chat chat) {
                if (null != message.getBody()) {
                    area.append(message.getFrom() + "：" + message.getBody() + "\n");
                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == conn) {
            try {
                System.out.println("正在连接服务器...");
                startServer();
                conn.setEnabled(false);
                disconn.setEnabled(true);
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(this, "启动失败...");
                connection.disconnect();
                conn.setEnabled(true);
                e1.printStackTrace();
            }
        } else if (e.getSource() == disconn) {
            try {
                if (connection == null || !connection.isConnected()) {
                    JOptionPane.showMessageDialog(this, "未连接...");
                } else {
                    connection.disconnect();
                    conn.setEnabled(true);
                    disconn.setEnabled(false);
                }
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(this, "断开失败...");
                e1.printStackTrace();
            }
        } else if (e.getSource() == send) {
            if (msg.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "发送的消息不能为空...");
            } else {
                if (connection == null || !connection.isConnected()) {
                    JOptionPane.showMessageDialog(this, "未连接...");
                } else {
                    try {
                        EntityBareJid jid = JidCreate.entityBareFrom(to.getText() + "@jf.com");
                        Chat chat = chatmanager.chatWith(jid);
                        Message message = new Message();
                        message.setBody(msg.getText());
                        message.setTo(jid);
                        chat.send(message);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}
