package util;

import java.util.Collection;

import entidades.ArvoreQuad;
import entidades.ArvoreQuadLocal;
import entidades.Corpo;
import eo.DirecaoEnum;

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
	public static void CalculoColisao(Corpo corpo1, Corpo corpo2) throws Exception
	{
		if (corpo1.getX().equals(corpo2.getX()) && corpo1.getY().equals(corpo2.getY()))
		{
			if ((corpo1.getDeslocamento() == null || corpo1.getDeslocamento() == 0.00) && (corpo2.getDeslocamento() == null || corpo2.getDeslocamento() == 0.00))
			{
				throw new Exception("corpos.locais.iguais");
			}
			else
			{
				Double deslocamentoCorpo1 = corpo2.getDeslocamento() / 2;
				Double deslocamentoCorpo2 = corpo1.getDeslocamento() / 2;
				
				if (corpo1.getSentido().equals(DirecaoEnum.LESTE))
				{
					corpo1.setSentido(DirecaoEnum.OESTE);
					corpo1.setY(corpo1.getY() - deslocamentoCorpo1);
				}
				if (corpo1.getSentido().equals(DirecaoEnum.OESTE))
				{
					corpo1.setSentido(DirecaoEnum.LESTE);
					corpo1.setY(corpo1.getY() + deslocamentoCorpo1);
				}
				if (corpo1.getSentido().equals(DirecaoEnum.NORTE))
				{
					corpo1.setSentido(DirecaoEnum.SUL);
					corpo1.setX(corpo1.getX() + deslocamentoCorpo1);
				}
				if (corpo1.getSentido().equals(DirecaoEnum.SUL))
				{
					corpo1.setSentido(DirecaoEnum.NORTE);
					corpo1.setX(corpo1.getX() - deslocamentoCorpo1);
				}
				
				
				
				if (corpo2.getSentido().equals(DirecaoEnum.LESTE))
				{
					corpo2.setSentido(DirecaoEnum.OESTE);
					corpo2.setY(corpo2.getY() - deslocamentoCorpo2);
				}
				if (corpo2.getSentido().equals(DirecaoEnum.OESTE))
				{
					corpo2.setSentido(DirecaoEnum.LESTE);
					corpo2.setY(corpo2.getY() + deslocamentoCorpo2);
				}
				if (corpo2.getSentido().equals(DirecaoEnum.NORTE))
				{
					corpo2.setSentido(DirecaoEnum.SUL);
					corpo2.setX(corpo2.getX() + deslocamentoCorpo2);
				}
				if (corpo2.getSentido().equals(DirecaoEnum.SUL))
				{
					corpo2.setSentido(DirecaoEnum.NORTE);
					corpo2.setX(corpo2.getX() - deslocamentoCorpo2);
				}
			}	
			
		}
	}
	
	private static Double calculoForca(Corpo corpo , Corpo corpoVizinho) 
	{
		return corpo.getMassa() /(calculaDistancia(corpo, corpoVizinho)) ;
	}
	private static Double calculaDistancia(Corpo corpo , Corpo corpoVizinho) 
	{
		return Math.sqrt(Math.pow((corpoVizinho.getX() - corpo.getX()),2)) + Math.sqrt(Math.pow((corpoVizinho.getY() - corpo.getY()),2)) ;
	}
	
	public static void calculoDeslocamento(Corpo corpo,Integer tempo)
	{		
		Double aceleracao;
		
		aceleracao = corpo.getForca()/corpo.getMassa();
		
		Double deslocamento =  ((corpo.getVelocidade() * tempo) + ((aceleracao * (tempo*tempo))/2));
		
		corpo.setDeslocamento(deslocamento);				
	}
}
