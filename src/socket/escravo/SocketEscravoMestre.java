package socket.escravo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class SocketEscravoMestre extends Thread
{
	Socket socket;
	PrintStream ps;
	public SocketEscravoMestre(Socket socket) throws IOException
	{
		this.socket = socket;
        ps = new PrintStream(socket.getOutputStream());        
        iniciarConversaMestre();
	}
	
	private void iniciarConversaMestre()
	{
		ps.println("Estou enviando dados para o servidor");       
	}
	
	@Override
	public void run()
	{	
		super.run();
		
		try
		{
			BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));        
			
			while(true)
			{
				System.out.println(entrada.readLine());	
			}						
		}
		catch (Exception e)
		{			
			e.printStackTrace();
		}   
	}
	public void finalizarExecucao()
	{ 
        
	}
}
