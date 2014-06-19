package core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;

import org.json.JSONArray;

import entidades.ArvoreQuadLocal;
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
	
	public static Servico getInstance()
	{
		return new Servico();
	}
}
