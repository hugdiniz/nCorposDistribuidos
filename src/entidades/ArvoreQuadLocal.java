package entidades;

import java.util.ArrayList;
import java.util.Collection;

import org.json.JSONArray;
import org.json.JSONObject;

import core.Constantes;
import util.Calculo;
import eo.DirecaoEnum;

public class ArvoreQuadLocal extends ArvoreQuad
{	
	public ArvoreQuadLocal(Double xMinimo, Double xMaximo, Double yMinimo,Double yMaximo,ArvoreQuadLocal pai)
	{
		this.y = (yMinimo + yMaximo) / 2;
		this.x = (xMaximo + xMinimo) / 2;		
		this.xMaximo = xMaximo;
		this.xMinimo = xMinimo;
		this.yMaximo = yMaximo;
		this.yMinimo = yMinimo;
		this.pai = pai;
	}
	public ArvoreQuadLocal(Double xMinimo, Double xMaximo, Double yMinimo,Double yMaximo)
	{
		this.y = (yMinimo + yMaximo) / 2;
		this.x = (xMinimo + xMaximo) / 2;
		this.xMaximo = xMaximo;
		this.xMinimo = xMinimo;
		this.yMaximo = yMaximo;
		this.yMinimo = yMinimo;
		this.pai = null;
	}
	public ArvoreQuadLocal criarArvoreFilho(Pagina filho) throws Exception
	{
		if (getNorteLeste() == filho)
		{
			return new ArvoreQuadLocal(getXMinimo(),getX(),getY(),getYMaximo(),this);
		}
		else if (getNorteOeste() == filho)
		{
			return new ArvoreQuadLocal(getXMinimo(),getX(),getYMinimo(),getY(),this);
		}
		else if (getSulLeste() == filho)
		{
			return new ArvoreQuadLocal(getX(),getXMaximo(),getY(),getYMaximo(),this);
		}
		else if (getSulOeste() == filho)
		{
			return new ArvoreQuadLocal(getXMinimo(),getX(),getY(),getYMaximo(),this);
		}
		else
		{
			throw new Exception("erro.filho.nao.existe");
		}
	}

	public Pagina getNorteOeste() 
	{
		return norteOeste;
	}

	public void setNorteOeste(Pagina norteOeste) 
	{
		this.norteOeste = norteOeste;
	}

	public Pagina getNorteLeste() 
	{
		return norteLeste;
	}

	public void setNorteLeste(Pagina norteLeste)
	{
		this.norteLeste = norteLeste;
	}

	public Pagina getSulOeste() 
	{
		return sulOeste;
	}	
	
	public void setSulOeste(Pagina sulOeste)
	{
		this.sulOeste = sulOeste;
	}
	
	public Pagina getSulLeste()
	{
		return sulLeste;
	}	
	
	public void setSulLeste(Pagina sulLeste) 
	{
		this.sulLeste = sulLeste;
	}
	
