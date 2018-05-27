package 界面;

import javax.swing.*;

import QQ_client.login;

import java.awt.event.*;
import java.io.IOException;
import java.awt.*;

public class UI_login extends JFrame implements ActionListener{
    JTextField jTextField ;//定义文本框组件
    JPasswordField jPasswordField;//定义密码框组件
    JLabel jLabel1,jLabel2;
    JPanel jp1,jp2,jp3,jp4;
    JButton jb1,jb2; //创建按钮
    public UI_login(){
        jTextField = new JTextField(12);
        jPasswordField = new JPasswordField(12);
        jLabel1 = new JLabel("用户名");
        jLabel2 = new JLabel("密码    ");
        jb1 = new JButton("登录");
        jb1.addActionListener(this);
        jb2 = new JButton("注册");
        jb2.addActionListener(this);
        jp1 = new JPanel();
        jp2 = new JPanel();
        jp3 = new JPanel();
        jp4 = new JPanel();
//        jp5 = new JPanel();
        //设置布局
        this.setLayout(new GridLayout(5,1));
        jp2.add(jLabel1); 
        jp2.add(jTextField);//第2块面板添加用户名和文本框 
        jp3.add(jLabel2);
        jp3.add(jPasswordField);//第3块面板添加密码和密码输入框
        jp4.add(jb1);
        jp4.add(jb2); 
        this.add(jp1);
        this.add(jp2);
        this.add(jp3);
        this.add(jp4);
        //设置显示
        this.setSize(400, 300);
        this.setLocationRelativeTo(null);
        //this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setTitle("登录界面");
         
    }
    
    public void actionPerformed(ActionEvent e){
    	String cmd=e.getActionCommand();
    	System.out.println("sdasdarfea");
    	String user=jTextField.getText();
    	System.out.println(user);
    	char pass[]=jPasswordField.getPassword();
    	String password=new String(pass);
    	if(cmd.equals("登录")){
    		if(user==null||user.equals("")){
        		JOptionPane.showMessageDialog(this, "用户名不能为空！！");
        	}
        	else if(password.equals("")){
        		JOptionPane.showMessageDialog(this, "密码不能为空！！");
        	}
    		login zc=new login(null,user,password);
    		try {
				zc.cert_as("localhost",6668);
			} catch (IOException e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			}
    		//Login.loginin(user,password)
    	}
    	else if(cmd.equals("注册")){
    		new UI_Logon().Log_on();;
    		dispose();  
    	}
    }
    public static void main(String[] args){
        new UI_login();
    }
}
