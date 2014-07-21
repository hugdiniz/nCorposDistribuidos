package core;

public class Constantes
{
	/**
	 * Constantes do Mestre
	 */
	
	public static String enderecoArquivoCorpos = "src/arquivos/50000Corpos.json";
	public static String enderecoMestre = "192.168.25.85"; //Nao utilizar 127.0.0.1
	public static Integer portaMestre = 7000;
	public static Integer quantidadeEscravo = 4;
	public static Integer tempo = 9;
	public static Double xMaximoArvore = 1000000.0;
	public static Double yMaximoArvore = 1000000.0;
	public static Double xMinimoArvore = 0.0;
	public static Double yMinimoArvore = 0.0;
	public static Integer tempoInicial = 1000;
	/**
	 * Constantes do Escravo
	 */
	
	public static Integer portaEscravo = 7001;
	
	
	/**
	 * Constantes do GeradorJSONCorpos
	 */
	
	public static String nomeEnderecoArquivoGerado = "src/arquivos/50000Corpos.json";
	public static Integer quantidadeCorposGerados = 50000;
	public static Integer xMaximoCorpoGerado = 1000000;
	public static Integer yMaximoCorpoGerado = 1000000;	
	public static Integer massaCorpoGerado = 400;
	
	
}
