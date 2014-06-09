package core;

import java.rmi.Naming;

import entidades.ArvoreQuad;

public class Mestre 
{
	public static void main(String[] args) 
	{	
		
		try 
		{
			ArvoreQuad arvoreQuad = Servico.getInstance().recuperarArquivoJSonCorpos("src/arquivos/teste.json");
			Naming.rebind("rmi://localhost:1099/ArvoreQuad", arvoreQuad);  
		} 
		catch (Exception e)
		{			
			e.printStackTrace();
		}
	}
}
