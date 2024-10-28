import java.io.Serializable;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Sistema implements Serializable {
    private List<Lutador> lutadores;
    private int qtdLutadores, contador, turnos = 0;
    private Random rand = new Random();
    private Scanner sc;
    private List<String> lutas;
    private List<Integer> lutasRealizadas;

    public Sistema(Scanner sc) {
        this.sc = sc;
        this.lutadores = new ArrayList<>();
        this.lutas = new ArrayList<>();
        this.lutasRealizadas = new ArrayList<>();
        this.qtdLutadores = 0;
    }

    public void addLutador(Lutador lutador) {
            this.lutadores.add(lutador);
            lutador.setIdLutador(qtdLutadores);
            this.qtdLutadores++;
    }

    public int getQtdLutadores() {
        return this.qtdLutadores;
    }
    public List<Integer> getLutasRealizadas() {
        return this.lutasRealizadas;
    }
    public List<String> getLutas() {
        return this.lutas;
    }

    public int getQtdLutas() {
        return lutas.size();
    }

    public Lutador getLutadorByName(String nome){
        for(Lutador lutador : this.lutadores){
            if(lutador.getNome().equals(nome)){
                return lutador;
            }
        }
        return null;
    }

    public void gerarLutas() {
        for (int i = 0; i < lutadores.size() - 1; i++) {
            for (int j = i + 1; j < lutadores.size(); j++) {
                lutas.add(lutadores.get(i).getNome() + " VS " + lutadores.get(j).getNome());
                lutasRealizadas.add(0);
            }
        }
    }
    public String sortearCompetidores() {
        int sorteio = 0;
        sorteio = lutas.size() == 0 ? 0 : rand.nextInt(lutas.size());
        String luta = lutas.get(sorteio);
        this.contador = 0;
        for (int i = 0; i < lutas.size(); i++){
            if (lutasRealizadas.get(i) == 1)
                this.contador ++;
        }
        if (this.contador == lutas.size()){
            return "-----FIM DO TORNEIO-----";
        }

        
        System.out.println("SORTEIO: " + luta + "\n" + "--------------------------------------------" );
        
        try {

            if (lutasRealizadas.get(sorteio) == 0) {
                lutasRealizadas.set(sorteio, 1); // sinaliza a luta como realizada 
            } else {
                throw new LutaJaRealizadaException("Luta já realizada.");
            }

        } catch (LutaJaRealizadaException e) {
            
            System.out.println(e.getMessage());
            lutas.remove(sorteio);
            lutasRealizadas.remove(sorteio);
            return sortearCompetidores(); // Sorteia outra luta 
        }

        return luta;
    }
    
    public void iniciarLuta(String competidores) {
        System.out.println("*** INICIANDO LUTA...");
        String[] competidoresArray = competidores.split("VS");
        Lutador lutador1 = this.getLutadorByName(competidoresArray[0].trim());
        Lutador lutador2 = this.getLutadorByName(competidoresArray[1].trim());
        System.out.println(lutador1.getNome() + " VS " + lutador2.getNome());

        try {
            while (lutador1.getVidaAtual() > 0 && lutador2.getVidaAtual() > 0) {
                this.turnos ++;
                if (this.turnos > 10){
                    System.out.println("------------------TIME OUT-----------------");
                    System.out.println("Nenhum lutador conseguiu alcançar o objetivo em 10 turnos");
                    break;
                } 
                System.out.println("++++++++++++++RESUMO DO TURNO++++++++++++++");
                System.out.println("Turno " + this.turnos);
                
                // Lutador 1 Turno
                int local = getAttackLocation(lutador1, this.sc);
                lutador1.atacar(local, lutador2);

                // Check if lutador2 is still alive
                if (lutador2.getVidaAtual() <= 0) {
                    throw new LutadorSemVidaException(lutador2.getNome() + " foi derrotado!");
                }

                // Lutador 2 Turno
                local = getAttackLocation(lutador2, this.sc);
                lutador2.atacar(local, lutador1);

                // Check if lutador1 is still alive
                if (lutador1.getVidaAtual() <= 0) {
                    throw new LutadorSemVidaException(lutador1.getNome() + " foi derrotado!");
                }
            }
        } catch (LutadorSemVidaException e) {
            // Reset health after the fight
            System.out.println(e.getMessage() );
        }
    }

    private int getAttackLocation(Lutador lutador, Scanner sc) {
        int local = -1;
        System.out.println("Lutador: " + lutador.getNome() + ", selecione o local de ataque:");
        System.out.println("0 - Inferior");
        System.out.println("1 - Tronco");
        System.out.println("2 - Superior");

        while (true) {
            try {
                local = sc.nextInt();
                if (local >= 0 && local <= 2) {
                    return local;
                } else {
                    System.out.println("Por favor, escolha uma opção válida (0, 1 ou 2)." + "\n");
                }
            } catch (Exception e) {
                System.out.println("Entrada inválida. Por favor, insira um número (0, 1 ou 2)." + "\n");
                sc.next(); // Clear invalid input
            }
        }
    }
    public void adicionarResultadoLuta(String competidores){
        String[] competidoresArray = competidores.split("VS");
        Lutador lutador1 = this.getLutadorByName(competidoresArray[0].trim());
        Lutador lutador2 = this.getLutadorByName(competidoresArray[1].trim());
        if (lutador1.getVidaAtual() > lutador2.getVidaAtual()) {
            System.out.println("VENCEDOR: " + lutador1.getNome());
            lutador1.addVitorias();
            lutador2.addDerrotas();
        }else {
            System.out.println("VENCEDOR: " + lutador2.getNome());
            lutador2.addVitorias();
            lutador1.addDerrotas();
        }
        
        this.turnos = 0;
        lutador1.setVidaTotal(lutador1.getVidaTotal());
        lutador2.setVidaTotal(lutador2.getVidaTotal());
        System.out.println("A vida dos lutadores foi devidamente restaurada" + "\n" + "-------------------------------------------" + "\n");
    }
    public void mostrarLutas(){
        for (String luta: lutas){
            System.out.println(luta);
        }
        System.out.println("--------------------------------------------");
    }
    // public void mostrarRanking(){
    //     System.out.println("--------------RANKING ATUALIZADO------------");
    //     for (Lutador lutador: lutadores){
    //         System.out.println(lutador.getNome() + " - " + lutador.getVitorias() + " vitórias e " + lutador.getDerrotas() + " derrotas");
    //     }
    //     System.out.println("--------------------------------------------");
        
    // }
    
    public void salvarDados() {
        FileOutputStream f = null;
        ObjectOutputStream o = null;
        try {
            f = new FileOutputStream(new File("./Sistema.dat"));
            o = new ObjectOutputStream(f);

            for (Lutador lutador : lutadores) {
                o.writeObject(lutador);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Close ObjectOutputStream first, then FileOutputStream
            if (o != null) {
                try {
                    o.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (f != null) {
                try {
                    f.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void carregarDados() {
        System.out.println("Carregando dados...");
        System.out.println("Resultado do torneio");

        FileInputStream f = null;
        ObjectInputStream o = null;

        try {
            f = new FileInputStream("./Sistema.dat");
            o = new ObjectInputStream(f);

            // Read objects until EOF is reached
            while (true) {
                Lutador lutador = (Lutador) o.readObject();
            }

        } catch (EOFException e) {
            // End of file reached, this is expected
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            // Ensure streams are closed
            try {
                if (o != null) o.close();
                if (f != null) f.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Optionally, print loaded lutadores for confirmation
        for (Lutador lutador : lutadores) {
            System.out.println(lutador.getNome() + ": " + lutador.getVitorias() + " vitórias e " + lutador.getDerrotas() + " derrotas");
        }
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Participantes do torneio: " + this.getQtdLutadores() + "\n");
        for (Lutador lutador : lutadores) {
            sb.append("Nome: " + lutador.getNome() + ", Vida Total: "
                    + lutador.getVidaTotal() + ", Resistência: " + lutador.getResistencia()
                    + ", Estilo de luta: " + lutador.getEstilo()).append("\n");
        }
        return sb.toString();
    }
}