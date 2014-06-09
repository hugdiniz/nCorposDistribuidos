package core;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import entidades.ArvoreQuad;

public class Escravo 
{
	public static void main(String[] args)	
	{
		ArvoreQuad arvoreQuad;
		try
		{
			arvoreQuad = (ArvoreQuad) Naming.lookup( "rmi://localhost/ArvoreQuad" );
			arvoreQuad.print();     
		}
		catch (MalformedURLException | RemoteException | NotBoundException e)
		{
			e.printStackTrace();
		}  
              
	}
}

