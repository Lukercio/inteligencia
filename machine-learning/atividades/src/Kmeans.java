import java.util.Random;

/**
 * Created by ldlopes on 10/31/17.
 */
public class Kmeans{
    public static double calculaCentroid(int []means, double[][] m){
        double r = 0;
        calculaCentroid(means, m);
        return r;
    }

    public static double calculaDistancia(double q, double p){
        return Math.sqrt(Math.pow((q-p), 2));
    }

    public static double calculaDistancia(double []q, double []p){
        double r = 0;

        for (int i  = 0; i < q.length; i++) {
            r = r + Math.pow((q[i]-p[i]), 2);
        }

        return Math.sqrt(r);
    }

    public static double[] calculaDistanciasCentroids() {
        double[] m = new double[1];

        return m;
    }

    public static double[][] calculaKmeans(double[][] matrizX, int k) {
        final int SEED = 40;
        int maxMean = matrizX.length;
        int means[] = new int[k];
        double[][] result = new double[k][k];


        MersenneTwisterFast r;
        r = new MersenneTwisterFast(SEED);

        for (int i = 0; i < k; i++) {
            means[i] = r.nextInt() % maxMean;
        }

        calculaCentroid(means, matrizX);

        calculaDistanciasCentroids();

        return result;
    }

    public static void main(String []args){
        double[][] matriz1 = { {1,69}, {1,67}, {1,71}, {1,65}, {1,72}, {1,68}, {1,74}, {1,65}, {1,66}, {1,72}} ;
        int K = 3;


        calculaKmeans(matriz1, K);


    //System.out.print(r.nextInt(SEED) + " ");


    }
}
