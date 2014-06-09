package entidades;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import org.json.JSONArray;

public abstract class Pagina extends UnicastRemoteObject implements Remote {

	public Pagina() throws RemoteException
	{
		super();		
	}
	protected Integer xMinimo,xMaximo,x,yMinimo,yMaximo,y;
	
	public abstract JSONArray toJsonArray();
	
	public Integer getX()
	{
		return x;
	}
	public Integer getXMaximo()
	{
		return xMaximo;
	}
	public Integer getXMinimo()
	{
		return xMinimo;
	}
	public Integer getY()
	{
		return y;
	}
	public Integer getYMaximo()
	{
		return yMaximo;
	}
	public Integer getYMinimo()
	{
		return yMinimo;
	}
	public void setX(Integer x)
	{
		this.x = x;
	}
	public void setY(Integer y) 
	{
		this.y = y;
	}
	
	
}
