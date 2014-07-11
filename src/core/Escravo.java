package core;

import java.net.Socket;

import javax.management.timer.Timer;

import socket.escravo.SocketEscravoMestre;

public class Escravo 
{
	
	
	public static void main(String[] args)	
	{
		Socket socket = null;
        try
        { 
            socket = new Socket(Constantes.enderecoMestre,Constantes.porta);  
            SocketEscravoMestre socketEscravoMestre = new SocketEscravoMestre(socket);	
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
	}
	
}

