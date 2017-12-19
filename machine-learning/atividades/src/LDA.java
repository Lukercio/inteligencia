/**
 * Created by ldlopes on 12/6/17.
 */
public class LDA extends Matriz{

        public static double[][] calculaLDA(double[][] matrizX, double[][] matrizY, boolean linear) {
            exibeMatriz(calculaCovariancia(multiplicaMatriz(calculaTransposta(matrizX), matrizX)), "Covariancia Matrizx");

            return calculaCovariancia(matrizX);
        }

        public static void main(String []args){
            //testMetodos();
            boolean linear = true;
            double[][] matriz1 = { {1,69}, {1,67}, {1,71}, {1,65}, {1,72}, {1,68}, {1,74}, {1,65}, {1,66}, {1,72}} ;
            double[][] matriz2 = { {9.5}, {8.5}, {11.5}, {10.5}, {11}, {7.5},{12}, {7}, {7.5},{13}};
            Matriz m = new Matriz();


            m.exibeMatriz(calculaLDA(matriz1, matriz2, !linear), "Calculo PCA - height x shoe size");


        }


}
