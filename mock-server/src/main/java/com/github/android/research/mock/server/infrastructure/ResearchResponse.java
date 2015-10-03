package com.github.android.research.mock.server.infrastructure;

import com.github.android.research.mock.server.domain.model.Research;

import java.util.List;

public class ResearchResponse extends ServiceResponse {
    protected List<Research> researches;

    public List<Research> getResearches() {
        return researches;
    }
}
