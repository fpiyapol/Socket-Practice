import java.util.*;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import javax.swing.*;

public class NewServer {
	
	private static final int port = 8910;
	
	private static HashSet<String> names = new HashSet<>();
	
	private static ArrayList<Integer> num1 = new ArrayList<Integer>();
	private static ArrayList<Integer> num2 = new ArrayList<Integer>();
	
	private static Random rdm = new Random();
	
	private static JFrame frame = new JFrame("AS Server");
	private static JTextField display = new JTextField(50);
	private static JTextField tf = new JTextField(50);
	private static JTextArea ta = new JTextArea("", 5, 50);
	private static JLabel time = new JLabel("Time : -- ");
	private static JPanel pn = new JPanel(new FlowLayout());
	
	private static int no = 0;
	private static int score = 0;
	
	public NewServer() {
		
		//Draw GUI
		display.setEditable(false);
		frame.add(display, BorderLayout.NORTH);
		frame.add(ta);
		pn.add(time);
		pn.add(tf);
		frame.add(pn, BorderLayout.SOUTH);
		
		frame.pack();
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		tf.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int res = Integer.parseInt(tf.getText());
				if(res == (num1.get(no) + num2.get(no))) {
					score += 1;
					tf.setText("");
					no += 1;
					display.setText(num1.get(no) + " + " + num2.get(no));
				}
				
			}
		});
	}
	
	private static class genNum1 extends Thread{
		public void run() {
			for(int i=1; i<=5; i++) {
				int  n = rdm.nextInt(9) + 1;
				num1.add(n);
			}
			for(int i=1; i<=7; i++) {
				int  n = rdm.nextInt(99) + 10;
				num1.add(n);
			}
			for(int i=1; i<=9; i++) {
				int  n = rdm.nextInt(999) + 100;
				num1.add(n);
			}
		}
	}
	
	private static class genNum2 extends Thread{
		public void run() {
			for(int i=1; i<=5; i++) {
				int  n = rdm.nextInt(9) + 1;
				num2.add(n);
			}
			for(int i=1; i<=7; i++) {
				int  n = rdm.nextInt(99) + 10;
				num2.add(n);
			}
			for(int i=1; i<=9; i++) {
				int  n = rdm.nextInt(999) + 100;
				num2.add(n);
			}
		}
	}
	
	private static class timer extends Thread{
		public void run() {
			for(int i = 60; i>=0; i--){
				try {
					Thread.sleep(1000);
					time.setText("Time : " + i);
				} catch (InterruptedException e) {
					System.out.println(e);
				}
			}
		}
	}
	
	private static class clientHandler extends Thread{
		private Socket cs;
		private String name;
        private PrintWriter out;
        private BufferedReader in;
        private ObjectOutputStream objOut;
		
		public clientHandler(Socket cs) {
			this.cs = cs;
		}
		
		public void run() {
			try {
				System.out.println("Connected");
				in = new BufferedReader(new InputStreamReader(cs.getInputStream()));
				out = new PrintWriter(cs.getOutputStream(), true);				
				objOut = new ObjectOutputStream(cs.getOutputStream());
				
				//send obj num1 num2 to client
        		objOut.writeObject(num1);
        		objOut.writeObject(num2);
				
        		//Loop for get unique name of client user
				while(true) {
					out.println("ENTERNAME");
					name = in.readLine();
					if(name == null) {
						return;
					}
					synchronized(names) {
						if (!names.contains(name)) {
                            names.add(name);
                            break;
						}
					}
				}
        		
				//communication between both
				while(true) {
					String input = in.readLine();
        			if(input == null) {
        				return;
        			}else if(input.equals("READY")) {
        				out.println("START");
                		timer t = new timer();
                		t.start();
                		display.setText(num1.get(no) + " + " + num2.get(no));
        			}
        			System.out.println(name + " " + input);
				}
				
			}catch(IOException e) {
				System.out.println(e);
			}finally {
				try {
					cs.close();
				}catch (IOException e) {
					System.out.println(e);
				}
			}
		}
	}
	
	public void init() throws IOException {
		ServerSocket ss = new ServerSocket(port);
		genNum1 gn1 = new genNum1();
		genNum2 gn2 = new genNum2();
		gn1.start();
		gn2.start();
		//Name of user as server
		names.add("Server");
		try {
			while(true) {
				clientHandler ch = new clientHandler(ss.accept());
				ch.start();
			}
		}finally{
			ss.close();
		}
	}
	
	public static void main(String[] args) throws IOException {
		NewServer ns = new NewServer();
		ns.init();
	}

}
