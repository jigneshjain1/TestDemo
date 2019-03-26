package com.app.testdemo.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ClsPostResponse {
    @SerializedName("hits")
    private List<ClsHitRequest> hitList = null;

    @SerializedName("nbHits")
    private Integer nbHits;

    @SerializedName("page")
    private Integer page;

    @SerializedName("nbPages")
    private Integer nbPages;

    @SerializedName("hitsPerPage")
    private Integer hitsPerPage;

    @SerializedName("processingTimeMS")
    private Integer processingTimeMS;

    @SerializedName("exhaustiveNbHits")
    private Boolean exhaustiveNbHits;

    @SerializedName("query")
    private String query;

    @SerializedName("params")
    private String params;


    public List<ClsHitRequest> getHitList() {
        return hitList;
    }

    public void setHitList(List<ClsHitRequest> hitList) {
        this.hitList = hitList;
    }

    public Integer getNbHits() {
        return nbHits;
    }

    public void setNbHits(Integer nbHits) {
        this.nbHits = nbHits;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getNbPages() {
        return nbPages;
    }

    public void setNbPages(Integer nbPages) {
        this.nbPages = nbPages;
    }

    public Integer getHitsPerPage() {
        return hitsPerPage;
    }

    public void setHitsPerPage(Integer hitsPerPage) {
        this.hitsPerPage = hitsPerPage;
    }

    public Integer getProcessingTimeMS() {
        return processingTimeMS;
    }

    public void setProcessingTimeMS(Integer processingTimeMS) {
        this.processingTimeMS = processingTimeMS;
    }

    public Boolean getExhaustiveNbHits() {
        return exhaustiveNbHits;
    }

    public void setExhaustiveNbHits(Boolean exhaustiveNbHits) {
        this.exhaustiveNbHits = exhaustiveNbHits;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

}