package socket.mestre;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import util.Util;
import core.Mestre;
import entidades.Pagina;
import eo.ComunicacaoEnum;

public class SocketMestreEscravo extends Thread
{
	Socket socket;
	PrintStream ps;
	private Long id;
	private Mestre mestre;
	BufferedReader entrada;
	public String getIp()
	{
		String ipPorta = socket.getRemoteSocketAddress().toString().replace("/", "");
		int doisPts = ipPorta.indexOf(":");
		return ipPorta.substring(0, doisPts);
	}
	public Long getIdSocket()
	{
		return id;
	}
	
	public SocketMestreEscravo(Socket socket,Mestre mestre) throws IOException
	{
		
		this.socket = socket;
		ps = new PrintStream(socket.getOutputStream());		
		this.mestre = mestre;
	}
	
	
	@Override
	public void run()
	{		
		super.run();
		try
		{
			entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));  
			while(true)
			{    
				String msg = entrada.readLine();
				executar(msg);
			}
		}
		catch (Exception e)
		{			
			e.printStackTrace();
		}   
	}
	private void executar(String msg) throws IOException 
	{
		if (ComunicacaoEnum.OIMESTRE.toString().equals(msg))
		{
			id = Long.parseLong(entrada.readLine());
			System.out.println(msg);
		}
		else if (ComunicacaoEnum.FIMEXECUCAO.toString().equals(msg))
		{
			mestre.setEscravoFinalizado(id);
		}	
	}
	
	public void enviarArvore(Pagina pagina)
	{
		if (pagina != null)
		{
			ps.println(pagina.toJsonObject());
		}
	}
	public void proximaAtualizacao() throws IOException
	{
		ps.println(ComunicacaoEnum.PROXIMAATUALIZACAO.toString());
		socket.getOutputStream().flush();		
	}
	
	public Long getIdEscravo()
	{
		return id;

	}
}
