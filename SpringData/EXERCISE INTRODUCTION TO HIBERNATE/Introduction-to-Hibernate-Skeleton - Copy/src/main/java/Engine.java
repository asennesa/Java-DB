import entities.Address;
import entities.Employee;
import entities.Project;
import entities.Town;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

        //Ex 9
        // findLatest10Projects();

        //Ex 10
        //increaseSalaries();

        //Ex 11
        try {
            removeTowns();
        } catch (IOException e) {
            e.printStackTrace();
        }

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

    private void findLatest10Projects() {
        List<Project> projects = this.entityManager
                .createQuery("SELECT p FROM Project AS p ORDER BY p.startDate DESC", Project.class)
                .setMaxResults(10).getResultList().stream()
                .sorted((p1,p2)->p1.getName().compareTo(p2.getName())).collect(Collectors.toList());

        projects.forEach(e -> {
            try {
                String endDate = e.getEndDate().toString();
                System.out.println(String.format("Project name: %s" +
                                "%n\tProject Description: %s" +
                                "%n\tProject Start Date:%s" +
                                "%n\tProject End Date: %s"
                        , e.getName(), e.getDescription()
                        , e.getStartDate().toString()
                        , endDate));
            }catch (NullPointerException npe){
                String endDate = "null";
                System.out.println(String.format("Project name: %s" +
                                "%n\tProject Description: %s" +
                                "%n\tProject Start Date:%s" +
                                "%n\tProject End Date: %s"
                        , e.getName(), e.getDescription()
                        , e.getStartDate().toString()
                        , endDate));
            }



        });

    }

    private void increaseSalaries(){
        List<Employee> employees = this.entityManager.createQuery("SELECT e FROM Employee AS e " +
                "WHERE e.department.name " +
                "IN('Engineering','Tool Design','Marketing','Information Services')",Employee.class).getResultList();
        this.entityManager.getTransaction().begin();
        for (Employee employee : employees) {
            BigDecimal twelvePercentIncrease = new BigDecimal("1.12");
            this.entityManager.detach(employee);
            employee.setSalary(employee.getSalary().multiply(twelvePercentIncrease));
            this.entityManager.merge(employee);
            System.out.println(String.format("%s %s ($%.2f)",employee.getFirstName()
                    ,employee.getLastName(),employee.getSalary()));
        }
        this.entityManager.flush();
        this.entityManager.getTransaction().commit();




    }

    private void removeTowns() throws IOException {
        System.out.println("Enter Town Name: ");
        String inputTown = reader.readLine();
        Town town =  this.entityManager.createQuery("SELECT t FROM Town AS t WHERE t.name =:tname",Town.class)
                .setParameter("tname",inputTown).getSingleResult();
        int townId = town.getId();
        List <Address> addresses = this.entityManager.createQuery("SELECT a FROM Address AS a " +
                "WHERE a.town.id =:tId",Address.class).setParameter("tId",townId).getResultList();

        List<Employee> employees = this.entityManager.createQuery("Select e FROM Employee AS e" +
                " JOIN Address as a ON a.id=e.address.id " +
                "JOIN Town as t ON t.id=a.town.id " +
                "WHERE t.id =:tId",Employee.class).setParameter("tId",townId).getResultList();

        this.entityManager.getTransaction().begin();
        for (Employee e : employees) {
            this.entityManager.detach(e);
            e.setAddress(null);
            this.entityManager.merge(e);
        }
        for (Address a : addresses) {
            this.entityManager.remove(a);
        }
        this.entityManager.remove(town);
        this.entityManager.flush();
        this.entityManager.getTransaction().commit();

        System.out.println(String.format("%d address in %s deleted",addresses.size(),town.getName()));

    }




}
