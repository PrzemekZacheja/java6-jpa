import com.sun.security.jgss.GSSUtil;
import entity.Alcohol;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class EntityDemo {
    static EntityManagerFactory factory = Persistence.createEntityManagerFactory("java6"); // jednostka persystencji ustawiana w pliku persistence.xml
    static EntityManager em = factory.createEntityManager();
    static Scanner scanner = new Scanner(System.in);

    static int menu() {
        System.out.println("1. Dodaj encję. \n" +
                "2. Wyświetl wszyskie encje. \n" +
                "3. Edytuj encję \n" +
                "4. Usuń encję \n" +
                "5. Odłączanie encji \n" +
                "6. Mergowanie \n" +
                "7. Znajdź encję wg nazwy \n" +
                "0. Wyjście");
        while (!scanner.hasNextInt()) {
            System.out.println("Wybierz numer polecenia");
        }
        int option = scanner.nextInt();
        scanner.nextLine();
        return option;
    }

    static void addEntity() {

        System.out.println("Podaj nazwę");
        String name = scanner.nextLine();
        System.out.println("Podaj kategorię");
        String category = scanner.nextLine();
        System.out.println(" Podaj zawartość %");
        BigDecimal volage = scanner.nextBigDecimal();
        System.out.println("Podaj pojemność");
        BigDecimal capacity = scanner.nextBigDecimal();

        Alcohol beer = new Alcohol();
        //        beer.setId(1);
        beer.setCapacity(capacity);
        beer.setName(name);
        beer.setVoltage(volage);
        beer.setCategory(category);

        em.getTransaction().begin();  // rozpoczęcie transakcji
        em.persist(beer);
        em.getTransaction().commit();  //wypuszczenie do bazy danych

//        em.close(); // można zamknąć ż po zakończeniu wszystkich operacji.
    }

    static void printAllEntities() {
        List list = em.createQuery("from Alcohol ").getResultList();
        list.forEach(System.out::println);
    }

    static void deleteEntity() {
        System.out.println("Podaj nr id jaki chcesz usunąć");
        long id = scanner.nextLong();
        Alcohol entity = em.find(Alcohol.class, id);

        em.getTransaction().begin();
        em.remove(entity);
        em.getTransaction().commit();
    }

    static void editEntity() {
        System.out.println("Podaj id edytowanej encji");
        long id = scanner.nextLong();

        em.getTransaction().begin(); // rozpoczęcie operacji na encji

        Alcohol entity = em.find(Alcohol.class, id);
        System.out.println(entity);
        scanner.nextLine();
        System.out.println("Podaj nową nazwę");
        String name = scanner.nextLine();
        System.out.println("Podaj pojemność");
        BigDecimal capacity = scanner.nextBigDecimal();
        System.out.println("Podaj zawartość alko w %");
        BigDecimal voltage = scanner.nextBigDecimal();

        entity.setName(name);
        entity.setCapacity(capacity);
        entity.setVoltage(voltage);

        em.getTransaction().commit();  // zamykanie trnsakcji aby wysłać ane do bazy
    }

    private static void detachEntity() {
        System.out.println("Podaj id odłączanej encji");
        long id = scanner.nextLong();

        em.getTransaction().begin();
        Alcohol entity = em.find(Alcohol.class, id);
        em.detach(entity);
        entity.setName(" ");
        em.getTransaction().commit();

        System.out.println(entity);
    }

    static void mergeEntty() {
        System.out.println("Poda id mergowanej encji");
        long id = scanner.nextLong();
        scanner.nextLine();
        Alcohol entity = new Alcohol();
        entity.setId(id);
        System.out.println("Podaj nową nazwę");
        String name = scanner.nextLine();
        entity.setName(name);

        em.getTransaction().begin();
        Alcohol newEntity = em.merge(entity);
        em.getTransaction().commit();
        System.out.println(newEntity);

    }

    static void findByName(){
        System.out.println("Jakiej nazwy szukasz?");
        String nameTemplate = scanner.nextLine();
        List<Alcohol> list = em.createQuery("select a from Alcohol a where a.name like :template" ,Alcohol.class).setParameter("template", nameTemplate).getResultList();
        list.forEach(System.out::println);
    }

    public static void main(String[] args) {
        while (true) {
            switch (menu()) {
                case 1:
                    addEntity();
                    break;
                case 2:
                    printAllEntities();
                    break;
                case 3:
                    editEntity();
                    break;
                case 4:
                    deleteEntity();
                    break;
                case 5:
                    detachEntity();
                    break;
                case 6:
                    mergeEntty();
                    break;
                case 7:
                    findByName();
                    break;
                case 0:
                    em.close(); // zakończenie fabryki
                    return;
            }

        }
    }


}
