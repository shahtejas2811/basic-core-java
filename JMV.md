# Java Virtual Machine Architecture
## 1. Class Loader
	^ Class loader loads .class file into the main memory.
	^ The first class to be loaded into memory is usually the class that contains the main() method.
	^ There are three phases in the class loading process
		* loading
			There are three built-in class loaders available in Java:
			1. Bootstrap Class Loader:
				- This is the root class loader. 
				- It is the superclass of Extension Class Loader
				- Loads the standard Java packages like java.lang, java.net, java.util, java.io, and so on. 
				- These packages are present inside the rt.jar file
				- Other core libraries present in the $JAVA_HOME/jre/lib directory.		
			2. Extension Class Loader: 
				- This is the subclass of the Bootstrap Class Loader 
				- Superclass of the Application Class Loader. 
				- This loads the extensions of standard Java libraries which are present in the $JAVA_HOME/jre/lib/ext directory.
			3. Application Class Loader:
				- This is the final class loader and the subclass of Extension Class Loader. 
				- It loads the files present on the classpath. 
				- By default, the classpath is set to the current directory of the application.
				- The classpath can also be modified by adding the -classpath or -cp command line option.
				
			The JVM uses the ClassLoader.loadClass() method for loading the class into memory. 
			It tries to load the class based on a fully qualified name.
			If a parent class loader is unable to find a class, it delegates the work to a child class loader. 
			If the last child class loader isn't able to load the class either, it throws NoClassDefFoundError or ClassNotFoundException.
				1. ClassNotFoundException:
					^ It is an exception. It is of type java.lang.Exception.
					^ It occurs when an application tries to load a class at run time using Class.forName(),loadClass() but class is not present in classpath.
					^ Program will throw ClassNotFoundException if the mentioned class “oracle.jdbc.driver.OracleDriver” is not found in the classpath.
					
					    public static void main(String[] args)
					    {
					        try
					        {
					            Class.forName("oracle.jdbc.driver.OracleDriver");
					        }catch (ClassNotFoundException e)
					        {
					            e.printStackTrace();
					        }
					    }
					
				2. NoClassDefFoundError:
					^ It is an error. It is of type java.lang.Error.
					^ It occurs when java runtime system doesn’t find a class definition, which is present at compile time, but missing at run time.
				 
		* linking
			After a class is loaded into memory, it undergoes the linking process. 
			Linking a class or interface involves combining the different elements and dependencies of the program together.
			Linking includes the following steps:
			1.Verification: 
				- This phase checks the structural correctness of the .class file by checking it against a set of constraints or rules. 
				- If verification fails for some reason, we get a VerifyException.
				- ex, if the code has been built using Java 11, but is being run on a system that has Java 8 installed, the verification phase will fail.
			2.Preparation: 
				- JVM allocates memory for the static fields of a class or interface, and initializes them with default values.
				- ex, Assume that you have declared the following variable in your class:
				private static final boolean enabled = true;
				JVM allocates memory for the variable enabled and sets its value to the default value for a boolean, which is false.
			3.Resolution: 
				- symbolic references are replaced with direct references present in the runtime constant pool.
				- ex, if you have references to other classes or constant variables present in other classes, 
				  they are resolved in this phase and replaced with their actual references.
		* initialization.
			- Initialization involves executing the initialization method of the class or interface (known as <clinit>). 
			- This can include calling the class's constructor, Executing the static block, and assigning values to all the static variables. 
			- This is the final stage of class loading.
			
			- Ex, when we declared the following code earlier:
				private static final boolean enabled = true;
			
			- The variable enabled was set to its default value of false during the preparation phase. 
			- In the initialization phase, this variable is assigned its actual value of true.

## 2. Runtime Memory/Data Area
	There are five components inside the runtime data area:
	1.Method Area
		- All the class level data such as the run-time constant pool,field,method data,the code for methods and constructors, are stored here.
		- If the memory available in the method area is not sufficient for the program startup, the JVM throws an OutOfMemoryError.
		- ex, assume that you have the following class definition:
			public class Employee {
			  
			  private String name;
			  private int age;
			  
			  public Employee(String name, int age) {
			  
			    this.name = name;
			    this.age = age;
			  }
			}
			
		- field level data such as name and age and the constructor details are loaded into the method area.
		- The method area is created on the virtual machine start-up, and there is only one method area per JVM.

	2.Heap Area
		- All the objects and their corresponding instance variables are stored here. 
		- This is the run-time data area from which memory for all class instances and arrays is allocated.
		- ex
			Employee employee = new Employee();
		- In this code example, an instance of Employee is created and loaded into the heap area.
		- The heap is created on the virtual machine start-up, and there is only one heap area per JVM.
		- Since the Method and Heap areas share the same memory for multiple threads, the data stored here is not thread safe.

	3.Stack Area
		- Whenever a new thread is created,A separate runtime stack is also created at the same time. 
		- All local variables, method calls, and partial results are stored in the stack area.
		- If the processing being done in a thread requires a larger stack size than what's available, the JVM throws a StackOverflowError.
		- For every method call, one entry is made in the stack memory which is called the Stack Frame. 
		- When the method call is complete, the Stack Frame is destroyed.
		- The Stack Frame is divided into three sub-parts:
			1. Local Variables : 
				^ Each frame contains an array of variables known as its local variables. All local variables and their values are stored here.
				^ The length of this array is determined at compile-time.
			2. Operand Stack  :
				^ Each frame contains a last-in-first-out (LIFO) stack known as its operand stack. 
				^ This acts as a runtime workspace to perform any intermediate operations. The maximum depth of this stack is determined at compile-time.
			3. Frame Data :
				– All symbols corresponding to the method are stored here. This also stores the catch block information in case of exceptions.
				  For example
				
				double calculateNormalisedScore(List<Answer> answers) {
				  
				  double score = getScore(answers);
				  return normalizeScore(score);
				}
				
				double normalizeScore(double score) {
				  
				  return (score – minScore) / (maxScore – minScore);
				}
				
				- In this code example, variables like answers and score are placed in the Local Variables array. 
				- The Operand Stack contains the variables and operators required to perform the mathematical calculations of subtraction and division.
				- Note: Since the Stack Area is not shared, it is inherently thread safe.

	4.Program Counter (PC) Registers
		- The JVM supports multiple threads at the same time. 
		- Each thread has its own PC Register to hold the address of the currently executing JVM instruction.
		- Once the instruction is executed, the PC register is updated with the next instruction.

	5.Native Method Stacks 
	  - JVM contains stacks that support native methods.
	  - These methods are written in a language other than the Java, such as C and C++. 
	  - For every new thread, a separate native method stack is also allocated.
