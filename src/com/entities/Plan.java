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
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author EQUIPO
 */
@Entity
@Table(name = "plan")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Plan.findAll", query = "SELECT p FROM Plan p")
    , @NamedQuery(name = "Plan.findByCodigo", query = "SELECT p FROM Plan p WHERE p.codigo = :codigo")
    , @NamedQuery(name = "Plan.findByCostoTotal", query = "SELECT p FROM Plan p WHERE p.costoTotal = :costoTotal")
    , @NamedQuery(name = "Plan.findByNumeroBeneficiarios", query = "SELECT p FROM Plan p WHERE p.numeroBeneficiarios = :numeroBeneficiarios")})
public class Plan implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo")
    private Integer codigo;
    @Basic(optional = false)
    @Column(name = "costo_total")
    private double costoTotal;
    @Basic(optional = false)
    @Column(name = "numero_beneficiarios")
    private int numeroBeneficiarios;
    @ManyToMany(mappedBy = "planList")
    private List<Servicio> servicioList;
    @OneToMany(mappedBy = "idPlan")
    private List<Comprobante> comprobanteList;

    public Plan() {
    }

    public Plan(Integer codigo) {
        this.codigo = codigo;
    }

    public Plan(Integer codigo, double costoTotal, int numeroBeneficiarios) {
        this.codigo = codigo;
        this.costoTotal = costoTotal;
        this.numeroBeneficiarios = numeroBeneficiarios;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public double getCostoTotal() {
        return costoTotal;
    }

    public void setCostoTotal(double costoTotal) {
        this.costoTotal = costoTotal;
    }

    public int getNumeroBeneficiarios() {
        return numeroBeneficiarios;
    }

    public void setNumeroBeneficiarios(int numeroBeneficiarios) {
        this.numeroBeneficiarios = numeroBeneficiarios;
    }

    @XmlTransient
    public List<Servicio> getServicioList() {
        return servicioList;
    }

    public void setServicioList(List<Servicio> servicioList) {
        this.servicioList = servicioList;
    }

    @XmlTransient
    public List<Comprobante> getComprobanteList() {
        return comprobanteList;
    }

    public void setComprobanteList(List<Comprobante> comprobanteList) {
        this.comprobanteList = comprobanteList;
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
        if (!(object instanceof Plan)) {
            return false;
        }
        Plan other = (Plan) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.Plan[ codigo=" + codigo + " ]";
    }
    
}
