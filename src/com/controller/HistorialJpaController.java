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
import com.entities.Veterinaria;
import com.entities.Beneficiario;
import com.entities.Afiliado;
import com.entities.Historial;
import com.entities.Servicio;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author EQUIPO
 */
public class HistorialJpaController implements Serializable {

    public HistorialJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Historial historial) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Veterinaria veterinariaAsociadaCodigo = historial.getVeterinariaAsociadaCodigo();
            if (veterinariaAsociadaCodigo != null) {
                veterinariaAsociadaCodigo = em.getReference(veterinariaAsociadaCodigo.getClass(), veterinariaAsociadaCodigo.getCodigo());
                historial.setVeterinariaAsociadaCodigo(veterinariaAsociadaCodigo);
            }
            Beneficiario mascotaConsultaCodigo = historial.getMascotaConsultaCodigo();
            if (mascotaConsultaCodigo != null) {
                mascotaConsultaCodigo = em.getReference(mascotaConsultaCodigo.getClass(), mascotaConsultaCodigo.getCodigo());
                historial.setMascotaConsultaCodigo(mascotaConsultaCodigo);
            }
            Afiliado clienteAsociadoCodigo = historial.getClienteAsociadoCodigo();
            if (clienteAsociadoCodigo != null) {
                clienteAsociadoCodigo = em.getReference(clienteAsociadoCodigo.getClass(), clienteAsociadoCodigo.getCodigo());
                historial.setClienteAsociadoCodigo(clienteAsociadoCodigo);
            }
            Servicio servicioContratadoCodigo = historial.getServicioContratadoCodigo();
            if (servicioContratadoCodigo != null) {
                servicioContratadoCodigo = em.getReference(servicioContratadoCodigo.getClass(), servicioContratadoCodigo.getCodigo());
                historial.setServicioContratadoCodigo(servicioContratadoCodigo);
            }
            em.persist(historial);
            if (veterinariaAsociadaCodigo != null) {
                veterinariaAsociadaCodigo.getHistorialList().add(historial);
                veterinariaAsociadaCodigo = em.merge(veterinariaAsociadaCodigo);
            }
            if (mascotaConsultaCodigo != null) {
                mascotaConsultaCodigo.getHistorialList().add(historial);
                mascotaConsultaCodigo = em.merge(mascotaConsultaCodigo);
            }
            if (clienteAsociadoCodigo != null) {
                clienteAsociadoCodigo.getHistorialList().add(historial);
                clienteAsociadoCodigo = em.merge(clienteAsociadoCodigo);
            }
            if (servicioContratadoCodigo != null) {
                servicioContratadoCodigo.getHistorialList().add(historial);
                servicioContratadoCodigo = em.merge(servicioContratadoCodigo);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Historial historial) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Historial persistentHistorial = em.find(Historial.class, historial.getCodigo());
            Veterinaria veterinariaAsociadaCodigoOld = persistentHistorial.getVeterinariaAsociadaCodigo();
            Veterinaria veterinariaAsociadaCodigoNew = historial.getVeterinariaAsociadaCodigo();
            Beneficiario mascotaConsultaCodigoOld = persistentHistorial.getMascotaConsultaCodigo();
            Beneficiario mascotaConsultaCodigoNew = historial.getMascotaConsultaCodigo();
            Afiliado clienteAsociadoCodigoOld = persistentHistorial.getClienteAsociadoCodigo();
            Afiliado clienteAsociadoCodigoNew = historial.getClienteAsociadoCodigo();
            Servicio servicioContratadoCodigoOld = persistentHistorial.getServicioContratadoCodigo();
            Servicio servicioContratadoCodigoNew = historial.getServicioContratadoCodigo();
            if (veterinariaAsociadaCodigoNew != null) {
                veterinariaAsociadaCodigoNew = em.getReference(veterinariaAsociadaCodigoNew.getClass(), veterinariaAsociadaCodigoNew.getCodigo());
                historial.setVeterinariaAsociadaCodigo(veterinariaAsociadaCodigoNew);
            }
            if (mascotaConsultaCodigoNew != null) {
                mascotaConsultaCodigoNew = em.getReference(mascotaConsultaCodigoNew.getClass(), mascotaConsultaCodigoNew.getCodigo());
                historial.setMascotaConsultaCodigo(mascotaConsultaCodigoNew);
            }
            if (clienteAsociadoCodigoNew != null) {
                clienteAsociadoCodigoNew = em.getReference(clienteAsociadoCodigoNew.getClass(), clienteAsociadoCodigoNew.getCodigo());
                historial.setClienteAsociadoCodigo(clienteAsociadoCodigoNew);
            }
            if (servicioContratadoCodigoNew != null) {
                servicioContratadoCodigoNew = em.getReference(servicioContratadoCodigoNew.getClass(), servicioContratadoCodigoNew.getCodigo());
                historial.setServicioContratadoCodigo(servicioContratadoCodigoNew);
            }
            historial = em.merge(historial);
            if (veterinariaAsociadaCodigoOld != null && !veterinariaAsociadaCodigoOld.equals(veterinariaAsociadaCodigoNew)) {
                veterinariaAsociadaCodigoOld.getHistorialList().remove(historial);
                veterinariaAsociadaCodigoOld = em.merge(veterinariaAsociadaCodigoOld);
            }
            if (veterinariaAsociadaCodigoNew != null && !veterinariaAsociadaCodigoNew.equals(veterinariaAsociadaCodigoOld)) {
                veterinariaAsociadaCodigoNew.getHistorialList().add(historial);
                veterinariaAsociadaCodigoNew = em.merge(veterinariaAsociadaCodigoNew);
            }
            if (mascotaConsultaCodigoOld != null && !mascotaConsultaCodigoOld.equals(mascotaConsultaCodigoNew)) {
                mascotaConsultaCodigoOld.getHistorialList().remove(historial);
                mascotaConsultaCodigoOld = em.merge(mascotaConsultaCodigoOld);
            }
            if (mascotaConsultaCodigoNew != null && !mascotaConsultaCodigoNew.equals(mascotaConsultaCodigoOld)) {
                mascotaConsultaCodigoNew.getHistorialList().add(historial);
                mascotaConsultaCodigoNew = em.merge(mascotaConsultaCodigoNew);
            }
            if (clienteAsociadoCodigoOld != null && !clienteAsociadoCodigoOld.equals(clienteAsociadoCodigoNew)) {
                clienteAsociadoCodigoOld.getHistorialList().remove(historial);
                clienteAsociadoCodigoOld = em.merge(clienteAsociadoCodigoOld);
            }
            if (clienteAsociadoCodigoNew != null && !clienteAsociadoCodigoNew.equals(clienteAsociadoCodigoOld)) {
                clienteAsociadoCodigoNew.getHistorialList().add(historial);
                clienteAsociadoCodigoNew = em.merge(clienteAsociadoCodigoNew);
            }
            if (servicioContratadoCodigoOld != null && !servicioContratadoCodigoOld.equals(servicioContratadoCodigoNew)) {
                servicioContratadoCodigoOld.getHistorialList().remove(historial);
                servicioContratadoCodigoOld = em.merge(servicioContratadoCodigoOld);
            }
            if (servicioContratadoCodigoNew != null && !servicioContratadoCodigoNew.equals(servicioContratadoCodigoOld)) {
                servicioContratadoCodigoNew.getHistorialList().add(historial);
                servicioContratadoCodigoNew = em.merge(servicioContratadoCodigoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = historial.getCodigo();
                if (findHistorial(id) == null) {
                    throw new NonexistentEntityException("The historial with id " + id + " no longer exists.");
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
            Historial historial;
            try {
                historial = em.getReference(Historial.class, id);
                historial.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The historial with id " + id + " no longer exists.", enfe);
            }
            Veterinaria veterinariaAsociadaCodigo = historial.getVeterinariaAsociadaCodigo();
            if (veterinariaAsociadaCodigo != null) {
                veterinariaAsociadaCodigo.getHistorialList().remove(historial);
                veterinariaAsociadaCodigo = em.merge(veterinariaAsociadaCodigo);
            }
            Beneficiario mascotaConsultaCodigo = historial.getMascotaConsultaCodigo();
            if (mascotaConsultaCodigo != null) {
                mascotaConsultaCodigo.getHistorialList().remove(historial);
                mascotaConsultaCodigo = em.merge(mascotaConsultaCodigo);
            }
            Afiliado clienteAsociadoCodigo = historial.getClienteAsociadoCodigo();
            if (clienteAsociadoCodigo != null) {
                clienteAsociadoCodigo.getHistorialList().remove(historial);
                clienteAsociadoCodigo = em.merge(clienteAsociadoCodigo);
            }
            Servicio servicioContratadoCodigo = historial.getServicioContratadoCodigo();
            if (servicioContratadoCodigo != null) {
                servicioContratadoCodigo.getHistorialList().remove(historial);
                servicioContratadoCodigo = em.merge(servicioContratadoCodigo);
            }
            em.remove(historial);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Historial> findHistorialEntities() {
        return findHistorialEntities(true, -1, -1);
    }

    public List<Historial> findHistorialEntities(int maxResults, int firstResult) {
        return findHistorialEntities(false, maxResults, firstResult);
    }

    private List<Historial> findHistorialEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Historial.class));
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

    public Historial findHistorial(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Historial.class, id);
        } finally {
            em.close();
        }
    }

    public int getHistorialCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Historial> rt = cq.from(Historial.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
