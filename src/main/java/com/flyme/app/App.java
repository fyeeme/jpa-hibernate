package com.flyme.app;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.flyme.app.entity.Geek;
import com.flyme.app.entity.Person;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {
    private final static Logger logger = LoggerFactory.getLogger("Flyme");
    private EntityManagerFactory entityManagerFactory = null;
    private EntityManager entityManager = null;

    public static void main(String[] args) {
        App app = new App();

        app.initEntityManager();
        app.persistPerson();
        app.persistGeek();
        app.queryGeeks();
    }

    private void persistPerson() {
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            Person person = new Person();
            person.setAge(18);
            person.setFirstName("Allen");
            person.setLastName("Lau");
            person.setAddress("浙江省滨江区");

            entityManager.persist(person);
            transaction.commit();

        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
    }

    private void queryGeeks(){
      List<Geek> list =   entityManager.createQuery("from Geek", Geek.class).getResultList();
        list.forEach(geek-> {
            logger.info( geek.getFirstName() + " " + geek.getLastName()+ "\n");
        });
    }

    void initEntityManager() {
        entityManagerFactory = Persistence.createEntityManagerFactory("JpaHibernate");
        entityManager = entityManagerFactory.createEntityManager();
        logger.info("Initialization entityManager");
    }

    /**
     * 为了代码整洁省略try catch
     */
    void persistGeek() {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Geek geek = new Geek();

        geek.setFirstName("Gavin");
        geek.setLastName("Coffee");
        geek.setPragramLanage("Java");
        geek.setAge(18);
        geek.setAddress("New York");

        entityManager.persist(geek);

        geek = new Geek();
        geek.setFirstName("Thomas");
        geek.setLastName("Micro");
        geek.setPragramLanage("C#");

        entityManager.persist(geek);

        geek = new Geek();
        geek.setFirstName("Christian");
        geek.setLastName("Cup");
        geek.setPragramLanage("Java");
        entityManager.persist(geek);

        logger.info(entityManager.createQuery("select g from Geek g where id =2").getResultList()+ "");

        transaction.commit();
    }

}
