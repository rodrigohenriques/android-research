package com.github.android.research.application.service.login;

import com.github.android.research.domain.model.Research;

import java.util.List;

public class LoginOutput {
    List<Research> researches;

    public LoginOutput(List<Research> researches) {
        this.researches = researches;
    }

    public int researchesCount() {
        return researches != null ? researches.size() : 0;
    }
}
