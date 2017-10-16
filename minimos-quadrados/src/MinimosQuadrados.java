import java.util.Scanner;

/**
 * Created by ldlopes on 10/8/17.
 */
public class MinimosQuadrados {

    // Limpa a tela da linha de comandos do Windows e Linux
    public static void limpaTela() {
        try {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows")) {
                Runtime.getRuntime().exec("cls");
            } else {
                Runtime.getRuntime().exec("clear");
            }
        }
        catch (final Exception e) {
            e.printStackTrace();
        }
    }

    // Mostra uma mensagem e a matriz na tela formatada
    public static void exibeMatriz(int[][] m, int tamanho, String mensagem) {
        System.out.println("\n" + mensagem);

        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {

                System.out.print(m[i][j] + "\t");
            }
            System.out.print("\n");
        }

    }

    // Multiplica Matrizes
    public double multiplicaMatriz(){
        double r = 0;


        return r;
    }

    // Calcula Determinante
    public static double calculaDeterminante() {


    }

    // Executa comandos em sequencia
    public static void main(String []args){
        int entrada, i, j;
        Scanner n = new Scanner( System.in );

        // Verifica o tamanho da matriz desejada
        do {
            limpaTela();
            System.out.println("Método dos Mínimos Quadrados\n\n");
            System.out.println("Informe a matriz desejada, sendo 2x2 (digite 2) ou 3x3 (digite 3):");
            entrada = n.nextInt();
        } while (entrada != 2 && entrada != 3);

        int[][] matriz = new int[entrada][entrada];

        System.out.println("Informe os valores da matriz " + entrada +" x " + entrada + " :");

        // Le Matriz com tamanho informado anteriormente
        for (i=0; i < entrada; i++) {
            for(j = 0; j< entrada; j++) {
                matriz[i][j] = n.nextInt();
            }
        }
        exibeMatriz(matriz, entrada, "Matriz Inserida:");

    }

}
