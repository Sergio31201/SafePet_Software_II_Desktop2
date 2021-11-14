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
import com.entities.Beneficiario;
import com.entities.Historial;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author EQUIPO
 */
public class BeneficiarioJpaController implements Serializable {

    public BeneficiarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Beneficiario beneficiario) {
        if (beneficiario.getHistorialList() == null) {
            beneficiario.setHistorialList(new ArrayList<Historial>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Afiliado beneficiarioCodigo = beneficiario.getBeneficiarioCodigo();
            if (beneficiarioCodigo != null) {
                beneficiarioCodigo = em.getReference(beneficiarioCodigo.getClass(), beneficiarioCodigo.getCodigo());
                beneficiario.setBeneficiarioCodigo(beneficiarioCodigo);
            }
            List<Historial> attachedHistorialList = new ArrayList<Historial>();
            for (Historial historialListHistorialToAttach : beneficiario.getHistorialList()) {
                historialListHistorialToAttach = em.getReference(historialListHistorialToAttach.getClass(), historialListHistorialToAttach.getCodigo());
                attachedHistorialList.add(historialListHistorialToAttach);
            }
            beneficiario.setHistorialList(attachedHistorialList);
            em.persist(beneficiario);
            if (beneficiarioCodigo != null) {
                beneficiarioCodigo.getBeneficiarioList().add(beneficiario);
                beneficiarioCodigo = em.merge(beneficiarioCodigo);
            }
            for (Historial historialListHistorial : beneficiario.getHistorialList()) {
                Beneficiario oldMascotaConsultaCodigoOfHistorialListHistorial = historialListHistorial.getMascotaConsultaCodigo();
                historialListHistorial.setMascotaConsultaCodigo(beneficiario);
                historialListHistorial = em.merge(historialListHistorial);
                if (oldMascotaConsultaCodigoOfHistorialListHistorial != null) {
                    oldMascotaConsultaCodigoOfHistorialListHistorial.getHistorialList().remove(historialListHistorial);
                    oldMascotaConsultaCodigoOfHistorialListHistorial = em.merge(oldMascotaConsultaCodigoOfHistorialListHistorial);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Beneficiario beneficiario) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Beneficiario persistentBeneficiario = em.find(Beneficiario.class, beneficiario.getCodigo());
            Afiliado beneficiarioCodigoOld = persistentBeneficiario.getBeneficiarioCodigo();
            Afiliado beneficiarioCodigoNew = beneficiario.getBeneficiarioCodigo();
            List<Historial> historialListOld = persistentBeneficiario.getHistorialList();
            List<Historial> historialListNew = beneficiario.getHistorialList();
            if (beneficiarioCodigoNew != null) {
                beneficiarioCodigoNew = em.getReference(beneficiarioCodigoNew.getClass(), beneficiarioCodigoNew.getCodigo());
                beneficiario.setBeneficiarioCodigo(beneficiarioCodigoNew);
            }
            List<Historial> attachedHistorialListNew = new ArrayList<Historial>();
            for (Historial historialListNewHistorialToAttach : historialListNew) {
                historialListNewHistorialToAttach = em.getReference(historialListNewHistorialToAttach.getClass(), historialListNewHistorialToAttach.getCodigo());
                attachedHistorialListNew.add(historialListNewHistorialToAttach);
            }
            historialListNew = attachedHistorialListNew;
            beneficiario.setHistorialList(historialListNew);
            beneficiario = em.merge(beneficiario);
            if (beneficiarioCodigoOld != null && !beneficiarioCodigoOld.equals(beneficiarioCodigoNew)) {
                beneficiarioCodigoOld.getBeneficiarioList().remove(beneficiario);
                beneficiarioCodigoOld = em.merge(beneficiarioCodigoOld);
            }
            if (beneficiarioCodigoNew != null && !beneficiarioCodigoNew.equals(beneficiarioCodigoOld)) {
                beneficiarioCodigoNew.getBeneficiarioList().add(beneficiario);
                beneficiarioCodigoNew = em.merge(beneficiarioCodigoNew);
            }
            for (Historial historialListOldHistorial : historialListOld) {
                if (!historialListNew.contains(historialListOldHistorial)) {
                    historialListOldHistorial.setMascotaConsultaCodigo(null);
                    historialListOldHistorial = em.merge(historialListOldHistorial);
                }
            }
            for (Historial historialListNewHistorial : historialListNew) {
                if (!historialListOld.contains(historialListNewHistorial)) {
                    Beneficiario oldMascotaConsultaCodigoOfHistorialListNewHistorial = historialListNewHistorial.getMascotaConsultaCodigo();
                    historialListNewHistorial.setMascotaConsultaCodigo(beneficiario);
                    historialListNewHistorial = em.merge(historialListNewHistorial);
                    if (oldMascotaConsultaCodigoOfHistorialListNewHistorial != null && !oldMascotaConsultaCodigoOfHistorialListNewHistorial.equals(beneficiario)) {
                        oldMascotaConsultaCodigoOfHistorialListNewHistorial.getHistorialList().remove(historialListNewHistorial);
                        oldMascotaConsultaCodigoOfHistorialListNewHistorial = em.merge(oldMascotaConsultaCodigoOfHistorialListNewHistorial);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = beneficiario.getCodigo();
                if (findBeneficiario(id) == null) {
                    throw new NonexistentEntityException("The beneficiario with id " + id + " no longer exists.");
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
            Beneficiario beneficiario;
            try {
                beneficiario = em.getReference(Beneficiario.class, id);
                beneficiario.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The beneficiario with id " + id + " no longer exists.", enfe);
            }
            Afiliado beneficiarioCodigo = beneficiario.getBeneficiarioCodigo();
            if (beneficiarioCodigo != null) {
                beneficiarioCodigo.getBeneficiarioList().remove(beneficiario);
                beneficiarioCodigo = em.merge(beneficiarioCodigo);
            }
            List<Historial> historialList = beneficiario.getHistorialList();
            for (Historial historialListHistorial : historialList) {
                historialListHistorial.setMascotaConsultaCodigo(null);
                historialListHistorial = em.merge(historialListHistorial);
            }
            em.remove(beneficiario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Beneficiario> findBeneficiarioEntities() {
        return findBeneficiarioEntities(true, -1, -1);
    }

    public List<Beneficiario> findBeneficiarioEntities(int maxResults, int firstResult) {
        return findBeneficiarioEntities(false, maxResults, firstResult);
    }

    private List<Beneficiario> findBeneficiarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Beneficiario.class));
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

    public Beneficiario findBeneficiario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Beneficiario.class, id);
        } finally {
            em.close();
        }
    }

    public int getBeneficiarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Beneficiario> rt = cq.from(Beneficiario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
