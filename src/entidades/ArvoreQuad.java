package entidades;


public abstract class ArvoreQuad extends Pagina
{
	Pagina norteOeste;
	Pagina norteLeste;
	Pagina sulOeste;
	Pagina sulLeste;
	
	public abstract Pagina getNorteOeste();
	public abstract Pagina getNorteLeste();	
	public abstract Pagina getSulOeste();	
	public abstract Pagina getSulLeste();
	
	public abstract void add(Pagina pagina);
}
