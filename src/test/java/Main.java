import org.junit;
import hust.mso.gaframework.Individual;

public class IndividualTest {
    @Test
    public void evalFitness() {
        Individual ind = new Individual();
        ind.Init();

        assertEquals(1000, ind.getFitness());
    }
}
