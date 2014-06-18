package entidades;

import org.json.JSONArray;
import org.json.JSONObject;

import eo.DirecaoEnum;


public class Corpo extends Pagina
{
	private Float massa;
	private Float forca;
	private Float velocidade;
	private DirecaoEnum sentido;
		
	
	public void calcDeslocamento(Integer T){
		
		Float deslocamento;
		Float aceleracao;
		
		aceleracao = forca/massa;
		
		deslocamento =  ((velocidade * T) + ((aceleracao * (T*T))/2));
		
		//Atualizando a posicao do Corpo
		atualizaPosicao(deslocamento);
		
	}
	
	
	public void atualizaPosicao(Float deslocamento){
		
		//Quando é NORTE ou SUL significa que apenas irá mexer no Y, ou seja na VERTICAL 
		if (sentido == DirecaoEnum.SUL || sentido == DirecaoEnum.NORTE){
			
			//Tem que verifica com o Hugo como funcionara essa atualização de Y
			
			//Adicionar um método que verifica se o corpo saiu do Nó o qual pertence
		}
		
		//Quando é LESTE ou OESTE significa que apenas irá mexer no X, ou seja na HORIZONTAL 
		if(sentido == DirecaoEnum.LESTE || sentido == DirecaoEnum.OESTE){
			
			//Tem que verifica com o Hugo como funcionara essa atualização de X
			
			//Adicionar um método que verifica se o corpo saiu do Nó o qual pertence
		}
	
	}
	
	//Verificar se o corpo saiu do Nó o qual pertence, para deslocar o corpo para outro nó,
	//ou para outro processo 
	public boolean verficarPosicao(){
		return false;
	}
	
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