## 3. Execution Engine
	Once the bytecode has been loaded into the main memory, and details are available in the runtime data area
	the next step is to run the program. The Execution Engine handles this by executing the code present in each class.
	However, before executing the program, the bytecode needs to be converted into machine language instructions. 
	The JVM can use an interpreter or a JIT compiler for the execution engine.
	
	1. Interpreter :
	- Interpreter reads and executes the bytecode instructions line by line. 
	- Due to the line by line execution, the interpreter is comparatively slower.
	- Another disadvantage of the interpreter is that when a method is called multiple times, every time a new interpretation is required.

	2. JIT Compiler:
	-	The JIT Compiler overcomes the disadvantage of the interpreter. 
	- The Execution Engine first uses the interpreter to execute the byte code, but when it finds some repeated code, it uses the JIT compiler.
	- The JIT compiler then compiles the entire bytecode and changes it to native machine code. 
	- This native machine code is used directly for repeated method calls, which improves the performance of the system.
	- The JIT Compiler has the following components:
		* Intermediate Code Generator - generates intermediate code
		* Code Optimizer - optimizes the intermediate code for better performance
		* Target Code Generator - converts intermediate code to native machine code
		* Profiler - finds the hotspots (code that is executed repeatedly)
	To better understand the difference between interpreter and JIT compiler, assume that you have the following code:

		int sum = 10;
		for(int i = 0 ; i <= 10; i++) {
		   sum += i;
		}
		System.out.println(sum);
		
	-	An interpreter will fetch the value of sum from memory for each iteration in the loop, add the value of i to it, and write it back to memory. 
		This is a costly operation because it is accessing the memory each time it enters the loop.
	-  However, the JIT compiler will recognize that this code has a HotSpot, and will perform optimizations on it. 
		It will store a local copy of sum in the PC register for the thread and will keep adding the value of i to it in the loop. 
		Once the loop is complete, it will write the value of sum back to memory.
	-  Note: a JIT compiler takes more time to compile the code than for the interpreter to interpret the code line by line. 
		If you are going to run a program only once, using the interpreter is better.


	3. Garbage Collector
	- The Garbage Collector (GC) collects and removes unreferenced objects from the heap area. 
	- It is the process of reclaiming the runtime unused memory automatically by destroying them.
	- Garbage collection makes Java memory efficient because it removes the unreferenced objects from heap memory and makes free space for new objects. 
	- It involves two phases:
		* Mark - GC identifies the unused objects in memory
		* Sweep - GC removes the objects identified during the previous phase
	- The JVM contains 3 different types of garbage collectors:
		* Serial GC 
			^ This is the simplest implementation of GC, and is designed for small applications running on single-threaded environments. 
			^ It uses a single thread for garbage collection. When it runs, it leads to a "stop the world" event where the entire application is paused.
			^ The JVM argument to use Serial Garbage Collector is -XX:+UseSerialGC
		* Parallel GC 
		   ^ This is the default implementation of GC in the JVM, and is also known as Throughput Collector.
		   ^ It uses multiple threads for garbage collection, but still pauses the application when running. 
		   ^ The JVM argument to use Parallel Garbage Collector is -XX:+UseParallelGC.
		* Garbage First (G1) GC 
		   ^ G1GC was designed for multi-threaded applications that have a large heap size available (more than 4GB). 
		   ^ It partitions the heap into a set of equal size regions, and uses multiple threads to scan them.
		   ^ G1GC identifies the regions with the most garbage and performs garbage collection on that region first. 
		   ^ The JVM argument to use G1 Garbage Collector is -XX:+UseG1GC
		   
	 ref: https://www.freecodecamp.org/news/garbage-collection-in-java-what-is-gc-and-how-it-works-in-the-jvm/
