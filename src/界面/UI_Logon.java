package ����;

import javax.swing.*;

import QQ_client.logon;

import java.awt.event.*;
import java.io.IOException;
import java.util.regex.Pattern;
import java.awt.*;
public class UI_Logon extends JFrame implements ActionListener{

	JTextField jTextField1,jTextField2,jTextField3,jTextField4,jTextField5,jTextField6;//�����ı������
    JPasswordField jPasswordField1,jPasswordField2;//������������
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
        jLabel0 = new JLabel("����д������Ϣ���ע�����");
        jLabel1 = new JLabel("�������û���");
        jLabel2 = new JLabel("����������    ");
        jLabel3 = new JLabel("��ȷ������    ");
        jLabel4 = new JLabel("����������    ");
        jLabel5 = new JLabel("�������Ա�    ");
        jLabel6 = new JLabel("������ѧУ    ");
        jLabel7 = new JLabel("����������    ");
        jLabel8 = new JLabel("����������    ");
        jb = new JButton("ע��");
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
        //���ò���
        setLocationRelativeTo(null);
        this.setLayout(new GridLayout(10,1));
        jp1.add(jLabel0);
        jp2.add(jLabel1); 
        jp2.add(jTextField1);//��2���������û������ı��� 
        jp3.add(jLabel2);
        jp3.add(jPasswordField1);
        jp4.add(jLabel3);
        jp4.add(jPasswordField2);//��3����������������������
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
        //������ʾ
        this.setSize(500, 400);
        this.setLocationRelativeTo(null);
        //this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setTitle("ע�����");
         
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
	    		JOptionPane.showMessageDialog(this, "�û�������Ϊ�գ���");
	    	}
	    	else if(password1.equals("")||!password1.equals(password2)){
	    		bool=false;
	    		JOptionPane.showMessageDialog(this, "����Ϊ�ջ������������벻һ��");
	    	}
	    	else if(mail.equals("")||!regex.matcher(mail).matches()){
	    		bool=false;
	    		JOptionPane.showMessageDialog(this, "����д��ȷ��������Ϣ");
	    	}
	    	else if(sex.equals("")){
	    		bool=false;
	    		JOptionPane.showMessageDialog(this, "�Ա���Ϊ�գ���");
	    	}
	    	else if(school.equals("")){
	    		bool=false;
	    		JOptionPane.showMessageDialog(this, "ѧУ����Ϊ�գ���");
	    	}
	    	else if(constellation.equals("")){
	    		bool=false;
	    		JOptionPane.showMessageDialog(this, "��������Ϊ�գ���");
	    	}
	    	else if(birthday.equals("")||!regex1.matcher(birthday).matches()){
	    		bool=false;
	    		JOptionPane.showMessageDialog(this, "��������ȷ�����ڸ�ʽ�磺2000.01.01");
	    	}
	    	if(cmd.equals("ע��")){
	    		
	    		if(bool)
	    		{
	    			//Login.loginin(user,password)
		    		logon log=new logon(user,password1,sex,birthday,mail,school,constellation);
		    		try {
						log.Logon_message("192.168.1.107",6666);
						String id=log.LOGIN_ID;
						if(log.LOGIN_ID!=null)
						{
							JOptionPane.showMessageDialog(this,"���ס���ĵ�¼ID��"+ log.LOGIN_ID,"ע����",1);
						}
						else
						{
							JOptionPane.showMessageDialog(this,"ע��ʧ�ܣ�����������Ϣ�Ƿ���ȷ","ע����",1);
						}
						
						
						
					} catch (IOException e1) {
						// TODO �Զ����ɵ� catch ��
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
