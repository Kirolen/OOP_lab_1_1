package lab_gems.services;

import jakarta.persistence.*;
import lab_gems.model.Necklace;
import lab_gems.model.NecklaceGem;

import java.util.List;

public class NecklaceService {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("gemPU");

    public void saveNecklace(Necklace n) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(n);
            tx.commit();
            System.out.println("Necklace saved with ID: " + n.getId());
        } catch (Exception e) {
            if (tx.isActive())
                tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public List<Necklace> getAllNecklaces() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Necklace> query = em.createQuery("SELECT n FROM Necklace n", Necklace.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public Necklace getNecklaceById(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Necklace.class, id);
        } finally {
            em.close();
        }
    }

    public void updateNecklace(Necklace n) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(n);
            tx.commit();
            System.out.println("Necklace updated successfully!");
        } catch (Exception e) {
            if (tx.isActive())
                tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public void deleteNecklace(Necklace n) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.remove(em.contains(n) ? n : em.merge(n));
            tx.commit();
            System.out.println("Necklace deleted successfully!");
        } catch (Exception e) {
            if (tx.isActive())
                tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public void addGemToNecklace(NecklaceGem ng) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(ng);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive())
                tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public void removeGemFromNecklace(int necklaceId, int gemId) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.createQuery("DELETE FROM NecklaceGem ng WHERE ng.necklace.id = :nid AND ng.gem.id = :gid")
                    .setParameter("nid", necklaceId)
                    .setParameter("gid", gemId)
                    .executeUpdate();
            tx.commit();
            System.out.println("Gem removed from necklace successfully!");
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
