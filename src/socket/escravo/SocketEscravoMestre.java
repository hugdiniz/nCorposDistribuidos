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
	Long id;
	
	public SocketEscravoMestre(Socket socket) throws Exception
	{
		this.socket = socket;
        ps = new PrintStream(socket.getOutputStream()); 
        entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));        
       
	}
	
	public ArvoreQuadLocal iniciarConversaMestre() throws Exception 
	{
		ps.println(ComunicacaoEnum.OIMESTRE.toString());		
		try 
		{
			id = Long.parseLong(entrada.readLine());
		} catch (Exception e)
		{
			throw new Exception("socketEscravoMestre.iniciarConversaMestre.erro.id.nao.reconhecido");
		}
		
		/*
		 * Espera os corpos para montar a arvore.
		 */		
		String corposString = entrada.readLine();
		ArvoreQuadLocal arvoreQuad = ArvoreQuad.montarArvore(new JSONObject(corposString));	
		return arvoreQuad;   
	}
	
	public Long getIdEscravo() 
	{
		return id;
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
				msg = entrada.readLine();
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	public void finalizarExecucao() throws Exception
	{ 		
		ps.println(ComunicacaoEnum.FIMEXECUCAO.toString()); 
		
		/*
		 * Espera os corpos para montar a arvore.
		 */		
		String msg = entrada.readLine();
		if (ComunicacaoEnum.PROXIMAATUALIZACAO.toString().equals(msg))
		{
			return;
		}
		else
		{
			throw new Exception("socket.escravo.escravo.finalizar.execucao.msg.nao.entendida");
		}	
		
	}
}
