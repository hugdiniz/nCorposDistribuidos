package util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import org.json.JSONArray;

import entidades.Corpo;
import eo.DirecaoEnum;

public class GeradorJSONCorpos
{
	public static void main(String[] args)
	{
		try
		{
			GeradorJSONCorposArquivo("src/arquivos/100Corpos.json", 100, 1000, 1000, 40);
		}
		catch (IOException e)
		{			
			e.printStackTrace();
		}
	}
	
	public static void GeradorJSONCorposArquivo(String nomeArquivo,Integer quantidade,Integer xMaximo,Integer yMaximo,Integer massaMaxima) throws IOException
	{
		File file = new File(nomeArquivo);
		/*
		 * Json e composto por: X, Y, massa, forca, velocidade, direcao
		 */
		Corpo corpo = new Corpo();
		FileWriter writer = new FileWriter(file); 
		PrintWriter saida = new PrintWriter(writer); 
		Random random = new Random();
		for (int i = 0; i < quantidade; i++)
		{
			JSONArray jsonArray = new JSONArray();
			Integer x = random.nextInt(xMaximo);
			Integer y = random.nextInt(yMaximo);
			Integer massa = random.nextInt(massaMaxima);
			jsonArray.put(x);
			jsonArray.put(y);
			jsonArray.put(massa);
			jsonArray.put(0);
			jsonArray.put(0);
			jsonArray.put(DirecaoEnum.NORTE.toString());
			saida.println(jsonArray.toString());
		}
		saida.close();
		writer.close();

	}
}
