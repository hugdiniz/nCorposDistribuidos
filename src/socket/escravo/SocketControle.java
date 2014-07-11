package socket.escravo;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import entidades.Corpo;

public class SocketControle extends Thread
{
	static Map<Long,SocketEscravoEscravo>sockets = new HashMap<Long,SocketEscravoEscravo>();	
	@Override
	public void run()
	{
			 
        ServerSocket serv=null;
        BufferedReader entrada = null;
        try
        {        
            serv = new ServerSocket(7001); 
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
	
	public static Corpo getCorpoMedio(Long id,String endereco) throws Exception
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
}
