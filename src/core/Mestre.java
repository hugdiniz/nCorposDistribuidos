package core;

import java.io.BufferedReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import socket.mestre.SocketMestreEscravo;
import entidades.ArvoreQuadLocal;

public class Mestre 
{
	Map<Long, Boolean>escravosExecucaoFinalizada = new HashMap<Long, Boolean>();
	List<SocketMestreEscravo>controles = new ArrayList<SocketMestreEscravo>();
	
	public static void main(String[] args) 
	{
		
        try
        {
        	new Mestre();
        }
		catch (Exception e)
		{			
			e.printStackTrace();
		}
                
		
	}
	public Mestre() throws Exception
	{		
		System.out.println("iniciando o server ---");        
        ServerSocket serv= null;
        BufferedReader entrada = null;
        
		ArvoreQuadLocal arvoreQuad = Servico.getInstance().recuperarArquivoJSonCorpos(Constantes.enderecoArquivoCorpos);        	
		//Map<Long,ArvoreQuadLocal> mapaArvore = Servico.getInstance().splitArvoreQuad(arvoreQuad, controles);
        serv = new ServerSocket(Constantes.portaMestre); 
        System.out.println("iniciado com sucesso !!!");  
        
        while(true)
        {
            Socket socket = null;                 
            socket = serv.accept();
            
            SocketMestreEscravo socketMestreControle = new SocketMestreEscravo(socket,this);                
            Thread tread = new Thread(socketMestreControle);
            escravosExecucaoFinalizada.put(socketMestreControle.getIdEscravo(), Boolean.FALSE);
            tread.start();
            controles.add(socketMestreControle);           
            
            if(Constantes.quantidadeEscravo.equals(controles.size()))
            {                	 
            	Map<Long,ArvoreQuadLocal> mapaArvore = Servico.getInstance().splitArvoreQuad(arvoreQuad, controles);
            	 
            	 for (SocketMestreEscravo socketMestreEscravo : controles)
            	 {
            		 socketMestreEscravo.enviarArvore(mapaArvore.get(socketMestreEscravo.getIdSocket()));
            	 }
            }	
            
        }  
		
	}
	public void setEscravoFinalizado(Long idEscravo)
	{
		escravosExecucaoFinalizada.put(idEscravo, Boolean.TRUE);
		if (!escravosExecucaoFinalizada.containsKey(Boolean.FALSE)) 
		{
			for (SocketMestreEscravo socketMestreEscravo : controles) 
			{
				socketMestreEscravo.proximaAtualizacao();
				escravosExecucaoFinalizada.put(socketMestreEscravo.getIdEscravo(), Boolean.FALSE);
			}
		}
	}
}
