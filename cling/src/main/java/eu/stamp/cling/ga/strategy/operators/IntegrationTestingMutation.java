package eu.stamp.cling.ga.strategy.operators;

import eu.stamp.botsing.commons.ga.strategy.operators.Mutation;
import eu.stamp.cling.testgeneration.CallableMethodPool;
import org.evosuite.ga.Chromosome;
import org.evosuite.testcase.TestChromosome;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IntegrationTestingMutation<T extends Chromosome> extends Mutation<T> {
    private static final Logger LOG = LoggerFactory.getLogger(IntegrationTestingMutation.class);

    public void mutateOffspring(T offspring) {
        boolean isValid = false;
        int nTrials = 0; // we try maximum 50 insertion mutations (to avoid infinite loop)
        while (!isValid && nTrials < 5) {
            try {
                doRandomMutation(offspring);
                isValid = CallableMethodPool.getInstance().includesPublicCall((TestChromosome) offspring);
            } catch (AssertionError e) {
                LOG.debug("Random insertion mutation was unsuccessful.");
            } finally {
                nTrials++;
            }
        }
        offspring.setChanged(true);
        offspring.updateAge(offspring.getAge() + 1);
    }


    protected void doRandomMutation(Chromosome offspring) {
        boolean mutated = false;
        while (!mutated){
            try{
                offspring.mutate();
                mutated=true;
            }catch (Exception e){
                LOG.debug("Mutation was unsuccessful!");
            }
        }
    }

}
