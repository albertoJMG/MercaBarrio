/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades.dao;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.Cliente;
import entidades.Envio;
import entidades.Pedido;
import entidades.SubPedido;
import entidades.dao.exceptions.NonexistentEntityException;
import entidades.dao.exceptions.RollbackFailureException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Alberto JMG
 */
public class PedidoJpaController implements Serializable {

    public PedidoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pedido pedido) throws RollbackFailureException, Exception {
        if (pedido.getSubPedido() == null) {
            pedido.setSubPedido(new ArrayList<SubPedido>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente cliente = pedido.getCliente();
            if (cliente != null) {
                cliente = em.getReference(cliente.getClass(), cliente.getId_usuario());
                pedido.setCliente(cliente);
            }
            Envio envio = pedido.getEnvio();
            if (envio != null) {
                envio = em.getReference(envio.getClass(), envio.getId_envio());
                pedido.setEnvio(envio);
            }
            List<SubPedido> attachedSubPedido = new ArrayList<SubPedido>();
            for (SubPedido subPedidoSubPedidoToAttach : pedido.getSubPedido()) {
                subPedidoSubPedidoToAttach = em.getReference(subPedidoSubPedidoToAttach.getClass(), subPedidoSubPedidoToAttach.getId_subPedido());
                attachedSubPedido.add(subPedidoSubPedidoToAttach);
            }
            pedido.setSubPedido(attachedSubPedido);
            em.persist(pedido);
            if (cliente != null) {
                cliente.getPedidos().add(pedido);
                cliente = em.merge(cliente);
            }
            if (envio != null) {
                Pedido oldPedidoEOfEnvio = envio.getPedidoE();
                if (oldPedidoEOfEnvio != null) {
                    oldPedidoEOfEnvio.setEnvio(null);
                    oldPedidoEOfEnvio = em.merge(oldPedidoEOfEnvio);
                }
                envio.setPedidoE(pedido);
                envio = em.merge(envio);
            }
            for (SubPedido subPedidoSubPedido : pedido.getSubPedido()) {
                Pedido oldPedidoOfSubPedidoSubPedido = subPedidoSubPedido.getPedido();
                subPedidoSubPedido.setPedido(pedido);
                subPedidoSubPedido = em.merge(subPedidoSubPedido);
                if (oldPedidoOfSubPedidoSubPedido != null) {
                    oldPedidoOfSubPedidoSubPedido.getSubPedido().remove(subPedidoSubPedido);
                    oldPedidoOfSubPedidoSubPedido = em.merge(oldPedidoOfSubPedidoSubPedido);
                }
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

    public void edit(Pedido pedido) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pedido persistentPedido = em.find(Pedido.class, pedido.getId_pedido());
            Cliente clienteOld = persistentPedido.getCliente();
            Cliente clienteNew = pedido.getCliente();
            Envio envioOld = persistentPedido.getEnvio();
            Envio envioNew = pedido.getEnvio();
            List<SubPedido> subPedidoOld = persistentPedido.getSubPedido();
            List<SubPedido> subPedidoNew = pedido.getSubPedido();
            if (clienteNew != null) {
                clienteNew = em.getReference(clienteNew.getClass(), clienteNew.getId_usuario());
                pedido.setCliente(clienteNew);
            }
            if (envioNew != null) {
                envioNew = em.getReference(envioNew.getClass(), envioNew.getId_envio());
                pedido.setEnvio(envioNew);
            }
            List<SubPedido> attachedSubPedidoNew = new ArrayList<SubPedido>();
            for (SubPedido subPedidoNewSubPedidoToAttach : subPedidoNew) {
                subPedidoNewSubPedidoToAttach = em.getReference(subPedidoNewSubPedidoToAttach.getClass(), subPedidoNewSubPedidoToAttach.getId_subPedido());
                attachedSubPedidoNew.add(subPedidoNewSubPedidoToAttach);
            }
            subPedidoNew = attachedSubPedidoNew;
            pedido.setSubPedido(subPedidoNew);
            pedido = em.merge(pedido);
            if (clienteOld != null && !clienteOld.equals(clienteNew)) {
                clienteOld.getPedidos().remove(pedido);
                clienteOld = em.merge(clienteOld);
            }
            if (clienteNew != null && !clienteNew.equals(clienteOld)) {
                clienteNew.getPedidos().add(pedido);
                clienteNew = em.merge(clienteNew);
            }
            if (envioOld != null && !envioOld.equals(envioNew)) {
                envioOld.setPedidoE(null);
                envioOld = em.merge(envioOld);
            }
            if (envioNew != null && !envioNew.equals(envioOld)) {
                Pedido oldPedidoEOfEnvio = envioNew.getPedidoE();
                if (oldPedidoEOfEnvio != null) {
                    oldPedidoEOfEnvio.setEnvio(null);
                    oldPedidoEOfEnvio = em.merge(oldPedidoEOfEnvio);
                }
                envioNew.setPedidoE(pedido);
                envioNew = em.merge(envioNew);
            }
            for (SubPedido subPedidoOldSubPedido : subPedidoOld) {
                if (!subPedidoNew.contains(subPedidoOldSubPedido)) {
                    subPedidoOldSubPedido.setPedido(null);
                    subPedidoOldSubPedido = em.merge(subPedidoOldSubPedido);
                }
            }
            for (SubPedido subPedidoNewSubPedido : subPedidoNew) {
                if (!subPedidoOld.contains(subPedidoNewSubPedido)) {
                    Pedido oldPedidoOfSubPedidoNewSubPedido = subPedidoNewSubPedido.getPedido();
                    subPedidoNewSubPedido.setPedido(pedido);
                    subPedidoNewSubPedido = em.merge(subPedidoNewSubPedido);
                    if (oldPedidoOfSubPedidoNewSubPedido != null && !oldPedidoOfSubPedidoNewSubPedido.equals(pedido)) {
                        oldPedidoOfSubPedidoNewSubPedido.getSubPedido().remove(subPedidoNewSubPedido);
                        oldPedidoOfSubPedidoNewSubPedido = em.merge(oldPedidoOfSubPedidoNewSubPedido);
                    }
                }
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
                Long id = pedido.getId_pedido();
                if (findPedido(id) == null) {
                    throw new NonexistentEntityException("The pedido with id " + id + " no longer exists.");
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
            Pedido pedido;
            try {
                pedido = em.getReference(Pedido.class, id);
                pedido.getId_pedido();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pedido with id " + id + " no longer exists.", enfe);
            }
            Cliente cliente = pedido.getCliente();
            if (cliente != null) {
                cliente.getPedidos().remove(pedido);
                cliente = em.merge(cliente);
            }
            Envio envio = pedido.getEnvio();
            if (envio != null) {
                envio.setPedidoE(null);
                envio = em.merge(envio);
            }
            List<SubPedido> subPedido = pedido.getSubPedido();
            for (SubPedido subPedidoSubPedido : subPedido) {
                subPedidoSubPedido.setPedido(null);
                subPedidoSubPedido = em.merge(subPedidoSubPedido);
            }
            em.remove(pedido);
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

    public List<Pedido> findPedidoEntities() {
        return findPedidoEntities(true, -1, -1);
    }

    public List<Pedido> findPedidoEntities(int maxResults, int firstResult) {
        return findPedidoEntities(false, maxResults, firstResult);
    }

    private List<Pedido> findPedidoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pedido.class));
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

    public Pedido findPedido(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pedido.class, id);
        } finally {
            em.close();
        }
    }

    public int getPedidoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pedido> rt = cq.from(Pedido.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