	public void add(Pagina pagina) throws Exception 
	{
		if (pagina != null)
		{
			if(pagina.getY() > getY() && pagina.getX() > getX())
			{			
				if (sulLeste != null)
				{
					if (sulLeste instanceof ArvoreQuadLocal) 
					{
						((ArvoreQuadLocal) sulLeste).add(pagina);				
					}
					else
					{
						Collection<Pagina> objetos = new ArrayList<Pagina>();
						objetos.add(pagina);
						objetos.add(sulLeste);
						if (pagina.getX().equals(sulLeste.getX()) && pagina.getY().equals(sulLeste.getY()))
						{
							throw new Exception("corpos.locais.iguais");
						}
						
						sulLeste = new ArvoreQuadLocal(getX(),getXMaximo(),getY(),getYMaximo(),this);
						((ArvoreQuadLocal) sulLeste).add(objetos);
					}	
				}
				else
				{				
					sulLeste = pagina;
					
					/*
					 * Luke, I am your father !
					 */
					pagina.setPai(this);
				}	
			}
			else if(pagina.getY() > getY() && pagina.getX() <= getX())
			{			
				if (sulOeste != null)
				{
					if (sulOeste instanceof ArvoreQuadLocal) 
					{
						((ArvoreQuadLocal) sulOeste).add(pagina);				
					}
					else
					{
						Collection<Pagina> objetos = new ArrayList<Pagina>();
						objetos.add(pagina);
						objetos.add(sulOeste);
						if (pagina.getX().equals(sulOeste.getX()) && pagina.getY().equals(sulOeste.getY()))
						{
							throw new Exception("corpos.locais.iguais");
						}
						
						sulOeste = new ArvoreQuadLocal(getXMinimo(),getX(),getY(),getYMaximo(),this);
						((ArvoreQuadLocal) sulOeste).add(objetos);
					}	
				}
				else
				{				
					sulOeste = pagina;
					pagina.setPai(this);
				}	
			}
			else if(pagina.getY() <= getY() && pagina.getX() > getX())
			{
				if (norteLeste != null)
				{
					if (norteLeste instanceof ArvoreQuadLocal) 
					{
							((ArvoreQuadLocal) norteLeste).add(pagina);			
					}
					else
					{
						Collection<Pagina> objetos = new ArrayList<Pagina>();
						objetos.add(pagina);
						objetos.add(norteLeste);
						if (pagina.getX().equals(norteLeste.getX()) && pagina.getY().equals(norteLeste.getY()))
						{
							throw new Exception("corpos.locais.iguais");
						}
						
						norteLeste = new ArvoreQuadLocal(getX(),getXMaximo(),getYMinimo(),getY(),this);
						((ArvoreQuadLocal) norteLeste).add(objetos);
					}	
				}
				else
				{				
					norteLeste = pagina;
					pagina.setPai(this);
				}	
			}
			else if(pagina.getY() <= getY() && pagina.getX() <= getX())
			{
				if (norteOeste != null)
				{
					if (norteOeste instanceof ArvoreQuadLocal) 
					{
								((ArvoreQuadLocal) norteOeste).add(pagina);		
					}
					else
					{
						Collection<Pagina> objetos = new ArrayList<Pagina>();
	 					objetos.add(pagina);
						objetos.add(norteOeste);					
						if (pagina.getX().equals(norteOeste.getX()) && pagina.getY().equals(norteOeste.getY()))
						{
							throw new Exception("corpos.locais.iguais");
						}
						
						norteOeste = new ArvoreQuadLocal(getXMinimo(),getX(),getYMinimo(),getY(),this);
						((ArvoreQuadLocal) norteOeste).add(objetos);
					}	
				}
				else
				{				
					norteOeste = pagina;
					pagina.setPai(this);
				}
			}
			else
			{
				System.err.println("Erro");
				System.err.println("getY:" + getY() + " pagina.getY():" + pagina.getY() +  " getX:" +getX() + " pagina.getX():" +pagina.getX());
			}
		}
		
	}
	
	public void add(Collection<? extends Pagina> paginas) throws Exception
	{
		for (Pagina pagina : paginas)
		{
			add(pagina);			
		}
	}
	
	public void print()
	{
		System.out.println(toString());
	}
	
	@Override
	public String toString()
	{
		StringBuffer string = new StringBuffer();
		if (norteOeste != null)
		{
			string.append("NorteOeste:{ "+ norteOeste.toString() + " }");
		}
		else
		{
			string.append("NorteOeste:{  }");
		}
		
		if (norteLeste != null)
		{
			string.append("NorteLeste:{ "+ norteLeste.toString() + " }");
		}
		else
		{
			string.append("NorteLeste:{  }");
		}
		
		if (sulOeste != null)
		{
			string.append("SulOeste:{ "+ sulOeste.toString() + " }");
		}
		else
		{
			string.append("SulOeste:{  }");
		}
	
		if (sulLeste != null)
		{
			string.append("SulLeste:{ "+ sulLeste.toString() + " }");
		}
		else
		{
			string.append("SulLeste:{  }");
		}
		
		return string.toString();
	}
	
	@Override
	public JSONArray toJsonArray()
	{
		JSONArray jsonArray = new JSONArray();
		
		for (Corpo corpo : getCorpos())
		{
			jsonArray.put(corpo.toJsonArray());
		}
		
		return jsonArray;
	}
	
	
	
	
	
	
	
	public Collection<Corpo> getCorpos() 
	{
		Collection<Corpo> corpos = new ArrayList();
		
		if (norteLeste instanceof Corpo) 
		{
			corpos.add((Corpo) norteLeste);
		}
		else if(norteLeste instanceof ArvoreQuadLocal)
		{
			corpos.addAll(((ArvoreQuadLocal) norteLeste).getCorpos());
		}
		
		if (norteOeste instanceof Corpo) 
		{
			corpos.add((Corpo) norteOeste);
		}
		else if(norteOeste instanceof ArvoreQuadLocal)
		{
			corpos.addAll(((ArvoreQuadLocal) norteOeste).getCorpos());
		}
		
		if (sulOeste instanceof Corpo) 
		{
			corpos.add((Corpo) sulOeste);
		}
		else if(sulOeste instanceof ArvoreQuadLocal)
		{
			corpos.addAll(((ArvoreQuadLocal) sulOeste).getCorpos());
		}
		
		if (sulLeste instanceof Corpo) 
		{
			corpos.add((Corpo) sulLeste);
		}
		else if(sulLeste instanceof ArvoreQuadLocal)
		{
			corpos.addAll(((ArvoreQuadLocal) sulLeste).getCorpos());
		}
		
		return corpos;
	}
	
