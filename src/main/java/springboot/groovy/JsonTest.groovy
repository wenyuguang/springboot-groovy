package springboot.groovy

class JsonTest {

	static void main(args) {
		def builder = new groovy.json.JsonBuilder()
		def root = builder.students {
		   student {
			  studentname 'Joe'
			  studentid '1'
				
			  Marks(
				 Subject1: 10,
				 Subject2: 20,
				 Subject3:30,
			  )
		   }
		}
		println(builder.toString());
	}
}
