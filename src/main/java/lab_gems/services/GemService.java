package lab_gems.services;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import lab_gems.model.Gem;
import lab_gems.types.GemType;

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

            em.createQuery("DELETE FROM NecklaceGem ng WHERE ng.gem = :gem")
                    .setParameter("gem", gem)
                    .executeUpdate();

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

    public List<Gem> sortGemsBy(String field, boolean ascending) {
        EntityManager em = emf.createEntityManager();
        try {
            String queryStr = "SELECT g FROM Gem g ORDER BY g." + field + (ascending ? " ASC" : " DESC");
            TypedQuery<Gem> query = em.createQuery(queryStr, Gem.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Gem> filterGemsByTransparency(double minTransparency, double maxTransparency) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Gem> query = em.createQuery(
                    "SELECT g FROM Gem g WHERE g.transparency BETWEEN :minTransp AND :maxTransp", Gem.class);
            query.setParameter("minTransp", minTransparency);
            query.setParameter("maxTransp", maxTransparency);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Gem> getGemsByCriteria(
            GemType type,
            double minWeight,
            double maxWeight,
            double minPrice,
            double maxPrice,
            double minTransparency,
            double maxTransparency) {
        EntityManager em = emf.createEntityManager();
        try {
            String jpql = "SELECT g FROM Gem g WHERE g.type = :type " +
                    "AND g.weightCarat BETWEEN :minWeight AND :maxWeight " +
                    "AND g.pricePerCarat BETWEEN :minPrice AND :maxPrice " +
                    "AND g.transparency BETWEEN :minTrans AND :maxTrans";

            TypedQuery<Gem> query = em.createQuery(jpql, Gem.class);
            query.setParameter("type", type);
            query.setParameter("minWeight", minWeight);
            query.setParameter("maxWeight", maxWeight);
            query.setParameter("minPrice", minPrice);
            query.setParameter("maxPrice", maxPrice);
            query.setParameter("minTrans", minTransparency);
            query.setParameter("maxTrans", maxTransparency);

            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public void close() {
        emf.close();
    }
}
