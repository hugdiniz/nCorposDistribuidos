package core;

import java.net.Socket;
import socket.escravo.SocketEscravoControle;

public class Escravo 
{
	public static void main(String[] args)	
	{
		Socket s = null;
        try
        {                
            //Cria o socket com o recurso desejado na porta especificada  
            s = new Socket("127.0.0.1",7000);  
            SocketEscravoControle socketEscravoControle = new SocketEscravoControle(s);	            
            Thread thread = new Thread(socketEscravoControle);
            thread.start();
           
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
	}
}

