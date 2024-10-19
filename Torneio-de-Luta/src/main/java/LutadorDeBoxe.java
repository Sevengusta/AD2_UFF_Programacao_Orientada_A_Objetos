public class LutadorDeBoxe extends Lutador {

  public LutadorDeBoxe(String nome, double vidaTotal, double resistencia) {
    super(nome, vidaTotal, resistencia);
    super.setEstilo("Lutador de Boxe");
  }

  @Override
  public void ataqueFraco(Lutador lutador) {
    int poderDeAtaque = 10;
    double danoBase = (poderDeAtaque * (lutador.getVidaTotal() / lutador.getVidaAtual()));
    double danoFinal = danoBase * (1 - lutador.getResistencia());
    lutador.setVidaAtual(danoFinal);
    System.out.println("O lutador " + this.getNome() + " acertou o golpe: Gancho");
  }

  @Override
  public void ataqueForte(Lutador lutador) {
    int poderDeAtaque = 14;
    double danoBase = (poderDeAtaque * (lutador.getVidaTotal() / lutador.getVidaAtual()));
    double danoFinal = danoBase * (1 - lutador.getResistencia());
    lutador.setVidaAtual(danoFinal);
    System.out.println("O lutador " + this.getNome() + " acertou o golpe: Cruzado");
  }
}