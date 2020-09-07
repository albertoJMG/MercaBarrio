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
import entidades.Producto;
import entidades.Tienda;
import entidades.Pedido;
import entidades.SubPedido;
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
public class SubPedidoJpaController implements Serializable {

    public SubPedidoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SubPedido subPedido) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto producto = subPedido.getProducto();
            if (producto != null) {
                producto = em.getReference(producto.getClass(), producto.getId_producto());
                subPedido.setProducto(producto);
            }
            Tienda tiendaSP = subPedido.getTienda();
            if (tiendaSP != null) {
                tiendaSP = em.getReference(tiendaSP.getClass(), tiendaSP.getId_usuario());
                subPedido.setTienda(tiendaSP);
            }
            Pedido pedido = subPedido.getPedido();
            if (pedido != null) {
                pedido = em.getReference(pedido.getClass(), pedido.getId_pedido());
                subPedido.setPedido(pedido);
            }
            em.persist(subPedido);
            if (producto != null) {
                producto.getSubPedido().add(subPedido);
                producto = em.merge(producto);
            }
            if (tiendaSP != null) {
                tiendaSP.getSubPedido().add(subPedido);
                tiendaSP = em.merge(tiendaSP);
            }
            if (pedido != null) {
                pedido.getSubPedido().add(subPedido);
                pedido = em.merge(pedido);
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

    public void edit(SubPedido subPedido) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SubPedido persistentSubPedido = em.find(SubPedido.class, subPedido.getId_subPedido());
            Producto productoOld = persistentSubPedido.getProducto();
            Producto productoNew = subPedido.getProducto();
            Tienda tiendaSPOld = persistentSubPedido.getTienda();
            Tienda tiendaSPNew = subPedido.getTienda();
            Pedido pedidoOld = persistentSubPedido.getPedido();
            Pedido pedidoNew = subPedido.getPedido();
            if (productoNew != null) {
                productoNew = em.getReference(productoNew.getClass(), productoNew.getId_producto());
                subPedido.setProducto(productoNew);
            }
            if (tiendaSPNew != null) {
                tiendaSPNew = em.getReference(tiendaSPNew.getClass(), tiendaSPNew.getId_usuario());
                subPedido.setTienda(tiendaSPNew);
            }
            if (pedidoNew != null) {
                pedidoNew = em.getReference(pedidoNew.getClass(), pedidoNew.getId_pedido());
                subPedido.setPedido(pedidoNew);
            }
            subPedido = em.merge(subPedido);
            if (productoOld != null && !productoOld.equals(productoNew)) {
                productoOld.getSubPedido().remove(subPedido);
                productoOld = em.merge(productoOld);
            }
            if (productoNew != null && !productoNew.equals(productoOld)) {
                productoNew.getSubPedido().add(subPedido);
                productoNew = em.merge(productoNew);
            }
            if (tiendaSPOld != null && !tiendaSPOld.equals(tiendaSPNew)) {
                tiendaSPOld.getSubPedido().remove(subPedido);
                tiendaSPOld = em.merge(tiendaSPOld);
            }
            if (tiendaSPNew != null && !tiendaSPNew.equals(tiendaSPOld)) {
                tiendaSPNew.getSubPedido().add(subPedido);
                tiendaSPNew = em.merge(tiendaSPNew);
            }
            if (pedidoOld != null && !pedidoOld.equals(pedidoNew)) {
                pedidoOld.getSubPedido().remove(subPedido);
                pedidoOld = em.merge(pedidoOld);
            }
            if (pedidoNew != null && !pedidoNew.equals(pedidoOld)) {
                pedidoNew.getSubPedido().add(subPedido);
                pedidoNew = em.merge(pedidoNew);
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
                Long id = subPedido.getId_subPedido();
                if (findSubPedido(id) == null) {
                    throw new NonexistentEntityException("The subPedido with id " + id + " no longer exists.");
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
            SubPedido subPedido;
            try {
                subPedido = em.getReference(SubPedido.class, id);
                subPedido.getId_subPedido();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The subPedido with id " + id + " no longer exists.", enfe);
            }
            Producto producto = subPedido.getProducto();
            if (producto != null) {
                producto.getSubPedido().remove(subPedido);
                producto = em.merge(producto);
            }
            Tienda tiendaSP = subPedido.getTienda();
            if (tiendaSP != null) {
                tiendaSP.getSubPedido().remove(subPedido);
                tiendaSP = em.merge(tiendaSP);
            }
            Pedido pedido = subPedido.getPedido();
            if (pedido != null) {
                pedido.getSubPedido().remove(subPedido);
                pedido = em.merge(pedido);
            }
            em.remove(subPedido);
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

    public List<SubPedido> findSubPedidoEntities() {
        return findSubPedidoEntities(true, -1, -1);
    }

    public List<SubPedido> findSubPedidoEntities(int maxResults, int firstResult) {
        return findSubPedidoEntities(false, maxResults, firstResult);
    }

    private List<SubPedido> findSubPedidoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SubPedido.class));
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

    public SubPedido findSubPedido(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SubPedido.class, id);
        } finally {
            em.close();
        }
    }

    public int getSubPedidoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SubPedido> rt = cq.from(SubPedido.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
