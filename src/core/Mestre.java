package core;

public class Mestre 
{
	public static void main(String[] args) 
	{	
		
		try 
		{
			Servico.getInstance().recuperarArquivoJSonCorpos("src/arquivos/teste.json");
		} catch (Exception e)
		{			
			e.printStackTrace();
		}
		
	}
}
