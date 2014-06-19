package entidades;

import java.util.ArrayList;
import java.util.Collection;
import org.json.JSONArray;

public class ArvoreQuadLocal extends ArvoreQuad
{	
	public ArvoreQuadLocal(Double xMinimo, Double xMaximo, Double yMinimo,Double yMaximo,Pagina pai)
	{
		this.y = yMinimo + yMaximo / 2;
		this.x = xMinimo + xMaximo / 2;
		this.xMaximo = xMaximo;
		this.xMinimo = xMinimo;
		this.yMaximo = yMaximo;
		this.yMinimo = yMinimo;
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
	
	public void add(Pagina pagina) 
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
					
					sulLeste = new ArvoreQuadLocal(getX(),getXMaximo(),getY(),getYMaximo(),this);
					((ArvoreQuadLocal) sulLeste).add(objetos);
				}	
			}
			else
			{
				sulLeste = pagina;
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
					
					sulOeste = new ArvoreQuadLocal(getXMinimo(),getX(),getY(),getYMaximo(),this);
					((ArvoreQuadLocal) sulOeste).add(objetos);
				}	
			}
			else
			{
				sulOeste = pagina;
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
					
					norteLeste = new ArvoreQuadLocal(getXMinimo(),getX(),getY(),getYMaximo(),this);
					((ArvoreQuadLocal) norteLeste).add(objetos);
				}	
			}
			else
			{
				norteLeste = pagina;
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
					
					norteOeste = new ArvoreQuadLocal(getXMinimo(),getX(),getYMinimo(),getY(),this);
					((ArvoreQuadLocal) norteOeste).add(objetos);
				}	
			}
			else
			{
				norteOeste = pagina;
			}
		}
		else
		{
			System.err.println("Erro");
			System.err.println("getY:" + getY() + " pagina.getY():" + pagina.getY() +  " getX:" +getX() + " pagina.getX():" +pagina.getX());
		}
	}
	
	public void add(Collection<Pagina> paginas)
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
	
	public Corpo corpoMedio()
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
	
}
