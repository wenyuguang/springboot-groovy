package springboot.spi;

public class DivisionOperationImpl implements IOperation {
	 
    @Override
    public int operation(int numberA, int numberB) {
        return numberA / numberB;
    }
}