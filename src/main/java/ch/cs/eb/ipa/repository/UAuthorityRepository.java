package ch.cs.eb.ipa.repository;

import ch.cs.eb.ipa.entity.UAuthority;
import ch.cs.eb.ipa.model.UserAuthority;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.Transactional;
import java.util.List;

/**
 * author: Elijon Berisha
 * date: 10.03.2022
 * class: UAuthorityRepository.java
 *
 * CLASS IS USED TO PERFORM DB OPERATIONS
 * CACHE IS CLEARED BEFORE EVERY TRANSACTION
 * TRANSACTIONAL QUERY EXECUTION IS USED
 * */
@Service
@Transactional
public class UAuthorityRepository {
    EntityManagerFactory emFactory;
    EntityManager emanager;

    // ENTITY MANAGER SET UP FUNCTION USING THE PERSISTENCE UNIT DEFINED IN THE persistence.xml FILE
    public void setup() {
        // PERSISTENCE UNIT IS GIVEN FOR THE ENTITY MANAGER CREATION
        emFactory = Persistence.createEntityManagerFactory("MyPersistenceUnit");
        // FACTORY CREATES ENTITY MANAGER
        emanager = emFactory.createEntityManager();
    }

    // REPOSITORY SETUP: ENTITY MANAGER IS CREATED
    public UAuthorityRepository() {
        setup();
    }

    // ENTITY MANAGER IS CLOSED
    public void quit() {
        if ((emanager != null) && emanager.isOpen()) {
            emanager.close();
        }
        if ((emFactory != null) && emFactory.isOpen()) {
            emFactory.close();
        }
    }

    // ALL AUTHORITIES ARE FETCHED FROM THE DB
    public List<UAuthority> fetchAllAuthorities() {
        List<UAuthority> authorities = null;

        try {
            // CACHE CLEARANCE TO UPDATE CACHE CONTENT
            clearCache();
            emanager.getTransaction().begin();
            // JPQL QUERY IS BUILT BY USING THE NAMED QUERY DEFINED IN THE UAuthority ENTITY
            authorities = emanager.createNamedQuery("UAuthority.findAll").getResultList();
            emanager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            // TRANSACTION IS INVALIDATED
            emanager.getTransaction().rollback();
        }
        return authorities;
    }

    // AUTHORITY ENTRY IS CREATED IN THE DB
    public void createAuthority(UAuthority authority) {
        try {
            // CACHE CLEARANCE TO UPDATE CACHE CONTENT
            clearCache();
            emanager.getTransaction().begin();
            // AUTHORITY IS INSERTED INTO THE DB USING THE persist() METHOD
            emanager.persist(authority);
            emanager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            // TRANSACTION IS INVALIDATED
            emanager.getTransaction().rollback();
        }
    }

    // AUTHORITY IS FETCHED WITH ITS ENUM
    public UAuthority getByAuthorityEnum(UserAuthority authority) {
        List<UAuthority> authorities = null;

        try {
            // CACHE CLEARANCE TO UPDATE CACHE CONTENT
            clearCache();
            emanager.getTransaction().begin();
            // JPQL QUERY IS BUILT WITH THE ROLE PARAMETER; RESULT LIMITED TO 1
            authorities = emanager.createQuery("SELECT u FROM UAuthority u WHERE u.role LIKE :role").setParameter("role", authority).setMaxResults(1).getResultList();
            emanager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            // TRANSACTION IS INVALIDATED
            emanager.getTransaction().rollback();
        }
        if (authorities.isEmpty()) {
            return null;
        }
        return authorities.get(0);
    }

    // POPULATES THE authority TABLE WITH AUTHORITY ENTRIES
    public void populateTable() {
        // IF THERE ARE NO AUTHORITIES, THE THREE AUTHORITIES ARE INSERTED INTO THE DB
        if (fetchAllAuthorities().isEmpty() || fetchAllAuthorities() == null) {
            createAuthority(new UAuthority(UserAuthority.INACTIVE));
            createAuthority(new UAuthority(UserAuthority.EMPLOYEE));
            createAuthority(new UAuthority(UserAuthority.ADMIN));
        }
    }

    // ENTITY MANAGER CACH CLEARANCE
    public void clearCache() {
        emanager.clear();
    }
}