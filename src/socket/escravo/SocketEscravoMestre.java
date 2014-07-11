package socket.escravo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import entidades.ArvoreQuad;
import entidades.ArvoreQuadLocal;
import entidades.Corpo;
import eo.ComunicacaoEnum;
import eo.DirecaoEnum;

public class SocketEscravoMestre extends Thread
{
	Socket socket;
	PrintStream ps;
	BufferedReader entrada;
	
	public SocketEscravoMestre(Socket socket) throws Exception
	{
		this.socket = socket;
        ps = new PrintStream(socket.getOutputStream()); 
        entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        iniciarConversaMestre();
        start();
	}
	
	public ArvoreQuad iniciarConversaMestre() throws Exception 
	{
		ps.println(ComunicacaoEnum.OIMESTRE.toString()); 
		
		/*
		 * Espera os corpos para montar a arvore.
		 */
		
		String corposString = entrada.readLine();
		ArvoreQuad arvoreQuad = ArvoreQuad.montarArvore(new JSONObject(corposString));
		System.out.println(arvoreQuad);
		
		
				
		return null;   
	}
	
	@Override
	public void run()
	{	
		super.run();
		String msg;
		try
		{
			msg = entrada.readLine();
			while(msg != null)
			{
				System.out.println(msg);
				
				msg = entrada.readLine();
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	public void finalizarExecucao()
	{ 
        
	}
}
