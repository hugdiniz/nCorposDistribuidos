package entidades;

import java.rmi.RemoteException;

import org.json.JSONArray;
import org.json.JSONObject;

import eo.DirecaoEnum;

public class Corpo extends Pagina
{	
	public Corpo() throws RemoteException
	{
		super();		
	}
	Float massa;
	Float forca;
	Float velocidade;
	DirecaoEnum sentido;
	
	
	public DirecaoEnum getSentido()
	{
		return sentido;
	}
	public void setSentido(DirecaoEnum sentido) 
	{
		this.sentido = sentido;
	}	
	
	public Float getMassa() 
	{
		return massa;
	}
	public void setMassa(Float massa)
	{
		this.massa = massa;
	}
	public Float getForca()
	{
		return forca;
	}
	public void setForca(Float forca)
	{
		this.forca = forca;
	}
	public Float getVelocidade()
	{
		return velocidade;
	}
	public void setVelocidade(Float velocidade)
	{
		this.velocidade = velocidade;
	}
	
	@Override
	public String toString() 
	{
		StringBuffer string = new StringBuffer();
		string.append("X:{ "+ getX().toString() + " }");
		string.append("Y:{ "+ getY().toString() + " }");
		string.append("Forca:{ "+ getForca().toString() + " }");
		string.append("Velocidade:{ "+ getVelocidade().toString() + " }");
		string.append("Massa:{ "+ getMassa().toString() + " }");
		return string.toString();
	}
	public JSONArray toJsonArray()
	{
		JSONArray jsonArray = new JSONArray();
		jsonArray.put(getX());
		jsonArray.put(getY());
		jsonArray.put(forca);
		jsonArray.put(velocidade);
		jsonArray.put(massa);
		
		return jsonArray;
	}
	public JSONObject toJsonObject()
	{
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("x",getX());
		jsonObject.put("y",getY());
		jsonObject.put("forca",forca);
		jsonObject.put("velocidade",velocidade);
		jsonObject.put("massa",massa);
		
		return jsonObject;
	}
	
}
