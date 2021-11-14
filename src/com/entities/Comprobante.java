/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Equipo Malware
 */
@Entity
@Table(name = "comprobante")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Comprobante.findAll", query = "SELECT c FROM Comprobante c")
    , @NamedQuery(name = "Comprobante.findByCodigo", query = "SELECT c FROM Comprobante c WHERE c.codigo = :codigo")
    , @NamedQuery(name = "Comprobante.findByMedioDePago", query = "SELECT c FROM Comprobante c WHERE c.medioDePago = :medioDePago")})
public class Comprobante implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo")
    private Integer codigo;
    @Basic(optional = false)
    @Column(name = "medio_de_pago")
    private int medioDePago;
    @JoinColumn(name = "id_afiliado", referencedColumnName = "codigo")
    @ManyToOne
    private Afiliado idAfiliado;
    @JoinColumn(name = "id_plan", referencedColumnName = "codigo")
    @ManyToOne
    private Plan idPlan;

    public Comprobante() {
    }

    public Comprobante(Integer codigo) {
        this.codigo = codigo;
    }

    public Comprobante(Integer codigo, int medioDePago) {
        this.codigo = codigo;
        this.medioDePago = medioDePago;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public int getMedioDePago() {
        return medioDePago;
    }

    public void setMedioDePago(int medioDePago) {
        this.medioDePago = medioDePago;
    }

    public Afiliado getIdAfiliado() {
        return idAfiliado;
    }

    public void setIdAfiliado(Afiliado idAfiliado) {
        this.idAfiliado = idAfiliado;
    }

    public Plan getIdPlan() {
        return idPlan;
    }

    public void setIdPlan(Plan idPlan) {
        this.idPlan = idPlan;
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
        if (!(object instanceof Comprobante)) {
            return false;
        }
        Comprobante other = (Comprobante) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.Comprobante[ codigo=" + codigo + " ]";
    }
    
}
