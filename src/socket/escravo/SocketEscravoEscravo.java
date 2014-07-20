package socket.escravo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

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
	List<Corpo> corposRecebidos;

	public SocketEscravoEscravo(Socket socket) throws Exception
	{
		corposRecebidos = new ArrayList<Corpo>();
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
	
	public void addCorpoEscravo(Pagina pagina) 
	{
		ps.println(pagina.toJsonObject());
	}
	
	private void executar(String msg) throws Exception
	{
		ComunicacaoEnum comunicacaoEnum = ComunicacaoEnum.valueOf(msg);
		if (comunicacaoEnum.equals(ComunicacaoEnum.SETCORPO))
		{
			String corpoString = entrada.readLine();
			JSONObject jsonObject = new JSONObject(corpoString);
			Corpo corpo = new Corpo(jsonObject);
			corposRecebidos.add(corpo);
			
		}
		else if (comunicacaoEnum.equals(ComunicacaoEnum.GETCORPOMEDIO)) 
		{
			ps.println(Escravo.getArvoreQuadLocal().getCorpoMedio());
		} 
		else if (comunicacaoEnum.equals(ComunicacaoEnum.GETCORPOS)) 
		{
			throw new Exception("socket.escravo.escravo.executar.nao.implementado");
		} 		
		else 
		{
			throw new Exception("socket.escravo.escravo.executar.mensagem.sem.identificacao");
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
		List<Corpo> corposRetirados = corposRecebidos.subList(0, corposRecebidos.size());
		corposRecebidos.clear();
		return corposRetirados;
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
