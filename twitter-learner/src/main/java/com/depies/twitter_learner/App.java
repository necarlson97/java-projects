package com.depies.twitter_learner;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
                .iterations(1)
                .weightInit(WeightInit.XAVIER)
                .activation("relu")
                .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
                .learningRate(0.05)
                // ... other hyperparameters
                .backprop(true)
                .build();
    }
}
