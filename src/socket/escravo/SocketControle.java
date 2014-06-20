package socket.escravo;

import java.io.BufferedReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class SocketControle extends Thread
{
	Map<String,SocketEscravoEscravo>sockets = new HashMap<String,SocketEscravoEscravo>();	
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
}
