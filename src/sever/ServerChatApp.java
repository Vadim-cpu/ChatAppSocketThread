package sever;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ServerChatApp {
	
	
	static ArrayList<Socket> clients = new ArrayList<>();

	public static void main(String[] args) throws IOException, InterruptedException {
		
		print("SERVER STARTING");
	
		// SOCKET HANDLING
		new Thread	(new Runnable() {

			@Override
			public void run() {
				ServerSocket ss;
				try {
					ss = new ServerSocket(9999);
				
				boolean loop = true;
				
				do {
					Socket cls;
					try {
						cls = ss.accept(); // <--this blocking
						clients.add(cls);
						print(">> client entered");
						print(">> total Clients entered " + clients.size());
//						clients.forEach(c -> System.out.println(" [" + c + "]") );
					
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
									
					
				}while(loop);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
			
		}).start();
	
	// MESSAGES HANDLING thread of main
	
	boolean loop = true;
	
	do {
		
//		clients.forEach( c -> {
//		for(Socket c: clients) {
		for (int i=0;i<clients.size(); i++) {
		 Socket c = clients.get(i);
			try {
//				DataOutputStream dous = new DataOutputStream(c.getOutputStream());
				DataInputStream dins = new DataInputStream(c.getInputStream());
				
				String message =  dins.readUTF();
				print(">>>>>> a client sends: " + message);
				print(">>>>>> broadcasting: " + message);
				//clients.forEach(bc -> {
//				for(Socket bc: clients)	{
				for(int ii=0;ii<clients.size();ii++) {
					Socket bc = clients.get(ii);
					if ( !bc.equals(c)) {
						try {
							DataOutputStream dous = new DataOutputStream(bc.getOutputStream());
							dous.writeUTF(message);
							dous.flush();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
				}//);
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}//);
//		print("size:" + clients.size());				
		Thread.sleep(200);
	}while(loop);
	
		print("SERVER ENDING");
		
	}

	public static String scan(String what) {
		System.out.println(what + ":" );
		return new Scanner(System.in).nextLine();
	}
	
	public static void print(String what) {
		System.out.println(what);
	}
	}

	
	

