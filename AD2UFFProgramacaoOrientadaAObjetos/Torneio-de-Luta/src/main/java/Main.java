import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner personagemSc = new Scanner(System.in);
        Sistema torneio = new Sistema(personagemSc);
        // Criar participantes Torneio
        while (true) {
            System.out.println("Digite o nome do participante: (Enter para iniciar o torneio) ");
            String nome = personagemSc.nextLine().trim();
            nome = torneio.getQtdLutadores() != 0 ? personagemSc.nextLine().trim() : nome;
            if (nome.isEmpty() & torneio.getQtdLutadores() >= 2) {
                break;
            }
            nome = nome.isEmpty() ? "Lutador Genérico " + (torneio.getQtdLutadores() + 1) : nome;

            double vidaTotal = 0;
            while (true) {
                System.out.println("Digite sua vida total: ");
                try {
                    vidaTotal = personagemSc.nextDouble();
                    if (vidaTotal <= 0) {
                        throw new IllegalArgumentException("A vida total deve ser positiva.");
                    }
                    break; // Saia do loop se a entrada for válida
                } catch (Exception e) {
                    System.out.println("Entrada inválida. Por favor, insira um número válido para a vida total.");
                    personagemSc.nextLine(); // Tratar a entrada inválida e limpar o buffer
                }
            }

            double resistencia = 0;
            while (true) {
                System.out.println("Digite seus pontos de resistência (entre 0.01 e 0.99): ");
                try {
                    resistencia = personagemSc.nextDouble();
                    if (resistencia < 0.01 || resistencia > 0.99) {
                        throw new IllegalArgumentException("Os pontos de resistência devem estar entre 0.01 e 0.99.");
                    }
                    break;
                } catch (Exception e) {
                    System.out.println("Entrada inválida. Por favor, insira um número válido para a resistência.");
                    personagemSc.nextLine();
                }
            }
            personagemSc.nextLine();

            Lutador lutador = null;
            while (true) {
                System.out.println("Digite o seu estilo de luta ");
                System.out.println("Opções:");
                System.out.println("1 - Lutador Normal");
                System.out.println("2 - Lutador de Karate");
                System.out.println("3 - Lutador de Boxe");
                System.out.println("4 - Lutador de JiuJitsu");

                int estilo = personagemSc.nextInt();

                if (estilo == 1) {
                    lutador = new Lutador(nome, vidaTotal, resistencia);
                    break;
                } else if (estilo == 2) {
                    lutador = new LutadorDeKarate(nome, vidaTotal, resistencia);
                    break;
                } else if (estilo == 3) {
                    lutador = new LutadorDeJiuJitsu(nome, vidaTotal, resistencia);
                    break;
                } else if (estilo == 4) {
                    lutador = new LutadorDeBoxe(nome, vidaTotal, resistencia);
                    break;
                } else {
                    System.out.println("Estilo de luta não reconhecido. Tente novamente.");
                }
            }
            torneio.addLutador(lutador); // Adiciona o Lutador a lista de lutadores
        }
        System.out.println("-------------INÍCIO DO TORNEIO--------------");
        System.out.println(torneio);  // mostra quem são os participantes do torneio
        System.out.println("---------LUTAS QUE SERÃO REALIZADAS---------");
        torneio.gerarLutas();
        torneio.mostrarLutas();
        
        while (true) {
            String luta = torneio.sortearCompetidores();
            if (luta == "-----FIM DO TORNEIO-----"){
                System.out.println("--------------FIM DO TORNEIO---------------");
                break;
            }
            torneio.iniciarLuta(luta);
            torneio.adicionarResultadoLuta(luta);
            // torneio.mostrarRanking();
        }
        // Garantindo a persistência dos dados
        torneio.salvarDados();
        torneio.carregarDados();
        personagemSc.close();
    }
}