package core;

import java.net.Socket;
import java.util.Date;

import entidades.ArvoreQuadLocal;
import socket.escravo.SocketControle;
import socket.escravo.SocketEscravoMestre;

public class Escravo 
{	
	public static void main(String[] args)	
	{		
        try
        { 
            new Escravo();
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
	}
	private static Long id = null;
	private static ArvoreQuadLocal arvoreQuadLocal;
	public Escravo() throws Exception
	{
		
		id = (new Date()).getTime();
		SocketControle.getInstance().start();
		Socket socket = null;
		socket = new Socket(Constantes.enderecoMestre,Constantes.portaMestre);  
        SocketEscravoMestre socketEscravoMestre = new SocketEscravoMestre(socket);
        arvoreQuadLocal = socketEscravoMestre.iniciarConversaMestre();        
        id = socketEscravoMestre.getIdEscravo();
        
        Thread.sleep(Constantes.tempoInicial);
        while (true)
        {
        	
			arvoreQuadLocal.atualizarArvore();
			System.out.println("atualizado");
			socketEscravoMestre.finalizarExecucao();
			System.out.println("execucao finalizada");
			arvoreQuadLocal.add(SocketControle.getInstance().getCorposRecebido());
			System.out.println("corpos recuperados");
		}
	}
	public static Long getId() 
	{		
		return id;
	}
	public static ArvoreQuadLocal getArvoreQuadLocal()
	{	
		return arvoreQuadLocal;
	}
	

	
}

