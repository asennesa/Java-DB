import entities.Address;
import entities.Employee;
import entities.Town;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class Engine implements Runnable {

    private final EntityManager entityManager;
    private final BufferedReader reader;


    public Engine(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run() {
        //Ex 2
        //  this.removeObjectsEx();

        /* Ex 3
        try {
            this.containsEmployeeEx();
        } catch (IOException e) {
            e.printStackTrace();
        }
         */

        //Ex 4
        //employeesWithSalaryOver50000();

        //Ex 5
        //employeesFromDepartment();

        /*Ex 6
        try {
            addingANewAddressAndUpdatingEmployee();
        } catch (IOException e) {
            e.printStackTrace();
        }
         */

        //Ex 7
        //Изисква се чиста база за получаване на верният отговор
        //addressesWithEmployeeCount();

        //Ex 8
        /*try {
            getEmployeeWithProject();
        } catch (IOException e) {
            e.printStackTrace();
        }
         */

    }

    private void removeObjectsEx() {
        List<Town> allTowns = this.entityManager
                .createQuery("SELECT t FROM Town AS t ", Town.class)
                .getResultList();

        this.entityManager.getTransaction().begin();
        /*Then transform the names of all ATTACHED towns
          to LOWERCASE and save them to the database.*/
        for (Town town : allTowns) {
            this.entityManager.detach(town);
            if (town.getName().length() < 5) {
                town.setName(town.getName().toLowerCase());
                this.entityManager.merge(town);
            }
        }
        this.entityManager.flush();
        this.entityManager.getTransaction().commit();

    }

    private void containsEmployeeEx() throws IOException {
        System.out.println("Enter employee full name:");
        String fullName = this.reader.readLine();
        try {
            Employee employee = this.entityManager
                    .createQuery("SELECT e from Employee AS e " +
                            "WHERE concat(e.firstName,' ',e.lastName) =:name", Employee.class)
                    .setParameter("name", fullName)
                    .getSingleResult();
            System.out.println("Yes");
        } catch (NoResultException nre) {
            System.out.println("No");
        }
    }

    private void employeesWithSalaryOver50000() {
        List<Employee> employees = this.entityManager
                .createQuery("SELECT e FROM Employee AS e WHERE e.salary >50000", Employee.class)
                .getResultList();
        employees.forEach(e -> System.out.println(e.getFirstName()));

    }

    private void employeesFromDepartment() {
        List<Employee> employees = this.entityManager.createQuery("SELECT e FROM Employee AS e " +
                "WHERE e.department.name = 'Research and Development' ORDER BY e.salary,e.id", Employee.class)
                .getResultList();
        employees.forEach(e -> System.out.println(String.format("%s %s from %s - $%.2f", e.getFirstName(), e.getLastName(),
                e.getDepartment().getName(), e.getSalary())));

    }

    private void addingANewAddressAndUpdatingEmployee() throws IOException {
        System.out.println("Enter employee last name: ");
        String lastName = reader.readLine();

        Address address = new Address();
        address.setText("Vitoshka 15");
        this.entityManager.getTransaction().begin();
        this.entityManager.persist(address);
        this.entityManager.getTransaction().commit();

        Employee employee = this.entityManager
                .createQuery("SELECT e FROM Employee AS e " +
                        "WHERE e.lastName =:name", Employee.class)
                .setParameter("name", lastName)
                .getSingleResult();

        this.entityManager.getTransaction().begin();
        this.entityManager.detach(employee);
        employee.setAddress(address);
        this.entityManager.merge(employee);
        this.entityManager.flush();
        this.entityManager.getTransaction().commit();

    }

    private void addressesWithEmployeeCount() {
        List<Address> addresses = this.entityManager.createQuery(
                "SELECT a FROM Address AS a  ", Address.class).getResultList();

        addresses.stream()
                .sorted((a1, a2) -> a2.getEmployees().size() - a1.getEmployees().size())
                .limit(10)
                .forEach(e -> System.out.println(String.format("%s, %s - %d employees"
                        , e.getText()
                        , e.getTown().getName()
                        , e.getEmployees().size())));
    }

    private void getEmployeeWithProject() throws IOException {
        System.out.println("Enter employee id:");
        String inputId = reader.readLine();
        Employee emp =
                this.entityManager.createQuery("SELECT e FROM Employee  as e " +
                        "WHERE e.id=:id", Employee.class)
                        .setParameter("id", Integer.parseInt(inputId)).getSingleResult();

        System.out.println(String.format("%s %s - %s"
                , emp.getFirstName()
                , emp.getLastName()
                , emp.getJobTitle()));

        emp.getProjects()
                .stream()
                .sorted((p1, p2) -> p1.getName().compareTo(p2.getName()))
                .forEach(e -> System.out.println("\t" + e.getName()));
    }

    

}
