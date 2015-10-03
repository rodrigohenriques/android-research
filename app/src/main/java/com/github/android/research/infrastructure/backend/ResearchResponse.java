package com.github.android.research.infrastructure.backend;

import com.github.android.research.domain.model.Research;

import java.util.List;

public class ResearchResponse extends ServiceResponse {
    public List<Research> researches;

    public List<Research> getResearches() {
        return researches;
    }
}
