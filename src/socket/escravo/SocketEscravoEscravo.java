package socket.escravo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import org.json.JSONObject;

import entidades.Corpo;
import eo.ComunicacaoEnum;

public class SocketEscravoEscravo extends Thread
{
	Socket socket;
	PrintStream ps;
	BufferedReader entrada;
	Long idEscravo;
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
			try
			{
				idEscravo = Long.parseLong(msg);
			}
			catch (Exception e)
			{
				throw new Exception("erro.recuperar.id.escravo");
			}
			
			
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
	public Long getIdEscravoRemoto() throws IOException
	{
		while(idEscravo == null)
		{
			
		}
		
		return idEscravo;
	}
	
	public Corpo getCorpoMedio() throws Exception
	{
		ps.println(ComunicacaoEnum.GETCORPOMEDIO);
		String msg = entrada.readLine();
		JSONObject jsonObject = new JSONObject(msg);
		Corpo corpo = new Corpo(jsonObject);
		return corpo;
	}
	public void finalizarExecucao()
	{ 
        
	}
}
