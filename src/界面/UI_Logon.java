package 界面;

import javax.swing.*;

import QQ_client.logon;

import java.awt.event.*;
import java.io.IOException;
import java.util.regex.Pattern;
import java.awt.*;
public class UI_Logon extends JFrame implements ActionListener{

	JTextField jTextField1,jTextField2,jTextField3,jTextField4,jTextField5,jTextField6;//定义文本框组件
    JPasswordField jPasswordField1,jPasswordField2;//定义密码框组件
    JLabel jLabel0,jLabel1,jLabel2,jLabel3,jLabel4,jLabel5,jLabel6,jLabel7,jLabel8;
    JPanel jp1,jp2,jp3,jp4,jp5,jp6,jp7,jp8,jp9,jp10;
    JButton jb; 
    public void Log_on(){
        jTextField1 = new JTextField(12);
        jTextField2 = new JTextField(12);
        jTextField3 = new JTextField(12);
        jTextField4 = new JTextField(12);
        jTextField5 = new JTextField(12);
        jTextField6 = new JTextField(12);
        jPasswordField1 = new JPasswordField(12);
        jPasswordField2 = new JPasswordField(12);
        jLabel0 = new JLabel("请填写以下信息完成注册过程");
        jLabel1 = new JLabel("请输入用户名");
        jLabel2 = new JLabel("请输入密码    ");
        jLabel3 = new JLabel("请确认密码    ");
        jLabel4 = new JLabel("请输入邮箱    ");
        jLabel5 = new JLabel("请输入性别    ");
        jLabel6 = new JLabel("请输入学校    ");
        jLabel7 = new JLabel("请输入星座    ");
        jLabel8 = new JLabel("请输入生日    ");
        jb = new JButton("注册");
        jb.addActionListener(this);
        jp1 = new JPanel();
        jp2 = new JPanel();
        jp3 = new JPanel();
        jp4 = new JPanel();
        jp5 = new JPanel();
        jp6 = new JPanel();
        jp7 = new JPanel();
        jp8 = new JPanel();
        jp9 = new JPanel();
        jp10= new JPanel();
        //设置布局
        setLocationRelativeTo(null);
        this.setLayout(new GridLayout(10,1));
        jp1.add(jLabel0);
        jp2.add(jLabel1); 
        jp2.add(jTextField1);//第2块面板添加用户名和文本框 
        jp3.add(jLabel2);
        jp3.add(jPasswordField1);
        jp4.add(jLabel3);
        jp4.add(jPasswordField2);//第3块面板添加密码和密码输入框
        jp5.add(jLabel4);
        jp5.add(jTextField2); 
        jp6.add(jLabel5);
        jp6.add(jTextField3);
        jp7.add(jLabel6);
        jp7.add(jTextField4);
        jp8.add(jLabel7);
        jp8.add(jTextField5);
        jp9.add(jLabel8);
        jp9.add(jTextField6);
        jp10.add(jb);
        this.add(jp1);
        this.add(jp2);
        this.add(jp3);
        this.add(jp4);
        this.add(jp5);
        this.add(jp6);
        this.add(jp7);
        this.add(jp8);
        this.add(jp9);
        this.add(jp10);
        //设置显示
        this.setSize(500, 400);
        this.setLocationRelativeTo(null);
        //this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setTitle("注册界面");
         
    }
	
	 public void actionPerformed(ActionEvent e){
		 boolean bool=true;
		 String cmd=e.getActionCommand();
//	    	System.out.println("sdasdarfea");
	    	String user=jTextField1.getText();
	    	System.out.println(user);
	    	char pass1[]=jPasswordField1.getPassword();
	    	String password1=new String(pass1);
	    	System.out.println(password1);
	    	char pass2[]=jPasswordField2.getPassword();
	    	String password2=new String(pass2);
	    	System.out.println(password1);
	    	String check="^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
	    	Pattern regex = Pattern.compile(check);
	    	String data="[0-9]{4}.[0-9]{2}.[0-9]{2}";
	    	Pattern regex1 = Pattern.compile(data);
	    	String mail=jTextField2.getText();
	    	System.out.println(mail);
	    	String sex=jTextField3.getText();
	    	String school=jTextField4.getText();
	    	String constellation=jTextField5.getText();
	    	String birthday=jTextField6.getText();
	    	if(user.equals("")){
	    		bool=false;
	    		JOptionPane.showMessageDialog(this, "用户名不能为空！！");
	    	}
	    	else if(password1.equals("")||!password1.equals(password2)){
	    		bool=false;
	    		JOptionPane.showMessageDialog(this, "密码为空或两次输入密码不一致");
	    	}
	    	else if(mail.equals("")||!regex.matcher(mail).matches()){
	    		bool=false;
	    		JOptionPane.showMessageDialog(this, "请填写正确的邮箱信息");
	    	}
	    	else if(sex.equals("")){
	    		bool=false;
	    		JOptionPane.showMessageDialog(this, "性别不能为空！！");
	    	}
	    	else if(school.equals("")){
	    		bool=false;
	    		JOptionPane.showMessageDialog(this, "学校不能为空！！");
	    	}
	    	else if(constellation.equals("")){
	    		bool=false;
	    		JOptionPane.showMessageDialog(this, "星座不能为空！！");
	    	}
	    	else if(birthday.equals("")||!regex1.matcher(birthday).matches()){
	    		bool=false;
	    		JOptionPane.showMessageDialog(this, "请输入正确的日期格式如：2000.01.01");
	    	}
	    	if(cmd.equals("注册")){
	    		
	    		if(bool)
	    		{
	    			//Login.loginin(user,password)
		    		logon log=new logon(user,password1,sex,birthday,mail,school,constellation);
		    		try {
						log.Logon_message("192.168.1.107",6666);
						String id=log.LOGIN_ID;
						if(log.LOGIN_ID!=null)
						{
							JOptionPane.showMessageDialog(this,"请记住您的登录ID："+ log.LOGIN_ID,"注册结果",1);
						}
						else
						{
							JOptionPane.showMessageDialog(this,"注册失败，请检查所填信息是否正确","注册结果",1);
						}
						
						
						
					} catch (IOException e1) {
						// TODO 自动生成的 catch 块
						e1.printStackTrace();
					}
	    		}
	    		
	    		
	    		
	    	}
	 }
//	    public static void main(String[] args){
//	        UI_Logon test =new UI_Logon();
//	        test.Log_on();
//	    }
}
