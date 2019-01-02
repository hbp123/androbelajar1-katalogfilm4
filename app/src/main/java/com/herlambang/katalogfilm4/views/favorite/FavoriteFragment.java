package com.herlambang.katalogfilm4.views.favorite;import android.content.Context;import android.net.Uri;import android.os.Bundle;import android.os.Handler;import android.support.v4.app.Fragment;import android.support.v4.widget.SwipeRefreshLayout;import android.support.v7.widget.LinearLayoutManager;import android.support.v7.widget.RecyclerView;import android.util.Log;import android.view.LayoutInflater;import android.view.View;import android.view.ViewGroup;import com.herlambang.katalogfilm4.R;import com.herlambang.katalogfilm4.data.MovieModel;import com.herlambang.katalogfilm4.data.local.MovieHelper;import com.herlambang.katalogfilm4.helper.MovieAdapter;import java.util.ArrayList;import java.util.List;import butterknife.BindView;import butterknife.ButterKnife;public class FavoriteFragment extends Fragment {    @BindView(R.id.recycler_favorite)    RecyclerView recyclerView;    @BindView(R.id.swipe)    SwipeRefreshLayout swipe;    MovieAdapter movieAdapter;    MovieHelper movieHelper;    ArrayList<MovieModel> movieModels;    @Override    public void onViewCreated(View view, Bundle savedInstanceState) {        super.onViewCreated(view, savedInstanceState);        getActivity().setTitle(getString(R.string.title_favorite));        ButterKnife.bind(this, view);        movieModels = new ArrayList<>();        movieAdapter = new MovieAdapter(movieModels, getContext());        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));        recyclerView.setAdapter(movieAdapter);        getData();        swipe.setColorScheme(android.R.color.holo_blue_bright,                android.R.color.holo_green_light,                android.R.color.holo_orange_light,                android.R.color.holo_red_light);        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {            @Override            public void onRefresh() {                new Handler().postDelayed(new Runnable() {                    @Override                    public void run() {                        swipe.setRefreshing(false);                        getData();                    }                }, 3000);            }        });    }    public void getData(){        movieHelper = new MovieHelper(getContext());        movieHelper.open();        ArrayList<MovieModel> movieModel = movieHelper.getAllMovie();        movieHelper.close();        for (int i=0; i<movieModel.size(); i++){            Log.e("id", movieModel.get(i).getRelease());        }        movieAdapter.setMovieModels(movieModel);        movieAdapter.notifyDataSetChanged();    }    @Override    public View onCreateView(LayoutInflater inflater, ViewGroup container,                             Bundle savedInstanceState) {        // Inflate the layout for this fragment        return inflater.inflate(R.layout.fragment_favorite, container, false);    }    @Override    public void onAttach(Context context) {        super.onAttach(context);    }    @Override    public void onDetach() {        super.onDetach();    }    public interface OnFragmentInteractionListener {        // TODO: Update argument type and name        void onFragmentInteraction(Uri uri);    }}