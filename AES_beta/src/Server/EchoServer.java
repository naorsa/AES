package Server;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.mysql.jdbc.PreparedStatement;

import application.Message;
import application.Question;
import ocsf.server.*;

/**
 * This Class is implements the server side The class extends the AbstractServer
 * class Super class is implemented all the connection methods
 */
public class EchoServer extends AbstractServer {

	final public static int DEFAULT_PORT = 8000;
	String[] recivedMSG;
	private ArrayList<Question> dataBase;
	private static Connection conn;

	public EchoServer(int port) {
		super(port);
	}

	// Instance methods ************************************************

	/**
	 * This method handles any messages received from the client.
	 *
	 * @param msg
	 *            The message received from the client.
	 * @param client
	 *            The connection from which the message originated.
	 */
	public void handleMessageFromClient(Object msg, ConnectionToClient client) {
		Message handleMsg = (Message) msg;
		System.out.println("Message received: " + handleMsg.getMsg() + " from " + client);
		recivedMSG = handleMsg.getMsg().split("-");
		dataBase = new ArrayList<Question>();
		if (recivedMSG[0].equals("get")) {
			getObj(handleMsg);
		}
		if (recivedMSG[0].equals("set"))
			setObj(handleMsg);
	}

	/**
	 * This method overrides the one in the superclass. Called when the server
	 * starts listening for connections.
	 */
	protected void serverStarted() {
		System.out.println("Server listening for connections on port " + getPort());
	}

	/**
	 * This method overrides the one in the superclass. Called when the server stops
	 * listening for connections.
	 */
	protected void serverStopped() {
		System.out.println("Server has stopped listening for connections.");
	}

	// Class methods ***************************************************

	/**
	 * This method is responsible for the creation of the server instance (there is
	 * no UI in this phase).
	 *
	 * @param args[0]
	 *            The port number to listen on. Defaults to 5555 if no argument is
	 *            entered.
	 */
	public static void main(String[] args) {
		int port = 0; // Port to listen on
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception ex) {
			/* handle the error */}

		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/aes", "root", "308023");
			System.out.println("SQL connection succeed");
		} catch (SQLException ex) {/* handle any errors */
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}

		try {
			port = Integer.parseInt(args[0]); // Get port from command line
		} catch (Throwable t) {
			port = DEFAULT_PORT;
		}

		EchoServer sv = new EchoServer(port);

		try {
			sv.listen(); // Start listening for connections
		} catch (Exception ex) {
			System.out.println("ERROR - Could not listen for clients!");
		}
	}

	public void getObj(Message msg) {
		recivedMSG = msg.getMsg().split("-");
		if (recivedMSG[1].equals("questions")) {
			Statement stmt;
			try {
				stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM questions;");
				while (rs.next()) {
					dataBase.add(new Question(rs.getString(1), rs.getInt(2), rs.getString(3), rs.getString(4),
							rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getInt(9)));
				}
				Message replyMsg = new Message("ok-arraylist", dataBase);
				this.sendToAllClients(replyMsg);
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void setObj(Message msg) {
		recivedMSG = msg.getMsg().split("-");
		String updateString = "UPDATE ? SET ? WHERE ?";
		if (recivedMSG[1].equals("questions") && recivedMSG[2].equals("map")) {
			PreparedStatement stmt;
			try {
				stmt = conn.prepareStatement(updateString);
				stmt.
				while (rs.next()) {
					dataBase.add(new Question(rs.getString(1), rs.getInt(2), rs.getString(3), rs.getString(4),
							rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getInt(9)));
				}
				Message replyMsg = new Message("ok-arraylist",dataBase);
				this.sendToAllClients(replyMsg);
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
	}
}
// End of EchoServer class