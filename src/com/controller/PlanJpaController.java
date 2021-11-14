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
import com.entities.Servicio;
import java.util.ArrayList;
import java.util.List;
import com.entities.Comprobante;
import com.entities.Plan;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author EQUIPO
 */
public class PlanJpaController implements Serializable {

    public PlanJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Plan plan) {
        if (plan.getServicioList() == null) {
            plan.setServicioList(new ArrayList<Servicio>());
        }
        if (plan.getComprobanteList() == null) {
            plan.setComprobanteList(new ArrayList<Comprobante>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Servicio> attachedServicioList = new ArrayList<Servicio>();
            for (Servicio servicioListServicioToAttach : plan.getServicioList()) {
                servicioListServicioToAttach = em.getReference(servicioListServicioToAttach.getClass(), servicioListServicioToAttach.getCodigo());
                attachedServicioList.add(servicioListServicioToAttach);
            }
            plan.setServicioList(attachedServicioList);
            List<Comprobante> attachedComprobanteList = new ArrayList<Comprobante>();
            for (Comprobante comprobanteListComprobanteToAttach : plan.getComprobanteList()) {
                comprobanteListComprobanteToAttach = em.getReference(comprobanteListComprobanteToAttach.getClass(), comprobanteListComprobanteToAttach.getCodigo());
                attachedComprobanteList.add(comprobanteListComprobanteToAttach);
            }
            plan.setComprobanteList(attachedComprobanteList);
            em.persist(plan);
            for (Servicio servicioListServicio : plan.getServicioList()) {
                servicioListServicio.getPlanList().add(plan);
                servicioListServicio = em.merge(servicioListServicio);
            }
            for (Comprobante comprobanteListComprobante : plan.getComprobanteList()) {
                Plan oldIdPlanOfComprobanteListComprobante = comprobanteListComprobante.getIdPlan();
                comprobanteListComprobante.setIdPlan(plan);
                comprobanteListComprobante = em.merge(comprobanteListComprobante);
                if (oldIdPlanOfComprobanteListComprobante != null) {
                    oldIdPlanOfComprobanteListComprobante.getComprobanteList().remove(comprobanteListComprobante);
                    oldIdPlanOfComprobanteListComprobante = em.merge(oldIdPlanOfComprobanteListComprobante);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Plan plan) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Plan persistentPlan = em.find(Plan.class, plan.getCodigo());
            List<Servicio> servicioListOld = persistentPlan.getServicioList();
            List<Servicio> servicioListNew = plan.getServicioList();
            List<Comprobante> comprobanteListOld = persistentPlan.getComprobanteList();
            List<Comprobante> comprobanteListNew = plan.getComprobanteList();
            List<Servicio> attachedServicioListNew = new ArrayList<Servicio>();
            for (Servicio servicioListNewServicioToAttach : servicioListNew) {
                servicioListNewServicioToAttach = em.getReference(servicioListNewServicioToAttach.getClass(), servicioListNewServicioToAttach.getCodigo());
                attachedServicioListNew.add(servicioListNewServicioToAttach);
            }
            servicioListNew = attachedServicioListNew;
            plan.setServicioList(servicioListNew);
            List<Comprobante> attachedComprobanteListNew = new ArrayList<Comprobante>();
            for (Comprobante comprobanteListNewComprobanteToAttach : comprobanteListNew) {
                comprobanteListNewComprobanteToAttach = em.getReference(comprobanteListNewComprobanteToAttach.getClass(), comprobanteListNewComprobanteToAttach.getCodigo());
                attachedComprobanteListNew.add(comprobanteListNewComprobanteToAttach);
            }
            comprobanteListNew = attachedComprobanteListNew;
            plan.setComprobanteList(comprobanteListNew);
            plan = em.merge(plan);
            for (Servicio servicioListOldServicio : servicioListOld) {
                if (!servicioListNew.contains(servicioListOldServicio)) {
                    servicioListOldServicio.getPlanList().remove(plan);
                    servicioListOldServicio = em.merge(servicioListOldServicio);
                }
            }
            for (Servicio servicioListNewServicio : servicioListNew) {
                if (!servicioListOld.contains(servicioListNewServicio)) {
                    servicioListNewServicio.getPlanList().add(plan);
                    servicioListNewServicio = em.merge(servicioListNewServicio);
                }
            }
            for (Comprobante comprobanteListOldComprobante : comprobanteListOld) {
                if (!comprobanteListNew.contains(comprobanteListOldComprobante)) {
                    comprobanteListOldComprobante.setIdPlan(null);
                    comprobanteListOldComprobante = em.merge(comprobanteListOldComprobante);
                }
            }
            for (Comprobante comprobanteListNewComprobante : comprobanteListNew) {
                if (!comprobanteListOld.contains(comprobanteListNewComprobante)) {
                    Plan oldIdPlanOfComprobanteListNewComprobante = comprobanteListNewComprobante.getIdPlan();
                    comprobanteListNewComprobante.setIdPlan(plan);
                    comprobanteListNewComprobante = em.merge(comprobanteListNewComprobante);
                    if (oldIdPlanOfComprobanteListNewComprobante != null && !oldIdPlanOfComprobanteListNewComprobante.equals(plan)) {
                        oldIdPlanOfComprobanteListNewComprobante.getComprobanteList().remove(comprobanteListNewComprobante);
                        oldIdPlanOfComprobanteListNewComprobante = em.merge(oldIdPlanOfComprobanteListNewComprobante);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = plan.getCodigo();
                if (findPlan(id) == null) {
                    throw new NonexistentEntityException("The plan with id " + id + " no longer exists.");
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
            Plan plan;
            try {
                plan = em.getReference(Plan.class, id);
                plan.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The plan with id " + id + " no longer exists.", enfe);
            }
            List<Servicio> servicioList = plan.getServicioList();
            for (Servicio servicioListServicio : servicioList) {
                servicioListServicio.getPlanList().remove(plan);
                servicioListServicio = em.merge(servicioListServicio);
            }
            List<Comprobante> comprobanteList = plan.getComprobanteList();
            for (Comprobante comprobanteListComprobante : comprobanteList) {
                comprobanteListComprobante.setIdPlan(null);
                comprobanteListComprobante = em.merge(comprobanteListComprobante);
            }
            em.remove(plan);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Plan> findPlanEntities() {
        return findPlanEntities(true, -1, -1);
    }

    public List<Plan> findPlanEntities(int maxResults, int firstResult) {
        return findPlanEntities(false, maxResults, firstResult);
    }

    private List<Plan> findPlanEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Plan.class));
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

    public Plan findPlan(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Plan.class, id);
        } finally {
            em.close();
        }
    }

    public int getPlanCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Plan> rt = cq.from(Plan.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
