package entidades;

import java.util.Collection;

import org.json.JSONObject;


public abstract class ArvoreQuad extends Pagina
{
	Pagina norteOeste;
	Pagina norteLeste;
	Pagina sulOeste;
	Pagina sulLeste;
	
	
	public abstract void add(Pagina pagina) throws Exception;
	
	public abstract Collection<Corpo> getCorpos(); 
	public abstract Corpo getCorpoMedio();
	
	public static ArvoreQuadLocal montarArvore(JSONObject jsonObject) throws Exception
	{
		Double xMinimo,xMaximo,yMinimo,yMaximo;
		xMinimo = jsonObject.getDouble("xMinimo");
		xMaximo = jsonObject.getDouble("xMaximo");
		yMinimo = jsonObject.getDouble("yMinimo");
		yMaximo = jsonObject.getDouble("yMaximo");
		
		ArvoreQuadLocal arvoreQuadLocal = new  ArvoreQuadLocal(xMinimo, xMaximo, yMinimo, yMaximo);
		
		if(jsonObject.has("norteLeste"))
		{
			JSONObject jsonObjectPagina = jsonObject.getJSONArray("norteLeste").getJSONObject(0);
			Pagina pagina; 
			if (jsonObjectPagina.has("ArvoreQuadLocal"))
			{
				pagina = montarArvore(jsonObjectPagina.getJSONArray("ArvoreQuadLocal").getJSONObject(0),arvoreQuadLocal);
			}
			else if(jsonObjectPagina.has("ArvoreQuadRemota"))
			{				
				pagina = new ArvoreQuadRemota(jsonObjectPagina.getJSONArray("ArvoreQuadRemota").getJSONObject(0), arvoreQuadLocal);
			}
			else if(jsonObjectPagina.has("Corpo"))
			{
				pagina = new Corpo(jsonObjectPagina.getJSONArray("Corpo").getJSONObject(0));
			}
			
			else
			{
				throw new Exception("erro.arvore.objeto.nao.identificado.json.object");
			}
			pagina.pai = arvoreQuadLocal;
			arvoreQuadLocal.norteLeste = pagina; 
		}
		if(jsonObject.has("norteOeste"))
		{
			JSONObject jsonObjectPagina = jsonObject.getJSONArray("norteOeste").getJSONObject(0);
			Pagina pagina; 
			if (jsonObjectPagina.has("ArvoreQuadLocal"))
			{
				pagina = montarArvore(jsonObjectPagina.getJSONArray("ArvoreQuadLocal").getJSONObject(0),arvoreQuadLocal);
			}
			else if(jsonObjectPagina.has("ArvoreQuadRemota"))
			{				
				pagina = new ArvoreQuadRemota(jsonObjectPagina.getJSONArray("ArvoreQuadRemota").getJSONObject(0), arvoreQuadLocal);
			}
			else if(jsonObjectPagina.has("Corpo"))
			{
				pagina = new Corpo(jsonObjectPagina.getJSONArray("Corpo").getJSONObject(0));
			}
			
			else
			{
				throw new Exception("erro.arvore.objeto.nao.identificado.json.object");
			}
			
			pagina.pai = arvoreQuadLocal;
			arvoreQuadLocal.norteOeste = pagina; 
		}
		if(jsonObject.has("sulOeste"))
		{
			JSONObject jsonObjectPagina = jsonObject.getJSONArray("sulOeste").getJSONObject(0);
			Pagina pagina; 
			if (jsonObjectPagina.has("ArvoreQuadLocal"))
			{
				pagina = montarArvore(jsonObjectPagina.getJSONArray("ArvoreQuadLocal").getJSONObject(0),arvoreQuadLocal);
			}
			else if(jsonObjectPagina.has("ArvoreQuadRemota"))
			{				
				pagina = new ArvoreQuadRemota(jsonObjectPagina.getJSONArray("ArvoreQuadRemota").getJSONObject(0), arvoreQuadLocal);
			}
			else if(jsonObjectPagina.has("Corpo"))
			{
				pagina = new Corpo(jsonObjectPagina.getJSONArray("Corpo").getJSONObject(0));
			}
			
			else
			{
				throw new Exception("erro.arvore.objeto.nao.identificado.json.object");
			}
			
			pagina.pai = arvoreQuadLocal;
			arvoreQuadLocal.sulOeste = pagina; 
		}
		if(jsonObject.has("sulLeste"))
		{
			JSONObject jsonObjectPagina = jsonObject.getJSONArray("sulLeste").getJSONObject(0);
			Pagina pagina; 
			if (jsonObjectPagina.has("ArvoreQuadLocal"))
			{
				pagina = montarArvore(jsonObjectPagina.getJSONArray("ArvoreQuadLocal").getJSONObject(0),arvoreQuadLocal);
			}
			else if(jsonObjectPagina.has("ArvoreQuadRemota"))
			{				
				pagina = new ArvoreQuadRemota(jsonObjectPagina.getJSONArray("ArvoreQuadRemota").getJSONObject(0), arvoreQuadLocal);
			}
			else if(jsonObjectPagina.has("Corpo"))
			{
				pagina = new Corpo(jsonObjectPagina.getJSONArray("Corpo").getJSONObject(0));
			}
			
			else
			{
				throw new Exception("erro.arvore.objeto.nao.identificado.json.object");
			}
			
			pagina.pai = arvoreQuadLocal;
			arvoreQuadLocal.sulLeste = pagina; 
		}
		
		return arvoreQuadLocal;
	}
	public ArvoreQuadLocal getRaiz()
	{
		if (pai != null) {
			return pai.getRaiz();
		}
		else
		{
			return pai;
		}	
	}
	
	public static ArvoreQuad montarArvore(JSONObject jsonObject,ArvoreQuadLocal pai) throws Exception
	{
		ArvoreQuad arvoreQuad = montarArvore(jsonObject);
		arvoreQuad.pai = pai;
		return arvoreQuad;
	}

	
}
