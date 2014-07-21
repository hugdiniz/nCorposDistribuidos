package core;

import java.util.Date;

import entidades.ArvoreQuadLocal;

public class Sequencial 
{
	static Date dataUltimaAtualizacao;
	public static void main(String[] args) throws Exception
	{
		ArvoreQuadLocal arvoreQuadLocal = Servico.getInstance().recuperarArquivoJSonCorpos(Constantes.enderecoArquivoCorpos);
		dataUltimaAtualizacao = new Date();
		while (true)
		{
			arvoreQuadLocal.atualizarArvore();
			gerarTempo();
			
		}
	
	}
	
	private static void gerarTempo()
	{
		Date atual = new Date();
		Long tempo = atual.getTime() - dataUltimaAtualizacao.getTime();
		System.out.println("Ciclo concluido em: " + tempo);
		dataUltimaAtualizacao = atual;
		
	}
}