	public Corpo getCorpoMedio()
	{
		Collection<Corpo> corpos = getCorpos();
		Double massa = new Double(0);
		Double xTotal = new Double(0);
		Double yTotal = new Double(0);
		for (Corpo corpo : corpos)
		{
			massa += corpo.getMassa();
			xTotal += corpo.getX();
			yTotal += corpo.getY();
		}
		Double x = xTotal / corpos.size();
		Double y = yTotal / corpos.size();
		Corpo corpo = new Corpo();
		corpo.setX(x);
		corpo.setY(y);
		corpo.setMassa(massa);
		return corpo;
		
	}
	
	@Override
	public JSONObject toJsonObject()
	{
		JSONObject jsonObject = new JSONObject();
		
		jsonObject.put("xMinimo", xMinimo);
		jsonObject.put("xMaximo", xMaximo);
		
		jsonObject.put("yMinimo", yMinimo);
		jsonObject.put("yMaximo", yMaximo);
		
		
		if (norteLeste instanceof Corpo) 
		{
			JSONObject norteLesteObject = new JSONObject();
			norteLesteObject.append("Corpo", norteLeste.toJsonObject());
			jsonObject.append("norteLeste", norteLesteObject);
		}
		else if(norteLeste instanceof ArvoreQuadLocal)
		{			
			JSONObject norteLesteObject = new JSONObject();
			norteLesteObject.append("ArvoreQuadLocal", norteLeste.toJsonObject());
			jsonObject.append("norteLeste", norteLesteObject);
		}
		else if(norteLeste instanceof ArvoreQuadRemota)
		{
			JSONObject norteLesteObject = new JSONObject();
			norteLesteObject.append("ArvoreQuadRemota", norteLeste.toJsonObject());
			jsonObject.append("norteLeste", norteLesteObject);			
		}
		
		if (norteOeste instanceof Corpo) 
		{
			JSONObject norteOesteObject = new JSONObject();
			norteOesteObject.append("Corpo", norteOeste.toJsonObject());
			jsonObject.append("norteOeste", norteOesteObject);
		}
		else if(norteOeste instanceof ArvoreQuadLocal)
		{			
			JSONObject norteOesteObject = new JSONObject();
			norteOesteObject.append("ArvoreQuadLocal", norteOeste.toJsonObject());
			jsonObject.append("norteOeste", norteOesteObject);
		}
		else if(norteOeste instanceof ArvoreQuadRemota)
		{
			JSONObject norteOesteObject = new JSONObject();
			norteOesteObject.append("ArvoreQuadRemota", norteOeste.toJsonObject());
			jsonObject.append("norteOeste", norteOesteObject);
		}
		
		if (sulOeste instanceof Corpo) 
		{
			JSONObject sulOesteObject = new JSONObject();
			sulOesteObject.append("Corpo", sulOeste.toJsonObject());
			jsonObject.append("sulOeste", sulOesteObject);
		}
		else if(sulOeste instanceof ArvoreQuadLocal)
		{			
			JSONObject sulOesteObject = new JSONObject();
			sulOesteObject.append("ArvoreQuadLocal", sulOeste.toJsonObject());
			jsonObject.append("sulOeste", sulOesteObject);
		}
		else if(sulOeste instanceof ArvoreQuadRemota)
		{
			JSONObject sulOesteObject = new JSONObject();
			sulOesteObject.append("ArvoreQuadRemota", sulOeste.toJsonObject());
			jsonObject.append("sulOeste", sulOesteObject);
		}
		
		if (sulLeste instanceof Corpo) 
		{
			JSONObject sulLesteObject = new JSONObject();
			sulLesteObject.append("Corpo", sulLeste.toJsonObject());
			jsonObject.append("sulLeste", sulLesteObject);
		}
		else if(sulLeste instanceof ArvoreQuadLocal)
		{			
			JSONObject sulLesteObject = new JSONObject();
			sulLesteObject.append("ArvoreQuadLocal", sulLeste.toJsonObject());
			jsonObject.append("sulLeste", sulLesteObject);
		}
		else if(sulLeste instanceof ArvoreQuadRemota)
		{
			JSONObject sulLesteObject = new JSONObject();
			sulLesteObject.append("ArvoreQuadRemota", sulLeste.toJsonObject());
			jsonObject.append("sulLeste", sulLesteObject);
		}
		
		return jsonObject;
	}
	
	

