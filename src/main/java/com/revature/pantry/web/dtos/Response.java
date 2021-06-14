package com.revature.pantry.web.dtos;

import com.fasterxml.jackson.annotation.*;

import java.util.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Response {

    @JsonProperty("q")
    private String q;

    @JsonProperty("from")
    private int from;

    @JsonProperty("to")
    private int to;

    @JsonProperty("count")
    private int count;

    @JsonProperty("more")
    private boolean more;

    @JsonProperty("hits")
    private List<Hit> hits;

    public Response(){
        super();
    }

    public Response(String q, int from, int to, int count, boolean more, List<Hit> hits) {
        this.q = q;
        this.from = from;
        this.to = to;
        this.count = count;
        this.more = more;
        this.hits = hits;
    }

    public String getQ() {
        return q;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public int getCount() {
        return count;
    }

    public boolean isMore() {
        return more;
    }

    public List<Hit> getHits() {
        return hits;
    }
}
