package su.eqx.iggdrasil.halberd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MPDHelper {
	
	private Socket mMPDConnection;
	private PrintWriter outStream;
	private BufferedReader inStream;
	private static final String SUC_CODE = "OK";
	private static final int PLAY = 0;
	private static final int PAUSE = 1;
	private String versionString;
	
	
	public MPDHelper(String hostname, int port) throws IOException  {
		mMPDConnection = new Socket(hostname, port);
		outStream = new PrintWriter(mMPDConnection.getOutputStream(), true);
		inStream = new BufferedReader(new InputStreamReader(
				mMPDConnection.getInputStream()));
		String hello = inStream.readLine();
		if (!hello.startsWith(SUC_CODE)) {
			throw new IOException();
		}
		versionString = hello.substring(3);
	}
	
	private void togglePlaying(int state) throws IOException {
		outStream.println("pause " + state);
		String response = inStream.readLine();
		if (!response.equals(SUC_CODE)) {
			throw new IOException();
		}
	}
	
	public void play() throws IOException {
		togglePlaying(PLAY);
	}
	
	public void pause() throws IOException {
		togglePlaying(PAUSE);
	}
	
	public void playNextSong()  {
		sendCommand("next");
	}
	
	public void playPreviuosSong()  {
		sendCommand("previous");
	}
	
	public void seek(int id, int pos) {
		sendCommand("seek " + id + " " + pos);
	}
	
	public String serverInfo() {
		return versionString;
	}
	
	public void close() {
		outStream.println("close");
		outStream.close();
		try {
			inStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			mMPDConnection.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void sendCommand(String message)  {
		outStream.println(message);
		try {
			String response = inStream.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
