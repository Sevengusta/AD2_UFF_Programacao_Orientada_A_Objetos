public class LutaJaRealizadaException extends RuntimeException{
    public LutaJaRealizadaException(String message) {
        super("Luta já realizada, Será feito um novo Sorteio.");
    }
}