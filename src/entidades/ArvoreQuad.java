package entidades;


import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;

import org.json.JSONArray;

public class ArvoreQuad extends Pagina
{	
	Pagina norteOeste;
	Pagina norteLeste;
	Pagina sulOeste;
	Pagina sulLeste;
	
	public ArvoreQuad(Integer xMinimo,Integer xMaximo,Integer yMinimo,Integer yMaximo) throws RemoteException
	{
		super();
		this.y = yMinimo + yMaximo / 2;
		this.x = xMinimo + xMaximo / 2;
		this.xMaximo = xMaximo;
		this.xMinimo = xMinimo;
		this.yMaximo = yMaximo;
		this.yMinimo = yMinimo;
	}
	
	public Object getNorteOeste()
	{
		return norteOeste;
	}
	
	public void setNorteOeste(Pagina norteOeste) 
	{
		this.norteOeste = norteOeste;
	}
	
	
	public Object getNorteLeste()
	{
		return norteLeste;
	}
	
	public void setNorteLeste(Pagina norteLeste) 
	{
		this.norteLeste = norteLeste;
	}	
	
	public Object getSulOeste() 
	{
		return sulOeste;
	}	
	
	public void setSulOeste(Pagina sulOeste)
	{
		this.sulOeste = sulOeste;
	}
	
	public Object getSulLeste()
	{
		return sulLeste;
	}	
	
	public void setSulLeste(Pagina sulLeste) 
	{
		this.sulLeste = sulLeste;
	}
	
	public void add(Pagina pagina) throws RemoteException
	{
		
		if(pagina.getY() > getY() && pagina.getX() > getX())
		{
			if (sulLeste != null)
			{
				if (sulLeste instanceof ArvoreQuad) 
				{
					((ArvoreQuad) sulLeste).add(pagina);				
				}
				else
				{
					Collection<Pagina> objetos = new ArrayList<Pagina>();
					objetos.add(pagina);
					objetos.add(sulLeste);
					
					sulLeste = new ArvoreQuad(getX(),getXMaximo(),getY(),getYMaximo());
					((ArvoreQuad) sulLeste).add(objetos);
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
				if (sulOeste instanceof ArvoreQuad) 
				{
					((ArvoreQuad) sulOeste).add(pagina);				
				}
				else
				{
					Collection<Pagina> objetos = new ArrayList<Pagina>();
					objetos.add(pagina);
					objetos.add(sulOeste);
					
					sulOeste = new ArvoreQuad(getXMinimo(),getX(),getY(),getYMaximo());
					((ArvoreQuad) sulOeste).add(objetos);
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
				if (norteLeste instanceof ArvoreQuad) 
				{
						((ArvoreQuad) norteLeste).add(pagina);			
				}
				else
				{
					Collection<Pagina> objetos = new ArrayList<Pagina>();
					objetos.add(pagina);
					objetos.add(norteLeste);
					
					norteLeste = new ArvoreQuad(getXMinimo(),getX(),getY(),getYMaximo());
					((ArvoreQuad) norteLeste).add(objetos);
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
				if (norteOeste instanceof ArvoreQuad) 
				{
							((ArvoreQuad) norteOeste).add(pagina);		
				}
				else
				{
					Collection<Pagina> objetos = new ArrayList<Pagina>();
					objetos.add(pagina);
					objetos.add(norteOeste);
					
					norteOeste = new ArvoreQuad(getXMinimo(),getX(),getYMinimo(),getY());
					((ArvoreQuad) norteOeste).add(objetos);
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
	
	public void add(Collection<Pagina> paginas) throws RemoteException
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
		else if(norteLeste instanceof ArvoreQuad)
		{
			corpos.addAll(((ArvoreQuad) norteLeste).getCorpos());
		}
		
		if (norteOeste instanceof Corpo) 
		{
			corpos.add((Corpo) norteOeste);
		}
		else if(norteOeste instanceof ArvoreQuad)
		{
			corpos.addAll(((ArvoreQuad) norteOeste).getCorpos());
		}
		
		if (sulOeste instanceof Corpo) 
		{
			corpos.add((Corpo) sulOeste);
		}
		else if(sulOeste instanceof ArvoreQuad)
		{
			corpos.addAll(((ArvoreQuad) sulOeste).getCorpos());
		}
		
		if (sulLeste instanceof Corpo) 
		{
			corpos.add((Corpo) sulLeste);
		}
		else if(sulLeste instanceof ArvoreQuad)
		{
			corpos.addAll(((ArvoreQuad) sulLeste).getCorpos());
		}
		
		return corpos;
	}
	
}
