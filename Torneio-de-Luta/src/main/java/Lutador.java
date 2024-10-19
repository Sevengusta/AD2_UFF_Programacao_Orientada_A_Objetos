import java.util.Random;

public class Lutador{
    private String nome, estilo;
    private double resistencia, vidaTotal, vidaAtual;
    private String[] defesas = new String[3];
    private int vitorias, derrotas = 0;
    protected int idLutador;

    public Lutador(String nome, double vidaTotal, double resistencia) {
        this.nome = nome;
        this.vidaTotal = vidaTotal;
        this.vidaAtual = vidaTotal;
        this.vitorias = 0;
        this.derrotas = 0;
        this.resistencia = resistencia;
        this.estilo = "Lutador";
        this.defesas[0] = "Inferior";
        this.defesas[1] = "Tronco";
        this.defesas[2] = "Superior";
    }
    protected void setIdLutador(int idLutador){
        this.idLutador = idLutador;
    }

    public String getNome() {
        return nome;
    }
    public String getEstilo() {
        return estilo;
    }
    protected String setEstilo(String estilo) {return this.estilo = estilo; }
    
    public int getIdLutador(){return idLutador;}
    
    public double getVidaTotal() {
        return vidaTotal;
    }

    public double getVidaAtual() {
        return vidaAtual;
    }
    public int getVitorias(){
        return this.vitorias;
    }
    public int getDerrotas(){
        return this.derrotas;
    }

    public void setVidaAtual(double ataque) {
        this.vidaAtual -= ataque;
    }
    public void setVidaTotal(double vidaTotal) {
        this.vidaAtual = vidaTotal;
    }
    public void addVitorias(){
        this.vitorias++;
    }
    public void addDerrotas(){
        this.derrotas++;
    }

    public double getResistencia() {
        return resistencia;
    }

    public void ataqueFraco(Lutador lutador) {
        int poderDeAtaque = 6;
        double danoBase = (poderDeAtaque * (lutador.getVidaTotal() / lutador.getVidaAtual()));
        double danoFinal = danoBase * (1 - lutador.getResistencia());
        lutador.setVidaAtual(danoFinal);
        System.out.println("O lutador " + this.nome + " acertou o golpe: ataque fraco");
    }

    public void ataqueForte(Lutador lutador) {
        int poderDeAtaque = 10;
        double danoBase = (poderDeAtaque * (lutador.getVidaTotal() / lutador.getVidaAtual()));
        double danoFinal = danoBase * (1 - lutador.getResistencia());
        lutador.setVidaAtual(danoFinal);
        System.out.println("O lutador " + this.nome + " acertou o golpe: ataque Forte");

    }

    public void atacar(int local, Lutador lutador) {
        Random rand = new Random();
        double ataque = rand.nextDouble();
        int defesa = rand.nextInt(3);
        if (local == defesa) {
            System.out.println("O lutador " + lutador.getNome() +
                    " Foi Experto, conseguiu bloquear o ataque do lutador " + this.nome);
        } else {
            if (ataque > 0.5) {
                this.ataqueFraco(lutador);
                if (lutador.getVidaAtual() <= 0){
                    System.out.println("----------------FIM DA LUTA----------------");
                    System.out.println("K.O: O lutador " + lutador.getNome() + " foi derrotado" );
                } else {
                    System.out.println(
                            "    Restam " + String.format("%.2f", lutador.getVidaAtual()) +
                                    " pontos de vida para o lutador " + lutador.getNome());

                }
                
            } else {
                this.ataqueForte(lutador);
                if (lutador.getVidaAtual() <= 0){
                    System.out.println("----------------FIM DA LUTA----------------");
                    System.out.println("K.O. O lutador " + lutador.getNome() + " foi derrotado");
                } else {
                    System.out.println(
                            "    Restam " + String.format("%.2f", lutador.getVidaAtual()) +
                                    " pontos de vida para o lutador " + lutador.getNome());

                }
            }

        }
    }
}