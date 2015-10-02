package com.github.android.research.mock.server.infrastructure;

import java.util.List;

import com.github.android.research.mock.server.domain.model.Research;

/**
 * Created by admin on 30/09/15.
 */
public class AuthServiceResponse extends ServiceResponse {
    protected List<Research> researches;

    public List<Research> getResearches() {
        return researches;
    }
}
