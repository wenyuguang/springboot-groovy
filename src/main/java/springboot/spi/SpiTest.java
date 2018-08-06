package springboot.spi;

import java.util.Iterator;
import java.util.ServiceLoader;

public class SpiTest {

	public static void main(String[] args) {
//		IOperation plus = new PlusOperationImpl();
//
//		IOperation division = new DivisionOperationImpl();
//		System.out.println(plus.operation(5, 3));
//
//		System.out.println(division.operation(9, 3));

		ServiceLoader<IOperation> operations = ServiceLoader.load(IOperation.class);
		Iterator<IOperation> operationIterator = operations.iterator();
		while (operationIterator.hasNext()) {
			IOperation operation = operationIterator.next();
			System.out.println(operation.operation(6, 3));
		}
	}
}
