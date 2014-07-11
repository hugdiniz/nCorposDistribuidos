package entidades;

import java.util.Collection;

import org.json.JSONArray;
import org.json.JSONObject;

import eo.DirecaoEnum;


public abstract class ArvoreQuad extends Pagina
{
	Pagina norteOeste;
	Pagina norteLeste;
	Pagina sulOeste;
	Pagina sulLeste;
	
	
	public abstract void add(Pagina pagina) throws Exception;
	
	public abstract Collection<Corpo> getCorpos(); 
	public abstract Corpo getCorpoMedio();
	
	public static ArvoreQuad montarArvore(JSONObject jsonObject) throws Exception
	{
		Double xMinimo,xMaximo,yMinimo,yMaximo;
		xMinimo = jsonObject.getDouble("xMinimo");
		xMaximo = jsonObject.getDouble("xMaximo");
		yMinimo = jsonObject.getDouble("yMinimo");
		yMaximo = jsonObject.getDouble("yMaximo");
		
		ArvoreQuadLocal  arvoreQuadLocal = new  ArvoreQuadLocal(xMinimo, xMaximo, yMinimo, yMaximo);
		
		if(jsonObject.has("norteLeste"))
		{
			JSONObject norteLesteJsonObject = jsonObject.getJSONArray("norteLeste").getJSONObject(0);
			
			if (norteLesteJsonObject.has("ArvoreQuadLocal"))
			{
				arvoreQuadLocal.norteLeste = montarArvore(norteLesteJsonObject.getJSONArray("ArvoreQuadLocal").getJSONObject(0),arvoreQuadLocal);
			}
			else if(norteLesteJsonObject.has("ArvoreQuadRemota"))
			{				
				arvoreQuadLocal.norteLeste = new ArvoreQuadRemota(norteLesteJsonObject.getJSONArray("ArvoreQuadRemota").getJSONObject(0), arvoreQuadLocal);
			}
			else if(norteLesteJsonObject.has("Corpo"))
			{
				arvoreQuadLocal.norteLeste = new Corpo(norteLesteJsonObject.getJSONArray("Corpo").getJSONObject(0));
			}
			else
			{
				throw new Exception("erro.arvore.objeto.nao.identificado.json.object");
			}	
		}
		if(jsonObject.has("norteOeste"))
		{
			JSONObject norteOesteJsonObject = jsonObject.getJSONArray("norteOeste").getJSONObject(0);
			
			if (norteOesteJsonObject.has("ArvoreQuadLocal"))
			{
				arvoreQuadLocal.norteOeste = montarArvore(norteOesteJsonObject.getJSONArray("ArvoreQuadLocal").getJSONObject(0),arvoreQuadLocal);
			}
			else if(norteOesteJsonObject.has("ArvoreQuadRemota"))
			{
				arvoreQuadLocal.norteOeste = new ArvoreQuadRemota(norteOesteJsonObject.getJSONArray("ArvoreQuadRemota").getJSONObject(0), arvoreQuadLocal);
			}
			else if(norteOesteJsonObject.has("Corpo"))
			{
				arvoreQuadLocal.norteOeste = new Corpo(norteOesteJsonObject.getJSONArray("Corpo").getJSONObject(0));
			}
			else
			{
				throw new Exception("erro.arvore.objeto.nao.identificado.json.object");
			}	
		}
		if(jsonObject.has("sulOeste"))
		{
			JSONObject sulOesteJsonObject = jsonObject.getJSONArray("sulOeste").getJSONObject(0);
			
			if (sulOesteJsonObject.has("ArvoreQuadLocal"))
			{
				arvoreQuadLocal.sulOeste = montarArvore(sulOesteJsonObject.getJSONArray("ArvoreQuadLocal").getJSONObject(0),arvoreQuadLocal);
			}
			else if(sulOesteJsonObject.has("ArvoreQuadRemota"))
			{
				arvoreQuadLocal.sulOeste = new ArvoreQuadRemota(sulOesteJsonObject.getJSONArray("ArvoreQuadRemota").getJSONObject(0), arvoreQuadLocal);
			}
			else if(sulOesteJsonObject.has("Corpo"))
			{
				arvoreQuadLocal.sulOeste = new Corpo(sulOesteJsonObject.getJSONArray("Corpo").getJSONObject(0));
			}
			else
			{
				throw new Exception("erro.arvore.objeto.nao.identificado.json.object");
			}	
		}
		if(jsonObject.has("sulLeste"))
		{
			JSONObject sulLesteJsonObject = jsonObject.getJSONArray("sulLeste").getJSONObject(0);
			
			if (sulLesteJsonObject.has("ArvoreQuadLocal"))
			{
				arvoreQuadLocal.sulLeste = montarArvore(sulLesteJsonObject.getJSONArray("ArvoreQuadLocal").getJSONObject(0),arvoreQuadLocal);
			}
			else if(sulLesteJsonObject.has("ArvoreQuadRemota"))
			{				
				arvoreQuadLocal.sulLeste = new ArvoreQuadRemota(sulLesteJsonObject.getJSONArray("ArvoreQuadRemota").getJSONObject(0), arvoreQuadLocal);
			}
			else if(sulLesteJsonObject.has("Corpo"))
			{
				arvoreQuadLocal.sulLeste = new Corpo(sulLesteJsonObject.getJSONArray("Corpo").getJSONObject(0));
			}
			else
			{
				throw new Exception("erro.arvore.objeto.nao.identificado.json.object");
			}	
		}
		return arvoreQuadLocal;
	}
	public static ArvoreQuad montarArvore(JSONObject jsonObject,ArvoreQuadLocal pai) throws Exception
	{
		ArvoreQuad arvoreQuad = montarArvore(jsonObject);
		arvoreQuad.pai = pai;
		return arvoreQuad;
	}

	
}
