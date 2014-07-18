package core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;

import socket.mestre.SocketMestreEscravo;
import entidades.ArvoreQuadLocal;
import entidades.ArvoreQuadRemota;
import entidades.Corpo;
import entidades.Pagina;
import eo.DirecaoEnum;

public class Servico 
{
	public ArvoreQuadLocal recuperarArquivoJSonCorpos(String endereco) throws Exception 
	{		
		FileReader fileReader = new FileReader(new File(endereco));
		BufferedReader bufferedReader = new BufferedReader( fileReader );	
		
		Collection<Pagina> corpos = new ArrayList<Pagina>();
		
		while(bufferedReader.ready())
		{
			Corpo corpo = new Corpo();
			String linha = bufferedReader.readLine();
			JSONArray jsonArray = new JSONArray(linha);
			
			/*
			 * Json e composto por: X, Y, massa, forca, velocidade, direcao
			 */
			corpo.setX(Double.parseDouble((jsonArray.get(0).toString())));
			corpo.setY(Double.parseDouble(jsonArray.get(1).toString()));
			corpo.setMassa(Double.parseDouble(jsonArray.get(2).toString()));
			corpo.setForca(Double.parseDouble(jsonArray.get(3).toString()));
			corpo.setVelocidade(Double.parseDouble(jsonArray.get(4).toString()));
			corpo.setSentido(DirecaoEnum.valueOf(jsonArray.getString(5)));
			corpos.add(corpo);
		}
		/*
		 * TODO: Criar uma heuristica para gerar dinamicamente o limite do campo da arvore quad
		 */
		ArvoreQuadLocal arvoreQuad = new ArvoreQuadLocal(0.0, 1000.0, 0.0, 1000.0,null);		
		arvoreQuad.add(corpos);
		arvoreQuad.print();
		return arvoreQuad;		
	}
	
	public Map<Long,ArvoreQuadLocal> splitArvoreQuad(ArvoreQuadLocal arvoreQuadLocal,List<SocketMestreEscravo> socketMestreEscravos) throws Exception
	{
			
		Map<Long,ArvoreQuadLocal> mapa = new HashMap<Long,ArvoreQuadLocal>();
		Integer quantidade = socketMestreEscravos.size();
		Integer profundidade = quantidade / 4;
		Integer sobra = quantidade%4;
		
		//gerarArvoreVazia(quantidade, arvoreQuadLocal.getXMinimo(), arvoreQuadLocal.getXMaximo(), arvoreQuadLocal.getYMinimo(), arvoreQuadLocal.getXMaximo());
		
		for (SocketMestreEscravo socketMestreEscravo : socketMestreEscravos)
		{
			mapa.put(socketMestreEscravo.getIdSocket(), gerarArvoreVazia(profundidade, arvoreQuadLocal.getXMinimo(), arvoreQuadLocal.getXMaximo(), arvoreQuadLocal.getYMinimo(), arvoreQuadLocal.getXMaximo()));
		}
		
		List<Pagina> partes = recuperarPartes(arvoreQuadLocal,profundidade);
		Integer prox = 0;		
		Iterator<Pagina> iteratorPagina = partes.iterator();
		while (iteratorPagina.hasNext()) 
		{
			SocketMestreEscravo socketMestreEscravo = socketMestreEscravos.get(prox);					
			
			ArvoreQuadLocal arvoreEscravo = mapa.get(socketMestreEscravo.getIdSocket());
			Pagina parte = iteratorPagina.next();			
			
			if (parte instanceof ArvoreQuadLocal)
			{
				arvoreEscravo.add(((ArvoreQuadLocal) parte).getCorpos());
			}
			else
			{
				arvoreEscravo.add(parte);
			}
			
			
			/*
			 * Criando arvores remotas para os outros escravos a partir da arvore que o escravo em questao ficou responsavel 			
			 */
			for (ArvoreQuadLocal arvore : mapa.values())
			{
				if (arvore != arvoreEscravo)
				{
					ArvoreQuadRemota arvoreQuadRemota = new ArvoreQuadRemota(socketMestreEscravo.getIdSocket(), socketMestreEscravo.getIp(), parte.getXMaximo(), parte.getXMinimo(), parte.getYMaximo(), parte.getYMinimo(), arvore);
					arvore.add(arvoreQuadRemota);
				}
			}
			
			prox++;	
			
			/*
				caso ele ultrapasse o numero de sockets
			*/						
			if (socketMestreEscravos.size() <= prox)
			 {
				prox = 0;
			}	
		
		}
		
		
		
		return mapa;		
	}
	
	
	private ArvoreQuadLocal gerarArvoreVazia(Integer profundidade,Double xMinimo,Double xMaximo,Double yMinimo,Double yMaximo)
	{
		ArvoreQuadLocal arvoreQuad = new ArvoreQuadLocal(xMinimo, xMaximo, yMinimo, yMaximo);
		Double x = (xMaximo - xMinimo)/2;
		Double y = (yMaximo - yMinimo)/2;
		if (profundidade > 1)
		{
			arvoreQuad.setNorteOeste(gerarArvoreVazia(profundidade - 1, xMinimo, x, yMinimo,y,arvoreQuad));
			arvoreQuad.setNorteLeste(gerarArvoreVazia(profundidade - 1, x, xMaximo, yMinimo, y,arvoreQuad));
			arvoreQuad.setSulOeste(gerarArvoreVazia(profundidade - 1, xMinimo, x, y,yMaximo,arvoreQuad));
			arvoreQuad.setSulLeste(gerarArvoreVazia(profundidade - 1, x, xMaximo, y, yMaximo,arvoreQuad));
		}		
	
		return arvoreQuad;
	}
	
