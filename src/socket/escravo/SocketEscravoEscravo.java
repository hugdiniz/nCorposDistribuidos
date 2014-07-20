package socket.escravo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.json.JSONObject;

import core.Escravo;
import entidades.Corpo;
import entidades.Pagina;
import eo.ComunicacaoEnum;

public class SocketEscravoEscravo extends Thread
{
	Socket socket;
	PrintStream ps;
	BufferedReader entrada;
	Long idEscravo;
	Queue<Corpo> corposRecebidos; 

	public SocketEscravoEscravo(Socket socket) throws Exception
	{
		corposRecebidos = new LinkedList<Corpo>();
		this.socket = socket;
        ps = new PrintStream(socket.getOutputStream()); 
        entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ps.println(Escravo.getId());
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
        
	}
	
	@Override
	public void run()
	{	
		super.run();
		
		try
		{
			
			
			String msg = new String();
			while(msg != null)
			{
				msg = entrada.readLine();
				try 
				{
					executar(msg);
				}
				catch (Exception e) 
				{
					e.printStackTrace();
					System.err.println("mensagem == " + msg);
				}
			}	
		}
		catch (Exception e)
		{			
			e.printStackTrace();
		}   
	}
	
	public void addCorpoEscravo(Pagina pagina) throws Exception 
	{
		ps.println(pagina.toJsonObject());
		String msg = entrada.readLine();
		if (msg != null && msg.equals(ComunicacaoEnum.REENVIAR))
		{
			addCorpoEscravo(pagina);
		}
	}
	
	private void executar(String msg) throws Exception
	{
		if (msg != null) 
		{
			try 
			{
				ComunicacaoEnum comunicacaoEnum = ComunicacaoEnum.valueOf(msg);
				if (comunicacaoEnum.equals(ComunicacaoEnum.SETCORPO))
				{
					String corpoString = entrada.readLine();
					JSONObject jsonObject = new JSONObject(corpoString);
					Corpo corpo = new Corpo(jsonObject);
					corposRecebidos.add(corpo);					
					ps.println(ComunicacaoEnum.OK.toString());
					
				}
				else if (comunicacaoEnum.equals(ComunicacaoEnum.GETCORPOMEDIO)) 
				{
					ps.println(Escravo.getArvoreQuadLocal().getCorpoMedio().toJsonObject());
				} 
				else if (comunicacaoEnum.equals(ComunicacaoEnum.GETCORPOS)) 
				{
					throw new Exception("socket.escravo.escravo.executar.nao.implementado");
				}
				else
				{
					throw new Exception("socket.escravo.escravo.executar.mensagem.sem.identificacao");
				}	
			} catch (Exception e) 
			{
				throw new Exception("socket.escravo.escravo.executar.mensagem.sem.identificacao");
			}
		}
		else 
		{
			ps.println(ComunicacaoEnum.REENVIAR.toString());
		}

	}
	public Long getIdEscravoRemoto() throws IOException
	{
		while(idEscravo == null)
		{
			
		}
		
		return idEscravo;
	}
	public List pollCorposRecebidos() 
	{
		List<Corpo> corposRetirados = new ArrayList();
		while (!corposRecebidos.isEmpty())
		{
			corposRetirados.add(corposRecebidos.poll());
		}
		return corposRetirados;
	}
	
	public Corpo getCorpoMedio() throws Exception
	{
		ps.println(ComunicacaoEnum.GETCORPOMEDIO);
		String msg = entrada.readLine();
		if (msg.equals(ComunicacaoEnum.REENVIAR.toString()))
		{
			return getCorpoMedio();
		}
		else
		{
			JSONObject jsonObject = new JSONObject(msg);
			Corpo corpo = new Corpo(jsonObject);
			return corpo;
		}		
	}
	public void finalizarExecucao()
	{ 
        
	}
}
