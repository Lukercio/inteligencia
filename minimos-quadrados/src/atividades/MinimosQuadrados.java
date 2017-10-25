import java.util.Scanner;

/**
 * Created by ldlopes on 10/8/17.
 */
public class MinimosQuadrados extends Matriz{

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

    public static double[][] calculaMMQ(double[][] matrizX, double[][] matrizY, boolean linear) {
        int lin = matrizX.length;
        int col = matrizX[0].length;
        double[][] matrizTemp = new double[lin][col + 1];
        double[][] inversaMultiplicacaoMatrizX;
        double[][] traspXporY;

        if (!linear) {
            for (int i = 0; i < matrizX.length ; i++) {
                for(int j = 0; j < matrizX[0].length; j++) {
                    matrizTemp[i][j] = matrizX[i][j];
                }
            }

            for(int i = 0; i < matrizX.length; i++) {
                matrizTemp[i][col] = matrizX[i][col-1] * matrizX[i][col-1];
            }

            exibeMatriz(matrizTemp, "matrizTemp");

            matrizX = new double[matrizTemp.length][matrizTemp[0].length];
            matrizX = matrizTemp;

        }

        inversaMultiplicacaoMatrizX = calculaInversa(multiplicaMatriz(calculaTransposta(matrizX), matrizX));
        exibeMatriz(multiplicaMatriz(calculaTransposta(matrizX), matrizX), "");

        traspXporY = multiplicaMatriz( calculaTransposta(matrizX) , matrizY);

        return multiplicaMatriz(inversaMultiplicacaoMatrizX, (traspXporY));
    }

    public static void testMetodos() {
        int entrada, i, j;
        Scanner n = new Scanner( System.in );

        // Verifica o tamanho da matriz desejada
        do {
            limpaTela();
            System.out.println("Método dos Mínimos Quadrados\n\n");
            System.out.println("Informe a matriz desejada, sendo 2x2 (digite 2) ou 3x3 (digite 3):");
            entrada = n.nextInt();
        } while (entrada != 2 && entrada != 3);

        double[][] matriz = new double[entrada][entrada];

        System.out.println("Informe os valores da matriz " + entrada +" x " + entrada + " :");

        // Le Matriz com tamanho informado anteriormente
        for (i=0; i < entrada; i++) {
            for(j = 0; j< entrada; j++) {
                matriz[i][j] = n.nextInt();
            }
        }
        exibeMatriz(matriz, "Matriz Inserida:");

        // Teste Matriz Transposta
        exibeMatriz(calculaTransposta(matriz), "Matriz transposta:" );
    }

        // Executa comandos em sequencia
    public static void main(String []args){
        //testMetodos();
        boolean linear = true;
        double[][] matriz1 = { {1,69}, {1,67}, {1,71}, {1,65}, {1,72}, {1,68}, {1,74}, {1,65}, {1,66}, {1,72}} ;
        double[][] matriz2 = { {9.5}, {8.5}, {11.5}, {10.5}, {11}, {7.5},{12}, {7}, {7.5},{13}};
        Matriz m = new Matriz();


        m.exibeMatriz(calculaMMQ(matriz1, matriz2, !linear), "Calculo MMQ - height x shoe size");


    }

}
