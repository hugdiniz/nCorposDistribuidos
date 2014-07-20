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
	
	public String getIp()
	{
		return socket.getLocalAddress().toString();
	}
	public Long getIdSocket()
	{
		return id;
	}
	
	public SocketMestreEscravo(Socket socket,Mestre mestre) throws IOException
	{
		id = Util.gerarId();
		this.socket = socket;
		ps = new PrintStream(socket.getOutputStream());
		ps.println(id);
		this.mestre = mestre;
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
