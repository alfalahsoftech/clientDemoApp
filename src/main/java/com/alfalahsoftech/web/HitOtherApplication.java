package com.alfalahsoftech.web;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class HitOtherApplication {
	static Timer timer = new Timer();
	public static void hitApp() {
		
		TimerTask task = new TimerTask() {
			
			@Override
			public void run() {
				
				System.out.println(new Date()+"Timer started for => http://afsso.herokuapp.com/headMethod \n\n ");
				try {
					URL url = new URL("http://afsso.herokuapp.com/headMethod");
					HttpURLConnection con = (HttpURLConnection) url.openConnection();
					con.setRequestMethod("GET");
					con.setRequestProperty("Accept", "application/json");
					con.connect();
					InputStream is= con.getInputStream();
					
					BufferedReader bfri= new BufferedReader(new InputStreamReader(is));
					String output;
					
					while((output =bfri.readLine())!=null) {
						System.out.println(output);
					}
				
					con.disconnect();
					System.out.println("\n\n  ");
					//timer.cancel();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		};
		timer.schedule(task, 10,1000*60*20); // 1000=1 sec* 60=> 1 minute *20=> 20 minutes:  every 20 minutes
	}
	
	public static void disConnect() {
		if(Objects.nonNull(timer)) {
			timer.cancel();
		}
	}
	public static void main(String[] args) {
		hitApp();

	}
}
