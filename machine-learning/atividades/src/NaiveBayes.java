/**
 * Created by ldlopes on 12/6/17.
 */
public class NaiveBayes extends Matriz{

    // Prob (x | input)
    public static double calculaNaiveBayes(Matrix A, double []input) {
        double x = input[input.length - 1];
        double prob = 0, fator1 = 1, fator2 = 1, fator3 = 1, f1, f3;
        Matrix probAtrib = new Matrix(1, A.colLen());

        // Calcula a prob de cada atributo
        for (int j = 0; j < A.colLen(); j++) {
            f1 = 0;
            f3 = 0;
            for (int i = 0; i < A.rowsLen(); i++) {
                if (A.data[i][j] == input[j]) {
                    f3++;
                    if (A.data[i][(A.colLen() - 1)] == x) {
                        f1++;
                    }
                }
            }
            f1 = f1 / A.rowsLen();
            f3 = f3 / A.rowsLen();
            fator1 = fator1*f1;
            fator3 = fator3*f3;
        }

        for (int i = 0; i < A.rowsLen(); i++) {
            if (A.data[i][A.colLen() - 1] == x) {
                fator2++;
            }
        }

        fator2 = fator2 / A.rowsLen();

        System.out.println(fator1);
        System.out.println(fator2);
        System.out.println(fator3);

        prob = (fator1 * fator2) / fator3;
        return prob;
    }

    public static void main(String []args){
        double resultado = 0;

        /********** Legenda - Mau Pagador
            s =1, n=0
            solteiro=0, casado = 1, divorc=-1
            alto = 2, medio = 1, baixo =0
            sim = 1, nao = 0
        */

        double[][] a = {{1,0,2,0},{0,1,1,0},{0,0,0,0},{1,1,2,0},{0,-1,1,1},{0,0,0,0},{1,-1,2,0},{0,0,1,1},{0,1,0,0},{0,0,1,1}};
        Matrix A = new Matrix(a);

        double mauPagador = 1;
        double[] input = {0,-1,1, mauPagador};
        System.out.println("Naive Bayes");
        A.show();

        resultado = calculaNaiveBayes(A, input);
        System.out.println("É mau pagador?\nR: " + (resultado < 0.5 ? "Sim " : "Não " ));

    }
}
