package streamNlamda.prog4;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProcessingEmployees
{
    public static void main(String[] args)
    {
        // initialize array of Employees
        Employee[] employees = {
                new Employee("Jason", "Red", 5000, "IT"),
                new Employee("Ashley", "Green", 7600, "IT"),
                new Employee("Matthew", "Indigo", 3587.5, "Sales"),
                new Employee("James", "Indigo", 4700.77, "Marketing"),
                new Employee("Luke", "Indigo", 6200, "IT"),
                new Employee("Jason", "Blue", 3200, "Sales"),
                new Employee("Wendy", "Brown", 4236.4, "Marketing")};

        // get List view of the Employees
        List<Employee> list = Arrays.asList(employees);

        // display all Employees
        System.out.println("Complete Employee list:");
        list.stream().forEach(System.out::println);   //A method reference.

        // Predicate (boolean-valued function) that returns true for salaries
        //in the range $4000-$6000
        Predicate<Employee> fourToSixThousand =
                e -> (e.getSalary() >= 4000 && e.getSalary() <= 6000);

        // Display Employees with salaries in the range $4000-$6000
        // sorted into ascending order by salary
        System.out.printf(
                "%nEmployees earning $4000-$6000 per month sorted by salary:%n");

        list.stream()
                .filter(fourToSixThousand)
                .sorted(Comparator.comparing(Employee::getSalary))
                .forEach(System.out::println);

        // Display first Employee with salary in the range $4000-$6000
        System.out.printf("%nFirst employee who earns $4000-$6000:%n%s%n",
                list.stream()
                        .filter(fourToSixThousand)
                        .findFirst()
                        .get());

        // Functions for getting first and last names from an Employee
        Function<Employee, String> byFirstName = Employee::getFirstName;
        Function<Employee, String> byLastName = Employee::getLastName;

        // Comparator for comparing Employees by first name then last name
        Comparator<Employee> lastThenFirst =
                Comparator.comparing(byLastName).thenComparing(byFirstName);

        // sort employees by last name, then first name
        System.out.printf(
                "%nEmployees in ascending order by last name then first:%n");
        list.stream()
                .sorted(lastThenFirst)
                .forEach(System.out::println);

        // sort employees in descending order by last name, then first name
        System.out.printf(
                "%nEmployees in descending order by last name then first:%n");
        list.stream()
                .sorted(lastThenFirst.reversed())
                .forEach(System.out::println);

        // display unique employee last names sorted
        System.out.printf("%nUnique employee last names:%n");
        list.stream()
                .map(Employee::getLastName)
                .distinct()
                .sorted()
                .forEach(System.out::println);

        // display only first and last names
        System.out.printf(
                "%nEmployee names in order by last name then first name:%n");
        list.stream()
                .sorted(lastThenFirst)
                .map(Employee::getName)
                .forEach(System.out::println);

        // group Employees by department
        System.out.printf("%nEmployees by department:%n");
        Map<String, List<Employee>> groupedByDepartment =
                list.stream()
                        .collect(Collectors.groupingBy(Employee::getDepartment));

        groupedByDepartment.forEach(
                (department, employeesInDepartment) ->
                {
                    System.out.println(department);
                    employeesInDepartment.forEach(
                            employee -> System.out.printf("   %s%n", employee));
                }
        );

        // count number of Employees in each department
        System.out.printf("%nCount of Employees by department:%n");

        Map<String, Long> employeeCountByDepartment =
                list.stream()
                        .collect(Collectors.groupingBy(Employee::getDepartment,
                                TreeMap::new, Collectors.counting()));

        employeeCountByDepartment.forEach(
                (department, count) -> System.out.printf(
                        "%s has %d employee(s)%n", department, count));

/*  Output looks something like :

        HR  4
        IT  8
        Sales  12
*/


        // sum of Employee salaries with DoubleStream sum method
        System.out.printf(
                "%nSum of Employees' salaries (via sum method): %.2f%n",
                list.stream()
                        .mapToDouble(Employee::getSalary)
                        .sum());

        // calculate sum of Employee salaries with Stream reduce method
        System.out.printf(
                "Sum of Employees' salaries (via reduce method): %.2f%n",
                list.stream()
                        .mapToDouble(Employee::getSalary)
                        .reduce(0, (value1, value2) -> value1 + value2));

        // average of Employee salaries with DoubleStream average method
        System.out.printf("Average of Employees' salaries: %.2f%n",
                list.stream()
                        .mapToDouble(Employee::getSalary)
                        .average()
                        .getAsDouble());

        // Count the number of last names that begin with the letter ‘B’
        System.out.printf("Number of last names that begin with the letter ‘B’: %d%n",
                list.stream()
                        .map(Employee::getLastName)
                        .filter(e -> e.startsWith("B"))
                        .count());

        //Print out all of the Employee objects whose last name begins with the letter ‘B’  in sorted order.
        System.out.println("all of the Employee objects whose last name begins with the letter ‘B’");
                list.stream()
                        .map(Employee::getLastName)
                        .filter(e -> e.startsWith("B"))
                        .sorted()
                        .forEach(System.out::println);

      //  if the last name begins with the letter ‘B’,  then capitalize all the letters in the last name.
        System.out.println("if the last name begins with the letter ‘B’,  then capitalize all the letters in the last name");
        list.stream()
                .map(Employee::getLastName)

                .filter(e -> e.startsWith("B"))
                .map(e -> e.toUpperCase())
                .forEach(System.out::println);

        //Use the Collectors.joining  method to print out All Employee objects
        String all = list.stream()
                .map(Employee::toString)
                .collect(Collectors.joining());

        String allWithDelimiter = list.stream()
                .map(Employee::toString)
                .collect(Collectors.joining("---\\n---"));

        System.out.println("Using the Collectors.joining \n" +all);
        System.out.println("Using the Collectors.joining \n" +allWithDelimiter);

        //Print out all of the Employee objects’ last names, whose last name begins with the letter  ‘I’  in sorted order,
        // and get rid of all the duplicates.  Print out only the last names.
        System.out.println("whose last name begins with the letter  ‘I’  in sorted order and Distinct value");
        list.stream()
                .map(Employee::getLastName)
                .filter(e -> e.startsWith("I"))
                .sorted()
                .distinct()
                .forEach(System.out::println);

        double avg = list.stream()
                .mapToDouble(Employee::getSalary)
                .average()
                .getAsDouble();
        System.out.printf("Average = %.2f", avg);
        System.out.println();

        //Use the  ‘reduce’  method to print out the total salary of all employees.

       double sum =  list.stream()
                .mapToDouble(Employee::getSalary)
                .reduce(0,(a,b)-> a+b);
        System.out.printf("Total = %.2f", sum);

        //Print out only the first names of all the employees.
        System.out.println("Print out only the first names of all the employees");
        list.stream()
                .map(Employee::getFirstName)
                .forEach(System.out::println);

        //Create an infinite stream of even numbers (0, 2, 4, …) and then, eventually print out only the first 20 even numbers from this stream.
        Stream<Integer> infiniteStream = Stream.iterate(0, i -> i + 2);

        // when
        infiniteStream
                .limit(20)
                .collect(Collectors.toList())
                .forEach(System.out::println);


        List<String> strList = new ArrayList<>(Arrays.asList("hello","world","leofo"));
        System.out.println( countWords(strList,'o','h',5));

        Stream strings = Stream.of("A", "good", "day", "to", "write", "some", "Java");

        String concat = (String) strings.reduce("",
                (element1, element2) -> (String) element1 + " "+ (String) element2);
        System.out.println(concat);


    } // end main

    public static int countWords(List<String> words, char c, char d, int len){
        CharSequence cq = c+"";
        CharSequence dq = d+"";

        return (int) words.stream()
                .filter(s-> s.contains(cq))
                .filter(s-> !(s.contains(dq)))
                .filter(s-> (s.length()==len))
                .count();


    }
} // end class ProcessingEmployees

