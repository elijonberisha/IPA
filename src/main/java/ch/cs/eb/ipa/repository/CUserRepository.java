package ch.cs.eb.ipa.repository;

import ch.cs.eb.ipa.config.CUserToUserDetailsMapper;
import ch.cs.eb.ipa.entity.CUser;
import ch.cs.eb.ipa.model.UserAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.Transactional;
import java.util.List;

/**
 * author: Elijon Berisha
 * date: 10.03.2022
 * class: CUserRepository.java
 *
 * CLASS IS USED TO PERFORM DB OPERATIONS
 * CACHE IS CLEARED BEFORE EVERY TRANSACTION
 * TRANSACTIONAL QUERY EXECUTION IS USED
 * */
@Service
@Transactional
public class CUserRepository implements UserDetailsService {
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
    public CUserRepository() {
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

    // FETCHES USER BY ITS EMAIL ADDRESS
    public CUser getByCtsId(int ctsId) {
        List<CUser> userList = null;

        try {
            // CACHE CLEARANCE TO UPDATE CACHE CONTENT
            clearCache();
            emanager.getTransaction().begin();
            // JPQL QUERY IS BUILT WITH THE CTS-ID PARAMETER; RESULT LIMITED TO 1
            userList = emanager.createQuery("SELECT c FROM CUser c WHERE c.cts_id LIKE :cid").setParameter("cid", ctsId).setMaxResults(1).getResultList();
            emanager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            // TRANSACTION IS INVALIDATED
            emanager.getTransaction().rollback();
        }
        if (userList.isEmpty()) {
            return null;
        }
        return userList.get(0);
    }

    // ALL USERS ARE RETURNED FROM THE DB
    public List<CUser> fetchAllUsers() {
        List<CUser> userList = null;

        try {
            // CACHE CLEARANCE TO UPDATE CACHE CONTENT
            clearCache();
            emanager.getTransaction().begin();
            // JPQL QUERY IS BUILT BY USING THE NAMED QUERY DEFINED IN THE CUser ENTITY
            userList = emanager.createNamedQuery("CUser.findAll").getResultList();
            emanager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            // TRANSACTION IS INVALIDATED
            emanager.getTransaction().rollback();
        }
        return userList;
    }

    // CREATES USER
    public void createUser(CUser c_user) {
        try {
            // CACHE CLEARANCE TO UPDATE CACHE CONTENT
            clearCache();
            emanager.getTransaction().begin();
            // USER IS INSERTED INTO THE DB USING THE persist() METHOD
            emanager.persist(c_user);
            emanager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            // TRANSACTION IS INVALIDATED
            emanager.getTransaction().rollback();
        }
    }

    // UPDATES USER
    public void updateUser(CUser user, int id) {
        try {
            // CACHE CLEARANCE TO UPDATE CACHE CONTENT
            clearCache();
            emanager.getTransaction().begin();
            CUser fetchedUser = emanager.find(CUser.class, id);
            if (fetchedUser != null) {
                // USER IS UPDATED IN THE DB USING THE merge() METHOD
                emanager.merge(user);
            }
            emanager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            // TRANSACTION IS INVALIDATED
            emanager.getTransaction().rollback();
        }
    }

    // USER IS FETCHED USING EMAIL
    public CUser getByEmail(String email) {
        List<CUser> userList = null;

        try {
            // CACHE CLEARANCE TO UPDATE CACHE CONTENT
            clearCache();
            emanager.getTransaction().begin();
            // JPQL QUERY IS BUILT WITH THE MAIL PARAMETER; RESULTS LIMITED TO 1
            userList = emanager.createQuery("SELECT c FROM CUser c WHERE c.email LIKE :mail").setParameter("mail", email).setMaxResults(1).getResultList();
            emanager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            // TRANSACTION IS INVALIDATED
            emanager.getTransaction().rollback();
        }
        if (userList.isEmpty()) {
            return null;
        }
        return userList.get(0);
    }

    // ADMIN IS CREATED AT THE START OF THE APPLICATION
    public void createAdmin() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        // IF THE USER WITH 100000 ID IS NULL, ADMIN WITH SAME ID IS CREATED
        if (getByCtsId(100000) == null) {
            CUser user = new CUser(100000, "admin", "1", encoder.encode("admin"), "admin@cognizant.com", new UAuthorityRepository().getByAuthorityEnum(UserAuthority.ADMIN));
            createUser(user);
        }
    }

    // CACHE CLEARANCE FUNCTION
    public void clearCache() {
        emanager.clear();
    }

    // LOAD USER BY USERNAME; USERNAME = CTS-ID
    @Override
    public UserDetails loadUserByUsername(String ctsId) throws UsernameNotFoundException {
        CUser user = getByCtsId(Integer.parseInt(ctsId));
        // CUser IS MAPPED TO USER
        return CUserToUserDetailsMapper.toUserDetails(user);
    }
}
