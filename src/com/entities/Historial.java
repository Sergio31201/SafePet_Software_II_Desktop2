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
 * @author EQUIPO
 */
@Entity
@Table(name = "historial")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Historial.findAll", query = "SELECT h FROM Historial h")
    , @NamedQuery(name = "Historial.findByCodigo", query = "SELECT h FROM Historial h WHERE h.codigo = :codigo")})
public class Historial implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo")
    private Integer codigo;
    @JoinColumn(name = "veterinaria_asociada_codigo", referencedColumnName = "codigo")
    @ManyToOne
    private Veterinaria veterinariaAsociadaCodigo;
    @JoinColumn(name = "mascota_consulta_codigo", referencedColumnName = "codigo")
    @ManyToOne
    private Beneficiario mascotaConsultaCodigo;
    @JoinColumn(name = "cliente_asociado_codigo", referencedColumnName = "codigo")
    @ManyToOne
    private Afiliado clienteAsociadoCodigo;
    @JoinColumn(name = "servicio_contratado_codigo", referencedColumnName = "codigo")
    @ManyToOne
    private Servicio servicioContratadoCodigo;

    public Historial() {
    }

    public Historial(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Veterinaria getVeterinariaAsociadaCodigo() {
        return veterinariaAsociadaCodigo;
    }

    public void setVeterinariaAsociadaCodigo(Veterinaria veterinariaAsociadaCodigo) {
        this.veterinariaAsociadaCodigo = veterinariaAsociadaCodigo;
    }

    public Beneficiario getMascotaConsultaCodigo() {
        return mascotaConsultaCodigo;
    }

    public void setMascotaConsultaCodigo(Beneficiario mascotaConsultaCodigo) {
        this.mascotaConsultaCodigo = mascotaConsultaCodigo;
    }

    public Afiliado getClienteAsociadoCodigo() {
        return clienteAsociadoCodigo;
    }

    public void setClienteAsociadoCodigo(Afiliado clienteAsociadoCodigo) {
        this.clienteAsociadoCodigo = clienteAsociadoCodigo;
    }

    public Servicio getServicioContratadoCodigo() {
        return servicioContratadoCodigo;
    }

    public void setServicioContratadoCodigo(Servicio servicioContratadoCodigo) {
        this.servicioContratadoCodigo = servicioContratadoCodigo;
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
        if (!(object instanceof Historial)) {
            return false;
        }
        Historial other = (Historial) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.Historial[ codigo=" + codigo + " ]";
    }
    
}