	public void atualizaPosicaoCorpo(Corpo corpo) throws Exception
	{

		Calculo.atualizaForca(corpo);
		Calculo.calculoDeslocamento(corpo, Constantes.tempo);
		
		// Quando é SUL significa que apenas irá mexer no Y, ou seja na VERTICAL
		if (corpo.getSentido() == DirecaoEnum.SUL) {

			// Significa que o corpo esta abaixo do centro de Y
			if (corpo.getY() > getY()) 
			{
				corpo.setY(corpo.getY() + corpo.getDeslocamento());
			}
			// Significa que o corpo esta acima do centro de Y
			if (corpo.getY() < getY()) 
			{				
				corpo.setY(corpo.getY() - corpo.getDeslocamento());
			}
		}

		// Quando é Norte significa que apenas irá mexer no Y, ou seja na
		// VERTICAL
		if (corpo.getSentido() == DirecaoEnum.NORTE) {

			// Significa que o corpo esta abaixo do centro de Y
			if (corpo.getY() > getY()) {

				corpo.setY(corpo.getY() - corpo.getDeslocamento());
			}
			// Significa que o corpo esta acima do centro de Y
			if (corpo.getY() < getY()) {

				corpo.setY(corpo.getY() + corpo.getDeslocamento());
			}

		}

		// Quando é LESTE significa que apenas irá mexer no X, ou seja na
		// HORIZONTAL
		if (corpo.getSentido() == DirecaoEnum.LESTE) {

			// Significa que o corpo esta abaixo do centro de X
			if (corpo.getX() < getX()) {

				corpo.setX(corpo.getX() - corpo.getDeslocamento());
			}

			// Significa que o corpo esta acima do centro de X
			if (corpo.getX() > getX()) {
				corpo.setX(corpo.getX() + corpo.getDeslocamento());
			}
		}

		// Quando é OESTE significa que apenas irá mexer no X, ou seja na
		// HORIZONTAL
		if (corpo.getSentido() == DirecaoEnum.OESTE) {
			// Significa que o corpo esta abaixo do centro de X
			if (corpo.getX() < getX()) {
				corpo.setX(corpo.getX() + corpo.getDeslocamento());
			}

			// Significa que o corpo esta acima do centro de X
			if (corpo.getX() > getX()) {
				corpo.setX(corpo.getX() - corpo.getDeslocamento());
			}
		}
		
		if (!verficarPosicao(corpo))
		{
			ArvoreQuadLocal raiz = corpo.getPai();
			
			while (raiz.getPai() != null)
			{
				raiz = raiz.getPai();
			}
			
			raiz.add(corpo);
		}

	}
	
	public void atualizarArvore() throws Exception 
	{
		if (norteOeste instanceof Corpo)
		{
			atualizaPosicaoCorpo((Corpo) norteOeste);
		}
		else if(norteOeste instanceof ArvoreQuadLocal)
		{
			((ArvoreQuadLocal) norteOeste).atualizarArvore();
		}
		
		if (norteLeste instanceof Corpo)
		{
			atualizaPosicaoCorpo((Corpo) norteLeste);
		}
		else if(norteLeste instanceof ArvoreQuadLocal)
		{
			((ArvoreQuadLocal) norteLeste).atualizarArvore();
		}
		
		if (sulOeste instanceof Corpo)
		{
			atualizaPosicaoCorpo((Corpo) sulOeste);
		}
		else if(sulOeste instanceof ArvoreQuadLocal)
		{
			((ArvoreQuadLocal) sulOeste).atualizarArvore();
		}
		
		if (sulLeste instanceof Corpo)
		{
			atualizaPosicaoCorpo((Corpo) sulLeste);
		}
		else if(sulLeste instanceof ArvoreQuadLocal)
		{
			((ArvoreQuadLocal) sulLeste).atualizarArvore();
		}
		
		

	}
	
	
		
	 //Verificar se o corpo saiu do Nó o qual pertence, para deslocar o corpo para outro nó,
	 //ou para outro processo
	 public Boolean verficarPosicao(Corpo corpo)
	 {
		 if (corpo.getX() > getXMinimo() &&  corpo.getX() < getXMaximo() && corpo.getY() > getYMinimo() &&  corpo.getY() < getYMaximo())
		 {
			return true;
		 }
		 
		 return false;
	 }
}
