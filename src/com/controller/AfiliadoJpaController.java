/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import com.controller.exceptions.NonexistentEntityException;
import com.entities.Afiliado;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.entities.Comprobante;
import java.util.ArrayList;
import java.util.List;
import com.entities.Historial;
import com.entities.Beneficiario;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author EQUIPO
 */
public class AfiliadoJpaController implements Serializable {

    public AfiliadoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Afiliado afiliado) {
        if (afiliado.getComprobanteList() == null) {
            afiliado.setComprobanteList(new ArrayList<Comprobante>());
        }
        if (afiliado.getHistorialList() == null) {
            afiliado.setHistorialList(new ArrayList<Historial>());
        }
        if (afiliado.getBeneficiarioList() == null) {
            afiliado.setBeneficiarioList(new ArrayList<Beneficiario>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Comprobante> attachedComprobanteList = new ArrayList<Comprobante>();
            for (Comprobante comprobanteListComprobanteToAttach : afiliado.getComprobanteList()) {
                comprobanteListComprobanteToAttach = em.getReference(comprobanteListComprobanteToAttach.getClass(), comprobanteListComprobanteToAttach.getCodigo());
                attachedComprobanteList.add(comprobanteListComprobanteToAttach);
            }
            afiliado.setComprobanteList(attachedComprobanteList);
            List<Historial> attachedHistorialList = new ArrayList<Historial>();
            for (Historial historialListHistorialToAttach : afiliado.getHistorialList()) {
                historialListHistorialToAttach = em.getReference(historialListHistorialToAttach.getClass(), historialListHistorialToAttach.getCodigo());
                attachedHistorialList.add(historialListHistorialToAttach);
            }
            afiliado.setHistorialList(attachedHistorialList);
            List<Beneficiario> attachedBeneficiarioList = new ArrayList<Beneficiario>();
            for (Beneficiario beneficiarioListBeneficiarioToAttach : afiliado.getBeneficiarioList()) {
                beneficiarioListBeneficiarioToAttach = em.getReference(beneficiarioListBeneficiarioToAttach.getClass(), beneficiarioListBeneficiarioToAttach.getCodigo());
                attachedBeneficiarioList.add(beneficiarioListBeneficiarioToAttach);
            }
            afiliado.setBeneficiarioList(attachedBeneficiarioList);
            em.persist(afiliado);
            for (Comprobante comprobanteListComprobante : afiliado.getComprobanteList()) {
                Afiliado oldIdAfiliadoOfComprobanteListComprobante = comprobanteListComprobante.getIdAfiliado();
                comprobanteListComprobante.setIdAfiliado(afiliado);
                comprobanteListComprobante = em.merge(comprobanteListComprobante);
                if (oldIdAfiliadoOfComprobanteListComprobante != null) {
                    oldIdAfiliadoOfComprobanteListComprobante.getComprobanteList().remove(comprobanteListComprobante);
                    oldIdAfiliadoOfComprobanteListComprobante = em.merge(oldIdAfiliadoOfComprobanteListComprobante);
                }
            }
            for (Historial historialListHistorial : afiliado.getHistorialList()) {
                Afiliado oldClienteAsociadoCodigoOfHistorialListHistorial = historialListHistorial.getClienteAsociadoCodigo();
                historialListHistorial.setClienteAsociadoCodigo(afiliado);
                historialListHistorial = em.merge(historialListHistorial);
                if (oldClienteAsociadoCodigoOfHistorialListHistorial != null) {
                    oldClienteAsociadoCodigoOfHistorialListHistorial.getHistorialList().remove(historialListHistorial);
                    oldClienteAsociadoCodigoOfHistorialListHistorial = em.merge(oldClienteAsociadoCodigoOfHistorialListHistorial);
                }
            }
            for (Beneficiario beneficiarioListBeneficiario : afiliado.getBeneficiarioList()) {
                Afiliado oldBeneficiarioCodigoOfBeneficiarioListBeneficiario = beneficiarioListBeneficiario.getBeneficiarioCodigo();
                beneficiarioListBeneficiario.setBeneficiarioCodigo(afiliado);
                beneficiarioListBeneficiario = em.merge(beneficiarioListBeneficiario);
                if (oldBeneficiarioCodigoOfBeneficiarioListBeneficiario != null) {
                    oldBeneficiarioCodigoOfBeneficiarioListBeneficiario.getBeneficiarioList().remove(beneficiarioListBeneficiario);
                    oldBeneficiarioCodigoOfBeneficiarioListBeneficiario = em.merge(oldBeneficiarioCodigoOfBeneficiarioListBeneficiario);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Afiliado afiliado) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Afiliado persistentAfiliado = em.find(Afiliado.class, afiliado.getCodigo());
            List<Comprobante> comprobanteListOld = persistentAfiliado.getComprobanteList();
            List<Comprobante> comprobanteListNew = afiliado.getComprobanteList();
            List<Historial> historialListOld = persistentAfiliado.getHistorialList();
            List<Historial> historialListNew = afiliado.getHistorialList();
            List<Beneficiario> beneficiarioListOld = persistentAfiliado.getBeneficiarioList();
            List<Beneficiario> beneficiarioListNew = afiliado.getBeneficiarioList();
            List<Comprobante> attachedComprobanteListNew = new ArrayList<Comprobante>();
            for (Comprobante comprobanteListNewComprobanteToAttach : comprobanteListNew) {
                comprobanteListNewComprobanteToAttach = em.getReference(comprobanteListNewComprobanteToAttach.getClass(), comprobanteListNewComprobanteToAttach.getCodigo());
                attachedComprobanteListNew.add(comprobanteListNewComprobanteToAttach);
            }
            comprobanteListNew = attachedComprobanteListNew;
            afiliado.setComprobanteList(comprobanteListNew);
            List<Historial> attachedHistorialListNew = new ArrayList<Historial>();
            for (Historial historialListNewHistorialToAttach : historialListNew) {
                historialListNewHistorialToAttach = em.getReference(historialListNewHistorialToAttach.getClass(), historialListNewHistorialToAttach.getCodigo());
                attachedHistorialListNew.add(historialListNewHistorialToAttach);
            }
            historialListNew = attachedHistorialListNew;
            afiliado.setHistorialList(historialListNew);
            List<Beneficiario> attachedBeneficiarioListNew = new ArrayList<Beneficiario>();
            for (Beneficiario beneficiarioListNewBeneficiarioToAttach : beneficiarioListNew) {
                beneficiarioListNewBeneficiarioToAttach = em.getReference(beneficiarioListNewBeneficiarioToAttach.getClass(), beneficiarioListNewBeneficiarioToAttach.getCodigo());
                attachedBeneficiarioListNew.add(beneficiarioListNewBeneficiarioToAttach);
            }
            beneficiarioListNew = attachedBeneficiarioListNew;
            afiliado.setBeneficiarioList(beneficiarioListNew);
            afiliado = em.merge(afiliado);
            for (Comprobante comprobanteListOldComprobante : comprobanteListOld) {
                if (!comprobanteListNew.contains(comprobanteListOldComprobante)) {
                    comprobanteListOldComprobante.setIdAfiliado(null);
                    comprobanteListOldComprobante = em.merge(comprobanteListOldComprobante);
                }
            }
            for (Comprobante comprobanteListNewComprobante : comprobanteListNew) {
                if (!comprobanteListOld.contains(comprobanteListNewComprobante)) {
                    Afiliado oldIdAfiliadoOfComprobanteListNewComprobante = comprobanteListNewComprobante.getIdAfiliado();
                    comprobanteListNewComprobante.setIdAfiliado(afiliado);
                    comprobanteListNewComprobante = em.merge(comprobanteListNewComprobante);
                    if (oldIdAfiliadoOfComprobanteListNewComprobante != null && !oldIdAfiliadoOfComprobanteListNewComprobante.equals(afiliado)) {
                        oldIdAfiliadoOfComprobanteListNewComprobante.getComprobanteList().remove(comprobanteListNewComprobante);
                        oldIdAfiliadoOfComprobanteListNewComprobante = em.merge(oldIdAfiliadoOfComprobanteListNewComprobante);
                    }
                }
            }
            for (Historial historialListOldHistorial : historialListOld) {
                if (!historialListNew.contains(historialListOldHistorial)) {
                    historialListOldHistorial.setClienteAsociadoCodigo(null);
                    historialListOldHistorial = em.merge(historialListOldHistorial);
                }
            }
            for (Historial historialListNewHistorial : historialListNew) {
                if (!historialListOld.contains(historialListNewHistorial)) {
                    Afiliado oldClienteAsociadoCodigoOfHistorialListNewHistorial = historialListNewHistorial.getClienteAsociadoCodigo();
                    historialListNewHistorial.setClienteAsociadoCodigo(afiliado);
                    historialListNewHistorial = em.merge(historialListNewHistorial);
                    if (oldClienteAsociadoCodigoOfHistorialListNewHistorial != null && !oldClienteAsociadoCodigoOfHistorialListNewHistorial.equals(afiliado)) {
                        oldClienteAsociadoCodigoOfHistorialListNewHistorial.getHistorialList().remove(historialListNewHistorial);
                        oldClienteAsociadoCodigoOfHistorialListNewHistorial = em.merge(oldClienteAsociadoCodigoOfHistorialListNewHistorial);
                    }
                }
            }
            for (Beneficiario beneficiarioListOldBeneficiario : beneficiarioListOld) {
                if (!beneficiarioListNew.contains(beneficiarioListOldBeneficiario)) {
                    beneficiarioListOldBeneficiario.setBeneficiarioCodigo(null);
                    beneficiarioListOldBeneficiario = em.merge(beneficiarioListOldBeneficiario);
                }
            }
            for (Beneficiario beneficiarioListNewBeneficiario : beneficiarioListNew) {
                if (!beneficiarioListOld.contains(beneficiarioListNewBeneficiario)) {
                    Afiliado oldBeneficiarioCodigoOfBeneficiarioListNewBeneficiario = beneficiarioListNewBeneficiario.getBeneficiarioCodigo();
                    beneficiarioListNewBeneficiario.setBeneficiarioCodigo(afiliado);
                    beneficiarioListNewBeneficiario = em.merge(beneficiarioListNewBeneficiario);
                    if (oldBeneficiarioCodigoOfBeneficiarioListNewBeneficiario != null && !oldBeneficiarioCodigoOfBeneficiarioListNewBeneficiario.equals(afiliado)) {
                        oldBeneficiarioCodigoOfBeneficiarioListNewBeneficiario.getBeneficiarioList().remove(beneficiarioListNewBeneficiario);
                        oldBeneficiarioCodigoOfBeneficiarioListNewBeneficiario = em.merge(oldBeneficiarioCodigoOfBeneficiarioListNewBeneficiario);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = afiliado.getCodigo();
                if (findAfiliado(id) == null) {
                    throw new NonexistentEntityException("The afiliado with id " + id + " no longer exists.");
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
            Afiliado afiliado;
            try {
                afiliado = em.getReference(Afiliado.class, id);
                afiliado.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The afiliado with id " + id + " no longer exists.", enfe);
            }
            List<Comprobante> comprobanteList = afiliado.getComprobanteList();
            for (Comprobante comprobanteListComprobante : comprobanteList) {
                comprobanteListComprobante.setIdAfiliado(null);
                comprobanteListComprobante = em.merge(comprobanteListComprobante);
            }
            List<Historial> historialList = afiliado.getHistorialList();
            for (Historial historialListHistorial : historialList) {
                historialListHistorial.setClienteAsociadoCodigo(null);
                historialListHistorial = em.merge(historialListHistorial);
            }
            List<Beneficiario> beneficiarioList = afiliado.getBeneficiarioList();
            for (Beneficiario beneficiarioListBeneficiario : beneficiarioList) {
                beneficiarioListBeneficiario.setBeneficiarioCodigo(null);
                beneficiarioListBeneficiario = em.merge(beneficiarioListBeneficiario);
            }
            em.remove(afiliado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Afiliado> findAfiliadoEntities() {
        return findAfiliadoEntities(true, -1, -1);
    }

    public List<Afiliado> findAfiliadoEntities(int maxResults, int firstResult) {
        return findAfiliadoEntities(false, maxResults, firstResult);
    }

    private List<Afiliado> findAfiliadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Afiliado.class));
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

    public Afiliado findAfiliado(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Afiliado.class, id);
        } finally {
            em.close();
        }
    }

    public int getAfiliadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Afiliado> rt = cq.from(Afiliado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
