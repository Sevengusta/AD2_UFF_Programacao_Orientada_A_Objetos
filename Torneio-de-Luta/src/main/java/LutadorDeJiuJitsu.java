public class LutadorDeJiuJitsu extends Lutador {

  public LutadorDeJiuJitsu(String nome, double vidaTotal, double resistencia) {
    super(nome, vidaTotal, resistencia);
    super.setEstilo("Lutador de Jiu Jitsu");
  }

  @Override
  public void ataqueFraco(Lutador lutador) {
    int poderDeAtaque = 9;
    double danoBase = (poderDeAtaque * (lutador.getVidaTotal() / lutador.getVidaAtual()));
    double danoFinal = danoBase * (1 - lutador.getResistencia());
    lutador.setVidaAtual(danoFinal);
    System.out.println("O lutador " + this.getNome() + " acertou o golpe: Ippon");
  }

  @Override
  public void ataqueForte(Lutador lutador) {
    int poderDeAtaque = 11;
    double danoBase = (poderDeAtaque * (lutador.getVidaTotal() / lutador.getVidaAtual()));
    double danoFinal = danoBase * (1 - lutador.getResistencia());
    lutador.setVidaAtual(danoFinal);
    System.out.println("O lutador " + this.getNome() + " acertou o golpe: Harai");
  }
}