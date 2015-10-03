package com.github.android.research.application.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.android.research.R;
import com.github.android.research.infrastructure.helper.DialogHelper;
import com.github.android.research.application.module.ApplicationModule;
import com.github.android.research.application.service.ApplicationService;
import com.github.android.research.application.service.ApplicationServiceCallback;
import com.github.android.research.application.service.ApplicationServiceError;
import com.github.android.research.application.service.research.ListResearchesInput;
import com.github.android.research.application.ui.adapter.ResearchRecyclerViewAdapter;
import com.github.android.research.application.ui.recyclerview.DividerItemDecoration;
import com.github.android.research.domain.model.Research;
import com.google.inject.Inject;
import com.google.inject.name.Named;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ResearchesFragment extends MainFragment implements ResearchRecyclerViewAdapter.OnItemClickListener {
    @Bind(R.id.simulation_textview_empty_list)
    View emptyList;

    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Bind(R.id.recyclerview)
    RecyclerView recyclerView;

    @Inject @Named(ApplicationModule.LIST_RESEARCHES_SERVICE)
    ApplicationService<ListResearchesInput, List<Research>> listResearchesService;

    List<Research> researches;

    ResearchRecyclerViewAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_researches, container, false);

        ButterKnife.bind(this, rootView);

        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        return rootView;
    }

    @Override
    public ViewGroup getRootView() {
        return (ViewGroup) getView().getRootView().findViewById(R.id.home_frame);
    }

    @Override
    public void onStart() {
        super.onStart();

        loadData();
    }

    private void loadData() {
        startProgress();

        ListResearchesInput input = new ListResearchesInput();

        listResearchesService.execute(input, new ApplicationServiceCallback<List<Research>>() {
            @Override
            public void onSuccess(List<Research> researches) {
                adapter = new ResearchRecyclerViewAdapter(getActivity(), researches);
                adapter.setOnItemClickListener(ResearchesFragment.this);

                recyclerView.setAdapter(adapter);

                finishProgress();
            }

            @Override
            public void onError(ApplicationServiceError error) {
                DialogHelper.showErrorDialog(getActivity(), error.getCode(), error.getMessage());
                finishProgress();
            }
        });
    }

    @OnClick(R.id.simulation_button_new)
    public void performResearch() {

    }

    public void setResearches(List<Research> researches) {
        this.researches = researches;
    }

    @Override
    public void onItemClick(Research research) {
        justSnackIt(research.getName());
    }
}
