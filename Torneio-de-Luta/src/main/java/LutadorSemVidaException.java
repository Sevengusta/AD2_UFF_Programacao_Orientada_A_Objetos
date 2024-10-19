public class LutadorSemVidaException extends RuntimeException{
  
  public LutadorSemVidaException(String message){
        super("Fim da Luta!!! O Lutador perdeu");
    }
}