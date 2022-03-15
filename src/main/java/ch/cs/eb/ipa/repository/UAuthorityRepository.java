package ch.cs.eb.ipa.repository;

import ch.cs.eb.ipa.entity.UAuthority;
import ch.cs.eb.ipa.model.UserAuthority;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UAuthorityRepository {
    EntityManagerFactory emFactory;
    EntityManager emanager;

    public void setup() {
        emFactory = Persistence.createEntityManagerFactory("MyPersistenceUnit");
        emanager = emFactory.createEntityManager();
    }

    public UAuthorityRepository() {
        setup();
    }

    public void quit() {
        if ((emanager != null) && emanager.isOpen()) {
            emanager.close();
        }
        if ((emFactory != null) && emFactory.isOpen()) {
            emFactory.close();
        }
    }

    public List<UAuthority> fetchAllAuthorities() {
        List<UAuthority> authorities = null;

        try {
            clearCache();
            emanager.getTransaction().begin();
            authorities = emanager.createNamedQuery("UAuthority.findAll").getResultList();
            emanager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            emanager.getTransaction().rollback();
        }
        return authorities;
    }

    public void createAuthority(UAuthority authority) {
        try {
            clearCache();
            emanager.getTransaction().begin();
            emanager.persist(authority);
            emanager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            emanager.getTransaction().rollback();
        }
    }

    public UAuthority readAuthority(int id) {
        UAuthority fetchedAuthority = null;
        try {
            clearCache();
            emanager.getTransaction().begin();
            fetchedAuthority = emanager.find(UAuthority.class, id);
            emanager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            emanager.getTransaction().rollback();
        }
        return fetchedAuthority;
    }

    public void updateAuthority(UAuthority authority, int id) {
        try {
            clearCache();
            emanager.getTransaction().begin();
            UAuthority fetchedAuthority = emanager.find(UAuthority.class, id);
            if (fetchedAuthority != null) {
                emanager.merge(authority);
            }
            emanager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            emanager.getTransaction().rollback();
        }
    }

    public void deleteAuthority(int id) {
        try {
            clearCache();
            emanager.getTransaction().begin();
            UAuthority fetchedAuthority = emanager.find(UAuthority.class, id);
            if (fetchedAuthority != null) {
                emanager.remove(fetchedAuthority);
            }
            emanager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            emanager.getTransaction().rollback();
        }
    }

    public UAuthority getByAuthorityEnum(UserAuthority authority) {
        List<UAuthority> authorities = null;

        try {
            clearCache();
            emanager.getTransaction().begin();
            authorities = emanager.createQuery("SELECT u FROM UAuthority u WHERE u.role LIKE :role").setParameter("role", authority).setMaxResults(1).getResultList();
            emanager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            emanager.getTransaction().rollback();
        }
        if (authorities.isEmpty()) {
            return null;
        }
        return authorities.get(0);
    }

    public void populateTable() {
        if (fetchAllAuthorities().isEmpty() || fetchAllAuthorities() == null) {
            createAuthority(new UAuthority(UserAuthority.INACTIVE));
            createAuthority(new UAuthority(UserAuthority.EMPLOYEE));
            createAuthority(new UAuthority(UserAuthority.ADMIN));
        }
    }

    public void clearCache() {
        emanager.clear();
    }
}