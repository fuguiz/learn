package ����;

import javax.swing.*;

import java.awt.*;

import java.awt.event.*;

public class ��ʾ {

//������½������

public void showLoginFrame(){

//�����������

JFrame loginFrame=new JFrame();

//���ô�С��λ�ã�����

loginFrame.setSize(300,200);

loginFrame.setTitle("QQ2014");

loginFrame.setLocationRelativeTo(null);

//������ʽ�ֲ�����

FlowLayout layout=new FlowLayout();

loginFrame.setLayout(layout);

//�����˻���������������

JLabel user_name=new JLabel("�˺ţ�");

JLabel user_password=new JLabel("���룺");

JTextField field_name=new JTextField(20);

JPasswordField field_password=new JPasswordField(20);

//������½�����ð�ť

JButton button_reset=new JButton("����");

JButton button_login=new JButton("��½");

//���ô���ɼ�

loginFrame.setVisible(true);

//�����¼���������

ActionListener action_listener1=new ActionListener(){

public void actionPerformed(ActionEvent e){

String name=field_name.getText();

String password=field_password.getText();

if("zhaoxin".equals(name)&&"123".equals(password))

{

showIndexFrame();

loginFrame.setDefaultCloseOperation(3);

loginFrame.setVisible(false);

}

else{

System.out.println("���������������!");

}

}

};

ActionListener action_listener2=new ActionListener(){

public void actionPerformed(ActionEvent e){

field_name.setText("");

field_password.setText("");

}

};

//���ı�����򣬰�ť���¼������������

loginFrame.add(user_name);

loginFrame.add(field_name);

loginFrame.add(user_password);

loginFrame.add(field_password);

loginFrame.add(button_reset);

loginFrame.add(button_login);

button_reset.addActionListener(action_listener2);

button_login.addActionListener(action_listener1);

}

public void showIndexFrame(){

//�����������

JFrame indexFrame=new JFrame();

indexFrame.setSize(200,500);

indexFrame.setTitle("QQ�����б�");

indexFrame.setLocationRelativeTo(null);

//������ʽ�ֲ�����

FlowLayout layout=new FlowLayout(FlowLayout.CENTER,100,10);

indexFrame.setLayout(layout);

//�������Ѱ�ť

for(int i=0;i<10;i++)

{

JButton button_friend=new JButton("friend"+i);

//���������¼���������

ActionListener action_listener=new ActionListener()

{

public void actionPerformed(ActionEvent e)

{

showChatFrame();

indexFrame.setVisible(false);

indexFrame.setDefaultCloseOperation(3);

}

};

button_friend.addActionListener(action_listener);

indexFrame.add(button_friend);

}

//���ô���ɼ�

indexFrame.setVisible(true);

}

public void showChatFrame(){

//�������壬��С��λ�ã�����

JFrame chatFrame=new JFrame();

chatFrame.setSize(400,400);

chatFrame.setTitle("����������...");

chatFrame.setLocationRelativeTo(null);

//���������¼��������

JTextArea area_input=new JTextArea(10,30);

JTextArea area_record=new JTextArea(5,30);

//������ʽ�ֲ�����

FlowLayout layout=new FlowLayout(FlowLayout.CENTER,0,10);

chatFrame.setLayout(layout);

//�������ͣ��رհ�Ť

JButton button_send=new JButton("����");

JButton button_close=new JButton("�ر�");

//���������¼���������

ActionListener action_listener1=new ActionListener()

{

public void actionPerformed(ActionEvent e){

area_record.setText(area_record.getText()+"\n"+area_input.getText());

area_input.setText("");

}

};

ActionListener action_listener2=new ActionListener()

{

public void actionPerformed(ActionEvent e){

chatFrame.setVisible(false);

chatFrame.setDefaultCloseOperation(3);

}

};

//���ô���ɼ�

chatFrame.setVisible(true);

//��Ӱ�ť���¼���������

chatFrame.add(area_record);

chatFrame.add(area_input);

chatFrame.add(button_send);

chatFrame.add(button_close);

button_send.addActionListener(action_listener1);

button_close.addActionListener(action_listener2);

}

} 