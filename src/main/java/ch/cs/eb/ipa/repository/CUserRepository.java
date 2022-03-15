package ch.cs.eb.ipa.repository;

import ch.cs.eb.ipa.config.CUserToUserDetailsMapper;
import ch.cs.eb.ipa.entity.CUser;
import ch.cs.eb.ipa.model.UserAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.CacheRetrieveMode;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CUserRepository implements UserDetailsService {
    EntityManagerFactory emFactory;
    EntityManager emanager;

    public void setup() {
        emFactory = Persistence.createEntityManagerFactory("MyPersistenceUnit");
        emanager = emFactory.createEntityManager();
        emanager.setProperty("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);

    }

    public CUserRepository() {
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

    public List<CUser> fetchAllUsers() {
        List<CUser> userList = null;

        try {
            clearCache();
            emanager.getTransaction().begin();
            userList = emanager.createNamedQuery("CUser.findAll").getResultList();
            emanager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            emanager.getTransaction().rollback();
        }
        return userList;
    }

    public void createUser(CUser c_user) {
        try {
            clearCache();
            emanager.getTransaction().begin();
            emanager.persist(c_user);
            emanager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            emanager.getTransaction().rollback();
        }
    }

    public CUser readUser(int id) {
        CUser fetchedUser = null;
        try {
            clearCache();
            emanager.getTransaction().begin();
            fetchedUser = emanager.find(CUser.class, id);
            emanager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            emanager.getTransaction().rollback();
        }
        return fetchedUser;
    }

    public void updateUser(CUser user, int id) {
        try {
            clearCache();
            emanager.getTransaction().begin();
            CUser fetchedUser = emanager.find(CUser.class, id);
            if (fetchedUser != null) {
                emanager.merge(user);
            }
            emanager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            emanager.getTransaction().rollback();
        }
    }

    public void deleteUser(int id) {
        try {
            clearCache();
            emanager.getTransaction().begin();
            CUser fetchedUser = emanager.find(CUser.class, id);
            if (fetchedUser != null) {
                emanager.remove(fetchedUser);
            }
            emanager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            emanager.getTransaction().rollback();
        }
    }

    public CUser getByEmail(String email) {
        List<CUser> userList = null;

        try {
            clearCache();
            emanager.getTransaction().begin();
            userList = emanager.createQuery("SELECT c FROM CUser c WHERE c.email LIKE :mail").setParameter("mail", email).setMaxResults(1).getResultList();
            emanager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            emanager.getTransaction().rollback();
        }
        if (userList.isEmpty()) {
            return null;
        }
        return userList.get(0);
    }

    public void createAdmin() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (getByCtsId(100000) == null) {
            CUser user = new CUser(100000, "admin", "1", encoder.encode("admin"), "admin@cognizant.com", new UAuthorityRepository().getByAuthorityEnum(UserAuthority.ADMIN));
            createUser(user);
        }
    }

    public CUser getByCtsId(int ctsId) {
        List<CUser> userList = null;

        try {
            clearCache();
            emanager.getTransaction().begin();
            userList = emanager.createQuery("SELECT c FROM CUser c WHERE c.cts_id LIKE :cid").setParameter("cid", ctsId).setMaxResults(1).getResultList();
            emanager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            emanager.getTransaction().rollback();
        }
        if (userList.isEmpty()) {
            return null;
        }
        return userList.get(0);
    }


    public void clearCache() {
        emanager.clear();
    }

    @Override
    public UserDetails loadUserByUsername(String ctsId) throws UsernameNotFoundException {
        CUser user = getByCtsId(Integer.parseInt(ctsId));
        return CUserToUserDetailsMapper.toUserDetails(user);
    }
}
