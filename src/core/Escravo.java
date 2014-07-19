package core;

import java.net.Socket;

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
	ArvoreQuadLocal arvoreQuadLocal;
	public Escravo() throws Exception
	{
		Socket socket = null;
		socket = new Socket(Constantes.enderecoMestre,Constantes.portaMestre);  
        SocketEscravoMestre socketEscravoMestre = new SocketEscravoMestre(socket);
        arvoreQuadLocal = socketEscravoMestre.iniciarConversaMestre();        
       
        SocketControle.getInstance().start();
        
        while (true)
        {
			arvoreQuadLocal.atualizarArvore();
			socketEscravoMestre.finalizarExecucao();
			arvoreQuadLocal.add(SocketControle.getInstance().getCorposRecebido());			
		}
	}
	

	
}

