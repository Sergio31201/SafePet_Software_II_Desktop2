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
import com.entities.Historial;
import com.entities.Veterinaria;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Equipo Malware
 */
public class VeterinariaJpaController implements Serializable {

    public VeterinariaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Veterinaria veterinaria) {
        if (veterinaria.getHistorialList() == null) {
            veterinaria.setHistorialList(new ArrayList<Historial>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Historial> attachedHistorialList = new ArrayList<Historial>();
            for (Historial historialListHistorialToAttach : veterinaria.getHistorialList()) {
                historialListHistorialToAttach = em.getReference(historialListHistorialToAttach.getClass(), historialListHistorialToAttach.getCodigo());
                attachedHistorialList.add(historialListHistorialToAttach);
            }
            veterinaria.setHistorialList(attachedHistorialList);
            em.persist(veterinaria);
            for (Historial historialListHistorial : veterinaria.getHistorialList()) {
                Veterinaria oldVeterinariaAsociadaCodigoOfHistorialListHistorial = historialListHistorial.getVeterinariaAsociadaCodigo();
                historialListHistorial.setVeterinariaAsociadaCodigo(veterinaria);
                historialListHistorial = em.merge(historialListHistorial);
                if (oldVeterinariaAsociadaCodigoOfHistorialListHistorial != null) {
                    oldVeterinariaAsociadaCodigoOfHistorialListHistorial.getHistorialList().remove(historialListHistorial);
                    oldVeterinariaAsociadaCodigoOfHistorialListHistorial = em.merge(oldVeterinariaAsociadaCodigoOfHistorialListHistorial);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Veterinaria veterinaria) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Veterinaria persistentVeterinaria = em.find(Veterinaria.class, veterinaria.getCodigo());
            List<Historial> historialListOld = persistentVeterinaria.getHistorialList();
            List<Historial> historialListNew = veterinaria.getHistorialList();
            List<Historial> attachedHistorialListNew = new ArrayList<Historial>();
            for (Historial historialListNewHistorialToAttach : historialListNew) {
                historialListNewHistorialToAttach = em.getReference(historialListNewHistorialToAttach.getClass(), historialListNewHistorialToAttach.getCodigo());
                attachedHistorialListNew.add(historialListNewHistorialToAttach);
            }
            historialListNew = attachedHistorialListNew;
            veterinaria.setHistorialList(historialListNew);
            veterinaria = em.merge(veterinaria);
            for (Historial historialListOldHistorial : historialListOld) {
                if (!historialListNew.contains(historialListOldHistorial)) {
                    historialListOldHistorial.setVeterinariaAsociadaCodigo(null);
                    historialListOldHistorial = em.merge(historialListOldHistorial);
                }
            }
            for (Historial historialListNewHistorial : historialListNew) {
                if (!historialListOld.contains(historialListNewHistorial)) {
                    Veterinaria oldVeterinariaAsociadaCodigoOfHistorialListNewHistorial = historialListNewHistorial.getVeterinariaAsociadaCodigo();
                    historialListNewHistorial.setVeterinariaAsociadaCodigo(veterinaria);
                    historialListNewHistorial = em.merge(historialListNewHistorial);
                    if (oldVeterinariaAsociadaCodigoOfHistorialListNewHistorial != null && !oldVeterinariaAsociadaCodigoOfHistorialListNewHistorial.equals(veterinaria)) {
                        oldVeterinariaAsociadaCodigoOfHistorialListNewHistorial.getHistorialList().remove(historialListNewHistorial);
                        oldVeterinariaAsociadaCodigoOfHistorialListNewHistorial = em.merge(oldVeterinariaAsociadaCodigoOfHistorialListNewHistorial);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = veterinaria.getCodigo();
                if (findVeterinaria(id) == null) {
                    throw new NonexistentEntityException("The veterinaria with id " + id + " no longer exists.");
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
            Veterinaria veterinaria;
            try {
                veterinaria = em.getReference(Veterinaria.class, id);
                veterinaria.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The veterinaria with id " + id + " no longer exists.", enfe);
            }
            List<Historial> historialList = veterinaria.getHistorialList();
            for (Historial historialListHistorial : historialList) {
                historialListHistorial.setVeterinariaAsociadaCodigo(null);
                historialListHistorial = em.merge(historialListHistorial);
            }
            em.remove(veterinaria);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Veterinaria> findVeterinariaEntities() {
        return findVeterinariaEntities(true, -1, -1);
    }

    public List<Veterinaria> findVeterinariaEntities(int maxResults, int firstResult) {
        return findVeterinariaEntities(false, maxResults, firstResult);
    }

    private List<Veterinaria> findVeterinariaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Veterinaria.class));
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

    public Veterinaria findVeterinaria(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Veterinaria.class, id);
        } finally {
            em.close();
        }
    }

    public int getVeterinariaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Veterinaria> rt = cq.from(Veterinaria.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
