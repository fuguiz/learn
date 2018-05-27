package 界面;

import javax.swing.*;

import java.awt.*;

import java.awt.event.*;

public class 所示 {

//创建登陆界面类

public void showLoginFrame(){

//创建船体对象

JFrame loginFrame=new JFrame();

//设置大小，位置，标题

loginFrame.setSize(300,200);

loginFrame.setTitle("QQ2014");

loginFrame.setLocationRelativeTo(null);

//创建流式分布对象

FlowLayout layout=new FlowLayout();

loginFrame.setLayout(layout);

//创建账户名，密码和输入框

JLabel user_name=new JLabel("账号：");

JLabel user_password=new JLabel("密码：");

JTextField field_name=new JTextField(20);

JPasswordField field_password=new JPasswordField(20);

//创建登陆，重置按钮

JButton button_reset=new JButton("重置");

JButton button_login=new JButton("登陆");

//设置窗体可见

loginFrame.setVisible(true);

//创建事件监听对象

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

System.out.println("密码错误，重新输入!");

}

}

};

ActionListener action_listener2=new ActionListener(){

public void actionPerformed(ActionEvent e){

field_name.setText("");

field_password.setText("");

}

};

//将文本输入框，按钮，事件监听对象添加

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

//创建窗体对象

JFrame indexFrame=new JFrame();

indexFrame.setSize(200,500);

indexFrame.setTitle("QQ好友列表");

indexFrame.setLocationRelativeTo(null);

//设置流式分布对象

FlowLayout layout=new FlowLayout(FlowLayout.CENTER,100,10);

indexFrame.setLayout(layout);

//创建好友按钮

for(int i=0;i<10;i++)

{

JButton button_friend=new JButton("friend"+i);

//创建动作事件监听对象

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

//设置窗体可见

indexFrame.setVisible(true);

}

public void showChatFrame(){

//创建窗体，大小，位置，标题

JFrame chatFrame=new JFrame();

chatFrame.setSize(400,400);

chatFrame.setTitle("正在聊天中...");

chatFrame.setLocationRelativeTo(null);

//创建聊天记录，输入域

JTextArea area_input=new JTextArea(10,30);

JTextArea area_record=new JTextArea(5,30);

//创建流式分布对象

FlowLayout layout=new FlowLayout(FlowLayout.CENTER,0,10);

chatFrame.setLayout(layout);

//创建发送，关闭按扭

JButton button_send=new JButton("发送");

JButton button_close=new JButton("关闭");

//创建动作事件监听对象

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

//设置窗体可见

chatFrame.setVisible(true);

//添加按钮，事件监听对象

chatFrame.add(area_record);

chatFrame.add(area_input);

chatFrame.add(button_send);

chatFrame.add(button_close);

button_send.addActionListener(action_listener1);

button_close.addActionListener(action_listener2);

}

} 