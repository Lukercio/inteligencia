/**
 * Created by ldlopes on 10/24/17.
 */
public class Matriz {

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

            //exibeMatriz(adjunta, "adjunta");
            //System.out.println("d=" + determinante);
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
}
