package entidades;

import org.json.JSONArray;
import org.json.JSONObject;

import eo.DirecaoEnum;


public class Corpo extends Pagina
{
	private Double massa;
	private Double forca;
	private Double velocidade;
	private DirecaoEnum sentido;
	private Double deslocamento;
		
	
	public void calcDeslocamento(Integer T){
		
		Double aceleracao;
		
		aceleracao = forca/massa;
		
		deslocamento =  ((velocidade * T) + ((aceleracao * (T*T))/2));
				
	}
	
	
	public DirecaoEnum getSentido()
	{
		return sentido;
	}
	public void setSentido(DirecaoEnum sentido) 
	{
		this.sentido = sentido;
	}	
	
	public Double getMassa() 
	{
		return massa;
	}
	public void setMassa(Double massa)
	{
		this.massa = massa;
	}
	public Double getForca()
	{
		return forca;
	}
	public void setForca(Double forca)
	{
		this.forca = forca;
	}
	public Double getVelocidade()
	{
		return velocidade;
	}
	public void setVelocidade(Double velocidade)
	{
		this.velocidade = velocidade;
	}
	
	public Double getDeslocamento(){
		
		return this.deslocamento;
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
