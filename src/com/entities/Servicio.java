/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Equipo Malware
 */
@Entity
@Table(name = "servicio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Servicio.findAll", query = "SELECT s FROM Servicio s")
    , @NamedQuery(name = "Servicio.findByCodigo", query = "SELECT s FROM Servicio s WHERE s.codigo = :codigo")
    , @NamedQuery(name = "Servicio.findByCalificacion", query = "SELECT s FROM Servicio s WHERE s.calificacion = :calificacion")
    , @NamedQuery(name = "Servicio.findByCopago", query = "SELECT s FROM Servicio s WHERE s.copago = :copago")
    , @NamedQuery(name = "Servicio.findByCostoEnPlan", query = "SELECT s FROM Servicio s WHERE s.costoEnPlan = :costoEnPlan")
    , @NamedQuery(name = "Servicio.findByNombre", query = "SELECT s FROM Servicio s WHERE s.nombre = :nombre")})
public class Servicio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo")
    private Integer codigo;
    @Basic(optional = false)
    @Column(name = "calificacion")
    private float calificacion;
    @Basic(optional = false)
    @Column(name = "copago")
    private double copago;
    @Basic(optional = false)
    @Column(name = "costo_en_plan")
    private double costoEnPlan;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @JoinTable(name = "servicio_plan_actual", joinColumns = {
        @JoinColumn(name = "servicios_codigo", referencedColumnName = "codigo")}, inverseJoinColumns = {
        @JoinColumn(name = "plan_actual_codigo", referencedColumnName = "codigo")})
    @ManyToMany
    private List<Plan> planList;
    @OneToMany(mappedBy = "servicioContratadoCodigo")
    private List<Historial> historialList;

    public Servicio() {
    }

    public Servicio(Integer codigo) {
        this.codigo = codigo;
    }

    public Servicio(Integer codigo, float calificacion, double copago, double costoEnPlan, String nombre) {
        this.codigo = codigo;
        this.calificacion = calificacion;
        this.copago = copago;
        this.costoEnPlan = costoEnPlan;
        this.nombre = nombre;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public float getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(float calificacion) {
        this.calificacion = calificacion;
    }

    public double getCopago() {
        return copago;
    }

    public void setCopago(double copago) {
        this.copago = copago;
    }

    public double getCostoEnPlan() {
        return costoEnPlan;
    }

    public void setCostoEnPlan(double costoEnPlan) {
        this.costoEnPlan = costoEnPlan;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlTransient
    public List<Plan> getPlanList() {
        return planList;
    }

    public void setPlanList(List<Plan> planList) {
        this.planList = planList;
    }

    @XmlTransient
    public List<Historial> getHistorialList() {
        return historialList;
    }

    public void setHistorialList(List<Historial> historialList) {
        this.historialList = historialList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigo != null ? codigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Servicio)) {
            return false;
        }
        Servicio other = (Servicio) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.Servicio[ codigo=" + codigo + " ]";
    }
    
}
