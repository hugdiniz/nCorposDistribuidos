package socket.escravo;

import java.io.BufferedReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import core.Constantes;
import entidades.Corpo;
import entidades.Pagina;

public class SocketControle extends Thread
{
	private SocketControle()
	{
		/*
		 * Construtor privado pois, a instancia desta classe e unica.
		 */
	}
	private  Map<Long,SocketEscravoEscravo>sockets = new HashMap<Long,SocketEscravoEscravo>();	
	private Collection<Corpo> corpos = new ArrayList<Corpo>();
	private static SocketControle socketControle;
	
	@Override
	public void run()
	{
			 
        ServerSocket serv=null;
        BufferedReader entrada = null;
        try
        {        
            serv = new ServerSocket(Constantes.portaEscravo); 
            System.out.println("iniciado com sucesso !!!");  
            
            while(true)
            {
                Socket socket = null;                 
                socket = serv.accept(); 
               
                SocketEscravoEscravo socketEscravoEscravo = new SocketEscravoEscravo(socket);                
                Thread tread = new Thread(socketEscravoEscravo);
                tread.start();
                sockets.put(socketEscravoEscravo.getIdEscravoRemoto(),socketEscravoEscravo); 
            }  
        }
		catch (Exception e)
		{			
			e.printStackTrace();
		}
                
		super.run();
	}
	
	public Corpo getCorpoMedio(Long id,String endereco) throws Exception
	{
		SocketEscravoEscravo socketEscravoEscravo = sockets.get(id);
		if(socketEscravoEscravo == null)
		{
			Socket socket = new Socket(endereco, 7001);
			socketEscravoEscravo = new SocketEscravoEscravo(socket);
			sockets.put(id, socketEscravoEscravo);
		}
		
		return socketEscravoEscravo.getCorpoMedio();
		
	}
	public Collection getCorposRecebido()
	{
		corpos.clear();
		
		for (SocketEscravoEscravo socketEscravoEscravo : sockets.values())
		{
			corpos.addAll(socketEscravoEscravo.pollCorposRecebidos());
		}
		return corpos;
	}
	
	public static synchronized SocketControle getInstance() 
	{
		if (socketControle == null) {
			socketControle = new SocketControle();
		}
		return socketControle;
	}
}
