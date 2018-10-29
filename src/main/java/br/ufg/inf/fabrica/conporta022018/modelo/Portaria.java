package br.ufg.inf.fabrica.conporta022018.modelo;

import java.sql.Blob;
import java.util.Date;
import java.util.List;

public class Portaria {
    private String idPortaria;
    private Integer anoId;
    private Integer segId;
    private String assunto;
    private Date dtExped;
    private String periodVig;
    private Date dtIniVig;
    private Date dtFimVig;
    private Date dPublicDou;
    private Integer horasDesig;
    private String resumo;
    private String textoCompleto;
    private Blob arqPdf;
    private String assinatura;
    private Enum<PortariaStatus> status;
    private List<PortariaReferenciada> portariasReferenciadas;
    private List<Designado> designados;
    private List<Recebedora> recebedoras;
    private UndAdm expedidora;

    /**
     * @return String return the idPortaria
     */
    public String getIdPortaria() {
        return idPortaria;
    }

    /**
     * @param idPortaria the idPortaria to set
     */
    public void setIdPortaria(String idPortaria) {
        this.idPortaria = idPortaria;
    }

    /**
     * @return Integer return the anoId
     */
    public Integer getAnoId() {
        return anoId;
    }

    /**
     * @param anoId the anoId to set
     */
    public void setAnoId(Integer anoId) {
        this.anoId = anoId;
    }

    /**
     * @return Integer return the segId
     */
    public Integer getSegId() {
        return segId;
    }

    /**
     * @param segId the segId to set
     */
    public void setSegId(Integer segId) {
        this.segId = segId;
    }

    /**
     * @return String return the assunto
     */
    public String getAssunto() {
        return assunto;
    }

    /**
     * @param assunto the assunto to set
     */
    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    /**
     * @return Date return the dtExped
     */
    public Date getDtExped() {
        return dtExped;
    }

    /**
     * @param dtExped the dtExped to set
     */
    public void setDtExped(Date dtExped) {
        this.dtExped = dtExped;
    }

    /**
     * @return String return the periodVig
     */
    public String getPeriodVig() {
        return periodVig;
    }

    /**
     * @param periodVig the periodVig to set
     */
    public void setPeriodVig(String periodVig) {
        this.periodVig = periodVig;
    }

    /**
     * @return Date return the dtIniVig
     */
    public Date getDtIniVig() {
        return dtIniVig;
    }

    /**
     * @param dtIniVig the dtIniVig to set
     */
    public void setDtIniVig(Date dtIniVig) {
        this.dtIniVig = dtIniVig;
    }

    /**
     * @return Date return the dtFimVig
     */
    public Date getDtFimVig() {
        return dtFimVig;
    }

    /**
     * @param dtFimVig the dtFimVig to set
     */
    public void setDtFimVig(Date dtFimVig) {
        this.dtFimVig = dtFimVig;
    }

    /**
     * @return Date return the dPublicDou
     */
    public Date getDPublicDou() {
        return dPublicDou;
    }

    /**
     * @param dPublicDou the dPublicDou to set
     */
    public void setDPublicDou(Date dPublicDou) {
        this.dPublicDou = dPublicDou;
    }

    /**
     * @return Integer return the horasDesig
     */
    public Integer getHorasDesig() {
        return horasDesig;
    }

    /**
     * @param horasDesig the horasDesig to set
     */
    public void setHorasDesig(Integer horasDesig) {
        this.horasDesig = horasDesig;
    }

    /**
     * @return String return the resumo
     */
    public String getResumo() {
        return resumo;
    }

    /**
     * @param resumo the resumo to set
     */
    public void setResumo(String resumo) {
        this.resumo = resumo;
    }

    /**
     * @return String return the textoCompleto
     */
    public String getTextoCompleto() {
        return textoCompleto;
    }

    /**
     * @param textoCompleto the textoCompleto to set
     */
    public void setTextoCompleto(String textoCompleto) {
        this.textoCompleto = textoCompleto;
    }

    /**
     * @return Blob return the arqPdf
     */
    public Blob getArqPdf() {
        return arqPdf;
    }

    /**
     * @param arqPdf the arqPdf to set
     */
    public void setArqPdf(Blob arqPdf) {
        this.arqPdf = arqPdf;
    }

    /**
     * @return String return the assinatura
     */
    public String getAssinatura() {
        return assinatura;
    }

    /**
     * @param assinatura the assinatura to set
     */
    public void setAssinatura(String assinatura) {
        this.assinatura = assinatura;
    }

    /**
     * @return Enum<PortariaStatus> return the status
     */
    public Enum<PortariaStatus> getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(Enum<PortariaStatus> status) {
        this.status = status;
    }

    /**
     * @return List<PortariaReferenciada> return the portariasReferenciadas
     */
    public List<PortariaReferenciada> getPortariasReferenciadas() {
        return portariasReferenciadas;
    }

    /**
     * @param portariasReferenciadas the portariasReferenciadas to set
     */
    public void setPortariasReferenciadas(List<PortariaReferenciada> portariasReferenciadas) {
        this.portariasReferenciadas = portariasReferenciadas;
    }

    /**
     * @return List<Designado> return the designados
     */
    public List<Designado> getDesignados() {
        return designados;
    }

    /**
     * @param designados the designados to set
     */
    public void setDesignados(List<Designado> designados) {
        this.designados = designados;
    }

    /**
     * @return List<Recebedora> return the recebedoras
     */
    public List<Recebedora> getRecebedoras() {
        return recebedoras;
    }

    /**
     * @param recebedoras the recebedoras to set
     */
    public void setRecebedoras(List<Recebedora> recebedoras) {
        this.recebedoras = recebedoras;
    }

    /**
     * @return UndAdm return the expedidora
     */
    public UndAdm getExpedidora() {
        return expedidora;
    }

    /**
     * @param expedidora the expedidora to set
     */
    public void setExpedidora(UndAdm expedidora) {
        this.expedidora = expedidora;
    }

}