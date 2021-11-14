/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import com.controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.entities.Afiliado;
import com.entities.Comprobante;
import com.entities.Plan;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author EQUIPO
 */
public class ComprobanteJpaController implements Serializable {

    public ComprobanteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Comprobante comprobante) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Afiliado idAfiliado = comprobante.getIdAfiliado();
            if (idAfiliado != null) {
                idAfiliado = em.getReference(idAfiliado.getClass(), idAfiliado.getCodigo());
                comprobante.setIdAfiliado(idAfiliado);
            }
            Plan idPlan = comprobante.getIdPlan();
            if (idPlan != null) {
                idPlan = em.getReference(idPlan.getClass(), idPlan.getCodigo());
                comprobante.setIdPlan(idPlan);
            }
            em.persist(comprobante);
            if (idAfiliado != null) {
                idAfiliado.getComprobanteList().add(comprobante);
                idAfiliado = em.merge(idAfiliado);
            }
            if (idPlan != null) {
                idPlan.getComprobanteList().add(comprobante);
                idPlan = em.merge(idPlan);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Comprobante comprobante) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Comprobante persistentComprobante = em.find(Comprobante.class, comprobante.getCodigo());
            Afiliado idAfiliadoOld = persistentComprobante.getIdAfiliado();
            Afiliado idAfiliadoNew = comprobante.getIdAfiliado();
            Plan idPlanOld = persistentComprobante.getIdPlan();
            Plan idPlanNew = comprobante.getIdPlan();
            if (idAfiliadoNew != null) {
                idAfiliadoNew = em.getReference(idAfiliadoNew.getClass(), idAfiliadoNew.getCodigo());
                comprobante.setIdAfiliado(idAfiliadoNew);
            }
            if (idPlanNew != null) {
                idPlanNew = em.getReference(idPlanNew.getClass(), idPlanNew.getCodigo());
                comprobante.setIdPlan(idPlanNew);
            }
            comprobante = em.merge(comprobante);
            if (idAfiliadoOld != null && !idAfiliadoOld.equals(idAfiliadoNew)) {
                idAfiliadoOld.getComprobanteList().remove(comprobante);
                idAfiliadoOld = em.merge(idAfiliadoOld);
            }
            if (idAfiliadoNew != null && !idAfiliadoNew.equals(idAfiliadoOld)) {
                idAfiliadoNew.getComprobanteList().add(comprobante);
                idAfiliadoNew = em.merge(idAfiliadoNew);
            }
            if (idPlanOld != null && !idPlanOld.equals(idPlanNew)) {
                idPlanOld.getComprobanteList().remove(comprobante);
                idPlanOld = em.merge(idPlanOld);
            }
            if (idPlanNew != null && !idPlanNew.equals(idPlanOld)) {
                idPlanNew.getComprobanteList().add(comprobante);
                idPlanNew = em.merge(idPlanNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = comprobante.getCodigo();
                if (findComprobante(id) == null) {
                    throw new NonexistentEntityException("The comprobante with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Comprobante comprobante;
            try {
                comprobante = em.getReference(Comprobante.class, id);
                comprobante.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The comprobante with id " + id + " no longer exists.", enfe);
            }
            Afiliado idAfiliado = comprobante.getIdAfiliado();
            if (idAfiliado != null) {
                idAfiliado.getComprobanteList().remove(comprobante);
                idAfiliado = em.merge(idAfiliado);
            }
            Plan idPlan = comprobante.getIdPlan();
            if (idPlan != null) {
                idPlan.getComprobanteList().remove(comprobante);
                idPlan = em.merge(idPlan);
            }
            em.remove(comprobante);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Comprobante> findComprobanteEntities() {
        return findComprobanteEntities(true, -1, -1);
    }

    public List<Comprobante> findComprobanteEntities(int maxResults, int firstResult) {
        return findComprobanteEntities(false, maxResults, firstResult);
    }

    private List<Comprobante> findComprobanteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Comprobante.class));
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

    public Comprobante findComprobante(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Comprobante.class, id);
        } finally {
            em.close();
        }
    }

    public int getComprobanteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Comprobante> rt = cq.from(Comprobante.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
