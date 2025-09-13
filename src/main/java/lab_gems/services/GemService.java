package lab_gems.services;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import lab_gems.model.Gem;

public class GemService {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("gemPU");

    public void saveGem(Gem gem) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(gem);
            tx.commit();
            System.out.println("Gem saved with ID: " + gem.getId());
        } catch (Exception e) {
            if (tx.isActive())
                tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public List<Gem> getAllGems() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Gem> query = em.createQuery("SELECT g FROM Gem g", Gem.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public Gem getGemById(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Gem.class, id);
        } finally {
            em.close();
        }
    }

    public void updateGem(Gem gem) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(gem);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive())
                tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public void deleteGem(int id) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            Gem gem = em.find(Gem.class, id);
            if (gem == null) {
                System.out.println("Gem with ID " + id + " not found!");
                return;
            }
            tx.begin();
            em.remove(gem);
            tx.commit();
            System.out.println("Gem with ID " + id + " deleted successfully.");
        } catch (Exception e) {
            if (tx.isActive())
                tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public void close() {
        emf.close();
    }
}
