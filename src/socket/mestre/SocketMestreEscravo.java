package socket.mestre;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import util.GeradorJSONCorpos;
import util.Util;

import entidades.Pagina;

public class SocketMestreEscravo extends Thread
{
	Socket socket;
	PrintStream ps;
	private Long id;
	
	public String getIp()
	{
		return socket.getLocalAddress().toString();
	}
	public Long getIdSocket()
	{
		return id;
	}
	
	public SocketMestreEscravo(Socket socket) throws IOException
	{
		id = Util.gerarId();
		this.socket = socket;
		ps = new PrintStream(socket.getOutputStream());
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
	
	public void enviarArvore(Pagina pagina)
	{
		if (pagina != null)
		{
			ps.println(pagina.toJsonObject());
		}
	}
}
