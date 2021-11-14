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
import com.entities.Plan;
import java.util.ArrayList;
import java.util.List;
import com.entities.Historial;
import com.entities.Servicio;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author EQUIPO
 */
public class ServicioJpaController implements Serializable {

    public ServicioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Servicio servicio) {
        if (servicio.getPlanList() == null) {
            servicio.setPlanList(new ArrayList<Plan>());
        }
        if (servicio.getHistorialList() == null) {
            servicio.setHistorialList(new ArrayList<Historial>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Plan> attachedPlanList = new ArrayList<Plan>();
            for (Plan planListPlanToAttach : servicio.getPlanList()) {
                planListPlanToAttach = em.getReference(planListPlanToAttach.getClass(), planListPlanToAttach.getCodigo());
                attachedPlanList.add(planListPlanToAttach);
            }
            servicio.setPlanList(attachedPlanList);
            List<Historial> attachedHistorialList = new ArrayList<Historial>();
            for (Historial historialListHistorialToAttach : servicio.getHistorialList()) {
                historialListHistorialToAttach = em.getReference(historialListHistorialToAttach.getClass(), historialListHistorialToAttach.getCodigo());
                attachedHistorialList.add(historialListHistorialToAttach);
            }
            servicio.setHistorialList(attachedHistorialList);
            em.persist(servicio);
            for (Plan planListPlan : servicio.getPlanList()) {
                planListPlan.getServicioList().add(servicio);
                planListPlan = em.merge(planListPlan);
            }
            for (Historial historialListHistorial : servicio.getHistorialList()) {
                Servicio oldServicioContratadoCodigoOfHistorialListHistorial = historialListHistorial.getServicioContratadoCodigo();
                historialListHistorial.setServicioContratadoCodigo(servicio);
                historialListHistorial = em.merge(historialListHistorial);
                if (oldServicioContratadoCodigoOfHistorialListHistorial != null) {
                    oldServicioContratadoCodigoOfHistorialListHistorial.getHistorialList().remove(historialListHistorial);
                    oldServicioContratadoCodigoOfHistorialListHistorial = em.merge(oldServicioContratadoCodigoOfHistorialListHistorial);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Servicio servicio) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Servicio persistentServicio = em.find(Servicio.class, servicio.getCodigo());
            List<Plan> planListOld = persistentServicio.getPlanList();
            List<Plan> planListNew = servicio.getPlanList();
            List<Historial> historialListOld = persistentServicio.getHistorialList();
            List<Historial> historialListNew = servicio.getHistorialList();
            List<Plan> attachedPlanListNew = new ArrayList<Plan>();
            for (Plan planListNewPlanToAttach : planListNew) {
                planListNewPlanToAttach = em.getReference(planListNewPlanToAttach.getClass(), planListNewPlanToAttach.getCodigo());
                attachedPlanListNew.add(planListNewPlanToAttach);
            }
            planListNew = attachedPlanListNew;
            servicio.setPlanList(planListNew);
            List<Historial> attachedHistorialListNew = new ArrayList<Historial>();
            for (Historial historialListNewHistorialToAttach : historialListNew) {
                historialListNewHistorialToAttach = em.getReference(historialListNewHistorialToAttach.getClass(), historialListNewHistorialToAttach.getCodigo());
                attachedHistorialListNew.add(historialListNewHistorialToAttach);
            }
            historialListNew = attachedHistorialListNew;
            servicio.setHistorialList(historialListNew);
            servicio = em.merge(servicio);
            for (Plan planListOldPlan : planListOld) {
                if (!planListNew.contains(planListOldPlan)) {
                    planListOldPlan.getServicioList().remove(servicio);
                    planListOldPlan = em.merge(planListOldPlan);
                }
            }
            for (Plan planListNewPlan : planListNew) {
                if (!planListOld.contains(planListNewPlan)) {
                    planListNewPlan.getServicioList().add(servicio);
                    planListNewPlan = em.merge(planListNewPlan);
                }
            }
            for (Historial historialListOldHistorial : historialListOld) {
                if (!historialListNew.contains(historialListOldHistorial)) {
                    historialListOldHistorial.setServicioContratadoCodigo(null);
                    historialListOldHistorial = em.merge(historialListOldHistorial);
                }
            }
            for (Historial historialListNewHistorial : historialListNew) {
                if (!historialListOld.contains(historialListNewHistorial)) {
                    Servicio oldServicioContratadoCodigoOfHistorialListNewHistorial = historialListNewHistorial.getServicioContratadoCodigo();
                    historialListNewHistorial.setServicioContratadoCodigo(servicio);
                    historialListNewHistorial = em.merge(historialListNewHistorial);
                    if (oldServicioContratadoCodigoOfHistorialListNewHistorial != null && !oldServicioContratadoCodigoOfHistorialListNewHistorial.equals(servicio)) {
                        oldServicioContratadoCodigoOfHistorialListNewHistorial.getHistorialList().remove(historialListNewHistorial);
                        oldServicioContratadoCodigoOfHistorialListNewHistorial = em.merge(oldServicioContratadoCodigoOfHistorialListNewHistorial);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = servicio.getCodigo();
                if (findServicio(id) == null) {
                    throw new NonexistentEntityException("The servicio with id " + id + " no longer exists.");
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
            Servicio servicio;
            try {
                servicio = em.getReference(Servicio.class, id);
                servicio.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The servicio with id " + id + " no longer exists.", enfe);
            }
            List<Plan> planList = servicio.getPlanList();
            for (Plan planListPlan : planList) {
                planListPlan.getServicioList().remove(servicio);
                planListPlan = em.merge(planListPlan);
            }
            List<Historial> historialList = servicio.getHistorialList();
            for (Historial historialListHistorial : historialList) {
                historialListHistorial.setServicioContratadoCodigo(null);
                historialListHistorial = em.merge(historialListHistorial);
            }
            em.remove(servicio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Servicio> findServicioEntities() {
        return findServicioEntities(true, -1, -1);
    }

    public List<Servicio> findServicioEntities(int maxResults, int firstResult) {
        return findServicioEntities(false, maxResults, firstResult);
    }

    private List<Servicio> findServicioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Servicio.class));
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

    public Servicio findServicio(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Servicio.class, id);
        } finally {
            em.close();
        }
    }

    public int getServicioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Servicio> rt = cq.from(Servicio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
