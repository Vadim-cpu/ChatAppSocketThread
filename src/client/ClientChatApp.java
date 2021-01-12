package client;
 
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientChatApp {

	public static void main(String[] args) throws IOException {
		
		print ("CLIENT STARTING");

		Socket cs = new Socket("localhost",9999);
		print("client connecting to server");
		DataOutputStream dous = new DataOutputStream(cs.getOutputStream());
		DataInputStream dins = new DataInputStream(cs.getInputStream());
		
		//Thread for handling incoming messages
		
		new Thread	(new Runnable() {

			@Override
			public void run() {
				boolean loop = true;
				do {
					try {
						if(dins.available() > 0) {
							String message = dins.readUTF();
							print("<<<<<From SERVER " + message);
						}
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}while(loop);
			}
		}).start();	
		//main thread for outgoing messages
		boolean loop = true;
		do {
			String message;
			
			
			message = scan("Enter message");
			dous.writeUTF(message);
			dous.flush();
		}while(loop);
		
		print ("CLIENT ENDING");
		
	}

	public static String scan(String what) {
		System.out.println(what + ":" );
		return new Scanner(System.in).nextLine();
	}
	
	public static void print(String what) {
		System.out.println(what);
	}
}
