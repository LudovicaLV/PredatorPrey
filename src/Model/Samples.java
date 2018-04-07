package Model;

	import org.apache.commons.math3.distribution.EnumeratedIntegerDistribution;
	import java.util.Random;	

	public class Samples {
		int[] numsToGenerate;
		double[] discreteProbabilities;
		
		public static int getDiscrete(int[] numsToGenerate, double[] discreteProbabilities){

			EnumeratedIntegerDistribution distribution = new EnumeratedIntegerDistribution(numsToGenerate, discreteProbabilities);

			int numSamples = 1;
			int[] samples = distribution.sample(numSamples);
			return samples[0];
			
			}
		
		static Random rnd = new Random();
	    public static double getExp (double rate) {	
	    			Double randomDouble = rnd.nextDouble();
	    			double ret = -Math.log(1-randomDouble) / rate;
	    			return ret;
	    		}
		public static int[] arrayActions(int length) {
			int[] arrayAct = new int[length];
			for (int i=0; i < length; i++){
			  arrayAct[i] = i + 1;
			}
			return arrayAct;
		}
	    }
		


