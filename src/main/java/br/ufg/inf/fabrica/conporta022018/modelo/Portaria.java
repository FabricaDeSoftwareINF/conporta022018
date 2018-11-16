package br.ufg.inf.fabrica.conporta022018.modelo;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Blob;
import java.util.Date;
import java.util.List;

public class Portaria implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int anoId;
    private int seqId;
    private String assunto;
    private Date dtExped;
    private Date dtIniVig;
    private Date dtFimVig;
    private Date dtPublicDou;
    private int horasDesig;
    private String resumo;
    private String textoCompleto;
    private Blob arqPdf;
    private String siglaUndId;
    private String assinatura;
    private PortariaStatus status;
    private List<PortariaReferenciada> portariasReferenciadas;
    private List<Recebedora> undRecebedora;
    private List<Designado> designados;
    private Pessoa expedidor;
    private UndAdm unidadeExpedidora;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAnoId() {
        return anoId;
    }

    public void setAnoId(int anoId) {
        this.anoId = anoId;
    }

    public int getSeqId() {
        return seqId;
    }

    public void setSeqId(int seqId) {
        this.seqId = seqId;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public Date getDtExped() {
        return dtExped;
    }

    public void setDtExped(Date dtExped) {
        this.dtExped = dtExped;
    }

    public Date getDtIniVig() {
        return dtIniVig;
    }

    public void setDtIniVig(Date dtIniVig) {
        this.dtIniVig = dtIniVig;
    }

    public Date getDtFimVig() {
        return dtFimVig;
    }

    public void setDtFimVig(Date dtFimVig) {
        this.dtFimVig = dtFimVig;
    }

    public Date getDtPublicDou() {
        return dtPublicDou;
    }

    public void setDtPublicDou(Date dtPublicDou) {
        this.dtPublicDou = dtPublicDou;
    }

    public int getHorasDesig() {
        return horasDesig;
    }

    public void setHorasDesig(int horasDesig) {
        this.horasDesig = horasDesig;
    }

    public String getResumo() {
        return resumo;
    }

    public void setResumo(String resumo) {
        this.resumo = resumo;
    }

    public String getTextoCompleto() {
        return textoCompleto;
    }

    public void setTextoCompleto(String textoCompleto) {
        this.textoCompleto = textoCompleto;
    }

    public Blob getArqPdf() {
        return arqPdf;
    }

    public void setArqPdf(Blob arqPdf) {
        this.arqPdf = arqPdf;
    }

    public String getSiglaUndId() {
        return siglaUndId;
    }

    public void setSiglaUndId(String siglaUndId) {
        this.siglaUndId = siglaUndId;
    }

    public String getAssinatura() {
        return assinatura;
    }

    public void setAssinatura(String assinatura) {
        this.assinatura = assinatura;
    }

    public PortariaStatus getStatus() {
        return status;
    }

    public void setStatus(PortariaStatus status) {
        this.status = status;
    }

    public List<PortariaReferenciada> getPortariasReferenciadas() {
        return portariasReferenciadas;
    }

    public void setPortariasReferenciadas(List<PortariaReferenciada> portariasReferenciadas) {
        this.portariasReferenciadas = portariasReferenciadas;
    }

    public List<Recebedora> getUndRecebedora() {
        return undRecebedora;
    }

    public void setUndRecebedora(List<Recebedora> undRecebedora) {
        this.undRecebedora = undRecebedora;
    }

    public List<Designado> getDesignados() {
        return designados;
    }

    public void setDesignados(List<Designado> designados) {
        this.designados = designados;
    }

    public Pessoa getExpedidor() {
        return expedidor;
    }

    public void setExpedidor(Pessoa expedidor) {
        this.expedidor = expedidor;
    }

    public UndAdm getUnidadeExpedidora() {
        return unidadeExpedidora;
    }

    public void setUnidadeExpedidora(UndAdm unidadeExpedidora) {
        this.unidadeExpedidora = unidadeExpedidora;
    }
}