	private ArvoreQuadLocal gerarArvoreVazia(Integer profundidade,Double xMinimo,Double xMaximo,Double yMinimo,Double yMaximo,ArvoreQuadLocal pai)
	{
		ArvoreQuadLocal arvoreQuad = gerarArvoreVazia(profundidade, xMinimo, xMaximo, yMinimo, yMaximo);
		arvoreQuad.setPai(pai);
		return arvoreQuad;
	}
	
	private List<Pagina> recuperarPartes(Pagina pagina,Integer profundidade) throws Exception
	{
		List<Pagina> partes = new ArrayList<Pagina>();
		if(profundidade > 1)
		{
			if (pagina != null)
			{
				if(pagina instanceof Corpo)
				{
					ArvoreQuadLocal arvoreQuadLocal = pagina.getPai().criarArvoreFilho(pagina);
					arvoreQuadLocal.add(pagina);
					pagina = arvoreQuadLocal;
				}
			}
			else
			{
				throw new Exception("erro.recuperar.arvore.nula");
			}	
			
			partes.addAll(recuperarPartes(((ArvoreQuadLocal) pagina).getNorteLeste(), profundidade -1));
			partes.addAll(recuperarPartes(((ArvoreQuadLocal) pagina).getNorteOeste(), profundidade -1));
			partes.addAll(recuperarPartes(((ArvoreQuadLocal) pagina).getSulLeste(), profundidade -1));
			partes.addAll(recuperarPartes(((ArvoreQuadLocal) pagina).getSulOeste(), profundidade -1));
			
		}
		else
		{
			if(pagina instanceof Corpo)
			{
				ArvoreQuadLocal arvoreQuadLocal = pagina.getPai().criarArvoreFilho(pagina);
				arvoreQuadLocal.add(pagina);
				pagina = arvoreQuadLocal;
			}	
			partes.add(((ArvoreQuadLocal) pagina).getNorteLeste());
			partes.add(((ArvoreQuadLocal) pagina).getNorteOeste());
			partes.add(((ArvoreQuadLocal) pagina).getSulLeste());
			partes.add(((ArvoreQuadLocal) pagina).getSulOeste());
		}	
		return partes;
	}
	
	public static Servico getInstance()
	{
		return new Servico();
	}
}
