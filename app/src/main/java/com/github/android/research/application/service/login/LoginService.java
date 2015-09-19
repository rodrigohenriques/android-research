package com.github.android.research.application.service.login;

import android.os.AsyncTask;

import com.github.android.research.application.service.ApplicationService;
import com.github.android.research.application.service.ApplicationServiceCallback;
import com.github.android.research.domain.model.Research;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LoginService implements ApplicationService<LoginInput, LoginOutput> {

    @Override
    public void execute(LoginInput loginInput, final ApplicationServiceCallback<LoginOutput> callback) {
        new AsyncTask<Void, Void, LoginOutput>() {

            @Override
            protected LoginOutput doInBackground(Void... params) {

                List<Research> researches = new ArrayList<>();

                try {
                    Thread.sleep(5000);

                    researches.add(new Research(1, "Pesquisa 1"));
                    researches.add(new Research(2, "Pesquisa 2"));

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                return new LoginOutput(Collections.unmodifiableList(researches));
            }

            @Override
            protected void onPostExecute(final LoginOutput output) {
                callback.onSuccess(output);
            }
        }.execute();
    }
}
