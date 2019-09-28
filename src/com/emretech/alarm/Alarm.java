package com.emretech.alarm;

import java.time.LocalTime;
import java.util.ArrayList;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class Alarm {
	
	static String stringTime;
	static int exit = 0;
	static JLabel Time;
	static File file = new File(System.getProperty("user.dir") + "/res/" + "alarms.txt");
	static ArrayList<String> alarms = new ArrayList<String>();
	
	/**
	 * @wbp.parser.entryPoint
	 */
	public Alarm() {
		JFrame frame = new JFrame("Alarm");
		frame.setSize(450, 300);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setLayout(null);	
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exit = 1;
			}
		});
		btnExit.setBounds(0, 243, 117, 29);
		frame.getContentPane().add(btnExit);
		
		Time = new JLabel("");
		Time.setFont(new Font("Lucida Grande", Font.PLAIN, 30));
		Time.setHorizontalAlignment(SwingConstants.CENTER);
		Time.setBounds(6, 57, 438, 86);
		frame.getContentPane().add(Time);
		
		JButton btnCreateNewAlarm = new JButton("Create new Alarm");
		btnCreateNewAlarm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String alarmName = JOptionPane.showInputDialog(null, "Name for Alarm:");
				String alarmTime = JOptionPane.showInputDialog(null, "Time for Alarm:");
				FileManagement.appendToFile(file, alarmName + " " + alarmTime);
			}
		});
		btnCreateNewAlarm.setBounds(6, 202, 138, 29);
		frame.getContentPane().add(btnCreateNewAlarm);
		frame.setVisible(true);
	}
	
	private static void calculateTime(int hour, int minute, int second, String AMPM, String midnight) {
		if (hour > 12) {
			AMPM = "PM";
			hour -= 12;
		}
		if (hour < 12 && AMPM == "AM") {
			AMPM = "AM";
		}
		if (hour == 12 && midnight == "No") {
			AMPM = "PM";
		}
		if (hour == 0) {
			midnight = "Yes";
			hour = 12;
		}
		if (hour > 0) {
			midnight = "No";
		}
		if (second < 10) {
			stringTime = hour + ":" + minute + ":0" + second + "" + AMPM;
		} else if (second > 9) {
			stringTime = hour + ":" + minute + ":" + second + "" + AMPM;
		}
		
		if (minute < 10) {
			stringTime = hour + ":0" + minute + ":" + second + "" + AMPM;
		} else if (minute > 9) {
			stringTime = hour + ":" + minute + ":" + second + "" + AMPM;
		}
	}
	
	private static void checkAlarms() {
		String alarmTimee = null;
		boolean alarm = false;
		FileManagement.readLineFromFile(file, alarms);
		for (int i = 0; i < alarms.size(); i++) {
			if (alarms.get(i).contains(" ")) { 
				alarmTimee = alarms.get(i).substring(alarms.get(i).indexOf(" "));
				alarmTimee.trim();
			} 
			//System.out.println(alarmTimee);
			alarm = alarmTimee == stringTime;
			if (alarm) {
				System.out.println("Alarm");
			}
		}
		
		System.out.println(alarm);
	}
	
	private static void alarm() {
		Music music = null;
		try {
			music = new Music(System.getProperty("user.dir") + "/res/morning_flower.wav");
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
		music.play();
	}
	
	public static void tick() {
		LocalTime time = java.time.LocalTime.now();
		int hour = time.getHour();
		int minute = time.getMinute();
		int second = time.getSecond();
		String AMPM = null;
		stringTime = null;
		String midnight = "No";
		calculateTime(hour,minute,second,AMPM,midnight);
		checkAlarms();
		Time.setText(stringTime);
	}
	
	public static void main(String[] args) {
		new Alarm();
		while (exit == 0) {
			tick();
		}
		if (exit == 1) {
			System.exit(0);
		}
	}
}
