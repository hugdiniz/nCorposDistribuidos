package util;

import java.util.Collection;

import entidades.ArvoreQuad;
import entidades.ArvoreQuadLocal;
import entidades.Corpo;

public class Calculo 
{
	public static void atualizaForca(Corpo corpo) throws Exception
	{
		
		Collection<Corpo> corpos = corpo.getCorposArvoreCompleta();
				
		
		Double forca = corpo.getForca();
		
		for (Corpo corpoVizinho : corpos)
		{
			 forca += calculoForca(corpo,corpoVizinho);
			
		}
		
		corpo.setForca(forca);		
	}
	
	private static Double calculoForca(Corpo corpo , Corpo corpoVizinho) 
	{
		return corpo.getMassa() /(calculaDistancia(corpo, corpoVizinho)) ;
	}
	private static Double calculaDistancia(Corpo corpo , Corpo corpoVizinho) 
	{
		return Math.sqrt(Math.pow((corpoVizinho.getX() - corpo.getX()),2)) + Math.sqrt(Math.pow((corpoVizinho.getY() - corpo.getY()),2)) ;
	}
	
public static void calculoDeslocamento(Corpo corpo,Integer tempo){
		
		Double aceleracao;
		
		aceleracao = corpo.getForca()/corpo.getMassa();
		
		Double deslocamento =  ((corpo.getVelocidade() * tempo) + ((aceleracao * (tempo*tempo))/2));
		
		corpo.setDeslocamento(deslocamento);				
	}
}
