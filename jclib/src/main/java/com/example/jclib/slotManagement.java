package com.example.jclib;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;
import java.util.Date;
public class slotManagement
{
    JFrame frame;
    JPanel topPanel,bottomPanel;
    JLabel date,slottime,slotmanagement,hour,minute,background,dateOfExam;
    JTextField dateText,timeText;
    JButton update,delete,close;
    Font headingfont,labelfont;
    JComboBox datecombo,monthcombo,yearcombo,mmcombo,hhcombo;
    Date today;

    String months[]={"Month","01","02","03","04","05","06","07","08","09","10","11","12"};
    String dates[]={"Date","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};
    String years[]={"Year","2014","2015","2016","2017","2018","2019","2020","2021","2022","2023","2024","2025","2026","2027","2028","2029","2030","2031","2032"};
    String hh[]=new String[24];
    String mm[]=new String[61];
    String dd,m,y,h,min;
    int j,i;
    int	mint;
    public slotManagement()
    {
        headingfont=new Font("Arial",Font.PLAIN,18);
        labelfont=new Font("Arial",Font.BOLD,25);

        datecombo=new JComboBox(dates);
        monthcombo=new JComboBox(months);
        yearcombo=new JComboBox(years);

        dateText=new JTextField(15);
        frame=new JFrame("Slot Management");
        topPanel=new JPanel();
        bottomPanel=new JPanel();
        // datet=new JLabel("Date:");
        //year=new JLabel("Year:");
        //	month=new JLabel("Month:");

        date=new JLabel("DATE:");
        date.setForeground(Color.WHITE);
        //  datet.setForeground(Color.WHITE);

        //  month.setForeground(Color.WHITE);
        //   year.setForeground(Color.WHITE);

        slottime=new JLabel("SLOT TIME:");
        slotmanagement=new JLabel("SLOT MANAGEMENT");
        slottime.setForeground(Color.WHITE);

        hour=new JLabel("Hour:");
        minute=new JLabel("Minute:");
        dateText=new JTextField(15);
        timeText=new JTextField(15);
        hour.setForeground(Color.WHITE);
        minute.setForeground(Color.WHITE);

        for(i=0;i<=60;i++)
        {
            mm[i]=i+"";}
        for(j=0;j<24;j++)
        {
            if(j<10)
            {
                hh[j]="0"+j;
            }
            else
            {
                hh[j]=j+"";
            }
        }

        mmcombo=new JComboBox(mm);
        hhcombo=new JComboBox(hh);
        update=new JButton("Create");
        delete=new JButton("Delete");
        update.setToolTipText("Create a new Time Slot");
        delete.setToolTipText("Delete a Time Slot");
        close=new JButton("Close");
        close.setToolTipText("Close Window");


        //background
        ImageIcon background_image=new ImageIcon("images/slot.jpg");
        Image img=background_image.getImage();
        Image temp_img=img.getScaledInstance(900, 600, Image.SCALE_SMOOTH);
        background_image=new ImageIcon(temp_img);
        background=new JLabel("",background_image, JLabel.CENTER);
        background.setBounds(0,0,900,600);
        background.setLayout(null);

        topPanel.setBackground(new Color(0,0,0,60));
        slotmanagement.setForeground(Color.white);
        topPanel.setBounds(0, 0, 500, 40);
        background.add(topPanel);

        slotmanagement.setFont(labelfont);
        topPanel.add(slotmanagement);

        date.setBounds(40,70,100,30);
        background.add(datecombo);


        //datet.setBounds(70,120,120,20);
        //background.add(datet);

        datecombo.setBounds(120,75,60,20);
        background.add(date);
        date.setBackground(Color.white);

        //month.setBounds(200,120,120,20);
        //background.add(month);

        monthcombo.setBounds(260,75,90,20);
        background.add(monthcombo);
        monthcombo.setBackground(Color.white);


        //year.setBounds(355,120,120,20);
        //background.add(year);

        yearcombo.setBounds(400,75,70,20);
        background.add(yearcombo);
        yearcombo.setBackground(Color.white);



        hour.setBounds(40,120,120,20);
        background.add(hour);

        hhcombo.setBounds(125,120,60,20);
        background.add(hhcombo);
        hhcombo.setBackground(Color.white);

        minute.setBounds(200,120,60,20);
        background.add(minute);

        mmcombo.setBounds(265,120,60,20);
        background.add(mmcombo);
        mmcombo.setBackground(Color.white);


        dateText.setBounds(200, 170, 120, 25);
        background.add(dateText);
        dateText.setEditable(false);
        dateText.setFont(headingfont);
        dateText.setBackground(Color.white);


        dateOfExam=new JLabel("Date Of Exam:");
        dateOfExam.setFont(headingfont);
        dateOfExam.setForeground(Color.white);
        dateOfExam.setBounds(40, 170, 120, 20);
        background.add(dateOfExam);


        slottime.setBounds(40,220,120,20);
        background.add(slottime);

        timeText.setBounds(200, 215, 120, 25);
        timeText.setFont(headingfont);
        background.add(timeText);
        timeText.setEditable(false);
        timeText.setBackground(Color.white);




        update.setBounds(70,300,100,30);
        background.add(update);

        delete.setBounds(190,300,100,30);
        background.add(delete);

        close.setBounds(310,300,100,30);
        background.add(close);

        update.setFont(headingfont);
        delete.setFont(headingfont);
        close.setFont(headingfont);
        date.setFont(headingfont);
        slottime.setFont(headingfont);
        hour.setFont(headingfont);
        minute.setFont(headingfont);
        //	month.setFont(headingfont);
        //	datet.setFont(headingfont);
        //	year.setFont(headingfont);


        frame.add(background);
        frame.setResizable(true);

        Dimension d= Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation((d.width-500)/2,(d.height-400)/2);
        frame.setSize(500,400);
        frame.setVisible(true);

    }

    public static void main(String[] args)
    {
        new slotManagement();
    }


}
