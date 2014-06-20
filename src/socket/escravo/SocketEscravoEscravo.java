package socket.escravo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class SocketEscravoEscravo extends Thread
{
	Socket socket;
	PrintStream ps;
	BufferedReader entrada;
	String idEscravoRemoto;
	public SocketEscravoEscravo(Socket socket) throws IOException
	{
		this.socket = socket;
        ps = new PrintStream(socket.getOutputStream()); 
        entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        
	}
	
	@Override
	public void run()
	{	
		super.run();
		
		try
		{
			String msg = entrada.readLine();
			
			/*
			 * Primeira msg sempre sera o seu id
			 */
			idEscravoRemoto = msg;
			
			while(msg != null)
			{
				System.out.println(msg);
				
				msg = entrada.readLine();
			}	
		}
		catch (Exception e)
		{			
			e.printStackTrace();
		}   
	}
	public String getIdEscravoRemoto() throws IOException
	{
		while(idEscravoRemoto == null)
		{
			
		}
		
		return idEscravoRemoto;
	}
	public void finalizarExecucao()
	{ 
        
	}
}
