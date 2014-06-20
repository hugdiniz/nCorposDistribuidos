package core;

import java.net.Socket;

import javax.management.timer.Timer;

import socket.escravo.SocketEscravoMestre;

public class Escravo 
{
	private static String id;
	public static void main(String[] args)	
	{
		/*
		 * Criando um id para o escravo.
		 */
		id = String.valueOf(Timer.ONE_HOUR);
		
		Socket socket = null;
        try
        { 
            socket = new Socket("127.0.0.1",7000);  
            SocketEscravoMestre socketEscravoControle = new SocketEscravoMestre(socket);	            
            Thread thread = new Thread(socketEscravoControle);
            thread.start();
           
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
	}
	public static String getNome()
	{
		return id;
	}
}

