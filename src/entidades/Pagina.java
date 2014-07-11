 package entidades;

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
	
	
}
