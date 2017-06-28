package fr.cesi.basecode.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.cesi.base.controllers.fragment.IPopableFragment;
import fr.cesi.basecode.R;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WorkingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WorkingFragment extends Fragment implements IPopableFragment {
    private static final String ARG_DURATION = "getDuration";
    private static final String ARG_DURATION_PAUSE = "getPause";
    private static final String ARG_NUMBER_REPETITION = "getRepetition";

    private int _getDuration;
    private int _getPause;
    private int _getRepetition;


    public WorkingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment WorkingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WorkingFragment newInstance(int getDuration, int getPause, int getRepetition) {
        WorkingFragment fragment = new WorkingFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_DURATION, getDuration);
        args.putInt(ARG_DURATION_PAUSE, getPause);
        args.putInt(ARG_NUMBER_REPETITION, getRepetition);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            _getDuration = getArguments().getInt(ARG_DURATION);
            _getPause = getArguments().getInt(ARG_DURATION_PAUSE);
            _getRepetition = getArguments().getInt(ARG_NUMBER_REPETITION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_working, container, false);
    }

    @Override
    public boolean hasParent() {
        return true;
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }
}
