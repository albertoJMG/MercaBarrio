/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades.dao;

import entidades.Producto;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.Tienda;
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
public class ProductoJpaController implements Serializable {

    public ProductoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Producto producto) throws RollbackFailureException, Exception {
        if (producto.getSubPedido() == null) {
            producto.setSubPedido(new ArrayList<SubPedido>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tienda tiendaP = producto.getTiendaP();
            if (tiendaP != null) {
                tiendaP = em.getReference(tiendaP.getClass(), tiendaP.getId_usuario());
                producto.setTiendaP(tiendaP);
            }
            List<SubPedido> attachedSubPedido = new ArrayList<SubPedido>();
            for (SubPedido subPedidoSubPedidoToAttach : producto.getSubPedido()) {
                subPedidoSubPedidoToAttach = em.getReference(subPedidoSubPedidoToAttach.getClass(), subPedidoSubPedidoToAttach.getId_subPedido());
                attachedSubPedido.add(subPedidoSubPedidoToAttach);
            }
            producto.setSubPedido(attachedSubPedido);
            em.persist(producto);
            if (tiendaP != null) {
                tiendaP.getProductos().add(producto);
                tiendaP = em.merge(tiendaP);
            }
            for (SubPedido subPedidoSubPedido : producto.getSubPedido()) {
                Producto oldProductoOfSubPedidoSubPedido = subPedidoSubPedido.getProducto();
                subPedidoSubPedido.setProducto(producto);
                subPedidoSubPedido = em.merge(subPedidoSubPedido);
                if (oldProductoOfSubPedidoSubPedido != null) {
                    oldProductoOfSubPedidoSubPedido.getSubPedido().remove(subPedidoSubPedido);
                    oldProductoOfSubPedidoSubPedido = em.merge(oldProductoOfSubPedidoSubPedido);
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

    public void edit(Producto producto) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto persistentProducto = em.find(Producto.class, producto.getId_producto());
            Tienda tiendaPOld = persistentProducto.getTiendaP();
            Tienda tiendaPNew = producto.getTiendaP();
            List<SubPedido> subPedidoOld = persistentProducto.getSubPedido();
            List<SubPedido> subPedidoNew = producto.getSubPedido();
            if (tiendaPNew != null) {
                tiendaPNew = em.getReference(tiendaPNew.getClass(), tiendaPNew.getId_usuario());
                producto.setTiendaP(tiendaPNew);
            }
            List<SubPedido> attachedSubPedidoNew = new ArrayList<SubPedido>();
            for (SubPedido subPedidoNewSubPedidoToAttach : subPedidoNew) {
                subPedidoNewSubPedidoToAttach = em.getReference(subPedidoNewSubPedidoToAttach.getClass(), subPedidoNewSubPedidoToAttach.getId_subPedido());
                attachedSubPedidoNew.add(subPedidoNewSubPedidoToAttach);
            }
            subPedidoNew = attachedSubPedidoNew;
            producto.setSubPedido(subPedidoNew);
            producto = em.merge(producto);
            if (tiendaPOld != null && !tiendaPOld.equals(tiendaPNew)) {
                tiendaPOld.getProductos().remove(producto);
                tiendaPOld = em.merge(tiendaPOld);
            }
            if (tiendaPNew != null && !tiendaPNew.equals(tiendaPOld)) {
                tiendaPNew.getProductos().add(producto);
                tiendaPNew = em.merge(tiendaPNew);
            }
            for (SubPedido subPedidoOldSubPedido : subPedidoOld) {
                if (!subPedidoNew.contains(subPedidoOldSubPedido)) {
                    subPedidoOldSubPedido.setProducto(null);
                    subPedidoOldSubPedido = em.merge(subPedidoOldSubPedido);
                }
            }
            for (SubPedido subPedidoNewSubPedido : subPedidoNew) {
                if (!subPedidoOld.contains(subPedidoNewSubPedido)) {
                    Producto oldProductoOfSubPedidoNewSubPedido = subPedidoNewSubPedido.getProducto();
                    subPedidoNewSubPedido.setProducto(producto);
                    subPedidoNewSubPedido = em.merge(subPedidoNewSubPedido);
                    if (oldProductoOfSubPedidoNewSubPedido != null && !oldProductoOfSubPedidoNewSubPedido.equals(producto)) {
                        oldProductoOfSubPedidoNewSubPedido.getSubPedido().remove(subPedidoNewSubPedido);
                        oldProductoOfSubPedidoNewSubPedido = em.merge(oldProductoOfSubPedidoNewSubPedido);
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
                Long id = producto.getId_producto();
                if (findProducto(id) == null) {
                    throw new NonexistentEntityException("The producto with id " + id + " no longer exists.");
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
            Producto producto;
            try {
                producto = em.getReference(Producto.class, id);
                producto.getId_producto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The producto with id " + id + " no longer exists.", enfe);
            }
            Tienda tiendaP = producto.getTiendaP();
            if (tiendaP != null) {
                tiendaP.getProductos().remove(producto);
                tiendaP = em.merge(tiendaP);
            }
            List<SubPedido> subPedido = producto.getSubPedido();
            for (SubPedido subPedidoSubPedido : subPedido) {
                subPedidoSubPedido.setProducto(null);
                subPedidoSubPedido = em.merge(subPedidoSubPedido);
            }
            em.remove(producto);
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

    public List<Producto> findProductoEntities() {
        return findProductoEntities(true, -1, -1);
    }

    public List<Producto> findProductoEntities(int maxResults, int firstResult) {
        return findProductoEntities(false, maxResults, firstResult);
    }

    private List<Producto> findProductoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Producto.class));
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

    public Producto findProducto(Long id) {
        EntityManager em = getEntityManager();
//        if (id == null){
//            id = 1L;
//        }
        try {
            return em.find(Producto.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Producto> rt = cq.from(Producto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
