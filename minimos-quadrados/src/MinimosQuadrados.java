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
    public static void exibeMatriz(double[][] m, String mensagem) {
        System.out.println("\n" + mensagem);

        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {

                System.out.print(m[i][j] + "\t");
            }
            System.out.print("\n");
        }

    }

    // Multiplica Matrizes
    public static double[][] multiplicaMatriz(double[][] matriz_1, double[][] matriz_2){
        int lin = matriz_1.length;
        int col = matriz_2[0].length;

        double[][] r = new double[lin][col];
        double v = 0;

        for (int i = 0; i < matriz_1.length; i++) {
            for (int j = 0; j < matriz_2[0].length; j++){
                for (int k = 0; k < matriz_1[0].length; k++) {
                    v = v + (matriz_1[i][k] * matriz_2[k][j]);
                }
                r[i][j] = v;
                v = 0;
            }
        }

        return r;
    }


    // Calcula Adjunta
    public static  double[][] calculaAdjunta(double[][] matriz) {
        int tamanho = matriz.length;
        double[][] adjunta = new double[tamanho][tamanho];

        if (tamanho < 4 && tamanho >1 ) {

            if (tamanho == 2) {
                adjunta[0][0] = matriz[1][1];
                adjunta[0][1] = -matriz[1][0];
                adjunta[1][0] = -matriz[0][1];
                adjunta[1][1] = matriz[0][0];

            } else if (tamanho == 3) {
                double[][] temp = new double[2][2];

                for (int i = 0; i < tamanho; i++) {
                    for (int j = 0; j < tamanho; j++) {
                        temp[0][0] = matriz[(i + 1) % 3][(j + 1) % 3];
                        temp[0][1] = matriz[(i + 1) % 3][(j + 2) % 3];
                        temp[1][0] = matriz[(i + 2) % 3][(j + 1) % 3];
                        temp[1][1] = matriz[(i + 2) % 3][(j + 2) % 3];

                        adjunta[i][j] = calculaDeterminante(temp);
                    }
                }
            }
        } else {
            System.out.println("Matriz não pode calcular adjunta. Não é 2x2 ou 3x3.");
            System.exit(1);
        }
        return calculaTransposta(adjunta);
    }


    // Calcula inversa
    public static double[][] calculaInversa(double[][] matriz) {
        int tamanho = matriz.length;
        double[][] inversa = new double[tamanho][tamanho];

        if(tamanho < 4  && tamanho > 1) {
            double[][] adjunta = calculaAdjunta(matriz);
            double determinante = calculaDeterminante(matriz);

            exibeMatriz(adjunta, "adjunta");
            System.out.println("d=" + determinante);
            for (int i = 0; i < tamanho; i++){
                for (int j = 0; j < tamanho; j++){
                    inversa[i][j] = (1/determinante) * adjunta[i][j];
                }
            }
        } else {
            System.out.println("Matriz não pode calcular inversa. Não é 2x2 ou 3x3.");
            System.exit(1);
        }

        return inversa;
    }

    // Calcula matriz Transposta
    public static double[][] calculaTransposta(double[][] matriz) {
        int col = matriz.length;
        int lin = matriz[0].length;
        double[][] transp = new double[lin][col];


        for (int i = 0; i < col; i++) {
            for (int j = 0; j < lin; j++) {
                transp[j][i] = matriz[i][j];
            }
        }

        return transp;
    }


    // Calcula Determinante
    public static double calculaDeterminante(double[][] matriz) {
        int tamanho = matriz.length;
        double det = 0;

        if (tamanho == 2)
            det =  (matriz[0][0] * matriz[1][1]) - (matriz[0][1] * matriz[1][0]);


        if (tamanho == 3) {
            for (int i = 0; i < tamanho; i++) {

                det = det + (matriz[0][i] * (matriz[1][(i + 1) % 3] * matriz[2][(i + 2) % 3] - matriz[1][(i + 2) % 3] * matriz[2][(i + 1) % 3]));

            }
        }

        return det;
    }

    /*
     X = np.array([np.append(i, i[col-1]*i[col-1]) for i in X])

    inv = calc_inversa(np.dot(np.transpose(X), X))

    * */

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

        }

        inversaMultiplicacaoMatrizX = calculaInversa(multiplicaMatriz(calculaTransposta(matrizX), matrizX));

        exibeMatriz(inversaMultiplicacaoMatrizX, "inversaMultiplicacaoMatrizX");

        exibeMatriz(calculaTransposta(matrizX),"calculaTransposta(matrizX)");
        exibeMatriz(matrizY, "matrizY:");

        traspXporY = multiplicaMatriz( calculaTransposta(matrizX) , matrizY);
        exibeMatriz(traspXporY,"traspXporY");

        return multiplicaMatriz(inversaMultiplicacaoMatrizX, (traspXporY));
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


        //exibeMatriz(multiplicaMatriz(matriz1,matriz2),"multi");
        double[][] matriz1 = { {1,69}, {1,67}, {1,71}, {1,65}, {1,72}, {1,68}, {1,74}, {1,65}, {1,66}, {1,72}} ;
        double[][] matriz2 = { {9.5}, {8.5}, {11.5}, {10.5}, {11}, {7.5},{12}, {7}, {7.5},{13}};

        exibeMatriz(calculaMMQ(matriz1, matriz2, false), "Calculo MMQ");


    }

}
