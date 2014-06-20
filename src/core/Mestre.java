package core;

import java.io.BufferedReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import socket.mestre.SocketMestreEscravo;
import entidades.ArvoreQuadLocal;

public class Mestre 
{
	public static void main(String[] args) 
	{
		Collection<SocketMestreEscravo>controles = new HashSet<SocketMestreEscravo>();
		
		System.out.println("iniciando o server ---");        
        ServerSocket serv=null;
        BufferedReader entrada = null;
        try
        {
        	ArvoreQuadLocal arvoreQuad = Servico.getInstance().recuperarArquivoJSonCorpos("src/arquivos/teste.json");
            serv = new ServerSocket(7000); 
            System.out.println("iniciado com sucesso !!!");  
            
            while(true)
            {
                Socket socket = null;                 
                socket = serv.accept(); 
               
                
                SocketMestreEscravo socketMestreControle = new SocketMestreEscravo(socket);                
                Thread tread = new Thread(socketMestreControle);
                tread.start();
                controles.add(socketMestreControle);
                /*
                 * TODO: Melhorar a heuristica de enviar partes da arvore
                 */
                if(controles.size() == 4)
                {
                	 Iterator<SocketMestreEscravo> iterator = controles.iterator();
                	 
                	 iterator.next().enviarArvore(arvoreQuad.getNorteLeste());
                	 iterator.next().enviarArvore(arvoreQuad.getNorteOeste());
                	 iterator.next().enviarArvore(arvoreQuad.getSulLeste());
                	 iterator.next().enviarArvore(arvoreQuad.getSulOeste());
                	 
                }	
                
            }  
        }
		catch (Exception e)
		{			
			e.printStackTrace();
		}
                
		
	}
}
