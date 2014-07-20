package entidades;

import java.util.Collection;

import org.json.JSONArray;
import org.json.JSONObject;

import core.Constantes;
import socket.escravo.SocketControle;

public class ArvoreQuadRemota extends ArvoreQuad
{
	private Long id;
	private String endereco;
	private Corpo corpoMedio = null;
	private Collection<Corpo> corpos = null;

	public ArvoreQuadRemota(Long id, String endereco,Double xMaximo,Double xMinimo,Double yMaximo,Double yMinimo,ArvoreQuadLocal pai)
	{
		
		if (endereco.contains("127.0.0.1"))
		{
			this.endereco = Constantes.enderecoMestre;
		}
		else
		{
			this.endereco = endereco.replace("/", "");
		}
		
		this.id = id;
		this.y = yMinimo + yMaximo / 2;
		this.x = xMinimo + xMaximo / 2;
		this.xMaximo = xMaximo;
		this.xMinimo = xMinimo;
		this.yMaximo = yMaximo;
		this.yMinimo = yMinimo;
		this.pai = pai;
	}
	public ArvoreQuadRemota(JSONObject jsonObject,ArvoreQuadLocal pai)
	{
		Double xMaximo,xMinimo,yMaximo,yMinimo;
		String endereco;
		
		Long id = jsonObject.getLong("id");
		endereco = jsonObject.getString("endereco");
		xMaximo = jsonObject.getDouble("xMaximo");			
		xMinimo = jsonObject.getDouble("xMinimo");			
		yMaximo = jsonObject.getDouble("yMaximo");			
		yMinimo = jsonObject.getDouble("yMinimo");
		
		this.endereco = endereco;
		this.id = id;
		this.y = yMinimo + yMaximo / 2;
		this.x = xMinimo + xMaximo / 2;
		this.xMaximo = xMaximo;
		this.xMinimo = xMinimo;
		this.yMaximo = yMaximo;
		this.yMinimo = yMinimo;
		this.pai = pai;
	}
	
	@Override
	public void add(Pagina pagina) throws Exception
	{
		SocketControle.getInstance().addPagina(id, pagina);
		
	}

	@Override
	public JSONArray toJsonArray()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Corpo> getCorpos()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Corpo getCorpoMedio()
	{
		try
		{
			if (corpoMedio == null)
			{
				corpoMedio = SocketControle.getInstance().getCorpoMedio(id, endereco);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return corpoMedio;
	}

	@Override
	public JSONObject toJsonObject()
	{
		JSONObject jsonObject = new JSONObject();
		
		jsonObject.put("xMinimo", xMinimo);
		jsonObject.put("xMaximo", xMaximo);
		
		jsonObject.put("yMinimo", yMinimo);
		jsonObject.put("yMaximo", yMaximo);
		
		jsonObject.put("endereco", endereco);
		jsonObject.put("id", id);
		
		return jsonObject;
	}
	



}
