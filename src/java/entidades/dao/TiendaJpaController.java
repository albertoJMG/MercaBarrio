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
import java.util.ArrayList;
import java.util.List;
import entidades.SubPedido;
import entidades.Tienda;
import entidades.dao.exceptions.NonexistentEntityException;
import entidades.dao.exceptions.RollbackFailureException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Alberto JMG
 */
public class TiendaJpaController implements Serializable {

    public TiendaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tienda tienda) throws RollbackFailureException, Exception {
        if (tienda.getProductos() == null) {
            tienda.setProductos(new ArrayList<Producto>());
        }
        if (tienda.getSubPedido() == null) {
            tienda.setSubPedido(new ArrayList<SubPedido>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Producto> attachedProductos = new ArrayList<Producto>();
            for (Producto productosProductoToAttach : tienda.getProductos()) {
                productosProductoToAttach = em.getReference(productosProductoToAttach.getClass(), productosProductoToAttach.getId_producto());
                attachedProductos.add(productosProductoToAttach);
            }
            tienda.setProductos(attachedProductos);
            List<SubPedido> attachedSubPedido = new ArrayList<SubPedido>();
            for (SubPedido subPedidoSubPedidoToAttach : tienda.getSubPedido()) {
                subPedidoSubPedidoToAttach = em.getReference(subPedidoSubPedidoToAttach.getClass(), subPedidoSubPedidoToAttach.getId_subPedido());
                attachedSubPedido.add(subPedidoSubPedidoToAttach);
            }
            tienda.setSubPedido(attachedSubPedido);
            em.persist(tienda);
            for (Producto productosProducto : tienda.getProductos()) {
                Tienda oldTiendaPOfProductosProducto = productosProducto.getTiendaP();
                productosProducto.setTiendaP(tienda);
                productosProducto = em.merge(productosProducto);
                if (oldTiendaPOfProductosProducto != null) {
                    oldTiendaPOfProductosProducto.getProductos().remove(productosProducto);
                    oldTiendaPOfProductosProducto = em.merge(oldTiendaPOfProductosProducto);
                }
            }
            for (SubPedido subPedidoSubPedido : tienda.getSubPedido()) {
                Tienda oldTiendaSPOfSubPedidoSubPedido = subPedidoSubPedido.getTienda();
                subPedidoSubPedido.setTienda(tienda);
                subPedidoSubPedido = em.merge(subPedidoSubPedido);
                if (oldTiendaSPOfSubPedidoSubPedido != null) {
                    oldTiendaSPOfSubPedidoSubPedido.getSubPedido().remove(subPedidoSubPedido);
                    oldTiendaSPOfSubPedidoSubPedido = em.merge(oldTiendaSPOfSubPedidoSubPedido);
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

    public void edit(Tienda tienda) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tienda persistentTienda = em.find(Tienda.class, tienda.getId_usuario());
            List<Producto> productosOld = persistentTienda.getProductos();
            List<Producto> productosNew = tienda.getProductos();
            List<SubPedido> subPedidoOld = persistentTienda.getSubPedido();
            List<SubPedido> subPedidoNew = tienda.getSubPedido();
            List<Producto> attachedProductosNew = new ArrayList<Producto>();
            for (Producto productosNewProductoToAttach : productosNew) {
                productosNewProductoToAttach = em.getReference(productosNewProductoToAttach.getClass(), productosNewProductoToAttach.getId_producto());
                attachedProductosNew.add(productosNewProductoToAttach);
            }
            productosNew = attachedProductosNew;
            tienda.setProductos(productosNew);
            List<SubPedido> attachedSubPedidoNew = new ArrayList<SubPedido>();
            for (SubPedido subPedidoNewSubPedidoToAttach : subPedidoNew) {
                subPedidoNewSubPedidoToAttach = em.getReference(subPedidoNewSubPedidoToAttach.getClass(), subPedidoNewSubPedidoToAttach.getId_subPedido());
                attachedSubPedidoNew.add(subPedidoNewSubPedidoToAttach);
            }
            subPedidoNew = attachedSubPedidoNew;
            tienda.setSubPedido(subPedidoNew);
            tienda = em.merge(tienda);
            for (Producto productosOldProducto : productosOld) {
                if (!productosNew.contains(productosOldProducto)) {
                    productosOldProducto.setTiendaP(null);
                    productosOldProducto = em.merge(productosOldProducto);
                }
            }
            for (Producto productosNewProducto : productosNew) {
                if (!productosOld.contains(productosNewProducto)) {
                    Tienda oldTiendaPOfProductosNewProducto = productosNewProducto.getTiendaP();
                    productosNewProducto.setTiendaP(tienda);
                    productosNewProducto = em.merge(productosNewProducto);
                    if (oldTiendaPOfProductosNewProducto != null && !oldTiendaPOfProductosNewProducto.equals(tienda)) {
                        oldTiendaPOfProductosNewProducto.getProductos().remove(productosNewProducto);
                        oldTiendaPOfProductosNewProducto = em.merge(oldTiendaPOfProductosNewProducto);
                    }
                }
            }
            for (SubPedido subPedidoOldSubPedido : subPedidoOld) {
                if (!subPedidoNew.contains(subPedidoOldSubPedido)) {
                    subPedidoOldSubPedido.setTienda(null);
                    subPedidoOldSubPedido = em.merge(subPedidoOldSubPedido);
                }
            }
            for (SubPedido subPedidoNewSubPedido : subPedidoNew) {
                if (!subPedidoOld.contains(subPedidoNewSubPedido)) {
                    Tienda oldTiendaSPOfSubPedidoNewSubPedido = subPedidoNewSubPedido.getTienda();
                    subPedidoNewSubPedido.setTienda(tienda);
                    subPedidoNewSubPedido = em.merge(subPedidoNewSubPedido);
                    if (oldTiendaSPOfSubPedidoNewSubPedido != null && !oldTiendaSPOfSubPedidoNewSubPedido.equals(tienda)) {
                        oldTiendaSPOfSubPedidoNewSubPedido.getSubPedido().remove(subPedidoNewSubPedido);
                        oldTiendaSPOfSubPedidoNewSubPedido = em.merge(oldTiendaSPOfSubPedidoNewSubPedido);
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
                Long id = tienda.getId_usuario();
                if (findTienda(id) == null) {
                    throw new NonexistentEntityException("The tienda with id " + id + " no longer exists.");
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
            Tienda tienda;
            try {
                tienda = em.getReference(Tienda.class, id);
                tienda.getId_usuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tienda with id " + id + " no longer exists.", enfe);
            }
            List<Producto> productos = tienda.getProductos();
            for (Producto productosProducto : productos) {
                productosProducto.setTiendaP(null);
                productosProducto = em.merge(productosProducto);
            }
            List<SubPedido> subPedido = tienda.getSubPedido();
            for (SubPedido subPedidoSubPedido : subPedido) {
                subPedidoSubPedido.setTienda(null);
                subPedidoSubPedido = em.merge(subPedidoSubPedido);
            }
            em.remove(tienda);
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

    public List<Tienda> findTiendaEntities() {
        return findTiendaEntities(true, -1, -1);
    }

    public List<Tienda> findTiendaEntities(int maxResults, int firstResult) {
        return findTiendaEntities(false, maxResults, firstResult);
    }

    private List<Tienda> findTiendaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tienda.class));
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

    public Tienda findTienda(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tienda.class, id);
        } finally {
            em.close();
        }
    }

    public int getTiendaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tienda> rt = cq.from(Tienda.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
