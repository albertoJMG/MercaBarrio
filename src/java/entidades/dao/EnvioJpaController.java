/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades.dao;

import entidades.Envio;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.Pedido;
import entidades.dao.exceptions.NonexistentEntityException;
import entidades.dao.exceptions.RollbackFailureException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Alberto JMG
 */
public class EnvioJpaController implements Serializable {

    public EnvioJpaController( EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Envio envio) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pedido pedidoE = envio.getPedidoE();
            if (pedidoE != null) {
                pedidoE = em.getReference(pedidoE.getClass(), pedidoE.getId_pedido());
                envio.setPedidoE(pedidoE);
            }
            em.persist(envio);
            if (pedidoE != null) {
                Envio oldEnvioOfPedidoE = pedidoE.getEnvio();
                if (oldEnvioOfPedidoE != null) {
                    oldEnvioOfPedidoE.setPedidoE(null);
                    oldEnvioOfPedidoE = em.merge(oldEnvioOfPedidoE);
                }
                pedidoE.setEnvio(envio);
                pedidoE = em.merge(pedidoE);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            try {
                em.getTransaction().rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Envio envio) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Envio persistentEnvio = em.find(Envio.class, envio.getId_envio());
            Pedido pedidoEOld = persistentEnvio.getPedidoE();
            Pedido pedidoENew = envio.getPedidoE();
            if (pedidoENew != null) {
                pedidoENew = em.getReference(pedidoENew.getClass(), pedidoENew.getId_pedido());
                envio.setPedidoE(pedidoENew);
            }
            envio = em.merge(envio);
            if (pedidoEOld != null && !pedidoEOld.equals(pedidoENew)) {
                pedidoEOld.setEnvio(null);
                pedidoEOld = em.merge(pedidoEOld);
            }
            if (pedidoENew != null && !pedidoENew.equals(pedidoEOld)) {
                Envio oldEnvioOfPedidoE = pedidoENew.getEnvio();
                if (oldEnvioOfPedidoE != null) {
                    oldEnvioOfPedidoE.setPedidoE(null);
                    oldEnvioOfPedidoE = em.merge(oldEnvioOfPedidoE);
                }
                pedidoENew.setEnvio(envio);
                pedidoENew = em.merge(pedidoENew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            try {
                em.getTransaction().rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = envio.getId_envio();
                if (findEnvio(id) == null) {
                    throw new NonexistentEntityException("The envio with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Envio envio;
            try {
                envio = em.getReference(Envio.class, id);
                envio.getId_envio();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The envio with id " + id + " no longer exists.", enfe);
            }
            Pedido pedidoE = envio.getPedidoE();
            if (pedidoE != null) {
                pedidoE.setEnvio(null);
                pedidoE = em.merge(pedidoE);
            }
            em.remove(envio);
            em.getTransaction().commit();
        } catch (Exception ex) {
            try {
                em.getTransaction().rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Envio> findEnvioEntities() {
        return findEnvioEntities(true, -1, -1);
    }

    public List<Envio> findEnvioEntities(int maxResults, int firstResult) {
        return findEnvioEntities(false, maxResults, firstResult);
    }

    private List<Envio> findEnvioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Envio.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Envio findEnvio(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Envio.class, id);
        } finally {
            em.close();
        }
    }

    public int getEnvioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Envio> rt = cq.from(Envio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
