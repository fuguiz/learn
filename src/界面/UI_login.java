package ����;

import javax.swing.*;

import QQ_client.login;

import java.awt.event.*;
import java.io.IOException;
import java.awt.*;

public class UI_login extends JFrame implements ActionListener{
    JTextField jTextField ;//�����ı������
    JPasswordField jPasswordField;//������������
    JLabel jLabel1,jLabel2;
    JPanel jp1,jp2,jp3,jp4;
    JButton jb1,jb2; //������ť
    public UI_login(){
        jTextField = new JTextField(12);
        jPasswordField = new JPasswordField(12);
        jLabel1 = new JLabel("�û���");
        jLabel2 = new JLabel("����    ");
        jb1 = new JButton("��¼");
        jb1.addActionListener(this);
        jb2 = new JButton("ע��");
        jb2.addActionListener(this);
        jp1 = new JPanel();
        jp2 = new JPanel();
        jp3 = new JPanel();
        jp4 = new JPanel();
//        jp5 = new JPanel();
        //���ò���
        this.setLayout(new GridLayout(5,1));
        jp2.add(jLabel1); 
        jp2.add(jTextField);//��2���������û������ı��� 
        jp3.add(jLabel2);
        jp3.add(jPasswordField);//��3����������������������
        jp4.add(jb1);
        jp4.add(jb2); 
        this.add(jp1);
        this.add(jp2);
        this.add(jp3);
        this.add(jp4);
        //������ʾ
        this.setSize(400, 300);
        this.setLocationRelativeTo(null);
        //this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setTitle("��¼����");
         
    }
    
    public void actionPerformed(ActionEvent e){
    	String cmd=e.getActionCommand();
    	System.out.println("sdasdarfea");
    	String user=jTextField.getText();
    	System.out.println(user);
    	char pass[]=jPasswordField.getPassword();
    	String password=new String(pass);
    	if(cmd.equals("��¼")){
    		if(user==null||user.equals("")){
        		JOptionPane.showMessageDialog(this, "�û�������Ϊ�գ���");
        	}
        	else if(password.equals("")){
        		JOptionPane.showMessageDialog(this, "���벻��Ϊ�գ���");
        	}
    		login zc=new login(null,user,password);
    		try {
				zc.cert_as("localhost",6668);
			} catch (IOException e1) {
				// TODO �Զ����ɵ� catch ��
				e1.printStackTrace();
			}
    		//Login.loginin(user,password)
    	}
    	else if(cmd.equals("ע��")){
    		new UI_Logon().Log_on();;
    		dispose();  
    	}
    }
    public static void main(String[] args){
        new UI_login();
    }
}
