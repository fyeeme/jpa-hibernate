package com.flyme.app;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.flyme.app.entity.Geek;
import com.flyme.app.entity.IdCard;
import com.flyme.app.entity.Person;
import com.flyme.app.entity.Phone;
import com.flyme.app.entity.Project;

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
        app.addPhone();
        app.createProject();
        app.queryGeeks();
    }

    private void createProject() {
        List<Geek> list = entityManager.createQuery("from Geek where pragramLanage = :pl", Geek.class).setParameter("pl", "Java").getResultList();

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Project project = new Project();
        project.setName("Java project");
        list.forEach(geek->{
            project.getGeeks().add(geek);
            geek.getProjects().add(project);
        });
        entityManager.persist(project);
        transaction.commit();

    }

    private void addPhone() {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Person> query = builder.createQuery(Person.class);
        Root<Person> root = query.from(Person.class);
        query.where(builder.and(
            builder.equal(root.get("firstName"), "Allen"),
            builder.equal(root.get("lastName"), "Lau")
        ));

        List<Person> result = entityManager.createQuery(query).getResultList();
        result.forEach(person->{
            Phone phone = new Phone();
            phone.setNumber("1666666666");
            entityManager.persist(phone);
            person.getPhones().add(phone);
            phone.setPerson(person);
        });
        transaction.commit();
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

            IdCard idCard = new IdCard();
            idCard.setIdNumber("100000100000100");
            idCard.setIssueDate(new Date());

            person.setIdCard(idCard);

            entityManager.persist(person);
            transaction.commit();

        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            logger.error("Error occurred:", e);
        }
    }

    private void queryGeeks() {
        List<Geek> list = entityManager.createQuery("from Geek", Geek.class).getResultList();
        list.forEach(geek -> {
            logger.info(geek.getFirstName() + " " + geek.getLastName() + geek.getProjects()+"\n");
        });

        List<Project> projectList = entityManager.createQuery("from Project", Project.class).getResultList();

        projectList.forEach(project->{
            logger.info(project.toString()+ "\n");
        });

        Person person = entityManager.find(Person.class, 1L);

        logger.info("Find persion : "+ person);

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

        transaction.commit();
    }

}
