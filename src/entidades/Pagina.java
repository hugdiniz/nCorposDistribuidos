 package entidades;

import java.util.ArrayList;
import java.util.Collection;

import org.json.JSONArray;
import org.json.JSONObject;

public abstract class Pagina 
{
	ArvoreQuadLocal pai;
	public Pagina() 
	{
			
	}
	protected Double xMinimo,xMaximo,x,yMinimo,yMaximo,y;
	
	public abstract JSONArray toJsonArray();
	public abstract JSONObject toJsonObject();
	
	public Double getX()
	{
		return x;
	}
	public Double getXMaximo()
	{
		return xMaximo;
	}
	public Double getXMinimo()
	{
		return xMinimo;
	}
	public Double getY()
	{
		return y;
	}
	public Double getYMaximo()
	{
		return yMaximo;
	}
	public Double getYMinimo()
	{
		return yMinimo;
	}
	public void setX(Double x)
	{
		this.x = x;
	}
	public void setY(Double y) 
	{
		this.y = y;
	}
	public ArvoreQuadLocal getPai()
	{
		return pai;
	}
	public void setPai(ArvoreQuadLocal pai)
	{
		this.pai = pai;
	}
	
	public  Collection<Corpo> getCorposArvoreCompleta() throws Exception
	{
		Collection<Corpo> corpos = new ArrayList<Corpo>();
		
		/*
		 * Recuperar os filhos dos ancenstrais
		 */
		if (pai != null) 
		{
			corpos.addAll(getCorposPai());
			if (pai.pai != null) 
			{
				corpos.addAll(getCorpoMedioAncestrais(pai));
			}
		}
		
		if (this instanceof ArvoreQuad)
		{
			corpos.addAll(((ArvoreQuad)this).getCorpos());
		}
		
		return corpos;
	}
	
	Collection<Corpo> getCorpoMedioAncestrais(ArvoreQuadLocal arvoreQuadLocal) throws Exception 
	{
		ArvoreQuadLocal ancestral = arvoreQuadLocal.pai;
		Collection<Corpo> corpos = new ArrayList<Corpo>();
		
		if (ancestral != null) 
		{
			Pagina norteLeste, norteOeste, sulLeste, sulOeste;
			
			norteLeste = ancestral.getNorteLeste();
			norteOeste = ancestral.getNorteOeste();
			sulLeste = ancestral.getSulLeste();
			sulOeste = ancestral.getSulOeste();
			
			if (norteOeste != null && norteOeste != arvoreQuadLocal)
			{
				if (norteOeste instanceof Corpo)
				{
					corpos.add((Corpo) norteOeste);
				}
				else if (norteOeste instanceof ArvoreQuad)
				{					
					corpos.add(((ArvoreQuad) norteOeste).getCorpoMedio());
				}			
				else 
				{
					throw new Exception("arvoreQuadLocal.getCorpoMedioAncestrais.pagina.nao.identificada");
				}			
			}
			if (norteLeste != null && norteLeste != arvoreQuadLocal)
			{
				if (norteLeste instanceof Corpo)
				{
					corpos.add((Corpo) norteLeste);
				}
				else if (norteLeste instanceof ArvoreQuad)
				{					
					corpos.add(((ArvoreQuad) norteLeste).getCorpoMedio());
				}			
				else 
				{
					throw new Exception("arvoreQuadLocal.getCorpoMedioAncestrais.pagina.nao.identificada");
				}			
			}
			if (sulOeste != null && sulOeste != arvoreQuadLocal)
			{
				if (sulOeste instanceof Corpo)
				{
					corpos.add((Corpo) sulOeste);
				}
				else if (sulOeste instanceof ArvoreQuad)
				{					
					corpos.add(((ArvoreQuad) sulOeste).getCorpoMedio());
				}			
				else 
				{
					throw new Exception("arvoreQuadLocal.getCorpoMedioAncestrais.pagina.nao.identificada");
				}			
			}
			if (sulLeste != null && sulLeste != arvoreQuadLocal)
			{
				if (sulLeste instanceof Corpo)
				{
					corpos.add((Corpo) sulLeste);
				}
				else if (sulLeste instanceof ArvoreQuad)
				{					
					corpos.add(((ArvoreQuad) sulLeste).getCorpoMedio());
				}			
				else 
				{
					throw new Exception("arvoreQuadLocal.getCorpoMedioAncestrais.pagina.nao.identificada");
				}			
			}
			
			if (ancestral.pai != null)
			{
				corpos.addAll(getCorpoMedioAncestrais(ancestral));
			}
		}
		
		return corpos;
	}
	
	Collection<Corpo> getCorposPai() throws Exception
	{
		Pagina norteLeste, norteOeste, sulLeste, sulOeste;
		norteLeste = pai.getNorteLeste();
		norteOeste = pai.getNorteOeste();
		sulLeste = pai.getSulLeste();
		sulOeste = pai.getSulOeste();
		Collection<Corpo> corpos = new ArrayList<Corpo>();
		
		if (norteOeste != null && norteOeste != this)
		{
			if (norteOeste instanceof Corpo)
			{
				corpos.add((Corpo) norteOeste);
			}
			else if (norteOeste instanceof ArvoreQuadLocal)
			{				
				corpos.addAll(((ArvoreQuadLocal) norteOeste).getCorpos());
			}
			else if (norteOeste instanceof ArvoreQuadRemota)
			{
				corpos.add(((ArvoreQuadRemota) norteOeste).getCorpoMedio());
			} 
			else 
			{
				throw new Exception("arvoreQuadLocal.getCorpoPaiMedio.pagina.nao.identificada");
			}			
		}
		
		if (norteLeste != null && norteLeste != this)
		{
			if (norteLeste instanceof Corpo)
			{
				corpos.add((Corpo) norteLeste);
			}
			else if (norteLeste instanceof ArvoreQuadLocal)
			{				
				corpos.addAll(((ArvoreQuadLocal) norteLeste).getCorpos());
			}
			else if (norteLeste instanceof ArvoreQuadRemota)
			{
				corpos.add(((ArvoreQuadRemota) norteLeste).getCorpoMedio());
			} 
			else 
			{
				throw new Exception("arvoreQuadLocal.getCorpoPaiMedio.pagina.nao.identificada");
			}			
		}
		if (sulOeste != null && sulOeste != this)
		{
			if (sulOeste instanceof Corpo)
			{
				corpos.add((Corpo) sulOeste);
			}
			else if (sulOeste instanceof ArvoreQuadLocal)
			{
				corpos.addAll(((ArvoreQuadLocal) sulOeste).getCorpos());
			}
			else if (sulOeste instanceof ArvoreQuadRemota)
			{
				corpos.add(((ArvoreQuadRemota) sulOeste).getCorpoMedio());
			} 
			else 
			{
				throw new Exception("arvoreQuadLocal.getCorpoPaiMedio.pagina.nao.identificada");
			}
		}
		if (sulLeste != null && sulLeste != this)
		{
			if (sulLeste instanceof Corpo)
			{
				corpos.add((Corpo) sulLeste);
			}
			else if (sulLeste instanceof ArvoreQuadLocal)
			{
				corpos.addAll(((ArvoreQuadLocal) sulLeste).getCorpos());
			}
			else if (sulLeste instanceof ArvoreQuadRemota)
			{
				corpos.add(((ArvoreQuadRemota) sulLeste).getCorpoMedio());
			} 
			else 
			{
				throw new Exception("arvoreQuadLocal.getCorpoPaiMedio.pagina.nao.identificada");
			}
		}
		return corpos;
	}
	
}
