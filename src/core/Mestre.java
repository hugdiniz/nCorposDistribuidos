package core;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import socket.mestre.SocketMestreEscravo;
import entidades.ArvoreQuadLocal;
import entidades.Corpo;

public class Mestre 
{
	Map<Long, Boolean>escravosExecucaoFinalizada = new HashMap<Long, Boolean>();
	List<SocketMestreEscravo>controles = new ArrayList<SocketMestreEscravo>();
	Date dataUltimaAtualizacao;
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
            		 Map<String, Corpo> corpoMap = new HashMap<>();
            		 for (Corpo corpo : mapaArvore.get(socketMestreEscravo.getIdSocket()).getCorpos()) {
						corpoMap.put(corpo.toString(), corpo);
					}
            		 socketMestreEscravo.enviarArvore(mapaArvore.get(socketMestreEscravo.getIdSocket()));
            	 }
            	 dataUltimaAtualizacao = new Date();
            }	
            
        }  
		
	}
	public void setEscravoFinalizado(Long idEscravo) throws IOException
	{
		escravosExecucaoFinalizada.put(idEscravo, Boolean.TRUE);
		if (!escravosExecucaoFinalizada.containsKey(Boolean.FALSE)) 
		{
			gerarTempo();
			for (SocketMestreEscravo socketMestreEscravo : controles) 
			{				
				socketMestreEscravo.proximaAtualizacao();
				escravosExecucaoFinalizada.put(socketMestreEscravo.getIdEscravo(), Boolean.FALSE);
			}
		}
	}
	
	private void gerarTempo()
	{
		Date atual = new Date();
		Long tempo = atual.getTime() - dataUltimaAtualizacao.getTime();
		System.out.println("Ciclo concluido em: " + tempo);
		dataUltimaAtualizacao = atual;
	}
}
