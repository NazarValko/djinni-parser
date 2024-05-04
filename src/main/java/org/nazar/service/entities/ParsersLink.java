package org.nazar.service.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "parsers_links")
public class ParsersLink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String link;

    @ManyToOne
    @JoinColumn(name = "FK_provider_id")
    private LinkProvider provider;

    public ParsersLink() {
    }

    public ParsersLink(Long id, String link, LinkProvider provider) {
        this.id = id;
        this.link = link;
        this.provider = provider;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public LinkProvider getProvider() {
        return provider;
    }

    public void setProvider(LinkProvider provider) {
        this.provider = provider;
    }
}
