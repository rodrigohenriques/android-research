package com.github.android.research.infrastructure.backend;

import com.github.android.research.domain.model.Research;

import java.util.List;

/**
 * Created by admin on 30/09/15.
 */
public class AuthServiceResponse extends ServiceResponse {
    private List<Research> researches;

    public List<Research> getResearches() {
        return researches;
    }
}
