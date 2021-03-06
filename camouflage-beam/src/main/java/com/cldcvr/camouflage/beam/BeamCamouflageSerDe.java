package com.cldcvr.camouflage.beam;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class BeamCamouflageSerDe implements Serializable {
    private final List<TopicAndColumns> topicAndColumns;

    public BeamCamouflageSerDe(@JsonProperty("DLPMetaData") List<TopicAndColumns> topicAndColumns)
    {
        this.topicAndColumns=topicAndColumns;
    }
    public List<TopicAndColumns> getList() {
        return topicAndColumns;
    }

    @Override
    public String toString() {
        return "SerDe{" +
                "topicAndColumns=" + topicAndColumns +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BeamCamouflageSerDe that = (BeamCamouflageSerDe) o;
        return Objects.equals(topicAndColumns, that.topicAndColumns);
    }

    @Override
    public int hashCode() {
        return Objects.hash(topicAndColumns);
    }
}

