/**
 * Created by ldlopes on 10/14/17.
 */
public class Pca extends Matriz{

    public static double[][] calculaPCA(double[][] matrizX, double[][] matrizY, boolean linear) {


    }

    public static void main(String []args){
        //testMetodos();
        boolean linear = true;
        double[][] matriz1 = { {1,69}, {1,67}, {1,71}, {1,65}, {1,72}, {1,68}, {1,74}, {1,65}, {1,66}, {1,72}} ;
        double[][] matriz2 = { {9.5}, {8.5}, {11.5}, {10.5}, {11}, {7.5},{12}, {7}, {7.5},{13}};
        Matriz m = new Matriz();


        m.exibeMatriz(calculaPCA(matriz1, matriz2, !linear), "Calculo PCA - height x shoe size");


    }
}
